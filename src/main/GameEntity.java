package main;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class GameEntity {
	
	public Vector2f pos, size;
	
	public GameEntity(Vector2f pos, Vector2f size){
		this.pos = pos;
		this.size = size;
	}
	
	Vector2f delta = new Vector2f(0,0);
	boolean first = false;
	public void updateActive(){
		if(Mouse.isButtonDown(0)){
			Vector2f absMouse = Camera.single.getAbsoluteMouse();
			if((absMouse.x > pos.x && absMouse.x < pos.x + size.x)&&(absMouse.y > pos.y && absMouse.y < pos.y + size.y)){
				if(first){
					delta = new Vector2f(pos.x-absMouse.x,pos.y-absMouse.y);
					first = false;
					if(Main.activeEntity == null){
						Main.activeEntity = this;
						//ScrollUI.single.copy(this);
					}
				}
			}
			if(Main.activeEntity == this && this != null && !first){
				pos.x = absMouse.x + delta.x;
				pos.y = absMouse.y + delta.y;
			}
		} else {
			first = true;
			Main.activeEntity = null;
		}
		
	}
	
//	if(this.pos.x < ScrollUI.single.size.x){
//		GameEntity g = this;
//		Main.activeEntity = g;
//		
//	}
	
//	public void copy(GameEntity g){
//		if(TexturedGameEntity.class.isInstance(g)){
//			Main.worlds[Main.currentWorld].texturedGameEntities.add(g);
//		}
//	}
	
	public void render(){
		GL11.glBegin(GL11.GL_QUADS);
	    GL11.glVertex2f(pos.x,pos.y);
	    GL11.glVertex2f(pos.x,pos.y+size.y);
	    GL11.glVertex2f(pos.x+size.x,pos.y+size.y);
	    GL11.glVertex2f(pos.x+size.x,pos.y);
	    GL11.glEnd();
	}

	public Vector2f getPos() {
		return pos;
	}

	public Vector2f getSize() {
		return size;
	}

	public void setPos(Vector2f dest) {
		pos = dest;
	}

}
