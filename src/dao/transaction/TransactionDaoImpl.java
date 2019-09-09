package dao.transaction;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import com.dbutil.OracleConnection;
import enumerations.EnumClass;
import enumerations.EnumClass.TransType;
import exception.BusinessException;
import transact.Transaction;

public class TransactionDaoImpl implements TransactionDao {

	@Override
	public Transaction find(long tranNo) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			Transaction tran = null;
			String sql = "select accountNo, t_type, dateof, amount, newbalance from transaction where transactionno  = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, tranNo);
			ResultSet rs = pstmt.executeQuery();
			EnumClass myenum = new EnumClass();
			java.sql.Date date = new java.sql.Date(5);
			LocalDate ld = date.toLocalDate();
			while (rs.next()) {
				tran = new Transaction();
				tran.setAccountNo(rs.getLong("accountNo"));
				tran.setAmount(rs.getDouble("amount"));
				tran.setT_type(myenum.IntToEnum(rs.getInt("t_type")));
				tran.setTransactionNo(rs.getLong("transactionno"));
				date = rs.getDate("dateof");
				ld = date.toLocalDate();
				tran.setDateOf(ld);
				tran.setNewbalance(rs.getDouble("newbalance"));
			}

			return tran;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is unavailable find (TransactionDaoImpl) " + e.getMessage());

		}

	}

	//// Transaction(long accountNo, LocalDate dateOf, EnumClass.TransType t_type,
	//// double amount)
	@Override // add new_balance to this tranactions class
	public long create(long accountNo, LocalDate dateOf, TransType t_type, double amount, double newbalance)
			throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			EnumClass e1 = new EnumClass();
			String sql = "{call createTran(?,?,?,?,?,?)}";
			CallableStatement callstmt = connection.prepareCall(sql); // connection.prepareCall(sql);
			callstmt.setLong(2, accountNo);
			long atran = e1.EnumtoLong(t_type);
			callstmt.setLong(3, atran);
			callstmt.setDouble(4, amount);
			java.sql.Date date = java.sql.Date.valueOf(dateOf);
			callstmt.setDate(5, date);
			callstmt.setDouble(6, newbalance);
			callstmt.registerOutParameter(1, java.sql.Types.NUMERIC);
			callstmt.execute();
			long tranno = callstmt.getLong(1);
			return tranno;
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is unavailable create (TransactionDaoImpl) " + e.getMessage());// TODO
																												// Auto-generated
																												// catch
																												// block

		}

	}

	@Override
	public long delete(long tranNo) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "delete from transaction where tranactionno = ?"; // DELETE FROM table_name WHERE condition;
			PreparedStatement ppstmt = connection.prepareStatement(sql);
			ppstmt.setLong(1, tranNo);
			long test = ppstmt.executeUpdate();
			return test;

		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Service is unavailable delete (CheckingDaoImpl) " + e.getMessage());// TODO
																												// Auto-generated
																												// catch
																												// block

		}

	}

}
