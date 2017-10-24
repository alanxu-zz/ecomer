package me.alanx.ecomer.core.services.catalog.product.attribute;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionPrice;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.repositories.catalog.product.attribute.ProductOptionPriceRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("productOptinPriceService")
public class ProductOptionPriceServiceImpl extends
		SalesManagerEntityServiceImpl<Long, ProductOptionPrice> implements ProductOptionPriceService {
	
	private ProductOptionPriceRepository productOptionPriceRepository;

	@Inject
	public ProductOptionPriceServiceImpl(ProductOptionPriceRepository productOptionPriceRepository) {
		super(productOptionPriceRepository);
		this.productOptionPriceRepository = productOptionPriceRepository;
	}
	
	@Override
	public ProductOptionPrice getById(Long id) {
		
		return productOptionPriceRepository.findOne(id);
		
	}
	
	
	@Override
	public List<ProductOptionPrice> getByOptionId(MerchantStore store,
			Long id) throws ServiceException {
		
		return productOptionPriceRepository.findByOptionId(store.getId(), id);
		
	}
	
	@Override
	public List<ProductOptionPrice> getByAttributeIds(MerchantStore store,
			Product product, List<Long> ids) throws ServiceException {
		
		return productOptionPriceRepository.findByAttributeIds(store.getId(), product.getId(), ids);
		
	}
	
	@Override
	public List<ProductOptionPrice> getByOptionValueId(MerchantStore store,
			Long id) throws ServiceException {
		
		return productOptionPriceRepository.findByOptionValueId(store.getId(), id);
		
	}
	
	/**
	 * Returns all product attributes
	 */
	@Override
	public List<ProductOptionPrice> getByProductId(MerchantStore store,
			Product product, Language language) throws ServiceException {
		return productOptionPriceRepository.findByProductId(store.getId(), product.getId(), language.getId());
		
	}


	@Override
	public void saveOrUpdate(ProductOptionPrice productOptionPrice)
			throws ServiceException {
		//if(productOptionPrice.getId()!=null && productOptionPrice.getId()>0) {
		//	productOptionPriceRepository.update(productOptionPrice);
		//} else {
			productOptionPriceRepository.save(productOptionPrice);
		//}
		
	}
	
	@Override
	public void delete(ProductOptionPrice attribute) throws ServiceException {
		
		//override method, this allows the error that we try to remove a detached instance
		attribute = this.getById(attribute.getId());
		super.delete(attribute);
		
	}

}
