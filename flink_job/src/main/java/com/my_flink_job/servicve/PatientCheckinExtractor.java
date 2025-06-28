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

public class PatientCheckinExtractor extends RichFlatMapFunction<GiamDinhHs, Tuple6<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>,List<AdmisionEquipment>,List<AdmisionSubclinical>>> {

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
    public void flatMap(GiamDinhHs giamDinhHs, Collector<Tuple6<Patient, AdmissionCheckin, Admision_Medical_Record, List<AdmisionMed>, List<AdmisionEquipment>,List<AdmisionSubclinical>>> collector) throws Exception {
        AdmissionCheckin admissionCheckin =null;
        Admision_Medical_Record admisionMedicalRecord = null;
        Patient patient = null;
        AdmisionMed admisionMed = null;
        AdmisionEquipment admisionEquipment = null;
        List<AdmisionMed> admisionMedList = new ArrayList<>();
        List<AdmisionEquipment> admisionEquipmentList = new ArrayList<>();
        AdmisionSubclinical admisionSubclinical =null;
        List<AdmisionSubclinical> admisionSubclinicalsList = new ArrayList<>();
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
                    break;
                case "XML7":
                    break;
                case "XML8":
                    break;
                case "XML9":
                    break;
                case "XML10":
                    break;
                case "XML11":
                    break;
                case "XML12":
                    break;
                case "XML13":
                    break;
                case "XML14":
                    break;
                case "XML15":
                    break;
            }


        }
        logger1.info("patient : {}",patient);
        logger1.info("admissionCheckin : {}",admissionCheckin);
        collector.collect(Tuple6.of(patient, admissionCheckin, admisionMedicalRecord, admisionMedList, admisionEquipmentList, admisionSubclinicalsList));
    }
    @Override
    public void close() throws Exception {
        if (connection != null) connection.close();
        super.close();
    }


}
