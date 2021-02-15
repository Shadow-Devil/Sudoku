package main;

import main.Model.Field;

import java.sql.*;

public class SQL {
	public int sid = 0; //Sudoku ID
	private int hid = 0; //Highscore ID
	private static final String URL = "jdbc:sqlite:db/Sudoku.db";
	
	public SQL() {
		
		try (Connection conn = DriverManager.getConnection(URL)) {
			Statement stmt = conn.createStatement();
			sid = stmt.executeQuery("SELECT * FROM sudoku ORDER BY id DESC LIMIT 1").getInt(1) + 1;
			stmt.close();
			
			Statement stmt2 = conn.createStatement();
			hid = stmt2.executeQuery("SELECT * FROM high ORDER BY id DESC LIMIT 1").getInt(1) + 1;
			System.out.println(hid);
			stmt2.close();
		} catch (SQLException ignored) {
		}
		newtable();
	}
	
	
	public void newtable() {
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement();
			 Statement stmt2 = conn.createStatement()) {
			
			// create a new table
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS sudoku (id int PRIMARY KEY AUTOINCREMENT, werte varchar(163), time int)");
			stmt2.executeUpdate("CREATE TABLE IF NOT EXISTS high (id int PRIMARY KEY AUTOINCREMENT, name String, time int)");
			
		} catch (SQLException ignored) {
		}
	}
	
	public void insert(Field[][] feld, int time) {
		StringBuilder s = new StringBuilder();
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				s.append(feld[x][y].getInhalt());
				s.append(feld[x][y].isConstant() ? "t" : "f");
			}
		}
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement()) {
			
			stmt.executeUpdate("INSERT INTO sudoku VALUES(" + sid + ",'" + s + "', " + time + ")");
			sid++;
		} catch (SQLException e) {
			System.out.println("test3");
			System.out.println(e.getMessage());
		}
		
	}
	
	public void insert(String name, int time) {
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("INSERT INTO high VALUES(" + hid + ",'" + name + "', " + time + ")");
			
			hid++;
		} catch (SQLException e) {
			System.out.println("test4");
			System.out.println(e.getMessage());
		}
	}
	
	public String sget() {
		String s = "";
		int idtmp = sid - 1;
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement();
			 Statement stmt2 = conn.createStatement()) {
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM sudoku WHERE id = " + idtmp);
			s = rs.getString("werte") + rs.getInt("time");
			rs.close();
			
			stmt2.executeUpdate("DELETE FROM sudoku WHERE id = " + idtmp);
			sid--;
		} catch (SQLException e) {
			System.out.println("test5");
			System.out.println(e.getMessage());
		}
		
		
		return s;
	}
	
	public String[] hget() {
		String[] s = new String[11];
		int i = 0;
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement()) {
			
			ResultSet rs = stmt.executeQuery("SELECT name,time FROM high ORDER BY time LIMIT 10");
			while (rs.next()) {
				s[i] = rs.getString("name");
				s[i] = s[i] + " " + rs.getInt("time");
				i++;
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("test6");
			System.out.println(e.getMessage());
		}
		return s;
	}
	
	public void delete(int iddel) {
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement()) {
			
			stmt.executeUpdate("DELETE FROM sudoku WHERE id = " + iddel);
			
		} catch (SQLException e) {
			System.out.println("test7");
			System.out.println(e.getMessage());
		}
	}
}
