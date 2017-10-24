package me.alanx.ecomer.core.repositories.order.orderaccount;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.order.account.OrderAccount;

public interface OrderAccountRepository extends JpaRepository<OrderAccount, Long> {


}
