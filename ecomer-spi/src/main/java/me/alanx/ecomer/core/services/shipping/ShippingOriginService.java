package me.alanx.ecomer.core.services.shipping;

import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.shipping.ShippingOrigin;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;

/**
 * ShippingOrigin object if different from MerchantStore address.
 * Can be managed through this service.
 * @author carlsamson
 *
 */
public interface ShippingOriginService  extends SalesManagerEntityService<Long, ShippingOrigin> {

	ShippingOrigin getByStore(MerchantStore store);



}
