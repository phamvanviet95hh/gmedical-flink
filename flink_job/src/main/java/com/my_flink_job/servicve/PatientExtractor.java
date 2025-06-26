package com.my_flink_job.servicve;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.FileHoSo;
import com.my_flink_job.dtos.GiamDinhHs;
import com.my_flink_job.dtos.Xml1;
import com.my_flink_job.dtos.Xml8;
import com.my_flink_job.dtos.iceberg.Admision_Medical_Record;
import com.my_flink_job.dtos.iceberg.Patient;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class PatientExtractor implements FlatMapFunction<GiamDinhHs, Patient> {

    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(PatientExtractor.class);

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
                if (xml1 != null && xml1.getMaLk() != null) {
                    patient = Patient.builder()
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
