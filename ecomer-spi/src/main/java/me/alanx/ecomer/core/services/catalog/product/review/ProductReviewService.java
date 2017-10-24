package me.alanx.ecomer.core.services.catalog.product.review;

import java.util.List;

import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.ProductReview;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface ProductReviewService extends
		SalesManagerEntityService<Long, ProductReview> {
	
	
	List<ProductReview> getByCustomer(Customer customer);
	List<ProductReview> getByProduct(Product product);
	List<ProductReview> getByProduct(Product product, Language language);
	ProductReview getByProductAndCustomer(Long productId, Long customerId);
	/**
	 * @param product
	 * @return
	 */
	List<ProductReview> getByProductNoCustomers(Product product);



}
