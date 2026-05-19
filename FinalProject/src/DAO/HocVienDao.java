package DAO;

import Model.QuanLyHocVienModel;
import Model.QuanLyKhoaHocModel;

import java.sql.*;
import java.util.*;

public class HocVienDao {

    public boolean insert(QuanLyHocVienModel hv) {
        String sql = "INSERT INTO hocvien VALUES (?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, hv.getMaHV());
            ps.setString(2, hv.getTenHV());
            ps.setString(3, hv.getLop());
            ps.setString(4, hv.getSdt());
            ps.setString(5, hv.getEmail());
            ps.setString(6, hv.getMaKH());
            ps.setString(7, hv.getHinhThucDong());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(QuanLyHocVienModel hv) {
        String sql = "UPDATE hocvien SET maHV=?, tenHV=?, maLop=?, sdt=?, email=?, maKH=?, hinhThucDong=? WHERE maHV=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
        	ps.setString(1, hv.getMaHV());
            ps.setString(2, hv.getTenHV());
            ps.setString(3, hv.getLop());
            ps.setString(4, hv.getSdt());
            ps.setString(5, hv.getEmail());
            ps.setString(6, hv.getMaKH());
            ps.setString(7, hv.getHinhThucDong());


            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(String maHV) {
        String sql = "DELETE FROM hocvien WHERE maHV=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, maHV);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public QuanLyHocVienModel findById(String maHV) {
        String sql = "SELECT * FROM hocvien WHERE maHV=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, maHV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new QuanLyHocVienModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );
            }
        } catch (Exception e) {}
        return null;
    }

    public List<QuanLyHocVienModel> getAll() {
        List<QuanLyHocVienModel> list = new ArrayList<>();
        String sql = "SELECT * FROM hocvien";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new QuanLyHocVienModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                ));
            }
        } catch (Exception e) {}
        return list;
    }

      public List<QuanLyHocVienModel> sortByName() {
        List<QuanLyHocVienModel> list = new ArrayList<>();
        String sql = "SELECT * FROM hocvien ORDER BY maHV ASC";
        try (Connection c = DBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new QuanLyHocVienModel(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                ));
            }
        } catch (Exception e) {}
        return list;
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

