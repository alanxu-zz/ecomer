package me.alanx.ecomer.web.dto.admin.web;

import java.util.List;

import me.alanx.ecomer.core.model.system.MerchantConfiguration;



public class ConfigListWrapper
{
	private List<MerchantConfiguration> merchantConfigs;

	public List<MerchantConfiguration> getMerchantConfigs()
	{
		return merchantConfigs;
	}

	public void setMerchantConfigs(List<MerchantConfiguration> merchantConfigs)
	{
		this.merchantConfigs = merchantConfigs;
	}

}
