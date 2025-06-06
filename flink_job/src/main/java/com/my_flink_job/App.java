package com.my_flink_job;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.*;
import com.my_flink_job.servicve.*;
import org.apache.flink.table.api.Table;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static org.apache.flink.table.api.Expressions.$;

public class App {
  private static MinioS3Reader minioService = new MinioS3Reader();
  static Logger logger1 = LoggerFactory.getLogger(App.class);
  public App(MinioS3Reader minioService) {
    App.minioService = minioService;

  }
  public static int count = 1;
  private static final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

  private static String pathMinio = null;
  private static final StreamTableEnvironment tableEnv = StreamTableEnvironment.create(
          env,
          EnvironmentSettings.newInstance().inStreamingMode().build());
  public static void main(String[] args) throws Exception {

    env.enableCheckpointing(12000, CheckpointingMode.EXACTLY_ONCE);

    logger1.info("Start create CATALOG: ----------------------------------------------------------------------- ");
    tableEnv.executeSql(
            "CREATE CATALOG iceberg WITH (" +
                    "'type'='iceberg'," +
                    "'catalog-impl'='org.apache.iceberg.nessie.NessieCatalog'," +
                    "'io-impl'='org.apache.iceberg.aws.s3.S3FileIO'," +
                    "'uri'='http://10.6.8.29:19120/api/v1'," +
                    "'authentication.type'='none'," +
                    "'ref'='main'," +
                    "'client.assume-role.region'='us-east-1'," +
                    "'warehouse' = 's3://warehouse'," +
                    "'s3.endpoint'='http://191.168.112.2:9000'" +
                    ")"
    );
    // List all catalogs
    TableResult result = tableEnv.executeSql("SHOW CATALOGS");

    // Print the result to standard out
    result.print();


    logger1.info("End create CATALOG: ----------------------------------------------------------------------- ");
    logger1.info("Start connect: -----------------------------------------------------------------------> ");
    KafkaSource<String> source = KafkaSource.<String>builder()
            .setBootstrapServers("10.6.8.29:9092")
            .setTopics("test-minio")
            .setGroupId("gmedical-id")
            .setStartingOffsets(OffsetsInitializer.earliest())
            .setValueOnlyDeserializer(new SimpleStringSchema())
            .setProperty("commit.offsets.on.checkpoint", "true")
            .setProperty("auto.commit.interval.ms", "5000")
            .setProperty("enable.auto.commit", "false")
            .build();

    DataStream<String> text = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");
    logger1.info("Connect Kafka Success: -----------------------------------------------------------------------> {}",text);
//    text.map(new MapFunction<String, String>() {
//      @Override
//      public String map(String s) throws Exception {
//
//        pathMinio = s;
//        return s;
//      }
//    }).rebalance().addSink(new MyIcebergSink());

    // map Kafka message (đường dẫn) -> FullXmlData object (chứa nhiều list con)
    DataStream<String> rawXmlString = text.map(new MinioXmlFetcher());
    DataStream<GiamDinhHs> fullXmlDataStream = rawXmlString
            .flatMap(new FullXmlParser());
    fullXmlDataStream.print();
    env.execute("Viet Bm");

  }

  public static void dataMinio(String fileData) throws Exception {
    logger1.info("Start minio: -----------------------------------------------------------------------> " + fileData);
    // Implement your conversion logic here
    // For example, you can read the InputStream and process it as needed
    InputStream tmpFileData =  minioService.downloadFile(fileData);
    App.convertData(tmpFileData);
  }

  public static void convertData(InputStream fileData) throws IOException, ParserConfigurationException, SAXException {
    byte[] xmlBytes = toByteArray(fileData);
    String xmlContent = new String(xmlBytes, StandardCharsets.UTF_8);

    XmlMapper xmlMapper = new XmlMapper();
    xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    // Parse XML thành DTO chính
    GiamDinhHs giamDinhHs = xmlMapper.readValue(xmlContent, GiamDinhHs.class);
    logger1.info("GiamDinhHs: -----------------------------------------------------------------------> " + giamDinhHs.getThongTinDonVi().getMaCSKCB());
    // Parse XML để lấy các FILEHOSO
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(new ByteArrayInputStream(xmlBytes));
    NodeList fileHosoList = document.getElementsByTagName("FILEHOSO");

    // Map ánh xạ LOAIHOSO -> class
    Map<String, Class<?>> xmlClassMap = Map.ofEntries(
            Map.entry("XML1", Xml1.class),
            Map.entry("XML2", Xml2.class),
            Map.entry("XML3", Xml3.class),
            Map.entry("XML4", Xml4.class),
            Map.entry("XML5", Xml5.class),
            Map.entry("XML6", Xml6.class),
            Map.entry("XML7", Xml7.class),
            Map.entry("XML8", Xml8.class),
            Map.entry("XML9", Xml9.class),
            Map.entry("XML10", Xml10.class),
            Map.entry("XML11", Xml11.class),
            Map.entry("XML12", Xml12.class),
            Map.entry("XML13", Xml13.class),
            Map.entry("XML14", Xml14.class),
            Map.entry("XML15", Xml15.class)
    );
    for (int i = 0; i < fileHosoList.getLength(); i++) {
      Element fileHoso = (Element) fileHosoList.item(i);
      String loaiHoso = fileHoso.getElementsByTagName("LOAIHOSO").item(0).getTextContent();
      String encodedContent = fileHoso.getElementsByTagName("NOIDUNGFILE").item(0).getTextContent();

      byte[] decodedBytes = Base64.getDecoder().decode(encodedContent);
      String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);

      logger1.info("loaiHoso: -----------------------------------------------------------------------> " + loaiHoso);
      Class<?> clazz = xmlClassMap.getOrDefault(loaiHoso, Xml1.class);
      Object xmlObj = xmlMapper.readValue(innerXml, clazz);

      // Xử lý từng loại
      if (xmlObj instanceof Xml1) {
        Xml1 xml1 = (Xml1) xmlObj;
        logger1.info("Xml1: {}", xml1.getHoTen());

//        DataStream<Tuple2<Long, String>> dataStream = env.fromElements(
//                Tuple2.of(Long.valueOf(xml1.getStt()), xml1.getHoTen()));
        // apply a map transformation to convert the Tuple2 to an ExampleData object
//        DataStream<ExampleData> mappedStream = dataStream.map(new MapFunction<Tuple2<Long, String>, ExampleData>() {
//          @Override
//          public ExampleData map(Tuple2<Long, String> value) throws Exception {
//            // perform your mapping logic here and return a ExampleData instance
//            // for example:
//            return new ExampleData(value.f0, value.f1);
//          }
//        });

      } else if (xmlObj instanceof Xml2) {
        Xml2 xml2 = (Xml2) xmlObj;
        if (xml2.getDanhSachChiTietThuoc() != null && xml2.getDanhSachChiTietThuoc().getChiTietThuoc().get(0).getMaLk() != null) {
          logger1.info("Xml2: {}" , xml2.getDanhSachChiTietThuoc().getChiTietThuoc().get(0).getMaLk());
        } else {
          logger1.warn("Bỏ qua Xml2 vì thiếu getDanhSachChiTietThuoc hoặc maLk");
        }

      }else if (xmlObj instanceof Xml3) {
        Xml3 xml3 = (Xml3) xmlObj;
        if (xml3.getDsachChiTietDvkt() != null && xml3.getDsachChiTietDvkt().getChiTietDvkt().get(0).getMaLk() != null) {
          logger1.info("Xml3: {}", xml3.getDsachChiTietDvkt().getChiTietDvkt().get(0).getMaLk());
        } else {
          logger1.warn("Bỏ qua Xml3 vì thiếu chiTietDvkt hoặc maLk");
        }
      }else if (xmlObj instanceof Xml4) {
        Xml4 xml4 = (Xml4) xmlObj;
        if (xml4.getDanhSachChiTietCls() != null &&
                xml4.getDanhSachChiTietCls().getChiTietCls() != null &&
                !xml4.getDanhSachChiTietCls().getChiTietCls().isEmpty() &&
                xml4.getDanhSachChiTietCls().getChiTietCls().get(0).getMaLk() != null) {
          logger1.info("Xml4: {}", xml4.getDanhSachChiTietCls().getChiTietCls().get(0).getMaLk());
        }else {
          logger1.warn("Bỏ qua Xml4 vì thiếu getDanhSachChiTietCls hoặc maLk");
        }
      }else if (xmlObj instanceof Xml5) {
        Xml5 xml5 = (Xml5) xmlObj;
        if (xml5.getDsachChiTietDienBienBenh() != null &&
                xml5.getDsachChiTietDienBienBenh().getChiTietDienBienBenhList() != null &&
                !xml5.getDsachChiTietDienBienBenh().getChiTietDienBienBenhList().isEmpty() &&
                xml5.getDsachChiTietDienBienBenh().getChiTietDienBienBenhList().get(0).getMaLk() != null) {
          logger1.info("Xml5: {}", xml5.getDsachChiTietDienBienBenh().getChiTietDienBienBenhList().get(0).getMaLk());
        }else {
          logger1.warn("Bỏ qua Xml5 vì thiếu getDsachChiTietDienBienBenh hoặc maLk");
        }
      }else if (xmlObj instanceof Xml6) {
        Xml6 xml6 = (Xml6) xmlObj;
        if (xml6.getHoSoBACSVADTHiv() != null && xml6.getHoSoBACSVADTHiv().getMaLk() != null) {
          logger1.info("Xml6: {}", xml6.getHoSoBACSVADTHiv().getMaLk());
        }else {
          logger1.warn("Bỏ qua Xml6 vì thiếu getHoSoBACSVADTHiv hoặc maLk");
        }
      }else if (xmlObj instanceof Xml7) {
        Xml7 xml7 = (Xml7) xmlObj;
        if (xml7.getMaLk() != null) {
          logger1.info("Xml7: {}", xml7.getMaLk());
        }else {
          logger1.warn("Bỏ qua Xml7 vì thiếu maLk");
        }
      }else if (xmlObj instanceof Xml8) {
        Xml8 xml8 = (Xml8) xmlObj;
        if (xml8.getMaLk() != null) {
          logger1.info("Xml8: {}", xml8.getMaLk());
        }else {
          logger1.warn("Bỏ qua Xml8 vì thiếu maLk");
        }
      }else if (xmlObj instanceof Xml9) {
        Xml9 xml9 = (Xml9) xmlObj;
        if (xml9.getDSachGiayChungSinh() != null &&
                xml9.getDSachGiayChungSinh().getDulieuGiayChungSinhs() != null &&
                !xml9.getDSachGiayChungSinh().getDulieuGiayChungSinhs().isEmpty() &&
                xml9.getDSachGiayChungSinh().getDulieuGiayChungSinhs().get(0).getMaLk() != null) {
          logger1.info("Xml9: {}", xml9.getDSachGiayChungSinh().getDulieuGiayChungSinhs().get(0).getMaLk());
        }else {
          logger1.warn("Bỏ qua Xml9 vì thiếu getDSachGiayChungSinh hoặc maLk");
        }
      }else if (xmlObj instanceof Xml10) {
        Xml10 xml10 = (Xml10) xmlObj;
        if (xml10.getMaLk() != null) {
          logger1.info("Xml10: {}", xml10.getMaLk());
        }else {
          logger1.warn("Bỏ qua Xml10 vì thiếu  maLk");
        }
      }else if (xmlObj instanceof Xml11) {
        Xml11 xml11 = (Xml11) xmlObj;
        if (xml11.getMaLk() != null) {
          logger1.info("Xml11: {}", xml11.getMaLk());
        }else {
          logger1.warn("Bỏ qua Xml11 vì thiếu maLk");
        }
      }else if (xmlObj instanceof Xml12) {
        Xml12 xml12 = (Xml12) xmlObj;
        if (xml12.getHoTen() != null) {
          logger1.info("Xml12: {}", xml12.getHoTen());
        }else {
          logger1.warn("Bỏ qua Xml12 vì thiếu getHoTen");
        }
      }else if (xmlObj instanceof Xml13) {
        Xml13 xml13 = (Xml13) xmlObj;
        if (xml13.getMaLk() != null) {
          logger1.info("Xml13: {}", xml13.getMaLk());
        }else {
          logger1.warn("Bỏ qua Xml13 vì thiếu maLk");
        }
      }else if (xmlObj instanceof Xml14) {
        Xml14 xml14 = (Xml14) xmlObj;
        if (xml14.getMaLk() != null) {
          logger1.info("Xml14: {}", xml14.getMaLk());
        }else {
          logger1.warn("Bỏ qua Xml14 vì thiếu maLk");
        }
      }else if (xmlObj instanceof Xml15) {
        Xml15 xml15 = (Xml15) xmlObj;
        if (xml15.getDsachChiTietDieuTriBenhLao() != null &&
                xml15.getDsachChiTietDieuTriBenhLao().getChiTietDieuTriBenhLaoList() != null &&
                !xml15.getDsachChiTietDieuTriBenhLao().getChiTietDieuTriBenhLaoList().isEmpty() &&
                xml15.getDsachChiTietDieuTriBenhLao().getChiTietDieuTriBenhLaoList().get(0).getMaLk() != null) {
          logger1.info("Xml15: {}", xml15.getDsachChiTietDieuTriBenhLao().getChiTietDieuTriBenhLaoList().get(0).getMaLk());
        }else {
          logger1.warn("Bỏ qua Xml15 vì thiếu getDsachChiTietDieuTriBenhLao hoặc maLk");
        }
      }

    }
  }
  public static byte[] toByteArray(InputStream input) throws IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    byte[] data = new byte[1024];
    int nRead;
    while ((nRead = input.read(data, 0, data.length)) != -1) {
      buffer.write(data, 0, nRead);
    }
    return buffer.toByteArray();
  }


}
