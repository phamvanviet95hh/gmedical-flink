package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ThongTinHoSo {

    @JacksonXmlProperty(localName = "NGAYLAP")
    public String ngayLap;

    @JacksonXmlProperty(localName = "SOLUONGHOSO")
    public Integer soLuongHoSo;

    @JacksonXmlProperty(localName = "DANHSACHHOSO")
    public DanhSachHoSo danhSachHoSo;

    @JacksonXmlProperty(localName = "CHUKYDONVI")
    public ChuKyDv chuKyDv;

    @JacksonXmlProperty(localName = "CHUKYDONVI")
    public ChuKyDv chuKyDv;


}
