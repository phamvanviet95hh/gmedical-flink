package com.my_flink_job.dtos.iceberg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    private String diaChi;
    private String dienThoai;
    private String gioiTinh;
    private String hoTen;
    private String hoTenCha;
    private String hoTenMe;
    private String maDanToc;
    private String maNgheNghiep;
    private String maQuocTich;
    private String maHuyenCuTru;
    private String maTinhCuTru;
    private String maXaCuTru;
    private String ngaySinh;
    private String nhomMau;
    private String soCccd;
    private String stt;

}
