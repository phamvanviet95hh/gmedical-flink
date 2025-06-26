package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ChiTietDieuTriBenhLao {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "STT")
    private String stt;

    @JacksonXmlProperty(localName = "MA_BN")
    private String maBn;

    @JacksonXmlProperty(localName = "HO_TEN")
    private String hoTen;

    @JacksonXmlProperty(localName = "SO_CCCD")
    private String soCccd;

    @JacksonXmlProperty(localName = "PHANLOAI_LAO_VITRI")
    private String phanLoaiLaoViTri;

    @JacksonXmlProperty(localName = "PHANLOAI_LAO_TS")
    private String phanLoaiLaoTs;

    @JacksonXmlProperty(localName = "PHANLOAI_LAO_HIV")
    private String phanLoaiLaoHiv;

    @JacksonXmlProperty(localName = "PHANLOAI_LAO_VK")
    private String phanLoaiLaoVk;

    @JacksonXmlProperty(localName = "PHANLOAI_LAO_KT")
    private String phanLoaiLaoKt;

    @JacksonXmlProperty(localName = "LOAI_DTRI_LAO")
    private String loaiDtriLao;

    @JacksonXmlProperty(localName = "NGAYBD_DTRI_LAO")
    private String ngayBdDtriLao;

    @JacksonXmlProperty(localName = "PHACDO_DTRI_LAO")
    private String phacDoDtriLao;

    @JacksonXmlProperty(localName = "NGAYKT_DTRI_LAO")
    private String ngayKtDtriLao;

    @JacksonXmlProperty(localName = "KET_QUA_DTRI_LAO")
    private String ketQuaDtriLao;

    @JacksonXmlProperty(localName = "MA_CSKCB")
    private String maCskcb;

    @JacksonXmlProperty(localName = "NGAYKD_HIV")
    private String ngayKdHiv;

    @JacksonXmlProperty(localName = "BDDT_ARV")
    private String bddtArv;

    @JacksonXmlProperty(localName = "NGAY_BAT_DAU_DT_CTX")
    private String ngayBatDauDtCtx;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;
}
