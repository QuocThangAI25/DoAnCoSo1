from database import init_database
from views.main_frame import MainFrame

if __name__ == "__main__":
    # Khởi tạo database
    init_database()
    
    # Chạy ứng dụng
    app = MainFrame()
    app.mainloop()