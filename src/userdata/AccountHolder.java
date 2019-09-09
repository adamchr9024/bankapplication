package userdata;

import accounts.Checking;


public class AccountHolder {
	private String flname;  
	private Checking acct;
	private String username;  //
	private String passwd; //encrypt
    //phonenumber maybe	
	
	public AccountHolder() {
		this("joe blow", new Checking(), "test", "pw");
	}
	public AccountHolder(String flname, Checking acct, String username, String passwd) {
		super();
		this.flname = flname;
		this.acct = acct;
		this.username = username;
		this.passwd = passwd;
	}
	
	public String getFlname() {
		return flname;
	}
	public void setFlname(String flname) {
		this.flname = flname;
	}
	public Checking getAcct() {
		return acct;
	}
	public void setAcct(Checking acct) {
		this.acct = acct;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	
	@Override
	public String toString() {
		String pw = "**.....**";
		return "AccountHolder [flname=" + flname + ", acct=" + acct + ", username=" + username + ", passwd=" + pw
				+ "]";
	}
	

}
