package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class X509Data {

    @JacksonXmlProperty(localName = "X509SubjectName")
    public String x509SubjectName;

    @JacksonXmlProperty(localName = "X509Certificate")
    public String x509Certificate;

}
