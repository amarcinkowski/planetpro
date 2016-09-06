package io.github.amarcinkowski;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	final static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException {
		DynamicTable dynamicTable = ExcelReader.read("src/main/resources/data.xls");
		logger.trace(dynamicTable.toString());
		Universe universe = UniverseSerializer.load(dynamicTable);
		logger.info(universe.toString());
	}

}
