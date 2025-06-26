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

public class Xml9Extractor implements FlatMapFunction<GiamDinhHs, DulieuGiayChungSinh> {

    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml9Extractor.class);

    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<DulieuGiayChungSinh> collector) throws Exception {
        logger1.info("Start ------------------> XMl9");
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();
        for (FileHoSo hoSo : hoSoList) {
            if ("XMl9".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                String base64 = hoSo.getNoiDungFile();
                byte[] decodedBytes = Base64.getDecoder().decode(base64);
                String innerXml = new String(decodedBytes, StandardCharsets.UTF_8);
                Xml9 xmlObj = xmlMapper.readValue(innerXml, Xml9.class);
                if (xmlObj != null
                        && xmlObj.getDSachGiayChungSinh() != null
                        && xmlObj.getDSachGiayChungSinh().getDulieuGiayChungSinhs() != null) {
                    List<DulieuGiayChungSinh> dulieuGiayChungSinhList = xmlObj.getDSachGiayChungSinh().getDulieuGiayChungSinhs();
                    if (dulieuGiayChungSinhList != null) {
                        for (DulieuGiayChungSinh dulieuGiayChungSinh : dulieuGiayChungSinhList) {
                            collector.collect(dulieuGiayChungSinh);
                        }
                    }
                }
            }
        }
    }
}
