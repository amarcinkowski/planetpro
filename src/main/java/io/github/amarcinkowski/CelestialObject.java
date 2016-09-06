package io.github.amarcinkowski;

import java.util.HashMap;

public class CelestialObject implements Comparable<CelestialObject> {

	HashMap<String, String> name = new HashMap<>();
	String mainName = "";
	Enum<CelestialObjectType> type;
	Double diameter = 0.0;
	Double mass = 0.0;
	Double distance = 0.0;
	Double transittime = 0.0;
	Double moons = 0.0;
	String parent = "";
	String notes = "";

	public enum CelestialObjectType {
		// simple
		STAR, PLANET, MOON, ASTEROID, PULSAR, BLACKHOLE,
		// complex
		NEBULAE, GALAXY, CLUSTER, QUASAR,
		// http://cdms.berkeley.edu/Education/DMpages/FAQ/question36.html
		DARKMATTER,
		// other
		OTHER
	}

	private int log(double d) {
		Double logdia = Math.log10(d);
		return (int) Math.ceil(logdia.doubleValue());
	}

	@Override
	public String toString() {
		Double density = this.mass / (Math.PI * 4 / 3 * Math.pow(this.diameter, 3));
		String.format("%#5.2f", new Double(1.75 * Math.pow(10, 12) / density));
		return String.format("\n%11s [%3d|%3d|%3d]%21s", type, log(diameter), log(mass), log(density), name.get("pl"));
	}

	@Override
	public int compareTo(CelestialObject o) {
		return this.mainName.compareTo(o.mainName);
	}

}
