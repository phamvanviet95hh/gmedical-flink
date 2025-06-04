package com.my_flink_job;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.servicve.MinioService;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class App {

  private static MinioService minioService = new MinioService();

  static Logger logger1 = LoggerFactory.getLogger(App.class);
  public App(MinioService minioService) {
    App.minioService = minioService;

  }

  public static void main(String[] args) throws Exception {


    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.enableCheckpointing(12000, CheckpointingMode.EXACTLY_ONCE);
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
    text.map(new MapFunction<String, String>() {
      @Override
      public String map(String s) throws Exception {

        JSONObject json = new JSONObject(s);
        // ✅ Lấy key từ JSON root level
        String key = json.getString("Key"); // Đây là dạng không encode
        // Tách bỏ tên bucket khỏi key nếu cần
        String objectPath = key.replace("gmedical.lake/", "");
        logger1.info("Received: -----------------------------------------------------------------------> " + objectPath);
        App.dataMinio(objectPath);
        return s;
      }
    }).rebalance().print();
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
    logger1.info("Data String minio: -----------------------------------------------------------------------> " + xmlContent);
    // Parse XML thành DTO chính
    // GiamDinhHs giamDinhHs = xmlMapper.readValue(xmlContent, GiamDinhHs.class);

    // Parse XML để lấy các FILEHOSO
    // DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    // DocumentBuilder builder = factory.newDocumentBuilder();
    // Document document = builder.parse(new ByteArrayInputStream(xmlBytes));
    // NodeList fileHosoList = document.getElementsByTagName("FILEHOSO");

    // Map ánh xạ LOAIHOSO -> class
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
