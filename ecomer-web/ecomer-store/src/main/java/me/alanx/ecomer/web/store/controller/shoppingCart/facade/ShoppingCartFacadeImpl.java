/**
 *
 */
package me.alanx.ecomer.web.store.controller.shoppingCart.facade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOptionPrice;
import me.alanx.ecomer.core.model.catalog.product.price.FinalPrice;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCart;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem;
import me.alanx.ecomer.core.services.catalog.product.PricingService;
import me.alanx.ecomer.core.services.catalog.product.ProductService;
import me.alanx.ecomer.core.services.catalog.product.attribute.ProductOptionPriceService;
import me.alanx.ecomer.core.services.shoppingcart.ShoppingCartCalculationService;
import me.alanx.ecomer.core.services.shoppingcart.ShoppingCartService;
import me.alanx.ecomer.core.utils.ProductPriceUtils;
import me.alanx.ecomer.web.constants.Constants;
import me.alanx.ecomer.web.dto.order.CartModificationException;
import me.alanx.ecomer.web.dto.shoppingcart.ShoppingCartAttribute;
import me.alanx.ecomer.web.dto.shoppingcart.ShoppingCartData;
import me.alanx.ecomer.web.dto.shoppingcart.ShoppingCartItem;
import me.alanx.ecomer.web.populator.shoppingCart.ShoppingCartDataPopulator;
import me.alanx.ecomer.web.utils.ImageFilePath;

/**
 * @author Umesh Awasthi
 * @version 1.2
 * @since 1.2
 */
@Service( value = "shoppingCartFacade" )
public class ShoppingCartFacadeImpl
    implements ShoppingCartFacade
{

    
    private static final Logger LOG = LoggerFactory.getLogger(ShoppingCartFacadeImpl.class);

    @Inject
    private ShoppingCartService shoppingCartService;

    @Inject
    ShoppingCartCalculationService shoppingCartCalculationService;

    @Inject
    private ProductService productService;

    @Inject
    private PricingService pricingService;

    @Inject
    private ProductOptionPriceService productAttributeService;
    
	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;

    public void deleteShoppingCart(final Long id, final MerchantStore store) throws Exception {
    	ShoppingCart cart = shoppingCartService.getById(id, store);
    	if(cart!=null) {
    		shoppingCartService.deleteCart(cart);
    	}
    }
    
    @Override
    public void deleteShoppingCart(final String code, final MerchantStore store) throws Exception {
    	ShoppingCart cart = shoppingCartService.getByCode(code, store);
    	if(cart!=null) {
    		shoppingCartService.deleteCart(cart);
    	}
    }

    @Override
    public ShoppingCartData addItemsToShoppingCart( final ShoppingCartData shoppingCartData,
                                                    final ShoppingCartItem item, final MerchantStore store, final Language language,final Customer customer )
        throws Exception
    {

        ShoppingCart cartModel = null;
        if ( !StringUtils.isBlank( item.getCode() ) )
        {
            // get it from the db
            cartModel = getShoppingCartModel( item.getCode(), store );
            if ( cartModel == null )
            {
                cartModel = createCartModel( shoppingCartData.getCode(), store,customer );
            }

        }

        if ( cartModel == null )
        {

            final String shoppingCartCode =
                StringUtils.isNotBlank( shoppingCartData.getCode() ) ? shoppingCartData.getCode() : null;
            cartModel = createCartModel( shoppingCartCode, store,customer );

        }
        me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem shoppingCartItem =
            createCartItem( cartModel, item, store );
        
        boolean duplicateFound = false;
        if(CollectionUtils.isEmpty(item.getShoppingCartAttributes())) {//increment quantity
        	//get duplicate item from the cart
        	Set<me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem> cartModelItems = cartModel.getLineItems();
        	for(me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem cartItem : cartModelItems) {
        		if(cartItem.getProduct().getId().longValue()==shoppingCartItem.getProduct().getId().longValue()) {
        			if(CollectionUtils.isEmpty(cartItem.getAttributes())) {
        				if(!duplicateFound) {
        					if(!shoppingCartItem.isProductVirtual()) {
	        					cartItem.setQuantity(cartItem.getQuantity() + shoppingCartItem.getQuantity());
        					}
        					duplicateFound = true;
        					break;
        				}
        			}
        		}
        	}
        } 
        
        if(!duplicateFound) {
        	cartModel.getLineItems().add( shoppingCartItem );
        }
        
        /** Update cart in database with line items **/
        shoppingCartService.saveOrUpdate( cartModel );

        //refresh cart
        cartModel = shoppingCartService.getById(cartModel.getId(), store);

        shoppingCartCalculationService.calculate( cartModel, store, language );

        ShoppingCartDataPopulator shoppingCartDataPopulator = new ShoppingCartDataPopulator();
        shoppingCartDataPopulator.setShoppingCartCalculationService( shoppingCartCalculationService );
        shoppingCartDataPopulator.setPricingService( pricingService );
        shoppingCartDataPopulator.setimageUtils(imageUtils);

        return shoppingCartDataPopulator.populate( cartModel, store, language );
    }

    private me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem createCartItem( final ShoppingCart cartModel,
                                                                                               final ShoppingCartItem shoppingCartItem,
                                                                                               final MerchantStore store )
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
            shoppingCartService.populateShoppingCartItem( product );

        item.setQuantity( shoppingCartItem.getQuantity() );
        item.setShoppingCart( cartModel );

        // attributes
        List<ShoppingCartAttribute> cartAttributes = shoppingCartItem.getShoppingCartAttributes();
        if ( !CollectionUtils.isEmpty( cartAttributes ) )
        {
            for ( ShoppingCartAttribute attribute : cartAttributes )
            {
                ProductOptionPrice productAttribute = productAttributeService.getById( attribute.getAttributeId() );
                if ( productAttribute != null
                    && productAttribute.getProduct().getId().longValue() == product.getId().longValue() )
                {
                	 me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem attributeItem =
                        new  me.alanx.ecomer.core.model.shoppingcart.ShoppingCartAttributeItem( item,
                                                                                                         productAttribute );

                    item.addAttributes( attributeItem );
                }
            }
        }
        return item;

    }

    @Override
    public ShoppingCart createCartModel( final String shoppingCartCode, final MerchantStore store,final Customer customer )
        throws Exception
    {
        final Long CustomerId = customer != null ? customer.getId() : null;
        ShoppingCart cartModel = new ShoppingCart();
        if ( StringUtils.isNotBlank( shoppingCartCode ) )
        {
            cartModel.setShoppingCartCode( shoppingCartCode );
        }
        else
        {
            cartModel.setShoppingCartCode( UUID.randomUUID().toString().replaceAll( "-", "" ) );
        }

        cartModel.setMerchantStore( store );
        if ( CustomerId != null )
        {
            cartModel.setCustomerId( CustomerId );
        }
        shoppingCartService.create( cartModel );
        return cartModel;
    }





    private  me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem getEntryToUpdate( final long entryId,
                                                                                                 final ShoppingCart cartModel )
    {
        if ( CollectionUtils.isNotEmpty( cartModel.getLineItems() ) )
        {
            for (  me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem shoppingCartItem : cartModel.getLineItems() )
            {
                if ( shoppingCartItem.getId().longValue() == entryId )
                {
                    LOG.info( "Found line item  for given entry id: " + entryId );
                    return shoppingCartItem;

                }
            }
        }
        LOG.info( "Unable to find any entry for given Id: " + entryId );
        return null;
    }

    private Object getKeyValue( final String key )
    {
        ServletRequestAttributes reqAttr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return reqAttr.getRequest().getAttribute( key );
    }

    @Override
    public ShoppingCartData getShoppingCartData( final Customer customer, final MerchantStore store,
                                                 final String shoppingCartId )
        throws Exception
    {

        ShoppingCart cart = null;
        try
        {
            if ( customer != null )
            {
                LOG.info( "Reteriving customer shopping cart..." );

                cart = shoppingCartService.getShoppingCart( customer );

            }

            else
            {
                if ( StringUtils.isNotBlank( shoppingCartId ) && cart == null )
                {
                    cart = shoppingCartService.getByCode( shoppingCartId, store );
                }

            }
        }
        catch ( ServiceException ex )
        {
            LOG.error( "Error while retriving cart from customer", ex );
        }
        catch( NoResultException nre) {
        	//nothing
        }

        if ( cart == null )
        {
            return null;
        }

        LOG.info( "Cart model found." );

        ShoppingCartDataPopulator shoppingCartDataPopulator = new ShoppingCartDataPopulator();
        shoppingCartDataPopulator.setShoppingCartCalculationService( shoppingCartCalculationService );
        shoppingCartDataPopulator.setPricingService( pricingService );
        shoppingCartDataPopulator.setimageUtils(imageUtils);

        Language language = (Language) getKeyValue( Constants.LANGUAGE );
        MerchantStore merchantStore = (MerchantStore) getKeyValue( Constants.MERCHANT_STORE );
        
        ShoppingCartData shoppingCartData = shoppingCartDataPopulator.populate( cart, merchantStore, language );
        
/*        List<ShoppingCartItem> unavailables = new ArrayList<ShoppingCartItem>();
        List<ShoppingCartItem> availables = new ArrayList<ShoppingCartItem>();
        //Take out items no more available
        List<ShoppingCartItem> items = shoppingCartData.getShoppingCartItems();
        for(ShoppingCartItem item : items) {
        	String code = item.getProductCode();
        	Product p =productService.getByCode(code, language);
        	if(!p.isAvailable()) {
        		unavailables.add(item);
        	} else {
        		availables.add(item);
        	}
        	
        }
        shoppingCartData.setShoppingCartItems(availables);
        shoppingCartData.setUnavailables(unavailables);*/
        
        return shoppingCartData;

    }

    @Override
    public ShoppingCartData getShoppingCartData( final ShoppingCart shoppingCartModel )
        throws Exception
    {

        ShoppingCartDataPopulator shoppingCartDataPopulator = new ShoppingCartDataPopulator();
        shoppingCartDataPopulator.setShoppingCartCalculationService( shoppingCartCalculationService );
        shoppingCartDataPopulator.setPricingService( pricingService );
        shoppingCartDataPopulator.setimageUtils(imageUtils);
        Language language = (Language) getKeyValue( Constants.LANGUAGE );
        MerchantStore merchantStore = (MerchantStore) getKeyValue( Constants.MERCHANT_STORE );
        return shoppingCartDataPopulator.populate( shoppingCartModel, merchantStore, language );
    }

    @Override
    public ShoppingCartData removeCartItem( final Long itemID, final String cartId ,final MerchantStore store,final Language language )
        throws Exception
    {
        if ( StringUtils.isNotBlank( cartId ) )
        {

            ShoppingCart cartModel = getCartModel( cartId,store );
            if ( cartModel != null )
            {
                if ( CollectionUtils.isNotEmpty( cartModel.getLineItems() ) )
                {
                    Set< me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem> shoppingCartItemSet =
                        new HashSet< me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem>();
                    for (  me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem shoppingCartItem : cartModel.getLineItems() )
                    {
                        //if ( shoppingCartItem.getId().longValue() != itemID.longValue() )
                    	if ( shoppingCartItem.getId().longValue() == itemID.longValue() )
                        {
                            //shoppingCartItemSet.add( shoppingCartItem );
                    		shoppingCartService.deleteShoppingCartItem(itemID);
                        }
                    }
                    //cartModel.setLineItems( shoppingCartItemSet );
                    //shoppingCartService.saveOrUpdate( cartModel );

                    cartModel = getCartModel( cartId,store );


                    ShoppingCartDataPopulator shoppingCartDataPopulator = new ShoppingCartDataPopulator();
                    shoppingCartDataPopulator.setShoppingCartCalculationService( shoppingCartCalculationService );
                    shoppingCartDataPopulator.setPricingService( pricingService );
                    shoppingCartDataPopulator.setimageUtils(imageUtils);
                    return shoppingCartDataPopulator.populate( cartModel, store, language );
                }
            }
        }
        return null;
    }

    @Override
    public ShoppingCartData updateCartItem( final Long itemID, final String cartId, final long newQuantity,final MerchantStore store, final Language language )
        throws Exception
    {
        if ( newQuantity < 1 )
        {
            throw new CartModificationException( "Quantity must not be less than one" );
        }
        if ( StringUtils.isNotBlank( cartId ) )
        {
            ShoppingCart cartModel = getCartModel( cartId,store );
            if ( cartModel != null )
            {
            	 me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem entryToUpdate =
                    getEntryToUpdate( itemID.longValue(), cartModel );

                if ( entryToUpdate == null )
                {
                    throw new CartModificationException( "Unknown entry number." );
                }

                entryToUpdate.getProduct();

                LOG.info( "Updating cart entry quantity to" + newQuantity );
                entryToUpdate.setQuantity( (int) newQuantity );
                List<ProductOptionPrice> productAttributes = new ArrayList<ProductOptionPrice>();
                productAttributes.addAll( entryToUpdate.getProduct().getOptions() );
                final FinalPrice finalPrice =
                		ProductPriceUtils.getFinalProductPrice( entryToUpdate.getProduct(), productAttributes );
                entryToUpdate.setItemPrice( finalPrice.getFinalPrice() );
                shoppingCartService.saveOrUpdate( cartModel );

                LOG.info( "Cart entry updated with desired quantity" );
                ShoppingCartDataPopulator shoppingCartDataPopulator = new ShoppingCartDataPopulator();
                shoppingCartDataPopulator.setShoppingCartCalculationService( shoppingCartCalculationService );
                shoppingCartDataPopulator.setPricingService( pricingService );
                shoppingCartDataPopulator.setimageUtils(imageUtils);
                return shoppingCartDataPopulator.populate( cartModel, store, language );

            }
        }
        return null;
    }
    
    @Override
    public ShoppingCartData updateCartItems( final List<ShoppingCartItem> shoppingCartItems, final MerchantStore store, final Language language )
            throws Exception
        {
    	
    		Validate.notEmpty(shoppingCartItems,"shoppingCartItems null or empty");
    		ShoppingCart cartModel = null;
    		Set< me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem> cartItems = new HashSet< me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem>();
    		for(ShoppingCartItem item : shoppingCartItems) {
    			
    			if(item.getQuantity()<1) {
    				throw new CartModificationException( "Quantity must not be less than one" );
    			}
    			
    			if(cartModel==null) {
    				cartModel = getCartModel( item.getCode(), store );
    			}
    			
    			 me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem entryToUpdate =
                        getEntryToUpdate( item.getId(), cartModel );

                if ( entryToUpdate == null ) {
                        throw new CartModificationException( "Unknown entry number." );
                }

                entryToUpdate.getProduct();

                LOG.info( "Updating cart entry quantity to" + item.getQuantity() );
                entryToUpdate.setQuantity( (int) item.getQuantity() );
                
                List<ProductOptionPrice> productAttributes = new ArrayList<ProductOptionPrice>();
                productAttributes.addAll( entryToUpdate.getProduct().getOptions() );
                
                final FinalPrice finalPrice =
                		ProductPriceUtils.getFinalProductPrice( entryToUpdate.getProduct(), productAttributes );
                entryToUpdate.setItemPrice( finalPrice.getFinalPrice() );
                    

                cartItems.add(entryToUpdate);
    			
    			
    			
    			
    		}
    		
    		cartModel.setLineItems(cartItems);
    		shoppingCartService.saveOrUpdate( cartModel );
            LOG.info( "Cart entry updated with desired quantity" );
            ShoppingCartDataPopulator shoppingCartDataPopulator = new ShoppingCartDataPopulator();
            shoppingCartDataPopulator.setShoppingCartCalculationService( shoppingCartCalculationService );
            shoppingCartDataPopulator.setPricingService( pricingService );
            shoppingCartDataPopulator.setimageUtils(imageUtils);
            return shoppingCartDataPopulator.populate( cartModel, store, language );

        }


    private ShoppingCart getCartModel( final String cartId,final MerchantStore store )
    {
        if ( StringUtils.isNotBlank( cartId ) )
        {
           try
            {
                return shoppingCartService.getByCode( cartId, store );
            }
            catch ( ServiceException e )
            {
                LOG.error( "unable to find any cart asscoiated with this Id: " + cartId );
                LOG.error( "error while fetching cart model...", e );
                return null;
            }
            catch( NoResultException nre) {
           	//nothing
            }

        }
        return null;
    }

	@Override
	public ShoppingCartData getShoppingCartData(String code, MerchantStore store) {
		try {
			ShoppingCart cartModel = shoppingCartService.getByCode( code, store );
			if(cartModel!=null) {
				ShoppingCartData cart = getShoppingCartData(cartModel);
				return cart;
			}
		} catch( NoResultException nre) {
	        	//nothing

		} catch(Exception e) {
			LOG.error("Cannot retrieve cart code " + code,e);
		}


		return null;
	}

	@Override
	public ShoppingCart getShoppingCartModel(String shoppingCartCode,
			MerchantStore store) throws Exception {
		return shoppingCartService.getByCode( shoppingCartCode, store );
	}

	@Override
	public ShoppingCart getShoppingCartModel(Customer customer,
			MerchantStore store) throws Exception {
		return shoppingCartService.getByCustomer(customer);
	}

	@Override
	public void saveOrUpdateShoppingCart(ShoppingCart cart) throws Exception {
		shoppingCartService.saveOrUpdate(cart);
		
	}


}
