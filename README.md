# Quản lý quán mì cay (TVT)

Ứng dụng desktop Java Swing quản lý bàn, gọi món, hóa đơn, thống kê và quản trị menu/nhân viên cho quán mì cay.

## Yêu cầu

- **JDK 21** trở lên
- **MySQL** (MariaDB tương thích) — cổng mặc định `3306`
- **Eclipse IDE** (khuyến nghị) hoặc IDE hỗ trợ Java + MySQL Connector/J

## Cấu trúc thư mục

```
DoAnCoSo1/
├── database/
│   └── quanly_quan_mi_cay.sql    # Script tạo DB và dữ liệu mẫu
├── QuanLyQuanMiCay/              # Project Java (Eclipse)
│   ├── lib/
│   │   └── mysql-connector-j-9.7.0.jar
│   └── src/
│       ├── app/Main.java         # Điểm chạy ứng dụng
│       ├── config/               # Kết nối database
│       ├── model/                # Entity
│       ├── dao/                  # Truy cập dữ liệu
│       ├── service/              # Nghiệp vụ
│       ├── util/                 # Tiện ích (ngày, tiền)
│       └── view/
│           ├── frame/            # Login, màn hình chính
│           └── panel/            # Các tab chức năng
└── README.md
```

## Cài đặt database

1. Khởi động MySQL.
2. Mở phpMyAdmin hoặc MySQL Workbench (hoặc dòng lệnh).
3. Import file `database/quanly_quan_mi_cay.sql` để tạo database `quanly_quan_mi_cay` và dữ liệu mẫu.

Hoặc chạy lệnh (điều chỉnh user/mật khẩu nếu cần):

```bash
mysql -u root -p < database/quanly_quan_mi_cay.sql
```

### Cấu hình kết nối

Sửa thông tin trong `QuanLyQuanMiCay/src/config/DBConnection.java` nếu MySQL của bạn khác mặc định:

| Tham số  | Mặc định                            |
| -------- | ----------------------------------- |
| Host/DB  | `localhost:3306/quanly_quan_mi_cay` |
| User     | `root`                              |
| Password | _(để trống)_                        |

## Thư viện MySQL

Đặt file **MySQL Connector/J** vào:

```
QuanLyQuanMiCay/lib/mysql-connector-j-9.7.0.jar
```

Tải tại: [https://dev.mysql.com/downloads/connector/j/](https://dev.mysql.com/downloads/connector/j/)

> Project Eclipse đã cấu hình classpath trỏ tới `lib/mysql-connector-j-9.7.0.jar`. Nếu dùng phiên bản JAR khác, đổi tên file hoặc cập nhật `.classpath`.

## Chạy bằng Eclipse

1. **File → Import → General → Existing Projects into Workspace**
2. Chọn thư mục gốc repo `DoAnCoSo1`, tick project **QuanLyQuanMiCay**.
3. Refresh project (F5).
4. **Run → Run Configurations…**
   - Main class: `app.Main`
   - Project: `QuanLyQuanMiCay`
5. Nhấn **Run**.

Nếu trước đây bạn import project tên `DACS1`, hãy **đóng/xóa project cũ** trong workspace và import lại `QuanLyQuanMiCay`.

> **Lưu ý:** Nếu trong repo vẫn còn thư mục `DACS1/` (do IDE đang mở), hãy đóng Eclipse/Cursor rồi xóa thư mục đó thủ công. Project chính thức nằm tại `QuanLyQuanMiCay/`.

## Chạy bằng dòng lệnh

Từ thư mục `QuanLyQuanMiCay` (cần có file JAR trong `lib/`):

```powershell
# Biên dịch
$src = Get-ChildItem -Recurse -Filter "*.java" -Path "src" | Where-Object { $_.Name -ne "module-info.java" }
javac --release 21 -d bin -sourcepath src -cp "lib\mysql-connector-j-9.7.0.jar" ($src | ForEach-Object FullName)

# Chạy
java -cp "bin;lib\mysql-connector-j-9.7.0.jar" app.Main
```

Trên Linux/macOS, thay `;` bằng `:` trong classpath.

## Tài khoản mẫu

Sau khi import SQL, đăng nhập bằng:

| Tài khoản | Mật khẩu | Vai trò          |
| --------- | -------- | ---------------- |
| `admin`   | `123456` | Quản trị (admin) |

Tài khoản admin có thêm tab **Quản lý** (menu, nhân viên).

## Chức năng chính

- **Sơ đồ bàn** — chọn bàn, mở hóa đơn
- **Gọi món** — menu mì cay, nước, topping; chỉnh hóa đơn, thanh toán
- **Thống kê** — doanh thu, số hóa đơn, top món theo ngày
- **Quản lý** _(admin)_ — CRUD món ăn, nhân viên

## Xử lý lỗi thường gặp

| Lỗi                            | Cách xử lý                                                                       |
| ------------------------------ | -------------------------------------------------------------------------------- |
| `Khong tim thay driver MySQL`  | Thêm `mysql-connector-j-*.jar` vào `lib/` và classpath                           |
| `LOI ket noi`                  | Kiểm tra MySQL đang chạy, đã import SQL, user/password trong `DBConnection.java` |
| `Sai tài khoản hoặc mật khẩu`  | Dùng `admin` / `123456` hoặc import lại file SQL                                 |
| Project không chạy sau đổi tên | Import lại `QuanLyQuanMiCay`, main class `app.Main`                              |

## Công nghệ

- Java 21, Swing
- MySQL + JDBC
- Kiến trúc phân lớp: View → Service → DAO → Model
