package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CHI_TIEU_DU_LIEU_GIAM_DINH_YKHOA")
public class Xml12 {

    @JacksonXmlProperty(localName = "NGUOI_CHU_TRI")
    private String nguoiChuTri;

    @JacksonXmlProperty(localName = "CHUC_VU")
    private String chucVu;

    @JacksonXmlProperty(localName = "NGAY_HOP")
    private String ngayHop;

    @JacksonXmlProperty(localName = "HO_TEN")
    private String hoTen;

    @JacksonXmlProperty(localName = "NGAY_SINH")
    private String ngaySinh;

    @JacksonXmlProperty(localName = "SO_CCCD")
    private String soCccd;

    @JacksonXmlProperty(localName = "NGAY_CAP_CCCD")
    private String ngayCapCccd;

    @JacksonXmlProperty(localName = "NOI_CAP_CCCD")
    private String noiCapCccd;

    @JacksonXmlProperty(localName = "DIA_CHI")
    private String diaChi;

    @JacksonXmlProperty(localName = "MATINH_CU_TRU")
    private String maTinhCuTru;

    @JacksonXmlProperty(localName = "MAHUYEN_CU_TRU")
    private String maHuyenCuTru;

    @JacksonXmlProperty(localName = "MAXA_CU_TRU")
    private String maXaCuTru;

    @JacksonXmlProperty(localName = "MA_BHXH")
    private String maBhxh;

    @JacksonXmlProperty(localName = "MA_THE_BHYT")
    private String maTheBhyt;

    @JacksonXmlProperty(localName = "NGHE_NGHIEP")
    private String ngheNghiep;

    @JacksonXmlProperty(localName = "DIEN_THOAI")
    private String dienThoai;

    @JacksonXmlProperty(localName = "MA_DOI_TUONG")
    private String maDoiTuong;

    @JacksonXmlProperty(localName = "KHAM_GIAM_DINH")
    private String khamGiamDinh;

    @JacksonXmlProperty(localName = "SO_BIEN_BAN")
    private String soBienBan;

    @JacksonXmlProperty(localName = "TYLE_TTCT_CU")
    private String tyleTtctCu;

    @JacksonXmlProperty(localName = "DANG_HUONG_CHE_DO")
    private String dangHuongCheDo;

    @JacksonXmlProperty(localName = "NGAY_CHUNG_TU")
    private String ngayChungTu;

    @JacksonXmlProperty(localName = "SO_GIAY_GIOI_THIEU")
    private String soGiayGioiThieu;

    @JacksonXmlProperty(localName = "NGAY_DE_NGHI")
    private String ngayDeNghi;

    @JacksonXmlProperty(localName = "MA_DONVI")
    private String maDonVi;

    @JacksonXmlProperty(localName = "GIOI_THIEU_CUA")
    private String gioiThieuCua;

    @JacksonXmlProperty(localName = "KET_QUA_KHAM")
    private String ketQuaKham;

    @JacksonXmlProperty(localName = "SO_VAN_BAN_CAN_CU")
    private String soVanBanCanCu;

    @JacksonXmlProperty(localName = "TYLE_TTCT_MOI")
    private String tyleTtctMoi;

    @JacksonXmlProperty(localName = "TONG_TYLE_TTCT")
    private String tongTyleTtct;

    @JacksonXmlProperty(localName = "DANG_KHUYETTAT")
    private String dangKhuyettat;

    @JacksonXmlProperty(localName = "MUC_DO_KHUYETTAT")
    private String mucDoKhuyettat;

    @JacksonXmlProperty(localName = "DE_NGHI")
    private String deNghi;

    @JacksonXmlProperty(localName = "DUOC_XACDINH")
    private String duocXacDinh;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;

}
