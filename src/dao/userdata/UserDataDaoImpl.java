package dao.userdata;

import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dbutil.OracleConnection;

import accounts.Checking;
import dao.checking.CheckingDaoImpl;
import exception.BusinessException;
import userdata.AccountHolder;

public class UserDataDaoImpl implements Userdatadao{

	@Override
	public boolean createUser(String username, String pw, String flname) throws BusinessException {
		boolean test=true;
		
		try(Connection connection = OracleConnection.getConnection()){
			SHA1 shaHash = new SHA1();
			String hashpw = shaHash.sha1(pw); 
			 String sql = "{call createUser(?,?,?,?)}";
			 //USERNAME, PASSWD, ACCT, FLNAME
			 CallableStatement callstmt = connection.prepareCall(sql);  //connection.prepareCall(sql);
			 callstmt.setString( 1, username);
			 callstmt.setString( 2, hashpw);  
			 callstmt.setString(4 , flname );
			 CheckingDaoImpl cki = new CheckingDaoImpl();
			 long acct = cki.create("checking", 0.0);
			 ///get the account number by creating and account object
			 callstmt.setLong(3, acct);
			 callstmt.execute();
	     return test; 		
		 } catch (ClassNotFoundException | SQLException e) {
			 if (e.getMessage().contains("ORA-00001"))
			 throw new BusinessException("User name is already taken. Please try again) ");
			 else
				 
			throw new BusinessException("Service is unavailable createUser (UserDataDaoImpl ) "+ e.getMessage());// TODO Auto-generated catch block
			
		}
	}

	@Override
	public boolean deleteUser(String username) throws BusinessException {
		//boolean test = true;
		try(Connection connection = OracleConnection.getConnection()){
			String sql = "delete from accountholder where username = ?";  //DELETE FROM table_name WHERE condition;
			 PreparedStatement ppstmt = connection.prepareStatement(sql);
			 ppstmt.setString(1 , username);
			 int test = ppstmt.executeUpdate(); 
			 return  test==1? true: false;
	  }
		catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is deleteUser unavailable (UserDataDaoImpl) "+ e.getMessage());// TODO Auto-generated catch block
			
		}
		
	}

	@Override
	public boolean updateUser(String username, String newpw) throws BusinessException {
		
		String sql = "update accountholder set passwd = ? where username = ?";
		try(Connection connection = OracleConnection.getConnection()){
			SHA1 shaHash = new SHA1();
			String hashpw = shaHash.sha1(newpw); 
			 PreparedStatement ppstmt = connection.prepareStatement(sql);
			 ppstmt.setString(1 , hashpw);
			 ppstmt.setString(2,  username);
			 int test = ppstmt.executeUpdate(); 
			 return  test==1? true: false;
	  }
		catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is deleteUser unavailable (UserDataDaoImpl) "+ e.getMessage());// TODO Auto-generated catch block
			
		}
		
		
		
	}

	@Override
	public AccountHolder find(String username) throws BusinessException { //need a method to verify user name and password
		/*  add a PASSWORD HERE
		 * private String flname; private Checking acct; private String username; //
		 * private String passwd; //encrypt
		*/	
		try(Connection connection = OracleConnection.getConnection()){
			
			AccountHolder ac = null;
			String sql ="select flname, username, acct from accountHolder where username = ?  ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, username);
			//pstmt.setString(2,  passwd);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next() ) {
				ac = new AccountHolder();
				Checking checking = null;
				//while (rs.next()) {
			      ac.setFlname( rs.getString("flname"));
			      ac.setUsername(rs.getString("username"));
			      long acnt = rs.getLong("acct");
			      CheckingDaoImpl cki = new  CheckingDaoImpl();
			      checking = cki.find(acnt);
			      ac.setAcct(checking);
			      ac.setPasswd("dummy value");  //this should be a hash
			    //}
			}
			return ac;
		 } catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException(" find username Service is unavailable (UserDataDaoImpl) "+ e.getMessage());
			
		}
		
		
	}
	public AccountHolder find(String username, String pw) throws BusinessException { //need a method to verify user name and password
		/*  add a PASSWORD HERE
		 * private String flname; private Checking acct; private String username; //
		 * private String passwd; //encrypt
		*/	
		
		try(Connection connection = OracleConnection.getConnection()){
		    SHA1 shaHash = new SHA1();
			String hashpw = shaHash.sha1(pw);
			AccountHolder ac = null;
			String sql ="select flname, username, acct, passwd from accountHolder where username = ? and passwd = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, hashpw);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ac = new AccountHolder();
				Checking checking = null;
				//while (rs.next()) {
			      ac.setFlname( rs.getString("flname"));
			      ac.setUsername(rs.getString("username"));
			      ac.setPasswd(pw); // pw entered not hashed pw
			      long acnt = rs.getLong("acct");
			      CheckingDaoImpl cki = new  CheckingDaoImpl();
			      checking = cki.find(acnt);
			      ac.setAcct(checking);
			     
			   // }
			}
			return ac;
		 } catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is unavailable find username pw (UserDataDaoImpl) "+ e.getMessage());
			
			
		}
		
	}
	private class SHA1 {  //innerclass

		public String sha1(String input) throws BusinessException {
			
			StringBuffer message=new StringBuffer();
			
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				byte[] hash = md.digest(input.getBytes("UTF-8"));

				
				for (byte w : hash) {
					message.append(String.format("%02x", w));
				}
				return message.toString();
				
			}catch (Exception e) {
				throw new BusinessException(" from UserDataDaoImpl hash method "+e.getMessage()) ;
			}

			

		}
			
	}
	

}

	