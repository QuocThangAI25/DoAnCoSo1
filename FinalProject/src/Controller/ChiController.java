package Controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import DAO.ChiDao;
import DAO.GiaoVienDao;
import Model.ChiModel;
import Model.QuanLyGiaoVienModel;
import Model.ThuModel;
import Utils.xuatPDF;
//import Utils.xuatPDF;
import ViewNew.QuanLyChiView;

public class ChiController {

    private QuanLyChiView view;
    private ChiDao chiDao;
    private List<QuanLyGiaoVienModel> listGV;
    
    public ChiController(QuanLyChiView view) {
        this.view = view;
        this.chiDao = new ChiDao();
        loadInit();
        initEvent();
    }
    /* ================= LOAD BAN ĐẦU ================= */
    private void loadInit() {
        view.getTxtMaGD().setText(chiDao.generateMaChi());
        view.getTxtNgay().setText(LocalDate.now().toString());

        loadComboGiaoVien();
        loadTable();
    }
    /* ================= LOAD TABLE ================= */
    private void loadTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (ChiModel c : chiDao.getAll()) {
            model.addRow(new Object[]{
                c.getMaChi(),
                c.getLoaiChi(),
                c.getMaGiaoVien(),
                c.getTenGiaoVien(),
                c.getNgayChi(),
                c.getSoTien(),
                c.getGhiChu()
            });
        }
    }
    /* ================= LOAD COMBO GIÁO VIÊN ================= */
    private void loadComboGiaoVien() {
    	GiaoVienDao gvDao = new GiaoVienDao();
        listGV = gvDao.getAll();
        view.getCbMaGV().removeAllItems();
        for (QuanLyGiaoVienModel gv : listGV) {
            view.getCbMaGV().addItem(gv.getMaGV()); // CHỈ MÃ
        }
    }
    /* ================= EVENT ================= */
    private void initEvent() {
    	view.getcbLoai().addActionListener(e -> {
    	    String loai = view.getcbLoai().getSelectedItem().toString();
    	    if (loai.equals("Chi Lương Giáo Viên")) {
    	        view.getCbMaGV().setVisible(true);
    	        view.getTxtMaGD().setVisible(true);
    	        view.getTxtTenGV().setVisible(true);
    	        view.getTxtSoTien().setEditable(false);
    	        String maGV = (String) view.getCbMaGV().getSelectedItem();
    	        if (maGV != null) {
    	            for (QuanLyGiaoVienModel gv : listGV) {
    	                if (gv.getMaGV().equals(maGV)) {
    	                    view.getTxtTenGV().setText(gv.getTenGV());
    	                    view.getTxtSoTien().setText(String.valueOf(gv.getLuong()));
    	                    break;
    	                }
    	            }
    	        }
    	    } else { // CHI TÀI LIỆU
    	        // ẨN giáo viên
    	    	view.getCbMaGV().setVisible(false);
    	        view.getTxtMaGD().setVisible(true);
    	        view.getTxtTenGV().setVisible(false);

    	        view.getTxtSoTien().setEditable(true);
    	        view.getTxtTenGV().setText("");
    	        view.getTxtSoTien().setText("");
    	    }
    	    view.repaint();
    	    view.revalidate();
    	});
    	view.getCbMaGV().addActionListener(e -> {
    	    String maGV = (String) view.getCbMaGV().getSelectedItem();
    	    if (maGV == null) return;
    	    for (QuanLyGiaoVienModel gv : listGV) {
    	        if (gv.getMaGV().equals(maGV)) {
    	            view.getTxtTenGV().setText(gv.getTenGV());
    	            view.getTxtSoTien().setText(String.valueOf(gv.getLuong()));
    	            break;
    	        }
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
        view.getXuatPdf().addActionListener(e -> {

            try {
                String ma = view.getTxtMaGD().getText();
                String loai = view.getcbLoai().getSelectedItem().toString();
                String tenGV = view.getTxtTenGV().getText();
                LocalDate ngay = LocalDate.parse(view.getTxtNgay().getText());
                double soTien = Double.parseDouble(view.getTxtSoTien().getText());
                String ghiChu = view.getTxtGhiChu().getText();

                JFileChooser chooser = new JFileChooser();
                chooser.setSelectedFile(new File("PhieuChi_" + ma + ".pdf"));

                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

                    xuatPDF.xuatPhieuChi(
                            ma, loai, tenGV, ngay, soTien, ghiChu,
                            chooser.getSelectedFile().getAbsolutePath()
                    );

                    JOptionPane.showMessageDialog(null, "Xuất hóa đơn thành công!");
                    
                    // MỞ PDF LUÔN
                    Desktop.getDesktop().open(chooser.getSelectedFile());
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Lỗi xuất PDF!");
                ex.printStackTrace();
            }
        });
        

        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row >= 0) {
                view.getTxtMaGD().setText(
                    view.getTable().getValueAt(row, 0).toString()
                );
                view.getcbLoai().setSelectedItem(
                    view.getTable().getValueAt(row, 1).toString()
                );
                Object maGVObj = view.getTable().getValueAt(row, 2);
                if (maGVObj != null) {
                    selectGiaoVien(maGVObj.toString());
                    view.getCbMaGV().setVisible(true);
                    view.getTxtTenGV().setVisible(true);
                } else {
                    view.getCbMaGV().setVisible(false);
                    view.getTxtTenGV().setVisible(false);
                }

                view.getTxtNgay().setText(
                    view.getTable().getValueAt(row, 4).toString()
                );
                view.getTxtSoTien().setText(
                    view.getTable().getValueAt(row, 5).toString()
                );
                view.getTxtGhiChu().setText(
                    view.getTable().getValueAt(row, 6).toString()
                );
            }
        });

    }
    /* ================= SELECT GIÁO VIÊN THEO MÃ ================= */
    /* ===== SELECT GIÁO VIÊN THEO MÃ ===== */
    private void selectGiaoVien(String maGV) {
        for (int i = 0; i < view.getCbMaGV().getItemCount(); i++) {
            String itemMaGV = view.getCbMaGV().getItemAt(i);

            if (itemMaGV.equals(maGV)) {
                view.getCbMaGV().setSelectedIndex(i);
                break;
            }
        }
    }
    private ChiModel getForm() {
        ChiModel c = new ChiModel();
        c.setMaChi(view.getTxtMaGD().getText());
        c.setLoaiChi(view.getcbLoai().getSelectedItem().toString());
        c.setNgayChi(LocalDate.parse(view.getTxtNgay().getText()));
        c.setSoTien(Double.parseDouble(view.getTxtSoTien().getText()));
        c.setGhiChu(view.getTxtGhiChu().getText());

        if (c.getLoaiChi().equals("Chi Lương Giáo Viên")) {
            String maGV = (String) view.getCbMaGV().getSelectedItem();
            c.setMaGiaoVien(maGV);

            // ⭐ DÒNG QUYẾT ĐỊNH
            for (QuanLyGiaoVienModel gv : listGV) {
                if (gv.getMaGV().equals(maGV)) {
                    c.setTenGiaoVien(gv.getTenGV());
                    break;
                }
            }
        } else {
            c.setMaGiaoVien(null);
            c.setTenGiaoVien(null);
        }
        return c;
    }

    /* ================= CRUD ================= */
    private void them() {
        ChiModel c = getForm();
        if (c != null && chiDao.insert(c)) {
            JOptionPane.showMessageDialog(view, "Thêm thành công");
            loadTable();
            reset();
        }
    }
    private void sua() {
        ChiModel c = getForm();
        if (c != null && chiDao.update(c)) {
            JOptionPane.showMessageDialog(view, "Sửa thành công");
            loadTable();
        }
    }
    private void xoa() {
        if (chiDao.delete(view.getTxtMaGD().getText())) {
            JOptionPane.showMessageDialog(view, "Xóa thành công");
            loadTable();
            reset();
        }
    }
    private void reset() {
        view.getTxtMaGD().setText(chiDao.generateMaChi());
        view.getTxtNgay().setText(LocalDate.now().toString());
        view.getTxtSoTien().setText("");
        view.getTxtGhiChu().setText("");
        view.getTxtTenGV().setText("");

        if (view.getCbMaGV().getItemCount() > 0)
            view.getCbMaGV().setSelectedIndex(0);
    }
    private void timTheoMaGD() {
        String ma = view.getMaGD().getText().trim();

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nhập mã giao dịch cần tìm");
            return;
        }

        ChiModel c = chiDao.findByMaChi(ma);

        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        if (c != null) {
            model.addRow(new Object[]{
                c.getMaChi(),
                c.getLoaiChi(),
                c.getMaGiaoVien(),
                c.getTenGiaoVien(),
                c.getNgayChi(),
                c.getSoTien(),
                c.getGhiChu()
            });
            // ⭐⭐ DÒNG QUAN TRỌNG ⭐⭐
            hienThiLenForm(c);
        } else {
            JOptionPane.showMessageDialog(view, "Không tìm thấy giao dịch");
        }
    }
    private void resetNhap() {
    	view.getMaGD().setText("");
    }
    private boolean isLoadingForm = false;
    private void hienThiLenForm(ChiModel c) {
        view.getTxtMaGD().setText(c.getMaChi());
        view.getcbLoai().setSelectedItem(c.getLoaiChi());

        if (c.getMaGiaoVien() != null) {
            view.getCbMaGV().setSelectedItem(c.getMaGiaoVien());
            view.getTxtTenGV().setText(c.getTenGiaoVien());
        } else {
            view.getCbMaGV().setSelectedIndex(-1);
            view.getTxtTenGV().setText("");
        }

        view.getTxtNgay().setText(c.getNgayChi().toString());
        view.getTxtSoTien().setText(String.valueOf(c.getSoTien()));
        view.getTxtGhiChu().setText(c.getGhiChu());
    }

    private void sapXepTheoMaGD() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);

        for (ChiModel c : chiDao.sortByMaChiASC()) {
            model.addRow(new Object[]{
                c.getMaChi(),
                c.getLoaiChi(),
                c.getMaGiaoVien(),
                c.getTenGiaoVien(),
                c.getNgayChi(),
                c.getSoTien(),
                c.getGhiChu()
            });
        }
    }
   
    

}

