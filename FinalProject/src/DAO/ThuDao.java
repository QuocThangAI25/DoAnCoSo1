package DAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Model.ThuModel;

public class ThuDao {

    /* ================= SINH MÃ ================= */
    public String generateMaThu() {
        String sql = "SELECT MAX(maThu) FROM thu";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getString(1) != null) {
                String max = rs.getString(1).replace("THU", "");
                int num = Integer.parseInt(max) + 1;
                return String.format("THU%03d", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "THU001";
    }
    /* ================= INSERT ================= */
    public boolean insert(ThuModel t) {
        String sql = """
            INSERT INTO thu(maThu, maHV, maKH, loaiThu, hinhThucThu, soTien, ngayThu, ghiChu)
            VALUES (?,?,?,?,?,?,?,?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, t.getMaThu());
            ps.setString(2, t.getMaHV());
            ps.setString(3, t.getMaKH());
            ps.setString(4, t.getLoaiThu());
            ps.setString(5, t.getHinhThucThu());
            ps.setDouble(6, t.getSoTien());
            ps.setDate(7, Date.valueOf(t.getNgayThu()));
            ps.setString(8, t.getGhiChu());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /* ================= UPDATE ================= */
    public boolean update(ThuModel t) {
        String sql = """
            UPDATE thu
            SET maHV=?, maKH=?, loaiThu=?, hinhThucThu=?, soTien=?, ngayThu=?, ghiChu=?
            WHERE maThu=?
        """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getMaHV());
            ps.setString(2, t.getMaKH());
            ps.setString(3, t.getLoaiThu());
            ps.setString(4, t.getHinhThucThu());
            ps.setDouble(5, t.getSoTien());
            ps.setDate(6, Date.valueOf(t.getNgayThu()));
            ps.setString(7, t.getGhiChu());
            ps.setString(8, t.getMaThu());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /* ================= DELETE ================= */
    public boolean delete(String maThu) {
        String sql = "DELETE FROM thu WHERE maThu=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maThu);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /* ================= LOAD TABLE ================= */
    public List<ThuModel> getAll() {
        List<ThuModel> list = new ArrayList<>();
        String sql = "SELECT t.maThu, t.loaiThu, t.maHV, hv.tenHV, t.maKH, kh.tenKH, t.hinhThucThu, t.ngayThu, t.soTien, t.ghiChu "
                   + "FROM thu t "
                   + "LEFT JOIN hocvien hv ON t.maHV = hv.maHV "
                   + "LEFT JOIN khoahoc  kh ON t.maKH = kh.maKH "
                   + "ORDER BY t.ngayThu DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ThuModel thu = new ThuModel();
                thu.setMaThu(rs.getString("maThu"));
                thu.setLoaiThu(rs.getString("loaiThu"));
                thu.setMaHV(rs.getString("maHV"));
                thu.setTenHV(rs.getString("tenHV")); // có thể null nếu maHV null hoặc không tìm thấy
                thu.setMaKH(rs.getString("maKH"));
                thu.setTenKH(rs.getString("tenKH"));
                Date d = rs.getDate("ngayThu");
                thu.setNgayThu(d != null ? d.toLocalDate() : null);
                thu.setSoTien(rs.getDouble("soTien"));
                thu.setHinhThucThu(rs.getString("hinhThucThu"));
                thu.setGhiChu(rs.getString("ghiChu"));
                list.add(thu);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public ThuModel findByMaThu(String maThu) {
        String sql = "SELECT * FROM thu WHERE maThu = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maThu);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ThuModel t = new ThuModel();
                t.setMaThu(rs.getString("maThu"));
                t.setLoaiThu(rs.getString("loaiThu"));
                t.setMaHV(rs.getString("maHV"));
                t.setMaKH(rs.getString("maKH"));
                t.setHinhThucThu(rs.getString("hinhThucThu"));
                t.setNgayThu(rs.getDate("ngayThu").toLocalDate());
                t.setSoTien(rs.getDouble("soTien"));
                t.setGhiChu(rs.getString("ghiChu"));
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<ThuModel> sortByMaThuASC() {
        List<ThuModel> list = new ArrayList<>();

        String sql = """
            SELECT t.maThu, t.loaiThu,
                   t.maHV, hv.tenHV,
                   t.maKH, kh.tenKH,
                   t.hinhThucThu, t.ngayThu, t.soTien, t.ghiChu
            FROM thu t
            LEFT JOIN hocvien hv ON t.maHV = hv.maHV
            LEFT JOIN khoahoc kh ON t.maKH = kh.maKH
            ORDER BY t.maThu ASC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ThuModel t = new ThuModel();
                t.setMaThu(rs.getString("maThu"));
                t.setLoaiThu(rs.getString("loaiThu"));

                t.setMaHV(rs.getString("maHV"));
                t.setTenHV(rs.getString("tenHV"));   // ✅ CÓ GIÁ TRỊ

                t.setMaKH(rs.getString("maKH"));
                t.setTenKH(rs.getString("tenKH"));   // ✅ CÓ GIÁ TRỊ

                t.setHinhThucThu(rs.getString("hinhThucThu"));
                t.setNgayThu(rs.getDate("ngayThu").toLocalDate());
                t.setSoTien(rs.getDouble("soTien"));
                t.setGhiChu(rs.getString("ghiChu"));

                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}


