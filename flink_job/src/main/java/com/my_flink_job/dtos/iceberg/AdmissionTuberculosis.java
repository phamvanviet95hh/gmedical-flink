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
public class AdmissionTuberculosis {

    private String uuid;
    private String maLk;
    private String createdAt;
    private String createdBy;
    private String updatedAt;

    private String stt;


    private String maBn;


    private String hoTen;


    private String soCccd;


    private String phanLoaiLaoViTri;


    private String phanLoaiLaoTs;


    private String phanLoaiLaoHiv;


    private String phanLoaiLaoVk;


    private String phanLoaiLaoKt;


    private String loaiDtriLao;


    private String ngayBdDtriLao;


    private String phacDoDtriLao;

    private String ngayKtDtriLao;

    private String ketQuaDtriLao;


    private String maCskcb;


    private String ngayKdHiv;


    private String bddtArv;


    private String ngayBatDauDtCtx;

    private String duPhong;
    private String admision_checkin_uuid;

}
