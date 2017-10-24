package me.alanx.ecomer.core.services.catalog.product.type;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.ProductType;
import me.alanx.ecomer.core.repositories.catalog.product.type.ProductTypeRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("productTypeService")
public class ProductTypeServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductType>
		implements ProductTypeService {

	private ProductTypeRepository productTypeRepository;
	
	@Inject
	public ProductTypeServiceImpl(
			ProductTypeRepository productTypeRepository) {
			super(productTypeRepository);
			this.productTypeRepository = productTypeRepository;
	}
	
	@Override
	public ProductType getProductType(String productTypeCode) throws ServiceException {
		
		return productTypeRepository.findByCode(productTypeCode);
		
	}



}
