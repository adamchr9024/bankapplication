package dao.checking;

import java.util.ArrayList;

import accounts.Checking;
import exception.BusinessException;
import transact.Transaction;
import userdata.AccountHolder;

public interface CheckingDao {
	 /// JdbcCheckingDoa jdbcCheckDoa;
	 ArrayList<Transaction> getTransactions(Long acctNo) throws BusinessException;
	 Checking find (long acctNo) throws BusinessException ;
	 long create(String accountType, double balance) throws BusinessException ;
	 boolean update(long acctNo) throws BusinessException ;
	 boolean delete(long acctNo) throws BusinessException ;
     //boolean deposit()	 

}
