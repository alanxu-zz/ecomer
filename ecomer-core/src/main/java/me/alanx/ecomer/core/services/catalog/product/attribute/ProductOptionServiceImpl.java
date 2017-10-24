package me.alanx.ecomer.core.services.catalog.product.attribute;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOption;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionPrice;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.repositories.catalog.product.attribute.ProductOptionRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("productOptionService")
public class ProductOptionServiceImpl extends
		SalesManagerEntityServiceImpl<Long, ProductOption> implements ProductOptionService {

	
	private ProductOptionRepository productOptionRepository;
	
	@Inject
	private ProductOptionPriceService productOptionPriceService;
	
	@Inject
	public ProductOptionServiceImpl(
			ProductOptionRepository productOptionRepository) {
			super(productOptionRepository);
			this.productOptionRepository = productOptionRepository;
	}
	
	@Override
	public List<ProductOption> listByStore(MerchantStore store, Language language) throws ServiceException {
		
		
		return productOptionRepository.findByStoreId(store.getId(), language.getId());
		
		
	}
	
	@Override
	public List<ProductOption> listReadOnly(MerchantStore store, Language language) throws ServiceException {

		return productOptionRepository.findByReadOnly(store.getId(), language.getId(), true);
		
		
	}
	

	
	@Override
	public List<ProductOption> getByName(MerchantStore store, String name, Language language) throws ServiceException {
		
		try {
			return productOptionRepository.findByName(store.getId(), name, language.getId());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		
	}
	
	@Override
	public void saveOrUpdate(ProductOption entity) throws ServiceException {
		
		
		//save or update (persist and attach entities
		if(entity.getId()!=null && entity.getId()>0) {
			super.update(entity);
		} else {
			super.save(entity);
		}
		
	}
	
	@Override
	public void delete(ProductOption entity) throws ServiceException {
		
		//remove all attributes having this option
		List<ProductOptionPrice> attributes = productOptionPriceService.getByOptionId(entity.getMerchantStore(), entity.getId());
		
		for(ProductOptionPrice attribute : attributes) {
			productOptionPriceService.delete(attribute);
		}
		
		ProductOption option = this.getById(entity.getId());
		
		//remove option
		super.delete(option);
		
	}
	
	@Override
	public ProductOption getByCode(MerchantStore store, String optionCode) {
		return productOptionRepository.findByCode(store.getId(), optionCode);
	}

	@Override
	public ProductOption getById(MerchantStore store, Long optionId) {
		return productOptionRepository.findOne(store.getId(), optionId);
	}
	

	




}
