package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class KeyInfo {

    @JacksonXmlProperty(localName = "KeyValue")
    private KeyValue keyValue;

    @JacksonXmlProperty(localName = "X509Data")
    private X509Data x509Data;

}
