package io.github.amarcinkowski;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Model3d {

	private DirectionalLight addLight() {
		Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
		light1.setInfluencingBounds(bounds);
		return light1;
	}

	private TransformGroup addSphere(double x, double y, double z, double size) {
		TransformGroup tg = new TransformGroup();
		Transform3D t3d = new Transform3D();
		Vector3f v3f = new Vector3f((float) x, (float) y, (float) z);
		t3d.setTranslation(v3f);
		tg.setTransform(t3d);
		Sphere sphere = new Sphere((float) size);
		tg.addChild(sphere);
		return tg;
	}

	public Model3d() {
		SimpleUniverse uni = new SimpleUniverse();
		BranchGroup group = new BranchGroup();

		group.addChild(addSphere(-0.15, 0, 0, 0.1));
		group.addChild(addSphere(0.0, 0.12, 0, 0.1));
		group.addChild(addSphere(0.15, 0, 0, 0.1));
		group.addChild(addSphere(0.35, 0, 0, 0.1));

		group.addChild(addLight());
		uni.getViewingPlatform().setNominalViewingTransform();
		uni.addBranchGraph(group);
	}

	public static void main(String[] args) {
		System.out.println("x");
		new Model3d();
	}
}
