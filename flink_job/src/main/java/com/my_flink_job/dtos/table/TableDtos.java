package com.my_flink_job.dtos.table;

public class TableDtos {

    public static String admissionCheckin = "CREATE TABLE IF NOT EXISTS db_3179.admision_checkin ( id String, maBn String, maLoaiRv String," +
            " ketQuaDtri String , maLk String, lyDoVv String , lyDoVnt String , canNang String, gtTheTu String," +
            " gtTheDen String , duPhong String, maNoiDen String, maNoiDi String, ngayVao String, " +
            " ngayRa String, maTheBhyt String, maLyDoVnt String, maHsba String, ngayVaoNoiTru String, " +
            " stt String, maCskb String, maTaiNan String, namNamLienTuc String, maDkbd String , ngayMienCct String, " +
            " maDoituongKcb String, createdAt String, updatedAt String, createdBy String, updatedBy String, patient_id STRING, PRIMARY KEY (id) NOT ENFORCED) " +
            " PARTITIONED BY (maCskb, ngayVao) WITH (\n" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'maCskb, ngayVao, id, patient_id, maLk', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";
    public static String admisionMed ="CREATE TABLE IF NOT EXISTS db_3179.admision_med(" +
            " uuid String, createdAt String, updatedAt String, stt STRING, maThuoc STRING, maPpCheBien STRING" +
            ", maCskcbThuoc STRING, maNhom STRING, tenThuoc STRING, donViTinh STRING, hamLuong STRING , duongDung STRING , dangBaoChe STRING" +
            ", lieuDung STRING , cachDung STRING, soDangKy STRING, ttThau STRING, phamVi STRING, tyleTtBh STRING" +
            ", soLuong STRING, donGia STRING, thanhTienBv STRING, thanhTienBh STRING, tNguonKhacNsnn STRING, tNguonKhacVtnn STRING, tNguonKhacVttn STRING, tNguonKhacCl STRING" +
            ", tNguonKhac STRING, mucHuong STRING, tBntt STRING, tBncct STRING, tBhtt STRING, maKhoa STRING, maBacSi STRING, maDichVu STRING" +
            ", ngayYl STRING, ngayThYl STRING, maPttt STRING, nguonCtra STRING," +
            " vetThuongTp STRING, duPhong STRING, admision_checkin_uuid String, maLk STRING, PRIMARY KEY (uuid) NOT ENFORCED) " +
            " PARTITIONED BY (admision_checkin_uuid) WITH (\n" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, uuid, createdAt, maLk', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";
    public static String admisionEquipment = "CREATE TABLE IF NOT EXISTS db_3179.admision_equipment(" +
            " uuid String, createdAt String, updatedAt String, " +
            "donGiaBh String, donGiaBv String, donViTinh String, duPhong String, goiVtyt String, maBacSi String, maBenh String, maBenhYhct String, " +
            "maDichVu String, maGiuong String, maHieuSp String, maKhoa String, maMay String, maNhom String, maPttt String, maPtttQt String, " +
            "maVatTu String, maXangDau String, mucHuong String, ngayKq String, ngayThYl String, ngayYl String, nguoiThucHien String, " +
            "phamVi String, ppVoCam String, soLuong String, stt String, tBhtt String, tBncct String, tBntt String, " +
            "tNguonKhac String, tNguonKhacCl String, tNguonKhacNsnn String, tNguonKhacVtnn String, tNguonKhacVttn String, " +
            "tTrantt String, taiSuDung String, tenDichVu String, tenVatTu String, thanhTienBh String, thanhTienBv String, " +
            "ttThau String, tyleTtBh String, tyleTtDv String, vetThuongTp String, viTriThDvkt String, admision_checkin_uuid String" +
            " , PRIMARY KEY (uuid) NOT ENFORCED) " +
            " PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (\n" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, uuid, createdAt', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";
    public static String admisionSubclinical = "CREATE TABLE IF NOT EXISTS db_3179.admision_subclinical(" +
            " uuid String, stt String, maDichVu String, maChiSo String, tenChiSo String, giaTri String, donViDo String, moTa String, ketLuan String" +
            ", ngayKq String, maBsDocKq String, duPhong String, admision_checkin_uuid String, createdAt String, updatedAt String, PRIMARY KEY (uuid) NOT ENFORCED)" +
            "  PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, maDichVu, uuid, createdAt', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";
    public static String admisionClinical = "CREATE TABLE IF NOT EXISTS db_3179.admision_clinical(" +
            " uuid String, maLk String, stt String, dienBienLs String, giaiDoanBenh String, hoiChan String, phauThuat String, thoiDiemDbls String, nguoiThucHien String, " +
            " duPhong String" +
            ", admision_checkin_uuid String, createdAt String, updatedAt String, PRIMARY KEY (uuid) NOT ENFORCED)" +
            "  PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, maLk, uuid, createdAt', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";
    public static String admisionDischarge =
            "CREATE TABLE IF NOT EXISTS db_3179.admision_discharge(" +
                    " uuid String, soLuuTru String, maYte String, maKhoaRv String, ngayVao String, ngayRa String, maDinhChiThai String, nguyennhanDinhchi String, thoigianDinhchi String, tuoiThai String," +
                    " chanDoanRv String, ppDieutri String, ghiChu String, maTtdv String, maBs String, tenBs String, ngayCt String, maCha String, maMe String, maTheTam String," +
                    " hoTenCha String, hoTenMe String, soNgayNghi String, ngoaitruTungay String, ngoaitruDenngay String, duPhong String, admision_checkin_uuid String, " +
                    " createdAt String, updatedAt String, PRIMARY KEY (uuid) NOT ENFORCED" +
                    ") " +
                    "   PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (" +
//                    "  'connector' = 'iceberg',\n" +
                    "  'format-version' = '2', " +
                    "  'write.format.default' = 'parquet', " +
                    "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
                    "  'write.ordering' = 'admision_checkin_uuid, uuid, createdAt', " +
                    "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
                    "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
                    "  'write.metadata.auto-merge.enabled' = 'false', " +
                    "  'write.metadata.delete-after-commit.enabled' = 'true', " +
                    "  'write.metadata.previous-versions-max' = '1' " +
                    ")";

    public static String admisionMedicalRecord ="CREATE TABLE IF NOT EXISTS db_3179.admision_medical_record(" +
            " uuid String, stt String, chanDoanRv String, chanDoanVao String, donVi String, duPhong String, ghiChu String, ketQuaDt String, " +
            " maBenhChinh String, maBenhKt String" +
            ", maBenhYhct String, maLoaiKcb String, maLoaiRv String, maPtttQt String, maTtdv String, namQt String, " +
            " ngayTaiKham String, ngayTtoan String, nguoiGiamHo String" +
            ", ppDieuTri String, qtBenhLy String, soNgayDt String, tBhtt String, tBhttGdv String, tBncct String," +
            " tBntt String, tNguonKhac String, tThuoc String, tTongChiBh String, tTongChiBv String, tVtyt String, " +
            " tomTatKq String, thangQt String, createdAt String, updatedAt String, admision_checkin_uuid String, PRIMARY KEY (uuid) NOT ENFORCED)" +
            "  PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, uuid, createdAt', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";
    public static String admisionBirthCertificate = "CREATE TABLE IF NOT EXISTS db_3179.admision_birth_certificate(" +
            " uuid String, createdAt String, createdBy String, updatedAt String," +
            " canNangCon String, duPhong String, ghiChu String, gioiTinhCon String, hoTenCha String," +
            " hoTenCon String, hoTenNnd String, lanSinh String, maBhxhNnd String, maDantocNnd String," +
            " maQuoctich String, maTheNnd String, maTheTam String, maTtdv String," +
            " mahuyenCuTru String, matinhCuTru String, maxaCuTru String," +
            " ngayCt String, ngaySinhCon String, ngaycapCccdNnd String, ngaysinhNnd String," +
            " nguoiDoDe String, nguoiGhiPhieu String, noiCuTruNnd String, noiSinhCon String," +
            " noicapCccdNnd String, quyenSo String, sinhconDuoi32tuan String, sinhconPhauthuat String," +
            " so String, soCccdNnd String, soCon String, soConSong String, stt String, tinhTrangCon String," +
            " admision_checkin_uuid String," +
            " PRIMARY KEY (uuid) NOT ENFORCED)" +
            "  PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, uuid, createdAt', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";

    public static String admisionReferral = "CREATE TABLE IF NOT EXISTS db_3179.admision_referral(" +
            " uuid String, createdAt String, createdBy String, updatedAt String, chanDoanRv String, chucdanhNguoiHt String, dauHieuLs String, diaChi String, duPhong String, giayChuyenTuyen String, gioiTinh String" +
            ", gtTheDen String, hoTen String, hotenNguoiHt String, huongDieuTri String, maBacSi String, maBenhChinh String, maBenhKt String, maBenhYhct String, maCskcb String" +
            ", maDantoc String, maLoaiRv String, maLydoCt String, maNgheNghiep String, maNoiDen String, maNoiDi String, maQuoctich String, maTheBhyt String, maTtdv String" +
            ", ngayRa String, ngaySinh String, ngayVao String, ngayVaoNoiTru String, phuongtienVc String, ppDieuTri String, qtBenhly String, soChuyentuyen String, soHoso String" +
            ", stt String, tomtatKq String, admision_checkin_uuid String, PRIMARY KEY (uuid) NOT ENFORCED)" +
            "  PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, uuid, createdAt', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";

    public static String admissionMedicalExam = "CREATE TABLE IF NOT EXISTS db_3179.admission_medical_exam(" +
            " uuid String, createdAt String, createdBy String, updatedAt String," +
            " nguoiChuTri String, chucVu String, ngayHop String, hoTen String, ngaySinh String, soCccd String," +
            " ngayCapCccd String, noiCapCccd String, diaChi String," +
            " maTinhCuTru String, maHuyenCuTru String, maXaCuTru String, maBhxh String, maTheBhyt String," +
            " ngheNghiep String, dienThoai String, maDoiTuong String, khamGiamDinh String, soBienBan String," +
            " tyleTtctCu String, dangHuongCheDo String, ngayChungTu String, soGiayGioiThieu String, ngayDeNghi String," +
            " maDonVi String, gioiThieuCua String, ketQuaKham String, soVanBanCanCu String, tyleTtctMoi String," +
            " tongTyleTtct String, dangKhuyettat String, mucDoKhuyettat String, deNghi String, duocXacDinh String," +
            " duPhong String, admision_checkin_uuid String," +
            " PRIMARY KEY (uuid) NOT ENFORCED) " +
            "  PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, uuid, createdAt', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";

    public static String admisionBenefitLeave = "CREATE TABLE IF NOT EXISTS db_3179.admision_benefit_leave(" +
            " uuid String, createdAt String, createdBy String, updatedAt String," +
            " maLk String, soCt String, soSeri String, soKcb String, donVi String," +
            " maBhxh String, maTheBhyt String, chanDoanRv String, ppDieuTri String," +
            " maDinhChiThai String, nguyennhanDinhchi String, tuoiThai String, soNgayNghi String," +
            " tuNgay String, denNgay String, hoTenCha String, hoTenMe String," +
            " maTtdv String, maBs String, ngayCt String, maTheTam String, mauSo String," +
            " duPhong String, admision_checkin_uuid String," +
            " PRIMARY KEY (uuid) NOT ENFORCED)" +
            "   PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, maLk, uuid, createdAt', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";


    public static String admisionAppointment = "CREATE TABLE IF NOT EXISTS db_3179.admision_appointment(" +
            " uuid String, createdAt String, createdBy String, updatedAt String, chanDoanRv String, diaChi String, duPhong String, gioiTinh String" +
            ", gtTheDen String, hoTen String, maBacSi String, maBenhChinh String, maBenhKt String, maBenhYhct String, maCskcb String, maDoiTuongKcb String" +
            ", maTheBhyt String, maTtdv String, ngayCt String, ngayHenKl String, ngayRa String, ngaySinh String, ngayVao String, ngayVaoNoiTru String" +
            ", soGiayHenKl String, stt String, admision_checkin_uuid String, PRIMARY KEY (uuid) NOT ENFORCED)" +
            "  PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, uuid, createdAt', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";


    public static String admissionMaternityLeave = "CREATE TABLE IF NOT EXISTS db_3179.admission_maternity_leave(" +
            " uuid String, createdAt String, createdBy String, updatedAt String," +
            " maLk String, soSeri String, soCt String, soNgay String, donVi String," +
            " chanDoanRv String, tuNgay String, denNgay String, maTtdv String," +
            " tenBs String, maBs String, ngayCt String, duPhong String, admision_checkin_uuid String," +
            " PRIMARY KEY (uuid) NOT ENFORCED)" +
            "  PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, uuid, createdAt, maLk', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";

    public static String admissionTuberculosis = "CREATE TABLE IF NOT EXISTS db_3179.admission_tuberculosis(" +
            " uuid String, maLk String, createdAt String, createdBy String, updatedAt String, stt String, maBn String, hoTen String, soCccd String" +
            ", phanLoaiLaoViTri String, phanLoaiLaoTs String, phanLoaiLaoHiv String, phanLoaiLaoVk String, phanLoaiLaoKt String, loaiDtriLao String" +
            ", ngayBdDtriLao String, phacDoDtriLao String, ngayKtDtriLao String, ketQuaDtriLao String, maCskcb String, ngayKdHiv String, bddtArv String" +
            ", ngayBatDauDtCtx String, duPhong String, admision_checkin_uuid String, PRIMARY KEY (uuid) NOT ENFORCED)" +
            "  PARTITIONED BY (admision_checkin_uuid, createdAt) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'admision_checkin_uuid, uuid, createdAt, maLk', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";


    public static String patient ="CREATE TABLE IF NOT EXISTS db_3179.patient(" +
            " uuid String, diaChi String, dienThoai String, gioiTinh String, hoTen String, hoTenCha String, hoTenMe String, maDanToc String, maNgheNghiep String" +
            ", maQuocTich String, maHuyenCuTru String, maTinhCuTru String, maXaCuTru String, ngaySinh String, nhomMau String, soCccd String, stt String," +
            " createdAt String, updatedAt String, PRIMARY KEY (soCccd, dienThoai) NOT ENFORCED)" +
            "   PARTITIONED BY (createdAt) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'soCccd, uuid, createdAt', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";

    public static String processed_files ="CREATE TABLE IF NOT EXISTS db_3179.processed_files(" +
            " uuid String, file_name String, unit_name String, directory String, " +
            " date_of_receipt_of_file String, processed_at String, etl_status String, " +
            " gmed_status String, ma_lk String, " +
            " created_at String, updated_at String, PRIMARY KEY (uuid) NOT ENFORCED)" +
            "  PARTITIONED BY (created_at) WITH (" +
//            "  'connector' = 'iceberg',\n" +
            "  'format-version' = '2', " +
            "  'write.format.default' = 'parquet', " +
            "  'write.parquet.compression-codec' = 'snappy', " + // đổi sang snappy
            "  'write.ordering' = 'file_name, uuid, createdAt, unit_name, ma_lk', " +
            "  'write.target-file-size-bytes' = '134217728', " + // file khoảng 128MB
            "  'write.parquet.block-size-bytes' = '134217728', " + // block 128MB để ổn định bộ nhớ
            "  'write.metadata.auto-merge.enabled' = 'false', " +
            "  'write.metadata.delete-after-commit.enabled' = 'true', " +
            "  'write.metadata.previous-versions-max' = '1' " +
            ")";

}
