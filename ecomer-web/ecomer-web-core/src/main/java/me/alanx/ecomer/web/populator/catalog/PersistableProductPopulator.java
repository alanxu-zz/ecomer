package me.alanx.ecomer.web.populator.catalog;


import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.catalog.category.Category;
import me.alanx.ecomer.core.model.catalog.product.Manufacturer;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.ProductAvailability;
import me.alanx.ecomer.core.model.catalog.product.ProductDescription;
import me.alanx.ecomer.core.model.catalog.product.ProductImage;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOption;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionPrice;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionValue;
import me.alanx.ecomer.core.model.catalog.product.price.ProductPrice;
import me.alanx.ecomer.core.model.catalog.product.price.ProductPriceDescription;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.services.catalog.category.CategoryService;
import me.alanx.ecomer.core.services.catalog.product.attribute.ProductOptionService;
import me.alanx.ecomer.core.services.catalog.product.attribute.ProductOptionValueService;
import me.alanx.ecomer.core.services.catalog.product.manufacturer.ManufacturerService;
import me.alanx.ecomer.core.services.reference.language.LanguageService;
import me.alanx.ecomer.core.services.tax.TaxClassService;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.catalog.product.PersistableImage;
import me.alanx.ecomer.web.dto.catalog.product.PersistableProduct;
import me.alanx.ecomer.web.dto.catalog.product.ProductPriceEntity;
import me.alanx.ecomer.web.utils.DateUtil;



public class PersistableProductPopulator extends
		AbstractDataPopulator<PersistableProduct, Product> {
	
	
	private CategoryService categoryService;
	private ManufacturerService manufacturerService;
	private TaxClassService taxClassService;
	private LanguageService languageService;
	
	private ProductOptionService productOptionService;
	private ProductOptionValueService productOptionValueService;
	

	@Override
	public Product populate(PersistableProduct source,
			Product target, MerchantStore store, Language language)
			throws ConversionException {
		
			Validate.notNull(manufacturerService, "Requires to set ManufacturerService");
			Validate.notNull(languageService, "Requires to set LanguageService");
			Validate.notNull(categoryService, "Requires to set CategoryService");
			Validate.notNull(taxClassService, "Requires to set TaxClassService");
			Validate.notNull(productOptionService, "Requires to set ProductOptionService");
			Validate.notNull(productOptionValueService, "Requires to set ProductOptionValueService");
		
		try {

			target.setSku(source.getSku());
			target.setAvailable(source.isAvailable());
			target.setPreOrder(source.isPreOrder());
			target.setRefSku(source.getRefSku());
			
			if(!StringUtils.isBlank(source.getDateAvailable())) {
				target.setDateAvailable(DateUtil.getDate(source.getDateAvailable()));
			}

			if(source.getManufacturer()!=null) {
				
				Manufacturer manuf = null;
				if(!StringUtils.isBlank(source.getManufacturer().getCode())) {
					manuf = manufacturerService.getByCode(store, source.getManufacturer().getCode());
				} else {
					Validate.notNull(source.getManufacturer().getId(), "Requires to set manufacturer id");
					manuf = manufacturerService.getById(source.getManufacturer().getId());
				}
				
				if(manuf==null) {
					throw new ConversionException("Invalid manufacturer id");
				}
				if(manuf!=null) {
					if(manuf.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
						throw new ConversionException("Invalid manufacturer id");
					}
					target.setManufacturer(manuf);
					
				}
			}
			
			target.setMerchantStore(store);
			
			List<Language> languages = new ArrayList<Language>();
			Set<ProductDescription> descriptions = new HashSet<ProductDescription>();
			if(!CollectionUtils.isEmpty(source.getDescriptions())) {
				for(me.alanx.ecomer.web.dto.catalog.product.ProductDescription description : source.getDescriptions()) {
					
					ProductDescription productDescription = new ProductDescription();
					productDescription.setProduct(target);
					productDescription.setDescription(description.getDescription());
					productDescription.setName(description.getName());
					productDescription.setSeUrl(description.getFriendlyUrl());
					productDescription.setMetatagKeywords(description.getKeyWords());
					productDescription.setMetatagDescription(description.getMetaDescription());
					productDescription.setTitle(description.getTitle());
					
					Language lang = languageService.getByCode(description.getLanguage());
					if(lang==null) {
						throw new ConversionException("Language code " + description.getLanguage() + " is invalid, use ISO code (en, fr ...)");
					}
					
					languages.add(lang);
					productDescription.setLanguage(lang);
					descriptions.add(productDescription);
				}
			}
			
			if(descriptions.size()>0) {
				target.setDescriptions(descriptions);
			}

			//target.setType(source.getType());//not implemented yet
			target.setProductHeight(source.getProductHeight());
			target.setProductLength(source.getProductLength());
			target.setProductWeight(source.getProductWeight());
			target.setProductWidth(source.getProductWidth());
			target.setSortOrder(source.getSortOrder());
			target.setProductVirtual(source.isProductVirtual());
			target.setProductShipeable(source.isProductShipeable());
			if(source.getRating() != null) {
				target.setProductReviewAvg(new BigDecimal(source.getRating()));
			}
			target.setProductReviewCount(source.getRatingCount());
			
			
			if(CollectionUtils.isNotEmpty(source.getProductPrices())) {
				
				ProductAvailability productAvailability = new ProductAvailability();
				
				productAvailability.setProductQuantity(source.getQuantity());
				productAvailability.setProductQuantityOrderMin(1);
				productAvailability.setProductQuantityOrderMax(1);
				
				for(ProductPriceEntity priceEntity : source.getProductPrices()) {
					
					ProductPrice price = new ProductPrice();
					price.setDefaultPrice(priceEntity.isDefaultPrice());
					price.setProductPriceAmount(priceEntity.getOriginalPrice());
					price.setCode(priceEntity.getCode());
					price.setProductPriceSpecialAmount(priceEntity.getDiscountedPrice());
					if(priceEntity.getDiscountStartDate()!=null) {
						Date startDate = DateUtil.getDate(priceEntity.getDiscountStartDate());
						price.setProductPriceSpecialStartDate(startDate);
					}
					if(priceEntity.getDiscountEndDate()!=null) {
						Date endDate = DateUtil.getDate(priceEntity.getDiscountEndDate());
						price.setProductPriceSpecialEndDate(endDate);
					}
					productAvailability.getPrices().add(price);
					target.getAvailabilities().add(productAvailability);
					for(Language lang : languages) {
						ProductPriceDescription ppd = new ProductPriceDescription();
						ppd.setProductPrice(price);
						ppd.setLanguage(lang);
						ppd.setName(ProductPriceDescription.DEFAULT_PRICE_DESCRIPTION);
						price.getDescriptions().add(ppd);
					}
				}

			} else {
				
				ProductAvailability productAvailability = new ProductAvailability();
				
				productAvailability.setProductQuantity(source.getQuantity());
				productAvailability.setProductQuantityOrderMin(1);
				productAvailability.setProductQuantityOrderMax(1);
				
				ProductPrice price = new ProductPrice();
				price.setDefaultPrice(true);
				price.setProductPriceAmount(source.getPrice());
				price.setCode(ProductPriceEntity.DEFAULT_PRICE_CODE);
				price.setProductAvailability(productAvailability);
				productAvailability.getPrices().add(price);
				target.getAvailabilities().add(productAvailability);
				for(Language lang : languages) {
					ProductPriceDescription ppd = new ProductPriceDescription();
					ppd.setProductPrice(price);
					ppd.setLanguage(lang);
					ppd.setName(ProductPriceDescription.DEFAULT_PRICE_DESCRIPTION);
					price.getDescriptions().add(ppd);
				}
				
				
			}

			
			//image
			if(source.getImages()!=null) {
				for(PersistableImage img : source.getImages()) {
					ByteArrayInputStream in = new ByteArrayInputStream(img.getBytes());
					ProductImage productImage = new ProductImage();
					productImage.setProduct(target);
					productImage.setProductImage(img.getImageName());
					productImage.setImage(in);
					target.getImages().add(productImage);
				}
			}
			
			//attributes
			if(source.getAttributes()!=null) {
				for(me.alanx.ecomer.web.dto.catalog.product.attribute.ProductAttributeEntity attr : source.getAttributes()) {
					
					ProductOption productOption = null;
							
					if(!StringUtils.isBlank(attr.getOption().getCode())) {
						productOption = productOptionService.getByCode(store, attr.getOption().getCode());
					} else {
						Validate.notNull(attr.getOption().getId(),"Product option id is null");
						productOption = productOptionService.getById(attr.getOption().getId());
					}

					if(productOption==null) {
						throw new ConversionException("Product option id " + attr.getOption().getId() + " does not exist");
					}
					
					ProductOptionValue productOptionValue = null;
					
					if(!StringUtils.isBlank(attr.getOptionValue().getCode())) {
						productOptionValue = productOptionValueService.getByCode(store, attr.getOptionValue().getCode());
					} else {
						productOptionValue = productOptionValueService.getById(attr.getOptionValue().getId());
					}
					
					if(productOptionValue==null) {
						throw new ConversionException("Product option value id " + attr.getOptionValue().getId() + " does not exist");
					}
					
					if(productOption.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
						throw new ConversionException("Invalid product option id ");
					}
					
					if(productOptionValue.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
						throw new ConversionException("Invalid product option value id ");
					}
					
					ProductOptionPrice attribute = new ProductOptionPrice();
					attribute.setProduct(target);
					attribute.setProductOption(productOption);
					attribute.setProductOptionValue(productOptionValue);
					attribute.setOptionPrice(attr.getProductAttributePrice());
					attribute.setWeight(attr.getProductAttributeWeight());
					attribute.setOptionPrice(attr.getProductAttributePrice());
					target.getOptions().add(attribute);

				}
			}

			
			//categories
			if(!CollectionUtils.isEmpty(source.getCategories())) {
				for(me.alanx.ecomer.web.dto.catalog.category.Category categ : source.getCategories()) {
					
					Category c = null;
					if(!StringUtils.isBlank(categ.getCode())) {
						c = categoryService.getByCode(store, categ.getCode());
					} else {
						Validate.notNull(categ.getId(), "Category id nust not be null");
						c = categoryService.getById(categ.getId());
					}
					
					if(c==null) {
						throw new ConversionException("Category id " + categ.getId() + " does not exist");
					}
					if(c.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
						throw new ConversionException("Invalid category id");
					}
					target.getCategories().add(c);
				}
			}
			return target;
		
		} catch (Exception e) {
			throw new ConversionException(e);
		}
	}



	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setManufacturerService(ManufacturerService manufacturerService) {
		this.manufacturerService = manufacturerService;
	}

	public ManufacturerService getManufacturerService() {
		return manufacturerService;
	}

	public void setTaxClassService(TaxClassService taxClassService) {
		this.taxClassService = taxClassService;
	}

	public TaxClassService getTaxClassService() {
		return taxClassService;
	}


	public LanguageService getLanguageService() {
		return languageService;
	}

	public void setLanguageService(LanguageService languageService) {
		this.languageService = languageService;
	}

	public ProductOptionService getProductOptionService() {
		return productOptionService;
	}

	public void setProductOptionService(ProductOptionService productOptionService) {
		this.productOptionService = productOptionService;
	}

	public ProductOptionValueService getProductOptionValueService() {
		return productOptionValueService;
	}

	public void setProductOptionValueService(
			ProductOptionValueService productOptionValueService) {
		this.productOptionValueService = productOptionValueService;
	}


	@Override
	protected Product createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
