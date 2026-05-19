package Controller;

import DAO.GiaoVienDao;
import Model.QuanLyGiaoVienModel;
import ViewNew.QuanLyGiaoVienView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QuanLyGiaoVienController {

    private QuanLyGiaoVienView view;
    private GiaoVienDao dao = new GiaoVienDao();

    public QuanLyGiaoVienController(QuanLyGiaoVienView view) {
        this.view = view;
        loadTable();
        init();
    }
    // ================= INIT =================
    private void init() {
        // ===== THÊM =====
        view.getthem().addActionListener(e -> {
            try {
                dao.insert(getForm());
                loadTable();
                JOptionPane.showMessageDialog(null, "Thêm giáo viên thành công");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi thêm giáo viên");
            }
        });
        // ===== SỬA =====
        view.getsua().addActionListener(e -> {
            try {
                dao.update(getForm());
                loadTable();
                JOptionPane.showMessageDialog(null, "Cập nhật thành công");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi cập nhật");
            }
        });
        // ===== XÓA =====
        view.getxoa().addActionListener(e -> {
            int row = view.gettable().getSelectedRow();
            if (row >= 0) {
                String maGV = view.getmaGV().getText();
                dao.delete(maGV);
                loadTable();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(null, "Chọn giáo viên cần xóa");
            }
        });
        // ===== ĐỔI MỚI =====
        view.getdoimoi().addActionListener(e -> resetForm());
        // ===== CLICK BẢNG =====
        view.gettable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = view.gettable().getSelectedRow();
                if (row >= 0) {
                    setForm(row);
                }
            }
        });
        view.getTim().addActionListener(e -> {

            String maGV = view.gettim2().getText().trim();

            if (maGV.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nhập mã giáo viên");
                return;
            }
            QuanLyGiaoVienModel gv = dao.findById(maGV);
            if (gv == null) {
                JOptionPane.showMessageDialog(null, "Không tìm thấy giáo viên");
                return;
            }
            view.getmaGV().setText(gv.getMaGV());
            view.gettenGV().setText(gv.getTenGV());
            view.getsdt().setText(gv.getSdt());
            view.getemail().setText(gv.getEmail());
            view.getluong().setText(String.valueOf(gv.getLuong()));
        });
        view.getsapXep().addActionListener(e -> {
            DefaultTableModel model =
                (DefaultTableModel) view.gettable().getModel();
            model.setRowCount(0);
            for (QuanLyGiaoVienModel gv : dao.sortByName()) {
                model.addRow(new Object[]{
                    gv.getMaGV(),
                    gv.getTenGV(),
                    gv.getSdt(),
                    gv.getEmail(),
                    gv.getLuong()
                });
            }
        });
        view.getdoiMoi().addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				resetNhap();
			}
		});
    }
    // ================= LẤY DỮ LIỆU FORM =================
    private QuanLyGiaoVienModel getForm() {
        return new QuanLyGiaoVienModel(
                view.getmaGV().getText(),
                view.gettenGV().getText(),
                view.getsdt().getText(),
                view.getemail().getText(),
                Double.parseDouble(view.getluong().getText())
        );
    }
    // ================= ĐỔ DỮ LIỆU TỪ BẢNG LÊN FORM =================
    private void setForm(int row) {
        DefaultTableModel model =
                (DefaultTableModel) view.gettable().getModel();
        view.getmaGV().setText(model.getValueAt(row, 0).toString());
        view.gettenGV().setText(model.getValueAt(row, 1).toString());
        view.getsdt().setText(model.getValueAt(row, 2).toString());
        view.getemail().setText(model.getValueAt(row, 3).toString());
        view.getluong().setText(model.getValueAt(row, 4).toString());
    }
    // ================= LOAD TABLE =================
    private void loadTable() {
        DefaultTableModel model =
                (DefaultTableModel) view.gettable().getModel();
        model.setRowCount(0);
        for (QuanLyGiaoVienModel gv : dao.getAll()) {
            model.addRow(new Object[]{
                    gv.getMaGV(),
                    gv.getTenGV(),
                    gv.getSdt(),
                    gv.getEmail(),
                    gv.getLuong()
            });
        }
    }
    // ================= RESET FORM =================
    private void resetForm() {
        view.getmaGV().setText("");
        view.gettenGV().setText("");
        view.getsdt().setText("");
        view.getemail().setText("");
        view.getluong().setText("");
        view.gettable().clearSelection();
    }
    private void resetNhap() {
    	view.gettim2().setText("");
    }
}
