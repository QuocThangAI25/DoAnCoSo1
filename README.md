# 🍜 Quản Lý Quán Mì Cay - TVT

## 📌 Giới thiệu
Phần mềm quản lý quán mì cay bằng **Java Swing** và **MySQL**.

**Branch:** `Update-New`

---

## ✨ Tính năng

| Tính năng | Mô tả |
|-----------|-------|
| Đăng nhập | Admin và nhân viên, phân quyền |
| Sơ đồ bàn | 8 bàn, trạng thái Trống/Đang phục vụ |
| Menu gọi món | Dạng lưới 2 cột, ảnh, spinner số lượng |
| Thanh toán | Tiền mặt / Chuyển khoản + mã QR |
| In hóa đơn | Xuất PDF, hiển thị nhân viên |
| Thống kê | Doanh thu, top món bán chạy |
| Quản lý | CRUD món ăn, nhân viên (admin) |

---

## 🛠️ Công nghệ
- Java Swing
- MySQL
- iTextPDF (xuất PDF)
- ZXing (tạo QR)

---

## 📦 Thư viện cần tải

| Thư viện | Link |
|----------|------|
| MySQL Connector | [Tải](https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.7.0/mysql-connector-j-9.7.0.jar) |
| iTextPDF | [Tải](https://repo1.maven.org/maven2/com/itextpdf/itextpdf/5.5.13.3/itextpdf-5.5.13.3.jar) |
| ZXing Core | [Tải](https://repo1.maven.org/maven2/com/google/zxing/core/3.5.3/core-3.5.3.jar) |
| ZXing JavaSE | [Tải](https://repo1.maven.org/maven2/com/google/zxing/javase/3.5.3/javase-3.5.3.jar) |

---

## 🔑 Tài khoản mặc định

| Tài khoản | Mật khẩu | Vai trò |
|-----------|----------|---------|
| admin | 123456 | Quản trị viên |
| nhanvien1 | 123456 | Nhân viên |

---

## 📝 Cập nhật (24/05/2026)
- ✅ Ảnh món ăn + spinner số lượng
- ✅ Thanh toán chuyển khoản + mã QR
- ✅ Xuất hóa đơn PDF
- ✅ Sửa lỗi kết nối DB

**Tác giả:** Nguyễn Quốc Thắng
