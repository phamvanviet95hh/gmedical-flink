package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class DanhSachHoSo {

    @JacksonXmlProperty(localName = "HOSO")
    public Hoso hoso;

}
