package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class HoSoBACSVADTHiv {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "MA_THE_BHYT")
    private String maTheBhyt;

    @JacksonXmlProperty(localName = "SO_CCCD")
    private String soCccd;

    @JacksonXmlProperty(localName = "NGAY_SINH")
    private String ngaySinh;

    @JacksonXmlProperty(localName = "GIOI_TINH")
    private String gioiTinh;

    @JacksonXmlProperty(localName = "DIA_CHI")
    private String diaChi;

    @JacksonXmlProperty(localName = "MATINH_CU_TRU")
    private String maTinhCuTru;

    @JacksonXmlProperty(localName = "MAHUYEN_CU_TRU")
    private String maHuyenCuTru;

    @JacksonXmlProperty(localName = "MAXA_CU_TRU")
    private String maXaCuTru;

    @JacksonXmlProperty(localName = "NGAYKD_HIV")
    private String ngayKDHiv;

    @JacksonXmlProperty(localName = "NOI_LAY_MAU_XN")
    private String noiLayMauXn;

    @JacksonXmlProperty(localName = "NOI_XN_KD")
    private String noiXnKd;

    @JacksonXmlProperty(localName = "NOI_BDDT_ARV")
    private String noiBDDTArv;

    @JacksonXmlProperty(localName = "BDDT_ARV")
    private String bddtArv;

    @JacksonXmlProperty(localName = "MA_PHAC_DO_DIEU_TRI_BD")
    private String maPhacDoDieuTriBd;

    @JacksonXmlProperty(localName = "MA_BAC_PHAC_DO_BD")
    private String maBacPhacDoBd;

    @JacksonXmlProperty(localName = "MA_LYDO_DTRI")
    private String maLyDoDtri;

    @JacksonXmlProperty(localName = "LOAI_DTRI_LAO")
    private String loaiDtriLao;

    @JacksonXmlProperty(localName = "SANG_LOC_LAO")
    private String sangLocLao;

    @JacksonXmlProperty(localName = "PHACDO_DTRI_LAO")
    private String phacDoDtriLao;

    @JacksonXmlProperty(localName = "NGAYBD_DTRI_LAO")
    private String ngayBdDtriLao;

    @JacksonXmlProperty(localName = "NGAYKT_DTRI_LAO")
    private String ngayKtDtriLao;

    @JacksonXmlProperty(localName = "KQ_DTRI_LAO")
    private String kqDtriLao;

    @JacksonXmlProperty(localName = "MA_LYDO_XNTL_VR")
    private String maLyDoXntlVr;

    @JacksonXmlProperty(localName = "NGAY_XN_TLVR")
    private String ngayXnTlvr;

    @JacksonXmlProperty(localName = "KQ_XNTL_VR")
    private String kqXntlVr;

    @JacksonXmlProperty(localName = "NGAY_KQ_XN_TLVR")
    private String ngayKqXnTlvr;

    @JacksonXmlProperty(localName = "MA_LOAI_BN")
    private String maLoaiBn;

    @JacksonXmlProperty(localName = "GIAI_DOAN_LAM_SANG")
    private String giaiDoanLamSang;

    @JacksonXmlProperty(localName = "NHOM_DOI_TUONG")
    private String nhomDoiTuong;

    @JacksonXmlProperty(localName = "MA_TINH_TRANG_DK")
    private String maTinhTrangDk;

    @JacksonXmlProperty(localName = "LAN_XN_PCR")
    private String lanXnPcr;

    @JacksonXmlProperty(localName = "NGAY_XN_PCR")
    private String ngayXnPcr;

    @JacksonXmlProperty(localName = "NGAY_KQ_XN_PCR")
    private String ngayKqXnPcr;

    @JacksonXmlProperty(localName = "MA_KQ_XN_PCR")
    private String maKqXnPcr;

    @JacksonXmlProperty(localName = "NGAY_NHAN_TT_MANG_THAI")
    private String ngayNhanTtMangThai;

    @JacksonXmlProperty(localName = "NGAY_BAT_DAU_DT_CTX")
    private String ngayBatDauDtCtx;

    @JacksonXmlProperty(localName = "MA_XU_TRI")
    private String maXuTri;

    @JacksonXmlProperty(localName = "NGAY_BAT_DAU_XU_TRI")
    private String ngayBatDauXuTri;

    @JacksonXmlProperty(localName = "NGAY_KET_THUC_XU_TRI")
    private String ngayKetThucXuTri;

    @JacksonXmlProperty(localName = "MA_PHAC_DO_DIEU_TRI")
    private String maPhacDoDieuTri;

    @JacksonXmlProperty(localName = "MA_BAC_PHAC_DO")
    private String maBacPhacDo;

    @JacksonXmlProperty(localName = "SO_NGAY_CAP_THUOC_ARV")
    private String soNgayCapThuocArv;

    @JacksonXmlProperty(localName = "NGAY_CHUYEN_PHAC_DO")
    private String ngayChuyenPhacDo;

    @JacksonXmlProperty(localName = "LY_DO_CHUYEN_PHAC_DO")
    private String lyDoChuyenPhacDo;

    @JacksonXmlProperty(localName = "MA_CSKCB")
    private String maCskcb;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;



}
