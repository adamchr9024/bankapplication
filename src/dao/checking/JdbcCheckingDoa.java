package dao.checking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.net.URL;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;

import com.dbutil.OracleConnection;
//import java.sql.DatabaseMetaData;

import dao.checking.CheckingDaoImpl;

public class JdbcCheckingDoa {  //db_band    username bank   password band
	private PreparedStatement prestmt;  //bank@//localhost:1521/xe
    private Connection connection;
    private  CheckingDaoImpl checkdao;
   // private String url = "jdbc:oracle:thin:@chris:1521:db1";
    private String url2 = "jdbc:oracle:thin:@localhost:1521:xe";
    private String passwd = "Chris";
	private String user = "Chris";
	 //  Connection connection = DriverManager.getConnection(url2, user, passwd);
	// private
	public Connection getConnection() throws SQLException {
		// https://docs.oracle.com/cd/E17781_01/install.112/e18802/toc.htm#XEINL122
		// Establish a connection
		try { // 2 create a connection  jdbc:oracle:thin:@hostname:port Number:databaseName
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//connection = DriverManager.getConnection(url2, user, passwd);
			connection = OracleConnection.getConnection();
			System.out.println("Driver loaded");
			System.out.println("Database connected");

			return connection;
		} catch (SQLException ex) {
			throw ex;
		}
		  catch(ClassNotFoundException ex2) {
			  System.out.println("class not found in jdbcCheckingDoa "+ex2.getMessage());
		  }
		return null;  //should never get here
	}

}
