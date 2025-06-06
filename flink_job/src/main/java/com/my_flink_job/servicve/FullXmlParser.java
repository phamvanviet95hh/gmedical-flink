package com.my_flink_job.servicve;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.GiamDinhHs;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

public class FullXmlParser extends RichFlatMapFunction<String, GiamDinhHs> {
    XmlMapper xmlMapper = new XmlMapper();
    @Override
    public void open(Configuration parameters) throws Exception {

    }

    @Override
    public void flatMap(String xml, Collector<GiamDinhHs> out) throws Exception {
        GiamDinhHs giamDinhHs = xmlMapper.readValue(xml, GiamDinhHs.class);
        out.collect(giamDinhHs);
    }

}
