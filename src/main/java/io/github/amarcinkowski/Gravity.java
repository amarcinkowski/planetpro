package io.github.amarcinkowski;

public class Gravity {

	private static double G = 0.0000000000667428676767676767676767676767676767; // m^3 / kg * s^2
	
	public static double getForce(double m1, double m2, double r) {
		return G * m1 * m2 / (r * r);
	}

	public static double getPerimeter(double r) {
		return r * 2 * Math.PI;
	}
	
	public static double getSurfaceGravity(double weight, double radius) {
		return G * weight / Math.pow(radius, 2);
	}
	
	public static double get1stCosmo(double weight, double radius) {
		return Math.sqrt(G * weight / radius);
	}
	
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