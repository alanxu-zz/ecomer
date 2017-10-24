package me.alanx.ecomer.core.services.catalog.product.review;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.ProductReview;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.repositories.catalog.product.review.ProductReviewRepository;
import me.alanx.ecomer.core.services.catalog.product.ProductService;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("productReviewService")
public class ProductReviewServiceImpl extends
		SalesManagerEntityServiceImpl<Long, ProductReview> implements
		ProductReviewService {


	private ProductReviewRepository productReviewRepository;
	
	@Inject
	private ProductService productService;
	
	@Inject
	public ProductReviewServiceImpl(
			ProductReviewRepository productReviewRepository) {
			super(productReviewRepository);
			this.productReviewRepository = productReviewRepository;
	}

	@Override
	public List<ProductReview> getByCustomer(Customer customer) {
		return productReviewRepository.findByCustomer(customer.getId());
	}

	@Override
	public List<ProductReview> getByProduct(Product product) {
		return productReviewRepository.findByProduct(product.getId());
	}
	
	@Override
	public ProductReview getByProductAndCustomer(Long productId, Long customerId) {
		return productReviewRepository.findByProductAndCustomer(productId, customerId);
	}
	
	@Override
	public List<ProductReview> getByProduct(Product product, Language language) {
		return productReviewRepository.findByProduct(product.getId(), language.getId());
	}
	
	public ProductReview create(ProductReview review) throws ServiceException {
		
		//adjust score
		
		//refresh product
		Product product = productService.getById(review.getProduct().getId());
		
		//ajust product rating
		Integer count = 0;
		if(product.getProductReviewCount()!=null) {
			count = product.getProductReviewCount();
		}
				
		
		

		BigDecimal averageRating = product.getProductReviewAvg();
		if(averageRating==null) {
			averageRating = new BigDecimal(0);
		}
		//get reviews

		
		BigDecimal totalRating = averageRating.multiply(new BigDecimal(count));
		totalRating = totalRating.add(new BigDecimal(review.getReviewRating()));
		
		count = count + 1;
		double avg = totalRating.doubleValue() / count.intValue();
		
		product.setProductReviewAvg(new BigDecimal(avg));
		product.setProductReviewCount(count);
		review = super.create(review);
		
		productService.update(product);
		
		return review;
		
	}

	/* (non-Javadoc)
	 * @see com.salesmanager.core.business.services.catalog.product.review.ProductReviewService#getByProductNoObjects(com.salesmanager.core.model.catalog.product.Product)
	 */
	@Override
	public List<ProductReview> getByProductNoCustomers(Product product) {
		return productReviewRepository.findByProductNoCustomers(product.getId());
	}


}
