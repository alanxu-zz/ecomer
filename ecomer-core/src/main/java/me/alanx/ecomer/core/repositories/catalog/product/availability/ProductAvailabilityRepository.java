package me.alanx.ecomer.core.repositories.catalog.product.availability;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.catalog.product.ProductAvailability;

public interface ProductAvailabilityRepository extends JpaRepository<ProductAvailability, Long> {

}
