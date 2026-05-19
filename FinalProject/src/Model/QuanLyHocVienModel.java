package Model;

public class QuanLyHocVienModel {

    private String maHV;
    private String tenHV;
    private String lop;
    private String sdt;
    private String email;
    private String maKH;        
    private String hinhThucDong;  

    public QuanLyHocVienModel() {
    }

    public QuanLyHocVienModel(
            String maHV,
            String tenHV,
            String lop,
            String sdt,
            String email,
            String maKH,
            String hinhThucDong
    ) {
        this.maHV = maHV;
        this.tenHV = tenHV;
        this.lop = lop;
        this.sdt = sdt;
        this.email = email;
        this.maKH = maKH;
        this.hinhThucDong = hinhThucDong;
    }


    public String getMaHV() {
        return maHV;
    }

    public void setMaHV(String maHV) {
        this.maHV = maHV;
    }

    public String getTenHV() {
        return tenHV;
    }

    public void setTenHV(String tenHV) {
        this.tenHV = tenHV;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHinhThucDong() {
        return hinhThucDong;
    }

    public void setHinhThucDong(String hinhThucDong) {
        this.hinhThucDong = hinhThucDong;
    }
//    public String toString() {
//    	return tenHV;
//    }
    @Override
    public String toString() {
        return maHV + " - " + tenHV;
    }

}
