package Model;

public class QuanLyKhoaHocModel {
	private String maKH;
	private String tenKH;
	private double hocPhi;
	private int thoiLuong;
	private String ngayKhaiGiang;
	private String trangThai;
	public QuanLyKhoaHocModel() {
		
	}
	public QuanLyKhoaHocModel(String maKH, String tenKH, double hocPhi, int thoiLuong, String ngayKhaiGiang,
			String trangThai) {
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.hocPhi = hocPhi;
		this.thoiLuong = thoiLuong;
		this.ngayKhaiGiang = ngayKhaiGiang;
		this.trangThai = trangThai;
	}
	public String getMaKH() {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public double getHocPhi() {
		return hocPhi;
	}
	public void setHocPhi(double hocPhi) {
		this.hocPhi = hocPhi;
	}
	public int getThoiLuong() {
		return thoiLuong;
	}
	public void setThoiLuong(int thoiLuong) {
		this.thoiLuong = thoiLuong;
	}
	public String getNgayKhaiGiang() {
		return ngayKhaiGiang;
	}
	public void setNgayKhaiGiang(String ngayKhaiGiang) {
		this.ngayKhaiGiang = ngayKhaiGiang;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
//	public String toString() {
//		return tenKH;
//	}
	@Override
	public String toString() {
	    return maKH + " - " + tenKH;
	}

}
