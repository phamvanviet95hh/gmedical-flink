package com.my_flink_job.dtos;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class ChiTietDienBienBenh {

    @JacksonXmlProperty(localName = "MA_LK")
    private String maLk;

    @JacksonXmlProperty(localName = "STT")
    private String stt;

    @JacksonXmlProperty(localName = "DIEN_BIEN_LS")
    private String dienBienLs;

    @JacksonXmlProperty(localName = "GIAI_DOAN_BENH")
    private String giaiDoanBenh;

    @JacksonXmlProperty(localName = "HOI_CHAN")
    private String hoiChan;

    @JacksonXmlProperty(localName = "PHAU_THUAT")
    private String phauThuat;

    @JacksonXmlProperty(localName = "THOI_DIEM_DBLS")
    private String thoiDiemDbls;

    @JacksonXmlProperty(localName = "NGUOI_THUC_HIEN")
    private String nguoiThucHien;

    @JacksonXmlProperty(localName = "DU_PHONG")
    private String duPhong;

}
