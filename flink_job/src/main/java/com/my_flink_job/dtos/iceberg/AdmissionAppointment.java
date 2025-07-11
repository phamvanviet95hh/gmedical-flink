package com.my_flink_job.dtos.iceberg;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionAppointment {
    private String uuid;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String chanDoanRv;
    private String diaChi;
    private String duPhong;
    private String gioiTinh;
    private String gtTheDen;
    private String hoTen;
    private String maBacSi;
    private String maBenhChinh;
    private String maBenhKt;
    private String maBenhYhct;
    private String maCskcb;
    private String maDoiTuongKcb;
    private String maTheBhyt;
    private String maTtdv;
    private String ngayCt;
    private String ngayHenKl;
    private String ngayRa;
    private String ngaySinh;
    private String ngayVao;
    private String ngayVaoNoiTru;
    private String soGiayHenKl;
    private String stt;
    private String admision_checkin_uuid;

}
