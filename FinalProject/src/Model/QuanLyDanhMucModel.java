package Model;

public class QuanLyDanhMucModel {
	private String maDM;
	private String tenDM;
	private String loai;
	public QuanLyDanhMucModel(String maDM, String tenDM, String loai) {
		this.maDM = maDM;
		this.tenDM = tenDM;
		this.loai = loai;
	}
	public String getMaDM() {
		return maDM;
	}
	public void setMaDM(String maDM) {
		this.maDM = maDM;
	}
	public String getTenDM() {
		return tenDM;
	}
	public void setTenDM(String tenDM) {
		this.tenDM = tenDM;
	}
	public String getLoai() {
		return loai;
	}
	public void setLoai(String loai) {
		this.loai = loai;
	}

}
