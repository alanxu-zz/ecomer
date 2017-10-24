package me.alanx.ecomer.core.services.reference.currency;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.model.reference.Currency;
import me.alanx.ecomer.core.repositories.reference.currency.CurrencyRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("currencyService")
public class CurrencyServiceImpl extends SalesManagerEntityServiceImpl<Long, Currency>
	implements CurrencyService {
	
	private CurrencyRepository currencyRepository;
	
	@Inject
	public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
		super(currencyRepository);
		this.currencyRepository = currencyRepository;
	}

	@Override
	public Currency getByCode(String code) {
		return currencyRepository.getByCode(code);
	}

}
