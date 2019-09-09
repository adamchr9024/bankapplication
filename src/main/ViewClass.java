package main;

public class ViewClass {
	
	public static String loginMenu() {
		StringBuilder strbld = new StringBuilder();
	    strbld.append("Welcome to Coder's Bank \n Please Enter:\n 1)Login \n 2) To Create an Account"+
		"\n 3) udate password (special priviledges required) "+"\n 9) to Cancel / Exit");
		return strbld.toString();
	}
	public static String successfulLoginCreated(){
		StringBuilder strbld = new StringBuilder();
		strbld.append("************===========*****************===========***********======**********");
		strbld.append("\n\n\n\n ***   Login successfully created \n Please Login to view your account ***" );
		strbld.append("\n\n      PLEASE LOGIN IN TO CREATE YOU ACCOUNT(S)     \n\n");
		strbld.append("************===========*****************===========***********======**********");
		
		return strbld.toString();
	}
	
	public static String accountMenu() { //accountMenu
		StringBuilder strbld = new StringBuilder();
		strbld.append("************===========*****************===========***********======**********");
		strbld.append("\n\n\n\n *** Welcome                 ***" );
		strbld.append("\n\n    ENTER 1) to deposit   ");
		strbld.append("\n\n    ENTER 2) to withdraw   ");
		strbld.append("\n\n    Enter 3) to view transactions");
		strbld.append("\n\n    ENTER 9) to exit   \n");
		//strbld.append("\n\n    ENTER 3) to deposit to acount   ");
		strbld.append("************===========*****************===========***********======**********");
		
		return strbld.toString();
	}
	
	public static String WithdrawalMenu() {
		StringBuilder strbld = new StringBuilder();
		strbld.append("************===========*****************===========***********======**********");
		strbld.append("\n\nEnter Amount to Withdraw: ####\n\n ");
		strbld.append("************===========*****************===========***********======**********");
		 
		return strbld.toString();
	}
	
	public static String  DepositMenu() {
		StringBuilder strbld = new StringBuilder();
		strbld.append("************===========*****************===========***********======**********");
		strbld.append("\n\nEnter Amount to Deposit: ##.##\n\n");
		strbld.append("************===========*****************===========***********======**********");
		 
		return strbld.toString();
	}
	

}
