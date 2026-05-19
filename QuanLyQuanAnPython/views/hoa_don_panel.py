import tkinter as tk
from tkinter import ttk, messagebox
from service import ThanhToanService
import utils

class HoaDonPanel(tk.Frame):
    def __init__(self, main_frame):
        super().__init__(main_frame)
        self.main_frame = main_frame
        self.current_hoa_don_id = -1
        self.init_ui()
    
    def init_ui(self):
        # Tiêu đề
        title = tk.Label(self, text="HÓA ĐƠN", font=("Arial", 16, "bold"))
        title.pack(pady=10)
        
        # Treeview để hiển thị chi tiết
        columns = ("STT", "Tên món", "Số lượng", "Đơn giá", "Thành tiền")
        self.tree = ttk.Treeview(self, columns=columns, show="headings", height=15)
        
        for col in columns:
            self.tree.heading(col, text=col)
            self.tree.column(col, width=100 if col == "STT" else 150)
        
        self.tree.pack(fill=tk.BOTH, expand=True, padx=10, pady=5)
        
        # Panel tổng tiền
        total_frame = tk.Frame(self)
        total_frame.pack(fill=tk.X, padx=10, pady=10)
        
        # Tạm tính
        row1 = tk.Frame(total_frame)
        row1.pack(fill=tk.X, pady=2)
        tk.Label(row1, text="Tạm tính:", font=("Arial", 12)).pack(side=tk.LEFT)
        self.lb_tam_tinh = tk.Label(row1, text="0 ₫", font=("Arial", 12))
        self.lb_tam_tinh.pack(side=tk.RIGHT)
        
        # Giảm giá
        row2 = tk.Frame(total_frame)
        row2.pack(fill=tk.X, pady=2)
        tk.Label(row2, text="Giảm giá:", font=("Arial", 12)).pack(side=tk.LEFT)
        self.txt_giam_gia = tk.Entry(row2, width=15)
        self.txt_giam_gia.pack(side=tk.RIGHT)
        self.txt_giam_gia.insert(0, "0")
        
        # Tổng cộng
        row3 = tk.Frame(total_frame)
        row3.pack(fill=tk.X, pady=5)
        tk.Label(row3, text="TỔNG CỘNG:", font=("Arial", 14, "bold")).pack(side=tk.LEFT)
        self.lb_tong_cong = tk.Label(row3, text="0 ₫", font=("Arial", 14, "bold"), fg="red")
        self.lb_tong_cong.pack(side=tk.RIGHT)
        
        # Buttons
        btn_frame = tk.Frame(total_frame)
        btn_frame.pack(fill=tk.X, pady=10)
        
        btn_ap_giam = tk.Button(btn_frame, text="Áp dụng giảm giá", command=self.ap_giam_gia)
        btn_ap_giam.pack(side=tk.LEFT, padx=5)
        
        btn_thanh_toan = tk.Button(btn_frame, text="THANH TOÁN", bg="green", fg="white", command=self.thanh_toan)
        btn_thanh_toan.pack(side=tk.LEFT, padx=5)
        
        btn_in = tk.Button(btn_frame, text="IN HÓA ĐƠN", command=self.in_hoa_don)
        btn_in.pack(side=tk.LEFT, padx=5)
    
    def load_hoa_don(self, hoa_don_id):
        self.current_hoa_don_id = hoa_don_id
        
        # Xóa dữ liệu cũ
        for item in self.tree.get_children():
            self.tree.delete(item)
        
        hoa_don = ThanhToanService.get_hoa_don(hoa_don_id)
        if hoa_don is None:
            return
        
        chi_tiet_list = ThanhToanService.get_chi_tiet_hoa_don(hoa_don_id)
        
        for ct in chi_tiet_list:
            self.tree.insert("", "end", values=(
                ct.id, ct.ten_mon, ct.so_luong, utils.format_vnd(ct.don_gia), utils.format_vnd(ct.thanh_tien)
            ))
        
        self.lb_tam_tinh.config(text=utils.format_vnd(hoa_don.tong_tien))
        self.txt_giam_gia.delete(0, tk.END)
        self.txt_giam_gia.insert(0, str(int(hoa_don.giam_gia)))
        self.lb_tong_cong.config(text=utils.format_vnd(hoa_don.thanh_tien))
    
    def ap_giam_gia(self):
        if self.current_hoa_don_id != -1:
            try:
                giam_gia = utils.parse_vnd(self.txt_giam_gia.get())
                ThanhToanService.cap_nhat_giam_gia(self.current_hoa_don_id, giam_gia)
                self.load_hoa_don(self.current_hoa_don_id)
            except:
                messagebox.showerror("Lỗi", "Vui lòng nhập số tiền hợp lệ!")
    
    def thanh_toan(self):
        if self.current_hoa_don_id != -1:
            if messagebox.askyesno("Xác nhận", "Xác nhận thanh toán?"):
                ThanhToanService.thanh_toan(self.current_hoa_don_id)
                messagebox.showinfo("Thành công", "Thanh toán thành công!")
                self.main_frame.thanh_toan_xong()
                self.current_hoa_don_id = -1
                self.clear_hoa_don()
    
    def in_hoa_don(self):
        if self.current_hoa_don_id != -1:
            hoa_don = ThanhToanService.get_hoa_don(self.current_hoa_don_id)
            content = f"=== HÓA ĐƠN QUÁN MÌ CAY TVT ===\n\n"
            for item in self.tree.get_children():
                values = self.tree.item(item)['values']
                content += f"{values[1]} x{values[2]} {values[4]}\n"
            content += f"\nTạm tính: {self.lb_tam_tinh.cget('text')}"
            content += f"\nGiảm giá: -{self.txt_giam_gia.get()} ₫"
            content += f"\nTỔNG CỘNG: {self.lb_tong_cong.cget('text')}"
            
            messagebox.showinfo("In hóa đơn", content)
    
    def clear_hoa_don(self):
        for item in self.tree.get_children():
            self.tree.delete(item)
        self.lb_tam_tinh.config(text="0 ₫")
        self.txt_giam_gia.delete(0, tk.END)
        self.txt_giam_gia.insert(0, "0")
        self.lb_tong_cong.config(text="0 ₫")