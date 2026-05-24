package model;

public class Ban {
    private int soBan;
    private String trangThai;
    private int hoaDonId;
    
    public Ban(int soBan, String trangThai, int hoaDonId) {
        this.soBan = soBan;
        this.trangThai = trangThai;
        this.hoaDonId = hoaDonId;
    }
    
    public int getSoBan() { return soBan; }
    public void setSoBan(int soBan) { this.soBan = soBan; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public int getHoaDonId() { return hoaDonId; }
    public void setHoaDonId(int hoaDonId) { this.hoaDonId = hoaDonId; }
}