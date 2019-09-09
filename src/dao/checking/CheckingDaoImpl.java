package dao.checking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.List;
import java.util.Date;
import dao.transaction.TransactionDaoImpl;
import accounts.Checking;
import enumerations.EnumClass;
import enumerations.EnumClass.TransType;
import exception.BusinessException;
import transact.Transaction;
//import userdata.AccountHolder;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
//import java.sql.Statement;
//import java.net.URL;
//import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
//import java.sql.DatabaseMetaData;
import com.dbutil.OracleConnection;
import java.sql.CallableStatement;

public class CheckingDaoImpl implements CheckingDao {

	
	

	@Override
	public ArrayList<Transaction> getTransactions(Long acctNo) throws BusinessException {
		ArrayList<Transaction> mylist = new ArrayList<Transaction>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select dateof, transactionno,t_type, amount, newbalance from transaction where accountno = ? order by transactionno desc";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, acctNo);
			ResultSet rs = pstmt.executeQuery();
			// if(rs != null) {
			EnumClass myenum = new EnumClass();
			// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			java.sql.Date date = new java.sql.Date(55);
			LocalDate ld = date.toLocalDate();
			while (rs.next()) {
				Transaction tran = new Transaction();
				tran.setAccountNo(acctNo);
				tran.setAmount(rs.getDouble("amount"));
				tran.setT_type(myenum.IntToEnum(rs.getInt("t_type")));
				tran.setTransactionNo(rs.getLong("transactionno"));
				date = rs.getDate("dateof");
				ld = date.toLocalDate();
				tran.setDateOf(ld);
				tran.setNewbalance(rs.getDouble("newbalance"));
				mylist.add(tran);
              
			}
			//Collections.sort(mylist, new SortById());
			return mylist;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is unavailable (CheckingDaoImpl transaction) " + e.getMessage());
		}

		
	}

	@Override
	public Checking find(long acctNo) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			Checking checking = null;
			String sql = "select accountNo, balance, accountType from Checking where accountNo = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, acctNo);
			ResultSet rs = pstmt.executeQuery();
			if (rs != null) {
				checking = new Checking();
				while (rs.next()) {
					checking.setAccountNo(rs.getLong("accountNo"));
					checking.setBalance(rs.getDouble("balance"));
					checking.setAccountType(rs.getString("accounttype"));
				}
			}
			return checking;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is unavailable (CheckingDaoImpl acctNo) " + e.getMessage());

		}

	}

	@Override
	public long create(String acctype, double balance) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "{call createAccount(?,?,?)}";
			CallableStatement callstmt = connection.prepareCall(sql); // connection.prepareCall(sql);
			callstmt.setString(2, acctype);
			callstmt.setDouble(3, balance);
			callstmt.registerOutParameter(1, java.sql.Types.NUMERIC);
			callstmt.execute(); // give exception about "" due to * missing ','
			long acctno = callstmt.getLong(1);// callstmt.getInt(1); error here;
			// 1System.out.println(" acctno = "+ acctno);
			return acctno;

		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is unavailable (CheckingDaoImpl create) " + e.getMessage());

		}

	}

	@Override
	public boolean update(long acctNo) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "update";
			PreparedStatement ppstmt = connection.prepareStatement(sql);
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is unavailable (CheckingDaoImpl update) " + e.getMessage());// TODO
																												

		}

	}

	@Override
	public boolean delete(long acctNo) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "delete from checking where ACCOUNTNO = ?"; // DELETE FROM table_name WHERE condition;
			PreparedStatement ppstmt = connection.prepareStatement(sql);
			ppstmt.setLong(1, acctNo); // need to delete user
			int test = ppstmt.executeUpdate();
			return (test == 1) ? true : false;

		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is unavailable delete (CheckingDaoImpl ) " + e.getMessage());

		}

	}

	public long withdrawal(Long acctNo, double amount) throws BusinessException, ClassNotFoundException, SQLException {
		// boolean overdraft = false;
		try {
			double balance = getBalance(acctNo);
			if (balance > amount) {
				balance -= amount;
				EnumClass.TransType t_type = EnumClass.TransType.DEBIT_WITHDRAWAL;
				// Transaction transaction =new Transaction(accountNo, LocalDate.now(), t_type,
				// amount);
				long hold = updateBalance(acctNo, balance);
				TransactionDaoImpl tdi = new TransactionDaoImpl();
				tdi.create(acctNo, LocalDate.now(), t_type, amount, balance);
				// long accountNo, LocalDate dateOf, TransType transactionType, double amoun

				return hold;
			}
			throw new BusinessException("Checking: Insufficient FUNDS available. ");
		} catch (BusinessException ex) {
			throw new BusinessException("Service is withdrawal unavailable (CheckingDaoImpl ) " + ex.getMessage());
		}

		// return overdraft;
	}

	public long deposit(long acctNo, double amount) throws BusinessException, ClassNotFoundException, SQLException {
		double balance = getBalance(acctNo);
		balance += amount;
		long hold = 0; 
		long hold2 = 0;
		// Transaction transaction = new Transaction(acctNo, LocalDate.now(),
		// EnumClass.TransType.DEPOSIT, amount);
		try {
			hold = updateBalance(acctNo, balance);
			TransactionDaoImpl tdi = new TransactionDaoImpl();
			hold2 = tdi.create(acctNo, LocalDate.now(), EnumClass.TransType.DEPOSIT, amount, balance); //next change
			//hold2 is the transaction number
		//		System.out.println("sucessful transaction" );
			//}
			// (long accountNo, LocalDate dateOf, TransType t_type, double amount)

			return hold;
		} catch (Exception ex) {
			throw ex;
		}

	}

	public long updateBalance(long accountNo, double balance) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "update checking set balance = ? where accountNo = ?";
			// connection.prepareStatement("UPDATE COUNSELOR SET first_name=?, last_name=?
			// WHERE counselor_id=?");
			PreparedStatement ppstmt = connection.prepareStatement(sql);
			ppstmt.setDouble(1, balance);
			ppstmt.setLong(2, accountNo);
			long hold = ppstmt.executeUpdate();
			return hold;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is unavailable (CheckingDaoImpl deposit" + e.getMessage());

		}
	}

	public double getBalance(long acctNo) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			double balance = 0.0;
			String sql = "select balance from Checking where accountNo = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, acctNo);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				

				balance = rs.getDouble("balance");
			}
			return balance;

		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is unavailable a (CheckingDaoImpl getBalance) " + e.getMessage());

		}

	}
	
	class SortById implements Comparator<Transaction>{

		@Override
		public int compare(Transaction arg0, Transaction arg1) {
			
			return (int)(arg0.getTransactionNo() - arg1.getTransactionNo()) ;
		}
		
	}
}
