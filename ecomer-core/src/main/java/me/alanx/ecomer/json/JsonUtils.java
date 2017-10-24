package me.alanx.ecomer.json;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import me.alanx.ecomer.core.model.catalog.product.Product;
import me.alanx.ecomer.core.model.catalog.product.ProductAttribute;
import me.alanx.ecomer.core.model.catalog.product.ProductAttributeType;
import me.alanx.ecomer.core.model.catalog.product.option.ProductOption;
import me.alanx.ecomer.core.model.filter.FilterDefinition;
import me.alanx.ecomer.core.model.filter.FilterValueMode;
import me.alanx.ecomer.core.model.filter.FilterValueType;
import me.alanx.ecomer.core.utils.FilterUtils;

public class JsonUtils {
	
	public static void writeJson(Object obj, String file) throws Exception {
		writeJson(obj, false, file);
		
	}
	
	public static void writeJson(Object obj, boolean printToConsole, String file) throws Exception {
		ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_EMPTY)
				.configure(SerializationFeature.INDENT_OUTPUT, true);

		if (printToConsole) {
			mapper.writeValue(System.out, obj);
		}
		
		if (file != null) {
			File jsonFile = new File(file);
			jsonFile.getParentFile().mkdirs();
			jsonFile.createNewFile();
			
			mapper.writeValue(new FileOutputStream(jsonFile), obj);
		}
		
	}
	
	public static void printJson(Object obj) {
		try {
			writeJson(obj, true, null);
		} catch (Exception e) {
			throw new RuntimeException("Failed printing obj. ", e);
		}
	}
	
	public static void main2(String[] args) {
		try {
			writeJson( FilterUtils.readFilterCandidates(Product.class, new String[]{"me.alanx.ecomer.core.model"}, null, false), null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		
		FilterDefinition gfd = new FilterDefinition("brand");
		FilterDefinition pafd = new FilterDefinition("brand");
		FilterDefinition pafd2 = new FilterDefinition("brand");
		FilterDefinition pofd = new FilterDefinition("brand");
		FilterDefinition pofd2 = new FilterDefinition("brand");
		
		pafd.setType(FilterValueType.NUMBER);
		pafd.setMode(FilterValueMode.SCOPE);
		pafd.setLabel("Price");
		
		pafd2.setType(FilterValueType.STRING);
		pafd2.setMode(FilterValueMode.RADIO);
		pafd2.setOptionLabels(Arrays.asList("Shakespear", "Martin Foller", "Rod Johnson", "Others"));
		pafd2.setOptionValues(Arrays.asList("ss001", "mf001", "rj001", "o"));
		pafd2.setLabel("Author");
		
		pofd.setType(FilterValueType.NUMBER);
		pofd.setMode(FilterValueMode.SELECT);
		pofd.setOptionLabels(Arrays.asList("Member", "Non-member"));
		pofd.setOptionValues(Arrays.asList("1", "0"));
		pofd.setLabel("Membership");
		
		pofd2.setType(FilterValueType.STRING);
		pofd2.setMode(FilterValueMode.CHECKBOX);
		pofd2.setOptionLabels(Arrays.asList("Red", "Yellow", "Black", "Blue"));
		pofd2.setOptionValues(Arrays.asList("red", "yellow", "black", "blue"));
		pofd2.setLabel("Colors");
		
		gfd.setType(FilterValueType.NUMBER);
		gfd.setMode(FilterValueMode.CHECKBOX);
		gfd.setOptionLabels(Arrays.asList("Manning", "Bradbury, Agnew & Co, Ltd", "Lightning Source", "Muller Martini"));
		gfd.setOptionValues(Arrays.asList("1", "2", "3", "4"));
		gfd.setLabel("Brand");
		
		List<Object> l = new ArrayList<>();
		l.add(pofd);
		l.add(pofd2);
		l.add(pafd);
		l.add(pafd2);
		l.add(gfd);
		
		
		try {
			writeJson(l, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
