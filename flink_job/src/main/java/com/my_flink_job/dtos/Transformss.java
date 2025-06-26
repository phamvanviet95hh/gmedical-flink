package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class Transformss {

    @JacksonXmlProperty(localName = "Transform")
    public String transform;

}
