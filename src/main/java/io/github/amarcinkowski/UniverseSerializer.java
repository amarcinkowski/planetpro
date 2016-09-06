package io.github.amarcinkowski;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class UniverseSerializer {

	final static Logger logger = LoggerFactory.getLogger(UniverseSerializer.class);

	public static Universe load(DynamicTable dynamicTable) {

		int size = dynamicTable.getTable().size() / dynamicTable.getHeaders().size();

		Universe universe = new Universe(size, "NAME.PL");

		for (String key : dynamicTable.getTable().keySet()) {
			int objectIndex = new Integer(key.split("\\.")[0]);
			int fieldIndex = new Integer(key.split("\\.")[1]);

			String fieldName = dynamicTable.getHeaders().elementAt(fieldIndex).toLowerCase();
			Object fieldValue = dynamicTable.getTable().get(key);
			logger.trace(String.format("%d/%d %s %s", objectIndex, fieldIndex, fieldName, fieldValue));
			universe.setField(objectIndex, fieldName, fieldValue);
		}

		return universe;
	}

	public static void save() {
	}

}