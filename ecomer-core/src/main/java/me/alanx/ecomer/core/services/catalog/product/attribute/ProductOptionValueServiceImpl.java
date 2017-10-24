package me.alanx.ecomer.core.services.catalog.product.attribute;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionPrice;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionValue;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.repositories.catalog.product.attribute.ProductOptionValueRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("productOptionValueService")
public class ProductOptionValueServiceImpl extends
		SalesManagerEntityServiceImpl<Long, ProductOptionValue> implements
		ProductOptionValueService {

	@Inject
	private ProductOptionPriceService productOptionPriceService;
	
	private ProductOptionValueRepository productOptionValueRepository;
	
	@Inject
	public ProductOptionValueServiceImpl(
			ProductOptionValueRepository productOptionValueRepository) {
			super(productOptionValueRepository);
			this.productOptionValueRepository = productOptionValueRepository;
	}
	
	
	@Override
	public List<ProductOptionValue> listByStore(MerchantStore store, Language language) throws ServiceException {
		
		return productOptionValueRepository.findByStoreId(store.getId(), language.getId());
	}
	
	@Override
	public List<ProductOptionValue> listByStoreNoReadOnly(MerchantStore store, Language language) throws ServiceException {
		
		return productOptionValueRepository.findByReadOnly(store.getId(), language.getId(), false);
	}

	@Override
	public List<ProductOptionValue> getByName(MerchantStore store, String name, Language language) throws ServiceException {
		
		try {
			return productOptionValueRepository.findByName(store.getId(), name, language.getId());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		
	}
	
	@Override
	public void saveOrUpdate(ProductOptionValue entity) throws ServiceException {
		
		
		//save or update (persist and attach entities
		if(entity.getId()!=null && entity.getId()>0) {

			super.update(entity);
			
		} else {
			
			super.save(entity);
			
		}
		
	}
	
	
	public void delete(ProductOptionValue entity) throws ServiceException {
		
		//remove all attributes having this option
		List<ProductOptionPrice> attributes = productOptionPriceService.getByOptionValueId(entity.getMerchantStore(), entity.getId());
		
		for(ProductOptionPrice attribute : attributes) {
			productOptionPriceService.delete(attribute);
		}
		
		ProductOptionValue option = getById(entity.getId());
		
		//remove option
		super.delete(option);
		
	}
	
	@Override
	public ProductOptionValue getByCode(MerchantStore store, String optionValueCode) {
		return productOptionValueRepository.findByCode(store.getId(), optionValueCode);
	}


	@Override
	public ProductOptionValue getById(MerchantStore store, Long optionValueId) {
		return productOptionValueRepository.findOne(store.getId(), optionValueId);
	}



}
