package com.my_flink_job.dtos.table;

public class TableDtos {

    public static String admissionCheckin = "CREATE TABLE IF NOT EXISTS db_3179.admision_checkin ( id String, maBn String, maLoaiRv String," +
            " ketQuaDtri String , maLk String, lyDoVv String , lyDoVnt String , canNang String, gtTheTu String," +
            " gtTheDen String , duPhong String, maNoiDen String, maNoiDi String, ngayVao String, " +
            " ngayRa String, maTheBhyt String, maLyDoVnt String, maHsba String, ngayVaoNoiTru String, " +
            " stt String, maCskb String, maTaiNan String, namNamLienTuc String, maDkbd String , ngayMienCct String, " +
            " maDoituongKcb String, createdAt String, updatedAt String, createdBy String, updatedBy String, patient_id STRING, PRIMARY KEY (maLk) NOT ENFORCED) " +
            "  WITH (\n" +
            "  'write.metadata.delete-after-commit.enabled' = 'true',\n" +
            "  'write.metadata.previous-versions-max' = '1',\n" +
            "  'write.metadata.auto-merge.enabled' = 'false',\n" +
            "  'write.parquet.compression-codec' = 'uncompressed',\n" +
            "  'format-version' = '2',\n" +
            "  'write.format.default' = 'parquet', 'connector' = 'print'\n" +
            ")";
    public static String admisionMed ="CREATE TABLE IF NOT EXISTS db_3179.admision_med(" +
            " createdAt String, updatedAt String, stt STRING, maThuoc STRING, maPpCheBien STRING" +
            ", maCskcbThuoc STRING, maNhom STRING, tenThuoc STRING, donViTinh STRING, hamLuong STRING , duongDung STRING , dangBaoChe STRING" +
            ", lieuDung STRING , cachDung STRING, soDangKy STRING, ttThau STRING, phamVi STRING, tyleTtBh STRING" +
            ", soLuong STRING, donGia STRING, thanhTienBv STRING, thanhTienBh STRING, tNguonKhacNsnn STRING, tNguonKhacVtnn STRING, tNguonKhacVttn STRING, tNguonKhacCl STRING" +
            ", tNguonKhac STRING, mucHuong STRING, tBntt STRING, tBncct STRING, tBhtt STRING, maKhoa STRING, maBacSi STRING, maDichVu STRING" +
            ", ngayYl STRING, ngayThYl STRING, maPttt STRING, nguonCtra STRING," +
            " vetThuongTp STRING, duPhong STRING, admision_checkin_uuid String, maLk STRING) WITH (\n" +
            "  'write.metadata.delete-after-commit.enabled' = 'true',\n" +
            "  'write.metadata.previous-versions-max' = '1',\n" +
            "  'write.metadata.auto-merge.enabled' = 'false',\n" +
            "  'write.parquet.compression-codec' = 'uncompressed',\n" +
            "  'format-version' = '2',\n" +
            "  'write.format.default' = 'parquet'\n" +
            ")";
    public static String admisionEquipment ="CREATE TABLE IF NOT EXISTS db_3179.admision_equipment(" +
            " maLk String, stt String, maDichVu String, maPtttQt String, maVatTu String, maNhom String, goiVtyt String, tenVatTu String, tenDichVu String," +
            " maXangDau String, donViTinh String, phamVi String, soLuong String, donGiaBv String, donGiaBh String, ttThau String, tyleTtDv String, tyleTtBh String," +
            " thanhTienBv String, thanhTienBh String, tTrantt String, mucHuong String, tNguonKhacNsnn String, tNguonKhacVtnn String, tNguonKhacVttn String, tNguonKhacCl String, tNguonKhac String," +
            " tBntt String, tBncct String, tBhtt String, maKhoa String, maGiuong String, maBacSi String, nguoiThucHien String, maBenh String, maBenhYhct String," +
            " ngayYl String, ngayThYl String, ngayKq String, maPttt String, vetThuongTp String, ppVoCam String, viTriThDvkt String, maMay String, maHieuSp String," +
            " taiSuDung String, duPhong String, PRIMARY KEY (maLk) NOT ENFORCED ) WITH (\n" +
            "  'write.metadata.delete-after-commit.enabled' = 'true',\n" +
            "  'write.metadata.previous-versions-max' = '1',\n" +
            "  'write.metadata.auto-merge.enabled' = 'false',\n" +
            "  'write.parquet.compression-codec' = 'uncompressed',\n" +
            "  'format-version' = '2',\n" +
            "  'write.format.default' = 'parquet'\n" +
            ")";
    public static String admisionSubclinical ="CREATE TABLE IF NOT EXISTS db_3179.admision_subclinical(" +
            " maLk String, stt String, maDichVu String, maDichSo String, tenChiSo String, giaTri String, donViDo String, moTa String, ketLuan String" +
            ", ngayKq String, maBsDocKq String, duPhong String, PRIMARY KEY (maLk) NOT ENFORCED)" +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";
    public static String admisionClinical ="CREATE TABLE IF NOT EXISTS db_3179.admision_clinical(" +
            " maLk String, stt String, dienBienLs String, giaiDoanBenh String, hoiChan String, phauThuat String, thoiDiemDbls String, nguoiThucHien String, duPhong String" +
            " , PRIMARY KEY (maLk) NOT ENFORCED)" +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";
    public static String admisionDischarge ="CREATE TABLE IF NOT EXISTS db_3179.admision_discharge(" +
            " maLk String, soLuuTru String, maYte String, maKhoaRv String, ngayVao String, ngayRa String, maDinhChiThai String, nguyenNhanDinhChi String, thoiGianDinhChi String, tuoiThai String" +
            ", chanDoanRv String, ppDieuTri String, ghiChu String, maTtdv String, maBs String, tenBs String, ngayCt String, maCha String, maMe String, maTheTam String" +
            ", hoTenCha String, hoTenMe String, soNgayNghi String, ngoaiTruTuNgay String, ngoaiTruDenNgay String, duPhong String, PRIMARY KEY (maLk) NOT ENFORCED)" +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";
    public static String admisionMedicalRecord ="CREATE TABLE IF NOT EXISTS db_3179.admision_medical_record(" +
            " stt String, chanDoanRv String, chanDoanVao String, donVi String, duPhong String, ghiChu String, ketQuaDt String, " +
            " maBenhChinh String, maBenhKt String" +
            ", maBenhYhct String, maLoaiKcb String, maLoaiRv String, maPtttQt String, maTtdv String, namQt String, " +
            " ngayTaiKham String, ngayTtoan String, nguoiGiamHo String" +
            ", ppDieuTri String, qtBenhLy String, soNgayDt String, tBhtt String, tBhttGdv String, tBncct String," +
            " tBntt String, tNguonKhac String, tThuoc String, tTongChiBh String, tTongChiBv String, tVtyt String, " +
            " tomTatKq String, thangQt String, createdAt String, updatedAt String, admision_checkin_uuid String, PRIMARY KEY (stt) NOT ENFORCED)" +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";
    public static String admisionBirthCertificate ="CREATE TABLE IF NOT EXISTS db_3179.admision_birth_certificate(" +
            " maLk String, maBhxhNnd String, maTheNnd String, hoTenNnd String, ngaySinhNnd String, maDanTocNnd String, soCccdNnd String, ngayCapCccdNnd String" +
            ", noiCapCccdNnd String, noiCuTruNnd String, maQuocTich String, maTinhCuTru String, maHuyenCuTru String, maXaCuTru String, hoTenCha String, maTheTam String" +
            ", hoTenCon String, gioiTinhCon String, soCon String, lanSinh String, soConSong String, canNangCon String, ngaySinhCon String, noiSinhCon String" +
            ", tinhTrangCon String, sinhConPhauThuat String, sinhConDuoi32Tuan String, ghiChu String, nguoiDoDe String, nguoiGhiPhieu String, ngayCt String, so String" +
            ", quyenSo String, maTtdv String, duPhong String, PRIMARY KEY (maLk) NOT ENFORCED)" +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";
    public static String admisionReferral ="CREATE TABLE IF NOT EXISTS db_3179.admision_referral(" +
            " maLk String, soHoSo String, soChuyenTuyen String, giayChuyenTuyen String, maCskcb String, maNoiDi String, maNoiDen String, hoTen String" +
            ", ngaySinh String, gioiTinh String, maQuocTich String, maDanToc String, maNgheNghiep String, diaChi String, maTheBhyt String, gtTheDen String" +
            ", ngayVao String, ngayVaoNoiTru String, ngayRa String, dauHieuLs String, chanDoanRv String, qtBenhLy String, tomTatKq String, ppDieuTri String" +
            ", maBenhChinh String, maBenhKt String, maBenhYhct String, tenDichVu String, tenThuoc String, ppDieuTriDuplicate String, maLoaiRv String, maLyDoCt String" +
            ", huongDieuTri String, phuongTienVc String, hoTenNguoiHt String, chucDanhNguoiHt String, maBacSi String, maTtdv String, duPhong String, PRIMARY KEY (maLk) NOT ENFORCED)" +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";
    public static String admissionMedicalExam ="CREATE TABLE IF NOT EXISTS db_3179.admission_medical_exam(" +
            " nguoiChuTri String, chucVu String, ngayHop String, hoTen String, ngaySinh String, soCccd String, ngayCapCccd String, noiCapCccd String" +
            ", diaChi String, maTinhCuTru String, maHuyenCuTru String, maXaCuTru String, maBhxh String, maTheBhyt String, ngheNghiep String, dienThoai String" +
            ", maDoiTuong String, khamGiamDinh String, soBienBan String, tyleTtctCu String, dangHuongCheDo String, ngayChungTu String, soGiayGioiThieu String, ngayDeNghi String" +
            ", maDonVi String, gioiThieuCua String, ketQuaKham String, soVanBanCanCu String, tyleTtctMoi String, tongTyleTtct String, dangKhuyettat String, mucDoKhuyettat String" +
            ", deNghi String, duocXacDinh String, duPhong String, PRIMARY KEY (hoTen) NOT ENFORCED) " +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";
    public static String admisionBenefitLeave ="CREATE TABLE IF NOT EXISTS db_3179.admision_benefit_leave(" +
            " maLk String, soCt String, soSeri String, soKcb String, donVi String, maBhxh String, maTheBhyt String, chanDoanRv String" +
            ", ppDieuTri String, maDinhChiThai String, nguyenNhanDinhChi String, tuoiThai String, soNgayNghi String, tuNgay String, denNgay String, hoTenCha String" +
            ", hoTenMe String, maTtdv String, maBs String, ngayCt String, maTheTam String, mauSo String, duPhong String, PRIMARY KEY (maLk) NOT ENFORCED)" +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";

    public static String admisionAppointment ="CREATE TABLE IF NOT EXISTS db_3179.admision_appointment(" +
            " maLk String, soGiayHenKl String, maCskcb String, hoTen String, ngaySinh String, gioiTinh String, diaChi String, maTheBhyt String, gtTheDen String" +
            ", ngayVao String, ngayVaoNoiTru String, ngayRa String, ngayHenKl String, chanDoanRv String, maBenhChinh String, maBenhKt String, maBenhYhct String, maDoiTuongKcb String" +
            ", maBacSi String, maTtdv String, ngayCt String, duPhong String, PRIMARY KEY (maLk) NOT ENFORCED)" +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";

    public static String admissionMaternityLeave ="CREATE TABLE IF NOT EXISTS db_3179.admission_maternity_leave(" +
            " maLk String, soSeri String, soCt String, soNgay String, donVi String, chanDoanRv String, tuNgay String, denNgay String" +
            ", maTtdv String, tenBs String, maBs String, ngayCt String, duPhong String, PRIMARY KEY (maLk) NOT ENFORCED)" +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";
    public static String admissionTuberculosis ="CREATE TABLE IF NOT EXISTS db_3179.admission_tuberculosis(" +
            " maLk String, stt String, maBn String, hoTen String, soCccd String, phanLoaiLaoViTri String, phanLoaiLaoTs String, phanLoaiLaoHiv String" +
            ", phanLoaiLaoVk String, phanLoaiLaoKt String, loaiDtriLao String, ngayBdDtriLao String, phacDoDtriLao String, ngayKtDtriLao String, ketQuaDtriLao String, maCskcb String" +
            ", ngayKdHiv String, bddtArv String, ngayBatDauDtCtx String, duPhong String, PRIMARY KEY (maLk) NOT ENFORCED)" +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";

    public static String patient ="CREATE TABLE IF NOT EXISTS db_3179.patient(" +
            " uuid String, diaChi String, dienThoai String, gioiTinh String, hoTen String, hoTenCha String, hoTenMe String, maDanToc String, maNgheNghiep String" +
            ", maQuocTich String, maHuyenCuTru String, maTinhCuTru String, maXaCuTru String, ngaySinh String, nhomMau String, soCccd String, stt String," +
            " createdAt String, updatedAt String, PRIMARY KEY (soCccd, dienThoai) NOT ENFORCED)" +
            " WITH ('write.metadata.delete-after-commit.enabled' = 'true'," +
            " 'write.metadata.previous-versions-max' = '1'," +
            " 'write.metadata.auto-merge.enabled' = 'false'," +
            " 'write.parquet.compression-codec' = 'uncompressed'," +
            " 'format-version' = '2'," +
            " 'write.format.default' = 'parquet')";

}
