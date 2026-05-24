package service;

import dao.MonDao;
import model.Mon;

import java.util.List;

public class MonService {
    private final MonDao dao = new MonDao();

    public List<Mon> getAll() {
        return dao.getAll();
    }

    public List<Mon> getAllForQuanLy() {
        return dao.getAllForQuanLy();
    }

    public List<Mon> getByLoai(String loai) {
        return dao.getByLoai(loai);
    }

    public Mon getById(int id) {
        return dao.getById(id);
    }

    public void them(Mon mon) {
        dao.add(mon);
    }

    public void capNhat(Mon mon) {
        dao.update(mon);
    }

    public void xoa(int id) {
        dao.delete(id);
    }
}
