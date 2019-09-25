package entities;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public abstract class GameEntity {
	
	public Vector2f pos;
	public ArrayList<Vector2f> shape;
	
	public GameEntity(Vector2f pos, ArrayList<Vector2f> shape){
		this.pos = pos;
		for(Vector2f p : shape){
			p.x += pos.x;
			p.y += pos.y;
		}
		this.shape = shape;
	}
	
	public void render(){
		GL11.glColor3f(255, 0, 100);
		GL11.glBegin(GL11.GL_POLYGON);
		for(Vector2f point:shape){
			GL11.glVertex2f(point.getX(),point.getY());
		}
	    GL11.glEnd();
	}

	public void move(Vector2f move){
		pos.x += move.x;
		pos.y += move.y;
		for(Vector2f p:shape){
			p.x += move.x;
			p.y += move.y;
		}
	}
}
