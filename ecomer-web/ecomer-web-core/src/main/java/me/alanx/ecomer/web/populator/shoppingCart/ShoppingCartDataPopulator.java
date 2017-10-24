/**
 *
 */
package me.alanx.ecomer.web.populator.shoppingCart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.ProductImage;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionDescription;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionValueDescription;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.OrderSummary;
import me.alanx.ecomer.core.model.order.OrderTotalSummary;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCart;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem;
import me.alanx.ecomer.core.services.catalog.product.PricingService;
import me.alanx.ecomer.core.services.shoppingcart.ShoppingCartCalculationService;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.order.OrderTotal;
import me.alanx.ecomer.web.dto.shoppingcart.ShoppingCartAttribute;
import me.alanx.ecomer.web.dto.shoppingcart.ShoppingCartData;
import me.alanx.ecomer.web.dto.shoppingcart.ShoppingCartItem;
import me.alanx.ecomer.web.utils.ImageFilePath;


/**
 * @author Umesh A
 *
 */


public class ShoppingCartDataPopulator extends AbstractDataPopulator<ShoppingCart,ShoppingCartData>
{

    private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartDataPopulator.class);

    private PricingService pricingService;

    private  ShoppingCartCalculationService shoppingCartCalculationService;
    
    private ImageFilePath imageUtils;

			public ImageFilePath getimageUtils() {
				return imageUtils;
			}
		
		
		
		
			public void setimageUtils(ImageFilePath imageUtils) {
				this.imageUtils = imageUtils;
			}



    @Override
    public ShoppingCartData createTarget()
    {

        return new ShoppingCartData();
    }



    public ShoppingCartCalculationService getOrderService() {
        return shoppingCartCalculationService;
    }



    public PricingService getPricingService() {
        return pricingService;
    }


    @Override
    public ShoppingCartData populate(final ShoppingCart shoppingCart,
                                     final ShoppingCartData cart, final MerchantStore store, final Language language) throws ConversionException {

    	//Validate.notNull(imageUtils, "Requires to set imageUtils");
    	int cartQuantity = 0;
        cart.setCode(shoppingCart.getShoppingCartCode());
        Set<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem> items = shoppingCart.getLineItems();
        List<ShoppingCartItem> shoppingCartItemsList=Collections.emptyList();
        try{
            if(items!=null) {
                shoppingCartItemsList=new ArrayList<ShoppingCartItem>();
                for(me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem item : items) {

                    ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                    shoppingCartItem.setCode(cart.getCode());
                    shoppingCartItem.setProductCode(item.getProduct().getSku());
                    shoppingCartItem.setProductVirtual(item.isProductVirtual());

                    shoppingCartItem.setProductId(item.getProductId());
                    shoppingCartItem.setId(item.getId());
                    shoppingCartItem.setName(item.getProduct().getProductDescription().getName());

                    shoppingCartItem.setPrice(pricingService.getDisplayAmount(item.getItemPrice(),store));
                    shoppingCartItem.setQuantity(item.getQuantity());
                    
                    
                    cartQuantity = cartQuantity + item.getQuantity();
                    
                    shoppingCartItem.setProductPrice(item.getItemPrice());
                    shoppingCartItem.setSubTotal(pricingService.getDisplayAmount(item.getSubTotal(), store));
                    ProductImage image = item.getProduct().getProductImage();
                    if(image!=null && imageUtils!=null) {
                        String imagePath = imageUtils.buildProductImageUtils(store, item.getProduct().getSku(), image.getProductImage());
                        shoppingCartItem.setImage(imagePath);
                    }
                    Set<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem> attributes = item.getAttributes();
                    if(attributes!=null) {
                        List<ShoppingCartAttribute> cartAttributes = new ArrayList<ShoppingCartAttribute>();
                        for(me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem attribute : attributes) {
                            ShoppingCartAttribute cartAttribute = new ShoppingCartAttribute();
                            cartAttribute.setId(attribute.getId());
                            cartAttribute.setAttributeId(attribute.getProductAttributeId());
                            cartAttribute.setOptionId(attribute.getProductAttribute().getProductOption().getId());
                            cartAttribute.setOptionValueId(attribute.getProductAttribute().getProductOptionValue().getId());
                            List<ProductOptionDescription> optionDescriptions = attribute.getProductAttribute().getProductOption().getDescriptionsSettoList();
                            List<ProductOptionValueDescription> optionValueDescriptions = attribute.getProductAttribute().getProductOptionValue().getDescriptionsSettoList();
                            if(!CollectionUtils.isEmpty(optionDescriptions) && !CollectionUtils.isEmpty(optionValueDescriptions)) {
                            	cartAttribute.setOptionName(optionDescriptions.get(0).getName());
                            	cartAttribute.setOptionValue(optionValueDescriptions.get(0).getName());
                            	cartAttributes.add(cartAttribute);
                            }
                        }
                        shoppingCartItem.setShoppingCartAttributes(cartAttributes);
                    }
                    shoppingCartItemsList.add(shoppingCartItem);
                }
            }
            if(CollectionUtils.isNotEmpty(shoppingCartItemsList)){
                cart.setShoppingCartItems(shoppingCartItemsList);
            }

            OrderSummary summary = new OrderSummary();
            List<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem> productsList = new ArrayList<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem>();
            productsList.addAll(shoppingCart.getLineItems());
            summary.setProducts(productsList);
            OrderTotalSummary orderSummary = shoppingCartCalculationService.calculate(shoppingCart,store, language );

            if(CollectionUtils.isNotEmpty(orderSummary.getTotals())) {
            	List<OrderTotal> totals = new ArrayList<OrderTotal>();
            	for(me.alanx.ecomer.core.model.order.OrderTotal t : orderSummary.getTotals()) {
            		OrderTotal total = new OrderTotal();
            		total.setCode(t.getOrderTotalCode());
            		total.setValue(t.getValue());
            		totals.add(total);
            	}
            	cart.setTotals(totals);
            }
            
            cart.setSubTotal(pricingService.getDisplayAmount(orderSummary.getSubTotal(), store));
            cart.setTotal(pricingService.getDisplayAmount(orderSummary.getTotal(), store));
            cart.setQuantity(cartQuantity);
            cart.setId(shoppingCart.getId());
        }
        catch(ServiceException ex){
            LOG.error( "Error while converting cart Model to cart Data.."+ex );
            throw new ConversionException( "Unable to create cart data", ex );
        }
        return cart;


    };





    public void setPricingService(final PricingService pricingService) {
        this.pricingService = pricingService;
    }






    public void setShoppingCartCalculationService(final ShoppingCartCalculationService shoppingCartCalculationService) {
        this.shoppingCartCalculationService = shoppingCartCalculationService;
    }




}
