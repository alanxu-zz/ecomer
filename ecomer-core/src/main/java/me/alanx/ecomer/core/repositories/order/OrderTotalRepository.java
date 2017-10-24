package me.alanx.ecomer.core.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.order.OrderTotal;

public interface OrderTotalRepository extends JpaRepository<OrderTotal, Long> {


}
