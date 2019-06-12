package main;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;

public class ScrollUI {
	
	public static ScrollUI single;
	
	public float startOfListY = 0, endOfListY = 0;
	public Vector2f pos, size, margin;
	public TexturedGameEntity[] elements = {};
	public TexturedUI box;
	
	public ScrollUI(Vector2f pos, Vector2f size, Vector2f margin, Color background){
		this.pos = pos;
		this.size = size;
		this.margin = margin;
		box = new TexturedUI(pos, size, "scrollBG");
	}
	
	public void addElement(TexturedGameEntity element){
		TexturedGameEntity[] newElements = new TexturedGameEntity[elements.length+1];
		int i = 0;
		for(TexturedGameEntity g:elements){
			newElements[i] = g;
			i++;
		}
		float sizeModifier = (this.size.x - 2*this.margin.x)/element.size.x;
		element.pos.x = this.pos.x + this.margin.x;
		element.size.x *= sizeModifier;
		element.size.y *= sizeModifier;
		element.pos.y = endOfListY + this.margin.y;
		newElements[elements.length] = element;

		elements = newElements;
		endOfListY = element.pos.y + element.size.y;
	}
	
	public void moveElements(float deltaY){
		if(elements[0].pos.y + deltaY >= margin.y){
			for (TexturedGameEntity e:elements){
				e.pos.y += deltaY;
			}
		}
	}
	
//	public void copy(GameEntity g){
//		if(g.pos.x < this.size.x){
//			for (TexturedGameEntity e:elements){
//				if(e == g){
//					GameEntity copiedGameEntity = g;
//					Main.worlds[currentworld];
//					Main.activeEntity = copiedGameEntity;
//				}
//			}
//		}
//	}
	
	public void renderElements(){
		for (TexturedGameEntity e:elements){
			e.render();
		}
	}
}
