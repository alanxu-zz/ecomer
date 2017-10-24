package me.alanx.ecomer.core.services.catalog.product.manufacturer;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import me.alanx.ecomer.core.exception.ServiceException;
import me.alanx.ecomer.core.model.catalog.product.Manufacturer;
import me.alanx.ecomer.core.model.catalog.product.ManufacturerDescription;
import me.alanx.ecomer.core.model.merchant.MerchantStore;
import me.alanx.ecomer.core.model.reference.Language;
import me.alanx.ecomer.core.repositories.catalog.product.manufacturer.ManufacturerRepository;
import me.alanx.ecomer.core.services.common.generic.SalesManagerEntityServiceImpl;



@Service("manufacturerService")
public class ManufacturerServiceImpl extends
		SalesManagerEntityServiceImpl<Long, Manufacturer> implements ManufacturerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManufacturerServiceImpl.class);

	private ManufacturerRepository manufacturerRepository;
	
	@Inject
	public ManufacturerServiceImpl(
		ManufacturerRepository manufacturerRepository) {
		super(manufacturerRepository);
		this.manufacturerRepository = manufacturerRepository;		
	}
	
	@Override 
	public void delete(Manufacturer manufacturer) throws ServiceException{
		manufacturer =  this.getById(manufacturer.getId() );
		super.delete( manufacturer );
	}
	
	@Override
	public Long getCountManufAttachedProducts( Manufacturer manufacturer ) throws ServiceException {
		return manufacturerRepository.countByProduct(manufacturer.getId());
				//.getCountManufAttachedProducts( manufacturer );
	}
	
	
	@Override
	public List<Manufacturer> listByStore(MerchantStore store, Language language) throws ServiceException {
		return manufacturerRepository.findByStoreAndLanguage(store.getId(), language.getId());
	}
	
	@Override
	public List<Manufacturer> listByStore(MerchantStore store) throws ServiceException {
		return manufacturerRepository.findByStore(store.getId());
	}
	
	@Override
	public List<Manufacturer> listByProductsByCategoriesId(MerchantStore store, List<Long> ids, Language language) throws ServiceException {
		return manufacturerRepository.findByCategoriesAndLanguage(ids, language.getId());
	}

	@Override
	public void addManufacturerDescription(Manufacturer manufacturer, ManufacturerDescription description)
			throws ServiceException {
		
		
		if(manufacturer.getDescriptions()==null) {
			manufacturer.setDescriptions(new HashSet<ManufacturerDescription>());
		}
		
		manufacturer.getDescriptions().add(description);
		description.setManufacturer(manufacturer);
		update(manufacturer);
	}
	
	@Override	
	public void saveOrUpdate(Manufacturer manufacturer) throws ServiceException {

		LOGGER.debug("Creating Manufacturer");
		
		if(manufacturer.getId()!=null && manufacturer.getId()>0) {
		   super.update(manufacturer);  
			
		} else {						
		   super.create(manufacturer);

		}
	}

	@Override
	public Manufacturer getByCode(me.alanx.ecomer.core.model.merchant.MerchantStore store, String code) {
		return manufacturerRepository.findByCodeAndMerchandStore(code, store.getId());
	}
}
