package me.alanx.ecomer.core.repositories.payments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.alanx.ecomer.core.model.payments.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("select t from Transaction t join fetch t.order to where to.id = ?1")
	List<Transaction> findByOrder(Long orderId);
}
