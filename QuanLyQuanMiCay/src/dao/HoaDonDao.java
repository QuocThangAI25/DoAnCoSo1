package dao;

import model.ChiTietHoaDon;
import model.HoaDon;
import config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDao {
    
    public HoaDon create(int soBan, int nhanVienId) {
        String sql = "INSERT INTO hoa_don (so_ban, nhan_vien_id, ngay_tao, tong_tien, giam_gia, thanh_tien, da_thanh_toan) VALUES (?, ?, NOW(), 0, 0, 0, 0)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, soBan);
            pstmt.setInt(2, nhanVienId);
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return getById(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public HoaDon getById(int id) {
        String sql = "SELECT id, so_ban, nhan_vien_id, ngay_tao, tong_tien, giam_gia, thanh_tien, da_thanh_toan FROM hoa_don WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("id"),
                    rs.getInt("so_ban"),
                    rs.getInt("nhan_vien_id"),
                    rs.getTimestamp("ngay_tao"),
                    rs.getDouble("tong_tien"),
                    rs.getDouble("giam_gia"),
                    rs.getDouble("thanh_tien"),
                    rs.getBoolean("da_thanh_toan")
                );
                hd.setChiTietList(getChiTietByHoaDon(id));
                return hd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public HoaDon getByBan(int soBan) {
        String sql = "SELECT id, so_ban, nhan_vien_id, ngay_tao, tong_tien, giam_gia, thanh_tien, da_thanh_toan FROM hoa_don WHERE so_ban = ? AND da_thanh_toan = 0";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, soBan);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getInt("id"),
                    rs.getInt("so_ban"),
                    rs.getInt("nhan_vien_id"),
                    rs.getTimestamp("ngay_tao"),
                    rs.getDouble("tong_tien"),
                    rs.getDouble("giam_gia"),
                    rs.getDouble("thanh_tien"),
                    rs.getBoolean("da_thanh_toan")
                );
                hd.setChiTietList(getChiTietByHoaDon(hd.getId()));
                return hd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private List<ChiTietHoaDon> getChiTietByHoaDon(int hoaDonId) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT id, hoa_don_id, mon_id, ten_mon, so_luong, don_gia, thanh_tien FROM chi_tiet_hoa_don WHERE hoa_don_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hoaDonId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ChiTietHoaDon ct = new ChiTietHoaDon(
                    rs.getInt("id"),
                    rs.getInt("hoa_don_id"),
                    rs.getInt("mon_id"),
                    rs.getString("ten_mon"),
                    rs.getInt("so_luong"),
                    rs.getDouble("don_gia"),
                    rs.getDouble("thanh_tien")
                );
                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public void addChiTiet(ChiTietHoaDon ct) {
        String sql = "INSERT INTO chi_tiet_hoa_don (hoa_don_id, mon_id, ten_mon, so_luong, don_gia, thanh_tien) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ct.getHoaDonId());
            pstmt.setInt(2, ct.getMonId());
            pstmt.setString(3, ct.getTenMon());
            pstmt.setInt(4, ct.getSoLuong());
            pstmt.setDouble(5, ct.getDonGia());
            pstmt.setDouble(6, ct.getThanhTien());
            pstmt.executeUpdate();
            
            updateTongTien(ct.getHoaDonId());
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateChiTiet(ChiTietHoaDon ct) {
        String sql = "UPDATE chi_tiet_hoa_don SET so_luong = ?, thanh_tien = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ct.getSoLuong());
            pstmt.setDouble(2, ct.getThanhTien());
            pstmt.setInt(3, ct.getId());
            pstmt.executeUpdate();
            
            updateTongTien(ct.getHoaDonId());
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteChiTiet(int ctId, int hoaDonId) {
        String sql = "DELETE FROM chi_tiet_hoa_don WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, ctId);
            pstmt.executeUpdate();
            
            updateTongTien(hoaDonId);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void updateTongTien(int hoaDonId) {
        String sql = "UPDATE hoa_don h SET " +
                     "h.tong_tien = (SELECT COALESCE(SUM(thanh_tien), 0) FROM chi_tiet_hoa_don WHERE hoa_don_id = ?), " +
                     "h.thanh_tien = h.tong_tien - h.giam_gia WHERE h.id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hoaDonId);
            pstmt.setInt(2, hoaDonId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void setGiamGia(int hoaDonId, double giamGia) {
        String sql = "UPDATE hoa_don SET giam_gia = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, giamGia);
            pstmt.setInt(2, hoaDonId);
            pstmt.executeUpdate();
            
            updateTongTien(hoaDonId);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void thanhToan(int hoaDonId) {
        String sql = "UPDATE hoa_don SET da_thanh_toan = 1 WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, hoaDonId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}