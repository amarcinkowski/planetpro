package io.github.amarcinkowski;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Universe extends Vector<CelestialObject> {

	final static Logger logger = LoggerFactory.getLogger(Universe.class);
	private final String nameField;

	public Universe(int size, String nameField) {
		this.nameField = nameField;
		for (int i = 0; i < size; i++) {
			CelestialObject celestialObject = new CelestialObject();
			add(celestialObject);
		}
		logger.trace("Initialize Universe with size: " + size);
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, String> getOrCreate(CelestialObject co, String fieldName) {
		try {
			HashMap<String, String> map = (HashMap<String, String>) co.getClass().getDeclaredField(fieldName).get(co);
			return map;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return new HashMap<String, String>();
	}

	public void setField(int objectIndex, String fieldName, Object value) {
		CelestialObject co;

		try {
			co = this.get(objectIndex);
			logger.trace("Getting celestial object");
		} catch (IndexOutOfBoundsException e) {
			co = new CelestialObject();
			logger.trace("Create new celestial object");
		}

		Field field = getField(fieldName, co, value);

	}

	private Field getField(String fieldName, CelestialObject co, Object value) {
		Field field;
		try {
			String subFieldName = (fieldName.contains(".") ? fieldName.substring(fieldName.indexOf(".") + 1) : "");
			fieldName = (fieldName.contains(".") ? fieldName.substring(0, fieldName.indexOf(".")) : fieldName);

			field = co.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);

			setValue(fieldName, subFieldName, co, field, value);
			logger.trace(String.format("/T:%s%10s/V:%s/", field.getName(), subFieldName, value));
			return field;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
				| ClassNotFoundException | ClassCastException e) {
			String err = String.format("%s %s %s", e.getMessage(), fieldName, value);
			logger.error(err);
		}
		return null;
	}

	private void setValue(String fieldName, String subFieldName, CelestialObject co, Field field, Object value)
			throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
		String fieldType = field.getType().getSimpleName();

		switch (fieldType) {
		case "String":
			if (fieldName.equals(this.nameField)) {
				
			}
			field.set(co, value);
			break;
		case "Double":
		case "Integer":
			field.set(co, value);
			break;
		case "Enum":
			String enumName = field.getGenericType().getTypeName();
			enumName = enumName.substring(enumName.indexOf("<") + 1, enumName.length() - 1);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			Object enumvalue = Enum.valueOf((Class<Enum>) Class.forName(enumName), (String) value);
			field.set(co, enumvalue);
			break;
		case "HashMap":
			HashMap<String, String> map = getOrCreate(co, fieldName);
			map.put(subFieldName, (String) value);
			field.set(co, map);
			break;
		default:
			logger.error("MISSING TYPE:" + fieldType + " unknown");
		}
	}

}
