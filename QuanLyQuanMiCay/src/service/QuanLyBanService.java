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
}