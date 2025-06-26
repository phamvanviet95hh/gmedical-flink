package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Signatures {

    @JacksonXmlProperty(isAttribute = true) // Nếu Id là attribute trong XML
    private String Id;

    @JacksonXmlProperty(localName = "SignedInfo")
    private SignedInfo signedInfo;

    @JacksonXmlProperty(localName = "SignatureValue")
    private String signatureValue;

    @JacksonXmlProperty(localName = "KeyInfo")
    private KeyInfo keyInfo;

    @JacksonXmlProperty(localName = "Object")
    private Objectss objects;

}
