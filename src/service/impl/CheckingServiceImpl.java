package service.impl;

import java.util.ArrayList;

import accounts.Checking;
import dao.checking.CheckingDaoImpl;
import exception.BusinessException;
import service.CheckingService;
import transact.Transaction;
import java.sql.SQLException;

public class CheckingServiceImpl implements CheckingService {
	CheckingDaoImpl cdi = new CheckingDaoImpl();

	@Override
	public ArrayList<Transaction> getTransactions(Long acctNo) throws BusinessException {
		ArrayList<Transaction> mlist = null;// call dao layer
		try {
			mlist = cdi.getTransactions(acctNo);
			return mlist;
		} catch (BusinessException ex) {
			throw ex;
		}

	}

	@Override
	public Checking find(long acctNo) throws BusinessException {
		Checking checking = null;
		try {
			checking = cdi.find(acctNo);
			return checking;
		} catch (BusinessException ex) {
			throw ex;
		}
	}

	@Override
	public long create(String accountType, double balance) throws BusinessException {
		long acctno = 0;
		try {
			acctno = cdi.create(accountType, balance);
			return acctno;
		} catch (BusinessException ex) {
			throw ex;
		}
	}

	@Override
	public boolean update(long acctNo) throws BusinessException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean delete(long acctNo) throws BusinessException {
		try {
			return cdi.delete(acctNo);
		} catch (BusinessException ex) {
			throw ex;
		}

	}
	public long withdrawal(Long acctNo, double amount) throws Exception{
		long success = 0;
		try {
			success = cdi.withdrawal(acctNo, amount);
			return success;
		}
		catch (BusinessException | SQLException | ClassNotFoundException ex) {
			throw ex;
		}
	}
	public long deposit(long acctNo, double amount) throws BusinessException, ClassNotFoundException, SQLException {
        long success = 0;
        try {
        	success = cdi.deposit(acctNo, amount);
        	return success;
        }
        catch (BusinessException | SQLException | ClassNotFoundException ex) {
			throw ex;
		}
	}
}
