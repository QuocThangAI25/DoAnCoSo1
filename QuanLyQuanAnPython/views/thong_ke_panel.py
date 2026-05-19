import tkinter as tk
from tkinter import ttk, messagebox
from datetime import datetime
from service import ThongKeService
import utils

class ThongKePanel(tk.Frame):
    def __init__(self):
        super().__init__()
        self.init_ui()
        self.load_thong_ke(datetime.now())
    
    def init_ui(self):
        # Panel trên cùng để chọn ngày
        top_frame = tk.Frame(self)
        top_frame.pack(fill=tk.X, padx=10, pady=10)
        
        tk.Label(top_frame, text="Ngày:").pack(side=tk.LEFT, padx=5)
        
        # Date entry
        self.ngay_entry = tk.Entry(top_frame, width=15)
        self.ngay_entry.pack(side=tk.LEFT, padx=5)
        self.ngay_entry.insert(0, datetime.now().strftime("%d/%m/%Y"))
        
        btn_xem = tk.Button(top_frame, text="Xem", command=self.xem_thong_ke)
        btn_xem.pack(side=tk.LEFT, padx=5)
        
        # Panel thống kê chính
        main_frame = tk.Frame(self)
        main_frame.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)
        
        # Panel trái - thông số
        info_frame = tk.LabelFrame(main_frame, text="THỐNG KÊ", font=("Arial", 12, "bold"))
        info_frame.pack(side=tk.LEFT, fill=tk.BOTH, expand=True, padx=5)
        
        # Doanh thu
        tk.Label(info_frame, text="Doanh thu:", font=("Arial", 12)).grid(row=0, column=0, padx=10, pady=10, sticky="w")
        self.lb_doanh_thu = tk.Label(info_frame, text="0 ₫", font=("Arial", 14, "bold"), fg="red")
        self.lb_doanh_thu.grid(row=0, column=1, padx=10, pady=10)
        
        # Số hóa đơn
        tk.Label(info_frame, text="Số hóa đơn:", font=("Arial", 12)).grid(row=1, column=0, padx=10, pady=10, sticky="w")
        self.lb_so_hoa_don = tk.Label(info_frame, text="0", font=("Arial", 14))
        self.lb_so_hoa_don.grid(row=1, column=1, padx=10, pady=10)
        
        # Tổng món bán
        tk.Label(info_frame, text="Tổng món bán:", font=("Arial", 12)).grid(row=2, column=0, padx=10, pady=10, sticky="w")
        self.lb_so_mon = tk.Label(info_frame, text="0", font=("Arial", 14))
        self.lb_so_mon.grid(row=2, column=1, padx=10, pady=10)
        
        # Panel phải - top món
        top_mon_frame = tk.LabelFrame(main_frame, text="TOP 3 MÓN BÁN CHẠY", font=("Arial", 12, "bold"))
        top_mon_frame.pack(side=tk.RIGHT, fill=tk.BOTH, expand=True, padx=5)
        
        self.list_top_mon = tk.Listbox(top_mon_frame, font=("Arial", 12), height=10)
        self.list_top_mon.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)
    
    def xem_thong_ke(self):
        try:
            ngay_str = self.ngay_entry.get()
            ngay = datetime.strptime(ngay_str, "%d/%m/%Y")
            self.load_thong_ke(ngay)
        except:
            messagebox.showerror("Lỗi", "Vui lòng nhập đúng định dạng dd/mm/yyyy")
    
    def load_thong_ke(self, ngay):
        doanh_thu = ThongKeService.get_doanh_thu(ngay)
        so_hoa_don = ThongKeService.get_so_hoa_don(ngay)
        so_mon = ThongKeService.get_tong_mon_ban(ngay)
        top_mon = ThongKeService.get_top_mon_ban_chay(ngay, 3)
        
        self.lb_doanh_thu.config(text=utils.format_vnd(doanh_thu))
        self.lb_so_hoa_don.config(text=str(so_hoa_don))
        self.lb_so_mon.config(text=str(so_mon))
        
        self.list_top_mon.delete(0, tk.END)
        for i, (ten, so_luong) in enumerate(top_mon, 1):
            self.list_top_mon.insert(tk.END, f"{i}. {ten} - {so_luông} phần")