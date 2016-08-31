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
		STAR, PLANET, MOON, ASTEROID, NEBULAE, GALAXY, CLUSTER, PULSAR, QUASAR, BLACKHOLE, DARKMATTER
	}

	public int size() {
		Double logdia = Math.log10(diameter);
		return (int) Math.ceil(logdia.doubleValue());
	}
	
	public int mass() {
		Double logdia = Math.log10(mass);
		return (int) Math.ceil(logdia.doubleValue());
	}

	@Override
	public String toString() {
		return String.format("%11s [%3d|%3d]%73s", type, size(), mass(), name);
	}

}
