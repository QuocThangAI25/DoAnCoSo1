package DAO;

import Model.QuanLyKhoaHocModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhoaHocDao {

    // ================= THÊM =================
    public void insert(QuanLyKhoaHocModel kh) {
        String sql = "INSERT INTO khoahoc VALUES (?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, kh.getMaKH());
            ps.setString(2, kh.getTenKH());
            ps.setDouble(3, kh.getHocPhi());
            ps.setInt(4, kh.getThoiLuong());
            ps.setString(5, kh.getNgayKhaiGiang());
            ps.setString(6, kh.getTrangThai());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ================= SỬA =================
    public void update(QuanLyKhoaHocModel kh) {
        String sql = """
            UPDATE khoahoc
            SET tenKH=?, hocPhi=?, thoiLuong=?, ngayKhaiGiang=?, trangThai=?
            WHERE maKH=?""";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, kh.getTenKH());
            ps.setDouble(2, kh.getHocPhi());
            ps.setInt(3, kh.getThoiLuong());
            ps.setString(4, kh.getNgayKhaiGiang());
            ps.setString(5, kh.getTrangThai());
            ps.setString(6, kh.getMaKH());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ================= XÓA =================
    public void delete(String maKH) {
        String sql = "DELETE FROM khoahoc WHERE maKH=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, maKH);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ================= LẤY TẤT CẢ =================
    public List<QuanLyKhoaHocModel> getAll() {
        List<QuanLyKhoaHocModel> list = new ArrayList<>();
        String sql = "SELECT * FROM khoahoc";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new QuanLyKhoaHocModel(
                        rs.getString("maKH"),
                        rs.getString("tenKH"),
                        rs.getDouble("hocPhi"),
                        rs.getInt("thoiLuong"),
                        rs.getString("ngayKhaiGiang"),
                        rs.getString("trangThai")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // ================= TÌM THEO MÃ =================
    public QuanLyKhoaHocModel findById(String maKH) {
        String sql = "SELECT * FROM khoahoc WHERE maKH=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new QuanLyKhoaHocModel(
                        rs.getString("maKH"),
                        rs.getString("tenKH"),
                        rs.getDouble("hocPhi"),
                        rs.getInt("thoiLuong"),
                        rs.getString("ngayKhaiGiang"),
                        rs.getString("trangThai")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public QuanLyKhoaHocModel getKhoaHocByHocVien(String maHV) {
        String sql = """
            SELECT kh.maKH, kh.tenKH, kh.hocPhi
            FROM hocvien hv
            JOIN khoahoc kh ON hv.maKH = kh.maKH
            WHERE hv.maHV = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHV);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                QuanLyKhoaHocModel kh = new QuanLyKhoaHocModel();
                kh.setMaKH(rs.getString("maKH"));
                kh.setTenKH(rs.getString("tenKH"));
                kh.setHocPhi(rs.getDouble("hocPhi"));
                return kh;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
}
