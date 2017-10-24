package me.alanx.ecomer.core.repositories.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.system.MerchantLog;

public interface MerchantLogRepository extends JpaRepository<MerchantLog, Long> {

	public List<MerchantLog> findByStore(MerchantStore store);
}
