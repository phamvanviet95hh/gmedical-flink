package com.my_flink_job.servicve;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.*;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class Xml15Extractor implements FlatMapFunction<GiamDinhHs, ChiTietDieuTriBenhLao> {

    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml15Extractor.class);

    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<ChiTietDieuTriBenhLao> collector) throws Exception {
        logger1.info("Start ------------------> XMl15");
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {
            if ("XMl15".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                String base64 = hoSo.getNoiDungFile();
                byte[] decodedBytes = Base64.getDecoder().decode(base64);
                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
                Xml15 xmlObj = xmlMapper.readValue(innerXml, Xml15.class);
                if (xmlObj != null
                        && xmlObj.getDsachChiTietDieuTriBenhLao() != null
                        && xmlObj.getDsachChiTietDieuTriBenhLao().getChiTietDieuTriBenhLaoList() != null) {
                    List<ChiTietDieuTriBenhLao> chiTietDieuTriBenhLaoList = xmlObj.getDsachChiTietDieuTriBenhLao().getChiTietDieuTriBenhLaoList();

                    if (chiTietDieuTriBenhLaoList != null) {
                        for (ChiTietDieuTriBenhLao chiTietDieuTriBenhLao : chiTietDieuTriBenhLaoList) {
                            collector.collect(chiTietDieuTriBenhLao);
                        }
                    }
                }
            }
        }
    }
}
