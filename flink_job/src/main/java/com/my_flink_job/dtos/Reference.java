package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Reference {

    @JacksonXmlProperty(isAttribute = true, localName = "URI")
    private String URI;

    @JacksonXmlProperty(localName = "Transforms")
    public Transformss transforms;

    @JacksonXmlProperty(localName = "DigestMethod")
    public String digestMethod;

    @JacksonXmlProperty(localName = "DigestValue")
    public String digestValue;

}
