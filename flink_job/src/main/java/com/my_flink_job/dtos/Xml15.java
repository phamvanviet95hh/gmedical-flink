package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CHI_TIEU_DIEUTRI_BENHLAO")
public class Xml15 {

    @JacksonXmlProperty(localName = "DSACH_CHITIET_DIEUTRI_BENHLAO")
    private DsachChiTietDieuTriBenhLao dsachChiTietDieuTriBenhLao;

}
