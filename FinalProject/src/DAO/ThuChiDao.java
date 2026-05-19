//package DAO;
//
//import Model.QuanLyThuChiModel;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ThuChiDao {
//
//    // THÊM
//    public void insert(QuanLyThuChiModel tc) {
//        String sql = "INSERT INTO thuchi VALUES (?,?,?,?,?,?,?)";
//        try (Connection c = DBConnection.getConnection();
//             PreparedStatement ps = c.prepareStatement(sql)) {
//
//            ps.setString(1, tc.getMaGD());
//            ps.setString(2, tc.getNgay());
//            ps.setString(3, tc.getLoai());
//            ps.setString(4, tc.getDanhMuc());
//            ps.setString(5, tc.getMaHV());
//            ps.setDouble(6, tc.getSoTien());
//            ps.setString(7, tc.getNoiDung());
//            ps.executeUpdate();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // SỬA
//    public void update(QuanLyThuChiModel tc) {
//        String sql = """
//            UPDATE thuchi 
//            SET ngay=?, loai=?, danhmuc=?, maHV=?, sotien=?, ghichu=?
//            WHERE maGD=?""";
//        try (Connection c = DBConnection.getConnection();
//             PreparedStatement ps = c.prepareStatement(sql)) {
//
//            ps.setString(1, tc.getNgay());
//            ps.setString(2, tc.getLoai());
//            ps.setString(3, tc.getDanhMuc());
//            ps.setString(4, tc.getMaHV());
//            ps.setDouble(5, tc.getSoTien());
//            ps.setString(6, tc.getNoiDung());
//            ps.setString(7, tc.getMaGD());
//            ps.executeUpdate();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // XÓA
//    public void delete(String maGD) {
//        String sql = "DELETE FROM thuchi WHERE maGD=?";
//        try (Connection c = DBConnection.getConnection();
//             PreparedStatement ps = c.prepareStatement(sql)) {
//            ps.setString(1, maGD);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // LẤY TẤT CẢ
//    public List<QuanLyThuChiModel> getAll() {
//        List<QuanLyThuChiModel> list = new ArrayList<>();
//        String sql = "SELECT * FROM thuchi";
//        try (Connection c = DBConnection.getConnection();
//             Statement st = c.createStatement();
//             ResultSet rs = st.executeQuery(sql)) {
//
//            while (rs.next()) {
//                list.add(new QuanLyThuChiModel(
//                        rs.getString("maGD"),
//                        rs.getString("ngay"),
//                        rs.getString("loai"),
//                        rs.getString("danhmuc"),
//                        rs.getString("maHV"),
//                        rs.getDouble("sotien"),
//                        rs.getString("ghichu")
//                ));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    // LẤY MÃ HV THEO MÃ GIAO DỊCH
//    public String findMaHVByMaGD(String maGD) {
//        String sql = "SELECT maHV FROM thuchi WHERE maGD=?";
//        try (Connection c = DBConnection.getConnection();
//             PreparedStatement ps = c.prepareStatement(sql)) {
//            ps.setString(1, maGD);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) return rs.getString("maHV");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
