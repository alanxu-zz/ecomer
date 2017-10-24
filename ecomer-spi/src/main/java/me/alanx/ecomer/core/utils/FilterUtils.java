package me.alanx.ecomer.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import me.alanx.ecomer.core.annotation.FilterCandidate;
import me.alanx.ecomer.core.model.filter.FilterDefinition;
import me.alanx.ecomer.core.model.filter.FilterValueType;
import me.alanx.ecomer.core.model.generic.ApplicationEntity;

public class FilterUtils {

	public static Set<FilterDefinition> readFilterCandidates(Class<?> clazz, String[] scanPackages) {
		return readFilterCandidates(clazz, scanPackages, null, false);
	}
	
	public static Set<FilterDefinition> readFilterCandidates(Class<?> clazz, String[] scanPackages, String parentKey,
			boolean isInCollection) {
		Map<String, Set<FilterDefinition>> fds = new HashMap<>();
		for (Field f : clazz.getDeclaredFields()) {
			FilterCandidate[] fAnnos = f.getAnnotationsByType(FilterCandidate.class);
			if (fAnnos.length > 0) {
				String fieldName = f.getName();
				Class<?> fieldCls = f.getType();
				// org.springframework.util.ReflectionUtils.find

				for (FilterCandidate fAnno : fAnnos) {
					String rootKey = StringUtils.isBlank(parentKey) ? "" : parentKey + ".";
					String filterKey = rootKey + (StringUtils.isBlank(fAnno.key())
							? fieldName + (StringUtils.isBlank(fAnno.by()) ? "" : "." + fAnno.by()) : fAnno.key());
					
					FilterValueType filterType = fAnno.type();
					
					
					boolean isCollection = false;
					if (ClassUtils.isAssignable(Collection.class, fieldCls)) {
						ParameterizedType fieldType = (ParameterizedType) f.getGenericType();
						fieldCls = (Class<?>) ReflectionUtils.getTypeArguments(fieldType)[0];
						isCollection = true;
					} else if (ClassUtils.isAssignable(Map.class, fieldCls)) {
						ParameterizedType fieldType = (ParameterizedType) f.getGenericType();
						fieldCls = (Class<?>) ReflectionUtils.getTypeArguments(fieldType)[1];
						isCollection = true;
					}
					

					if (filterType != FilterValueType.UNDEFINED) {
						includeFilterDefinitions(fds, fieldName, new FilterDefinition(filterKey, filterType, isCollection));
					} else {
						Set<FilterDefinition> childFds = scanChildIfApplicable(fAnno, fieldCls, filterKey, scanPackages,
								isCollection);

						if (childFds == null) {

							if (Number.class.isAssignableFrom(fieldCls)) {
								filterType = FilterValueType.NUMBER;
							} else if (ApplicationEntity.class.isAssignableFrom(fieldCls)) {
								filterType = FilterValueType.NUMBER;
							} else if (Date.class.isAssignableFrom(fieldCls)
									|| LocalTime.class.isAssignableFrom(fieldCls)) {
								filterType = FilterValueType.DATE;
							} else if (CharSequence.class.isAssignableFrom(fieldCls)) {
								filterType = FilterValueType.STRING;
							} else if (ClassUtils.isAssignable(fieldCls, Boolean.class)) {
								filterType = FilterValueType.BOOLEAN;
							}

							if (filterType != FilterValueType.UNDEFINED)
								includeFilterDefinitions(fds, fieldName,
										new FilterDefinition(filterKey, filterType, isInCollection));
							
						} else {
							includeFilterDefinitions(fds, fieldName, childFds);
						}
					}

					

				}

			}
		}

		Set<FilterDefinition> results = new HashSet<>();
		for (Set<FilterDefinition> fd : fds.values()) {
			if (fd != null)
				results.addAll(fd);
		}
		return results;
	}

	/**
	 * 
	 * @param fc
	 * @param childClass
	 * @param propertyName
	 * @param parentKey
	 * @param fds
	 * @param scanPackages
	 * @return a set of {@link FilterDefinition} if the childClass is eligible
	 *         to scan, the set is empty if no filter cadidate (see
	 *         {@link FilterCandidate}) is defined. Or null if the childClass is
	 *         not eligible to be scanned.
	 * 
	 */
	private static Set<FilterDefinition> scanChildIfApplicable(FilterCandidate fc, Class<?> childClass,
			String parentKey, String[] scanPackages, boolean isCollection) {

		if (shouldScan(childClass, scanPackages)) {
			Set<FilterDefinition> subFds = readFilterCandidates(childClass, scanPackages, parentKey, isCollection);
			if (subFds == null)
				subFds = Collections.emptySet();
			return subFds;
		}
		return null;
	}

	private static void includeFilterDefinitions(Map<String, Set<FilterDefinition>> fds, String propertyName,
			Set<FilterDefinition> someFds) {
		createFDSetIfNull(fds, propertyName);
		if (!CollectionUtils.sizeIsEmpty(someFds)) {
			fds.put(propertyName, SetUtils.union(fds.get(propertyName), someFds));
		}
	}

	private static void includeFilterDefinitions(Map<String, Set<FilterDefinition>> fds, String propertyName,
			FilterDefinition fd) {
		if (fd != null) {
			createFDSetIfNull(fds, propertyName);
			Set<FilterDefinition> fieldFd = fds.get(propertyName);
			fieldFd.add(fd);
		}
	}

	private static void createFDSetIfNull(Map<String, Set<FilterDefinition>> fds, String propertyName) {
		Set<FilterDefinition> fieldFd = fds.get(propertyName);
		if (fieldFd == null) {
			fieldFd = new HashSet<FilterDefinition>();
			fds.put(propertyName, fieldFd);
		}
	}

	private static boolean shouldScan(Class<?> clazz, String[] scanPackages) {
		String clsPkgName = clazz.getCanonicalName();
		for (String pkgName : scanPackages) {
			if (clsPkgName.contains(pkgName)) {
				return true;
			}
		}
		return false;
	}

}
