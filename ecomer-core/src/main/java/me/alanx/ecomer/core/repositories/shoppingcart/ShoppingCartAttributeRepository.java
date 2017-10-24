package me.alanx.ecomer.core.repositories.shoppingcart;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem;
public interface ShoppingCartAttributeRepository extends JpaRepository<ShoppingCartAttributeItem, Long> {


}
