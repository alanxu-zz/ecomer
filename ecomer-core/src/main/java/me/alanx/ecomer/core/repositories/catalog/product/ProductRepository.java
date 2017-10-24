package me.alanx.ecomer.core.repositories.catalog.product;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.catalog.product.Product;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

}
