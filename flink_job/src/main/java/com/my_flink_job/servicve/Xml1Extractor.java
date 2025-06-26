package com.my_flink_job.servicve;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.*;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class Xml1Extractor implements FlatMapFunction<GiamDinhHs, Xml1> {
    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml1Extractor.class);
    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<Xml1> collector) throws Exception {
        logger1.info("Start XMl1");
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {

            if ("XML1".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                String base64Xml1 = hoSo.getNoiDungFile();
                byte[] decodedBytes = Base64.getDecoder().decode(base64Xml1);
                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
                Xml1 xmlObj = xmlMapper.readValue(innerXml, Xml1.class);
                collector.collect(xmlObj);
            }
        }
    }
}
