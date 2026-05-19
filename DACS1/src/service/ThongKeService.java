package service;

import dao.ThongKeDao;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ThongKeService {
    private static ThongKeDao thongKeDAO = new ThongKeDao();
    
    public static double getDoanhThu(Date ngay) {
        return thongKeDAO.getDoanhThuTheoNgay(new java.sql.Date(ngay.getTime()));
    }
    
    public static int getSoHoaDon(Date ngay) {
        return thongKeDAO.getSoHoaDonTheoNgay(new java.sql.Date(ngay.getTime()));
    }
    
    public static int getTongMonBan(Date ngay) {
        return thongKeDAO.getTongMonBanTheoNgay(new java.sql.Date(ngay.getTime()));
    }
    
    public static List<Map.Entry<String, Integer>> getTopMonBanChay(Date ngay, int limit) {
        return thongKeDAO.getTopMonBanChay(new java.sql.Date(ngay.getTime()), limit);
    }
}