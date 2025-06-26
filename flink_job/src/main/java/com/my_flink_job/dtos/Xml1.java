package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "TONG_HOP")
public class Xml1 {

    public Xml1() {
        // Không cần khởi tạo gì, Jackson chỉ cần constructor tồn tại
    }

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

    @JacksonXmlProperty(localName = "NGAY_SINH")
    private String ngaySinh;

    @JacksonXmlProperty(localName = "GIOI_TINH")
    private String gioiTinh;

    @JacksonXmlProperty(localName = "NHOM_MAU")
    private String nhomMau;

    @JacksonXmlProperty(localName = "MA_QUOCTICH")
    private String maQuocTich;

    @JacksonXmlProperty(localName = "MA_DANTOC")
    private String maDanToc;

    @JacksonXmlProperty(localName = "MA_NGHE_NGHIEP")
    private String maNgheNghiep;

    @JacksonXmlProperty(localName = "DIA_CHI")
    private String diaChi;

    @JacksonXmlProperty(localName = "MATINH_CU_TRU")
    private String maTinhCuTru;

    @JacksonXmlProperty(localName = "MAHUYEN_CU_TRU")
    private String maHuyenCuTru;

    @JacksonXmlProperty(localName = "MAXA_CU_TRU")
    private String maXaCuTru;

    @JacksonXmlProperty(localName = "DIEN_THOAI")
    private String dienThoai;

    @JacksonXmlProperty(localName = "MA_THE_BHYT")
    private String maTheBhyt;

    @JacksonXmlProperty(localName = "MA_DKBD")
    private String maDkbd;

    @JacksonXmlProperty(localName = "GT_THE_TU")
    private String gtTheTu;

    @JacksonXmlProperty(localName = "GT_THE_DEN")
    private String gtTheDen;

    @JacksonXmlProperty(localName = "NGAY_MIEN_CCT")
    private String ngayMienCct;

    @JacksonXmlProperty(localName = "LY_DO_VV")
    private String lyDoVv;

    @JacksonXmlProperty(localName = "LY_DO_VNT")
    private String lyDoVnt;

    @JacksonXmlProperty(localName = "MA_LY_DO_VNT")
    private String maLyDoVnt;

    @JacksonXmlProperty(localName = "CHAN_DOAN_VAO")
    private String chanDoanVao;

    @JacksonXmlProperty(localName = "CHAN_DOAN_RV")
    private String chanDoanRv;

    @JacksonXmlProperty(localName = "MA_BENH_CHINH")
    private String maBenhChinh;

    @JacksonXmlProperty(localName = "MA_BENH_KT")
    private String maBenhKt;

    @JacksonXmlProperty(localName = "MA_BENH_YHCT")
    private String maBenhYhct;

    @JacksonXmlProperty(localName = "MA_PTTT_QT")
    private String maPtttQt;

    @JacksonXmlProperty(localName = "MA_DOITUONG_KCB")
    private String maDoiTuongKcb;

    @JacksonXmlProperty(localName = "MA_NOI_DI")
    private String maNoiDi;

    @JacksonXmlProperty(localName = "MA_NOI_DEN")
    private String maNoiDen;

    @JacksonXmlProperty(localName = "MA_TAI_NAN")
    private String maTaiNan;

    @JacksonXmlProperty(localName = "NGAY_VAO")
    private String ngayVao;

    @JacksonXmlProperty(localName = "NGAY_VAO_NOI_TRU")
    private String ngayVaoNoiTru;

    @JacksonXmlProperty(localName = "NGAY_RA")
    private String ngayRa;

    @JacksonXmlProperty(localName = "GIAY_CHUYEN_TUYEN")
    private String giayChuyenTuyen;

    @JacksonXmlProperty(localName = "SO_NGAY_DTRI")
    private String soNgayDt;

    @JacksonXmlProperty(localName = "PP_DIEU_TRI")
    private String ppDieuTri;

    @JacksonXmlProperty(localName = "KET_QUA_DTRI")
    private String ketQuaDt;

    @JacksonXmlProperty(localName = "MA_LOAI_RV")
    private String maLoaiRv;

    @JacksonXmlProperty(localName = "GHI_CHU")
    private String ghiChu;

    @JacksonXmlProperty(localName = "NGAY_TTOAN")
    private String ngayTtoan;

    @JacksonXmlProperty(localName = "T_THUOC")
    private String tThuoc;

    @JacksonXmlProperty(localName = "T_VTYT")
    private String tVtyt;

    @JacksonXmlProperty(localName = "T_TONGCHI_BV")
    private String tTongChiBv;

    @JacksonXmlProperty(localName = "T_TONGCHI_BH")
    private String tTongChiBh;

    @JacksonXmlProperty(localName = "T_BNTT")
    private String tBntt;

    @JacksonXmlProperty(localName = "T_BNCCT")
    private String tBncct;

    @JacksonXmlProperty(localName = "T_BHTT")
    private String tBhtt;

    @JacksonXmlProperty(localName = "T_NGUONKHAC")
    private String tNguonKhac;

    @JacksonXmlProperty(localName = "T_BHTT_GDV")
    private String tBhttGdv;

    @JacksonXmlProperty(localName = "NAM_QT")
    private String namQt;

    @JacksonXmlProperty(localName = "THANG_QT")
    private String thangQt;

    @JacksonXmlProperty(localName = "MA_LOAI_KCB")
    private String maLoaiKcb;

    @JacksonXmlProperty(localName = "MA_KHOA")
    private String maKhoa;

    @JacksonXmlProperty(localName = "MA_CSKCB")
    private String maCskcb;

    @JacksonXmlProperty(localName = "MA_KHUVUC")
    private String maKhuVuc;

    @JacksonXmlProperty(localName = "CAN_NANG")
    private String canNang;

    @JacksonXmlProperty(localName = "CAN_NANG_CON")
    private String canNangCon;

    @JacksonXmlProperty(localName = "NAM_NAM_LIEN_TUC")
    private String namNamLienTuc;

    @JacksonXmlProperty(localName = "NGAY_TAI_KHAM")
    private String ngayTaiKham;

    @JacksonXmlProperty(localName = "MA_HSBA")
    private String maHsba;

    @JacksonXmlProperty(localName = "MA_TTDV")
    private String maTtdv;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;

}
