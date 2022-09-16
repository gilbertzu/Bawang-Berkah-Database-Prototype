package item;

public class Bawang {
	private String Nama_bawang;
	private int Stock;
	private int price;
	private int grosir;
	private int partai;
	public Bawang(String nama_bawang, int stock, int price, int grosir, int partai) {
		super();
		Nama_bawang = nama_bawang;
		Stock = stock;
		this.price = price;
		this.grosir = grosir;
		this.partai = partai;
	}
	public String getNama_bawang() {
		return Nama_bawang;
	}
	public void setNama_bawang(String nama_bawang) {
		Nama_bawang = nama_bawang;
	}
	public int getStock() {
		return Stock;
	}
	public void setStock(int stock) {
		Stock = stock;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getGrosir() {
		return grosir;
	}
	public void setGrosir(int grosir) {
		this.grosir = grosir;
	}
	public int getPartai() {
		return partai;
	}
	public void setPartai(int partai) {
		this.partai = partai;
	}
	
	
	
}
