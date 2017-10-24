package me.alanx.ecomer.core.cms.product;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.ProductImage;
import me.alanx.ecomer.core.model.content.ImageContentFile;


public interface ProductImagePut {
	
	
	public void addProductImage(ProductImage productImage, ImageContentFile contentImage) throws ServiceException;


}
