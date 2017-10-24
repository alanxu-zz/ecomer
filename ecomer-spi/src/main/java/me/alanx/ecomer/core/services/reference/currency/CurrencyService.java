package me.alanx.ecomer.core.services.reference.currency;

import me.alanx.ecomer.core.model.reference.Currency;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

public interface CurrencyService extends SalesManagerEntityService<Long, Currency> {

	Currency getByCode(String code);

}
