package me.alanx.ecomer.core.repositories.shoppingcart;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem;
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {


}
