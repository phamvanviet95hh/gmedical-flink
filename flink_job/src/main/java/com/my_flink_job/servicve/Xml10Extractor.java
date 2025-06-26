package com.my_flink_job.servicve;


import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.FileHoSo;
import com.my_flink_job.dtos.GiamDinhHs;
import com.my_flink_job.dtos.Xml10;
import com.my_flink_job.dtos.Xml8;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class Xml10Extractor implements FlatMapFunction<GiamDinhHs, Xml10> {

    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml10Extractor.class);
    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<Xml10> collector) throws Exception {

        logger1.info("Start ------------------> XMl10");
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {
            if ("XMl10".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                String base64 = hoSo.getNoiDungFile();
                byte[] decodedBytes = Base64.getDecoder().decode(base64);
                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
                Xml10 xmlObj = xmlMapper.readValue(innerXml, Xml10.class);
                if (xmlObj != null && xmlObj.getMaLk() != null) {
                    collector.collect(xmlObj);
                }
            }
        }


    }
}
