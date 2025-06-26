package com.my_flink_job.servicve;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.App;
import com.my_flink_job.dtos.*;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class Xml2Extractor implements FlatMapFunction<GiamDinhHs, ChiTietThuoc> {
    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml2Extractor.class);

    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<ChiTietThuoc> collector) throws Exception {
        logger1.info("Start ------------------> XMl2");
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {
            if ("XML2".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                String base64 = hoSo.getNoiDungFile();
                byte[] decodedBytes = Base64.getDecoder().decode(base64);
                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
                Xml2 xmlObj = xmlMapper.readValue(innerXml, Xml2.class);
                if (xmlObj != null
                        && xmlObj.getDanhSachChiTietThuoc() != null
                        && xmlObj.getDanhSachChiTietThuoc().getChiTietThuoc() != null) {
                    List<ChiTietThuoc> listThuoc = xmlObj.getDanhSachChiTietThuoc().getChiTietThuoc();

                    if (listThuoc != null) {
                        for (ChiTietThuoc chiTietThuoc : listThuoc) {
                            collector.collect(chiTietThuoc);
                        }
                    }
                }
            }
        }

    }
}
