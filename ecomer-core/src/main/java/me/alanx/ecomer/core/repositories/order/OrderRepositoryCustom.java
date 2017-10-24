package me.alanx.ecomer.core.repositories.order;

import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.order.OrderCriteria;
import me.alanx.ecomer.core.model.order.OrderList;




public interface OrderRepositoryCustom {

	OrderList listByStore(MerchantStore store, OrderCriteria criteria);


}
