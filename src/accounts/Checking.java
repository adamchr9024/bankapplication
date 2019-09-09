package accounts;

import java.time.LocalDate;
import enumerations.EnumClass;
import java.util.ArrayList;
//import transact.TransType;


//import enumerations.EnumClass.TransType;
import transact.Transaction;
 import exception.BusinessException;

public class Checking {
	
	private long accountNo;
	private double balance;
	private String accountType;
	private static EnumClass.TransType t_type; //shared by all
	private ArrayList<Transaction> transList;
	
	//private static long forNewAccounts = 1230000000;
	
	
	public Checking() {
		// TODO Auto-generated constructor stub
		this(1.0, "Checking");
	}
	public Checking(double initdeposit, String accounttype) {
		super();
		this.accountType = "Checking";
		this.accountNo =  999999999;
		//this.balance = initdeposit; done in deposit
		transList = new ArrayList<Transaction>();
		deposit(initdeposit);
	}
	public boolean withdrawal (double amount, EnumClass.TransType t_type  ) throws BusinessException{
	  //boolean overdraft = false;
	      if (balance > amount) {
	    	  balance -= amount; 
	    	  t_type = EnumClass.TransType.DEBIT_WITHDRAWAL;
	    	  Transaction transaction =new Transaction(accountNo, LocalDate.now(), t_type, amount, balance);
	    	 // transList.add(tranaction);
	  	      //long accountNo, LocalDate dateOf, TransType transactionType, double amoun 
	  	   return true;		   
	       }
	      throw new BusinessException("Insufficient funds available :CheckingClass");
	  
	 //    return overdraft;
	}
	public void deposit(double amount) {
		balance += amount; 
		Transaction transaction = new Transaction(accountNo, LocalDate.now(), EnumClass.TransType.DEPOSIT, amount, balance);
		//transList.add(transaction);
		
	}
	public long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public EnumClass.TransType getT_type() {
		return t_type;
	}
	public void setT_type(EnumClass.TransType t_type) {
		this.t_type = t_type;
	}
	public ArrayList<Transaction> getTransList() {
		return transList;
	}
	public void setTransList(ArrayList<Transaction> transList) {
		this.transList = transList;
	}
	
	@Override
	public String toString() {
		StringBuilder holdstr = new StringBuilder("");
		String strAcc = ""+accountNo;
		for(int i = 0; i < strAcc.length(); i++) {
			if (i>9) {
				holdstr.append(strAcc.charAt(i));
				continue;
			}
			holdstr.append("*");
		}
		return "Checking [accountNo=" + holdstr.toString() + ", balance= " + balance + ", accountType=" + accountType + ", transList=" + transList + "]";
	}

}
