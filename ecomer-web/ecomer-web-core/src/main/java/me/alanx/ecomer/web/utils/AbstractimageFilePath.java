package me.alanx.ecomer.web.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import me.alanx.ecomer.core.model.catalog.product.Manufacturer;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.content.FileContentType;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.web.constants.Constants;


public abstract class AbstractimageFilePath implements ImageFilePath {


	public abstract String getBasePath();

	public abstract void setBasePath(String basePath);
	
	protected static final String CONTEXT_PATH = "CONTEXT_PATH";
	
	//public @Resource(name="shopizer-properties") Properties properties = env.getP

	@Autowired
	private Environment env;
	
	public String getProperty(String key) {
		return this.env.getProperty(key);
	}


	/**
	 * Builds a static content image file path that can be used by image servlet
	 * utility for getting the physical image
	 * @param store
	 * @param imageName
	 * @return
	 */
	public String buildStaticImageUtils(MerchantStore store, String imageName) {
		StringBuilder imgName = new StringBuilder().append(getBasePath()).append(Constants.FILES_URI).append(Constants.SLASH).append(store.getCode()).append(Constants.SLASH).append(FileContentType.IMAGE.name()).append(Constants.SLASH);
				if(!StringUtils.isBlank(imageName)) {
					imgName.append(imageName);
				}
		return imgName.toString();
				
	}
	
	/**
	 * Builds a static content image file path that can be used by image servlet
	 * utility for getting the physical image by specifying the image type
	 * @param store
	 * @param imageName
	 * @return
	 */
	public String buildStaticImageUtils(MerchantStore store, String type, String imageName) {
		StringBuilder imgName = new StringBuilder().append(getBasePath()).append(Constants.FILES_URI).append(Constants.SLASH).append(store.getCode()).append(Constants.SLASH).append(type).append(Constants.SLASH);
		if(!StringUtils.isBlank(imageName)) {
				imgName.append(imageName);
		}
		return imgName.toString();

	}
	
	/**
	 * Builds a manufacturer image file path that can be used by image servlet
	 * utility for getting the physical image
	 * @param store
	 * @param manufacturer
	 * @param imageName
	 * @return
	 */
	public String buildManufacturerImageUtils(MerchantStore store, Manufacturer manufacturer, String imageName) {
		return new StringBuilder().append(getBasePath()).append(Constants.SLASH).append(store.getCode()).append(Constants.SLASH).
				append(FileContentType.MANUFACTURER.name()).append(Constants.SLASH)
				.append(manufacturer.getId()).append(Constants.SLASH)
				.append(imageName).toString();
	}
	
	/**
	 * Builds a product image file path that can be used by image servlet
	 * utility for getting the physical image
	 * @param store
	 * @param product
	 * @param imageName
	 * @return
	 */
	public String buildProductImageUtils(MerchantStore store, Product product, String imageName) {
		return new StringBuilder().append(getBasePath()).append(Constants.SLASH).append(store.getCode()).append(Constants.SLASH).append(FileContentType.PRODUCT.name()).append(Constants.SLASH)
				.append(product.getSku()).append(Constants.SLASH).append(imageName).toString();
	}
	
	/**
	 * Builds a default product image file path that can be used by image servlet
	 * utility for getting the physical image
	 * @param store
	 * @param sku
	 * @param imageName
	 * @return
	 */
	public String buildProductImageUtils(MerchantStore store, String sku, String imageName) {
		return new StringBuilder().append(getBasePath()).append(Constants.SLASH).append(store.getCode()).append(Constants.SLASH).append(FileContentType.PRODUCT.name()).append(Constants.SLASH)
				.append(sku).append(Constants.SLASH).append(imageName).toString();
	}
	
	/**
	 * Builds a large product image file path that can be used by the image servlet
	 * @param store
	 * @param sku
	 * @param imageName
	 * @return
	 */
	public String buildLargeProductImageUtils(MerchantStore store, String sku, String imageName) {
		return new StringBuilder().append(getBasePath()).append(Constants.SLASH).append(store.getCode()).append(Constants.SLASH).append(FileContentType.PRODUCTLG.name()).append(Constants.SLASH)
				.append(sku).append(Constants.SLASH).append(imageName).toString();
	}


	
	/**
	 * Builds a merchant store logo path
	 * @param store
	 * @return
	 */
	public String buildStoreLogoFilePath(MerchantStore store) {
		return new StringBuilder().append(getBasePath()).append(Constants.SLASH).append(store.getCode()).append(Constants.SLASH).append(FileContentType.LOGO).append(Constants.SLASH)
				.append(store.getStoreLogo()).toString();
	}
	
	/**
	 * Builds product property image url path
	 * @param store
	 * @param imageName
	 * @return
	 */
	public String buildProductPropertyImageFilePath(MerchantStore store, String imageName) {
		return new StringBuilder().append(getBasePath()).append(Constants.SLASH).append(store.getCode()).append(Constants.SLASH).append(FileContentType.PROPERTY).append(Constants.SLASH)
				.append(imageName).toString();
	}
	
	public String buildProductPropertyImageUtils(MerchantStore store, String imageName) {
		return new StringBuilder().append(getBasePath()).append("/files/").append(store.getCode()).append("/").append(FileContentType.PROPERTY).append("/")
				.append(imageName).toString();
	}
	
	/**
	 * Builds pstatic file url path
	 * @param store
	 * @param imageName
	 * @return
	 */
	public String buildStaticContentFilePath(MerchantStore store, String fileName) {
		return new StringBuilder().append(getBasePath()).append(Constants.FILES_URI).append(Constants.SLASH).append(store.getCode()).append(Constants.SLASH).append(fileName).toString();
	}
	

	
	


}
