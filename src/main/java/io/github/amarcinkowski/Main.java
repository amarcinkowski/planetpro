package io.github.amarcinkowski;

import java.io.IOException;
import java.util.Vector;

public class Main {

	public static void main(String[] args) throws IOException {
		Vector<CelestialObject> celestialObjects = new Vector<>();
		celestialObjects = ExcelReader.read("src/main/resources/data.xls");
	}

}
