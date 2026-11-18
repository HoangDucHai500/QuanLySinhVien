package Quanlysinhvien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class JavaApplication31 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}

// ==========================================
// 1. DATABASE: KẾT NỐI SQL SERVER (JDBCUtil)
// ==========================================
class JDBCUtil {
    public static Connection getConnection() {
        try {
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=master;encrypt=true;trustServerCertificate=true;";
            String user = "kiet";
            String pass = "2007";
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối SQL: " + e.getMessage());
            return null;
        }
    }

    public static void closeConnection(Connection c) {
        try { if (c != null) c.close(); } catch (Exception e) { e.printStackTrace(); }
    }
}

// ==========================================
// 2. MODEL: 3 ĐỐI TƯỢNG TƯƠNG ỨNG 3 BẢNG SQL
// ==========================================
class SinhVien {
    String maSV, hoTen, ngaySinh, lop;
    public SinhVien(String maSV, String hoTen, String ngaySinh, String lop) {
        this.maSV = maSV; this.hoTen = hoTen; this.ngaySinh = ngaySinh; this.lop = lop;
    }
}

class MonHoc {
    String maMH, tenMH;
    int soTinChi;
    public MonHoc(String maMH, String tenMH, int soTinChi) {
        this.maMH = maMH; this.tenMH = tenMH; this.soTinChi = soTinChi;
    }
}

class Diem {
    String maSV, maMH;
    float diemSo;
    public Diem(String maSV, String maMH, float diemSo) {
        this.maSV = maSV; this.maMH = maMH; this.diemSo = diemSo;
    }
}

// ==========================================
// 3. DAO: XỬ LÝ SQL CHO CẢ 3 BẢNG
// ==========================================
class SinhVienDAO {
    public static SinhVienDAO getInstance() { return new SinhVienDAO(); }

    public void insert(SinhVien t) throws Exception {
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO SinhVien (MaSV, HoTen, NgaySinh, Lop) VALUES (?, ?, ?, ?)");
        ps.setString(1, t.maSV); ps.setString(2, t.hoTen); ps.setString(3, t.ngaySinh); ps.setString(4, t.lop);
        ps.executeUpdate(); JDBCUtil.closeConnection(con);
    }

    public void update(SinhVien t, String maCu) throws Exception {
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE SinhVien SET MaSV=?, HoTen=?, NgaySinh=?, Lop=? WHERE MaSV=?");
        ps.setString(1, t.maSV); ps.setString(2, t.hoTen); ps.setString(3, t.ngaySinh); ps.setString(4, t.lop); ps.setString(5, maCu);
        ps.executeUpdate(); JDBCUtil.closeConnection(con);
    }

    public void delete(String maSV) throws Exception {
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM SinhVien WHERE MaSV=?");
        ps.setString(1, maSV);
        ps.executeUpdate(); JDBCUtil.closeConnection(con);
    }

    public ArrayList<SinhVien> selectByName(String kw) throws Exception {
        ArrayList<SinhVien> list = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM SinhVien WHERE HoTen LIKE ?");
        ps.setString(1, "%" + kw + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) list.add(new SinhVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
        JDBCUtil.closeConnection(con);
        return list;
    }
}

class MonHocDAO {
    public static MonHocDAO getInstance() { return new MonHocDAO(); }

    public void insert(MonHoc t) throws Exception {
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO MonHoc (MaMH, TenMH, SoTinChi) VALUES (?, ?, ?)");
        ps.setString(1, t.maMH); ps.setString(2, t.tenMH); ps.setInt(3, t.soTinChi);
        ps.executeUpdate(); JDBCUtil.closeConnection(con);
    }

    public void update(MonHoc t, String maCu) throws Exception {
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE MonHoc SET MaMH=?, TenMH=?, SoTinChi=? WHERE MaMH=?");
        ps.setString(1, t.maMH); ps.setString(2, t.tenMH); ps.setInt(3, t.soTinChi); ps.setString(4, maCu);
        ps.executeUpdate(); JDBCUtil.closeConnection(con);
    }

    public void delete(String maMH) throws Exception {
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM MonHoc WHERE MaMH=?");
        ps.setString(1, maMH);
        ps.executeUpdate(); JDBCUtil.closeConnection(con);
    }

    public ArrayList<MonHoc> selectByName(String kw) throws Exception {
        ArrayList<MonHoc> list = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM MonHoc WHERE TenMH LIKE ?");
        ps.setString(1, "%" + kw + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) list.add(new MonHoc(rs.getString(1), rs.getString(2), rs.getInt(3)));
        JDBCUtil.closeConnection(con);
        return list;
    }
}

class DiemDAO {
    public static DiemDAO getInstance() { return new DiemDAO(); }

    public void insert(Diem t) throws Exception {
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO Diem (MaSV, MaMH, DiemSo) VALUES (?, ?, ?)");
        ps.setString(1, t.maSV); ps.setString(2, t.maMH); ps.setFloat(3, t.diemSo);
        ps.executeUpdate(); JDBCUtil.closeConnection(con);
    }

    public void update(Diem t, String maSVCu, String maMHCu) throws Exception {
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("UPDATE Diem SET MaSV=?, MaMH=?, DiemSo=? WHERE MaSV=? AND MaMH=?");
        ps.setString(1, t.maSV); ps.setString(2, t.maMH); ps.setFloat(3, t.diemSo); ps.setString(4, maSVCu); ps.setString(5, maMHCu);
        ps.executeUpdate(); JDBCUtil.closeConnection(con);
    }

    public void delete(String maSV, String maMH) throws Exception {
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM Diem WHERE MaSV=? AND MaMH=?");
        ps.setString(1, maSV); ps.setString(2, maMH);
        ps.executeUpdate(); JDBCUtil.closeConnection(con);
    }

    public ArrayList<Diem> selectByMaSV(String kw) throws Exception {
        ArrayList<Diem> list = new ArrayList<>();
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM Diem WHERE MaSV LIKE ?");
        ps.setString(1, "%" + kw + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) list.add(new Diem(rs.getString(1), rs.getString(2), rs.getFloat(3)));
        JDBCUtil.closeConnection(con);
        return list;
    }
}

// ==========================================
// 4. VIEW 1 & 2: ĐĂNG NHẬP VÀ MENU CHÍNH
// ==========================================
class LoginView extends JFrame {
    JTextField txtUser = new JTextField(15);
    JPasswordField txtPass = new JPasswordField(15);
    JButton btnLogin = new JButton("Đăng nhập");

    public LoginView() {
        setTitle("Đăng Nhập Hệ Thống");
        setSize(340, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("  Tài khoản:")); add(txtUser);
        add(new JLabel("  Mật khẩu:"));  add(txtPass);
        add(new JLabel(""));             add(btnLogin);

        btnLogin.addActionListener(e -> {
            String u = txtUser.getText().trim(), p = new String(txtPass.getPassword()).trim();
            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
                return;
            }
            if (u.equals("admin") && p.equals("123")) {
                new MainMenuView().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai thông tin! Dùng tài khoản: admin / 123");
            }
        });
    }
}

class MainMenuView extends JFrame {
    public MainMenuView() {
        setTitle("Trang Chủ Quản Lý Đào Tạo");
        setSize(520, 180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 50));

        JButton btnSV = new JButton("Quản Lý Sinh Viên");
        JButton btnMH = new JButton("Quản Lý Môn Học");
        JButton btnDiem = new JButton("Quản Lý Điểm");
        JButton btnLogout = new JButton("Đăng Xuất");

        add(btnSV); add(btnMH); add(btnDiem); add(btnLogout);

        btnSV.addActionListener(e -> new SinhVienView().setVisible(true));
        btnMH.addActionListener(e -> new MonHocView().setVisible(true));
        btnDiem.addActionListener(e -> new DiemView().setVisible(true));
        btnLogout.addActionListener(e -> {
            new LoginView().setVisible(true);
            this.dispose();
        });
    }
}

// ==========================================
// 5. VIEW 3: QUẢN LÝ BẢNG SINH VIÊN
// ==========================================
class SinhVienView extends JFrame {
    JTextField txtMa = new JTextField(10), txtTen = new JTextField(15);
    JTextField txtNgaySinh = new JTextField(10), txtLop = new JTextField(10), txtTim = new JTextField(15);
    DefaultTableModel model = new DefaultTableModel(new String[]{"MaSV", "HoTen", "NgaySinh", "Lop"}, 0);
JTable table = new JTable(model);
String maSVCu = "";
public SinhVienView() {
setTitle("Quản Lý Bảng SinhVien");
setSize(720, 450);
setLocationRelativeTo(null);
setLayout(new BorderLayout(10, 10));
JPanel pnlInput = new JPanel(new GridLayout(3, 4, 5, 5));
pnlInput.add(new JLabel("  Mã SV:"));                  pnlInput.add(txtMa);
pnlInput.add(new JLabel("  Họ Tên:"));                 pnlInput.add(txtTen);
pnlInput.add(new JLabel("  Ngày Sinh (YYYY-MM-DD):")); pnlInput.add(txtNgaySinh);
pnlInput.add(new JLabel("  Lớp:"));                    pnlInput.add(txtLop);
pnlInput.add(new JLabel("  Tìm theo tên:"));           pnlInput.add(txtTim);
JButton btnTim = new JButton("Tìm kiếm");
pnlInput.add(btnTim);
add(pnlInput, BorderLayout.NORTH);
add(new JScrollPane(table), BorderLayout.CENTER);
JPanel pnlBtn = new JPanel();
JButton btnThem = new JButton("Thêm"), btnSua = new JButton("Sửa"), btnXoa = new JButton("Xóa"), btnLamMoi = new JButton("Làm mới");
pnlBtn.add(btnThem); pnlBtn.add(btnSua); pnlBtn.add(btnXoa); pnlBtn.add(btnLamMoi);
add(pnlBtn, BorderLayout.SOUTH);
btnThem.addActionListener(e -> xuLy(true));
btnSua.addActionListener(e -> xuLy(false));
btnXoa.addActionListener(e -> xoa());
btnTim.addActionListener(e -> load(txtTim.getText().trim()));
btnLamMoi.addActionListener(e -> lamMoi());
table.getSelectionModel().addListSelectionListener(e -> {
int r = table.getSelectedRow();
if (r >= 0) {
maSVCu = model.getValueAt(r, 0).toString();
txtMa.setText(maSVCu);
txtTen.setText(model.getValueAt(r, 1).toString());
txtNgaySinh.setText(model.getValueAt(r, 2).toString());
txtLop.setText(model.getValueAt(r, 3).toString());
}
});
load("");
}
void lamMoi() {
maSVCu = ""; txtMa.setText(""); txtTen.setText(""); txtNgaySinh.setText(""); txtLop.setText(""); txtTim.setText("");
table.clearSelection(); load("");
}
void load(String kw) {
model.setRowCount(0);
try {
for (SinhVien sv : SinhVienDAO.getInstance().selectByName(kw))
model.addRow(new Object[]{sv.maSV, sv.hoTen, sv.ngaySinh, sv.lop});
} catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
}
void xuLy(boolean isInsert) {
if (txtMa.getText().trim().isEmpty() || txtTen.getText().trim().isEmpty() || txtNgaySinh.getText().trim().isEmpty() || txtLop.getText().trim().isEmpty()) {
JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin Sinh Viên!"); return;
}
try {
SinhVien sv = new SinhVien(txtMa.getText().trim(), txtTen.getText().trim(), txtNgaySinh.getText().trim(), txtLop.getText().trim());
if (isInsert) SinhVienDAO.getInstance().insert(sv);
else {
if (maSVCu.isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên trên bảng!"); return; }
SinhVienDAO.getInstance().update(sv, maSVCu);
}
lamMoi(); JOptionPane.showMessageDialog(this, "Thành công!");
} catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
}
void xoa() {
if (txtMa.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên cần xóa!"); return; }
try {
SinhVienDAO.getInstance().delete(txtMa.getText().trim());
lamMoi(); JOptionPane.showMessageDialog(this, "Đã xóa!");
} catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi (có thể sinh viên đang có điểm): " + ex.getMessage()); }
}
}
// ==========================================
// 6. VIEW 4: QUẢN LÝ BẢNG MÔN HỌC
// ==========================================
class MonHocView extends JFrame {
JTextField txtMaMH = new JTextField(10), txtTenMH = new JTextField(15), txtTinChi = new JTextField(10), txtTim = new JTextField(15);
DefaultTableModel model = new DefaultTableModel(new String[]{"MaMH", "TenMH", "SoTinChi"}, 0);
JTable table = new JTable(model);
String maMHCu = "";
public MonHocView() {
setTitle("Quản Lý Bảng MonHoc");
setSize(680, 420);
setLocationRelativeTo(null);
setLayout(new BorderLayout(10, 10));
JPanel pnlInput = new JPanel(new GridLayout(2, 4, 5, 5));
pnlInput.add(new JLabel("  Mã Môn Học:"));    pnlInput.add(txtMaMH);
pnlInput.add(new JLabel("  Tên Môn Học:"));   pnlInput.add(txtTenMH);
pnlInput.add(new JLabel("  Số Tín Chỉ:"));    pnlInput.add(txtTinChi);
pnlInput.add(new JLabel("  Tìm tên môn:"));   pnlInput.add(txtTim);
add(pnlInput, BorderLayout.NORTH);
add(new JScrollPane(table), BorderLayout.CENTER);
JPanel pnlBtn = new JPanel();
JButton btnThem = new JButton("Thêm"), btnSua = new JButton("Sửa"), btnXoa = new JButton("Xóa"), btnTim = new JButton("Tìm kiếm"), btnLamMoi = new JButton("Làm mới");
pnlBtn.add(btnThem); pnlBtn.add(btnSua); pnlBtn.add(btnXoa); pnlBtn.add(btnTim); pnlBtn.add(btnLamMoi);
add(pnlBtn, BorderLayout.SOUTH);
btnThem.addActionListener(e -> xuLy(true));
btnSua.addActionListener(e -> xuLy(false));
btnXoa.addActionListener(e -> xoa());
btnTim.addActionListener(e -> load(txtTim.getText().trim()));
btnLamMoi.addActionListener(e -> lamMoi());
table.getSelectionModel().addListSelectionListener(e -> {
int r = table.getSelectedRow();
if (r >= 0) {
maMHCu = model.getValueAt(r, 0).toString();
txtMaMH.setText(maMHCu);
txtTenMH.setText(model.getValueAt(r, 1).toString());
txtTinChi.setText(model.getValueAt(r, 2).toString());
}
});
load("");
}
void lamMoi() {
maMHCu = ""; txtMaMH.setText(""); txtTenMH.setText(""); txtTinChi.setText(""); txtTim.setText("");
table.clearSelection(); load("");
}
void load(String kw) {
model.setRowCount(0);
try {
for (MonHoc mh : MonHocDAO.getInstance().selectByName(kw))
model.addRow(new Object[]{mh.maMH, mh.tenMH, mh.soTinChi});
} catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
}
void xuLy(boolean isInsert) {
if (txtMaMH.getText().trim().isEmpty() || txtTenMH.getText().trim().isEmpty() || txtTinChi.getText().trim().isEmpty()) {
JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin Môn Học!"); return;
}
try {
int tc = Integer.parseInt(txtTinChi.getText().trim());
MonHoc mh = new MonHoc(txtMaMH.getText().trim(), txtTenMH.getText().trim(), tc);
if (isInsert) MonHocDAO.getInstance().insert(mh);
else {
if (maMHCu.isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học trên bảng!"); return; }
MonHocDAO.getInstance().update(mh, maMHCu);
}
lamMoi(); JOptionPane.showMessageDialog(this, "Thành công!");
} catch (NumberFormatException nfe) {
JOptionPane.showMessageDialog(this, "Số tín chỉ phải là số nguyên!");
} catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
}
void xoa() {
if (txtMaMH.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng chọn môn học cần xóa!"); return; }
try {
MonHocDAO.getInstance().delete(txtMaMH.getText().trim());
lamMoi(); JOptionPane.showMessageDialog(this, "Đã xóa!");
} catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi (có thể môn học đang có điểm): " + ex.getMessage()); }
}
}
// ==========================================
// 7. VIEW 5: QUẢN LÝ BẢNG ĐIỂM (KHÓA NGOẠI)
// ==========================================
class DiemView extends JFrame {
JTextField txtMaSV = new JTextField(10), txtMaMH = new JTextField(10), txtDiem = new JTextField(10), txtTim = new JTextField(10);
DefaultTableModel model = new DefaultTableModel(new String[]{"MaSV", "MaMH", "DiemSo"}, 0);
JTable table = new JTable(model);
String maSVCu = "", maMHCu = "";
public DiemView() {
setTitle("Quản Lý Bảng Diem");
setSize(680, 420);
setLocationRelativeTo(null);
setLayout(new BorderLayout(10, 10));
JPanel pnlInput = new JPanel(new GridLayout(2, 4, 5, 5));
pnlInput.add(new JLabel("  Mã Sinh Viên:")); pnlInput.add(txtMaSV);
pnlInput.add(new JLabel("  Mã Môn Học:"));   pnlInput.add(txtMaMH);
pnlInput.add(new JLabel("  Điểm Số:"));      pnlInput.add(txtDiem);
pnlInput.add(new JLabel("  Tìm theo Mã SV:")); pnlInput.add(txtTim);
add(pnlInput, BorderLayout.NORTH);
add(new JScrollPane(table), BorderLayout.CENTER);
JPanel pnlBtn = new JPanel();
JButton btnThem = new JButton("Thêm"), btnSua = new JButton("Sửa"), btnXoa = new JButton("Xóa"), btnTim = new JButton("Tìm kiếm"), btnLamMoi = new JButton("Làm mới");
pnlBtn.add(btnThem); pnlBtn.add(btnSua); pnlBtn.add(btnXoa); pnlBtn.add(btnTim); pnlBtn.add(btnLamMoi);
add(pnlBtn, BorderLayout.SOUTH);
btnThem.addActionListener(e -> xuLy(true));
btnSua.addActionListener(e -> xuLy(false));
btnXoa.addActionListener(e -> xoa());
btnTim.addActionListener(e -> load(txtTim.getText().trim()));
btnLamMoi.addActionListener(e -> lamMoi());
table.getSelectionModel().addListSelectionListener(e -> {
int r = table.getSelectedRow();
if (r >= 0) {
maSVCu = model.getValueAt(r, 0).toString();
maMHCu = model.getValueAt(r, 1).toString();
txtMaSV.setText(maSVCu);
txtMaMH.setText(maMHCu);
txtDiem.setText(model.getValueAt(r, 2).toString());
}
});
load("");
}
void lamMoi() {
maSVCu = ""; maMHCu = ""; txtMaSV.setText(""); txtMaMH.setText(""); txtDiem.setText(""); txtTim.setText("");
table.clearSelection(); load("");
}
void load(String kw) {
model.setRowCount(0);
try {
for (Diem d : DiemDAO.getInstance().selectByMaSV(kw))
model.addRow(new Object[]{d.maSV, d.maMH, d.diemSo});
} catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage()); }
}
void xuLy(boolean isInsert) {
if (txtMaSV.getText().trim().isEmpty() || txtMaMH.getText().trim().isEmpty() || txtDiem.getText().trim().isEmpty()) {
JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ Mã SV, Mã MH và Điểm Số!"); return;
}
try {
float diem = Float.parseFloat(txtDiem.getText().trim());
Diem d = new Diem(txtMaSV.getText().trim(), txtMaMH.getText().trim(), diem);
if (isInsert) DiemDAO.getInstance().insert(d);
else {
if (maSVCu.isEmpty() || maMHCu.isEmpty()) { JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng điểm trên bảng!"); return; }
DiemDAO.getInstance().update(d, maSVCu, maMHCu);
}
lamMoi(); JOptionPane.showMessageDialog(this, "Thành công!");
} catch (NumberFormatException nfe) {
JOptionPane.showMessageDialog(this, "Điểm số phải là số (ví dụ: 8.5)!");
} catch (Exception ex) {
JOptionPane.showMessageDialog(this, "Lỗi ràng buộc khóa ngoại (kiểm tra Mã SV và Mã MH đã tồn tại chưa): " + ex.getMessage());
}
}
void xoa() {
if (txtMaSV.getText().trim().isEmpty() || txtMaMH.getText().trim().isEmpty()) {
JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng điểm cần xóa!"); return;
}
try {
DiemDAO.getInstance().delete(txtMaSV.getText().trim(), txtMaMH.getText().trim());
lamMoi(); JOptionPane.showMessageDialog(this, "Đã xóa điểm!");
} catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lỗi xóa: " + ex.getMessage()); }
}
}
