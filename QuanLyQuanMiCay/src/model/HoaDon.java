package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDon {
    private int id;
    private int soBan;
    private int nhanVienId;
    private Date ngayTao;
    private double tongTien;
    private double giamGia;
    private double thanhTien;
    private boolean daThanhToan;
    private List<ChiTietHoaDon> chiTietList;
    
    public HoaDon(int id, int soBan, int nhanVienId, Date ngayTao, double tongTien, double giamGia, double thanhTien, boolean daThanhToan) {
        this.id = id;
        this.soBan = soBan;
        this.nhanVienId = nhanVienId;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
        this.giamGia = giamGia;
        this.thanhTien = thanhTien;
        this.daThanhToan = daThanhToan;
        this.chiTietList = new ArrayList<>();
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSoBan() { return soBan; }
    public void setSoBan(int soBan) { this.soBan = soBan; }
    public int getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(int nhanVienId) { this.nhanVienId = nhanVienId; }
    public Date getNgayTao() { return ngayTao; }
    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public double getGiamGia() { return giamGia; }
    public void setGiamGia(double giamGia) { this.giamGia = giamGia; }
    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
    public boolean isDaThanhToan() { return daThanhToan; }
    public void setDaThanhToan(boolean daThanhToan) { this.daThanhToan = daThanhToan; }
    public List<ChiTietHoaDon> getChiTietList() { return chiTietList; }
    public void setChiTietList(List<ChiTietHoaDon> chiTietList) { this.chiTietList = chiTietList; }
}