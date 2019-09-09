package service.impl;

import exception.BusinessException;
import service.UserDataService;
import userdata.AccountHolder;
import dao.userdata.UserDataDaoImpl;

public class UserDataServiceImpl implements UserDataService {

	private UserDataDaoImpl uddi = new UserDataDaoImpl();

	@Override
	public boolean createUser(String username, String pw, String flname) throws BusinessException {
		
		try{// TODO Auto-generated method stub
			
		return	uddi.createUser(username, pw, flname);
		}
		
		catch(BusinessException ex) {
			throw ex;
		}
	}

	@Override
	public boolean deleteUser(String username) throws BusinessException {
		// TODO Auto-generated method stub
	 try {
		return uddi.deleteUser(username);
	  }catch(BusinessException ex) {
		  throw ex;
	  }
	}

	@Override
	public boolean updateUser(String username, String newpw) throws BusinessException {
	  try {	// TODO Auto-generated method stub
		return uddi.updateUser(username, newpw);
	 }
	  catch(BusinessException ex) {
		 throw ex; 
	  }
	}

	@Override
	public AccountHolder find(String username) throws BusinessException {
		AccountHolder ac = null;// TODO Auto-generated method stub
		try {
			ac = uddi.find(username);

			return ac;
		} catch (BusinessException ex) {
			throw ex;

		}

	}

	public AccountHolder find(String username, String pw) throws BusinessException {
		AccountHolder ac = null;// TODO Auto-generated method stub
		try {
			ac = uddi.find(username, pw);

			return ac;
		} catch (BusinessException ex) {
			throw ex;
		}
	}

}
