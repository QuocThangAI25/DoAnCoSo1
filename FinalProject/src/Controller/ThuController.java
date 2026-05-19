package Controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import DAO.HocVienDao;
import DAO.KhoaHocDao;
import DAO.ThuDao;
import Model.ChiModel;
import Model.QuanLyGiaoVienModel;
import Model.QuanLyHocVienModel;
import Model.QuanLyKhoaHocModel;
import Model.ThuModel;
import Utils.xuatPDFThu;
import ViewNew.QuanLyThuView;

public class ThuController {

    private QuanLyThuView view;
    private ThuDao thuDao;
    private HocVienDao hocVienDao;

    public ThuController(QuanLyThuView view) {
        this.view = view;
        this.thuDao = new ThuDao();
        this.hocVienDao = new HocVienDao();
       

        loadInit();
        initEvent();
    }

    /* ================= LOAD BAN ĐẦU ================= */
    private void loadInit() {
        view.getTxtMaGD().setText(thuDao.generateMaThu());
        view.getTxtNgay().setText(LocalDate.now().toString());

        loadComboHocVien();
        loadComboKhoaHoc();
        loadTable();
    }

    /* ================= LOAD TABLE ================= */
    private void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        for (ThuModel t : thuDao.getAll()) {
            model.addRow(new Object[]{
                t.getMaThu(),
                t.getLoaiThu(),
                t.getMaHV(),
                t.getTenHV(),
                t.getMaKH(),
                t.getTenKH(),
                t.getHinhThucThu(),
                t.getNgayThu(),
                t.getSoTien(),
                t.getGhiChu()
            });
        }
    }

    /* ================= LOAD COMBO ================= */
    private void loadComboHocVien() {
        view.getCbMaHV().removeAllItems();
        for (QuanLyHocVienModel hv : hocVienDao.getAll()) {
            view.getCbMaHV().addItem(hv); // ADD OBJECT
        }
    }

    private void loadComboKhoaHoc() {
        view.getCbMaKH().removeAllItems();
        KhoaHocDao dao = new KhoaHocDao();

        for (QuanLyKhoaHocModel kh : dao.getAll()) {
            view.getCbMaKH().addItem(kh);
        }
    }


    /* ================= EVENT ================= */
    private void initEvent() {
    	view.getCbMaHV().addActionListener(e -> {
    	    QuanLyHocVienModel hv =
    	        (QuanLyHocVienModel) view.getCbMaHV().getSelectedItem();

    	    if (hv == null) return;

    	    view.getTxtTenHV().setText(hv.getTenHV());
    	    view.getTxtHinhThucThu().setText(hv.getHinhThucDong());

    	    selectKhoaHoc(hv.getMaKH());
    	});
    	view.getCbMaKH().addActionListener(e -> {
    	    QuanLyKhoaHocModel kh =
    	        (QuanLyKhoaHocModel) view.getCbMaKH().getSelectedItem();

    	    if (kh == null) return;

    	    view.getTxtTenKH().setText(kh.getTenKH());
    	    String loai = view.getCbLoaiThu().getSelectedItem().toString();
    	    if(loai.equals("Thu Tiền In Tài Liệu")) {
    	        view.getTxtSoTien().setEditable(true);
    	    	view.getTxtSoTien().setText("");
    	    }
    	    else {
        	    view.getTxtSoTien().setText(String.valueOf(kh.getHocPhi()));

    	    }
    	});
    	
        /* ===== BUTTON ===== */
        view.getbtthem().addActionListener(e -> them());
        view.getbtsua().addActionListener(e -> sua());
        view.getbtxoa().addActionListener(e -> xoa());
        view.getbtdoimoi().addActionListener(e -> reset());
        view.getTim().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				timTheoMaGD();
				
			}
		});
        view.getLamMoi().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resetNhap();
				
			}
		});
        view.getSapXep().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sapXepTheoMaGD();
				
			}
		});
        view.getXuatHoaDon().addActionListener(e -> {
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setSelectedFile(new File("PhieuThu_" + view.getTxtMaGD().getText() + ".pdf"));

                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    xuatPDFThu.xuatPhieuThu(
                        view.getTxtMaGD().getText(),
                        view.getCbLoaiThu().getSelectedItem().toString(),
                        view.getTxtTenHV().getText(),
                        LocalDate.parse(view.getTxtNgay().getText()),
                        Double.parseDouble(view.getTxtSoTien().getText()),
                        view.getTxtGhiChu().getText(),
                        chooser.getSelectedFile().getAbsolutePath()
                    );

                    Desktop.getDesktop().open(chooser.getSelectedFile());
                    JOptionPane.showMessageDialog(null, "Xuất phiếu thu thành công!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        /* ===== CLICK TABLE ===== */
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row >= 0) {

                view.getTxtMaGD().setText(view.getTable().getValueAt(row, 0).toString());
                selectHocVien(view.getTable().getValueAt(row, 2).toString());
                selectKhoaHoc(view.getTable().getValueAt(row, 4).toString());

                view.getTxtNgay().setText(view.getTable().getValueAt(row, 7).toString());
                view.getTxtSoTien().setText(view.getTable().getValueAt(row, 8).toString());
                view.getTxtGhiChu().setText(view.getTable().getValueAt(row, 9).toString());
            }
        });
    }

    /* ================= SELECT COMBO THEO MÃ ================= */
    private void selectHocVien(String maHV) {
        for (int i = 0; i < view.getCbMaHV().getItemCount(); i++) {
            QuanLyHocVienModel hv = view.getCbMaHV().getItemAt(i);
            if (hv.getMaHV().equals(maHV)) {
                view.getCbMaHV().setSelectedIndex(i);
                break;
            }
        }
    }

    private void selectKhoaHoc(String maKH) {
        for (int i = 0; i < view.getCbMaKH().getItemCount(); i++) {
            QuanLyKhoaHocModel kh = view.getCbMaKH().getItemAt(i);
            if (kh.getMaKH().equals(maKH)) {
                view.getCbMaKH().setSelectedIndex(i);
                break;
            }
        }
    }


    /* ================= LẤY FORM ================= */
    private ThuModel getForm() {
        QuanLyHocVienModel hv =
            (QuanLyHocVienModel) view.getCbMaHV().getSelectedItem();
        QuanLyKhoaHocModel kh =
            (QuanLyKhoaHocModel) view.getCbMaKH().getSelectedItem();

        if (hv == null || kh == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn học viên và khóa học");
            return null;
        }

        ThuModel t = new ThuModel();
        t.setMaThu(view.getTxtMaGD().getText());
        t.setLoaiThu(view.getCbLoaiThu().getSelectedItem().toString());
        t.setMaHV(hv.getMaHV());
        t.setTenHV(hv.getTenHV());
        t.setMaKH(kh.getMaKH());
        t.setTenKH(kh.getTenKH());
        t.setHinhThucThu(view.getTxtHinhThucThu().getText());
        t.setNgayThu(LocalDate.parse(view.getTxtNgay().getText()));
        t.setSoTien(Double.parseDouble(view.getTxtSoTien().getText()));
        t.setGhiChu(view.getTxtGhiChu().getText());

        return t;
    }

    /* ================= CRUD ================= */
    private void them() {
        ThuModel t = getForm();
        if (t != null && thuDao.insert(t)) {
            JOptionPane.showMessageDialog(view, "Thêm thành công");
            loadTable();
            reset();
        }
    }

    private void sua() {
        ThuModel t = getForm();
        if (t != null && thuDao.update(t)) {
            JOptionPane.showMessageDialog(view, "Sửa thành công");
            loadTable();
        }
    }

    private void xoa() {
        if (thuDao.delete(view.getTxtMaGD().getText())) {
            JOptionPane.showMessageDialog(view, "Xóa thành công");
            loadTable();
            reset();
        }
    }

    private void reset() {
        view.getTxtMaGD().setText(thuDao.generateMaThu());
        view.getTxtNgay().setText(LocalDate.now().toString());
        view.getTxtSoTien().setText("");
        view.getTxtGhiChu().setText("");
        view.getTxtTenHV().setText("");
        view.getTxtTenKH().setText("");
        view.getTxtHinhThucThu().setText("");

        if (view.getCbMaHV().getItemCount() > 0)
            view.getCbMaHV().setSelectedIndex(0);
        if (view.getCbMaKH().getItemCount() > 0)
            view.getCbMaKH().setSelectedIndex(0);
    }
    private void timTheoMaGD() {
        String ma = view.getTimKiem().getText().trim();

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nhập mã giao dịch cần tìm");
            return;
        }

        ThuModel t = thuDao.findByMaThu(ma);

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        if (t != null) {
            model.addRow(new Object[]{
                t.getMaThu(),
                t.getLoaiThu(),
                t.getMaHV(),
                "",
                t.getMaKH(),
                "",
                t.getHinhThucThu(),
                t.getNgayThu(),
                t.getSoTien(),
                t.getGhiChu()
            });
            hienThiLenForm(t);
        } else {
            JOptionPane.showMessageDialog(view, "Không tìm thấy mã giao dịch");
        }
    }
    private void hienThiLenForm(ThuModel t) {
        view.getTxtMaGD().setText(t.getMaThu());
        view.getCbLoaiThu().setSelectedItem(t.getLoaiThu());

        if (t.getMaHV() != null) {
            selectHocVien(t.getMaHV());
            view.getTxtTenHV().setText(t.getTenHV());
        } else {
            view.getCbMaHV().setSelectedIndex(-1);
            view.getTxtTenHV().setText("");
        }

        if (t.getMaKH() != null) {
            selectKhoaHoc(t.getMaKH());
            view.getTxtTenKH().setText(t.getTenKH());
        } else {
            view.getCbMaKH().setSelectedIndex(-1);
            view.getTxtTenKH().setText("");
        }

        view.getTxtHinhThucThu().setText(t.getHinhThucThu());
        view.getTxtNgay().setText(t.getNgayThu().toString());
        view.getTxtSoTien().setText(String.valueOf(t.getSoTien()));
        view.getTxtGhiChu().setText(t.getGhiChu());
    }


    private void resetNhap() {
    	view.getTimKiem().setText("");
    }
    private void sapXepTheoMaGD() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        for (ThuModel t : thuDao.sortByMaThuASC()) {
            model.addRow(new Object[]{
                t.getMaThu(),
                t.getLoaiThu(),
                t.getMaHV(),
                t.getTenHV(),
                t.getMaKH(),
                t.getTenKH(),
                t.getHinhThucThu(),
                t.getNgayThu(),
                t.getSoTien(),
                t.getGhiChu()
            });
        }
    }

}
