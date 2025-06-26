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

public class Xml3Extractor implements FlatMapFunction<GiamDinhHs, ChiTietDvkt> {

    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml3Extractor.class);
    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<ChiTietDvkt> collector) throws Exception {
        logger1.info("Start ------------------> XMl3");
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {
            if ("XML3".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                String base64 = hoSo.getNoiDungFile();
                byte[] decodedBytes = Base64.getDecoder().decode(base64);
                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
                Xml3 xmlObj = xmlMapper.readValue(innerXml, Xml3.class);
                if (xmlObj != null
                        && xmlObj.getDsachChiTietDvkt() != null
                        && xmlObj.getDsachChiTietDvkt().getChiTietDvkt() != null) {
                    List<ChiTietDvkt> tietDvktList = xmlObj.getDsachChiTietDvkt().getChiTietDvkt();

                    if (tietDvktList != null) {
                        for (ChiTietDvkt chiTietDvkt : tietDvktList) {
                            collector.collect(chiTietDvkt);
                        }
                    }
                }
            }
        }
    }
}
