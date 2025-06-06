package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CHI_TIEU_GIAYHEN_KHAMLAI")
public class Xml14 {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "SO_GIAYHEN_KL")
    private String soGiayHenKl;

    @JacksonXmlProperty(localName = "MA_CSKCB")
    private String maCskcb;

    @JacksonXmlProperty(localName = "HO_TEN")
    private String hoTen;

    @JacksonXmlProperty(localName = "NGAY_SINH")
    private String ngaySinh;

    @JacksonXmlProperty(localName = "GIOI_TINH")
    private String gioiTinh;

    @JacksonXmlProperty(localName = "DIA_CHI")
    private String diaChi;

    @JacksonXmlProperty(localName = "MA_THE_BHYT")
    private String maTheBhyt;

    @JacksonXmlProperty(localName = "GT_THE_DEN")
    private String gtTheDen;

    @JacksonXmlProperty(localName = "NGAY_VAO")
    private String ngayVao;

    @JacksonXmlProperty(localName = "NGAY_VAO_NOI_TRU")
    private String ngayVaoNoiTru;

    @JacksonXmlProperty(localName = "NGAY_RA")
    private String ngayRa;

    @JacksonXmlProperty(localName = "NGAY_HEN_KL")
    private String ngayHenKl;

    @JacksonXmlProperty(localName = "CHAN_DOAN_RV")
    private String chanDoanRv;

    @JacksonXmlProperty(localName = "MA_BENH_CHINH")
    private String maBenhChinh;

    @JacksonXmlProperty(localName = "MA_BENH_KT")
    private String maBenhKt;

    @JacksonXmlProperty(localName = "MA_BENH_YHCT")
    private String maBenhYhct;

    @JacksonXmlProperty(localName = "MA_DOITUONG_KCB")
    private String maDoiTuongKcb;

    @JacksonXmlProperty(localName = "MA_BAC_SI")
    private String maBacSi;

    @JacksonXmlProperty(localName = "MA_TTDV")
    private String maTtdv;

    @JacksonXmlProperty(localName = "NGAY_CT")
    private String ngayCt;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;

}
