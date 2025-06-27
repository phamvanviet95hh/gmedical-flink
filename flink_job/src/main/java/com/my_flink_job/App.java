package com.my_flink_job;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.*;
import com.my_flink_job.dtos.iceberg.AdmisionMed;
import com.my_flink_job.dtos.iceberg.Admision_Medical_Record;
import com.my_flink_job.dtos.iceberg.AdmissionCheckin;
import com.my_flink_job.dtos.iceberg.Patient;
import com.my_flink_job.dtos.table.TableDtos;
import com.my_flink_job.servicve.*;
import org.apache.flink.api.java.tuple.*;
import org.apache.flink.table.api.*;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.util.*;

import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.CloseableIterator;
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
import org.apache.flink.api.common.typeinfo.TypeHint;

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
    tableEnv.executeSql(TableDtos.patient);

    StatementSet stmt = tableEnv.createStatementSet();

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
    String uuidCheckIn = UUID.randomUUID().toString();
    logger1.info("uuidCheckIn: -----------------------------------------------------------------------> {}",uuidCheckIn);
    // map Kafka message (đường dẫn) -> FullXmlData object (chứa nhiều list con)
    DataStream<String> rawXmlString = text.flatMap(new MinioXmlFetcher());
    DataStream<GiamDinhHs> fullXmlDataStream = rawXmlString.flatMap(new FullXmlParser());

    //Comvert Data theo XML
    DataStream<Tuple4<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>>> combinedStream = fullXmlDataStream.flatMap(new PatientCheckinExtractor());

    DataStream<Patient> patientDataStream = combinedStream.map(t -> t.f0).filter(Objects::nonNull);
    DataStream<AdmissionCheckin> admissionCheckinDataStream = combinedStream.map(t -> t.f1);
    DataStream<Admision_Medical_Record> admisionGmedicalRecordDataStream = combinedStream.map(t -> t.f2);
    DataStream<AdmisionMed> admisionMedDataStream = combinedStream
            .flatMap((Tuple4<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>> t, Collector<AdmisionMed> out) -> {
              if (t.f3 != null) {
                for (AdmisionMed med : t.f3) {
                  out.collect(med);
                }
              }
            })
            .returns(AdmisionMed.class);

//    DataStream<Patient> patientDataStream = fullXmlDataStream.flatMap(new PatientExtractor(uuidCheckIn));
//    DataStream<AdmissionCheckin> admissionCheckinDataStream = fullXmlDataStream.flatMap(new Xml1Extractor(uuidCheckIn));

//     DataStream<Admision_Medical_Record> admisionGmedicalRecordDataStream = fullXmlDataStream.flatMap(new Xml8Extractor());


//    DataStream<ChiTietThuoc> admisionMedDataStream = fullXmlDataStream.flatMap(new Xml2Extractor());

    DataStream<ChiTietDvkt> chiTietDvktStream = fullXmlDataStream.flatMap(new Xml3Extractor());
    DataStream<ChiTietCls> chiTietClsDataStream = fullXmlDataStream.flatMap(new Xml4Extractor());
    DataStream<ChiTietDienBienBenh> admisionClinicalDataStream = fullXmlDataStream.flatMap(new Xml5Extractor());
    DataStream<Xml7> admisionDischargeDataStream = fullXmlDataStream.flatMap(new Xml7Extractor());

    DataStream<DulieuGiayChungSinh> admisionBirthCertificateDataStream = fullXmlDataStream.flatMap(new Xml9Extractor());
    DataStream<Xml10> admissionMaternityLeaveDataStream = fullXmlDataStream.flatMap(new Xml10Extractor());
    DataStream<Xml11> admisionBenefitLeaveDataStream = fullXmlDataStream.flatMap(new Xml11Extractor());
    DataStream<Xml12> admissionMedicalExamDataStream = fullXmlDataStream.flatMap(new Xml12Extractor());
    DataStream<Xml13> admisionReferralDataStream = fullXmlDataStream.flatMap(new Xml13Extractor());
    DataStream<Xml14> admisionAppointmentDataStream = fullXmlDataStream.flatMap(new Xml14Extractor());
    DataStream<ChiTietDieuTriBenhLao> chiTietDieuTriBenhLaoDataStream = fullXmlDataStream.flatMap(new Xml15Extractor());

    //Khởi tạo dữ liệu
    Table admissionCheckinTable = tableEnv.fromDataStream(
            admissionCheckinDataStream,$("id"), $("maLk"), $("maBn"), $("maLoaiRv"), $("ketQuaDtri"), $("lyDoVv"), $("lyDoVnt")
            , $("canNang"), $("gtTheTu"), $("gtTheDen"), $("duPhong"), $("maNoiDen"), $("maNoiDi")
            , $("ngayVao"), $("ngayRa"), $("maTheBhyt"), $("maLyDoVnt"), $("maHsba"), $("ngayVaoNoiTru")
            , $("stt"), $("maCskb"), $("maTaiNan"), $("namNamLienTuc"), $("maDkbd"), $("ngayMienCct")
            , $("maDoituongKcb"), $("createdAt"), $("updatedAt"), $("createdBy"), $("updatedBy"), $("patient_id")// đổi tên trường cho phù hợp với bảng Iceberg
    );
    if (patientDataStream != null){
      Table patientTable = tableEnv.fromDataStream(
              patientDataStream, $("uuid"), $("diaChi"), $("dienThoai"), $("gioiTinh"), $("hoTen"), $("hoTenCha")
              , $("hoTenMe"), $("maDanToc"), $("maNgheNghiep"), $("maQuocTich"), $("maHuyenCuTru"), $("maTinhCuTru")
              , $("maXaCuTru"), $("ngaySinh"), $("nhomMau"), $("soCccd"), $("stt"), $("createdAt"), $("updatedAt")// đổi tên trường cho phù hợp với bảng Iceberg
      );
      tableEnv.createTemporaryView("patient_view", patientTable);
      stmt.addInsertSql("INSERT INTO db_3179.patient SELECT * FROM patient_view");
    }


    Table admisionGmedicalRecordTable = tableEnv.fromDataStream(
            admisionGmedicalRecordDataStream, $("stt"), $("chanDoanRv"), $("chanDoanVao"), $("donVi"), $("duPhong"), $("ghiChu"), $("ketQuaDt")
            , $("maBenhChinh"), $("maBenhKt"), $("maBenhYhct"), $("maLoaiKcb"), $("maLoaiRv"), $("maPtttQt"), $("maTtdv"), $("namQt")
            , $("ngayTaiKham"), $("ngayTtoan"), $("nguoiGiamHo"), $("ppDieuTri"), $("qtBenhLy"), $("soNgayDt"), $("tBhtt")
            , $("tBhttGdv"), $("tBncct"), $("tBntt"), $("tNguonKhac"), $("tThuoc"), $("tTongChiBh")
            , $("tTongChiBv"), $("tVtyt"), $("tomTatKq"), $("thangQt"), $("createdAt"), $("updatedAt"), $("admision_checkin_uuid")
    );

    Table admisionMedTable = tableEnv.fromDataStream(
            admisionMedDataStream, $("createdAt"), $("updatedAt"), $("stt"), $("maThuoc"), $("maPpCheBien"), $("maCskcbThuoc"), $("maNhom")
            , $("tenThuoc"), $("donViTinh"), $("hamLuong"), $("duongDung"), $("dangBaoChe"), $("lieuDung"), $("cachDung")
            , $("soDangKy"), $("ttThau"), $("phamVi"), $("tyleTtBh"), $("soLuong"), $("donGia"), $("thanhTienBv")
            , $("thanhTienBh"), $("tNguonKhacNsnn"), $("tNguonKhacVtnn"), $("tNguonKhacVttn"), $("tNguonKhacCl"), $("tNguonKhac"), $("mucHuong")
            , $("tBntt"), $("tBncct"), $("tBhtt"), $("maKhoa"), $("maBacSi"), $("maDichVu"), $("ngayYl")
            , $("ngayThYl"), $("maPttt"), $("nguonCtra"), $("vetThuongTp"), $("duPhong"), $("admision_checkin_uuid"), $("maLk")
    );

    Table admisionEquipmentTable = tableEnv.fromDataStream(
            chiTietDvktStream, $("maLk"), $("stt"), $("maDichVu"), $("maPtttQt"), $("maVatTu"), $("maNhom"), $("goiVtyt")
            , $("tenVatTu"), $("tenDichVu"), $("maXangDau"), $("donViTinh"), $("phamVi"), $("soLuong"), $("donGiaBv"), $("donGiaBh"), $("ttThau"), $("tyleTtDv")
            , $("tyleTtBh"), $("thanhTienBv"), $("thanhTienBh"), $("tTrantt"), $("mucHuong"), $("tNguonKhacNsnn"), $("tNguonKhacVtnn"), $("tNguonKhacVttn"), $("tNguonKhacCl")
            , $("tNguonKhac"), $("tBntt"), $("tBncct"), $("tBhtt"), $("maKhoa"), $("maGiuong"), $("maBacSi"), $("nguoiThucHien"), $("maBenh"), $("maBenhYhct")
            , $("ngayYl"), $("ngayThYl"), $("ngayKq"), $("maPttt"), $("vetThuongTp"), $("ppVoCam"), $("viTriThDvkt"), $("maMay"), $("maHieuSp")
            , $("taiSuDung"), $("duPhong")
    );
    Table admisionSubclinicalTable = tableEnv.fromDataStream(
            chiTietClsDataStream, $("maLk"), $("stt"), $("maDichVu"), $("maChiSo"), $("tenChiSo"), $("giaTri"), $("donViDo")
            , $("moTa"), $("ketLuan"), $("ngayKq"), $("maBsDocKq"), $("duPhong")

    );
    Table admisionClinicalTable = tableEnv.fromDataStream(
            admisionClinicalDataStream, $("maLk"), $("stt"), $("dienBienLs"), $("giaiDoanBenh"), $("hoiChan"), $("phauThuat")
            , $("thoiDiemDbls"), $("nguoiThucHien"), $("duPhong")

    );
    Table admisionDischargeTable = tableEnv.fromDataStream(
            admisionDischargeDataStream, $("maLk"), $("soLuuTru"), $("maYte"), $("maKhoaRv"), $("ngayVao"), $("ngayRa")
            , $("maDinhChiThai"), $("nguyenNhanDinhChi"), $("thoiGianDinhChi"), $("tuoiThai"), $("chanDoanRv"), $("ppDieuTri"), $("ghiChu")
            , $("maTtdv"), $("maBs"), $("tenBs"), $("ngayCt"), $("maCha"), $("maMe"), $("maTheTam")
            , $("hoTenCha"), $("hoTenMe"), $("soNgayNghi"), $("ngoaiTruTuNgay"), $("ngoaiTruDenNgay"), $("duPhong")
    );

    Table admisionBirthCertificateTable = tableEnv.fromDataStream(
            admisionBirthCertificateDataStream, $("maLk"), $("maBhxhNnd"), $("maTheNnd"), $("hoTenNnd"), $("ngaySinhNnd"), $("maDanTocNnd")
            , $("soCccdNnd"), $("ngayCapCccdNnd"), $("noiCapCccdNnd"), $("noiCuTruNnd"), $("maQuocTich"), $("maTinhCuTru"), $("maHuyenCuTru")
            , $("maXaCuTru"), $("hoTenCha"), $("maTheTam"), $("hoTenCon"), $("gioiTinhCon"), $("soCon"), $("lanSinh"), $("soConSong")
            , $("canNangCon"), $("ngaySinhCon"), $("noiSinhCon"), $("tinhTrangCon"), $("sinhConPhauThuat"), $("sinhConDuoi32Tuan"), $("ghiChu")
            , $("nguoiDoDe"), $("nguoiGhiPhieu"), $("ngayCt"), $("so"), $("quyenSo"), $("maTtdv"), $("duPhong")
    );

    Table admissionMaternityLeaveTable = tableEnv.fromDataStream(
            admissionMaternityLeaveDataStream, $("maLk"), $("soSeri"), $("soCt"), $("soNgay"), $("donVi"), $("chanDoanRv"), $("tuNgay")
            , $("denNgay"), $("maTtdv"), $("tenBs"), $("maBs"), $("ngayCt"), $("duPhong")
    );

    Table admisionBenefitLeaveTable = tableEnv.fromDataStream(
            admisionBenefitLeaveDataStream, $("maLk"), $("soCt"), $("soSeri"), $("soKcb"), $("donVi"), $("maBhxh"), $("maTheBhyt"), $("chanDoanRv"), $("ppDieuTri")
            , $("maDinhChiThai"), $("nguyenNhanDinhChi"), $("tuoiThai"), $("soNgayNghi"), $("tuNgay"), $("denNgay"), $("hoTenCha"), $("hoTenMe"), $("maTtdv")
            , $("maBs"), $("ngayCt"), $("maTheTam"), $("mauSo"), $("duPhong")
    );

    Table admissionMedicalExamTable = tableEnv.fromDataStream(
            admissionMedicalExamDataStream, $("nguoiChuTri"), $("chucVu"), $("ngayHop"), $("hoTen"), $("ngaySinh"), $("soCccd"), $("ngayCapCccd"), $("noiCapCccd"), $("diaChi"), $("maTinhCuTru")
            , $("maHuyenCuTru"), $("maXaCuTru"), $("maBhxh"), $("maTheBhyt"), $("ngheNghiep"), $("dienThoai"), $("maDoiTuong"), $("khamGiamDinh"), $("soBienBan"), $("tyleTtctCu")
            , $("dangHuongCheDo"), $("ngayChungTu"), $("soGiayGioiThieu"), $("ngayDeNghi"), $("maDonVi"), $("gioiThieuCua"), $("ketQuaKham"), $("soVanBanCanCu"), $("tyleTtctMoi"), $("tongTyleTtct"), $("dangKhuyettat")
            , $("mucDoKhuyettat"), $("deNghi"), $("duocXacDinh"), $("duPhong")
    );


    Table admisionReferralTable = tableEnv.fromDataStream(
            admisionReferralDataStream, $("maLk"), $("soHoSo"), $("soChuyenTuyen"), $("giayChuyenTuyen"), $("maCskcb"), $("maNoiDi"), $("maNoiDen"), $("hoTen"), $("ngaySinh")
            , $("gioiTinh"), $("maQuocTich"), $("maDanToc"), $("maNgheNghiep"), $("diaChi"), $("maTheBhyt"), $("gtTheDen"), $("ngayVao"), $("ngayVaoNoiTru")
            , $("ngayRa"), $("dauHieuLs"), $("chanDoanRv"), $("qtBenhLy"), $("tomTatKq"), $("ppDieuTri"), $("maBenhChinh"), $("maBenhKt"), $("maBenhYhct")
            , $("tenDichVu"), $("tenThuoc"), $("ppDieuTriDuplicate"), $("maLoaiRv"), $("maLyDoCt"), $("huongDieuTri"), $("phuongTienVc"), $("hoTenNguoiHt"), $("chucDanhNguoiHt")
            , $("maBacSi"), $("maTtdv"), $("duPhong")
    );

    Table admisionAppointmentTable = tableEnv.fromDataStream(
            admisionAppointmentDataStream, $("maLk"), $("soGiayHenKl"), $("maCskcb"), $("hoTen"), $("ngaySinh"), $("gioiTinh"), $("diaChi"), $("maTheBhyt"), $("gtTheDen")
            , $("ngayVao"), $("ngayVaoNoiTru"), $("ngayRa")
            , $("ngayHenKl"), $("chanDoanRv"), $("maBenhChinh"), $("maBenhKt"), $("maBenhYhct"), $("maDoiTuongKcb")
            , $("maBacSi"), $("maTtdv"), $("ngayCt")
            , $("duPhong")
    );

    Table admissionTuberculosisTable = tableEnv.fromDataStream(
            chiTietDieuTriBenhLaoDataStream, $("maLk"), $("stt"), $("maBn"), $("hoTen"), $("soCccd"), $("phanLoaiLaoViTri"), $("phanLoaiLaoTs")
            , $("phanLoaiLaoHiv"), $("phanLoaiLaoVk"), $("phanLoaiLaoKt"), $("loaiDtriLao"), $("ngayBdDtriLao"), $("phacDoDtriLao"), $("ngayKtDtriLao"), $("ketQuaDtriLao")
            , $("maCskcb"), $("ngayKdHiv"), $("bddtArv"), $("ngayBatDauDtCtx"), $("duPhong")
    );

    // Tạo view tạm cho Table
    tableEnv.createTemporaryView("admision_checkin_view", admissionCheckinTable);
    tableEnv.createTemporaryView("admision_med_view", admisionMedTable);
    tableEnv.createTemporaryView("admision_equipment_view", admisionEquipmentTable);
    tableEnv.createTemporaryView("admision_subclinical_view", admisionSubclinicalTable);
    tableEnv.createTemporaryView("admision_clinical_view", admisionClinicalTable);
    tableEnv.createTemporaryView("admision_discharge_view", admisionDischargeTable);
    tableEnv.createTemporaryView("admision_gmedical_record_view", admisionGmedicalRecordTable);
    tableEnv.createTemporaryView("admision_birth_certificate_view", admisionBirthCertificateTable);
    tableEnv.createTemporaryView("admission_maternity_leave_view", admissionMaternityLeaveTable);
    tableEnv.createTemporaryView("admision_benefit_leave_view", admisionBenefitLeaveTable);
    tableEnv.createTemporaryView("admission_medical_examTable_view", admissionMedicalExamTable);
    tableEnv.createTemporaryView("admision_referral_view", admisionReferralTable);
    tableEnv.createTemporaryView("admision_appointment_view", admisionAppointmentTable);
    tableEnv.createTemporaryView("admission_tuberculosis_view", admissionTuberculosisTable);

    App.flinkQuery(stmt);
    stmt.execute();

  }

  public static void flinkQuery(StatementSet stmt){
    stmt.addInsertSql("INSERT INTO db_3179.admision_checkin SELECT * FROM admision_checkin_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_med SELECT * FROM admision_med_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_equipment SELECT * FROM admision_equipment_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_subclinical SELECT * FROM admision_subclinical_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_clinical SELECT * FROM admision_clinical_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_discharge SELECT * FROM admision_discharge_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_medical_record SELECT * FROM admision_gmedical_record_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_birth_certificate SELECT * FROM admision_birth_certificate_view");
    stmt.addInsertSql("INSERT INTO db_3179.admission_maternity_leave SELECT * FROM admission_maternity_leave_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_benefit_leave SELECT * FROM admision_benefit_leave_view");
    stmt.addInsertSql("INSERT INTO db_3179.admission_medical_exam SELECT * FROM admission_medical_examTable_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_referral SELECT * FROM admision_referral_view");
    stmt.addInsertSql("INSERT INTO db_3179.admision_appointment SELECT * FROM admision_appointment_view");
    stmt.addInsertSql("INSERT INTO db_3179.admission_tuberculosis SELECT * FROM admission_tuberculosis_view");
  }


}
