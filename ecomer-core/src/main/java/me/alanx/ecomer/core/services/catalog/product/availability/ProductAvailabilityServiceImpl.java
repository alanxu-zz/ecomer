package me.alanx.ecomer.core.services.catalog.product.availability;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.ProductAvailability;
import me.alanx.ecomer.core.repositories.catalog.product.availability.ProductAvailabilityRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("productAvailabilityService")
public class ProductAvailabilityServiceImpl extends
		SalesManagerEntityServiceImpl<Long, ProductAvailability> implements
		ProductAvailabilityService {

	
	private ProductAvailabilityRepository productAvailabilityRepository;
	
	@Inject
	public ProductAvailabilityServiceImpl(
			ProductAvailabilityRepository productAvailabilityRepository) {
			super(productAvailabilityRepository);
			this.productAvailabilityRepository = productAvailabilityRepository;
	}
	
	
	@Override
	public void saveOrUpdate(ProductAvailability availability) throws ServiceException {
		
		if(availability.getId()!=null && availability.getId()>0) {
			
			this.update(availability);
			
		} else {
			this.create(availability);
		}
		
	}



}
