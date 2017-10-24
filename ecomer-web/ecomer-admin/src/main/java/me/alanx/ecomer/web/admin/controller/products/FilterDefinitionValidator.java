package me.alanx.ecomer.web.admin.controller.products;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import me.alanx.ecomer.core.model.filter.FilterDefinition;
import me.alanx.ecomer.core.model.filter.FilterValueMode;

public class FilterDefinitionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ClassUtils.isAssignable(clazz, FilterDefinition.class);
	}

	@Override
	public void validate(Object target, Errors e) {
		FilterDefinition fd = (FilterDefinition) target;
		
		Collection<String> ols = fd.getOptionLabels();
		Collection<String> ovs = fd.getOptionValues();
		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, errorCode);
		
		if (fd.getMode() != FilterValueMode.SCOPE && fd.getMode() != FilterValueMode.UNDEFINED) {
			if (CollectionUtils.isEmpty(ols)) {
				e.rejectValue("optionLabels", "validation.filter-definition.options.label.empty", "Option labels are empty! ");
			}
			if (CollectionUtils.isEmpty(ovs)) {
				e.rejectValue("optionValues", "validation.filter-definition.options.value.empty", "Option values are empty! ");
			}
			if (e.hasErrors()) {
				return;
			}
			if (ols.size() != ovs.size()) {
				e.reject("validation.filter-definition.options.length-not-match", "Options labels and vavlues are not of same length. ");
			}
		} else {
			if (!CollectionUtils.isEmpty(ols)) {
				e.rejectValue("optionLabels", "validation.filter-definition.options.label.not-empty", "Option labels are not empty! ");
			}
			if (!CollectionUtils.isEmpty(ovs)) {
				e.rejectValue("optionValues", "validation.filter-definition.options.value.not-empty", "Option values are not empty! ");
			}
		}
		
	}

}
