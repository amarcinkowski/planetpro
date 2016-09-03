package io.github.amarcinkowski;

import java.io.IOException;

public class Main {

	public static Universe celestialObjects;

	public static void main(String[] args) throws IOException {
		celestialObjects = (Universe) ExcelReader.read("src/main/resources/data.xls");
	}

}
