package service;

import exception.BusinessException;
import userdata.AccountHolder;

public interface UserDataService {
	boolean createUser(String username, String pw, String flname) throws BusinessException;
	boolean deleteUser(String username) throws BusinessException;
	boolean updateUser (String username, String newpw) throws BusinessException;
	 AccountHolder find( String username) throws BusinessException;
}
