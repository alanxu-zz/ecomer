/**
 *
 */
package me.alanx.ecomer.web.store.controller.customer.facade;

import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCart;
import me.alanx.ecomer.core.services.customer.CustomerService;
import me.alanx.ecomer.web.dto.customer.Address;
import me.alanx.ecomer.web.dto.customer.CustomerEntity;
import me.alanx.ecomer.web.dto.customer.PersistableCustomer;
import me.alanx.ecomer.web.dto.customer.ReadableCustomer;

/**
 * <p>Customer facade working as a bridge between {@link CustomerService} and Controller
 * It will take care about interacting with Service API and doing any pre and post processing
 * </p>
 *
 * @author Umesh Awasthi
 * @version 1/2
 *
 *
 */
public interface CustomerFacade
{

    /**
     * Method used to fetch customer based on the username and storecode.
     * Customer username is unique to each store.
     *
     * @param userName
     * @param store
     * @param store
     * @param language
     * @throws Exception
     *
     */
    public CustomerEntity getCustomerDataByUserName(final String userName,final MerchantStore store, final Language language) throws Exception;

    /**
     * Creates a ReadableCustomer
     * @param id
     * @param merchantStore
     * @param language
     * @return
     */
    public ReadableCustomer getCustomerById(final Long id, final MerchantStore merchantStore, final Language language) throws Exception;
    
    /**
     * <p>Method responsible for merging cart during authentication, 
     *     Method will perform following operations
     * <li> Merge Customer Shopping Cart with Session Cart if any.</li>
     * <li> Convert Customer to {@link CustomerEntity} </li>
     * </p>
     *
     * @param customer username of Customer
     * @param sessionShoppingCartId session shopping cart, if user already have few items in Cart.
     * @throws Exception
     */
    public ShoppingCart mergeCart(final Customer customer,final String sessionShoppingCartId,final MerchantStore store,final Language language) throws Exception;
    
    public Customer getCustomerByUserName(final String userName, final MerchantStore store) throws Exception;
    
    public boolean checkIfUserExists(final String userName,final MerchantStore store) throws Exception;
    
    public CustomerEntity  registerCustomer( final PersistableCustomer customer,final MerchantStore merchantStore, final Language language) throws Exception;
    
    public Address getAddress(final Long userId, final MerchantStore merchantStore,boolean isBillingAddress) throws Exception;
    
    public void updateAddress( Long userId, MerchantStore merchantStore, Address address, final Language language )
                    throws Exception;

    public void setCustomerModelDefaultProperties(Customer customer, MerchantStore store) throws Exception; 
	//public Customer populateCustomerModel(PersistableCustomer customer,
	//		MerchantStore merchantStore) throws Exception;
	
	public void authenticate(Customer customer, String userName, String password) throws Exception;

	Customer getCustomerModel(PersistableCustomer customer,
			MerchantStore merchantStore, Language language) throws Exception;
	
	Customer populateCustomerModel(Customer customerModel, PersistableCustomer customer,
			MerchantStore merchantStore, Language language) throws Exception;
	

	

}
