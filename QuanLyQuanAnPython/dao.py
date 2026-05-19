from database import get_connection
from models import Mon, Ban, HoaDon, ChiTietHoaDon
from datetime import datetime
from typing import List, Optional

class MonDAO:
    @staticmethod
    def get_all() -> List[Mon]:
        conn = get_connection()
        if not conn:
            return []
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT id, ten, loai, cap_do_cay, gia, con_ban FROM mon")
        rows = cursor.fetchall()
        cursor.close()
        conn.close()
        return [Mon(row['id'], row['ten'], row['loai'], row['cap_do_cay'], row['gia'], bool(row['con_ban'])) for row in rows]
    
    @staticmethod
    def get_by_loai(loai: str) -> List[Mon]:
        conn = get_connection()
        if not conn:
            return []
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT id, ten, loai, cap_do_cay, gia, con_ban FROM mon WHERE loai = %s AND con_ban = 1", (loai,))
        rows = cursor.fetchall()
        cursor.close()
        conn.close()
        return [Mon(row['id'], row['ten'], row['loai'], row['cap_do_cay'], row['gia'], True) for row in rows]
    
    @staticmethod
    def get_by_id(mon_id: int) -> Optional[Mon]:
        conn = get_connection()
        if not conn:
            return None
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT id, ten, loai, cap_do_cay, gia, con_ban FROM mon WHERE id = %s", (mon_id,))
        row = cursor.fetchone()
        cursor.close()
        conn.close()
        if row:
            return Mon(row['id'], row['ten'], row['loai'], row['cap_do_cay'], row['gia'], bool(row['con_ban']))
        return None
    
    @staticmethod
    def add(mon: Mon) -> int:
        conn = get_connection()
        if not conn:
            return -1
        cursor = conn.cursor()
        cursor.execute("INSERT INTO mon (ten, loai, cap_do_cay, gia, con_ban) VALUES (%s, %s, %s, %s, %s)",
                       (mon.ten, mon.loai, mon.cap_do_cay, mon.gia, 1))
        conn.commit()
        mon_id = cursor.lastrowid
        cursor.close()
        conn.close()
        return mon_id
    
    @staticmethod
    def update(mon: Mon):
        conn = get_connection()
        if not conn:
            return
        cursor = conn.cursor()
        cursor.execute("UPDATE mon SET ten = %s, loai = %s, cap_do_cay = %s, gia = %s WHERE id = %s",
                       (mon.ten, mon.loai, mon.cap_do_cay, mon.gia, mon.id))
        conn.commit()
        cursor.close()
        conn.close()
    
    @staticmethod
    def delete(mon_id: int):
        conn = get_connection()
        if not conn:
            return
        cursor = conn.cursor()
        cursor.execute("DELETE FROM mon WHERE id = %s", (mon_id,))
        conn.commit()
        cursor.close()
        conn.close()

class BanDAO:
    @staticmethod
    def get_all() -> List[Ban]:
        conn = get_connection()
        if not conn:
            return []
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT so_ban, trang_thai, hoa_don_id FROM ban")
        rows = cursor.fetchall()
        cursor.close()
        conn.close()
        return [Ban(row['so_ban'], row['trang_thai'], row['hoa_don_id']) for row in rows]
    
    @staticmethod
    def get_by_so(so_ban: int) -> Optional[Ban]:
        conn = get_connection()
        if not conn:
            return None
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT so_ban, trang_thai, hoa_don_id FROM ban WHERE so_ban = %s", (so_ban,))
        row = cursor.fetchone()
        cursor.close()
        conn.close()
        if row:
            return Ban(row['so_ban'], row['trang_thai'], row['hoa_don_id'])
        return None
    
    @staticmethod
    def update(ban: Ban):
        conn = get_connection()
        if not conn:
            return
        cursor = conn.cursor()
        cursor.execute("UPDATE ban SET trang_thai = %s, hoa_don_id = %s WHERE so_ban = %s",
                       (ban.trang_thai, ban.hoa_don_id, ban.so_ban))
        conn.commit()
        cursor.close()
        conn.close()

class HoaDonDAO:
    @staticmethod
    def create(so_ban: int) -> HoaDon:
        conn = get_connection()
        if not conn:
            return None
        cursor = conn.cursor()
        now = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        cursor.execute("INSERT INTO hoa_don (so_ban, ngay_tao, tong_tien, giam_gia, thanh_tien, da_thanh_toan) VALUES (%s, %s, 0, 0, 0, 0)",
                       (so_ban, now))
        conn.commit()
        hoa_don_id = cursor.lastrowid
        cursor.close()
        conn.close()
        return HoaDon(hoa_don_id, so_ban, datetime.now(), 0, 0, 0, False)
    
    @staticmethod
    def get_by_id(hoa_don_id: int) -> Optional[HoaDon]:
        conn = get_connection()
        if not conn:
            return None
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT id, so_ban, ngay_tao, tong_tien, giam_gia, thanh_tien, da_thanh_toan FROM hoa_don WHERE id = %s", (hoa_don_id,))
        row = cursor.fetchone()
        cursor.close()
        conn.close()
        if row:
            return HoaDon(row['id'], row['so_ban'], row['ngay_tao'], row['tong_tien'], row['giam_gia'], row['thanh_tien'], bool(row['da_thanh_toan']))
        return None
    
    @staticmethod
    def get_by_ban(so_ban: int) -> Optional[HoaDon]:
        conn = get_connection()
        if not conn:
            return None
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT id, so_ban, ngay_tao, tong_tien, giam_gia, thanh_tien, da_thanh_toan FROM hoa_don WHERE so_ban = %s AND da_thanh_toan = 0", (so_ban,))
        row = cursor.fetchone()
        cursor.close()
        conn.close()
        if row:
            return HoaDon(row['id'], row['so_ban'], row['ngay_tao'], row['tong_tien'], row['giam_gia'], row['thanh_tien'], bool(row['da_thanh_toan']))
        return None
    
    @staticmethod
    def update_tong_tien(hoa_don_id: int):
        conn = get_connection()
        if not conn:
            return
        cursor = conn.cursor()
        cursor.execute("SELECT SUM(thanh_tien) FROM chi_tiet_hoa_don WHERE hoa_don_id = %s", (hoa_don_id,))
        result = cursor.fetchone()
        tong = result[0] if result[0] else 0
        cursor.execute("SELECT giam_gia FROM hoa_don WHERE id = %s", (hoa_don_id,))
        giam_gia = cursor.fetchone()[0]
        thanh_tien = tong - giam_gia
        cursor.execute("UPDATE hoa_don SET tong_tien = %s, thanh_tien = %s WHERE id = %s", (tong, thanh_tien, hoa_don_id))
        conn.commit()
        cursor.close()
        conn.close()
    
    @staticmethod
    def set_giam_gia(hoa_don_id: int, giam_gia: float):
        conn = get_connection()
        if not conn:
            return
        cursor = conn.cursor()
        cursor.execute("UPDATE hoa_don SET giam_gia = %s WHERE id = %s", (giam_gia, hoa_don_id))
        conn.commit()
        cursor.close()
        conn.close()
        HoaDonDAO.update_tong_tien(hoa_don_id)
    
    @staticmethod
    def thanh_toan(hoa_don_id: int):
        conn = get_connection()
        if not conn:
            return
        cursor = conn.cursor()
        cursor.execute("UPDATE hoa_don SET da_thanh_toan = 1 WHERE id = %s", (hoa_don_id,))
        conn.commit()
        cursor.close()
        conn.close()
    
    @staticmethod
    def get_all() -> List[HoaDon]:
        conn = get_connection()
        if not conn:
            return []
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT id, so_ban, ngay_tao, tong_tien, giam_gia, thanh_tien, da_thanh_toan FROM hoa_don")
        rows = cursor.fetchall()
        cursor.close()
        conn.close()
        return [HoaDon(row['id'], row['so_ban'], row['ngay_tao'], row['tong_tien'], row['giam_gia'], row['thanh_tien'], bool(row['da_thanh_toan'])) for row in rows]

class ChiTietHoaDonDAO:
    @staticmethod
    def add(ct: ChiTietHoaDon) -> int:
        conn = get_connection()
        if not conn:
            return -1
        cursor = conn.cursor()
        cursor.execute("INSERT INTO chi_tiet_hoa_don (hoa_don_id, mon_id, ten_mon, so_luong, don_gia, thanh_tien) VALUES (%s, %s, %s, %s, %s, %s)",
                       (ct.hoa_don_id, ct.mon_id, ct.ten_mon, ct.so_luong, ct.don_gia, ct.thanh_tien))
        conn.commit()
        ct_id = cursor.lastrowid
        cursor.close()
        conn.close()
        HoaDonDAO.update_tong_tien(ct.hoa_don_id)
        return ct_id
    
    @staticmethod
    def get_by_hoa_don(hoa_don_id: int) -> List[ChiTietHoaDon]:
        conn = get_connection()
        if not conn:
            return []
        cursor = conn.cursor(dictionary=True)
        cursor.execute("SELECT id, hoa_don_id, mon_id, ten_mon, so_luong, don_gia, thanh_tien FROM chi_tiet_hoa_don WHERE hoa_don_id = %s", (hoa_don_id,))
        rows = cursor.fetchall()
        cursor.close()
        conn.close()
        return [ChiTietHoaDon(row['id'], row['hoa_don_id'], row['mon_id'], row['ten_mon'], row['so_luong'], row['don_gia'], row['thanh_tien']) for row in rows]
    
    @staticmethod
    def update(ct: ChiTietHoaDon):
        conn = get_connection()
        if not conn:
            return
        cursor = conn.cursor()
        cursor.execute("UPDATE chi_tiet_hoa_don SET so_luong = %s, thanh_tien = %s WHERE id = %s",
                       (ct.so_luong, ct.thanh_tien, ct.id))
        conn.commit()
        cursor.close()
        conn.close()
        HoaDonDAO.update_tong_tien(ct.hoa_don_id)
    
    @staticmethod
    def delete(ct_id: int, hoa_don_id: int):
        conn = get_connection()
        if not conn:
            return
        cursor = conn.cursor()
        cursor.execute("DELETE FROM chi_tiet_hoa_don WHERE id = %s", (ct_id,))
        conn.commit()
        cursor.close()
        conn.close()
        HoaDonDAO.update_tong_tien(hoa_don_id)