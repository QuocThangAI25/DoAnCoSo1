package Model;

import java.sql.Date;
import java.time.LocalDate;

public class ChiModel {
    private String maChi;
    private String loaiChi;
    private String maGiaoVien;
    private String tenGiaoVien;
    private LocalDate ngayChi;
    double soTien;
    private String ghiChu;
    public ChiModel() {
    	
    }
	public ChiModel(String maChi, String loaiChi, String maGiaoVien, String tenGiaoVien, LocalDate ngayChi, double soTien,
			String ghiChu) {
		this.maChi = maChi;
		this.loaiChi = loaiChi;
		this.maGiaoVien = maGiaoVien;
		this.tenGiaoVien = tenGiaoVien;
		this.ngayChi = ngayChi;
		this.soTien = soTien;
		this.ghiChu = ghiChu;
	}
	public String getMaChi() {
		return maChi;
	}
	public void setMaChi(String maChi) {
		this.maChi = maChi;
	}
	public String getLoaiChi() {
		return loaiChi;
	}
	public void setLoaiChi(String loaiChi) {
		this.loaiChi = loaiChi;
	}
	public String getMaGiaoVien() {
		return maGiaoVien;
	}
	public void setMaGiaoVien(String maGiaoVien) {
		this.maGiaoVien = maGiaoVien;
	}
	public String getTenGiaoVien() {
		return tenGiaoVien;
	}
	public void setTenGiaoVien(String tenGiaoVien) {
		this.tenGiaoVien = tenGiaoVien;
	}
	public LocalDate getNgayChi() {
		return ngayChi;
	}
	public void setNgayChi(LocalDate ngayChi) {
		this.ngayChi = ngayChi;
	}
	public double getSoTien() {
		return soTien;
	}
	public void setSoTien(double soTien) {
		this.soTien = soTien;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}


}