package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CHITIEU_CHITIET_DICHVUCANLAMSANG")
public class Xml4 {

    @JacksonXmlProperty(localName = "DSACH_CHI_TIET_CLS")
    private DanhSachChiTietCls danhSachChiTietCls;

}
