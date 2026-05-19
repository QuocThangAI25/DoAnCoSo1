import mysql.connector
from mysql.connector import Error
import os

# Cấu hình kết nối MySQL
DB_CONFIG = {
    'host': 'localhost',      # hoặc 127.0.0.1
    'user': 'root',           # tên đăng nhập MySQL của bạn
    'password': '',           # mật khẩu MySQL (để trống nếu chưa đặt)
    'database': 'quanly_quan_an',  # tên database sẽ tạo
    'charset': 'utf8mb4'
}

def get_connection():
    """Kết nối MySQL database"""
    try:
        conn = mysql.connector.connect(**DB_CONFIG)
        return conn
    except Error as e:
        print(f"Lỗi kết nối MySQL: {e}")
        return None

def init_database():
    """Tạo database và các bảng nếu chưa có"""
    try:
        # Kết nối không database để tạo database
        conn = mysql.connector.connect(
            host=DB_CONFIG['host'],
            user=DB_CONFIG['user'],
            password=DB_CONFIG['password'],
            charset='utf8mb4'
        )
        cursor = conn.cursor()
        
        # Tạo database nếu chưa tồn tại
        cursor.execute(f"CREATE DATABASE IF NOT EXISTS {DB_CONFIG['database']} CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
        cursor.execute(f"USE {DB_CONFIG['database']}")
        
        # Bảng món ăn
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS mon (
                id INT PRIMARY KEY AUTO_INCREMENT,
                ten VARCHAR(100) NOT NULL,
                loai VARCHAR(50) NOT NULL,
                cap_do_cay INT,
                gia DECIMAL(10,0) NOT NULL,
                con_ban BOOLEAN DEFAULT TRUE
            )
        ''')
        
        # Bảng bàn
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS ban (
                so_ban INT PRIMARY KEY,
                trang_thai VARCHAR(50) DEFAULT 'Trống',
                hoa_don_id INT DEFAULT -1
            )
        ''')
        
        # Bảng hóa đơn
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS hoa_don (
                id INT PRIMARY KEY AUTO_INCREMENT,
                so_ban INT NOT NULL,
                ngay_tao DATETIME NOT NULL,
                tong_tien DECIMAL(10,0) DEFAULT 0,
                giam_gia DECIMAL(10,0) DEFAULT 0,
                thanh_tien DECIMAL(10,0) DEFAULT 0,
                da_thanh_toan BOOLEAN DEFAULT FALSE,
                FOREIGN KEY (so_ban) REFERENCES ban(so_ban)
            )
        ''')
        
        # Bảng chi tiết hóa đơn
        cursor.execute('''
            CREATE TABLE IF NOT EXISTS chi_tiet_hoa_don (
                id INT PRIMARY KEY AUTO_INCREMENT,
                hoa_don_id INT NOT NULL,
                mon_id INT NOT NULL,
                ten_mon VARCHAR(100) NOT NULL,
                so_luong INT NOT NULL,
                don_gia DECIMAL(10,0) NOT NULL,
                thanh_tien DECIMAL(10,0) NOT NULL,
                FOREIGN KEY (hoa_don_id) REFERENCES hoa_don(id) ON DELETE CASCADE,
                FOREIGN KEY (mon_id) REFERENCES mon(id)
            )
        ''')
        
        # Thêm dữ liệu mẫu cho bàn
        cursor.execute("SELECT COUNT(*) FROM ban")
        if cursor.fetchone()[0] == 0:
            for i in range(1, 9):
                cursor.execute("INSERT INTO ban (so_ban) VALUES (%s)", (i,))
        
        # Thêm dữ liệu mẫu cho món ăn
        cursor.execute("SELECT COUNT(*) FROM mon")
        if cursor.fetchone()[0] == 0:
            mon_mau = [
                ("Mì Cay", "Mi Cay", 0, 35000, True),
                ("Mì Cay", "Mi Cay", 3, 45000, True),
                ("Mì Cay", "Mi Cay", 5, 55000, True),
                ("Mì Cay", "Mi Cay", 7, 65000, True),
                ("Mì Cay TvT", "Mi Cay", 10, 75000, True),
                ("Trà Chanh", "Nuoc Uong", -1, 15000, True),
                ("Trà Sữa Matcha", "Nuoc Uong", -1, 25000, True),
                ("Phô Mai", "Topping", -1, 10000, True),
                ("Trứng", "Topping", -1, 8000, True),
            ]
            cursor.executemany("INSERT INTO mon (ten, loai, cap_do_cay, gia, con_ban) VALUES (%s, %s, %s, %s, %s)", mon_mau)
        
        conn.commit()
        cursor.close()
        conn.close()
        print("✅ MySQL Database initialized successfully!")
        
    except Error as e:
        print(f"Lỗi khởi tạo database: {e}")

if __name__ == "__main__":
    init_database()