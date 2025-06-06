package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CHI_TIEU_DU_LIEU_GIAY_NGHI_DUONG_THAI")
public class Xml10 {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "SO_SERI")
    private String soSeri;

    @JacksonXmlProperty(localName = "SO_CT")
    private String soCt;

    @JacksonXmlProperty(localName = "SO_NGAY")
    private Integer soNgay;

    @JacksonXmlProperty(localName = "DON_VI")
    private String donVi;

    @JacksonXmlProperty(localName = "CHAN_DOAN_RV")
    private String chanDoanRv;

    @JacksonXmlProperty(localName = "TU_NGAY")
    private String tuNgay;

    @JacksonXmlProperty(localName = "DEN_NGAY")
    private String denNgay;

    @JacksonXmlProperty(localName = "MA_TTDV")
    private String maTtdv;

    @JacksonXmlProperty(localName = "TEN_BS")
    private String tenBs;

    @JacksonXmlProperty(localName = "MA_BS")
    private String maBs;

    @JacksonXmlProperty(localName = "NGAY_CT")
    private String ngayCt;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;

}