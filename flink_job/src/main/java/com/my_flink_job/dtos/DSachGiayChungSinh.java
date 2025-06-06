package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class DSachGiayChungSinh {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "DU_LIEU_GIAY_CHUNG_SINH")
    private List<DulieuGiayChungSinh> dulieuGiayChungSinhs;

}
