package com.my_flink_job.servicve;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.*;
import com.my_flink_job.dtos.iceberg.*;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.java.tuple.*;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class PatientCheckinExtractor extends RichFlatMapFunction<GiamDinhHs, Tuple15<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
        List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
        AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
        AdmissionAppointment, List<AdmissionTuberculosis>>> {

    XmlMapper xmlMapper = new XmlMapper();
    Logger logger1 = LoggerFactory.getLogger(Xml1Extractor.class);



    private transient Connection connection;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = DriverManager.getConnection(
                "jdbc:clickhouse://10.6.8.29:8123/default", "default", "Gtel@123"
        );
    }

    @Override
    public void flatMap(GiamDinhHs giamDinhHs, Collector<Tuple15<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,
            List<AdmisionSubclinical>, List<AdmisionClinical>, AdmisionDischarge, List<AdmisionBirthCertificate>,
            AdmissionMaternityLeave, AdmissionBenefitLeave, AdmissionMedicalExam, AdmisionReferral,
            AdmissionAppointment, List<AdmissionTuberculosis>>> collector) throws Exception {
        AdmissionCheckin admissionCheckin =null;
        Admision_Medical_Record admisionMedicalRecord = null;
        Patient patient = null;
        AdmisionMed admisionMed = null;
        AdmisionEquipment admisionEquipment = null;
        List<AdmisionMed> admisionMedList = new ArrayList<>();
        List<AdmisionEquipment> admisionEquipmentList = new ArrayList<>();
        AdmisionSubclinical admisionSubclinical =null;
        List<AdmisionSubclinical> admisionSubclinicalsList = new ArrayList<>();
        AdmisionClinical admisionClinical =null;
        List<AdmisionClinical> admisionClinicalsList = new ArrayList<>();
        AdmisionDischarge admisionDischarge = null;
        AdmisionBirthCertificate admisionBirthCertificate = null;
        List<AdmisionBirthCertificate> admisionBirthCertificateList = new ArrayList<>();
        AdmissionMaternityLeave admissionMaternityLeave = null;
        AdmissionBenefitLeave admissionBenefitLeave = null;
        AdmissionMedicalExam admissionMedicalExam = null;
        AdmisionReferral admisionReferral = null;
        AdmissionAppointment admissionAppointment = null;
        AdmissionTuberculosis admissionTuberculosis = null;
        List<AdmissionTuberculosis> admissionTuberculosisList = new ArrayList<>();
        Xml1 xml1 = null;
        Xml2 xml2 = null;
        Xml3 xml3 = null;
        Xml4 xml4 = null;
        Xml5 xml5 = null;
        Xml7 xml7 = null;
        Xml8 xml8 = null;
        Xml9 xml9 = null;
        Xml10 xml10 = null;
        Xml11 xml11 = null;
        Xml12 xml12 = null;
        Xml13 xml13 = null;
        Xml14 xml14 = null;
        Xml15 xml15 = null;
        List<FileHoSo> hoSoList = giamDinhHs.getThongTinHoSo().getDanhSachHoSo().getHoso().getHoSoList();

        for (FileHoSo hoSo : hoSoList) {
            String innerXml = new String(Base64.getDecoder().decode(hoSo.getNoiDungFile()), StandardCharsets.UTF_8);
            String uuid =UUID.randomUUID().toString();
            if ("XMl8".equalsIgnoreCase(hoSo.getLoaiHoSo())) {
                xml8 = xmlMapper.readValue(innerXml, Xml8.class);
            }
            switch (hoSo.getLoaiHoSo()){
                case "XML1":
                    logger1.info("Xml1->>>>>>>>>>>>>>>>>>>>>>>>>>>>>Start");
                    xml1 = xmlMapper.readValue(innerXml, Xml1.class);

                    String soCccd = xml1.getSoCccd();
                    String dienThoai = xml1.getDienThoai();
                    String patientUuid = null;
                    // 1. Kiểm tra tồn tại
                    String query = "SELECT uuid FROM iceberg.patient WHERE soCccd = ? AND dienThoai = ? LIMIT 1";
                    try (PreparedStatement stmt = connection.prepareStatement(query)) {
                        stmt.setString(1, soCccd);
                        stmt.setString(2, dienThoai);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            logger1.info("Đã tồn tại Patient");
                            patientUuid = rs.getString("uuid");
                        } else {
                            logger1.info("Chưa tồn tại Patient");
                            patient = Patient.builder()
                                    .uuid(UUID.randomUUID().toString())
                                    .stt(xml1.getStt())
                                    .diaChi(xml1.getDiaChi())
                                    .dienThoai(xml1.getDienThoai())
                                    .gioiTinh(xml1.getGioiTinh())
                                    .hoTen(xml1.getHoTen())
                                    .hoTenCha(null)
                                    .hoTenMe(null)
                                    .maDanToc(xml1.getMaDanToc())
                                    .maNgheNghiep(xml1.getMaNgheNghiep())
                                    .maQuocTich(xml1.getMaQuocTich())
                                    .maHuyenCuTru(xml1.getMaHuyenCuTru())
                                    .maTinhCuTru(xml1.getMaTinhCuTru())
                                    .maXaCuTru(xml1.getMaXaCuTru())
                                    .ngaySinh(xml1.getNgaySinh())
                                    .nhomMau(xml1.getNhomMau())
                                    .soCccd(xml1.getSoCccd())
                                    .createdAt(LocalDateTime.now().toString())
                                    .updatedAt(LocalDateTime.now().toString())
                                    .build();
                        }
                    }

                    admissionCheckin = AdmissionCheckin.builder()
                            .id(uuid)
                            .maBn(xml1.getMaBn())
                            .canNang(xml1.getCanNang())
                            .maTheBhyt(xml1.getMaTheBhyt())
                            .namNamLienTuc(xml1.getNamNamLienTuc())
                            .maDkbd(xml1.getMaDkbd())
                            .gtTheTu(xml1.getGtTheTu())
                            .gtTheDen(xml1.getGtTheDen())
                            .ngayMienCct(xml1.getNgayMienCct())
                            .lyDoVv(xml1.getLyDoVv())
                            .lyDoVnt(xml1.getLyDoVnt())
                            .maLyDoVnt(xml1.getMaLyDoVnt())
                            .maDoituongKcb(xml1.getMaDoiTuongKcb())
                            .maNoiDi(xml1.getMaNoiDi())
                            .maNoiDen(xml1.getMaNoiDen())
                            .maTaiNan(xml1.getMaTaiNan())
                            .ngayVao(xml1.getNgayVao())
                            .ngayVaoNoiTru(xml1.getNgayVaoNoiTru())
                            .ngayRa(xml1.getNgayRa())
                            .maHsba(xml1.getMaHsba())
                            .duPhong(xml1.getDuPhong())
                            .maLk(xml1.getMaLk())
                            .maLoaiRv(xml1.getMaLoaiRv())
                            .ketQuaDtri(xml1.getKetQuaDt())
                            .createdAt(LocalDateTime.now().toString())
                            .updatedAt(LocalDateTime.now().toString())
                            .createdBy(null)
                            .updatedBy(null)
                            .stt(xml1.getStt())
                            .maCskb(xml1.getMaCskcb())
                            .patient_id(patient == null ? patientUuid : patient.getUuid())
                            .build();


                    admisionMedicalRecord = Admision_Medical_Record.builder()
                            .uuid(UUID.randomUUID().toString())
                            .stt(xml1.getStt())
                            .chanDoanRv(xml1.getChanDoanRv())
                            .chanDoanVao(xml1.getChanDoanVao())
                            .donVi(xml8 != null ? xml8.getDonVi() : null)
                            .duPhong(xml8 != null ? xml8.getDuPhong() : xml1.getDuPhong())
                            .ghiChu(xml1.getGhiChu())
                            .ketQuaDt(xml1.getKetQuaDt())
                            .maBenhChinh(xml1.getMaBenhChinh())
                            .maBenhKt(xml1.getMaBenhKt())
                            .maBenhYhct(xml1.getMaBenhYhct())
                            .maLoaiKcb(xml1.getMaLoaiKcb())
                            .maLoaiRv(xml1.getMaLoaiRv())
                            .maPtttQt(xml1.getMaPtttQt())
                            .maTtdv(xml1.getMaTtdv())
                            .namQt(xml1.getNamQt())
                            .ngayTaiKham(xml1.getNgayTaiKham())
                            .ngayTtoan(xml1.getNgayTtoan())
                            .nguoiGiamHo(xml8 != null ? xml8.getNguoiGiamHo() : null)
                            .ppDieuTri(xml1.getPpDieuTri())
                            .qtBenhLy(xml8 != null ? xml8.getQtBenhLy() : null)
                            .soNgayDt(xml1.getSoNgayDt())
                            .tBhtt(xml1.getTBhtt())
                            .tBhttGdv(xml1.getTBhttGdv())
                            .tBncct(xml1.getTBncct())
                            .tBntt(xml1.getTBntt())
                            .tNguonKhac(xml1.getTNguonKhac())
                            .tThuoc(xml1.getTThuoc())
                            .tTongChiBh(xml1.getTTongChiBh())
                            .tTongChiBv(xml1.getTTongChiBv())
                            .tVtyt(xml1.getTVtyt())
                            .tomTatKq(xml8 != null ? xml8.getTomTatKq() : null)
                            .thangQt(xml1.getThangQt())
                            .createdAt(LocalDateTime.now().toString())
                            .updatedAt(LocalDateTime.now().toString())
                            .admision_checkin_uuid(admissionCheckin.getId())
                            .build();
                    break;
                case "XML2":
                    xml2 = xmlMapper.readValue(innerXml, Xml2.class);
                    if (xml2 != null
                            && xml2.getDanhSachChiTietThuoc() != null
                            && xml2.getDanhSachChiTietThuoc().getChiTietThuoc() != null) {
                        List<ChiTietThuoc> listThuoc = xml2.getDanhSachChiTietThuoc().getChiTietThuoc();

                        if (listThuoc != null) {
                            for (ChiTietThuoc chiTietThuoc : listThuoc) {
                                admisionMed = AdmisionMed.builder()
                                        .uuid(UUID.randomUUID().toString())
                                        .createdAt(LocalDateTime.now().toString())
                                        .updatedAt(LocalDateTime.now().toString())
                                        .stt(chiTietThuoc.getStt())
                                        .maThuoc(chiTietThuoc.getMaThuoc())
                                        .maPpCheBien(chiTietThuoc.getMaPpCheBien())
                                        .maCskcbThuoc(chiTietThuoc.getMaCskcbThuoc())
                                        .maNhom(chiTietThuoc.getMaNhom())
                                        .tenThuoc(chiTietThuoc.getTenThuoc())
                                        .donViTinh(chiTietThuoc.getDonViTinh())
                                        .hamLuong(chiTietThuoc.getHamLuong())
                                        .duongDung(chiTietThuoc.getDuongDung())
                                        .dangBaoChe(chiTietThuoc.getDangBaoChe())
                                        .lieuDung(chiTietThuoc.getLieuDung())
                                        .cachDung(chiTietThuoc.getCachDung())
                                        .soDangKy(chiTietThuoc.getSoDangKy())
                                        .ttThau(chiTietThuoc.getTtThau())
                                        .phamVi(chiTietThuoc.getPhamVi())
                                        .tyleTtBh(chiTietThuoc.getTyleTtBh())
                                        .soLuong(chiTietThuoc.getSoLuong())
                                        .donGia(chiTietThuoc.getDonGia())
                                        .thanhTienBv(chiTietThuoc.getThanhTienBv())
                                        .soLuong(chiTietThuoc.getSoLuong())
                                        .donGia(chiTietThuoc.getDonGia())
                                        .thanhTienBh(chiTietThuoc.getTyleTtBh())
                                        .tNguonKhacNsnn(chiTietThuoc.getTNguonKhacNsnn())
                                        .tNguonKhacVtnn(chiTietThuoc.getTNguonKhacVtnn())
                                        .tNguonKhacVttn(chiTietThuoc.getTNguonKhacVttn())
                                        .tNguonKhacCl(chiTietThuoc.getTNguonKhacCl())
                                        .tNguonKhac(chiTietThuoc.getTNguonKhac())
                                        .mucHuong(chiTietThuoc.getMucHuong())
                                        .tBntt(chiTietThuoc.getTBntt())
                                        .tBhtt(chiTietThuoc.getTBhtt())
                                        .tBncct(chiTietThuoc.getTBncct())
                                        .maKhoa(chiTietThuoc.getMaKhoa())
                                        .maBacSi(chiTietThuoc.getMaBacSi())
                                        .maDichVu(chiTietThuoc.getMaDichVu())
                                        .ngayYl(chiTietThuoc.getNgayYl())
                                        .ngayThYl(chiTietThuoc.getNgayThYl())
                                        .nguonCtra(chiTietThuoc.getNguonCtra())
                                        .vetThuongTp(chiTietThuoc.getVetThuongTp())
                                        .duPhong(chiTietThuoc.getDuPhong())
                                        .admision_checkin_uuid(admissionCheckin.getId())
                                        .maLk(chiTietThuoc.getMaLk())
                                        .build();

                                admisionMedList.add(admisionMed);
                            }
                        }
                    }
                    break;
                case "XML3":
                    xml3 = xmlMapper.readValue(innerXml, Xml3.class);
                    if (xml3 != null
                            && xml3.getDsachChiTietDvkt() != null
                            && xml3.getDsachChiTietDvkt().getChiTietDvkt() != null) {
                        List<ChiTietDvkt> tietDvktList = xml3.getDsachChiTietDvkt().getChiTietDvkt();

                        if (tietDvktList != null) {
                            for (ChiTietDvkt chiTietDvkt : tietDvktList) {
                                admisionEquipment = AdmisionEquipment.builder()
                                        .uuid(UUID.randomUUID().toString())
                                        .createdAt(LocalDateTime.now().toString())
                                        .updatedAt(LocalDateTime.now().toString())
                                        .donGiaBh(chiTietDvkt.getDonGiaBh())
                                        .donGiaBv(chiTietDvkt.getDonGiaBv())
                                        .donViTinh(chiTietDvkt.getDonViTinh())
                                        .duPhong(chiTietDvkt.getDuPhong())
                                        .goiVtyt(chiTietDvkt.getGoiVtyt())
                                        .maBacSi(chiTietDvkt.getMaBacSi())
                                        .maBenh(chiTietDvkt.getMaBenh())
                                        .maBenhYhct(chiTietDvkt.getMaBenhYhct())
                                        .maDichVu(chiTietDvkt.getMaDichVu())
                                        .maGiuong(chiTietDvkt.getMaGiuong())
                                        .maHieuSp(chiTietDvkt.getMaHieuSp())
                                        .maKhoa(chiTietDvkt.getMaKhoa())
                                        .maMay(chiTietDvkt.getMaMay())
                                        .maNhom(chiTietDvkt.getMaNhom())
                                        .maPttt(chiTietDvkt.getMaPttt())
                                        .maPtttQt(chiTietDvkt.getMaPtttQt())
                                        .maVatTu(chiTietDvkt.getMaVatTu())
                                        .maXangDau(chiTietDvkt.getMaXangDau())
                                        .mucHuong(chiTietDvkt.getMucHuong())
                                        .ngayKq(chiTietDvkt.getNgayKq())
                                        .ngayThYl(chiTietDvkt.getNgayThYl())
                                        .ngayYl(chiTietDvkt.getNgayYl())
                                        .nguoiThucHien(chiTietDvkt.getNguoiThucHien())
                                        .phamVi(chiTietDvkt.getPhamVi())
                                        .ppVoCam(chiTietDvkt.getPpVoCam())
                                        .soLuong(chiTietDvkt.getSoLuong())
                                        .stt(chiTietDvkt.getStt())
                                        .tBhtt(chiTietDvkt.getTBhtt())
                                        .tBncct(chiTietDvkt.getTBncct())
                                        .tBntt(chiTietDvkt.getTBntt())
                                        .tNguonKhac(chiTietDvkt.getTNguonKhac())
                                        .tNguonKhacCl(chiTietDvkt.getTNguonKhacCl())
                                        .tNguonKhacNsnn(chiTietDvkt.getTNguonKhacNsnn())
                                        .tNguonKhacVtnn(chiTietDvkt.getTNguonKhacVtnn())
                                        .tNguonKhacVttn(chiTietDvkt.getTNguonKhacVttn())
                                        .tTrantt(chiTietDvkt.getTTrantt())
                                        .taiSuDung(chiTietDvkt.getTaiSuDung())
                                        .tenDichVu(chiTietDvkt.getTenDichVu())
                                        .tenVatTu(chiTietDvkt.getTenVatTu())
                                        .thanhTienBh(chiTietDvkt.getThanhTienBh())
                                        .thanhTienBv(chiTietDvkt.getThanhTienBv())
                                        .ttThau(chiTietDvkt.getTtThau())
                                        .tyleTtBh(chiTietDvkt.getTyleTtBh())
                                        .tyleTtDv(chiTietDvkt.getTyleTtDv())
                                        .vetThuongTp(chiTietDvkt.getVetThuongTp())
                                        .viTriThDvkt(chiTietDvkt.getViTriThDvkt())
                                        .admision_checkin_uuid(admissionCheckin.getId())
                                        .build();

                                admisionEquipmentList.add(admisionEquipment);
                            }
                        }
                    }
                    break;
                case "XML4":
                    xml4 = xmlMapper.readValue(innerXml, Xml4.class);
                    if (xml4 != null
                            && xml4.getDanhSachChiTietCls() != null
                            && xml4.getDanhSachChiTietCls().getChiTietCls() != null) {
                        List<ChiTietCls> chiTietClsList = xml4.getDanhSachChiTietCls().getChiTietCls();

                        if (chiTietClsList != null) {
                            for (ChiTietCls chiTietDvkt : chiTietClsList) {
                                admisionSubclinical = AdmisionSubclinical.builder()
                                        .uuid(UUID.randomUUID().toString())
                                        .stt(chiTietDvkt.getStt())
                                        .maDichVu(chiTietDvkt.getMaDichVu())
                                        .maChiSo(chiTietDvkt.getMaChiSo())
                                        .tenChiSo(chiTietDvkt.getTenChiSo())
                                        .giaTri(chiTietDvkt.getGiaTri())
                                        .donViDo(chiTietDvkt.getDonViDo())
                                        .moTa(chiTietDvkt.getMoTa())
                                        .ketLuan(chiTietDvkt.getKetLuan())
                                        .ngayKq(chiTietDvkt.getNgayKq())
                                        .maBsDocKq(chiTietDvkt.getMaBsDocKq())
                                        .duPhong(chiTietDvkt.getDuPhong())
                                        .admision_checkin_uuid(admissionCheckin.getId())
                                        .build();
                                admisionSubclinicalsList.add(admisionSubclinical);

                            }
                        }
                    }
                    break;
                case "XML5":
                    xml5 = xmlMapper.readValue(innerXml, Xml5.class);
                    if (xml5 != null
                            && xml5.getDsachChiTietDienBienBenh() != null
                            && xml5.getDsachChiTietDienBienBenh().getChiTietDienBienBenhList() != null) {
                        List<ChiTietDienBienBenh> chiTietDienBienBenhList = xml5.getDsachChiTietDienBienBenh().getChiTietDienBienBenhList();

                        if (chiTietDienBienBenhList != null) {
                            for (ChiTietDienBienBenh chiTietDienBienBenh : chiTietDienBienBenhList) {
                                admisionClinical = AdmisionClinical.builder()
                                        .uuid(UUID.randomUUID().toString())
                                        .maLk(chiTietDienBienBenh.getMaLk())
                                        .createdAt(LocalDateTime.now().toString())
                                        .createdBy("system")
                                        .updatedAt(LocalDateTime.now().toString())
                                        .dienBienLs(chiTietDienBienBenh.getDienBienLs())
                                        .duPhong(chiTietDienBienBenh.getDuPhong())
                                        .giaiDoanBenh(chiTietDienBienBenh.getGiaiDoanBenh())
                                        .hoiChan(chiTietDienBienBenh.getHoiChan())
                                        .nguoiThucHien(chiTietDienBienBenh.getNguoiThucHien())
                                        .phauThuat(chiTietDienBienBenh.getPhauThuat())
                                        .stt(chiTietDienBienBenh.getStt())
                                        .thoiDiemDbls(chiTietDienBienBenh.getThoiDiemDbls())
                                        .admision_checkin_uuid(admissionCheckin.getId())
                                        .build();
                                admisionClinicalsList.add(admisionClinical);
                            }
                        }
                    }
                    break;
                case "XML7":
                    xml7 = xmlMapper.readValue(innerXml, Xml7.class);
                    if (xml7 != null ) {
                        admisionDischarge = AdmisionDischarge.builder()
                                .uuid(UUID.randomUUID().toString())
                                .createdAt(LocalDateTime.now().toString())
                                .createdBy("system")
                                .updatedAt(LocalDateTime.now().toString())
                                .chanDoanRv(xml7.getChanDoanRv())
                                .duPhong(xml7.getDuPhong())
                                .ghiChu(xml7.getGhiChu())
                                .hoTenCha(xml7.getHoTenCha())
                                .hoTenMe(xml7.getHoTenMe())
                                .maBs(xml7.getMaBs())
                                .maCha(xml7.getMaCha())
                                .maDinhChiThai(xml7.getMaDinhChiThai())
                                .maKhoaRv(xml7.getMaKhoaRv())
                                .maMe(xml7.getMaMe())
                                .maTheTam(xml7.getMaTheTam())
                                .maTtdv(xml7.getMaTtdv())
                                .maYte(xml7.getMaYte())
                                .ngayCt(xml7.getNgayCt())
                                .ngayRa(xml7.getNgayRa())
                                .ngayVao(xml7.getNgayVao())
                                .ngoaitruDenngay(xml7.getNgoaiTruDenNgay())
                                .ngoaitruTungay(xml7.getNgoaiTruTuNgay())
                                .nguyennhanDinhchi(xml7.getNguyenNhanDinhChi())
                                .ppDieutri(xml7.getPpDieuTri())
                                .soLuuTru(xml7.getSoLuuTru())
                                .soNgayNghi(xml7.getSoNgayNghi())
                                .stt(null)
                                .tenBs(xml7.getTenBs())
                                .thoigianDinhchi(xml7.getThoiGianDinhChi())
                                .tuoiThai(xml7.getTuoiThai())
                                .admision_checkin_uuid(admissionCheckin.getId())
                                .build();
                    }
                    break;
                case "XML9":
                    xml9 = xmlMapper.readValue(innerXml, Xml9.class);
                    if (xml9 != null
                            && xml9.getDSachGiayChungSinh() != null
                            && xml9.getDSachGiayChungSinh().getDulieuGiayChungSinhs() != null) {
                        List<DulieuGiayChungSinh> dulieuGiayChungSinhList = xml9.getDSachGiayChungSinh().getDulieuGiayChungSinhs();
                        if (dulieuGiayChungSinhList != null) {
                            for (DulieuGiayChungSinh dulieuGiayChungSinh : dulieuGiayChungSinhList) {
                                admisionBirthCertificate = AdmisionBirthCertificate.builder()
                                        .uuid(UUID.randomUUID().toString())
                                        .createdAt(LocalDateTime.now().toString())
                                        .createdBy("system")
                                        .updatedAt(LocalDateTime.now().toString())
                                        .canNangCon(dulieuGiayChungSinh.getCanNangCon())
                                        .duPhong(dulieuGiayChungSinh.getDuPhong())
                                        .ghiChu(dulieuGiayChungSinh.getGhiChu())
                                        .gioiTinhCon(dulieuGiayChungSinh.getGioiTinhCon())
                                        .hoTenCha(dulieuGiayChungSinh.getHoTenCha())
                                        .hoTenCon(dulieuGiayChungSinh.getHoTenCon())
                                        .hoTenNnd(dulieuGiayChungSinh.getHoTenNnd())
                                        .lanSinh(dulieuGiayChungSinh.getLanSinh())
                                        .maBhxhNnd(dulieuGiayChungSinh.getMaBhxhNnd())
                                        .maDantocNnd(dulieuGiayChungSinh.getMaDanTocNnd())
                                        .maQuoctich(dulieuGiayChungSinh.getMaQuocTich())
                                        .maTheNnd(dulieuGiayChungSinh.getMaTheNnd())
                                        .maTheTam(dulieuGiayChungSinh.getMaTheTam())
                                        .maTtdv(dulieuGiayChungSinh.getMaTtdv())
                                        .mahuyenCuTru(dulieuGiayChungSinh.getMaHuyenCuTru())
                                        .matinhCuTru(dulieuGiayChungSinh.getMaTinhCuTru())
                                        .maxaCuTru(dulieuGiayChungSinh.getMaXaCuTru())
                                        .ngayCt(dulieuGiayChungSinh.getNgayCt())
                                        .ngaySinhCon(dulieuGiayChungSinh.getNgaySinhCon())
                                        .ngaycapCccdNnd(dulieuGiayChungSinh.getNgayCapCccdNnd())
                                        .ngaysinhNnd(dulieuGiayChungSinh.getNgaySinhNnd())
                                        .nguoiDoDe(dulieuGiayChungSinh.getNguoiDoDe())
                                        .nguoiGhiPhieu(dulieuGiayChungSinh.getNguoiGhiPhieu())
                                        .noiCuTruNnd(dulieuGiayChungSinh.getNoiCuTruNnd())
                                        .noiSinhCon(dulieuGiayChungSinh.getNoiSinhCon())
                                        .noicapCccdNnd(dulieuGiayChungSinh.getNoiCapCccdNnd())
                                        .quyenSo(dulieuGiayChungSinh.getQuyenSo())
                                        .sinhconDuoi32tuan(dulieuGiayChungSinh.getSinhConDuoi32Tuan())
                                        .sinhconPhauthuat(dulieuGiayChungSinh.getSinhConPhauThuat())
                                        .so(dulieuGiayChungSinh.getSo())
                                        .soCccdNnd(dulieuGiayChungSinh.getSoCccdNnd())
                                        .soCon(dulieuGiayChungSinh.getSoCon())
                                        .soConSong(dulieuGiayChungSinh.getSoConSong())
                                        .stt(null)
                                        .tinhTrangCon(dulieuGiayChungSinh.getTinhTrangCon())
                                        .admision_checkin_uuid(admissionCheckin.getId())
                                        .build();

                                admisionBirthCertificateList.add(admisionBirthCertificate);
                            }
                        }
                    }
                    break;
                case "XML10":
                    xml10 = xmlMapper.readValue(innerXml, Xml10.class);
                    if (xml10 != null) {
                        admissionMaternityLeave = AdmissionMaternityLeave.builder()
                                .uuid(UUID.randomUUID().toString())
                                .createdAt(LocalDateTime.now().toString())
                                .createdBy("system")
                                .updatedAt(LocalDateTime.now().toString())
                                .maLk(xml10.getMaLk())
                                .soSeri(xml10.getSoSeri())
                                .soCt(xml10.getSoCt())
                                .soNgay(xml10.getSoNgay())
                                .donVi(xml10.getDonVi())
                                .chanDoanRv(xml10.getChanDoanRv())
                                .tuNgay(xml10.getTuNgay())
                                .denNgay(xml10.getDenNgay())
                                .maTtdv(xml10.getMaTtdv())
                                .tenBs(xml10.getTenBs())
                                .maBs(xml10.getMaBs())
                                .ngayCt(xml10.getNgayCt())
                                .duPhong(xml10.getDuPhong())
                                .admision_checkin_uuid(admissionCheckin.getId())
                                .build();
                    }
                    break;
                case "XML11":
                    xml11 = xmlMapper.readValue(innerXml, Xml11.class);
                    if (xml11 != null) {
                        admissionBenefitLeave = AdmissionBenefitLeave.builder()
                                .uuid(UUID.randomUUID().toString())
                                .createdAt(LocalDateTime.now().toString())
                                .createdBy("system")
                                .updatedAt(LocalDateTime.now().toString())
                                .chanDoanRv(xml11.getChanDoanRv())
                                .denNgay(xml11.getDenNgay())
                                .donVi(xml11.getDonVi())
                                .duPhong(xml11.getDuPhong())
                                .hoTenCha(xml11.getHoTenCha())
                                .hoTenMe(xml11.getHoTenMe())
                                .maBhxh(xml11.getMaBhxh())
                                .maBs(xml11.getMaBs())
                                .maDinhChiThai(xml11.getMaDinhChiThai())
                                .maTheBhyt(xml11.getMaTheBhyt())
                                .maTheTam(xml11.getMaTheTam())
                                .maTtdv(xml11.getMaTtdv())
                                .mauSo(xml11.getMauSo())
                                .ngayCt(xml11.getNgayCt())
                                .nguyennhanDinhchi(xml11.getNguyenNhanDinhChi())
                                .ppDieutri(xml11.getPpDieuTri())
                                .soCt(xml11.getSoCt())
                                .soKcb(xml11.getSoKcb())
                                .soNgayNghi(xml11.getSoNgayNghi())
                                .soSeri(xml11.getSoSeri())
                                .stt(null)
                                .tuNgay(xml11.getTuNgay())
                                .tuoiThai(xml11.getTuoiThai())
                                .admision_checkin_uuid(admissionCheckin.getId())
                                .build();

                    }
                    break;
                case "XML12":
                    xml12 = xmlMapper.readValue(innerXml, Xml12.class);
                    if (xml12 != null) {
                        admissionMedicalExam = AdmissionMedicalExam.builder()
                                .uuid(UUID.randomUUID().toString())
                                .createdAt(LocalDateTime.now().toString())
                                .createdBy("system")
                                .updatedAt(LocalDateTime.now().toString())
                                .nguoiChuTri(xml12.getNguoiChuTri())
                                .chucVu(xml12.getChucVu())
                                .ngayHop(xml12.getNgayHop())
                                .hoTen(xml12.getHoTen())
                                .ngaySinh(xml12.getNgaySinh())
                                .soCccd(xml12.getSoCccd())
                                .ngayCapCccd(xml12.getNgayCapCccd())
                                .noiCapCccd(xml12.getNoiCapCccd())
                                .diaChi(xml12.getDiaChi())
                                .maTinhCuTru(xml12.getMaTinhCuTru())
                                .maHuyenCuTru(xml12.getMaHuyenCuTru())
                                .maXaCuTru(xml12.getMaXaCuTru())
                                .maBhxh(xml12.getMaBhxh())
                                .maTheBhyt(xml12.getMaTheBhyt())
                                .ngheNghiep(xml12.getNgheNghiep())
                                .dienThoai(xml12.getDienThoai())
                                .maDoiTuong(xml12.getMaDoiTuong())
                                .khamGiamDinh(xml12.getKhamGiamDinh())
                                .soBienBan(xml12.getSoBienBan())
                                .tyleTtctCu(xml12.getTyleTtctCu())
                                .dangHuongCheDo(xml12.getDangHuongCheDo())
                                .ngayChungTu(xml12.getNgayChungTu())
                                .soGiayGioiThieu(xml12.getSoGiayGioiThieu())
                                .ngayDeNghi(xml12.getNgayDeNghi())
                                .maDonVi(xml12.getMaDonVi())
                                .gioiThieuCua(xml12.getGioiThieuCua())
                                .ketQuaKham(xml12.getKetQuaKham())
                                .soVanBanCanCu(xml12.getSoVanBanCanCu())
                                .tyleTtctMoi(xml12.getTyleTtctMoi())
                                .tongTyleTtct(xml12.getTongTyleTtct())
                                .dangKhuyettat(xml12.getDangKhuyettat())
                                .mucDoKhuyettat(xml12.getMucDoKhuyettat())
                                .deNghi(xml12.getDeNghi())
                                .duocXacDinh(xml12.getDuocXacDinh())
                                .duPhong(xml12.getDuPhong())
                                .admision_checkin_uuid(admissionCheckin.getId())
                                .build();

                    }
                    break;
                case "XML13":

                    xml13 = xmlMapper.readValue(innerXml, Xml13.class);
                    if(xml13 != null){
                        admisionReferral = AdmisionReferral.builder()
                                .uuid(UUID.randomUUID().toString())
                                .createdAt(LocalDateTime.now().toString())
                                .createdBy("system")
                                .updatedAt(LocalDateTime.now().toString())
                                .chanDoanRv(xml13.getChanDoanRv())
                                .chucdanhNguoiHt(xml13.getChucDanhNguoiHt())
                                .dauHieuLs(xml13.getDauHieuLs())
                                .diaChi(xml13.getDiaChi())
                                .duPhong(xml13.getDuPhong())
                                .giayChuyenTuyen(xml13.getGiayChuyenTuyen())
                                .gioiTinh(xml13.getGioiTinh())
                                .gtTheDen(xml13.getGtTheDen())
                                .hoTen(xml13.getHoTen())
                                .hotenNguoiHt(xml13.getHoTenNguoiHt())
                                .huongDieuTri(xml13.getHuongDieuTri())
                                .maBacSi(xml13.getMaBacSi())
                                .maBenhChinh(xml13.getMaBenhChinh())
                                .maBenhKt(xml13.getMaBenhKt())
                                .maBenhYhct(xml13.getMaBenhYhct())
                                .maCskcb(xml13.getMaCskcb())
                                .maDantoc(xml13.getMaDanToc())
                                .maLoaiRv(xml13.getMaLoaiRv())
                                .maLydoCt(xml13.getMaLyDoCt())
                                .maNgheNghiep(xml13.getMaNgheNghiep())
                                .maNoiDen(xml13.getMaNoiDen())
                                .maNoiDi(xml13.getMaNoiDi())
                                .maQuoctich(xml13.getMaQuocTich())
                                .maTheBhyt(xml13.getMaTheBhyt())
                                .maTtdv(xml13.getMaTtdv())
                                .ngayRa(xml13.getNgayRa())
                                .ngaySinh(xml13.getNgaySinh())
                                .ngayVao(xml13.getNgayVao())
                                .ngayVaoNoiTru(xml13.getNgayVaoNoiTru())
                                .phuongtienVc(xml13.getPhuongTienVc())
                                .ppDieuTri(xml13.getPpDieuTri())
                                .qtBenhly(xml13.getQtBenhLy())
                                .soChuyentuyen(xml13.getSoChuyenTuyen())
                                .soHoso(xml13.getSoHoSo())
                                .stt(null)
                                .tomtatKq(xml13.getTomTatKq())
                                .admision_checkin_uuid(admissionCheckin.getId())
                                .build();

                    }
                    break;
                case "XML14":
                    xml14 = xmlMapper.readValue(innerXml, Xml14.class);
                    if(xml14 != null){
                        admissionAppointment = AdmissionAppointment.builder()
                                .uuid(UUID.randomUUID().toString())
                                .createdAt(LocalDateTime.now().toString())
                                .createdBy("system")
                                .updatedAt(LocalDateTime.now().toString())
                                .chanDoanRv(xml14.getChanDoanRv())
                                .diaChi(xml14.getDiaChi())
                                .duPhong(xml14.getDuPhong())
                                .gioiTinh(xml14.getGioiTinh())
                                .gtTheDen(xml14.getGtTheDen())
                                .hoTen(xml14.getHoTen())
                                .maBacSi(xml14.getMaBacSi())
                                .maBenhChinh(xml14.getMaBenhChinh())
                                .maBenhKt(xml14.getMaBenhKt())
                                .maBenhYhct(xml14.getMaBenhYhct())
                                .maCskcb(xml14.getMaCskcb())
                                .maDoiTuongKcb(xml14.getMaDoiTuongKcb())
                                .maTheBhyt(xml14.getMaTheBhyt())
                                .maTtdv(xml14.getMaTtdv())
                                .ngayCt(xml14.getNgayCt())
                                .ngayHenKl(xml14.getNgayHenKl())
                                .ngayRa(xml14.getNgayRa())
                                .ngaySinh(xml14.getNgaySinh())
                                .ngayVao(xml14.getNgayVao())
                                .ngayVaoNoiTru(xml14.getNgayVaoNoiTru())
                                .soGiayHenKl(xml14.getSoGiayHenKl())
                                .stt(null)
                                .admision_checkin_uuid(admissionCheckin.getId())
                                .build();

                    }
                    break;
                case "XML15":
                    xml15 = xmlMapper.readValue(innerXml, Xml15.class);
                    if (xml15 != null
                            && xml15.getDsachChiTietDieuTriBenhLao() != null
                            && xml15.getDsachChiTietDieuTriBenhLao().getChiTietDieuTriBenhLaoList() != null) {
                        List<ChiTietDieuTriBenhLao> chiTietDieuTriBenhLaoList = xml15.getDsachChiTietDieuTriBenhLao().getChiTietDieuTriBenhLaoList();

                        if (chiTietDieuTriBenhLaoList != null) {
                            for (ChiTietDieuTriBenhLao chiTietDieuTriBenhLao : chiTietDieuTriBenhLaoList) {
                                admissionTuberculosis = AdmissionTuberculosis.builder()
                                        .uuid(UUID.randomUUID().toString())
                                        .maLk(chiTietDieuTriBenhLao.getMaLk())
                                        .createdAt(LocalDateTime.now().toString())
                                        .createdBy("system")
                                        .updatedAt(LocalDateTime.now().toString())
                                        .stt(chiTietDieuTriBenhLao.getStt())
                                        .maBn(chiTietDieuTriBenhLao.getMaBn())
                                        .hoTen(chiTietDieuTriBenhLao.getHoTen())
                                        .soCccd(chiTietDieuTriBenhLao.getSoCccd())
                                        .phanLoaiLaoViTri(chiTietDieuTriBenhLao.getPhanLoaiLaoViTri())
                                        .phanLoaiLaoTs(chiTietDieuTriBenhLao.getPhanLoaiLaoTs())
                                        .phanLoaiLaoHiv(chiTietDieuTriBenhLao.getPhanLoaiLaoHiv())
                                        .phanLoaiLaoVk(chiTietDieuTriBenhLao.getPhanLoaiLaoVk())
                                        .phanLoaiLaoKt(chiTietDieuTriBenhLao.getPhanLoaiLaoKt())
                                        .loaiDtriLao(chiTietDieuTriBenhLao.getLoaiDtriLao())
                                        .ngayBdDtriLao(chiTietDieuTriBenhLao.getNgayBdDtriLao())
                                        .phacDoDtriLao(chiTietDieuTriBenhLao.getPhacDoDtriLao())
                                        .ngayKtDtriLao(chiTietDieuTriBenhLao.getNgayKtDtriLao())
                                        .ketQuaDtriLao(chiTietDieuTriBenhLao.getKetQuaDtriLao())
                                        .maCskcb(chiTietDieuTriBenhLao.getMaCskcb())
                                        .ngayKdHiv(chiTietDieuTriBenhLao.getNgayKdHiv())
                                        .bddtArv(chiTietDieuTriBenhLao.getBddtArv())
                                        .ngayBatDauDtCtx(chiTietDieuTriBenhLao.getNgayBatDauDtCtx())
                                        .duPhong(chiTietDieuTriBenhLao.getDuPhong())
                                        .admision_checkin_uuid(admissionCheckin.getId())
                                        .build();
                                admissionTuberculosisList.add(admissionTuberculosis);
                            }
                        }
                    }
                    break;
            }


        }
        logger1.info("patient : {}",patient);
        logger1.info("admissionCheckin : {}",admissionCheckin);
        collector.collect(Tuple15.of(patient, admissionCheckin, admisionMedicalRecord, admisionMedList, admisionEquipmentList,
                admisionSubclinicalsList, admisionClinicalsList, admisionDischarge, admisionBirthCertificateList, admissionMaternityLeave,
                admissionBenefitLeave, admissionMedicalExam, admisionReferral, admissionAppointment, admissionTuberculosisList));
    }
    @Override
    public void close() throws Exception {
        if (connection != null) connection.close();
        super.close();
    }


}
