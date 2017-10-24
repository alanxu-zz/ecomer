package me.alanx.ecomer.core.repositories.catalog.product.type;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.catalog.product.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

	ProductType findByCode(String code);
}
