package com.my_flink_job.dtos;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class RSAKeyValue {

    @JacksonXmlProperty(localName = "Modulus")
    public String modulus;

    @JacksonXmlProperty(localName = "Exponent")
    public String exponent;

}
