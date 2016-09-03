package io.github.amarcinkowski;

import java.util.HashMap;

public class CelestialObject {

	HashMap<String, String> name = new HashMap<>();
	Enum<CelestialObjectType> type;
	Double diameter;
	Double mass;
	Double distance;
	Double transittime;
	Double moons;
	String parent;

	public enum CelestialObjectType {
		STAR, PLANET, MOON, ASTEROID, NEBULAE, GALAXY, CLUSTER, PULSAR, QUASAR, BLACKHOLE, OTHER,
		// http://cdms.berkeley.edu/Education/DMpages/FAQ/question36.html
		DARKMATTER
	}

	private int log(double d) {
		Double logdia = Math.log10(d);
		return (int) Math.ceil(logdia.doubleValue());
	}

	@Override
	public String toString() {
		Double density = this.mass / (Math.PI * 4 / 3 * Math.pow(this.diameter, 3));
		return String.format("%11s [%3d|%3d|%3d]%23s", type, log(diameter), log(mass), log(density), name.get("pl"));
	}

}
