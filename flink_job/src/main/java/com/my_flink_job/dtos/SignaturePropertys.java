package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class SignaturePropertys {

    @JacksonXmlProperty(isAttribute = true, localName = "Target")
    private String target;

    @JacksonXmlProperty(localName = "SigningTime")
    public String signingTime;

}
