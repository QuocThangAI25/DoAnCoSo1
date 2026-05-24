package dao;

import model.NhanVien;
import config.DBConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDao {
    
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }
    
    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT id, ten, tai_khoan, mat_khau, vai_tro, ngay_tao, trang_thai FROM nhan_vien";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                    rs.getInt("id"),
                    rs.getString("ten"),
                    rs.getString("tai_khoan"),
                    rs.getString("mat_khau"),
                    rs.getString("vai_tro"),
                    rs.getTimestamp("ngay_tao"),
                    rs.getBoolean("trang_thai")
                );
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public NhanVien getByTaiKhoan(String taiKhoan) {
        String sql = "SELECT id, ten, tai_khoan, mat_khau, vai_tro, ngay_tao, trang_thai FROM nhan_vien WHERE tai_khoan = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, taiKhoan);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new NhanVien(
                    rs.getInt("id"),
                    rs.getString("ten"),
                    rs.getString("tai_khoan"),
                    rs.getString("mat_khau"),
                    rs.getString("vai_tro"),
                    rs.getTimestamp("ngay_tao"),
                    rs.getBoolean("trang_thai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public NhanVien authenticate(String taiKhoan, String matKhau) {
        NhanVien nv = getByTaiKhoan(taiKhoan);
        if (nv != null && nv.getMatKhau().equals(hashPassword(matKhau)) && nv.isTrangThai()) {
            return nv;
        }
        return null;
    }
    
    public void add(NhanVien nv) {
        String sql = "INSERT INTO nhan_vien (ten, tai_khoan, mat_khau, vai_tro) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nv.getTen());
            pstmt.setString(2, nv.getTaiKhoan());
            pstmt.setString(3, hashPassword(nv.getMatKhau()));
            pstmt.setString(4, nv.getVaiTro());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(NhanVien nv) {
        String sql = "UPDATE nhan_vien SET ten = ?, vai_tro = ?, trang_thai = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nv.getTen());
            pstmt.setString(2, nv.getVaiTro());
            pstmt.setBoolean(3, nv.isTrangThai());
            pstmt.setInt(4, nv.getId());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updatePassword(int id, String newPassword) {
        String sql = "UPDATE nhan_vien SET mat_khau = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, hashPassword(newPassword));
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
        String sql = "DELETE FROM nhan_vien WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public NhanVien getById(int id) {
        String sql = "SELECT id, ten, tai_khoan, mat_khau, vai_tro, ngay_tao, trang_thai FROM nhan_vien WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new NhanVien(
                    rs.getInt("id"),
                    rs.getString("ten"),
                    rs.getString("tai_khoan"),
                    rs.getString("mat_khau"),
                    rs.getString("vai_tro"),
                    rs.getTimestamp("ngay_tao"),
                    rs.getBoolean("trang_thai")
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
}