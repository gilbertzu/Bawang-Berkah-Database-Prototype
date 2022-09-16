package facade;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connect.Connect;

public class Facade {
	public void update(String nama_bawang, int Price, int Grosir, int Partai) {
		Connect connect = Connect.getConnection();
		PreparedStatement ps = connect.prepareStatement("UPDATE bawang SET Price = ?, Grosir = ?, Partai = ? WHERE Nama_bawang = ?");
		try {
			ps.setInt(1, Price);
			ps.setInt(2, Grosir);
			ps.setInt(3, Partai);
			ps.setString(4, nama_bawang);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void view() {
		Connect connect = Connect.getConnection();
		PreparedStatement ps = connect.prepareStatement("SELECT * FROM bawang");
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.printf("%s %d %d %d %d\n", rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void insertCust(String Nama_pembeli) {
		Connect connect = Connect.getConnection();
		PreparedStatement ps = connect.prepareStatement("INSERT IGNORE INTO pembeli(Nama_pembeli) VALUES (?)");
		try {
			ps.setString(1, Nama_pembeli);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertTransaction(String no_faktur, String Nama_pembeli) {
		Connect connect = Connect.getConnection();
		PreparedStatement ps1 = connect.prepareStatement("INSERT INTO transaction(No_faktur, Nama_pembeli) VALUES (?,?)");
		try {
			ps1.setString(1, no_faktur);
			ps1.setString(2, Nama_pembeli);
			ps1.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void insertTransactionDetail(String no_faktur, String Nama_bawang, int qty, float weight,int subtotal, int total) {
		Connect connect = Connect.getConnection();
		PreparedStatement ps = connect.prepareStatement("INSERT INTO transaction_detail(No_faktur, Nama_bawang, Qty, weight, Subtotal) VALUES (?,?,?,?,?)");
		try {
			ps.setString(1, no_faktur);
			ps.setString(2, Nama_bawang);
			ps.setInt(3, qty);
			ps.setFloat(4, weight);
			ps.setInt(5, subtotal);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		PreparedStatement ps1 = connect.prepareStatement("UPDATE transaction SET total = (?) WHERE No_faktur = (?)");
		try {
			ps1.setInt(1, total);
			ps1.setString(2, no_faktur);
			ps1.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateStockKeluar(String Nama_bawang, int qty) {
		int newStock;
		Connect connect = Connect.getConnection();
		PreparedStatement ps0 = connect.prepareStatement("SELECT stock FROM bawang WHERE Nama_bawang = (?)");
		PreparedStatement ps2 = connect.prepareStatement("UPDATE bawang SET stock = (?) WHERE Nama_bawang = (?)");
		try {
			ps0.setString(1, Nama_bawang);
			ResultSet rs = ps0.executeQuery();
			while(rs.next()) {
				newStock = rs.getInt(1);
				newStock -= qty;
				ps2.setInt(1, newStock);
				ps2.setString(2, Nama_bawang);
				ps2.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertTruck(String kode, String driver) {
		Connect connect = Connect.getConnection();
		PreparedStatement ps1 = connect.prepareStatement("INSERT INTO truck(ID_truck, driver) VALUES (?,?)");
		try {
			ps1.setString(1, kode);
			ps1.setString(2, driver);
			ps1.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void insertTruckDetail(String kode, String id, String Nama_bawang, int qty) {
		Connect connect = Connect.getConnection();
		PreparedStatement ps = connect.prepareStatement("INSERT INTO truck_detail(ID_truck, id, Nama_bawang, Qty) VALUES (?,?,?,?)");
		try {
			ps.setString(1, kode);
			ps.setString(2, id);
			ps.setString(3, Nama_bawang);
			ps.setInt(4, qty);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void updateStockMasuk(String Nama_bawang, int qty) {
		int newStock;
		Connect connect = Connect.getConnection();
		PreparedStatement ps0 = connect.prepareStatement("SELECT stock FROM bawang WHERE Nama_bawang = (?)");
		PreparedStatement ps2 = connect.prepareStatement("UPDATE bawang SET stock = (?) WHERE Nama_bawang = (?)");
		try {
			ps0.setString(1, Nama_bawang);
			ResultSet rs = ps0.executeQuery();
			while(rs.next()) {
				newStock = rs.getInt(1);
				newStock += qty;
				ps2.setInt(1, newStock);
				ps2.setString(2, Nama_bawang);
				ps2.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void viewTransdetail(String id) {
		Connect connect = Connect.getConnection();
		PreparedStatement ps = connect.prepareStatement("SELECT * FROM transaction_detail WHERE No_faktur = (?)");
		PreparedStatement ps1 = connect.prepareStatement("SELECT total FROM transaction WHERE No_faktur = (?)");
		try {
			ps.setString(1, id);
			ps1.setString(1, id);
			ResultSet rs = ps.executeQuery();
			ResultSet rs1 = ps1.executeQuery();
			while(rs.next()) {
				System.out.printf("%s %s %d %.2f %d\n", rs.getString(1), rs.getString(3), rs.getInt(4), rs.getFloat(5), rs.getInt(6));
			}
			while(rs1.next()) {
				System.out.printf("%d\n", rs1.getInt(1));;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
