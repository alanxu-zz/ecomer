package me.alanx.ecomer.core.repositories.reference.currency;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.reference.Currency;

public interface CurrencyRepository extends JpaRepository <Currency, Long> {

	
	Currency getByCode(String code);
}
