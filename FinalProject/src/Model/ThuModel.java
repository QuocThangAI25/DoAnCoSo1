package Model;

import java.time.LocalDate;

public class ThuModel {
	    private String maThu;
	    private String maHV;
	    private String tenHV;
	    private String maKH;
	    private String tenKH;
	    private String loaiThu;
	    private String hinhThucThu;
	    private double soTien;
	    private LocalDate ngayThu;
	    private String ghiChu;
	    public ThuModel() {
	       
	    }
		public ThuModel(String maThu, String maHV, String tenHV, String maKH, String tenKH, String loaiThu,
				String hinhThucThu, double soTien, LocalDate ngayThu, String ghiChu) {
			this.maThu = maThu;
			this.maHV = maHV;
			this.tenHV = tenHV;
			this.maKH = maKH;
			this.tenKH = tenKH;
			this.loaiThu = loaiThu;
			this.hinhThucThu = hinhThucThu;
			this.soTien = soTien;
			this.ngayThu = ngayThu;
			this.ghiChu = ghiChu;
		}
		public String getMaThu() {
			return maThu;
		}
		public void setMaThu(String maThu) {
			this.maThu = maThu;
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
		public String getLoaiThu() {
			return loaiThu;
		}
		public void setLoaiThu(String loaiThu) {
			this.loaiThu = loaiThu;
		}
		public String getHinhThucThu() {
			return hinhThucThu;
		}
		public void setHinhThucThu(String hinhThucThu) {
			this.hinhThucThu = hinhThucThu;
		}
		public double getSoTien() {
			return soTien;
		}
		public void setSoTien(double soTien) {
			this.soTien = soTien;
		}
		public LocalDate getNgayThu() {
			return ngayThu;
		}
		public void setNgayThu(LocalDate ngayThu) {
			this.ngayThu = ngayThu;
		}
		public String getGhiChu() {
			return ghiChu;
		}
		public void setGhiChu(String ghiChu) {
			this.ghiChu = ghiChu;
		}


	}

