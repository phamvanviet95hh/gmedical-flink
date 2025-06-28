package com.my_flink_job.dtos.iceberg;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionMedicalExam {

    private String uuid;
    private String createdAt;
    private String createdBy;
    private String updatedAt;

    private String nguoiChuTri;


    private String chucVu;


    private String ngayHop;


    private String hoTen;


    private String ngaySinh;


    private String soCccd;


    private String ngayCapCccd;


    private String noiCapCccd;


    private String diaChi;


    private String maTinhCuTru;


    private String maHuyenCuTru;


    private String maXaCuTru;


    private String maBhxh;


    private String maTheBhyt;


    private String ngheNghiep;


    private String dienThoai;


    private String maDoiTuong;


    private String khamGiamDinh;


    private String soBienBan;


    private String tyleTtctCu;


    private String dangHuongCheDo;


    private String ngayChungTu;


    private String soGiayGioiThieu;


    private String ngayDeNghi;


    private String maDonVi;


    private String gioiThieuCua;


    private String ketQuaKham;


    private String soVanBanCanCu;


    private String tyleTtctMoi;


    private String tongTyleTtct;


    private String dangKhuyettat;


    private String mucDoKhuyettat;


    private String deNghi;


    private String duocXacDinh;


    private String duPhong;

    private String admision_checkin_uuid;
}
