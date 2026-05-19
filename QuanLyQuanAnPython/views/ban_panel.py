import tkinter as tk
from tkinter import ttk
from service import QuanLyBanService

class BanPanel(tk.Frame):
    def __init__(self, main_frame):
        super().__init__(main_frame)
        self.main_frame = main_frame
        self.init_ui()
        self.load_data()
    
    def init_ui(self):
        # Tiêu đề
        title = tk.Label(self, text="SƠ ĐỒ BÀN", font=("Arial", 20, "bold"))
        title.pack(pady=10)
        
        # Container cho các bàn
        self.ban_container = tk.Frame(self)
        self.ban_container.pack(fill=tk.BOTH, expand=True, padx=20, pady=20)
    
    def load_data(self):
        # Xóa các bàn cũ
        for widget in self.ban_container.winfo_children():
            widget.destroy()
        
        danh_sach_ban = QuanLyBanService.get_all_ban()
        
        # Tạo grid 2x4
        for i, ban in enumerate(danh_sach_ban):
            row = i // 4
            col = i % 4
            
            btn = tk.Button(
                self.ban_container,
                text=f"Bàn {ban.so_ban}\n{ban.trang_thai}",
                font=("Arial", 12, "bold"),
                width=15,
                height=4,
                bg="green" if ban.trang_thai == "Trống" else "orange",
                command=lambda b=ban.so_ban: self.main_frame.chon_ban(b)
            )
            btn.grid(row=row, column=col, padx=10, pady=10, sticky="nsew")
        
        # Cấu hình grid
        for i in range(2):
            self.ban_container.grid_rowconfigure(i, weight=1)
        for i in range(4):
            self.ban_container.grid_columnconfigure(i, weight=1)
    
    def refresh(self):
        self.load_data()