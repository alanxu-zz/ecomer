package me.alanx.ecomer.core.repositories.order.orderproduct;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.order.product.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {


}
