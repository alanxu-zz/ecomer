package me.alanx.ecomer.core.bind;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

public class StringCollectionFomatter implements Formatter<Collection<String>>{
	
	@Override
	public String print(Collection<String> object, Locale locale) {
		boolean first = true;
		StringBuffer sb = new StringBuffer();
		for(String s : object) {
			sb.append(first ? "" : ",").append(s);
			if(first) first = false;
		}
		
		return sb.toString();
	}

	@Override
	public Collection<String> parse(String text, Locale locale) throws ParseException {
		List<String> l = new ArrayList<>();
		for (String s : StringUtils.split(text, ",")) {
			if (s != null) l.add(s.trim());
		}
		return l;
	}
	
}
