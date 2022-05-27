package org.services;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.models.annotations.Init;
import org.models.annotations.JSONElement;
import org.models.annotations.JSONSerializable;

public class Processing {

	private void checkIfSerializable(Object object) throws Exception {
		if (Objects.isNull(object)) {
			throw new Exception("The object to serialize can not be null");
		}

		Class<?> clazz = object.getClass();
		if (!clazz.isAnnotationPresent(JSONSerializable.class)) {
			throw new Exception("The class " + clazz.getSimpleName() + " is not annotated with JsonSerializable");
		}
	}

	private void initializeObject(Object object) throws Exception {
		Class<?> clazz = object.getClass();
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(Init.class)) {
				method.setAccessible(true);
				method.invoke(object);
			}
		}
	}

	private String getJsonString(Object object) throws Exception {
		Class<?> clazz = object.getClass();
		
		Map<String, String> jsonElementsMap = new HashMap<>();
		
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(JSONElement.class)) {
				jsonElementsMap.put(field.getName(), String.valueOf(field.get(object)));
			}
		}

		String jsonString = jsonElementsMap
				.entrySet()
				.stream()
				.map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
				.collect(Collectors.joining(","));
		
		return "{" + jsonString + "}";
	}

	public String convertToJson(Object object) throws Exception {
		try {
			checkIfSerializable(object);
			initializeObject(object);
			return getJsonString(object);
		} catch (Exception e) {
			throw e;
		}
	}

}
