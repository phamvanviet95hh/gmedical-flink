package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class SignedInfo {

    @JacksonXmlProperty(localName = "CanonicalizationMethod")
    public String canonicalizationMethod;

    @JacksonXmlProperty(localName = "SignatureMethod")
    public String signatureMethod;

    @JacksonXmlProperty(localName = "Reference")
    public Reference reference;

}
