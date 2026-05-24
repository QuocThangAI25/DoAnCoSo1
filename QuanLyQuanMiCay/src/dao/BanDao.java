package dao;

import model.Ban;
import config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BanDao {
    
    public List<Ban> getAll() {
        List<Ban> list = new ArrayList<>();
        String sql = "SELECT so_ban, trang_thai, hoa_don_id FROM ban ORDER BY so_ban";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Ban ban = new Ban(
                    rs.getInt("so_ban"),
                    rs.getString("trang_thai"),
                    rs.getInt("hoa_don_id")
                );
                list.add(ban);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }
    
    public Ban getBySo(int soBan) {
        String sql = "SELECT so_ban, trang_thai, hoa_don_id FROM ban WHERE so_ban = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, soBan);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Ban(
                    rs.getInt("so_ban"),
                    rs.getString("trang_thai"),
                    rs.getInt("hoa_don_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return null;
    }
    
    public void update(Ban ban) {
        String sql = "UPDATE ban SET trang_thai = ?, hoa_don_id = ? WHERE so_ban = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ban.getTrangThai());
            pstmt.setInt(2, ban.getHoaDonId());
            pstmt.setInt(3, ban.getSoBan());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}