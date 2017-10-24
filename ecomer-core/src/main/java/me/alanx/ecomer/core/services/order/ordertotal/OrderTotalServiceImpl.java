package me.alanx.ecomer.core.services.order.ordertotal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.customer.Customer;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.OrderSummary;
import me.alanx.ecomer.core.model.order.OrderTotal;
import me.alanx.ecomer.core.model.order.OrderTotalVariation;
import me.alanx.ecomer.core.model.order.RebatesOrderTotalVariation;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.model.shoppingcart.ShoppingCartItem;
import me.alanx.ecomer.core.services.catalog.product.ProductService;
import me.alanx.ecomer.core.services.reference.language.LanguageService;

@Service("OrderTotalService")
public class OrderTotalServiceImpl implements OrderTotalService {
	
	@Autowired(required = false)
	@Qualifier("orderTotalsPostProcessors")
	List<OrderTotalPostProcessor> orderTotalPostProcessors;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private LanguageService languageService;

	@Override
	public OrderTotalVariation findOrderTotalVariation(OrderSummary summary, Customer customer, MerchantStore store, Language language)
			throws Exception {
	
		RebatesOrderTotalVariation variation = new RebatesOrderTotalVariation();
		
		List<OrderTotal> totals = null;
		
		if(orderTotalPostProcessors != null) {
			for(OrderTotalPostProcessor module : orderTotalPostProcessors) {
				//TODO check if the module is enabled from the Admin
				
				List<ShoppingCartItem> items = summary.getProducts();
				for(ShoppingCartItem item : items) {
					
					Long productId = item.getProductId();
					Product product = productService.getProductForLocale(productId, language, languageService.toLocale(language));
					
					OrderTotal orderTotal = module.caculateProductPiceVariation(summary, item, product, customer, store);
					if(orderTotal==null) {
						continue;
					}
					if(totals==null) {
						totals = new ArrayList<OrderTotal>();
						variation.setVariations(totals);
					}
					
					//if product is null it will be catched when invoking the module
					orderTotal.setText(product.getProductDescription().getName());
					variation.getVariations().add(orderTotal);	
				}
			}
		}
		
		
		return variation;
	}

}
