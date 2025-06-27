package com.my_flink_job.servicve;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.*;
import com.my_flink_job.dtos.iceberg.AdmissionCheckin;
import com.my_flink_job.dtos.iceberg.Patient;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.sql.PreparedStatement;
import java.util.UUID;

public class Xml1Extractor extends RichFlatMapFunction<GiamDinhHs, AdmissionCheckin> {
    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml1Extractor.class);

    AdmissionCheckin admissionCheckin =null;



    private String id;

    public Xml1Extractor(String uuid){
        this.id = uuid;
    }

    private transient Connection connection;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = DriverManager.getConnection(
                "jdbc:clickhouse://10.6.8.29:8123/default", "default", "Gtel@123"
        );
    }

    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<AdmissionCheckin> collector) throws Exception {
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {
            if (!"XML1".equalsIgnoreCase(hoSo.getLoaiHoSo())) continue;

            String innerXml = new String(Base64.getDecoder().decode(hoSo.getNoiDungFile()), StandardCharsets.UTF_8);
            Xml1 xml1 = xmlMapper.readValue(innerXml, Xml1.class);

            String soCccd = xml1.getSoCccd();
            String dienThoai = xml1.getDienThoai();
            String patientUuid = null;


            // 1. Kiểm tra tồn tại
            String query = "SELECT uuid FROM iceberg.patient WHERE soCccd = ? AND dienThoai = ? LIMIT 1";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, soCccd);
                stmt.setString(2, dienThoai);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    logger1.info("Đã tồn tại Patient" );
                    patientUuid = rs.getString("uuid");
                }else {
                    logger1.info("Chưa tồn tại Patient" );
                }
            }
            logger1.info("checkin : {}", admissionCheckin);
            admissionCheckin = AdmissionCheckin.builder()
                    .id(id)
                    .maBn(xml1.getMaBn())
                    .canNang(xml1.getCanNang())
                    .maTheBhyt(xml1.getMaTheBhyt())
                    .namNamLienTuc(xml1.getNamNamLienTuc())
                    .maDkbd(xml1.getMaDkbd())
                    .gtTheTu(xml1.getGtTheTu())
                    .gtTheDen(xml1.getGtTheDen())
                    .ngayMienCct(xml1.getNgayMienCct())
                    .lyDoVv(xml1.getLyDoVv())
                    .lyDoVnt(xml1.getLyDoVnt())
                    .maLyDoVnt(xml1.getMaLyDoVnt())
                    .maDoituongKcb(xml1.getMaDoiTuongKcb())
                    .maNoiDi(xml1.getMaNoiDi())
                    .maNoiDen(xml1.getMaNoiDen())
                    .maTaiNan(xml1.getMaTaiNan())
                    .ngayVao(xml1.getNgayVao())
                    .ngayVaoNoiTru(xml1.getNgayVaoNoiTru())
                    .ngayRa(xml1.getNgayRa())
                    .maHsba(xml1.getMaHsba())
                    .duPhong(xml1.getDuPhong())
                    .maLk(xml1.getMaLk())
                    .maLoaiRv(xml1.getMaLoaiRv())
                    .ketQuaDtri(xml1.getKetQuaDt())
                    .createdAt(LocalDateTime.now().toString())
                    .updatedAt(LocalDateTime.now().toString())
                    .createdBy(null)
                    .updatedBy(null)
                    .stt(xml1.getStt())
                    .maCskb(xml1.getMaCskcb())
                    .patient_id(patientUuid == null ? id : patientUuid)
                    .build();
            logger1.info("checkin : {}", admissionCheckin);
            collector.collect(admissionCheckin);
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) connection.close();
        super.close();
    }

//    @Override
//    public void flatMap(GiamDinhHs giamDinhHs, Collector<AdmissionCheckin> collector) throws Exception {
//        logger1.info("Start XMl1");
//        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
//        for (FileHoSo hoSo : hoSoList) {
//            if ("XML1".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
//                String base64Xml1 = hoSo.getNoiDungFile();
//                byte[] decodedBytes = Base64.getDecoder().decode(base64Xml1);
//                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
//                Xml1 xml1 = xmlMapper.readValue(innerXml, Xml1.class);
//                if (xml1 != null || xml1.getMaLk() != null){
//                    admissionCheckin = AdmissionCheckin.builder()
//                            .id(id)
//                            .maBn(xml1.getMaBn())
//                            .canNang(xml1.getCanNang())
//                            .maTheBhyt(xml1.getMaTheBhyt())
//                            .namNamLienTuc(xml1.getNamNamLienTuc())
//                            .maDkbd(xml1.getMaDkbd())
//                            .gtTheTu(xml1.getGtTheTu())
//                            .gtTheDen(xml1.getGtTheDen())
//                            .ngayMienCct(xml1.getNgayMienCct())
//                            .lyDoVv(xml1.getLyDoVv())
//                            .lyDoVnt(xml1.getLyDoVnt())
//                            .maLyDoVnt(xml1.getMaLyDoVnt())
//                            .maDoituongKcb(xml1.getMaDoiTuongKcb())
//                            .maNoiDi(xml1.getMaNoiDi())
//                            .maNoiDen(xml1.getMaNoiDen())
//                            .maTaiNan(xml1.getMaTaiNan())
//                            .ngayVao(xml1.getNgayVao())
//                            .ngayVaoNoiTru(xml1.getNgayVaoNoiTru())
//                            .ngayRa(xml1.getNgayRa())
//                            .maHsba(xml1.getMaHsba())
//                            .duPhong(xml1.getDuPhong())
//                            .maLk(xml1.getMaLk())
//                            .maLoaiRv(xml1.getMaLoaiRv())
//                            .ketQuaDtri(xml1.getKetQuaDt())
//                            .createdAt(LocalDateTime.now().toString())
//                            .updatedAt(LocalDateTime.now().toString())
//                            .createdBy(null)
//                            .updatedBy(null)
//                            .stt(xml1.getStt())
//                            .maCskb(xml1.getMaCskcb())
//                            .build();
//                }
//                collector.collect(admissionCheckin);
//            }
//        }
//    }
}
