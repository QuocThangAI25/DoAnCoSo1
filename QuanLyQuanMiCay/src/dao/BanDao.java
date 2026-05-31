package dao;

import model.Ban;
import config.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BanDao {

    public List<Ban> getAll() {
        List<Ban> list = new ArrayList<>();
        // 🟢 Nâng cấp: Lấy thêm cột "tang" và sắp xếp theo tầng
        String sql = "SELECT so_ban, trang_thai, hoa_don_id, tang FROM ban ORDER BY tang, so_ban";
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Ban(rs.getInt("so_ban"), rs.getString("trang_thai"), rs.getInt("hoa_don_id"),
                        rs.getInt("tang")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Ban getBySo(int soBan) {
        String sql = "SELECT so_ban, trang_thai, hoa_don_id, tang FROM ban WHERE so_ban = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, soBan);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Ban(rs.getInt("so_ban"), rs.getString("trang_thai"), rs.getInt("hoa_don_id"),
                        rs.getInt("tang"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Ban ban) {
        String sql = "UPDATE ban SET trang_thai = ?, hoa_don_id = ? WHERE so_ban = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ban.getTrangThai());
            pstmt.setInt(2, ban.getHoaDonId());
            pstmt.setInt(3, ban.getSoBan());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🟢 Nâng cấp hàm insert nhận thêm biến "tang"
    public boolean insert(int soBan, int tang) {
        String sql = "INSERT INTO ban (so_ban, trang_thai, hoa_don_id, tang) VALUES (?, 'Trống', -1, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, soBan);
            pstmt.setInt(2, tang);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    // 🟢 HÀM MỚI: Xóa vĩnh viễn bàn khỏi Database
    public boolean delete(int soBan) {
        String sql = "DELETE FROM ban WHERE so_ban = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, soBan);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🟢 HÀM MỚI: Cập nhật lại số bàn và tầng
    public boolean updateThongTin(int oldSoBan, int newSoBan, int newTang) {
        String sql = "UPDATE ban SET so_ban = ?, tang = ? WHERE so_ban = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newSoBan);
            pstmt.setInt(2, newTang);
            pstmt.setInt(3, oldSoBan);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}