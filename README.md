## 🚀 Quick Start

1. **Clone & Setup**: Xem file `SETUP.md` để hướng dẫn chi tiết
2. **Default Accounts**: Xem phần "🔑 Tài khoản mặc định"
3. **Database**: File `database/quanly_quan_mi_cay.sql`

## 📂 Cấu trúc Dự án

---

## 🚀 Quick Start

1. Copy sample config and edit if needed:

```bash
copy QuanLyQuanMiCay\src\config.properties.example QuanLyQuanMiCay\src\config.properties
```

2. Import database:

```bash
mysql -u root -p < QuanLyQuanMiCay\database\quanly_quan_mi_cay.sql
```

3. Open `QuanLyQuanMiCay` in Eclipse/IntelliJ, ensure `lib/` jars are added to Project Build Path.

4. Run `src/app/Main.java`.

If you need custom DB credentials, edit `QuanLyQuanMiCay/src/config.properties`.

Sửa đổi `src/config.properties` nếu:

- MySQL chạy trên host/port khác
- Username/password DB khác
