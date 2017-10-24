package me.alanx.ecomer.core.services.customer.attribute;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.customer.attribute.CustomerAttribute;
import me.alanx.ecomer.core.model.customer.attribute.CustomerOptionSet;
import me.alanx.ecomer.core.model.customer.attribute.CustomerOptionValue;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.repositories.customer.attribute.CustomerOptionValueRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;


@Service("customerOptionValueService")
public class CustomerOptionValueServiceImpl extends
		SalesManagerEntityServiceImpl<Long, CustomerOptionValue> implements
		CustomerOptionValueService {

	@Inject
	private CustomerAttributeService customerAttributeService;
	
	private CustomerOptionValueRepository customerOptionValueRepository;
	
	@Inject
	private CustomerOptionSetService customerOptionSetService;
	
	@Inject
	public CustomerOptionValueServiceImpl(
			CustomerOptionValueRepository customerOptionValueRepository) {
			super(customerOptionValueRepository);
			this.customerOptionValueRepository = customerOptionValueRepository;
	}
	
	
	@Override
	public List<CustomerOptionValue> listByStore(MerchantStore store, Language language) throws ServiceException {
		
		return customerOptionValueRepository.findByStore(store.getId(), language.getId());
	}
	


	
	@Override
	public void saveOrUpdate(CustomerOptionValue entity) throws ServiceException {
		
		
		//save or update (persist and attach entities
		if(entity.getId()!=null && entity.getId()>0) {

			super.update(entity);
			
		} else {
			
			super.save(entity);
			
		}
		
	}
	
	
	public void delete(CustomerOptionValue customerOptionValue) throws ServiceException {
		
		//remove all attributes having this option
		List<CustomerAttribute> attributes = customerAttributeService.getByCustomerOptionValueId(customerOptionValue.getMerchantStore(), customerOptionValue.getId());
		
		for(CustomerAttribute attribute : attributes) {
			customerAttributeService.delete(attribute);
		}
		
		List<CustomerOptionSet> optionSets = customerOptionSetService.listByOptionValue(customerOptionValue, customerOptionValue.getMerchantStore());
		
		for(CustomerOptionSet optionSet : optionSets) {
			customerOptionSetService.delete(optionSet);
		}
		
		CustomerOptionValue option = super.getById(customerOptionValue.getId());
		
		//remove option
		super.delete(option);
		
	}
	
	@Override
	public CustomerOptionValue getByCode(MerchantStore store, String optionValueCode) {
		return customerOptionValueRepository.findByCode(store.getId(), optionValueCode);
	}



}
