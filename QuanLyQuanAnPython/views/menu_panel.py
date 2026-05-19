import tkinter as tk
from tkinter import ttk
from dao import MonDAO
from service import ThanhToanService
import utils

class MenuPanel(tk.Frame):
    def __init__(self, hoa_don_panel):
        super().__init__()
        self.hoa_don_panel = hoa_don_panel
        self.current_ban = -1
        self.init_ui()
        self.load_menu()
    
    def init_ui(self):
        self.notebook = ttk.Notebook(self)
        self.notebook.pack(fill=tk.BOTH, expand=True)
        
        self.mi_cay_frame = tk.Frame(self.notebook)
        self.nuoc_uong_frame = tk.Frame(self.notebook)
        self.topping_frame = tk.Frame(self.notebook)
        
        self.notebook.add(self.mi_cay_frame, text="Mì Cay")
        self.notebook.add(self.nuoc_uong_frame, text="Nước Uống")
        self.notebook.add(self.topping_frame, text="Topping")
        
        # Canvas để cuộn
        self.create_scrollable_frame(self.mi_cay_frame)
        self.create_scrollable_frame(self.nuoc_uong_frame)
        self.create_scrollable_frame(self.topping_frame)
    
    def create_scrollable_frame(self, parent):
        canvas = tk.Canvas(parent)
        scrollbar = ttk.Scrollbar(parent, orient="vertical", command=canvas.yview)
        scrollable_frame = tk.Frame(canvas)
        
        scrollable_frame.bind(
            "<Configure>",
            lambda e: canvas.configure(scrollregion=canvas.bbox("all"))
        )
        
        canvas.create_window((0, 0), window=scrollable_frame, anchor="nw")
        canvas.configure(yscrollcommand=scrollbar.set)
        
        canvas.pack(side="left", fill="both", expand=True)
        scrollbar.pack(side="right", fill="y")
        
        return scrollable_frame
    
    def load_menu(self):
        self.load_mon_theo_loai("Mi Cay", self.mi_cay_frame.winfo_children()[0] if self.mi_cay_frame.winfo_children() else None)
        self.load_mon_theo_loai("Nuoc Uong", self.nuoc_uong_frame.winfo_children()[0] if self.nuoc_uong_frame.winfo_children() else None)
        self.load_mon_theo_loai("Topping", self.topping_frame.winfo_children()[0] if self.topping_frame.winfo_children() else None)
    
    def load_mon_theo_loai(self, loai, container):
        if container is None:
            return
        
        for widget in container.winfo_children():
            widget.destroy()
        
        mon_list = MonDAO.get_by_loai(loai)
        
        for mon in mon_list:
            frame = tk.Frame(container, bd=1, relief=tk.RAISED)
            frame.pack(fill=tk.X, padx=10, pady=5)
            
            info_frame = tk.Frame(frame)
            info_frame.pack(side=tk.LEFT, fill=tk.BOTH, expand=True, padx=10, pady=5)
            
            lb_ten = tk.Label(info_frame, text=str(mon), font=("Arial", 12, "bold"))
            lb_ten.pack(anchor="w")
            
            lb_gia = tk.Label(info_frame, text=utils.format_vnd(mon.gia), fg="red", font=("Arial", 10))
            lb_gia.pack(anchor="w")
            
            btn_order = tk.Button(frame, text="Gọi", command=lambda m=mon: self.goi_mon(m))
            btn_order.pack(side=tk.RIGHT, padx=10, pady=10)
    
    def goi_mon(self, mon):
        if self.current_ban > 0 and self.hoa_don_panel.current_hoa_don_id != -1:
            ThanhToanService.them_mon_vao_hoa_don(self.hoa_don_panel.current_hoa_don_id, mon, 1)
            self.hoa_don_panel.load_hoa_don(self.hoa_don_panel.current_hoa_don_id)
        else:
            from tkinter import messagebox
            messagebox.showwarning("Thông báo", "Vui lòng chọn bàn trước!")
    
    def set_current_ban(self, ban):
        self.current_ban = ban