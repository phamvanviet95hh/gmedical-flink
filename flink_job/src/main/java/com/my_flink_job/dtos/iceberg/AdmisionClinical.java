package com.my_flink_job.dtos.iceberg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmisionClinical {

    private String uuid;
    private String maLk;
    private String createdAt;
    private String createdBy;
    private String updatedAt;
    private String dienBienLs;
    private String duPhong;
    private String giaiDoanBenh;
    private String hoiChan;
    private String nguoiThucHien;
    private String phauThuat;
    private String stt;
    private String thoiDiemDbls;
    private String admision_checkin_uuid;

}
