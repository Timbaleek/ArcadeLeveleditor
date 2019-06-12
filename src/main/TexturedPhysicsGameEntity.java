package main;

import org.lwjgl.util.vector.Vector2f;

public class TexturedPhysicsGameEntity extends TexturedGameEntity {

	Vector2f vel, acc;

	public TexturedPhysicsGameEntity(Vector2f pos, Vector2f size, int worldnumber, String name, Vector2f vel, Vector2f acc) {
		super(pos, size, worldnumber, name);
		this.vel = vel;
		this.acc = acc;
		Main.worlds[worldnumber].texturedPhysicsEntities.add(this);
	}

	public void updateMovement() {
		Vector2f.add(pos, vel, pos);
		vel.scale(0.98f);
		Vector2f.add(vel, acc, vel);
	}
}
