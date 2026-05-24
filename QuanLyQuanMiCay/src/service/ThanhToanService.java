package service;

import dao.HoaDonDao;
import model.ChiTietHoaDon;
import model.HoaDon;
import model.Mon;
import java.util.List;

public class ThanhToanService {
    private static HoaDonDao hoaDonDAO = new HoaDonDao();
    
    public static void themMonVaoHoaDon(int hoaDonId, Mon mon, int soLuong) {
        double thanhTien = mon.getGia() * soLuong;
        ChiTietHoaDon ct = new ChiTietHoaDon(0, hoaDonId, mon.getId(), mon.getTenHienThi(), soLuong, mon.getGia(), thanhTien);
        hoaDonDAO.addChiTiet(ct);
    }
    
    public static List<ChiTietHoaDon> getChiTietHoaDon(int hoaDonId) {
        HoaDon hd = hoaDonDAO.getById(hoaDonId);
        return hd != null ? hd.getChiTietList() : null;
    }
    
    public static void capNhatSoLuongMon(ChiTietHoaDon ct, int soLuongMoi) {
        ct.setSoLuong(soLuongMoi);
        ct.setThanhTien(ct.getDonGia() * soLuongMoi);
        hoaDonDAO.updateChiTiet(ct);
    }
    
    public static void xoaMonKhoiHoaDon(int ctId, int hoaDonId) {
        hoaDonDAO.deleteChiTiet(ctId, hoaDonId);
    }
    
    public static void capNhatGiamGia(int hoaDonId, double giamGia) {
        hoaDonDAO.setGiamGia(hoaDonId, giamGia);
    }
    
    public static void thanhToan(int hoaDonId) {
        hoaDonDAO.thanhToan(hoaDonId);
    }
    
    public static HoaDon getHoaDon(int hoaDonId) {
        return hoaDonDAO.getById(hoaDonId);
    }
}