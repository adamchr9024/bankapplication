package service.impl.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import accounts.Checking;
import exception.BusinessException;
import service.impl.CheckingServiceImpl;
import transact.Transaction;

class CheckingServiceImplTest {
	  private static CheckingServiceImpl cki = new CheckingServiceImpl();
	  private static long acctNo = 1234567891123482l;
	  @BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testGetTransactions() throws BusinessException {
		
		ArrayList<Transaction> list;
		list = cki.getTransactions(acctNo);
		assertEquals(7, list.size());
	}

	@Test
	void testFind() throws BusinessException {
		Checking test = null;
		test = cki.find(acctNo);
		assertEquals(acctNo, test.getAccountNo());
	}

	@Test
	void testCreate() throws BusinessException {
		
		String accountType = "checking";
		double balance = 6600.00;
		long test = cki.create(accountType, balance);
		System.out.println("delete account "+ test);
		assertTrue(test>=1234567891123486l);
		
	}

	@Test
	void testUpdate() throws BusinessException {
		assertTrue(cki.update(acctNo));
	}

	@Test
	void testDelete() throws BusinessException {
		long acctno = cki.create("checking", 666.00);
		assertTrue(cki.delete(acctno));
		System.out.println("verify deleted account "+ acctno);
	}

	@Test
	void testWithdrawal() throws Exception {
		assertEquals(cki.withdrawal(acctNo, 50.0),1);
	}

	@Test
	void testDeposit() throws ClassNotFoundException, BusinessException, SQLException {
		assertEquals(cki.deposit(acctNo, 50.00),1);
		
	}

}
