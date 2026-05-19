from dao import MonDAO, BanDAO, HoaDonDAO, ChiTietHoaDonDAO
from models import ChiTietHoaDon, HoaDon
from datetime import datetime
from typing import List, Dict, Tuple

class QuanLyBanService:
    @staticmethod
    def get_all_ban():
        return BanDAO.get_all()
    
    @staticmethod
    def update_trang_thai_ban(so_ban: int, trang_thai: str, hoa_don_id: int):
        from models import Ban
        ban = BanDAO.get_by_so(so_ban)
        if ban:
            ban.trang_thai = trang_thai
            ban.hoa_don_id = hoa_don_id
            BanDAO.update(ban)
    
    @staticmethod
    def get_hoa_don_by_ban(so_ban: int):
        return HoaDonDAO.get_by_ban(so_ban)
    
    @staticmethod
    def tao_hoa_don_moi(so_ban: int):
        return HoaDonDAO.create(so_ban)

class ThanhToanService:
    @staticmethod
    def them_mon_vao_hoa_don(hoa_don_id: int, mon, so_luong: int):
        thanh_tien = mon.gia * so_luong
        ct = ChiTietHoaDon(0, hoa_don_id, mon.id, str(mon), so_luong, mon.gia, thanh_tien)
        ChiTietHoaDonDAO.add(ct)
    
    @staticmethod
    def get_chi_tiet_hoa_don(hoa_don_id: int):
        return ChiTietHoaDonDAO.get_by_hoa_don(hoa_don_id)
    
    @staticmethod
    def cap_nhat_so_luong_mon(ct: ChiTietHoaDon, so_luong_moi: int):
        ct.so_luong = so_luong_moi
        ct.thanh_tien = ct.don_gia * so_luong_moi
        ChiTietHoaDonDAO.update(ct)
    
    @staticmethod
    def xoa_mon_khoi_hoa_don(ct_id: int, hoa_don_id: int):
        ChiTietHoaDonDAO.delete(ct_id, hoa_don_id)
    
    @staticmethod
    def cap_nhat_giam_gia(hoa_don_id: int, giam_gia: float):
        HoaDonDAO.set_giam_gia(hoa_don_id, giam_gia)
    
    @staticmethod
    def thanh_toan(hoa_don_id: int):
        HoaDonDAO.thanh_toan(hoa_don_id)
    
    @staticmethod
    def get_hoa_don(hoa_don_id: int):
        return HoaDonDAO.get_by_id(hoa_don_id)

class ThongKeService:
    @staticmethod
    def get_doanh_thu(ngay: datetime) -> float:
        ngay_str = ngay.strftime("%Y-%m-%d")
        tong = 0
        for hd in HoaDonDAO.get_all():
            if hd.da_thanh_toan and hd.ngay_tao.strftime("%Y-%m-%d") == ngay_str:
                tong += hd.thanh_tien
        return tong
    
    @staticmethod
    def get_so_hoa_don(ngay: datetime) -> int:
        ngay_str = ngay.strftime("%Y-%m-%d")
        count = 0
        for hd in HoaDonDAO.get_all():
            if hd.da_thanh_toan and hd.ngay_tao.strftime("%Y-%m-%d") == ngay_str:
                count += 1
        return count
    
    @staticmethod
    def get_tong_mon_ban(ngay: datetime) -> int:
        ngay_str = ngay.strftime("%Y-%m-%d")
        total = 0
        for hd in HoaDonDAO.get_all():
            if hd.da_thanh_toan and hd.ngay_tao.strftime("%Y-%m-%d") == ngay_str:
                ct_list = ChiTietHoaDonDAO.get_by_hoa_don(hd.id)
                total += sum(ct.so_luong for ct in ct_list)
        return total
    
    @staticmethod
    def get_top_mon_ban_chay(ngay: datetime, limit: int = 3) -> List[Tuple[str, int]]:
        ngay_str = ngay.strftime("%Y-%m-%d")
        mon_count = {}
        for hd in HoaDonDAO.get_all():
            if hd.da_thanh_toan and hd.ngay_tao.strftime("%Y-%m-%d") == ngay_str:
                ct_list = ChiTietHoaDonDAO.get_by_hoa_don(hd.id)
                for ct in ct_list:
                    mon_count[ct.ten_mon] = mon_count.get(ct.ten_mon, 0) + ct.so_luong
        sorted_mon = sorted(mon_count.items(), key=lambda x: x[1], reverse=True)
        return sorted_mon[:limit]