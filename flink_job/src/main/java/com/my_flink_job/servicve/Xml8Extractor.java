package com.my_flink_job.servicve;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.*;
import com.my_flink_job.dtos.iceberg.Admision_Medical_Record;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class Xml8Extractor implements FlatMapFunction<GiamDinhHs, Admision_Medical_Record> {

    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml8Extractor.class);


    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<Admision_Medical_Record> collector) throws Exception {
        Xml8 xml8 = null;
        Xml1 xml1 = null;
        Admision_Medical_Record admisionMedicalRecord= null;
        logger1.info("Start ------------------> XMl8 + xml1");
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {
            if ("XMl1".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                String base64 = hoSo.getNoiDungFile();
                byte[] decodedBytes = Base64.getDecoder().decode(base64);
                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
                if("XMl8".equalsIgnoreCase(hoSo.getLoaiHoSo())){
                    xml8 = xmlMapper.readValue(innerXml, Xml8.class);
                }
                xml1 = xmlMapper.readValue(innerXml, Xml1.class);
                if (xml1 != null && xml1.getMaLk() != null) {
                    admisionMedicalRecord = Admision_Medical_Record.builder()
                            .stt(xml1.getStt())
                            .chanDoanRv(xml1.getChanDoanRv())
                            .chanDoanVao(xml1.getChanDoanVao())
                            .donVi(xml8 != null ? xml8.getDonVi() : null)
                            .duPhong(xml8 != null ? xml8.getDuPhong() : xml1.getDuPhong())
                            .ghiChu(xml1.getGhiChu())
                            .ketQuaDt(xml1.getKetQuaDt())
                            .maBenhChinh(xml1.getMaBenhChinh())
                            .maBenhKt(xml1.getMaBenhKt())
                            .maBenhYhct(xml1.getMaBenhYhct())
                            .maLoaiKcb(xml1.getMaLoaiKcb())
                            .maLoaiRv(xml1.getMaLoaiRv())
                            .maPtttQt(xml1.getMaPtttQt())
                            .maTtdv(xml1.getMaTtdv())
                            .namQt(xml1.getNamQt())
                            .ngayTaiKham(xml1.getNgayTaiKham())
                            .ngayTtoan(xml1.getNgayTtoan())
                            .nguoiGiamHo(xml8 != null ? xml8.getNguoiGiamHo() : null)
                            .ppDieuTri(xml1.getPpDieuTri())
                            .qtBenhLy(xml8 != null ? xml8.getQtBenhLy() : null)
                            .soNgayDt(xml1.getSoNgayDt())
                            .tBhtt(xml1.getTBhtt())
                            .tBhttGdv(xml1.getTBhttGdv())
                            .tBncct(xml1.getTBncct())
                            .tBntt(xml1.getTBntt())
                            .tNguonKhac(xml1.getTNguonKhac())
                            .tThuoc(xml1.getTThuoc())
                            .tTongChiBh(xml1.getTTongChiBh())
                            .tTongChiBv(xml1.getTTongChiBv())
                            .tVtyt(xml1.getTVtyt())
                            .tomTatKq(xml8 != null ? xml8.getTomTatKq() : null)
                            .thangQt(xml1.getThangQt())
                            .build();

                    logger1.info("Data admisionMedicalRecord ------------------> {}", admisionMedicalRecord);
                    collector.collect(admisionMedicalRecord);
                }
            }
        }
    }
}
