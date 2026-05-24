package service;

import dao.NhanVienDao;
import model.NhanVien;

import java.util.List;

public class NhanVienService {
    private final NhanVienDao dao = new NhanVienDao();

    public NhanVien dangNhap(String taiKhoan, String matKhau) {
        return dao.authenticate(taiKhoan, matKhau);
    }

    public List<NhanVien> getAll() {
        return dao.getAll();
    }

    public NhanVien getByTaiKhoan(String taiKhoan) {
        return dao.getByTaiKhoan(taiKhoan);
    }

    public void them(NhanVien nv) {
        dao.add(nv);
    }

    public void capNhat(NhanVien nv) {
        dao.update(nv);
    }

    public void capNhatMatKhau(int id, String matKhauMoi) {
        dao.updatePassword(id, matKhauMoi);
    }

    public void xoa(int id) {
        dao.delete(id);
    }
}
