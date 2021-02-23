package main.controller;

import java.sql.*;

public class SQL {
	private static final String URL = "jdbc:sqlite:db/Sudoku.db";
	
	public SQL() {
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement()) {
			
			// create a new table
			stmt.executeUpdate("""
					CREATE TABLE IF NOT EXISTS sudoku (
						id INTEGER PRIMARY KEY AUTOINCREMENT,
						werte varchar(163),
						time int
					);
					CREATE TABLE IF NOT EXISTS high (
						id INTEGER PRIMARY KEY AUTOINCREMENT,
						name String,
						time int
					);""");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement()) {
			
			
			stmt.executeUpdate("DROP TABLE sudoku; DROP TABLE high");
//			System.out.println(x);
//			stmt.executeUpdate("INSERT INTO sudoku2ada(werte,time) VALUES('test', 25)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void save(String field, long time) {

		try (Connection conn = DriverManager.getConnection(URL);
			 PreparedStatement ps = conn.prepareStatement("INSERT INTO sudoku(werte, time) VALUES(?, ?)")) {
			ps.setString(1, field);
			ps.setLong(2, time);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void insertHighscore(String name, long time) {
		try (Connection conn = DriverManager.getConnection(URL);
			 PreparedStatement ps = conn.prepareStatement("INSERT INTO high(name, time) VALUES(?, ?)")) {
			ps.setString(1, name);
			ps.setLong(2, time);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isSudokuEmpty(){
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM sudoku")) {
			return rs.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public String getSudoku() {
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT werte FROM sudoku LIMIT 1")) {
			return rs.getString("werte");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getSudokuTime() {
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT time FROM sudoku LIMIT 1")) {
			return rs.getInt("time");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void clearSudoku() {
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("DELETE FROM sudoku");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public String[] getHighscores() {
		String[] s = new String[11];
		
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT name, time FROM high ORDER BY time LIMIT 10")) {
			for (int i = 0; rs.next(); i++) {
				s[i] = rs.getString("name") + " Time:" + (rs.getLong("time") / 1000);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public void delete(int id) {
		try (Connection conn = DriverManager.getConnection(URL);
			 Statement stmt = conn.createStatement()) {
			
			stmt.executeUpdate("DELETE FROM sudoku WHERE id = " + id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
