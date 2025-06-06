package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class DanhSachChiTietCls {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CHI_TIET_CLS")
    private List<ChiTietCls> chiTietCls;


}
