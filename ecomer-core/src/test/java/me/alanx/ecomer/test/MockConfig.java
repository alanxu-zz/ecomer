package me.alanx.ecomer.test;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import me.alanx.ecomer.core.services.catalog.product.image.ProductImageService;
import me.alanx.ecomer.core.services.codec.EncryptionService;
import me.alanx.ecomer.core.services.geo.GeoLocationService;
import me.alanx.ecomer.core.services.invoice.InvoiceService;
import me.alanx.ecomer.core.services.system.EmailSender;
import me.alanx.ecomer.integration.modules.ModularizedPaymentService;

@Profile("test")
@Configuration
public class MockConfig {
	
	@Bean
	@Primary
	public ProductImageService productImageService() {
		return mock(ProductImageService.class);
	}

	@Bean
	@Primary
	public GeoLocationService geoLocationService() {
		return mock(GeoLocationService.class);
	}
	
	@Bean
	@Primary
	public InvoiceService invoiceService() {
		return mock(InvoiceService.class);
	}
	
	@Bean
	@Primary
	public EncryptionService encryptionService() {
		return mock(EncryptionService.class);
	}
	
	@Bean
	@Primary
	public ModularizedPaymentService paymentService1() {
		return mock(ModularizedPaymentService.class);
	}
	
	@Bean
	@Primary
	public ModularizedPaymentService paymentService2() {
		return mock(ModularizedPaymentService.class);
	}
	
	@Bean
	@Primary
	public EmailSender emailSender() {
		return mock(EmailSender.class);
	}
	
	
}
