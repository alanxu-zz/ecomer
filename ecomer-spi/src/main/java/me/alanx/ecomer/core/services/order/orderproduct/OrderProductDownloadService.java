package me.alanx.ecomer.core.services.order.orderproduct;

import java.util.List;

import me.alanx.ecomer.core.model.order.product.OrderProductDownload;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityService;


public interface OrderProductDownloadService extends SalesManagerEntityService<Long, OrderProductDownload> {

	/**
	 * List {@link OrderProductDownload} by order id
	 * @param orderId
	 * @return
	 */
	List<OrderProductDownload> getByOrderId(Long orderId);

}
