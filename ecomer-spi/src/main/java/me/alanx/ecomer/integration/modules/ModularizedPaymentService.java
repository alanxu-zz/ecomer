package me.alanx.ecomer.integration.modules;

import me.alanx.ecomer.core.services.payments.PaymentService;

public abstract class ModularizedPaymentService  extends SpringBasedModule implements PaymentService{

	public ModularizedPaymentService(String moduleName) {
		super(moduleName);
	}

}
