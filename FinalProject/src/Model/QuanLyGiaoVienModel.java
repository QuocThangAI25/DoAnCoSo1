package Model;

public class QuanLyGiaoVienModel {
	private String maGV;
	private String tenGV;
	private String sdt;
	private String email;
	private double luong;
	public QuanLyGiaoVienModel(){
		
	}
	public QuanLyGiaoVienModel(String maGV, String tenGV, String sdt, String email, double luong) {
		this.maGV = maGV;
		this.tenGV = tenGV;
		this.sdt = sdt;
		this.email = email;
		this.luong = luong;
	}
	public String getMaGV() {
		return maGV;
	}
	public void setMaGV(String maGV) {
		this.maGV = maGV;
	}
	public String getTenGV() {
		return tenGV;
	}
	public void setTenGV(String tenGV) {
		this.tenGV = tenGV;
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
	public double getLuong() {
		return luong;
	}
	public void setLuong(double luong) {
		this.luong = luong;
	}
	@Override
	public String toString() {
	    return maGV;
	}
	
}
