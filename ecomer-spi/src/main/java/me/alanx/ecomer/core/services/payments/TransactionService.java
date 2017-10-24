package me.alanx.ecomer.core.services.payments;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.order.Order;
import me.alanx.ecomer.core.model.payments.Transaction;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;




public interface TransactionService extends SalesManagerEntityService<Long, Transaction> {

	/**
	 * Obtain a previous transaction that has type authorize for a give order
	 * @param order
	 * @return
	 * @throws ServiceException
	 */
	Transaction getCapturableTransaction(Order order) throws ServiceException;

	Transaction getRefundableTransaction(Order order) throws ServiceException;

	List<Transaction> listTransactions(Order order) throws ServiceException;



}