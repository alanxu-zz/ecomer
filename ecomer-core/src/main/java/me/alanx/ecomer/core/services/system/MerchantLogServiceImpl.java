package me.alanx.ecomer.core.services.system;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.model.system.MerchantLog;
import me.alanx.ecomer.core.repositories.system.MerchantLogRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;

@Service("merchantLogService")
public class MerchantLogServiceImpl extends
		SalesManagerEntityServiceImpl<Long, MerchantLog> implements
		MerchantLogService {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantLogServiceImpl.class);


	
	private MerchantLogRepository merchantLogRepository;
	
	@Inject
	public MerchantLogServiceImpl(
			MerchantLogRepository merchantLogRepository) {
			super(merchantLogRepository);
			this.merchantLogRepository = merchantLogRepository;
	}


	




}
