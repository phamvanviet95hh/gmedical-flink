package com.my_flink_job.dtos.iceberg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdmissionCheckin {

    private String id;//
    private String maBn;//
    private String canNang;//
    private String maTheBhyt;//
    private String namNamLienTuc;//
    private String maDkbd;//
    private String gtTheTu;//
    private String gtTheDen;//
    private String ngayMienCct;//
    private String lyDoVv;//
    private String lyDoVnt;//
    private String maLyDoVnt;//
    private String maDoituongKcb;//
    private String maNoiDi;//
    private String maNoiDen;//
    private String maTaiNan;//
    private String ngayVao;//
    private String ngayVaoNoiTru;//
    private String ngayRa;//
    private String maHsba;//
    private String duPhong;//
    private String maLk;//
    private String maLoaiRv;//
    private String ketQuaDtri;//
    private String createdAt;//
    private String updatedAt;//
    private String createdBy;//
    private String updatedBy;
    private String stt;//
    private String maCskb;//

}
