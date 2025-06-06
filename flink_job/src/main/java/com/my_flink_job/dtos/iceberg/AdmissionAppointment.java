package com.my_flink_job.dtos.iceberg;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Data
public class AdmissionAppointment {
    private String stt;
    private String soGiayHenKl;
    private String maCskcb;
    private String hoTen;
    private String ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String maTheBhyt;
    private String gtTheDen;
    private String ngayVao;
    private String ngayVaoNoiTru;
    private String ngayRa;
    private String ngayHenKl;
    private String chanDoanRv;
    private String maBenhChinh;
    private String maBenhKt;
    private String maBenhYhct;
    private String maDoiTuongKcb;
    private String maBacSi;
    private String maTtdv;
    private String ngayCt;
    private String duPhong;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

}
