package me.alanx.ecomer.core.services.catalog.product.price;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.price.ProductPrice;
import me.alanx.ecomer.core.model.catalog.product.price.ProductPriceDescription;
import me.alanx.ecomer.core.repositories.catalog.product.price.ProductPriceRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("productPrice")
public class ProductPriceServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductPrice> 
	implements ProductPriceService {

	@Inject
	public ProductPriceServiceImpl(ProductPriceRepository productPriceRepository) {
		super(productPriceRepository);
	}

	@Override
	public void addDescription(ProductPrice price,
			ProductPriceDescription description) throws ServiceException {
		price.getDescriptions().add(description);
		//description.setPrice(price);
		update(price);
	}
	
	
	@Override
	public void saveOrUpdate(ProductPrice price) throws ServiceException {
		
		if(price.getId()!=null && price.getId()>0) {
			this.update(price);
		} else {
			
			Set<ProductPriceDescription> descriptions = price.getDescriptions();
			price.setDescriptions(new HashSet<ProductPriceDescription>());
			this.create(price);
			for(ProductPriceDescription description : descriptions) {
				description.setProductPrice(price);
				this.addDescription(price, description);
			}
			
		}
		
		
		
	}
	
	@Override
	public void delete(ProductPrice price) throws ServiceException {
		
		//override method, this allows the error that we try to remove a detached instance
		price = this.getById(price.getId());
		super.delete(price);
		
	}
	


}
