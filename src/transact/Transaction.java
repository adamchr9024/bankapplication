package transact;

import enumerations.EnumClass;
//import enumerations.EnumClass.TransType;

import java.time.LocalDate;

public class Transaction {
	//private long tranId;
	private long accountNo;  //pk
	private long transactionNo;
	private LocalDate dateOf;
	private EnumClass.TransType t_type;
	private double amount;
	//private static long forNewTransaction = 500400; //b
	private double newbalance;
	@Override
	public String toString() {
		StringBuilder holdstr = new StringBuilder("");
		String strAcc = ""+accountNo;
		String depwid = "";
		for(int i = 0; i < strAcc.length(); i++) {
			if (i>9) {
				holdstr.append(strAcc.charAt(i));
				continue;
			}
			holdstr.append("*"); //DEPOSIT(5), ATM_WITHDRAWAL(4), TRANSFER(3), CHECK_WITHDRAWAL(2), DEBIT_WITHDRAWAL(1);
		}
		if (t_type == EnumClass.TransType.DEPOSIT)
			  depwid = "Deposit";
		else 
			depwid = "Withdrawal";
		return "Transaction [accountNo=" + holdstr.toString() + ", transactionNo=" + transactionNo + ", dateOf=" + dateOf +
				 ", tranaction type=" + depwid + ", amount=" + String.format("$%.2f",amount) +  "\nbalance now: "+  String.format("$%.2f", newbalance) +"]";
	}
	
	public double getNewbalance() {
		return newbalance;
	}

	public void setNewbalance(double newbalance) {
		this.newbalance = newbalance;
	}

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Transaction(long accountNo, LocalDate dateOf, EnumClass.TransType t_type, double amount, double newbalance) {
		super();
		this.accountNo = accountNo;
		this.dateOf = dateOf;
		this.t_type = t_type;
		this.amount = amount;
		this.transactionNo = 999;
		this.newbalance = newbalance;
	}
	public void setTransactionNo(long transNo) {
		transactionNo = transNo;
	}
	public long getTransactionNo() {
		return transactionNo;
	}
	public long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	public LocalDate getDateOf() {
		return dateOf;
	}
	public void setDateOf(LocalDate dateOf) {
		this.dateOf = dateOf;
	}
	public EnumClass.TransType getT_type() {
		return t_type;
	}
	public void setT_type(EnumClass.TransType t_type){
		this.t_type = t_type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	} 

	
}
 

