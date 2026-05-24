package model;

public class ChiTietHoaDon {
    private int id;
    private int hoaDonId;
    private int monId;
    private String tenMon;
    private int soLuong;
    private double donGia;
    private double thanhTien;
    
    public ChiTietHoaDon(int id, int hoaDonId, int monId, String tenMon, int soLuong, double donGia, double thanhTien) {
        this.id = id;
        this.hoaDonId = hoaDonId;
        this.monId = monId;
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getHoaDonId() { return hoaDonId; }
    public void setHoaDonId(int hoaDonId) { this.hoaDonId = hoaDonId; }
    public int getMonId() { return monId; }
    public void setMonId(int monId) { this.monId = monId; }
    public String getTenMon() { return tenMon; }
    public void setTenMon(String tenMon) { this.tenMon = tenMon; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
    public double getThanhTien() { return thanhTien; }
    public void setThanhTien(double thanhTien) { this.thanhTien = thanhTien; }
}