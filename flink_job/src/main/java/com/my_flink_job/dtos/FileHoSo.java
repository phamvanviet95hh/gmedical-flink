package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class FileHoSo {

    @JacksonXmlProperty(localName = "LOAIHOSO")
    private String loaiHoSo;

    @JacksonXmlProperty(localName = "NOIDUNGFILE")
    private String noiDungFile;

}
