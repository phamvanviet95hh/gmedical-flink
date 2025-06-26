package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Objectss {

    @JacksonXmlProperty(localName = "SignatureProperties")
    public SignatureProperties signatureProperties;

}
