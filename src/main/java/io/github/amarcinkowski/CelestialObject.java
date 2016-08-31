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

	public enum CelestialObjectType {
		STAR, PLANET, MOON, ASTEROID, NEBULAE, GALAXY, CLUSTER, PULSAR, QUASAR, BLACKHOLE, DARKMATTER
	}

	public String size() {
		return "[" + (int) Math.ceil(Math.log10(diameter)) + "]";
	}

	@Override
	public String toString() {
		return String.format("%7s%4s%53s", type, size(), name);
	}

}
