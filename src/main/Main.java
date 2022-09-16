package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

import connect.Connect;
import facade.Facade;
import item.Bawang;

public class Main {
	Scanner sc = new Scanner(System.in);
	Connect connect = Connect.getConnection(); 
	Vector<Bawang> bawang = new Vector<>();
	Facade f = new Facade();
	
	public void menu() {
		System.out.println("1. Update Harga");
		System.out.println("2. Input Pembelian");
		System.out.println("3. Input Barang Masuk");
		System.out.println("4. Exit");
		System.out.print(">> ");
	}
	
	public void initializeData() {
		bawang.clear();
		ResultSet rs = connect.executeQuery("SELECT * FROM bawang");
		try {
			while(rs.next()) {
				Bawang b = new Bawang(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				bawang.add(b);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int search(String item) {
		for(int i = 0; i < bawang.size(); i++) {
			if(item.equals(bawang.get(i).getNama_bawang())){
				return i;
			}
		}
		return -1;
	}
	
	public void updatePrice() {
		int flag, price;
		String item = "";
		f.view();
		System.out.print("Masukan Nama Bawang [ Ketik 'Exit' untuk keluar ]: ");
		do {
			item = sc.nextLine();
			flag = search(item);
			if(flag != -1) {
				System.out.print("Masukan Harga Hari ini: ");
				price = sc.nextInt(); sc.nextLine();
				if(item.contains("Bombay")) {
					f.update(item, price, price-5000, price-10000);
				}
				else if(!item.contains("Bombay")) {
					f.update(item, price, price-200, price-400);
				}
			}
			else if(item.equalsIgnoreCase("exit")) {
				return;
			}
			else if(flag == -1) {
				System.out.println("Maaf Bawang yang anda masukan salah");sc.nextLine();
			}
		} while(flag == -1);
	}
	
	public void inputPembelian() {
		int flag = 0, qty = 0, total = 0, itemPrice = 0;
		float weight = 0 , subtotal;
		int convertedSub;
		String no_faktur, nama_pembeli, nama_bawang;
		System.out.print("Masukan no faktur: ");
		no_faktur = sc.nextLine();
		System.out.print("Masukan nama pembeli: ");
		nama_pembeli = sc.nextLine();
		f.insertCust(nama_pembeli);
		f.insertTransaction(no_faktur, nama_pembeli);
		do {
			System.out.print("Masukan jenis bawang [Ketik 'done' jika sudah selesai]: ");
			nama_bawang = sc.nextLine();
			flag = search(nama_bawang);
			if(flag != -1) {
				do {
					System.out.print("Masukan jumlah bawang: ");
					qty = sc.nextInt();
					sc.nextLine();
					if(qty >= 50) {
						itemPrice = bawang.get(flag).getPartai();
					}
					else if(qty >=10 && qty < 50) {
						itemPrice = bawang.get(flag).getGrosir();
					}
					else {
						itemPrice = bawang.get(flag).getPrice();
					}
					if (bawang.get(flag).getStock() < qty) {
						System.out.println("Maaf Stock Tidak Cukup");
						sc.nextLine();
					} else if (!nama_bawang.contains("Bombay")){
						System.out.print("Masukan Berat bawang: ");
						weight = sc.nextFloat(); sc.nextLine();
						subtotal = weight * itemPrice;
						convertedSub = (int)(Math.round(subtotal/1000) *1000);
						total+=convertedSub;
						f.insertTransactionDetail(no_faktur, nama_bawang, qty, weight, convertedSub, total);
						f.updateStockKeluar(nama_bawang, qty);
						System.out.println("Pesanan ditambahkan");
						sc.nextLine();
					} 
					 else if(nama_bawang.contains("Bombay")){
							convertedSub = qty * itemPrice;
							total+=convertedSub;
							f.insertTransactionDetail(no_faktur, nama_bawang, qty, 20*qty, convertedSub, total);
							f.updateStockKeluar(nama_bawang, qty);
							System.out.println("Pesanan ditambahkan");
							sc.nextLine();
						} 
				} while (bawang.get(flag).getStock() < qty);
			}
		} while (flag != -1 || !nama_bawang.equalsIgnoreCase("done"));
		f.viewTransdetail(no_faktur);
		System.out.println("Thank you for purchasing");
		sc.nextLine();
	}
	
	public void inputTruck() {
		int flag = 0, qty = 0, count = 0;
		Character c = 'A';
		String kode_barang = "";
		String kode, driver, nama_bawang;
		System.out.print("Masukan kode truck: ");
		kode = sc.nextLine();
		System.out.print("Masukan nama supir: ");
		driver = sc.nextLine();
		do {
			System.out.print("Masukan jenis bawang [Ketik 'done' jika sudah selesai]: ");
			nama_bawang = sc.nextLine();
			flag = search(nama_bawang);
			if(flag != -1) {
				kode_barang = kode.substring(7) + c;
				System.out.print("Masukan jumlah bawang [masukan 0 untuk membatalkan]: ");
				qty = sc.nextInt();
				sc.nextLine();
				if(qty == 0) {
					return;
				}
				else {
					if(count == 0) {
						f.insertTruck(kode, driver);
						count++;
					}
					f.insertTruckDetail(kode, kode_barang, nama_bawang, qty);
					c++;
					f.updateStockMasuk(nama_bawang, qty);
				}
				
			}
		} while (flag != -1 || !nama_bawang.equalsIgnoreCase("done"));
		
		System.out.println("Input Berhasil");
		sc.nextLine();
	}
	
	public Main() {
		int choice = 0;
		
		do {
			initializeData();
			f.view();
			System.out.println("=======================================");
			menu();
			choice = sc.nextInt(); sc.nextLine();
			switch (choice) {
			case 1:
				updatePrice();
				break;
				
			case 2:
				inputPembelian();
				break;
			
			case 3:
				inputTruck();
				break;
			}
	
		} while (choice != 4);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}
