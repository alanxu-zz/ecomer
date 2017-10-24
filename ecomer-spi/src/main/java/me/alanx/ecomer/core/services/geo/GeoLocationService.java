package me.alanx.ecomer.core.services.geo;

import me.alanx.ecomer.core.model.common.Address;

public interface GeoLocationService {
	
	Address getAddress(String ipAddress) throws Exception;

}
