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

public class Xml7Extractor implements FlatMapFunction<GiamDinhHs, Xml7> {

    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml7Extractor.class);
    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<Xml7> collector) throws Exception {
        logger1.info("Start ------------------> XMl7");
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {
            if ("XML7".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                String base64 = hoSo.getNoiDungFile();
                byte[] decodedBytes = Base64.getDecoder().decode(base64);
                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
                Xml7 xmlObj = xmlMapper.readValue(innerXml, Xml7.class);
                if (xmlObj != null && xmlObj.getMaLk() != null) {
                    collector.collect(xmlObj);
                }
            }
        }
    }
}
