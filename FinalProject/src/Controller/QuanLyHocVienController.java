
package Controller;

import DAO.HocVienDao;
import Model.QuanLyHocVienModel;
import ViewNew.QuanLyHocVienView;

import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class QuanLyHocVienController {

    private QuanLyHocVienView view;
    private HocVienDao dao = new HocVienDao();
    public QuanLyHocVienController(QuanLyHocVienView view) {
        this.view = view;
        loadTable();
        init();
    }

    private void init() {
        view.getthem().addActionListener(e -> {
            dao.insert(getForm());
            loadTable();
        });
        view.getxoa().addActionListener(e -> {
            dao.delete(view.getmahv().getText());
            loadTable();
        });
        view.getsua().addActionListener(e -> {
            dao.update(getForm());
            loadTable();
        });
        view.getdoimoi().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetForm();
				loadTable();
			}
		});
        view.getTim().addActionListener(e -> {
            QuanLyHocVienModel hv = dao.findById(view.getSearch().getText());
            if (hv != null) setForm(hv);
        });
        view.getReset().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetNhap();
			}
		});
        view.getSapXep().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 DefaultTableModel model =
				            (DefaultTableModel) view.gettable().getModel();
				    model.setRowCount(0);
				    for (QuanLyHocVienModel hv : dao.sortByName()) {
				        model.addRow(new Object[]{
				                hv.getMaHV(),
				                hv.getTenHV(),
				                hv.getLop(),
				                hv.getSdt(),
				                hv.getEmail(),
				                hv.getMaKH(),
				                hv.getHinhThucDong()
				        });
				    }
			}
		});
        view.gettable().getSelectionModel()
        .addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                int row = view.gettable().getSelectedRow();

                if (row >= 0) {
                    // ⚠️ QUAN TRỌNG nếu có sort
                    int modelRow = view.gettable()
                            .convertRowIndexToModel(row);
                    DefaultTableModel model =
                            (DefaultTableModel) view.gettable().getModel();
                    view.getmahv().setText(model.getValueAt(modelRow, 0).toString());
                    view.gettenhv().setText(model.getValueAt(modelRow, 1).toString());
                    view.getlop().setText(model.getValueAt(modelRow, 2).toString());
                    view.getsdt().setText(model.getValueAt(modelRow, 3).toString());
                    view.getemail().setText(model.getValueAt(modelRow, 4).toString());
                    view.getCbKhoaHoc().setSelectedItem(
                            model.getValueAt(modelRow, 5).toString()
                        );
                        view.getCbHinhThucDong().setSelectedItem(
                            model.getValueAt(modelRow, 6).toString()
                        );
                }
            }
        });
    }
    private QuanLyHocVienModel getForm() {
        return new QuanLyHocVienModel(
                view.getmahv().getText(),
                view.gettenhv().getText(),
                view.getlop().getText(),
                view.getsdt().getText(),
                view.getemail().getText(),
                view.getCbKhoaHoc().getSelectedItem().toString(),
                view.getCbHinhThucDong().getSelectedItem().toString()
        );
    }
    private void setForm(QuanLyHocVienModel hv) {
        view.getmahv().setText(hv.getMaHV());
        view.gettenhv().setText(hv.getTenHV());
        view.getlop().setText(hv.getLop());
        view.getsdt().setText(hv.getSdt());
        view.getemail().setText(hv.getEmail());
        view.getCbKhoaHoc().getSelectedItem().toString();
        view.getCbHinhThucDong().getSelectedItem().toString();
    }
    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) view.gettable().getModel();
        model.setRowCount(0);
        for (QuanLyHocVienModel hv : dao.getAll()) {
            model.addRow(new Object[]{
                    hv.getMaHV(),
                    hv.getTenHV(),
                    hv.getLop(),
                    hv.getSdt(),
                    hv.getEmail(),
                    hv.getMaKH(),
                    hv.getHinhThucDong()
            });
        }
    }
    private void resetForm() {
        view.getmahv().setText("");
        view.gettenhv().setText("");
        view.getlop().setText("");
        view.getsdt().setText("");
        view.getemail().setText("");
        view.gettable().clearSelection();
    }
    private void resetNhap() {
    	view.getSearch().setText("");
    }
}

