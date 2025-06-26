package com.my_flink_job;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.*;
import com.my_flink_job.dtos.table.TableDtos;
import com.my_flink_job.servicve.*;
import org.apache.flink.table.api.*;
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

    env.enableCheckpointing(15000L, CheckpointingMode.EXACTLY_ONCE);

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
                    "'s3.endpoint'='http://10.6.8.29:9000'" +
                    ")"
    );
    // Hiển thị tất cả catalogs
    TableResult result = tableEnv.executeSql("SHOW CATALOGS");
    result.print();

    //Lựa chọn catalogs sử dụng
    tableEnv.useCatalog("iceberg");


    // Kiểm tra và tạo mới DATABASE theo DB
    tableEnv.executeSql("CREATE DATABASE IF NOT EXISTS db_3179");

    // Kiểm tra và tạo mới bảng
    tableEnv.executeSql(TableDtos.admissionCheckin);
    tableEnv.executeSql(TableDtos.admisionMed);
    tableEnv.executeSql(TableDtos.admisionEquipment);
    tableEnv.executeSql(TableDtos.admisionSubclinical);
    tableEnv.executeSql(TableDtos.admisionClinical);
    tableEnv.executeSql(TableDtos.admisionDischarge);
    tableEnv.executeSql(TableDtos.admisionMedicalRecord);
    tableEnv.executeSql(TableDtos.admisionBirthCertificate);
    tableEnv.executeSql(TableDtos.admissionMaternityLeave);
    tableEnv.executeSql(TableDtos.admisionBenefitLeave);
    tableEnv.executeSql(TableDtos.admissionMedicalExam);
    tableEnv.executeSql(TableDtos.admisionReferral);
    tableEnv.executeSql(TableDtos.admisionAppointment);
    tableEnv.executeSql(TableDtos.admissionTuberculosis);

    logger1.info("End create CATALOG: ----------------------------------------------------------------------- ");
    logger1.info("Start connect: -----------------------------------------------------------------------> ");
    //Cấu hình kết nối Kafka
    KafkaSource<String> source = KafkaSource.<String>builder()
            .setBootstrapServers("10.6.8.29:9092")
            .setTopics("test-minio")
            .setGroupId("gmedical-id")
            .setStartingOffsets(OffsetsInitializer.latest())
            .setValueOnlyDeserializer(new SimpleStringSchema())
            .setProperty("commit.offsets.on.checkpoint", "true")
            .setProperty("auto.commit.interval.ms", "5000")
            .setProperty("enable.auto.commit", "false")
            .build();

    //Nhận message từ Kafka job
    DataStream<String> text = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");
    logger1.info("Connect Kafka Success: -----------------------------------------------------------------------> {}",text);

    // map Kafka message (đường dẫn) -> FullXmlData object (chứa nhiều list con)
    DataStream<String> rawXmlString = text.flatMap(new MinioXmlFetcher());
    DataStream<GiamDinhHs> fullXmlDataStream = rawXmlString.flatMap(new FullXmlParser());

    //Comvert Data theo XML
    DataStream<Xml1> xml1Stream = fullXmlDataStream.flatMap(new Xml1Extractor());
    DataStream<ChiTietThuoc> xml2Stream = fullXmlDataStream.flatMap(new Xml2Extractor());
    DataStream<ChiTietDvkt> xml3Stream = fullXmlDataStream.flatMap(new Xml3Extractor());
    DataStream<ChiTietCls> xml4Stream = fullXmlDataStream.flatMap(new Xml4Extractor());
    DataStream<ChiTietDienBienBenh> xml5Stream = fullXmlDataStream.flatMap(new Xml5Extractor());
    DataStream<Xml7> xml7Stream = fullXmlDataStream.flatMap(new Xml7Extractor());
    DataStream<Xml8> xml8Stream = fullXmlDataStream.flatMap(new Xml8Extractor());
    DataStream<DulieuGiayChungSinh> xml9Stream = fullXmlDataStream.flatMap(new Xml9Extractor());
    DataStream<Xml10> xml10Stream = fullXmlDataStream.flatMap(new Xml10Extractor());
    DataStream<Xml11> xml11Stream = fullXmlDataStream.flatMap(new Xml11Extractor());
    DataStream<Xml12> xml12Stream = fullXmlDataStream.flatMap(new Xml12Extractor());
    DataStream<Xml13> xml13Stream = fullXmlDataStream.flatMap(new Xml13Extractor());
    DataStream<Xml14> xml14Stream = fullXmlDataStream.flatMap(new Xml14Extractor());
    DataStream<ChiTietDieuTriBenhLao> xml15Stream = fullXmlDataStream.flatMap(new Xml15Extractor());


    //Khởi tạo dữ liệu
    Table xml1Table = tableEnv.fromDataStream(
            xml1Stream, $("maLk"), $("stt"), $("maBn"), $("hoTen"), $("soCccd"), $("ngaySinh")
            , $("gioiTinh"), $("nhomMau"), $("maQuocTich"), $("maDanToc"), $("maNgheNghiep")
            , $("diaChi"), $("maTinhCuTru"), $("maHuyenCuTru"), $("maXaCuTru"), $("dienThoai"), $("maTheBhyt"), $("maDkbd")
            , $("gtTheTu"), $("gtTheDen"), $("ngayMienCct"), $("lyDoVv"), $("lyDoVnt"), $("maLyDoVnt"), $("chanDoanVao")
            , $("chanDoanRv"), $("maBenhChinh"), $("maBenhKt"), $("maBenhYhct"), $("maPtttQt"), $("maDoiTuongKcb"), $("maNoiDi")
            , $("maNoiDen"), $("maTaiNan"), $("ngayVao"), $("ngayVaoNoiTru"), $("ngayRa"), $("giayChuyenTuyen")
            , $("soNgayDt"), $("ppDieuTri"), $("ketQuaDt"), $("maLoaiRv"), $("ghiChu"), $("ngayTtoan")
            , $("tThuoc"), $("tVtyt"), $("tTongChiBv"), $("tTongChiBh"), $("tBntt"), $("tBncct")
            , $("tBhtt"), $("tNguonKhac"), $("tBhttGdv"), $("namQt"), $("thangQt"), $("maLoaiKcb")
            , $("maKhoa"), $("maCskcb"), $("maKhuVuc")
            , $("canNang"), $("canNangCon"), $("namNamLienTuc"), $("ngayTaiKham"), $("maHsba"), $("maTtdv")
            , $("duPhong")// đổi tên trường cho phù hợp với bảng Iceberg
    );

    Table xml2Table = tableEnv.fromDataStream(
            xml2Stream, $("maLk"), $("stt"), $("maThuoc"), $("maPpCheBien"), $("maCskcbThuoc"), $("maNhom")
            , $("tenThuoc"), $("donViTinh"), $("hamLuong"), $("duongDung"), $("dangBaoChe"), $("lieuDung"), $("cachDung")
            , $("soDangKy"), $("ttThau"), $("phamVi"), $("tyleTtBh"), $("soLuong"), $("donGia"), $("thanhTienBv")
            , $("thanhTienBh"), $("tNguonKhacNsnn"), $("tNguonKhacVtnn"), $("tNguonKhacVttn"), $("tNguonKhacCl"), $("tNguonKhac"), $("mucHuong")
            , $("tBntt"), $("tBncct"), $("tBhtt"), $("maKhoa"), $("maBacSi"), $("maDichVu"), $("ngayYl")
            , $("ngayThYl"), $("maPttt"), $("nguonCtra"), $("vetThuongTp"), $("duPhong")
    );

    Table xml3Table = tableEnv.fromDataStream(
            xml3Stream, $("maLk"), $("stt"), $("maDichVu"), $("maPtttQt"), $("maVatTu"), $("maNhom"), $("goiVtyt")
            , $("tenVatTu"), $("tenDichVu"), $("maXangDau"), $("donViTinh"), $("phamVi"), $("soLuong"), $("donGiaBv"), $("donGiaBh"), $("ttThau"), $("tyleTtDv")
            , $("tyleTtBh"), $("thanhTienBv"), $("thanhTienBh"), $("tTrantt"), $("mucHuong"), $("tNguonKhacNsnn"), $("tNguonKhacVtnn"), $("tNguonKhacVttn"), $("tNguonKhacCl")
            , $("tNguonKhac"), $("tBntt"), $("tBncct"), $("tBhtt"), $("maKhoa"), $("maGiuong"), $("maBacSi"), $("nguoiThucHien"), $("maBenh"), $("maBenhYhct")
            , $("ngayYl"), $("ngayThYl"), $("ngayKq"), $("maPttt"), $("vetThuongTp"), $("ppVoCam"), $("viTriThDvkt"), $("maMay"), $("maHieuSp")
            , $("taiSuDung"), $("duPhong")
    );
    Table xml4Table = tableEnv.fromDataStream(
            xml4Stream, $("maLk"), $("stt"), $("maDichVu"), $("maChiSo"), $("tenChiSo"), $("giaTri"), $("donViDo")
            , $("moTa"), $("ketLuan"), $("ngayKq"), $("maBsDocKq"), $("duPhong")

    );
    Table xml5Table = tableEnv.fromDataStream(
            xml5Stream, $("maLk"), $("stt"), $("dienBienLs"), $("giaiDoanBenh"), $("hoiChan"), $("phauThuat")
            , $("thoiDiemDbls"), $("nguoiThucHien"), $("duPhong")

    );
    Table xml7Table = tableEnv.fromDataStream(
            xml7Stream, $("maLk"), $("soLuuTru"), $("maYte"), $("maKhoaRv"), $("ngayVao"), $("ngayRa")
            , $("maDinhChiThai"), $("nguyenNhanDinhChi"), $("thoiGianDinhChi"), $("tuoiThai"), $("chanDoanRv"), $("ppDieuTri"), $("ghiChu")
            , $("maTtdv"), $("maBs"), $("tenBs"), $("ngayCt"), $("maCha"), $("maMe"), $("maTheTam")
            , $("hoTenCha"), $("hoTenMe"), $("soNgayNghi"), $("ngoaiTruTuNgay"), $("ngoaiTruDenNgay"), $("duPhong")
    );

    Table xml8Table = tableEnv.fromDataStream(
            xml8Stream, $("maLk"), $("maLoaiKcb"), $("hoTenCha"), $("hoTenMe"), $("nguoiGiamHo"), $("donVi"), $("ngayVao")
            , $("ngayRa"), $("chanDoanVao"), $("chanDoanRv"), $("qtBenhLy"), $("tomTatKq"), $("ppDieuTri"), $("ngaySinhCon"), $("ngayConChet")
            , $("soConChet"), $("ketQuaDieuTri"), $("ghiChu"), $("maTtdv"), $("ngayCt"), $("maTheTam"), $("duPhong")
    );

    Table xml9Table = tableEnv.fromDataStream(
            xml9Stream, $("maLk"), $("maBhxhNnd"), $("maTheNnd"), $("hoTenNnd"), $("ngaySinhNnd"), $("maDanTocNnd")
            , $("soCccdNnd"), $("ngayCapCccdNnd"), $("noiCapCccdNnd"), $("noiCuTruNnd"), $("maQuocTich"), $("maTinhCuTru"), $("maHuyenCuTru")
            , $("maXaCuTru"), $("hoTenCha"), $("maTheTam"), $("hoTenCon"), $("gioiTinhCon"), $("soCon"), $("lanSinh"), $("soConSong")
            , $("canNangCon"), $("ngaySinhCon"), $("noiSinhCon"), $("tinhTrangCon"), $("sinhConPhauThuat"), $("sinhConDuoi32Tuan"), $("ghiChu")
            , $("nguoiDoDe"), $("nguoiGhiPhieu"), $("ngayCt"), $("so"), $("quyenSo"), $("maTtdv"), $("duPhong")
    );

    Table xml10Table = tableEnv.fromDataStream(
            xml10Stream, $("maLk"), $("soSeri"), $("soCt"), $("soNgay"), $("donVi"), $("chanDoanRv"), $("tuNgay")
            , $("denNgay"), $("maTtdv"), $("tenBs"), $("maBs"), $("ngayCt"), $("duPhong")
    );

    Table xml11Table = tableEnv.fromDataStream(
            xml11Stream, $("maLk"), $("soCt"), $("soSeri"), $("soKcb"), $("donVi"), $("maBhxh"), $("maTheBhyt"), $("chanDoanRv"), $("ppDieuTri")
            , $("maDinhChiThai"), $("nguyenNhanDinhChi"), $("tuoiThai"), $("soNgayNghi"), $("tuNgay"), $("denNgay"), $("hoTenCha"), $("hoTenMe"), $("maTtdv")
            , $("maBs"), $("ngayCt"), $("maTheTam"), $("mauSo"), $("duPhong")
    );

    Table xml12Table = tableEnv.fromDataStream(
            xml12Stream, $("nguoiChuTri"), $("chucVu"), $("ngayHop"), $("hoTen"), $("ngaySinh"), $("soCccd"), $("ngayCapCccd"), $("noiCapCccd"), $("diaChi"), $("maTinhCuTru")
            , $("maHuyenCuTru"), $("maXaCuTru"), $("maBhxh"), $("maTheBhyt"), $("ngheNghiep"), $("dienThoai"), $("maDoiTuong"), $("khamGiamDinh"), $("soBienBan"), $("tyleTtctCu")
            , $("dangHuongCheDo"), $("ngayChungTu"), $("soGiayGioiThieu"), $("ngayDeNghi"), $("maDonVi"), $("gioiThieuCua"), $("ketQuaKham"), $("soVanBanCanCu"), $("tyleTtctMoi"), $("tongTyleTtct"), $("dangKhuyettat")
            , $("mucDoKhuyettat"), $("deNghi"), $("duocXacDinh"), $("duPhong")
    );


    Table xml13Table = tableEnv.fromDataStream(
            xml13Stream, $("maLk"), $("soHoSo"), $("soChuyenTuyen"), $("giayChuyenTuyen"), $("maCskcb"), $("maNoiDi"), $("maNoiDen"), $("hoTen"), $("ngaySinh")
            , $("gioiTinh"), $("maQuocTich"), $("maDanToc"), $("maNgheNghiep"), $("diaChi"), $("maTheBhyt"), $("gtTheDen"), $("ngayVao"), $("ngayVaoNoiTru")
            , $("ngayRa"), $("dauHieuLs"), $("chanDoanRv"), $("qtBenhLy"), $("tomTatKq"), $("ppDieuTri"), $("maBenhChinh"), $("maBenhKt"), $("maBenhYhct")
            , $("tenDichVu"), $("tenThuoc"), $("ppDieuTriDuplicate"), $("maLoaiRv"), $("maLyDoCt"), $("huongDieuTri"), $("phuongTienVc"), $("hoTenNguoiHt"), $("chucDanhNguoiHt")
            , $("maBacSi"), $("maTtdv"), $("duPhong")
    );

    Table xml14Table = tableEnv.fromDataStream(
            xml14Stream, $("maLk"), $("soGiayHenKl"), $("maCskcb"), $("hoTen"), $("ngaySinh"), $("gioiTinh"), $("diaChi"), $("maTheBhyt"), $("gtTheDen")
            , $("ngayVao"), $("ngayVaoNoiTru"), $("ngayRa")
            , $("ngayHenKl"), $("chanDoanRv"), $("maBenhChinh"), $("maBenhKt"), $("maBenhYhct"), $("maDoiTuongKcb")
            , $("maBacSi"), $("maTtdv"), $("ngayCt")
            , $("duPhong")
    );

    Table xml15Table = tableEnv.fromDataStream(
            xml15Stream, $("maLk"), $("stt"), $("maBn"), $("hoTen"), $("soCccd"), $("phanLoaiLaoViTri"), $("phanLoaiLaoTs")
            , $("phanLoaiLaoHiv"), $("phanLoaiLaoVk"), $("phanLoaiLaoKt"), $("loaiDtriLao"), $("ngayBdDtriLao"), $("phacDoDtriLao"), $("ngayKtDtriLao"), $("ketQuaDtriLao")
            , $("maCskcb"), $("ngayKdHiv"), $("bddtArv"), $("ngayBatDauDtCtx"), $("duPhong")
    );

    // Tạo view tạm cho Table
    tableEnv.createTemporaryView("xml1_view", xml1Table);
    tableEnv.createTemporaryView("xml2_view", xml2Table);
    tableEnv.createTemporaryView("xml3_view", xml3Table);
    tableEnv.createTemporaryView("xml4_view", xml4Table);
    tableEnv.createTemporaryView("xml5_view", xml5Table);
    tableEnv.createTemporaryView("xml7_view", xml7Table);
    tableEnv.createTemporaryView("xml8_view", xml8Table);
    tableEnv.createTemporaryView("xml9_view", xml9Table);
    tableEnv.createTemporaryView("xml10_view", xml10Table);
    tableEnv.createTemporaryView("xml11_view", xml11Table);
    tableEnv.createTemporaryView("xml12_view", xml12Table);
    tableEnv.createTemporaryView("xml13_view", xml13Table);
    tableEnv.createTemporaryView("xml14_view", xml14Table);
    tableEnv.createTemporaryView("xml15_view", xml15Table);

    StatementSet stmt = tableEnv.createStatementSet();
    App.flinkQuery(stmt);
    stmt.execute();

  }

  public static void flinkQuery(StatementSet stmt){
    stmt.addInsertSql("INSERT INTO db_3179.admision_checkin SELECT * FROM xml1_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_med SELECT * FROM xml2_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_equipment SELECT * FROM xml3_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_subclinical SELECT * FROM xml4_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_clinical SELECT * FROM xml5_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_discharge SELECT * FROM xml7_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_medical_record SELECT * FROM xml8_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_birth_certificate SELECT * FROM xml9_view");
    stmt.addInsertSql("INSERT INTO db_3179.admission_maternity_leave SELECT * FROM xml10_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_benefit_leave SELECT * FROM xml11_view");
    stmt.addInsertSql("INSERT INTO db_3179.admission_medical_exam SELECT * FROM xml12_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_referral SELECT * FROM xml13_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_appointment SELECT * FROM xml14_view");
    stmt.addInsertSql("INSERT INTO db_3179.admission_tuberculosis SELECT * FROM xml15_view");
  }


}
