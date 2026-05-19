package dao;

import utils.DBConnection;

import java.sql.*;
import java.util.*;

public class ThongKeDao {
    
    public double getDoanhThuTheoNgay(java.sql.Date ngay) {
        String sql = "SELECT COALESCE(SUM(thanh_tien), 0) FROM hoa_don WHERE DATE(ngay_tao) = ? AND da_thanh_toan = 1";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, ngay);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return 0;
    }
    
    public int getSoHoaDonTheoNgay(java.sql.Date ngay) {
        String sql = "SELECT COUNT(*) FROM hoa_don WHERE DATE(ngay_tao) = ? AND da_thanh_toan = 1";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, ngay);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return 0;
    }
    
    public int getTongMonBanTheoNgay(java.sql.Date ngay) {
        String sql = "SELECT COALESCE(SUM(ct.so_luong), 0) FROM chi_tiet_hoa_don ct " +
                     "INNER JOIN hoa_don h ON ct.hoa_don_id = h.id " +
                     "WHERE DATE(h.ngay_tao) = ? AND h.da_thanh_toan = 1";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, ngay);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return 0;
    }
    
    public List<Map.Entry<String, Integer>> getTopMonBanChay(java.sql.Date ngay, int limit) {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT ct.ten_mon, SUM(ct.so_luong) as total " +
                     "FROM chi_tiet_hoa_don ct " +
                     "INNER JOIN hoa_don h ON ct.hoa_don_id = h.id " +
                     "WHERE DATE(h.ngay_tao) = ? AND h.da_thanh_toan = 1 " +
                     "GROUP BY ct.ten_mon " +
                     "ORDER BY total DESC LIMIT ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, ngay);
            pstmt.setInt(2, limit);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                map.put(rs.getString("ten_mon"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        
        return new ArrayList<>(map.entrySet());
    }
}