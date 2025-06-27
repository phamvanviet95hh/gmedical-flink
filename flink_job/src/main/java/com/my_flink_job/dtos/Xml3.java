package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@JacksonXmlRootElement(localName = "CHITIEU_CHITIET_DVKT_VTYT")
public class Xml3 {

    @JacksonXmlProperty(localName = "DSACH_CHI_TIET_DVKT")
    private DSachChiTietDVKT dsachChiTietDvkt;

}
