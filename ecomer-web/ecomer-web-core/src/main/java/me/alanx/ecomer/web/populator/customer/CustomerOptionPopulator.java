package me.alanx.ecomer.web.populator.customer;


import java.util.ArrayList;
import java.util.List;

import me.alanx.ecomer.core.exception.ConversionException;
import me.alanx.ecomer.core.model.customer.attribute.CustomerOptionSet;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.data.populator.AbstractDataPopulator;
import me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOption;
import me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOptionValue;



/**
 * Used in the admin section
 * @author c.samson
 *
 */

public class CustomerOptionPopulator extends
		AbstractDataPopulator<me.alanx.ecomer.core.model.customer.attribute.CustomerOption, me.alanx.ecomer.web.dto.admin.customer.attribute.CustomerOption> {

	
	private CustomerOptionSet optionSet;
	
	public CustomerOptionSet getOptionSet() {
		return optionSet;
	}

	public void setOptionSet(CustomerOptionSet optionSet) {
		this.optionSet = optionSet;
	}

	@Override
	public CustomerOption populate(
			me.alanx.ecomer.core.model.customer.attribute.CustomerOption source,
			CustomerOption target, MerchantStore store, Language language) throws ConversionException {
		
		
		CustomerOption customerOption = target;
		if(customerOption==null) {
			customerOption = new CustomerOption();
		} 
		
		customerOption.setId(source.getId());
		customerOption.setType(source.getCustomerOptionType());
		customerOption.setName(source.getDescriptionsSettoList().get(0).getName());

		List<CustomerOptionValue> values = customerOption.getAvailableValues();
		if(values==null) {
			values = new ArrayList<CustomerOptionValue>();
			customerOption.setAvailableValues(values);
		}
		
		me.alanx.ecomer.core.model.customer.attribute.CustomerOptionValue optionValue = optionSet.getCustomerOptionValue();
		CustomerOptionValue custOptValue = new CustomerOptionValue();
		custOptValue.setId(optionValue.getId());
		custOptValue.setLanguage(language.getCode());
		custOptValue.setName(optionValue.getDescriptionsSettoList().get(0).getName());
		values.add(custOptValue);
		
		return customerOption;

	}

    @Override
    protected CustomerOption createTarget()
    {
        // TODO Auto-generated method stub
        return null;
    }


}
