package io.github.amarcinkowski;

public class Gravity {

	private static double G = 0.0000000000667428676767676767676767676767676767; // m^3 / kg * s^2
	
	/*public static double getSpeed(CelestialObject object) {
		double v = Math.sqrt(G * Main.celestialObjects.get(1).getClass() / object.getOdlegloscOdSlonca());
		return v;
	}
	*/
/*	public static double getObw(CelestialObject object) {
		return object.getOdlegloscOdSlonca() * 2 * Math.PI;
	}
	
	public static double getFgrawitNaPowierzchni(CelestialObject object) {
		return G * object.getWeight() / Math.pow(object.getPromien(), 2);
	}
	
	public static double get1stCosmo(CelestialObject object) {
		return Math.sqrt(G * object.getWeight() / object.getPromien());
	}*/
	
//	public static void main(String[] args) {
//		print("Ziemia");
//	}
	
/*	public static void print(String name) {
		SpaceObject object = SolarSystemBodies.get(name);
		System.out.println(name);
		double speed = getSpeed(object);
		double obw = getObw(object);
		double time = obw / speed;
		System.out.println("M=" + object.getWeight() + " kg");
		System.out.println("V=" + speed + " m/s");
		System.out.println("obwód orbity=" + obw + " m");
		System.out.println("Tobiegu_wokol_slonca=" + time/60/60/24 + " dni");
		System.out.println("Fgrawitacyjna=" + getFgrawitNaPowierzchni(object) + " N/kg");
		System.out.println("V1sza_kosmiczna=" + get1stCosmo(object) + " m/s");
	}*/
}