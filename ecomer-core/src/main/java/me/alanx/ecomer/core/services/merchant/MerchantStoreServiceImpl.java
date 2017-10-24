package me.alanx.ecomer.core.services.merchant;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.auth.User;
import me.alanx.ecomer.core.model.catalog.category.Category;
import me.alanx.ecomer.core.model.catalog.product.Manufacturer;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.Order;
import me.alanx.ecomer.core.model.system.MerchantConfiguration;
import me.alanx.ecomer.core.model.tax.taxclass.TaxClass;
import me.alanx.ecomer.core.repositories.merchant.MerchantRepository;
import me.alanx.ecomer.core.services.catalog.product.manufacturer.ManufacturerService;
import me.alanx.ecomer.core.services.catalog.product.type.ProductTypeService;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;
import me.alanx.ecomer.core.services.tax.TaxClassService;

@Service("merchantService")
public class MerchantStoreServiceImpl extends SalesManagerEntityServiceImpl<Integer, MerchantStore> 
		implements MerchantStoreService {
	

		
	@Inject
	protected ProductTypeService productTypeService;
	
	@Inject
	private TaxClassService taxClassService;
	
/*	@Inject
	private ContentService contentService;
	
	@Inject
	private MerchantConfigurationService merchantConfigurationService;
	
	@Inject
	private CategoryService categoryService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private OrderService orderService;
	
	@Inject
	private CustomerService customerService;*/
	
	@Inject
	private ManufacturerService manufacturerService;
	
	private MerchantRepository merchantRepository;
	
	@Inject
	public MerchantStoreServiceImpl(MerchantRepository merchantRepository) {
		super(merchantRepository);
		this.merchantRepository = merchantRepository;
	}


	public MerchantStore getMerchantStore(String merchantStoreCode) throws ServiceException {
		return merchantRepository.findByCode(merchantStoreCode);
	}
	
	@Override
	public void saveOrUpdate(MerchantStore store) throws ServiceException {
				
		super.save(store);

	}
	

	@Override
	public MerchantStore getByCode(String code) throws ServiceException {
		
		return merchantRepository.findByCode(code);
	}
	
/*	@Override
	public void delete(MerchantStore merchant) throws ServiceException {
		
		merchant = this.getById(merchant.getId());
		
		
		//reference
		List<Manufacturer> manufacturers = manufacturerService.listByStore(merchant);
		for(Manufacturer manufacturer : manufacturers) {
			manufacturerService.delete(manufacturer);
		}
		
		List<MerchantConfiguration> configurations = merchantConfigurationService.listByStore(merchant);
		for(MerchantConfiguration configuration : configurations) {
			merchantConfigurationService.delete(configuration);
		}
		

		//TODO taxService
		List<TaxClass> taxClasses = taxClassService.listByStore(merchant);
		for(TaxClass taxClass : taxClasses) {
			taxClassService.delete(taxClass);
		}
		
		//content
		contentService.removeFiles(merchant.getCode());
		//TODO staticContentService.removeImages
		
		//category / product
		List<Category> categories = categoryService.listByStore(merchant);
		for(Category category : categories) {
			categoryService.delete(category);
		}

		//users
		List<User> users = userService.listByStore(merchant);
		for(User user : users) {
			userService.delete(user);
		}
		
		//customers
		List<Customer> customers = customerService.listByStore(merchant);
		for(Customer customer : customers) {
			customerService.delete(customer);
		}
		
		//orders
		List<Order> orders = orderService.listByStore(merchant);
		for(Order order : orders) {
			orderService.delete(order);
		}
		
		super.delete(merchant);
		
	}*/

}
