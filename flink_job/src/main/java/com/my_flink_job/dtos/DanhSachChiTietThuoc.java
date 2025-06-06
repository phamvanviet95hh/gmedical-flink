package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class DanhSachChiTietThuoc {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CHI_TIET_THUOC")
    private List<ChiTietThuoc> chiTietThuoc;

}
