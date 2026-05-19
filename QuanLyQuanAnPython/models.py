from dataclasses import dataclass
from typing import List
from datetime import datetime

@dataclass
class Mon:
    id: int
    ten: str
    loai: str
    cap_do_cay: int
    gia: float
    con_ban: bool = True
    
    def __str__(self):
        if self.loai == "Mi Cay":
            if self.cap_do_cay == 0:
                cay_str = "Không cay"
            elif self.cap_do_cay == 7:
                cay_str = "ĐẶC BIỆT"
            elif self.cap_do_cay == 10:
                cay_str = "Siêu cay"
            else:
                cay_str = f"Cấp {self.cap_do_cay}"
            return f"{self.ten} {cay_str}"
        return self.ten

@dataclass
class Ban:
    so_ban: int
    trang_thai: str
    hoa_don_id: int

@dataclass
class ChiTietHoaDon:
    id: int
    hoa_don_id: int
    mon_id: int
    ten_mon: str
    so_luong: int
    don_gia: float
    thanh_tien: float

@dataclass
class HoaDon:
    id: int
    so_ban: int
    ngay_tao: datetime
    tong_tien: float
    giam_gia: float
    thanh_tien: float
    da_thanh_toan: bool
    chi_tiet_list: list = None
    
    def __post_init__(self):
        if self.chi_tiet_list is None:
            self.chi_tiet_list = []