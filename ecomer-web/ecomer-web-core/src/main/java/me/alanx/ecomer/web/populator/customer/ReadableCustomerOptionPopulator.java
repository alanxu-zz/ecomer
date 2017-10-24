package me.alanx.ecomer.web.populator.customer;

import java.util.ArrayList;
import java.util.List;


import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.customer.attribute.CustomerOption;
import me.alanx.ecomer.core.model.customer.attribute.CustomerOptionSet;
import me.alanx.ecomer.core.model.customer.attribute.CustomerOptionValue;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;



public class ReadableCustomerOptionPopulator extends
		AbstractDataPopulator<CustomerOption, me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOption> {

	
	private CustomerOptionSet optionSet;
	
	public CustomerOptionSet getOptionSet() {
		return optionSet;
	}

	public void setOptionSet(CustomerOptionSet optionSet) {
		this.optionSet = optionSet;
	}
	

	@Override
	public me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOption populate(
			CustomerOption source,
			me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOption target, MerchantStore store, Language language) throws ConversionException {
		
		
		me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOption customerOption = target;
		if(customerOption==null) {
			customerOption = new me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOption();
		} 
		
		customerOption.setId(source.getId());
		customerOption.setType(source.getCustomerOptionType());
		customerOption.setName(source.getDescriptionsSettoList().get(0).getName());

		List<me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOptionValue> values = customerOption.getAvailableValues();
		if(values==null) {
			values = new ArrayList<me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOptionValue>();
			customerOption.setAvailableValues(values);
		}
		
		CustomerOptionValue optionValue = optionSet.getCustomerOptionValue();
		me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOptionValue custOptValue = new me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOptionValue();
		custOptValue.setId(optionValue.getId());
		custOptValue.setLanguage(language.getCode());
		custOptValue.setName(optionValue.getDescriptionsSettoList().get(0).getName());
		values.add(custOptValue);
		
		return customerOption;

	}

    @Override
    protected me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOption createTarget()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
