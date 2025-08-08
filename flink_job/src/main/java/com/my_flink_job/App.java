package com.my_flink_job;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.*;
import com.my_flink_job.dtos.iceberg.*;
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

  private static final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

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
    tableEnv.executeSql(TableDtos.processed_files);

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
    DataStream<FileContentDto> rawXmlString = text.flatMap(new MinioXmlFetcher());
    DataStream<FileContentGdDto> fullXmlDataStream = rawXmlString.flatMap(new FullXmlParser());

    //Comvert Data theo XML
    DataStream<Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
            List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
            AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
            AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles>>  combinedStream;
            combinedStream = fullXmlDataStream.flatMap(new PatientCheckinExtractor());

    DataStream<Patient> patientDataStream = combinedStream.map(t -> t.f0).filter(Objects::nonNull);
    DataStream<AdmissionCheckin> admissionCheckinDataStream = combinedStream.map(t -> t.f1);
    DataStream<Admision_Medical_Record> admisionGmedicalRecordDataStream = combinedStream.map(t -> t.f2);
    DataStream<AdmisionMed> admisionMedDataStream = combinedStream
            .flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t, Collector<AdmisionMed> out) -> {
              if (t.f3 != null) {
                for (AdmisionMed med : t.f3) {
                  out.collect(med);
                }
              }
            })
            .returns(AdmisionMed.class);
    DataStream<AdmisionEquipment> admisionEquipmentDataStream = combinedStream
            .flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<AdmisionEquipment> out2) -> {
              if (t2.f4 != null) {
                for (AdmisionEquipment med : t2.f4) {
                  out2.collect(med);
                }
              }
            })
            .returns(AdmisionEquipment.class);

    DataStream<AdmisionSubclinical> admisionSubclinicalDataStream = combinedStream
            .flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<AdmisionSubclinical> out2) -> {
              if (t2.f5 != null) {
                for (AdmisionSubclinical med : t2.f5) {
                  out2.collect(med);
                }
              }
            })
            .returns(AdmisionSubclinical.class);

    DataStream<AdmisionClinical> admisionClinicalDataStream = combinedStream
            .flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<AdmisionClinical> out2) -> {
              if (t2.f6 != null) {
                for (AdmisionClinical med : t2.f6) {
                  out2.collect(med);
                }
              }
            })
            .returns(AdmisionClinical.class);

    DataStream<AdmisionDischarge> admisionDischargeDataStream = combinedStream
            .flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<AdmisionDischarge> out2) -> {
      if (t2.f7 != null) {
        out2.collect(t2.f7);
      }
    })
            .returns(AdmisionDischarge.class);


    DataStream<AdmisionBirthCertificate> admisionBirthCertificateDataStream = combinedStream
            .flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<AdmisionBirthCertificate> out2) -> {
              if (t2.f6 != null) {
                for (AdmisionBirthCertificate med : t2.f8) {
                  out2.collect(med);
                }
              }
            })
            .returns(AdmisionBirthCertificate.class);


    DataStream<AdmissionMaternityLeave> admissionMaternityLeaveDataStream = combinedStream.flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<AdmissionMaternityLeave> out2) -> {
      if (t2.f9 != null) {
          out2.collect(t2.f9);
      }
    })
            .returns(AdmissionMaternityLeave.class);


    DataStream<AdmissionBenefitLeave> admisionBenefitLeaveDataStream = combinedStream.flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<AdmissionBenefitLeave> out2) -> {
              if (t2.f10 != null) {
                out2.collect(t2.f10);
              }
            })
            .returns(AdmissionBenefitLeave.class);
    DataStream<AdmissionMedicalExam> admissionMedicalExamDataStream = combinedStream.flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<AdmissionMedicalExam> out2) -> {
              if (t2.f11 != null) {
                out2.collect(t2.f11);
              }
            })
            .returns(AdmissionMedicalExam.class);


    DataStream<AdmisionReferral> admisionReferralDataStream = combinedStream.flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<AdmisionReferral> out2) -> {
              if (t2.f12 != null) {
                out2.collect(t2.f12);
              }
            })
            .returns(AdmisionReferral.class);


    DataStream<AdmissionAppointment> admisionAppointmentDataStream =combinedStream.flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<AdmissionAppointment> out2) -> {
              if (t2.f13 != null) {
                out2.collect(t2.f13);
              }
            })
            .returns(AdmissionAppointment.class);


    DataStream<AdmissionTuberculosis> chiTietDieuTriBenhLaoDataStream = combinedStream
            .flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<AdmissionTuberculosis> out2) -> {
              if (t2.f14 != null) {
                for (AdmissionTuberculosis med : t2.f14) {
                  out2.collect(med);
                }
              }
            })
            .returns(AdmissionTuberculosis.class);

    DataStream<ProcessFiles> processFilesDataStream =combinedStream.flatMap((Tuple16<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
                    List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
                    AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
                    AdmissionAppointment, List<AdmissionTuberculosis>, ProcessFiles> t2, Collector<ProcessFiles> out2) -> {
              if (t2.f15 != null) {
                out2.collect(t2.f15);
              }
            })
            .returns(ProcessFiles.class);


    //Khởi tạo dữ liệu
    Table admissionCheckinTable = tableEnv.fromDataStream(
            admissionCheckinDataStream,$("id"), $("maBn"), $("maLoaiRv"), $("ketQuaDtri"),$("maLk"), $("lyDoVv"), $("lyDoVnt")
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
            admisionGmedicalRecordDataStream, $("uuid"), $("stt"), $("chanDoanRv"), $("chanDoanVao"), $("donVi"), $("duPhong"), $("ghiChu"), $("ketQuaDt")
            , $("maBenhChinh"), $("maBenhKt"), $("maBenhYhct"), $("maLoaiKcb"), $("maLoaiRv"), $("maPtttQt"), $("maTtdv"), $("namQt")
            , $("ngayTaiKham"), $("ngayTtoan"), $("nguoiGiamHo"), $("ppDieuTri"), $("qtBenhLy"), $("soNgayDt"), $("tBhtt")
            , $("tBhttGdv"), $("tBncct"), $("tBntt"), $("tNguonKhac"), $("tThuoc"), $("tTongChiBh")
            , $("tTongChiBv"), $("tVtyt"), $("tomTatKq"), $("thangQt"), $("createdAt"), $("updatedAt"), $("admision_checkin_uuid")
    );

    Table admisionMedTable = tableEnv.fromDataStream(
            admisionMedDataStream, $("uuid"), $("createdAt"), $("updatedAt"), $("stt"), $("maThuoc"), $("maPpCheBien"), $("maCskcbThuoc"), $("maNhom")
            , $("tenThuoc"), $("donViTinh"), $("hamLuong"), $("duongDung"), $("dangBaoChe"), $("lieuDung"), $("cachDung")
            , $("soDangKy"), $("ttThau"), $("phamVi"), $("tyleTtBh"), $("soLuong"), $("donGia"), $("thanhTienBv")
            , $("thanhTienBh"), $("tNguonKhacNsnn"), $("tNguonKhacVtnn"), $("tNguonKhacVttn"), $("tNguonKhacCl"), $("tNguonKhac"), $("mucHuong")
            , $("tBntt"), $("tBncct"), $("tBhtt"), $("maKhoa"), $("maBacSi"), $("maDichVu"), $("ngayYl")
            , $("ngayThYl"), $("maPttt"), $("nguonCtra"), $("vetThuongTp"), $("duPhong"), $("admision_checkin_uuid"), $("maLk")
    );

    Table admisionEquipmentTable = tableEnv.fromDataStream(
            admisionEquipmentDataStream, $("uuid"),
            $("createdAt"), $("updatedAt"),
            $("donGiaBh"), $("donGiaBv"), $("donViTinh"), $("duPhong"), $("goiVtyt"), $("maBacSi"),
            $("maBenh"), $("maBenhYhct"), $("maDichVu"), $("maGiuong"), $("maHieuSp"), $("maKhoa"),
            $("maMay"), $("maNhom"), $("maPttt"), $("maPtttQt"), $("maVatTu"), $("maXangDau"),
            $("mucHuong"), $("ngayKq"), $("ngayThYl"), $("ngayYl"), $("nguoiThucHien"),
            $("phamVi"), $("ppVoCam"), $("soLuong"), $("stt"), $("tBhtt"), $("tBncct"),
            $("tBntt"), $("tNguonKhac"), $("tNguonKhacCl"), $("tNguonKhacNsnn"),
            $("tNguonKhacVtnn"), $("tNguonKhacVttn"), $("tTrantt"), $("taiSuDung"),
            $("tenDichVu"), $("tenVatTu"), $("thanhTienBh"), $("thanhTienBv"),
            $("ttThau"), $("tyleTtBh"), $("tyleTtDv"), $("vetThuongTp"),
            $("viTriThDvkt"), $("admision_checkin_uuid")
    );
    Table admisionSubclinicalTable = tableEnv.fromDataStream(
            admisionSubclinicalDataStream,$("uuid"), $("stt"), $("maDichVu"), $("maChiSo"), $("tenChiSo"),
            $("giaTri"), $("donViDo"), $("moTa"), $("ketLuan"), $("ngayKq"), $("maBsDocKq"),
            $("duPhong"), $("admision_checkin_uuid"), $("createdAt"), $("updatedAt")
    );
    Table admisionClinicalTable = tableEnv.fromDataStream(
            admisionClinicalDataStream,
            $("uuid"),
            $("maLk"),
            $("stt"),
            $("dienBienLs"),
            $("giaiDoanBenh"),
            $("hoiChan"),
            $("phauThuat"),
            $("thoiDiemDbls"),
            $("nguoiThucHien"),
            $("duPhong"),
            $("admision_checkin_uuid"),
            $("createdAt"), $("updatedAt")
    );
    Table admisionDischargeTable = tableEnv.fromDataStream(
            admisionDischargeDataStream,
            $("uuid"), $("soLuuTru"), $("maYte"), $("maKhoaRv"), $("ngayVao"),
            $("ngayRa"), $("maDinhChiThai"), $("nguyennhanDinhchi"), $("thoigianDinhchi"),
            $("tuoiThai"), $("chanDoanRv"), $("ppDieutri"), $("ghiChu"), $("maTtdv"),
            $("maBs"), $("tenBs"), $("ngayCt"), $("maCha"), $("maMe"), $("maTheTam"),
            $("hoTenCha"), $("hoTenMe"), $("soNgayNghi"), $("ngoaitruTungay"), $("ngoaitruDenngay"),
            $("duPhong"), $("admision_checkin_uuid"), $("createdAt"), $("updatedAt")
    );


    Table admisionBirthCertificateTable = tableEnv.fromDataStream(
            admisionBirthCertificateDataStream,
            $("uuid"), $("createdAt"), $("createdBy"), $("updatedAt"), $("canNangCon"), $("duPhong"),
            $("ghiChu"), $("gioiTinhCon"), $("hoTenCha"), $("hoTenCon"), $("hoTenNnd"), $("lanSinh"),
            $("maBhxhNnd"), $("maDantocNnd"), $("maQuoctich"), $("maTheNnd"), $("maTheTam"), $("maTtdv"),
            $("mahuyenCuTru"), $("matinhCuTru"), $("maxaCuTru"), $("ngayCt"), $("ngaySinhCon"), $("ngaycapCccdNnd"),
            $("ngaysinhNnd"), $("nguoiDoDe"), $("nguoiGhiPhieu"), $("noiCuTruNnd"), $("noiSinhCon"),
            $("noicapCccdNnd"), $("quyenSo"), $("sinhconDuoi32tuan"), $("sinhconPhauthuat"), $("so"),
            $("soCccdNnd"), $("soCon"), $("soConSong"), $("stt"), $("tinhTrangCon"), $("admision_checkin_uuid")
    );


    Table admissionMaternityLeaveTable = tableEnv.fromDataStream(
            admissionMaternityLeaveDataStream,
            $("uuid"), $("createdAt"), $("createdBy"), $("updatedAt"), $("maLk"), $("soSeri"),
            $("soCt"), $("soNgay"), $("donVi"), $("chanDoanRv"), $("tuNgay"), $("denNgay"),
            $("maTtdv"), $("tenBs"), $("maBs"), $("ngayCt"), $("duPhong"), $("admision_checkin_uuid")
    );


    Table admisionBenefitLeaveTable = tableEnv.fromDataStream(
            admisionBenefitLeaveDataStream,
            $("uuid"), $("createdAt"), $("createdBy"), $("updatedAt"), $("chanDoanRv"), $("denNgay"),
            $("donVi"), $("duPhong"), $("hoTenCha"), $("hoTenMe"), $("maBhxh"), $("maBs"),
            $("maDinhChiThai"), $("maTheBhyt"), $("maTheTam"), $("maTtdv"), $("mauSo"),
            $("ngayCt"), $("nguyennhanDinhchi"), $("ppDieutri"), $("soCt"), $("soKcb"),
            $("soNgayNghi"), $("soSeri"), $("stt"), $("tuNgay"), $("tuoiThai"), $("admision_checkin_uuid")
    );


    Table admissionMedicalExamTable = tableEnv.fromDataStream(
            admissionMedicalExamDataStream,
            $("uuid"), $("createdAt"), $("createdBy"), $("updatedAt"), $("nguoiChuTri"), $("chucVu"),
            $("ngayHop"), $("hoTen"), $("ngaySinh"), $("soCccd"), $("ngayCapCccd"), $("noiCapCccd"),
            $("diaChi"), $("maTinhCuTru"), $("maHuyenCuTru"), $("maXaCuTru"), $("maBhxh"), $("maTheBhyt"),
            $("ngheNghiep"), $("dienThoai"), $("maDoiTuong"), $("khamGiamDinh"), $("soBienBan"),
            $("tyleTtctCu"), $("dangHuongCheDo"), $("ngayChungTu"), $("soGiayGioiThieu"), $("ngayDeNghi"),
            $("maDonVi"), $("gioiThieuCua"), $("ketQuaKham"), $("soVanBanCanCu"), $("tyleTtctMoi"),
            $("tongTyleTtct"), $("dangKhuyettat"), $("mucDoKhuyettat"), $("deNghi"), $("duocXacDinh"),
            $("duPhong"), $("admision_checkin_uuid")
    );



    Table admisionReferralTable = tableEnv.fromDataStream(
            admisionReferralDataStream,
            $("uuid"), $("createdAt"), $("createdBy"), $("updatedAt"), $("chanDoanRv"), $("chucdanhNguoiHt"), $("dauHieuLs"),
            $("diaChi"), $("duPhong"), $("giayChuyenTuyen"), $("gioiTinh"), $("gtTheDen"), $("hoTen"), $("hotenNguoiHt"),
            $("huongDieuTri"), $("maBacSi"), $("maBenhChinh"), $("maBenhKt"), $("maBenhYhct"), $("maCskcb"), $("maDantoc"),
            $("maLoaiRv"), $("maLydoCt"), $("maNgheNghiep"), $("maNoiDen"), $("maNoiDi"), $("maQuoctich"), $("maTheBhyt"),
            $("maTtdv"), $("ngayRa"), $("ngaySinh"), $("ngayVao"), $("ngayVaoNoiTru"), $("phuongtienVc"), $("ppDieuTri"),
            $("qtBenhly"), $("soChuyentuyen"), $("soHoso"), $("stt"), $("tomtatKq"), $("admision_checkin_uuid")
    );


    Table admisionAppointmentTable = tableEnv.fromDataStream(
            admisionAppointmentDataStream,
            $("uuid"), $("createdAt"), $("createdBy"), $("updatedAt"),
            $("chanDoanRv"), $("diaChi"), $("duPhong"), $("gioiTinh"), $("gtTheDen"),
            $("hoTen"), $("maBacSi"), $("maBenhChinh"), $("maBenhKt"), $("maBenhYhct"),
            $("maCskcb"), $("maDoiTuongKcb"), $("maTheBhyt"), $("maTtdv"),
            $("ngayCt"), $("ngayHenKl"), $("ngayRa"), $("ngaySinh"),
            $("ngayVao"), $("ngayVaoNoiTru"), $("soGiayHenKl"),
            $("stt"), $("admision_checkin_uuid")
    );


    Table admissionTuberculosisTable = tableEnv.fromDataStream(
            chiTietDieuTriBenhLaoDataStream,
            $("uuid"), $("maLk"), $("createdAt"), $("createdBy"), $("updatedAt"), $("stt"),
            $("maBn"), $("hoTen"), $("soCccd"),
            $("phanLoaiLaoViTri"), $("phanLoaiLaoTs"), $("phanLoaiLaoHiv"), $("phanLoaiLaoVk"), $("phanLoaiLaoKt"),
            $("loaiDtriLao"), $("ngayBdDtriLao"), $("phacDoDtriLao"), $("ngayKtDtriLao"), $("ketQuaDtriLao"),
            $("maCskcb"), $("ngayKdHiv"), $("bddtArv"), $("ngayBatDauDtCtx"),
            $("duPhong"), $("admision_checkin_uuid")
    );

    Table processFile = tableEnv.fromDataStream(
            processFilesDataStream,
            $("uuid"), $("file_name"), $("unit_name"), $("directory"), $("date_of_receipt_of_file"), $("processed_at"),
            $("etl_status"), $("gmed_status"), $("ma_lk"),
            $("created_at"), $("updated_at")
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
    tableEnv.createTemporaryView("process_file_view", processFile);

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
    stmt.addInsertSql("INSERT INTO db_3179.processed_files SELECT * FROM process_file_view");
  }


}
