package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class DanhSachChiTietDienBienBenh {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CHI_TIET_DIEN_BIEN_BENH")
    private List<ChiTietDienBienBenh> chiTietDienBienBenhList;

}
