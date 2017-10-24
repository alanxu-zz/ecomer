/**
 * 
 */
package me.alanx.ecomer.web.populator.shoppingCart;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionPrice;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCart;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem;
import me.alanx.ecomer.core.services.catalog.product.ProductService;
import me.alanx.ecomer.core.services.catalog.product.attribute.ProductOptionPriceService;
import me.alanx.ecomer.core.services.shoppingcart.ShoppingCartService;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.shoppingcart.ShoppingCartAttribute;
import me.alanx.ecomer.web.dto.shoppingcart.ShoppingCartData;
import me.alanx.ecomer.web.dto.shoppingcart.ShoppingCartItem;

/**
 * @author Umesh A
 */

@Service(value="shoppingCartModelPopulator")
public class ShoppingCartModelPopulator
    extends AbstractDataPopulator<ShoppingCartData,ShoppingCart>
{

	private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartModelPopulator.class);

    private ShoppingCartService shoppingCartService;
    
    private Customer customer;

    public ShoppingCartService getShoppingCartService() {
		return shoppingCartService;
	}


	public void setShoppingCartService(ShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}


	private ProductService productService;


    public ProductService getProductService() {
		return productService;
	}


	public void setProductService(ProductService productService) {
		this.productService = productService;
	}


	private ProductOptionPriceService productAttributeService;
    
   
    public ProductOptionPriceService getProductAttributeService() {
		return productAttributeService;
	}


	public void setProductAttributeService(
			ProductOptionPriceService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}


	@Override
    public ShoppingCart populate(ShoppingCartData shoppingCart,ShoppingCart cartMdel,final MerchantStore store, Language language) throws ConversionException
    {


        // if id >0 get the original from the database, override products
       try{
        if ( shoppingCart.getId() > 0  && StringUtils.isNotBlank( shoppingCart.getCode()))
        {
            cartMdel = shoppingCartService.getByCode( shoppingCart.getCode(), store );
            if(cartMdel==null){
                cartMdel=new ShoppingCart();
                cartMdel.setShoppingCartCode( shoppingCart.getCode() );
                cartMdel.setMerchantStore( store );
                if ( customer != null )
                {
                    cartMdel.setCustomerId( customer.getId() );
                }
                shoppingCartService.create( cartMdel );
            }
        }
        else
        {
            cartMdel.setShoppingCartCode( shoppingCart.getCode() );
            cartMdel.setMerchantStore( store );
            if ( customer != null )
            {
                cartMdel.setCustomerId( customer.getId() );
            }
            shoppingCartService.create( cartMdel );
        }

        List<ShoppingCartItem> items = shoppingCart.getShoppingCartItems();
        Set<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem> newItems =
            new HashSet<>();
        if ( items != null && items.size() > 0 )
        {
            for ( ShoppingCartItem item : items )
            {

                Set<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem> cartItems = cartMdel.getLineItems();
                if ( cartItems != null && cartItems.size() > 0 )
                {

                    for ( me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem dbItem : cartItems )
                    {
                        if ( dbItem.getId().longValue() == item.getId() )
                        {
                            dbItem.setQuantity( item.getQuantity() );
                            // compare attributes
                            Set<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem> attributes =
                                dbItem.getAttributes();
                            Set<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem> newAttributes =
                                new HashSet<>();
                            List<ShoppingCartAttribute> cartAttributes = item.getShoppingCartAttributes();
                            if ( !CollectionUtils.isEmpty( cartAttributes ) )
                            {
                                for ( ShoppingCartAttribute attribute : cartAttributes )
                                {
                                    for ( me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem dbAttribute : attributes )
                                    {
                                        if ( dbAttribute.getId().longValue() == attribute.getId() )
                                        {
                                            newAttributes.add( dbAttribute );
                                        }
                                    }
                                }
                                
                                dbItem.setAttributes( newAttributes );
                            }
                            else
                            {
                                dbItem.removeAllAttributes();
                            }
                            newItems.add( dbItem );
                        }
                    }
                }
                else
                {// create new item
                	me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem cartItem =
                        createCartItem( cartMdel, item, store );
                    Set<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem> lineItems =
                        cartMdel.getLineItems();
                    if ( lineItems == null )
                    {
                        lineItems = new HashSet<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem>();
                        cartMdel.setLineItems( lineItems );
                    }
                    lineItems.add( cartItem );
                    shoppingCartService.update( cartMdel );
                }
            }// end for
        }// end if
       }catch(ServiceException se){
           LOG.error( "Error while converting cart data to cart model.."+se );
           throw new ConversionException( "Unable to create cart model", se ); 
       }
       catch (Exception ex){
           LOG.error( "Error while converting cart data to cart model.."+ex );
           throw new ConversionException( "Unable to create cart model", ex );  
       }

        return cartMdel;
    }

   
    private me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem createCartItem( me.alanx.ecomer.core.model.shoppingcart.ShoppingCart cart,
                                                                                               ShoppingCartItem shoppingCartItem,
                                                                                               MerchantStore store )
        throws Exception
    {

        Product product = productService.getById( shoppingCartItem.getProductId() );

        if ( product == null )
        {
            throw new Exception( "Item with id " + shoppingCartItem.getProductId() + " does not exist" );
        }

        if ( product.getMerchantStore().getId().intValue() != store.getId().intValue() )
        {
            throw new Exception( "Item with id " + shoppingCartItem.getProductId() + " does not belong to merchant "
                + store.getId() );
        }

        me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem item =
            new me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem( cart, product );
        item.setQuantity( shoppingCartItem.getQuantity() );
        item.setItemPrice( shoppingCartItem.getProductPrice() );
        item.setShoppingCart( cart );

        // attributes
        List<ShoppingCartAttribute> cartAttributes = shoppingCartItem.getShoppingCartAttributes();
        if ( !CollectionUtils.isEmpty( cartAttributes ) )
        {
            Set<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem> newAttributes =
                new HashSet<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem>();
            for ( ShoppingCartAttribute attribute : cartAttributes )
            {
                ProductOptionPrice productAttribute = productAttributeService.getById( attribute.getAttributeId() );
                if ( productAttribute != null
                    && productAttribute.getProduct().getId().longValue() == product.getId().longValue() )
                {
                	me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem attributeItem =
                        new me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem( item,
                                                                                                         productAttribute );
                    if ( attribute.getAttributeId() > 0 )
                    {
                        attributeItem.setId( attribute.getId() );
                    }
                    item.addAttributes( attributeItem );
                    //newAttributes.add( attributeItem );
                }

            }
            
            //item.setAttributes( newAttributes );
        }

        return item;

    }




    @Override
    protected ShoppingCart createTarget()
    {
      
        return new ShoppingCart();
    }


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


   


   

   

}
