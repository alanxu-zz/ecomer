package me.alanx.ecomer.web.store.controller.items.facade;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.query.ProductCriteria;
import me.alanx.ecomer.core.model.catalog.product.query.ProductList;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.catalog.product.PricingService;
import me.alanx.ecomer.core.services.catalog.product.ProductService;
import me.alanx.ecomer.web.dto.catalog.product.ReadableProduct;
import me.alanx.ecomer.web.dto.catalog.product.ReadableProductList;
import me.alanx.ecomer.web.populator.catalog.ReadableProductPopulator;
import me.alanx.ecomer.web.utils.ImageFilePath;

@Component
public class ProductItemsFacadeImpl implements ProductItemsFacade {
	
	
	@Inject
	ProductService productService;
	
	@Inject
	PricingService pricingService;
	
	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;

	@Override
	public ReadableProductList listItemsByManufacturer(MerchantStore store,
			Language language, Long manufacturerId, int startCount, int maxCount) throws Exception {
		
		
		ProductCriteria productCriteria = new ProductCriteria();
		productCriteria.setMaxCount(maxCount);
		productCriteria.setStartIndex(startCount);
		

		productCriteria.setManufacturerId(manufacturerId);
		ProductList products = productService.listByStore(store, language, productCriteria);

		
		ReadableProductPopulator populator = new ReadableProductPopulator();
		populator.setPricingService(pricingService);
		populator.setimageUtils(imageUtils);
		
		
		ReadableProductList productList = new ReadableProductList();
		for(Product product : products.getProducts()) {

			//create new proxy product
			ReadableProduct readProduct = populator.populate(product, new ReadableProduct(), store, language);
			productList.getProducts().add(readProduct);
			
		}
		
		productList.setTotalCount(products.getTotalCount());
		
		
		return productList;
	}
	
	@Override
	public ReadableProductList listItemsByIds(MerchantStore store, Language language, List<Long> ids, int startCount,
			int maxCount) throws Exception {
		
		
		ProductCriteria productCriteria = new ProductCriteria();
		productCriteria.setMaxCount(maxCount);
		productCriteria.setStartIndex(startCount);
		productCriteria.setProductIds(ids);
		

		ProductList products = productService.listByStore(store, language, productCriteria);

		
		ReadableProductPopulator populator = new ReadableProductPopulator();
		populator.setPricingService(pricingService);
		populator.setimageUtils(imageUtils);
		
		
		ReadableProductList productList = new ReadableProductList();
		for(Product product : products.getProducts()) {

			//create new proxy product
			ReadableProduct readProduct = populator.populate(product, new ReadableProduct(), store, language);
			productList.getProducts().add(readProduct);
			
		}
		
		productList.setTotalCount(products.getTotalCount());
		
		
		return productList;
	}

}
