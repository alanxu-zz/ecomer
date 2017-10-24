package me.alanx.ecomer.core.services.shipping;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.shipping.ShippingOrigin;
import me.alanx.ecomer.core.repositories.shipping.ShippingOriginRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;



@Service("shippingOriginService")
public class ShippingOriginServiceImpl extends SalesManagerEntityServiceImpl<Long, ShippingOrigin> implements ShippingOriginService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShippingOriginServiceImpl.class);
	
	private ShippingOriginRepository shippingOriginRepository;

	

	@Inject
	public ShippingOriginServiceImpl(ShippingOriginRepository shippingOriginRepository) {
		super(shippingOriginRepository);
		this.shippingOriginRepository = shippingOriginRepository;
	}


	@Override
	public ShippingOrigin getByStore(MerchantStore store) {
		// TODO Auto-generated method stub
		ShippingOrigin origin = shippingOriginRepository.findByStore(store.getId());
		return origin;
	}
	

}
