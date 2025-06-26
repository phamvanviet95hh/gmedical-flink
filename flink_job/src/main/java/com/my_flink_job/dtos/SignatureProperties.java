package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class SignatureProperties {

    @JacksonXmlProperty(isAttribute = true, localName = "Id")
    private String id;

    @JacksonXmlProperty(localName = "SignatureProperty")
    public SignaturePropertys signatureProperty;

}
