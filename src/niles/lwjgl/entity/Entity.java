package niles.lwjgl.entity;

public class Entity {
	
	private Geometry geometry;
	private Transform transform;
	
	public Entity(int geometrySize) {
		geometry = new Geometry(geometrySize);
		transform = new Transform();
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}
	
	

}
