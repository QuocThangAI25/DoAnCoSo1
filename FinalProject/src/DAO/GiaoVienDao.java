package DAO;

import Model.QuanLyGiaoVienModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiaoVienDao {

    // ================= LẤY TẤT CẢ =================
    public List<QuanLyGiaoVienModel> getAll() {
        List<QuanLyGiaoVienModel> list = new ArrayList<>();
        String sql = "SELECT * FROM GiaoVien";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                QuanLyGiaoVienModel gv = new QuanLyGiaoVienModel(
                        rs.getString("MaGV"),
                        rs.getString("TenGV"),
                        rs.getString("SDT"),
                        rs.getString("Email"),
                        rs.getDouble("LuongCoBan")
                );
                list.add(gv);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= THÊM =================
    public void insert(QuanLyGiaoVienModel gv) {
        String sql = "INSERT INTO GiaoVien VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gv.getMaGV());
            ps.setString(2, gv.getTenGV());
            ps.setString(3, gv.getSdt());
            ps.setString(4, gv.getEmail());
            ps.setDouble(5, gv.getLuong());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ================= SỬA =================
    public void update(QuanLyGiaoVienModel gv) {
        String sql = """
                UPDATE GiaoVien
                SET TenGV = ?, SDT = ?, Email = ?, LuongCoBan = ?
                WHERE MaGV = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gv.getTenGV());
            ps.setString(2, gv.getSdt());
            ps.setString(3, gv.getEmail());
            ps.setDouble(4, gv.getLuong());
            ps.setString(5, gv.getMaGV());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ================= XÓA =================
    public void delete(String maGV) {
        String sql = "DELETE FROM GiaoVien WHERE MaGV = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGV);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ================= TÌM THEO MÃ =================
    public QuanLyGiaoVienModel findById(String maGV) {
        String sql = "SELECT * FROM GiaoVien WHERE MaGV = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new QuanLyGiaoVienModel(
                        rs.getString("MaGV"),
                        rs.getString("TenGV"),
                        rs.getString("SDT"),
                        rs.getString("Email"),
                        rs.getDouble("LuongCoBan")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<QuanLyGiaoVienModel> sortByName() {
        List<QuanLyGiaoVienModel> list = new ArrayList<>();
        String sql = "SELECT * FROM GiaoVien ORDER BY tenGV";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new QuanLyGiaoVienModel(
                    rs.getString("maGV"),
                    rs.getString("tenGV"),
                    rs.getString("sdt"),
                    rs.getString("email"),
                    rs.getDouble("luongCoBan")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
