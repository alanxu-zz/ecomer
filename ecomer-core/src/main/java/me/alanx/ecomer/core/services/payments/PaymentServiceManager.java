package me.alanx.ecomer.core.services.payments;

import org.springframework.stereotype.Service;

import me.alanx.ecomer.integration.modules.ModularizedPaymentService;
import me.alanx.ecomer.integration.modules.SpringModuleManager;

@Service
public class PaymentServiceManager extends SpringModuleManager<ModularizedPaymentService>{}
