package dao.transaction;

import java.time.LocalDate;
import java.util.Date;

import enumerations.EnumClass;
import exception.BusinessException;
import transact.Transaction;

public interface TransactionDao {
	//Transaction(long accountNo, LocalDate dateOf, EnumClass.TransType t_type, double amount) {
        Transaction find(long tranNo) throws BusinessException;
        long create (long accountNo, LocalDate dateOf, EnumClass.TransType t_type, double amount, double newbalance) throws BusinessException;
        long delete(long tranNo) throws BusinessException;
}
