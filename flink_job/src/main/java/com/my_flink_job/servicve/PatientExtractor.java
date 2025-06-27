package com.my_flink_job.servicve;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.FileHoSo;
import com.my_flink_job.dtos.GiamDinhHs;
import com.my_flink_job.dtos.Xml1;
import com.my_flink_job.dtos.Xml8;
import com.my_flink_job.dtos.iceberg.Admision_Medical_Record;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import java.util.List;

public class PatientExtractor extends RichFlatMapFunction<GiamDinhHs, Patient> {

    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(PatientExtractor.class);
    private String id;

    private transient Connection connection;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = DriverManager.getConnection(
                "jdbc:clickhouse://10.6.8.29:8123/default", "default", "Gtel@123"
        );
    }

    public PatientExtractor(String uuid){
        this.id = uuid;
    }

    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<Patient> collector) throws Exception {
        logger1.info("Start ------------------> Patient");
        Patient patient = null;
        Xml1 xml1 = null;
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {
            if ("XMl1".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                String base64 = hoSo.getNoiDungFile();
                byte[] decodedBytes = Base64.getDecoder().decode(base64);
                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
                xml1 = xmlMapper.readValue(innerXml, Xml1.class);

                String soCccd = xml1.getSoCccd();
                String dienThoai = xml1.getDienThoai();

                // 1. Kiểm tra tồn tại
                String query = "SELECT uuid FROM iceberg.patient WHERE soCccd = ? AND dienThoai = ? LIMIT 1";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, soCccd);
                    stmt.setString(2, dienThoai);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        logger1.info("Đã tồn tại Patient" );
                        collector.collect(null);
                    }else {
                        logger1.info("Chưa tồn tại Patient" );
                        if (xml1 != null && xml1.getMaLk() != null) {
                            patient = Patient.builder()
                                    .uuid(id)
                                    .stt(xml1.getStt())
                                    .diaChi(xml1.getDiaChi())
                                    .dienThoai(xml1.getDienThoai())
                                    .gioiTinh(xml1.getGioiTinh())
                                    .hoTen(xml1.getHoTen())
                                    .hoTenCha(null)
                                    .hoTenMe(null)
                                    .maDanToc(xml1.getMaDanToc())
                                    .maNgheNghiep(xml1.getMaNgheNghiep())
                                    .maQuocTich(xml1.getMaQuocTich())
                                    .maHuyenCuTru(xml1.getMaHuyenCuTru())
                                    .maTinhCuTru(xml1.getMaTinhCuTru())
                                    .maXaCuTru(xml1.getMaXaCuTru())
                                    .ngaySinh(xml1.getNgaySinh())
                                    .nhomMau(xml1.getNhomMau())
                                    .soCccd(xml1.getSoCccd())
                                    .build();

                            logger1.info("Data patient ------------------> {}", patient);
                            collector.collect(patient);
                        }
                    }
                }


            }
        }
    }
    @Override
    public void close() throws Exception {
        if (connection != null) connection.close();
        super.close();
    }
}
