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
        ChiTietHoaDon ct = new ChiTietHoaDon(0, hoaDonId, mon.getId(), mon.getTenHienThi(), soLuong, mon.getGia(),
                thanhTien);
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

    public static void xoaHoaDon(int hoaDonId) {
        String sqlDeleteChiTiet = "DELETE FROM chi_tiet_hoa_don WHERE hoa_don_id = ?";
        String sqlDeleteHoaDon = "DELETE FROM hoa_don WHERE id = ?";

        // Sử dụng đúng đường dẫn config.DBConnection.getConnection() của bạn
        try (java.sql.Connection conn = config.DBConnection.getConnection()) {

            // Bước 1: Xóa toàn bộ món ăn trong hóa đơn này trước (Để giải phóng khóa ngoại)
            try (java.sql.PreparedStatement psChiTiet = conn.prepareStatement(sqlDeleteChiTiet)) {
                psChiTiet.setInt(1, hoaDonId);
                psChiTiet.executeUpdate();
            }

            // Bước 2: Xóa tờ hóa đơn chính
            try (java.sql.PreparedStatement psHoaDon = conn.prepareStatement(sqlDeleteHoaDon)) {
                psHoaDon.setInt(1, hoaDonId);
                psHoaDon.executeUpdate();
            }

            System.out.println("✅ Đã hủy thành công Hóa đơn ID: " + hoaDonId);

        } catch (Exception e) {
            System.err.println("❌ Lỗi khi xóa hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==========================================
    // CÁC HÀM XỬ LÝ VOUCHER GIẢM GIÁ
    // ==========================================

    // 1. Kiểm tra mã có hợp lệ và chưa sử dụng không
    public static int kiemTraVoucher(String maVoucher) {
        String sql = "SELECT muc_giam FROM voucher WHERE ma_voucher = ? AND trang_thai = 'Chưa sử dụng'";
        try (java.sql.Connection conn = config.DBConnection.getConnection();
                java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVoucher);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("muc_giam"); // Trả về phần trăm giảm (VD: 10)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // -1 là mã sai hoặc đã bị dùng
    }

    // 2. Cập nhật số tiền được giảm vào Hóa đơn hiện tại
    public static void capNhatTienGiamGia(int hoaDonId, double tienGiam) {
        // Cập nhật giam_gia và tự động tính lại thanh_tien = tong_tien - tienGiam
        String sql = "UPDATE hoa_don SET giam_gia = ?, thanh_tien = tong_tien - ? WHERE id = ?";
        try (java.sql.Connection conn = config.DBConnection.getConnection();
                java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, tienGiam);
            ps.setDouble(2, tienGiam); // Truyền lần 2 để làm phép trừ
            ps.setInt(3, hoaDonId);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Đốt mã: Chuyển trạng thái mã thành Đã sử dụng (Gọi khi thanh toán xong)
    public static void chotVoucher(String maVoucher) {
        String sql = "UPDATE voucher SET trang_thai = 'Đã sử dụng' WHERE ma_voucher = ?";
        try (java.sql.Connection conn = config.DBConnection.getConnection();
                java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVoucher);
            ps.executeUpdate();
            System.out.println("✅ Đã chốt sử dụng Voucher: " + maVoucher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}