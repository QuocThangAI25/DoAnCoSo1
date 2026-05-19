import tkinter as tk
from tkinter import ttk
from views.ban_panel import BanPanel
from views.menu_panel import MenuPanel
from views.hoa_don_panel import HoaDonPanel
from views.thong_ke_panel import ThongKePanel
from views.quan_ly_menu_panel import QuanLyMenuPanel

class MainFrame(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("TVT - QUẢN LÝ QUÁN MÌ CAY")
        self.geometry("1400x800")
        self.current_ban = -1
        
        self.init_components()
    
    def init_components(self):
        # Tạo Notebook (tabbed pane)
        self.notebook = ttk.Notebook(self)
        self.notebook.pack(fill=tk.BOTH, expand=True)
        
        # Tab 1: Sơ đồ bàn
        self.ban_panel = BanPanel(self)
        self.notebook.add(self.ban_panel, text="Sơ đồ bàn")
        
        # Tab 2: Gọi món (chia đôi màn hình)
        self.goi_mon_frame = tk.Frame(self.notebook)
        self.hoa_don_panel = HoaDonPanel(self)
        self.menu_panel = MenuPanel(self.hoa_don_panel)
        
        self.goi_mon_frame.pack(fill=tk.BOTH, expand=True)
        self.menu_panel.pack(side=tk.LEFT, fill=tk.BOTH, expand=True, padx=(0, 2))
        self.hoa_don_panel.pack(side=tk.RIGHT, fill=tk.BOTH, expand=True, padx=(2, 0))
        
        self.notebook.add(self.goi_mon_frame, text="Gọi món")
        
        # Tab 3: Thống kê
        self.thong_ke_panel = ThongKePanel()
        self.notebook.add(self.thong_ke_panel, text="Thống kê")
        
        # Tab 4: Quản lý menu
        self.quan_ly_menu_panel = QuanLyMenuPanel()
        self.notebook.add(self.quan_ly_menu_panel, text="Quản lý menu")
    
    def chon_ban(self, so_ban: int):
        self.current_ban = so_ban
        self.menu_panel.set_current_ban(so_ban)
        
        from service import QuanLyBanService
        hoa_don = QuanLyBanService.get_hoa_don_by_ban(so_ban)
        if hoa_don is None:
            hoa_don = QuanLyBanService.tao_hoa_don_moi(so_ban)
            QuanLyBanService.update_trang_thai_ban(so_ban, "Đang phục vụ", hoa_don.id)
            self.ban_panel.refresh()
        
        self.hoa_don_panel.load_hoa_don(hoa_don.id)
        self.notebook.select(1)  # Chuyển sang tab gọi món
    
    def thanh_toan_xong(self):
        if self.current_ban != -1:
            from service import QuanLyBanService
            QuanLyBanService.update_trang_thai_ban(self.current_ban, "Trống", -1)
            self.ban_panel.refresh()
            self.current_ban = -1
            self.menu_panel.set_current_ban(-1)