package me.alanx.ecomer.core.repositories.catalog.product.relationship;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.catalog.product.ProductRelationship;


public interface ProductRelationshipRepository extends JpaRepository<ProductRelationship, Long>, ProductRelationshipRepositoryCustom {

}
