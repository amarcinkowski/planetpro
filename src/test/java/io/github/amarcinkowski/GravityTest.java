package io.github.amarcinkowski;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GravityTest {

	static Universe universe;

	@BeforeClass
	public static void setup() throws IOException {
		DynamicTable dynamicTable = ExcelReader.read("src/main/resources/data.xls");
		universe = UniverseSerializer.load(dynamicTable);
	}

	@Test
	public void earthFTest() {
		CelestialObject earth = universe.get(3);
		double mass = earth.mass;
		double radius_meter = earth.diameter * 1000 / 2;
		double gravit = Gravity.getFgrawitNaPowierzchni(mass, radius_meter);
		double expected = 9.8;
		Assert.assertEquals(expected, gravit, 0.01);
	}

}
