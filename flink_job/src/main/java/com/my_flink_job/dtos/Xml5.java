package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CHITIEU_CHITIET_DIENBIENLAMSANG")
public class Xml5 {

    @JacksonXmlProperty(localName = "DSACH_CHI_TIET_DIEN_BIEN_BENH")
    private DanhSachChiTietDienBienBenh dsachChiTietDienBienBenh;

}
