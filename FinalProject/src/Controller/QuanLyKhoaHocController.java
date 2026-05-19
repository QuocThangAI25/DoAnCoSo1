package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import DAO.KhoaHocDao;
import Model.QuanLyHocVienModel;
import Model.QuanLyKhoaHocModel;
import ViewNew.QuanLyHocVienView;
import ViewNew.QuanLyKhoaHocView;

public class QuanLyKhoaHocController {
    private QuanLyKhoaHocView view;
    private DefaultTableModel model;
    private QuanLyHocVienView hocVienView;
    private KhoaHocDao dao = new KhoaHocDao();
    public QuanLyKhoaHocController(QuanLyKhoaHocView view, QuanLyHocVienView hocVienView) {
        this.view = view;
        this.hocVienView = hocVienView;
        this.model = (DefaultTableModel) view.gettable().getModel();
        loadTable();
        init();
    }
    // ================= LOAD TABLE =================
    private void loadTable() {
        model.setRowCount(0);
        for (QuanLyKhoaHocModel kh : dao.getAll()) {
            model.addRow(new Object[]{
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getHocPhi(),
                kh.getThoiLuong(),
                kh.getNgayKhaiGiang(),
                kh.getTrangThai()
            });
        }
    }
    // ================= INIT CONTROLLER =================
    private void init() {
        // ===== THÊM =====
        view.getbtnthem().addActionListener(e -> {
            try {
                QuanLyKhoaHocModel kh = getForm();
                dao.insert(kh);
                hocVienView.loadKhoaHocToCombo();
                loadTable();
                JOptionPane.showMessageDialog(null, "Thêm thành công");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi thêm khóa học");
            }
        });
        // ===== XÓA =====
        view.getbtnxoa().addActionListener(e -> {
            int row = view.gettable().getSelectedRow();
            if (row >= 0) {
                try {
                    String maKH = model.getValueAt(row, 0).toString();
                    dao.delete(maKH);
                    loadTable();
                    resetForm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi xóa");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Chọn dòng cần xóa");
            }
        });
        // ===== SỬA =====
        view.getbtnsua().addActionListener(e -> {
            int row = view.gettable().getSelectedRow();
            if (row >= 0) {
                try {
                    QuanLyKhoaHocModel kh = getForm();
                    dao.update(kh);
                    loadTable();
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi cập nhật");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Chọn dòng cần sửa");
            }
        });

        // ===== ĐỔI MỚI =====
        view.getbtndoimoi().addActionListener(e -> resetForm());
        view.getReset().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resetNhap();
			}
		});
        // ===== CLICK BẢNG =====
        view.gettable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = view.gettable().getSelectedRow();
                if (row >= 0) {
                    int modelRow = view.gettable()
                            .convertRowIndexToModel(row);
                    DefaultTableModel model =
                            (DefaultTableModel) view.gettable().getModel();
                    // ===== ĐỔ DỮ LIỆU LÊN LABEL =====
                    view.getlbkh().setText(
                            model.getValueAt(modelRow, 0).toString());
                    view.getlbtenkh().setText(
                            model.getValueAt(modelRow, 1).toString());
                    view.getlbhp().setText(
                            model.getValueAt(modelRow, 2).toString());
                    view.getlbtl().setText(
                            model.getValueAt(modelRow, 3).toString());
                    view.getlbngaykg().setText(
                            model.getValueAt(modelRow, 4).toString());
                    view.getlbtt().setText(
                            model.getValueAt(modelRow, 5).toString());
                }
            }
        });
        view.getTim().addActionListener(e -> {
            String maKH = view.gettxTim().getText().trim();
            if (maKH.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nhập mã khóa học");
                return;
            }
            QuanLyKhoaHocModel kh = dao.findById(maKH);
            if (kh == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy khóa học");
                return;
            }
            // 👉 ĐỔ DỮ LIỆU SANG BẢNG BÊN PHẢI (LABEL)
            view.getlbkh().setText(kh.getMaKH());
            view.getlbtenkh().setText(kh.getTenKH());
            view.getlbhp().setText(String.valueOf(kh.getHocPhi()));
            view.getlbtl().setText(String.valueOf(kh.getThoiLuong()));
            view.getlbngaykg().setText(kh.getNgayKhaiGiang());
            view.getlbtt().setText(kh.getTrangThai());
        });

    }
    // ================= LẤY DỮ LIỆU FORM =================
    private QuanLyKhoaHocModel getForm() {
        return new QuanLyKhoaHocModel(
            view.getxtkh().getText(),
            view.getxttenkh().getText(),
            Double.parseDouble(view.getxthp().getText()),
            Integer.parseInt(view.getxttl().getText()),
            view.getxtnkh().getText(),
            view.getCbTrangThai().getSelectedItem().toString()
        );
    }
    // ================= RESET FORM =================
    private void resetForm() {
        view.getxtkh().setText("");
        view.getxttenkh().setText("");
        view.getxthp().setText("");
        view.getxttl().setText("");
        view.getxtnkh().setText("");
        view.getCbTrangThai().setSelectedIndex(0);
        view.gettable().clearSelection();
    }
    public void resetNhap() {
    	view.gettxTim().setText("");
    }
}
