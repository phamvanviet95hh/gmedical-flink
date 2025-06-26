package com.my_flink_job.servicve;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.FileHoSo;
import com.my_flink_job.dtos.GiamDinhHs;
import com.my_flink_job.dtos.Xml11;
import com.my_flink_job.dtos.Xml8;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class Xml11Extractor implements FlatMapFunction<GiamDinhHs, Xml11> {

    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml11Extractor.class);

    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<Xml11> collector) throws Exception {
        logger1.info("Start ------------------> XMl11");
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {
            if ("XMl11".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                String base64 = hoSo.getNoiDungFile();
                byte[] decodedBytes = Base64.getDecoder().decode(base64);
                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
                Xml11 xmlObj = xmlMapper.readValue(innerXml, Xml11.class);
                if (xmlObj != null && xmlObj.getMaLk() != null) {
                    collector.collect(xmlObj);
                }
            }
        }
    }
}
