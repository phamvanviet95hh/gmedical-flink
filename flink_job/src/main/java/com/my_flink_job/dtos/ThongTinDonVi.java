package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ThongTinDonVi {

    @JacksonXmlProperty(localName = "MACSKCB")
    private String maCSKCB;

}
