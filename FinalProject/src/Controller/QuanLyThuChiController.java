//package Controller;
//
//import DAO.HocVienDao;
//import DAO.ThuChiDao;
//import Model.QuanLyHocVienModel;
//import Model.QuanLyThuChiModel;
//import ViewNew.QuanLyThuChiView;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.event.*;
//
//public class QuanLyThuChiController {
//
//    private QuanLyThuChiView view;
//    private ThuChiDao dao;
//    private HocVienDao hvDao;
//
//    public QuanLyThuChiController(QuanLyThuChiView view) {
//        this.view = view;
//        dao = new ThuChiDao();
//        hvDao = new HocVienDao();
//
//        loadComboHocVien();
//        loadTable();
//        initController();
//    }
//
//    // ================= INIT =================
//    private void initController() {
//
//        // THÊM
//        view.getbtthem().addActionListener(e -> {
//            dao.insert(getForm());
//            loadTable();
//            clearForm();
//        });
//
//        // SỬA
//        view.getbtsua().addActionListener(e -> {
//            dao.update(getForm());
//            loadTable();
//        });
//
//        // XÓA
//        view.getbtxoa().addActionListener(e -> {
//            dao.delete(view.getTxtMaGD().getText());
//            loadTable();
//            clearForm();
//        });
//
//        // ĐỔI MỚI
//        view.getbtdoimoi().addActionListener(e -> clearForm());
//
//        // CHỌN MÃ HV → HIỆN TÊN
//        view.getCbMaHV().addActionListener(e -> {
//            String maHV = view.getCbMaHV().getSelectedItem().toString();
//            QuanLyHocVienModel hv = hvDao.findById(maHV);
//            if (hv != null) {
//                view.getTxtTenHV().setText(hv.getTenHV());
//            }
//        });
//
//        // CLICK TABLE → ĐỔ FORM
//        view.getTable().addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                int row = view.getTable().getSelectedRow();
//                setFormFromTable(row);
//            }
//        });
//    }
//
//    // ================= FORM =================
//    private QuanLyThuChiModel getForm() {
//        return new QuanLyThuChiModel(
//                view.getTxtMaGD().getText(),
//                view.getTxtNgay().getText(),
//                view.getCbLoai().getSelectedItem().toString(),
//                view.getCbDanhMuc().getSelectedItem().toString(),
//                view.getCbMaHV().getSelectedItem().toString(),
//                Double.parseDouble(view.getTxtSoTien().getText()),
//                view.getTxtGhiChu().getText()
//        );
//    }
//
//    private void setFormFromTable(int row) {
//        DefaultTableModel model = view.getTableModel();
//
//        view.getTxtMaGD().setText(model.getValueAt(row, 0).toString());
//        view.getTxtNgay().setText(model.getValueAt(row, 1).toString());
//        view.getCbLoai().setSelectedItem(model.getValueAt(row, 2));
//        view.getCbDanhMuc().setSelectedItem(model.getValueAt(row, 3));
//        view.getTxtSoTien().setText(model.getValueAt(row, 4).toString());
//        view.getTxtGhiChu().setText(model.getValueAt(row, 5).toString());
//    }
//
//    private void clearForm() {
//        view.getTxtMaGD().setText("");
//        view.getTxtNgay().setText("");
//        view.getTxtSoTien().setText("");
//        view.getTxtGhiChu().setText("");
//        view.getTxtTenHV().setText("");
//    }
//
//    // ================= LOAD DATA =================
//    private void loadTable() {
//        DefaultTableModel model = view.getTableModel();
//        model.setRowCount(0);
//
//        for (QuanLyThuChiModel tc : dao.getAll()) {
//            model.addRow(new Object[]{
//                    tc.getMaGD(),
//                    tc.getNgay(),
//                    tc.getLoai(),
//                    tc.getMaHV(),
//                    tc.getSoTien(),
//                    tc.getNoiDung()
//            });
//        }
//        System.out.println("Số dòng: " + dao.getAll().size());
//
//    }
//
//    private void loadComboHocVien() {
//        view.getCbMaHV().removeAllItems();
//        for (QuanLyHocVienModel hv : hvDao.getAll()) {
//            view.getCbMaHV().addItem(hv.getMaHV());
//        }
//    }
//}

