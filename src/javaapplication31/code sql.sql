-- Bảng 1: SinhVien (Danh sách sinh viên)
CREATE TABLE SinhVien (
    MaSV VARCHAR(10) PRIMARY KEY,       -- Khóa chính
    HoTen NVARCHAR(50) NOT NULL,
    NgaySinh DATE,
    Lop VARCHAR(10)
);

-- Bảng 2: MonHoc (Lưu danh sách môn học)
CREATE TABLE MonHoc (
    MaMH VARCHAR(10) PRIMARY KEY,       -- Khóa chính
    TenMH NVARCHAR(50) NOT NULL,
    SoTinChi INT
);

-- Bảng 3: Diem (Bảng trung gian tạo quan hệ Ràng buộc)
CREATE TABLE Diem (
    MaSV VARCHAR(10),
    MaMH VARCHAR(10),
    DiemSo FLOAT,
    PRIMARY KEY (MaSV, MaMH),           -- Khóa chính kết hợp từ cả 2 mã
    FOREIGN KEY (MaSV) REFERENCES SinhVien(MaSV), -- Khóa ngoại ràng buộc tới bảng SinhVien
    FOREIGN KEY (MaMH) REFERENCES MonHoc(MaMH)   -- Khóa ngoại ràng buộc tới bảng MonHoc
);







-- Lệnh xem bảng Sinh Viên
SELECT * FROM SinhVien;

-- Lệnh xem bảng Môn Học
SELECT * FROM MonHoc;

-- Lệnh xem bảng Điểm
SELECT * FROM Diem;
