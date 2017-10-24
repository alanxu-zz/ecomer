package me.alanx.ecomer.core.cms.product;

import me.alanx.ecomer.core.cms.content.ImageRemove;
import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.ProductImage;


public interface ProductImageRemove extends ImageRemove {
	
	
	public void removeProductImage(ProductImage productImage) throws ServiceException;
	public void removeProductImages(Product product) throws ServiceException;
	


}
