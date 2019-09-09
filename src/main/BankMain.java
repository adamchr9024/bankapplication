package main;

import org.apache.log4j.Logger;
import service.impl.UserDataServiceImpl;
import exception.BusinessException;
import service.impl.CheckingServiceImpl;
import transact.Transaction;

import java.lang.NumberFormatException;
import java.sql.SQLException;

//import accounts.Checking;
//import dao.checking.CheckingDaoImpl;
//import dao.userdata.UserDataDaoImpl;
//import dao.transaction.TransactionDaoImpl;
import userdata.AccountHolder;

import java.util.ArrayList;
import java.util.Scanner;

public class BankMain {

	private static final Logger log = Logger.getLogger(BankMain.class);
	private static Scanner scanner = new Scanner(System.in);
	// private static boolean isLogin = false;
	// private static boolean newUser = false;
	// private static UserDataDaoImpl uddi = new UserDataDaoImpl();
	private static UserDataServiceImpl userservice = new UserDataServiceImpl();
	// private static CheckingDaoImpl cdi = new CheckingDaoImpl();
	private static CheckingServiceImpl checkingservice = new CheckingServiceImpl();
	// private static TransactionDaoImpl tdi = new TransactionDaoImpl();
	private static AccountHolder acthold = null;// should be null
	private static int ch = 0;
	private static ArrayList<Transaction> mylist = null;

	public static void main(String args[]) {
		String holdin = "";
		// System.out.println("max long = "+Long.MAX_VALUE);
		// System.out.println("max int = "+Integer.MAX_VALUE);
		// int ch = 0;
		do {
			log.info(ViewClass.loginMenu());

			try {
				ch = intInput(); // remove the 3 after the updating
				if (!(ch + "").matches("\\s*[1239]{1}\\s*") || ch == -999) {
					log.info("\n\nInvalid Input, Please Try Again\n\n");
					continue;
				}
				switch (ch) {
				case 1:
					if (login())
						accountMenu();

					break;
				case 2:
					if (createLogin()) { // create account
						log.info(ViewClass.successfulLoginCreated());
						// accountMenu();
					}
					break;
				case 3:
					if (update())
						log.info("\n\nPassword updated successfully; login to verify your pw");
					else
						log.info("\n\n Service unavailable at this time\n");
					break;
				case 9:
					log.info("\n\n Thank Your For Using our Bank \n\n         Good Bye  ");
					break;
				default:
					log.info("\n\nInvalid Input, Please Try Again\n\n");
					continue;

				}// view account information allow deposit and withdrawals

			} // end try

			// catch (BusinessException ex) { log.info(ex.getMessage()); }

			catch (NumberFormatException ex1) {
				log.info("\n\nInvalid Input, Please Try Again\n\n");
			}

			catch (Exception ex) {
				log.info(ex.getMessage());
			}

		} while (ch != 9);
		/// user has successfull logged in

	} // end main

	public static boolean update() { // change password for a user
    try {
    	do {
    	log.info("\n\n Please Enter User name: ");
    	String username = scanner.next();
    	log.info("\n Please Enter Old Password");
    	String oldpw = scanner.next();
    	log.info("\n Please Enter New Password");
    	String newpw1 = scanner.next();
    	log.info("\n Please RE-Enter New Password");
    	String newpw2 = scanner.next();
    	if(!newpw1.equals(newpw2)) {
    		log.info(" New passwords does not match..Retry");
    		continue;
    		}
    	AccountHolder ac = userservice.find(username, oldpw);
    	if(ac == null) {
    		log.info(username + " does not exist...verify your data");
    		return false;
    	}
    	else {
    	   return userservice.updateUser(username, newpw1); 
    	}
    	//return false; //should never get here
    } while(true);
      // return false;
   } // end try
    catch(BusinessException ex) {
    	log.info("Exception happened: "+ex.getMessage());
      }
    return false; //should never get here
	}
	
	public static int intInput() {
		String hold = scanner.next();
		if (hold.matches("\\s*[123456789]{1}\\s*")) {
			return Integer.parseInt(hold);
		}
		return -999;
		// bad input
	}

	public static void accountMenu() throws ClassNotFoundException, BusinessException, SQLException {
		double amt;
		int ch1 = 0;
		AccountHolder act2hold = null;// new AccountHolder(); //maybe set to null

		do {
			act2hold = userservice.find(acthold.getUsername(), acthold.getPasswd());
			log.info(ViewClass.accountMenu());
			log.info("\n\n Welcome " + acthold.getFlname() + "\n");
			log.info("\n Enter A Selection Please");
			log.info("Your balance is " + String.format("$%.2f", act2hold.getAcct().getBalance()));
//String.format("%.2f", (double)value);
			ch1 = intInput();
			if (!(ch1 + "").matches("\\s*[1239]{1}\\s*") || ch1 == -999) {
				log.info("\n\nInvalid Input, Please Try Again\n\n");
				continue;
			}
			try {
				switch (ch1) {
				case 1: // deposit
					log.info(ViewClass.DepositMenu());
					amt = depwit();
					long updated = checkingservice.deposit(act2hold.getAcct().getAccountNo(), amt);//last change
					if (updated == 1)
						log.info(" success ");
					else
						log.info(" check your account ");
					break;
				case 2: // withdrawal
					log.info(ViewClass.WithdrawalMenu());
					amt = depwit();
					long updated1 = checkingservice.withdrawal(act2hold.getAcct().getAccountNo(), amt);
					if (updated1 == 1)
						log.info(" success ");
					else
						log.info(" check your account ");
					break;
				case 3: // view transactions
					mylist = checkingservice.getTransactions(act2hold.getAcct().getAccountNo());
					for (int i = 0; i < mylist.size(); i++) {
						log.info(mylist.get(i).toString());
						if (i == 10)
							break;
					}
					log.info("\n\n Enter '1' to Continue\n\n");
					scanner.next(); // stop to view data
					break;
				case 9: // exit
					return;
				default:
					log.info("\n\nInvalid Input, Please Try Again\n\n");
					continue;
				} // end try

			} catch (BusinessException e) {
				log.info(e.getMessage());
			} catch (Exception ex) {
				log.info(ex.getMessage());
			}
		} while (ch1 != 9);

	}

	public static double depwit() {
		String hold = "";
		double dd = 0.0;
		do {
			try {
				hold = scanner.next();
				dd = Double.valueOf(hold);
				if (dd > 0)
					return dd;
				else {
					log.info("Invalid Input Please Re-enter");
					continue;
				}

			} catch (NumberFormatException nfx) {
				log.info("Invalid Input Please Re-enter");
				continue;

			}

		} while (true);
	}

	public static boolean login() {

		log.info("\n\nPlease Enter User Name (no spaces): ");
		String username = scanner.next();
		log.info("\n\nPlease Enter Password: ");
		String pw = scanner.next();
		try {
			acthold = null;
			acthold = userservice.find(username, pw);
			if (acthold != null)
				return true;
			else {
				log.info("\n\n Username and password not found");
				return false;
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			// throw e;
		} // data base with dao object
		return false;
	}

	public static boolean createLogin() {
		boolean iscreated = false;
		String username = "";
		String flname = "";
		do {
			log.info("\n\nPlease Enter You First Name ");
			flname = scanner.next(); // may give error

			log.info("\n\nPlease Enter You Last Name ");
			String hold = scanner.next();
			flname += " " + hold;
			log.info("\n\nPlease Enter User Name (no spaces) or '9' to cancel/exit: ");
			username = scanner.next();
			if (username.matches("\\s[9]{1}\\s"))
				break;
			log.info("\n\nPlease Enter Password: ");
			String pw = scanner.next();
			log.info("\n\nPlease ReEnter Password: ");
			String pw2 = scanner.next();
			if (!pw2.equals(pw) && (!username.equals("9"))) {
				log.info("\n\nPassword don't match try again\n\n");
				continue;

			}

			try {
				iscreated = userservice.createUser(username, pw2, flname);
				if (iscreated) {
					acthold = null;
					acthold = userservice.find(username, pw2);
					return iscreated;
				}
				return iscreated;
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				log.info(e.getMessage());
			}
			// return iscreated;
		} while (!iscreated || username.matches("\\s[9]{1}\\s"));
		return false;
	} // end createLogin

}
