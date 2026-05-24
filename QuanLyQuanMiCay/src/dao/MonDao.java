package dao;

import model.Mon;
import config.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonDao {
    
    public List<Mon> getAll() {
        List<Mon> list = new ArrayList<>();
        String sql = "SELECT id, ten, loai, cap_do_cay, gia, con_ban FROM mon WHERE con_ban = 1 ORDER BY loai, cap_do_cay, ten";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapMon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Mon> getAllForQuanLy() {
        List<Mon> list = new ArrayList<>();
        String sql = "SELECT id, ten, loai, cap_do_cay, gia, con_ban FROM mon ORDER BY loai, cap_do_cay, ten";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapMon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Mon> getByLoai(String loai) {
        List<Mon> list = new ArrayList<>();
        String sql = "SELECT id, ten, loai, cap_do_cay, gia, con_ban FROM mon WHERE loai = ? AND con_ban = 1 ORDER BY cap_do_cay";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, loai);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                list.add(mapMon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Mon mapMon(ResultSet rs) throws SQLException {
        return new Mon(
            rs.getInt("id"),
            rs.getString("ten"),
            rs.getString("loai"),
            rs.getInt("cap_do_cay"),
            rs.getDouble("gia"),
            rs.getBoolean("con_ban")
        );
    }

    public Mon getById(int id) {
        String sql = "SELECT id, ten, loai, cap_do_cay, gia, con_ban FROM mon WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapMon(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void add(Mon mon) {
        String sql = "INSERT INTO mon (ten, loai, cap_do_cay, gia, con_ban) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, mon.getTen());
            pstmt.setString(2, mon.getLoai());
            pstmt.setInt(3, mon.getCapDoCay());
            pstmt.setDouble(4, mon.getGia());
            pstmt.setBoolean(5, mon.isConBan());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(Mon mon) {
        String sql = "UPDATE mon SET ten = ?, loai = ?, cap_do_cay = ?, gia = ?, con_ban = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, mon.getTen());
            pstmt.setString(2, mon.getLoai());
            pstmt.setInt(3, mon.getCapDoCay());
            pstmt.setDouble(4, mon.getGia());
            pstmt.setBoolean(5, mon.isConBan());
            pstmt.setInt(6, mon.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
        String sql = "DELETE FROM mon WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}