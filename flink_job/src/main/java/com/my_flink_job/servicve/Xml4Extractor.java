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

public class Xml4Extractor implements FlatMapFunction<GiamDinhHs, ChiTietCls> {
    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml4Extractor.class);
    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<ChiTietCls> collector) throws Exception {
        logger1.info("Start ------------------> XMl4");
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {
            if ("XML4".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                String base64 = hoSo.getNoiDungFile();
                byte[] decodedBytes = Base64.getDecoder().decode(base64);
                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
                Xml4 xmlObj = xmlMapper.readValue(innerXml, Xml4.class);
                if (xmlObj != null
                        && xmlObj.getDanhSachChiTietCls() != null
                        && xmlObj.getDanhSachChiTietCls().getChiTietCls() != null) {
                    List<ChiTietCls> chiTietClsList = xmlObj.getDanhSachChiTietCls().getChiTietCls();

                    if (chiTietClsList != null) {
                        for (ChiTietCls chiTietDvkt : chiTietClsList) {
                            collector.collect(chiTietDvkt);
                        }
                    }
                }
            }
        }
    }
}
