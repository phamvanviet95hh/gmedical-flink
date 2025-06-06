package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CHI_TIEU_DU_LIEU_GIAY_CHUNG_SINH")
public class Xml9 {

    @JacksonXmlProperty(localName = "DSACH_GIAYCHUNGSINH")
    private DSachGiayChungSinh dSachGiayChungSinh;

}
