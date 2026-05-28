package model;

public class Mon {
    private int id;
    private String ten;
    private String loai;
    private int capDoCay;
    private double gia;
    private boolean conBan;

    public Mon(int id, String ten, String loai, int capDoCay, double gia, boolean conBan) {
        this.id = id;
        this.ten = ten;
        this.loai = loai;
        this.capDoCay = capDoCay;
        this.gia = gia;
        this.conBan = conBan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getCapDoCay() {
        return capDoCay;
    }

    public void setCapDoCay(int capDoCay) {
        this.capDoCay = capDoCay;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public boolean isConBan() {
        return conBan;
    }

    public void setConBan(boolean conBan) {
        this.conBan = conBan;
    }

    public String getTenHienThi() {
        if (!"Mi Cay".equals(this.loai)) {
            return this.ten;
        }

        String chuThichCapDo = "";
        switch (this.capDoCay) {
            case 0:
                chuThichCapDo = " Cấp 0 (Không cay)";
                break;
            case 7:
                chuThichCapDo = " Cấp 7 (ĐẶC BIỆT)";
                break;
            case 10:
                chuThichCapDo = " (Siêu cay)";
                break;
            default:
                chuThichCapDo = " Cấp " + this.capDoCay;
                break;
        }

        return this.ten + chuThichCapDo;
    }

    @Override
    public String toString() {
        return getTenHienThi();
    }
}