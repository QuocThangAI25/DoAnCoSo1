package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Model.ChiModel;
import Model.ThuModel;

public class ChiDao {

    /* ===== 1. SINH MÃ CHI ===== */
    public String generateMaChi() {
        String sql = "SELECT COUNT(*) FROM chi";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                return String.format("CHI%03d", count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "CHI001";
    }

    /* ===== 2. THÊM CHI ===== */
    public boolean insert(ChiModel c) {
        String sql = """
            INSERT INTO chi(maChi, loaiChi, maGV, tenGV, ngayChi, soTien, ghiChu)
            VALUES (?,?,?,?,?,?,?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getMaChi());
            ps.setString(2, c.getLoaiChi());
            ps.setString(3, c.getMaGiaoVien());
            ps.setString(4, c.getTenGiaoVien());
            ps.setDate(5, java.sql.Date.valueOf(c.getNgayChi()));
            ps.setDouble(6, c.getSoTien());
            ps.setString(7, c.getGhiChu());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ===== 3. SỬA CHI ===== */
    public boolean update(ChiModel c) {
        String sql = """
            UPDATE chi SET
                loaiChi = ?,
                maGV = ?,
                tenGV = ?,
                ngayChi = ?,
                soTien = ?,
                ghiChu = ?
            WHERE maChi = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getLoaiChi());
            ps.setString(2, c.getMaGiaoVien());
            ps.setString(3, c.getTenGiaoVien());
            ps.setDate(4, java.sql.Date.valueOf(c.getNgayChi()));
            ps.setDouble(5, c.getSoTien());
            ps.setString(6, c.getGhiChu());
            ps.setString(7, c.getMaChi());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ===== 4. XÓA CHI ===== */
    public boolean delete(String maChi) {
        String sql = "DELETE FROM chi WHERE maChi = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maChi);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
   
    /* ===== 5. LẤY TẤT CẢ ===== */
    public List<ChiModel> getAll() {
        List<ChiModel> list = new ArrayList<>();
        String sql = "SELECT * FROM chi ORDER BY ngayChi DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ChiModel c = new ChiModel();
                c.setMaChi(rs.getString("maChi"));
                c.setLoaiChi(rs.getString("loaiChi"));
                c.setMaGiaoVien(rs.getString("maGV"));
                c.setTenGiaoVien(rs.getString("tenGV"));
                c.setNgayChi(rs.getDate("ngayChi").toLocalDate());
                c.setSoTien(rs.getDouble("soTien"));
                c.setGhiChu(rs.getString("ghiChu"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /* ===== 6. TÌM THEO MÃ CHI ===== */
    public ChiModel findByMaChi(String maChi) {
        String sql = "SELECT * FROM chi WHERE maChi=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maChi);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ChiModel c = new ChiModel();
                c.setMaChi(rs.getString("maChi"));
                c.setLoaiChi(rs.getString("loaiChi"));
                c.setMaGiaoVien(rs.getString("maGV"));   // ⭐
                c.setTenGiaoVien(rs.getString("tenGV")); // ⭐
                c.setNgayChi(rs.getDate("ngayChi").toLocalDate());
                c.setSoTien(rs.getDouble("soTien"));
                c.setGhiChu(rs.getString("ghiChu"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ChiModel> sortByMaChiASC() {
        List<ChiModel> list = new ArrayList<>();
        String sql = "SELECT * FROM chi ORDER BY maChi ASC";

        try (
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                ChiModel c = new ChiModel();
                c.setMaChi(rs.getString("maChi"));
                c.setLoaiChi(rs.getString("loaiChi"));
                c.setMaGiaoVien(rs.getString("maGV"));
                c.setTenGiaoVien(rs.getString("tenGV"));
                c.setNgayChi(rs.getDate("ngayChi").toLocalDate());
                c.setSoTien(rs.getDouble("soTien"));
                c.setGhiChu(rs.getString("ghiChu"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
