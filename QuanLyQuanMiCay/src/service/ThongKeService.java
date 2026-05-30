package service;

import config.DBConnection;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.AbstractMap.SimpleEntry;

public class ThongKeService {

    // 🟢 HÀM 1: Tự động tạo câu lệnh WHERE dựa trên bộ lọc
    // ĐÃ NÂNG CẤP: Thêm bí danh "h." để tránh lỗi trùng lặp cột khi JOIN bảng
    private static String buildWhereClause(String loaiThoiGian, int nhanVienId) {
        StringBuilder sb = new StringBuilder(" WHERE 1=1 ");

        // LƯU Ý QUAN TRỌNG VỀ NGHIỆP VỤ:
        // Nếu database của bạn có cột trạng thái hóa đơn (VD: trang_thai = 'Đã thanh
        // toán')
        // Hãy bỏ comment dòng dưới đây để KHÔNG CỘNG NHẦM tiền của các bàn "Đang phục
        // vụ" (chưa thu tiền) nhé:
        // sb.append(" AND h.trang_thai = 'Đã thanh toán' ");

        // Lọc theo thời gian (Sử dụng hàm YEAR, MONTH, DATE của SQL)
        if ("Theo ngày".equals(loaiThoiGian)) {
            sb.append(" AND DATE(h.ngay_tao) = ? ");
        } else if ("Theo tháng".equals(loaiThoiGian)) {
            sb.append(" AND MONTH(h.ngay_tao) = ? AND YEAR(h.ngay_tao) = ? ");
        } else if ("Theo năm".equals(loaiThoiGian)) {
            sb.append(" AND YEAR(h.ngay_tao) = ? ");
        }

        // Lọc theo Nhân viên (Nếu nhanVienId != -1 tức là không phải xem Tất cả)
        if (nhanVienId != -1) {
            sb.append(" AND h.nhan_vien_id = ? ");
        }
        return sb.toString();
    }

    // 🟢 HÀM 2: Truyền giá trị vào dấu ? của câu lệnh SQL
    // ĐÃ NÂNG CẤP: Trả về vị trí paramIndex tiếp theo để tái sử dụng cho câu lệnh
    // LIMIT cực mượt
    private static int setParameters(PreparedStatement ps, Date date, String loaiThoiGian, int nhanVienId)
            throws SQLException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int paramIndex = 1;

        if ("Theo ngày".equals(loaiThoiGian)) {
            ps.setDate(paramIndex++, new java.sql.Date(date.getTime()));
        } else if ("Theo tháng".equals(loaiThoiGian)) {
            ps.setInt(paramIndex++, cal.get(Calendar.MONTH) + 1);
            ps.setInt(paramIndex++, cal.get(Calendar.YEAR));
        } else if ("Theo năm".equals(loaiThoiGian)) {
            ps.setInt(paramIndex++, cal.get(Calendar.YEAR));
        }

        if (nhanVienId != -1) {
            ps.setInt(paramIndex++, nhanVienId);
        }

        return paramIndex; // Trả về index để các hàm khác có thể nối thêm biến (VD: LIMIT ?)
    }

    public static double getDoanhThu(Date date, String loaiThoiGian, int nhanVienId) {
        String sql = "SELECT SUM(h.thanh_tien) AS total FROM hoa_don h " + buildWhereClause(loaiThoiGian, nhanVienId);
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setParameters(ps, date, loaiThoiGian, nhanVienId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getDouble("total");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getSoHoaDon(Date date, String loaiThoiGian, int nhanVienId) {
        String sql = "SELECT COUNT(*) AS total FROM hoa_don h " + buildWhereClause(loaiThoiGian, nhanVienId);
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setParameters(ps, date, loaiThoiGian, nhanVienId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("total");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getTongMonBan(Date date, String loaiThoiGian, int nhanVienId) {
        String sql = "SELECT SUM(c.so_luong) AS total FROM chi_tiet_hoa_don c " +
                "JOIN hoa_don h ON c.hoa_don_id = h.id " +
                buildWhereClause(loaiThoiGian, nhanVienId);
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setParameters(ps, date, loaiThoiGian, nhanVienId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("total");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Map.Entry<String, Integer>> getTopMonBanChay(Date date, String loaiThoiGian, int nhanVienId,
            int limit) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>();
        String sql = "SELECT c.ten_mon, SUM(c.so_luong) AS total_sl FROM chi_tiet_hoa_don c " +
                "JOIN hoa_don h ON c.hoa_don_id = h.id " +
                buildWhereClause(loaiThoiGian, nhanVienId) +
                " GROUP BY c.ten_mon ORDER BY total_sl DESC LIMIT ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            // 🟢 ĐÃ NÂNG CẤP: Code gọi cực kỳ ngắn gọn nhờ tận dụng hàm setParameters
            int nextIndex = setParameters(ps, date, loaiThoiGian, nhanVienId);
            ps.setInt(nextIndex, limit); // Set giá trị LIMIT vào dấu ? cuối cùng

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new SimpleEntry<>(rs.getString("ten_mon"), rs.getInt("total_sl")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🟢 Hàm lấy danh sách tất cả nhân viên để đổ vào ComboBox
    public static Map<Integer, String> getDanhSachNhanVien() {
        Map<Integer, String> map = new LinkedHashMap<>();
        String sql = "SELECT id, ten FROM nhan_vien";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getInt("id"), rs.getString("ten"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 🟢 HÀM MỚI: Gom nhóm Doanh thu theo thời gian (Giờ/Ngày/Tháng) để vẽ biểu đồ
    public static Map<Integer, Double> getDoanhThuBieuDo(Date date, String loaiThoiGian, int nhanVienId) {
        Map<Integer, Double> map = new LinkedHashMap<>();
        String timeUnit = "";

        if ("Theo ngày".equals(loaiThoiGian))
            timeUnit = "HOUR(h.ngay_tao)";
        else if ("Theo tháng".equals(loaiThoiGian))
            timeUnit = "DAY(h.ngay_tao)";
        else if ("Theo năm".equals(loaiThoiGian))
            timeUnit = "MONTH(h.ngay_tao)";

        // Câu lệnh SQL nhóm theo đơn vị thời gian tương ứng
        String sql = "SELECT " + timeUnit + " AS moc_tg, SUM(h.thanh_tien) AS total FROM hoa_don h " +
                buildWhereClause(loaiThoiGian, nhanVienId) +
                " GROUP BY " + timeUnit + " ORDER BY moc_tg";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setParameters(ps, date, loaiThoiGian, nhanVienId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getInt("moc_tg"), rs.getDouble("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}