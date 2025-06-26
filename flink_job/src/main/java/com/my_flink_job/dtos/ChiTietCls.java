package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ChiTietCls {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "STT")
    private String stt;

    @JacksonXmlProperty(localName = "MA_DICH_VU")
    private String maDichVu;

    @JacksonXmlProperty(localName = "MA_CHI_SO")
    private String maChiSo;

    @JacksonXmlProperty(localName = "TEN_CHI_SO")
    private String tenChiSo;

    @JacksonXmlProperty(localName = "GIA_TRI")
    private String giaTri;

    @JacksonXmlProperty(localName = "DON_VI_DO")
    private String donViDo;

    @JacksonXmlProperty(localName = "MO_TA")
    private String moTa;

    @JacksonXmlProperty(localName = "KET_LUAN")
    private String ketLuan;

    @JacksonXmlProperty(localName = "NGAY_KQ")
    private String ngayKq;

    @JacksonXmlProperty(localName = "MA_BS_DOC_KQ")
    private String maBsDocKq;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;

}
