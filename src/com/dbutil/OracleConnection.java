package com.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;

public class OracleConnection {
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
	   String url2 = "jdbc:oracle:thin:@localhost:1521:xe";
	   Class.forName("oracle.jdbc.driver.OracleDriver"); 
	   String passwd = "Chris";
	   String user = "Chris";
	   String user2 = "dbbank";
	   String passwd2 = "admin";
	   Connection connection = DriverManager.getConnection(url2, user2, passwd2);
	   return connection;
    }

	/*
	 * public static void main(String[] args) {
	 * 
	 * Connection myconnection = null; try { myconnection = getConnection();
	 * System.out.println("connectio good"); } catch (SQLException ex) {
	 * System.out.println(ex.getMessage()); } catch (ClassNotFoundException ex2) {
	 * System.out.println("class not found in jdbcCheckingDoa " + ex2.getMessage());
	 * } finally { try { myconnection.close(); } catch (SQLException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } } }
	 */
}
