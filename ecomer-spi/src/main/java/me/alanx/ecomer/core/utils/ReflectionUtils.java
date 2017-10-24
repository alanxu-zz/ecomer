package me.alanx.ecomer.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionUtils {
	public static Type[] getTypeArguments(Type type) {
		if (type instanceof ParameterizedType) {
			return ((ParameterizedType)type).getActualTypeArguments();
		}
		throw new IllegalArgumentException("The type is not a ParameterizedType! ");
	}
}
