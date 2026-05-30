# Quản Lý Quán Mì Cay

Hướng dẫn này mô tả các bước cần làm khi kéo mã nguồn từ Git để chạy ứng dụng trên Windows.

## Yêu cầu

- Java Development Kit (JDK) 8 hoặc cao hơn
- MySQL (hoặc MariaDB) đang chạy
- (Tùy chọn) IDE: IntelliJ IDEA hoặc Eclipse

## Bước 1 — Tải mã nguồn

Clone repository về máy:

```powershell
git clone <repo-url>
cd <repo-folder>
```

## Bước 2 — Tạo và import cơ sở dữ liệu

File SQL để tạo schema và dữ liệu mẫu nằm ở `database/quanly_quan_mi_cay.sql`.

Trên Windows (sử dụng MySQL client):

```powershell
mysql -u root -p < database/quanly_quan_mi_cay.sql
```

Nếu muốn tạo database trước rồi import vào database cụ thể:

```powershell
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS quanly_quan_mi_cay CHARACTER SET utf8;"
mysql -u root -p quanly_quan_mi_cay < database/quanly_quan_mi_cay.sql
```

## Bước 3 — Cấu hình kết nối cơ sở dữ liệu

Sao chép file cấu hình mẫu và chỉnh thông tin DB (host, port, user, password):

```powershell
Copy-Item -Path .\QuanLyQuanMiCay\src\config.properties.example -Destination .\QuanLyQuanMiCay\src\config.properties
```

Mở `QuanLyQuanMiCay/src/config.properties` và chỉnh các thông số `db.host`, `db.port`, `db.user`, `db.password` nếu cần.

## Bước 4 — Chạy ứng dụng

Cách 1 — Dùng IDE (khuyến nghị):

- Mở dự án `QuanLyQuanMiCay` trong IntelliJ hoặc Eclipse.
- Đánh dấu `QuanLyQuanMiCay/src` là Source Folder (nếu IDE không tự nhận).
- Thêm tất cả thư viện trong `QuanLyQuanMiCay/lib/` vào Classpath / Project Libraries.
- Chạy class `src.app.Main` (chứa `main()`).

Cách 2 — Dòng lệnh (nếu dự án đã được đóng gói hoặc có file .jar)

- Nếu tác giả đã để sẵn `bin/` chứa class đã biên dịch, bạn có thể chạy trực tiếp:

```powershell
cd QuanLyQuanMiCay
java -cp "bin;lib/*" app.Main
```

- Nếu không có class được biên dịch, biên dịch toàn bộ source và chạy (ví dụ đơn giản):

```powershell
# Biên dịch tất cả .java vào thư mục out
Get-ChildItem -Path .\QuanLyQuanMiCay\src -Recurse -Filter *.java | ForEach-Object { $_.FullName } > sources.txt
javac -d QuanLyQuanMiCay\bin -cp "QuanLyQuanMiCay\lib/*" @sources.txt
java -cp "QuanLyQuanMiCay\bin;QuanLyQuanMiCay\lib/*" app.Main
```

Lưu ý: câu lệnh trên tạo file `sources.txt` liệt kê đường dẫn tới tất cả file `.java` rồi dùng `javac @sources.txt` để biên dịch.

## Vấn đề thường gặp

- Lỗi kết nối DB: kiểm tra `QuanLyQuanMiCay/src/config.properties`, đảm bảo MySQL đang chạy và thông tin đúng.
- Thiếu thư viện: kiểm tra thư mục `QuanLyQuanMiCay/lib` có chứa các .jar cần thiết và đã được thêm vào classpath.
- Lỗi mã hóa: nếu tiếng Việt hiển thị sai, kiểm tra `db.charset` trong config và encoding của terminal/IDE.

## Thông tin thêm

- File cấu hình mẫu: `QuanLyQuanMiCay/src/config.properties.example`
- SQL schema: `database/quanly_quan_mi_cay.sql`

Nếu bạn muốn, tôi có thể đóng gói dự án thành file `.jar` chạy được (cần xác nhận dependency trong `lib/`).
