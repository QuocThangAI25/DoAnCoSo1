import tkinter as tk
from tkinter import ttk, messagebox
from dao import MonDAO
from models import Mon
import utils

class QuanLyMenuPanel(tk.Frame):
    def __init__(self):
        super().__init__()
        self.init_ui()
        self.load_menu()
    
    def init_ui(self):
        # Treeview để hiển thị danh sách món
        columns = ("ID", "Tên món", "Loại", "Cấp độ cay", "Giá", "Trạng thái")
        self.tree = ttk.Treeview(self, columns=columns, show="headings", height=15)
        
        for col in columns:
            self.tree.heading(col, text=col)
            self.tree.column(col, width=100)
        
        self.tree.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)
        
        # Panel nhập liệu
        input_frame = tk.LabelFrame(self, text="Thêm/Sửa món", font=("Arial", 12, "bold"))
        input_frame.pack(fill=tk.X, padx=10, pady=10)
        
        # Tên món
        row1 = tk.Frame(input_frame)
        row1.pack(fill=tk.X, padx=10, pady=5)
        tk.Label(row1, text="Tên món:", width=15, anchor="w").pack(side=tk.LEFT)
        self.txt_ten = tk.Entry(row1, width=30)
        self.txt_ten.pack(side=tk.LEFT, padx=5)
        
        # Loại
        row2 = tk.Frame(input_frame)
        row2.pack(fill=tk.X, padx=10, pady=5)
        tk.Label(row2, text="Loại:", width=15, anchor="w").pack(side=tk.LEFT)
        self.cb_loai = ttk.Combobox(row2, values=["Mi Cay", "Nuoc Uong", "Topping"], width=27)
        self.cb_loai.pack(side=tk.LEFT, padx=5)
        
        # Cấp độ cay
        row3 = tk.Frame(input_frame)
        row3.pack(fill=tk.X, padx=10, pady=5)
        tk.Label(row3, text="Cấp độ cay:", width=15, anchor="w").pack(side=tk.LEFT)
        self.cb_cap_do = ttk.Combobox(row3, values=["0 (Không cay)", "3", "5", "7 (Đặc biệt)", "10 (Siêu cay)", "Không áp dụng"], width=27)
        self.cb_cap_do.pack(side=tk.LEFT, padx=5)
        
        # Giá
        row4 = tk.Frame(input_frame)
        row4.pack(fill=tk.X, padx=10, pady=5)
        tk.Label(row4, text="Giá:", width=15, anchor="w").pack(side=tk.LEFT)
        self.txt_gia = tk.Entry(row4, width=30)
        self.txt_gia.pack(side=tk.LEFT, padx=5)
        
        # Buttons
        btn_frame = tk.Frame(input_frame)
        btn_frame.pack(fill=tk.X, padx=10, pady=10)
        
        btn_them = tk.Button(btn_frame, text="Thêm", command=self.them_mon, bg="blue", fg="white")
        btn_them.pack(side=tk.LEFT, padx=5)
        
        btn_sua = tk.Button(btn_frame, text="Sửa", command=self.sua_mon, bg="orange", fg="white")
        btn_sua.pack(side=tk.LEFT, padx=5)
        
        btn_xoa = tk.Button(btn_frame, text="Xóa", command=self.xoa_mon, bg="red", fg="white")
        btn_xoa.pack(side=tk.LEFT, padx=5)
        
        # Sự kiện chọn dòng
        self.tree.bind("<<TreeviewSelect>>", self.on_select)
    
    def load_menu(self):
        for item in self.tree.get_children():
            self.tree.delete(item)
        
        mon_list = MonDAO.get_all()
        for mon in mon_list:
            cap_do = self.get_cap_do_str(mon.cap_do_cay)
            gia_str = utils.format_vnd(mon.gia)
            trang_thai = "Đang bán" if mon.con_ban else "Ngừng bán"
            self.tree.insert("", "end", values=(mon.id, mon.ten, mon.loai, cap_do, gia_str, trang_thai))
    
    def get_cap_do_str(self, cap_do):
        if cap_do == -1:
            return "Không áp dụng"
        elif cap_do == 0:
            return "0 (Không cay)"
        elif cap_do == 7:
            return "7 (Đặc biệt)"
        elif cap_do == 10:
            return "10 (Siêu cay)"
        else:
            return str(cap_do)
    
    def get_cap_do_value(self, cap_do_str):
        if "Không áp dụng" in cap_do_str:
            return -1
        elif "0" in cap_do_str:
            return 0
        elif "3" in cap_do_str:
            return 3
        elif "5" in cap_do_str:
            return 5
        elif "7" in cap_do_str:
            return 7
        elif "10" in cap_do_str:
            return 10
        return -1
    
    def on_select(self, event):
        selection = self.tree.selection()
        if selection:
            item = self.tree.item(selection[0])
            values = item['values']
            self.txt_ten.delete(0, tk.END)
            self.txt_ten.insert(0, values[1])
            self.cb_loai.set(values[2])
            self.cb_cap_do.set(values[3])
            self.txt_gia.delete(0, tk.END)
            self.txt_gia.insert(0, values[4].replace("₫", "").replace(".", "").strip())
    
    def them_mon(self):
        ten = self.txt_ten.get().strip()
        loai = self.cb_loai.get()
        cap_do = self.get_cap_do_value(self.cb_cap_do.get())
        gia = utils.parse_vnd(self.txt_gia.get())
        
        if not ten or not loai or gia <= 0:
            messagebox.showerror("Lỗi", "Vui lòng nhập đầy đủ thông tin!")
            return
        
        mon = Mon(0, ten, loai, cap_do, gia, True)
        MonDAO.add(mon)
        self.load_menu()
        self.clear_input()
        messagebox.showinfo("Thành công", "Thêm món thành công!")
    
    def sua_mon(self):
        selection = self.tree.selection()
        if not selection:
            messagebox.showerror("Lỗi", "Vui lòng chọn món cần sửa!")
            return
        
        item = self.tree.item(selection[0])
        mon_id = item['values'][0]
        
        ten = self.txt_ten.get().strip()
        loai = self.cb_loai.get()
        cap_do = self.get_cap_do_value(self.cb_cap_do.get())
        gia = utils.parse_vnd(self.txt_gia.get())
        
        mon = Mon(mon_id, ten, loai, cap_do, gia, True)
        MonDAO.update(mon)
        self.load_menu()
        messagebox.showinfo("Thành công", "Sửa món thành công!")
    
    def xoa_mon(self):
        selection = self.tree.selection()
        if not selection:
            messagebox.showerror("Lỗi", "Vui lòng chọn món cần xóa!")
            return
        
        if messagebox.askyesno("Xác nhận", "Bạn có chắc muốn xóa món này?"):
            item = self.tree.item(selection[0])
            mon_id = item['values'][0]
            MonDAO.delete(mon_id)
            self.load_menu()
            self.clear_input()
            messagebox.showinfo("Thành công", "Xóa món thành công!")
    
    def clear_input(self):
        self.txt_ten.delete(0, tk.END)
        self.cb_loai.set("")
        self.cb_cap_do.set("")
        self.txt_gia.delete(0, tk.END)