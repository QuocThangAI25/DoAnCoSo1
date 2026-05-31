package service;

import dao.BanDao;
import dao.HoaDonDao;
import model.Ban;
import model.HoaDon;
import java.util.List;

public class QuanLyBanService {
    private static BanDao banDAO = new BanDao();
    private static HoaDonDao hoaDonDAO = new HoaDonDao();

    public static List<Ban> getAllBan() {
        return banDAO.getAll();
    }

    public static void updateTrangThaiBan(int soBan, String trangThai, int hoaDonId) {
        Ban ban = banDAO.getBySo(soBan);
        if (ban != null) {
            ban.setTrangThai(trangThai);
            ban.setHoaDonId(hoaDonId);
            banDAO.update(ban);
        }
    }

    public static HoaDon getHoaDonByBan(int soBan) {
        return hoaDonDAO.getByBan(soBan);
    }

    public static HoaDon taoHoaDonMoi(int soBan, int nhanVienId) {
        return hoaDonDAO.create(soBan, nhanVienId);
    }

    // Xử lý logic Thêm Bàn
    public static boolean themBanMoi(int soBan, int tang) {
        Ban banTonTai = banDAO.getBySo(soBan);
        if (banTonTai != null) {
            return false;
        }
        return banDAO.insert(soBan, tang);
    }

    // 🟢 Xóa bàn
    public static boolean xoaBan(int soBan) {
        return banDAO.delete(soBan);
    }

    // 🟢 HÀM MỚI: Xử lý đổi số bàn / chuyển tầng
    public static boolean suaThongTinBan(int oldSoBan, int newSoBan, int newTang) {
        // Nếu người dùng đổi sang số bàn mới, phải kiểm tra xem số mới đó đã có ai dùng
        // chưa
        if (oldSoBan != newSoBan) {
            Ban checkTonTai = banDAO.getBySo(newSoBan);
            if (checkTonTai != null) {
                return false; // Trùng số bàn, báo lỗi
            }
        }
        return banDAO.updateThongTin(oldSoBan, newSoBan, newTang);
    }
}