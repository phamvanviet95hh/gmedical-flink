package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class DulieuGiayChungSinh {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "MA_BHXH_NND")
    private String maBhxhNnd;

    @JacksonXmlProperty(localName = "MA_THE_NND")
    private String maTheNnd;

    @JacksonXmlProperty(localName = "HO_TEN_NND")
    private String hoTenNnd;

    @JacksonXmlProperty(localName = "NGAYSINH_NND")
    private String ngaySinhNnd;

    @JacksonXmlProperty(localName = "MA_DANTOC_NND")
    private String maDanTocNnd;

    @JacksonXmlProperty(localName = "SO_CCCD_NND")
    private String soCccdNnd;

    @JacksonXmlProperty(localName = "NGAYCAP_CCCD_NND")
    private String ngayCapCccdNnd;

    @JacksonXmlProperty(localName = "NOICAP_CCCD_NND")
    private String noiCapCccdNnd;

    @JacksonXmlProperty(localName = "NOI_CU_TRU_NND")
    private String noiCuTruNnd;

    @JacksonXmlProperty(localName = "MA_QUOCTICH")
    private String maQuocTich;

    @JacksonXmlProperty(localName = "MATINH_CU_TRU")
    private String maTinhCuTru;

    @JacksonXmlProperty(localName = "MAHUYEN_CU_TRU")
    private String maHuyenCuTru;

    @JacksonXmlProperty(localName = "MAXA_CU_TRU")
    private String maXaCuTru;

    @JacksonXmlProperty(localName = "HO_TEN_CHA")
    private String hoTenCha;

    @JacksonXmlProperty(localName = "MA_THE_TAM")
    private String maTheTam;

    @JacksonXmlProperty(localName = "HO_TEN_CON")
    private String hoTenCon;

    @JacksonXmlProperty(localName = "GIOI_TINH_CON")
    private String gioiTinhCon;

    @JacksonXmlProperty(localName = "SO_CON")
    private String soCon;

    @JacksonXmlProperty(localName = "LAN_SINH")
    private String lanSinh;

    @JacksonXmlProperty(localName = "SO_CON_SONG")
    private String soConSong;

    @JacksonXmlProperty(localName = "CAN_NANG_CON")
    private String canNangCon;

    @JacksonXmlProperty(localName = "NGAY_SINH_CON")
    private String ngaySinhCon;

    @JacksonXmlProperty(localName = "NOI_SINH_CON")
    private String noiSinhCon;

    @JacksonXmlProperty(localName = "TINH_TRANG_CON")
    private String tinhTrangCon;

    @JacksonXmlProperty(localName = "SINHCON_PHAUTHUAT")
    private String sinhConPhauThuat;

    @JacksonXmlProperty(localName = "SINHCON_DUOI32TUAN")
    private String sinhConDuoi32Tuan;

    @JacksonXmlProperty(localName = "GHI_CHU")
    private String ghiChu;

    @JacksonXmlProperty(localName = "NGUOI_DO_DE")
    private String nguoiDoDe;

    @JacksonXmlProperty(localName = "NGUOI_GHI_PHIEU")
    private String nguoiGhiPhieu;

    @JacksonXmlProperty(localName = "NGAY_CT")
    private String ngayCt;

    @JacksonXmlProperty(localName = "SO")
    private String so;

    @JacksonXmlProperty(localName = "QUYEN_SO")
    private String quyenSo;

    @JacksonXmlProperty(localName = "MA_TTDV")
    private String maTtdv;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;

}
