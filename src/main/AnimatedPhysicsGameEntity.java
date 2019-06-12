package main;

import org.lwjgl.util.vector.Vector2f;

public class AnimatedPhysicsGameEntity extends AnimatedGameEntity{

	Vector2f vel, acc;
	int worldnumber;
	float friction;
	
	public AnimatedPhysicsGameEntity(Vector2f pos, Vector2f size, int worldnumber, String name, int state, int stateCount,
			float secPerState, Vector2f vel, Vector2f acc, float friction) {
		super(pos, size, worldnumber, name, state, stateCount, secPerState);
		this.vel = vel;
		this.acc = acc;
		this.friction = friction;
		//Main.worlds[worldnumber].animatedPhysicsEntities.add(this);
	}

	public void updateMovement(){
		Vector2f.add(vel,acc,vel);
		Vector2f.add(pos,vel,pos);
	}

}
