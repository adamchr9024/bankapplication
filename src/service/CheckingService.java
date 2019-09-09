package service;

import java.util.ArrayList;

import accounts.Checking;
import exception.BusinessException;
import transact.Transaction;

public interface CheckingService {
	
	 ArrayList<Transaction> getTransactions(Long acctNo) throws BusinessException;
	 Checking find (long acctNo) throws BusinessException ;
	 long create(String accountType, double balance) throws BusinessException ;
	 boolean update(long acctNo) throws BusinessException ;
	 boolean delete(long acctNo) throws BusinessException ;

}
