package enumerations;

/*public enum TransType{TRANS5(5), TRANS4(4), TRANS3(3), TRANS2(2), TRANS1(1);
   private int value;
   private TransType(int value) {
	   this.value = value;
   }
 }*/

public class EnumClass {
	 
	public enum TransType{DEPOSIT(5), ATM_WITHDRAWAL(4), TRANSFER(3), CHECK_WITHDRAWAL(2), DEBIT_WITHDRAWAL(1);
		   private int value;
		   private TransType(int value) {
			   this.value = value;
		   }
		   
    		   
	   }
     
	/*
	 * public final static TransType DEPOSIT = TransType.TRANS5; public final static
	 * TransType ATM_WITHDRAWAL = TransType.TRANS4 ; public final static TransType
	 * TRANSFER = TransType.TRANS3; public final static TransType CHECK_WITHDRAWAL =
	 * TransType.TRANS2; public final static TransType DEBIT_WITHDRAWAL =
	 * TransType.TRANS1;
	 */
	 
	 public EnumClass() {
		// TODO Auto-generated constructor stub
	}
	 
	 public EnumClass.TransType IntToEnum(int test) {
	    	
		    switch (test) {
	    	case 5:
	    	   return EnumClass.TransType.DEPOSIT;
	    	case 4: 
	    	   return EnumClass.TransType.ATM_WITHDRAWAL;
	    	case 3: 
	    	   return EnumClass.TransType.TRANSFER;
	       	case 2:
	       	   return EnumClass.TransType.CHECK_WITHDRAWAL;
	    	case 1: 
	    	    return EnumClass.TransType.DEBIT_WITHDRAWAL;
	    		
	    	}
			return null; //should never get here
	    }
	 public long EnumtoLong(EnumClass.TransType t_type) {
		 long test =5;
		 switch(t_type) {
		 case DEPOSIT:
		   return 5;
		 case ATM_WITHDRAWAL:
			 return 4;
		 case TRANSFER:
		    return 3;
		 case CHECK_WITHDRAWAL:
			 return 2;
		 case DEBIT_WITHDRAWAL:
			 return 1;
		 default:
		    return 0;
		 }
	 }

}
   
   
  
	
 
  
 

