package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class KeyValue {

    @JacksonXmlProperty(localName = "RSAKeyValue")
    public RSAKeyValue rsaKeyValue;

}
