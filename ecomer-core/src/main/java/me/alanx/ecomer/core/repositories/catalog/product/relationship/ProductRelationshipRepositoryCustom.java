package me.alanx.ecomer.core.repositories.catalog.product.relationship;

import java.util.List;

import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.ProductRelationship;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;


public interface ProductRelationshipRepositoryCustom {

	List<ProductRelationship> getByType(MerchantStore store, String type,
			Language language);

	List<ProductRelationship> getByType(MerchantStore store, String type,
			Product product, Language language);

	List<ProductRelationship> getByGroup(MerchantStore store, String group);

	List<ProductRelationship> getGroups(MerchantStore store);

	List<ProductRelationship> getByType(MerchantStore store, String type);

	List<ProductRelationship> listByProducts(Product product);

	List<ProductRelationship> getByType(MerchantStore store, String type,
			Product product);
	

}
