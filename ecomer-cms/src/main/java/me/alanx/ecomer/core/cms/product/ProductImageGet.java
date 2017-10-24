package me.alanx.ecomer.core.cms.product;

import java.util.List;

import me.alanx.ecomer.core.cms.content.ImageGet;
import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.ProductImage;
import me.alanx.ecomer.core.model.catalog.product.ProductImageSize;
import me.alanx.ecomer.core.model.content.OutputContentFile;

public interface ProductImageGet extends ImageGet{
	
	/**
	 * Used for accessing the path directly
	 * @param merchantStoreCode
	 * @param product
	 * @param imageName
	 * @return
	 * @throws ServiceException
	 */
	public OutputContentFile getProductImage(final String merchantStoreCode, final String productCode, final String imageName) throws ServiceException;
	public OutputContentFile getProductImage(final String merchantStoreCode, final String productCode, final String imageName, final ProductImageSize size) throws ServiceException;
	public OutputContentFile getProductImage(ProductImage productImage) throws ServiceException;
	public List<OutputContentFile> getImages(Product product) throws ServiceException;


}
