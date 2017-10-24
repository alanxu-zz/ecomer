package me.alanx.ecomer.core.services.tax;

import java.util.List;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.tax.taxclass.TaxClass;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface TaxClassService extends SalesManagerEntityService<Long, TaxClass> {

	public List<TaxClass> listByStore(MerchantStore store) throws ServiceException;

	TaxClass getByCode(String code) throws ServiceException;

	TaxClass getByCode(String code, MerchantStore store)
			throws ServiceException;

}
