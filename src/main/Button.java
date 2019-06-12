package main;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class Button extends GameEntity{
	
	boolean active;

	public Button(Vector2f pos, Vector2f size) {
		super(pos, size);
		// TODO Auto-generated constructor stub
	}
	
	public boolean pressed(){
		if((Mouse.getX() > pos.x && Mouse.getX() < pos.x + size.x)&&(Mouse.getY() > pos.y && Mouse.getY() < pos.y + size.y)){
			this.active = true;
			return true;
		}
		this.active = false;
		return false;
	}
	
	public Vector2f draged(){
		
		if((Mouse.getX() > pos.x && Mouse.getX() < pos.x + size.x)&&(Mouse.getY() > pos.y && Mouse.getY() < pos.y + size.y)){
			this.active = true;
			if(Mouse.isButtonDown(0)){
				return new Vector2f(Mouse.getDX(),Mouse.getDY());
			}
		}
		return new Vector2f(0,0);
	}
}
