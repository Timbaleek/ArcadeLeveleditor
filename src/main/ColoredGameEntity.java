package main;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class ColoredGameEntity extends GameEntity {

	private Vector2f size;
	private Color c;

	public ColoredGameEntity(Vector2f pos, Vector2f size, Color c) {
		super(pos, size);
		this.size = size;
		this.c = c;
	}

	public void render() {
		GL11.glColor3f(c.getRed(), c.getGreen(), c.getBlue());
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(pos.x,pos.y);
	    GL11.glVertex2f(pos.x+size.x,pos.y);
	    GL11.glVertex2f(pos.x+size.x,pos.y+size.y);
	    GL11.glVertex2f(pos.x,pos.y+size.y);
		GL11.glEnd();
	}
}
