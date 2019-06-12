package main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
public class Main {

	final static int screenWidth = 1920;
	final static int screenHeight = 1080;
	final static int numOfWorlds = 1;
	static World[] worlds = new World[numOfWorlds];
	static int currentWorld = 0;
	public static GameEntity activeEntity;
	
	public static void main(String[] args) {
		try {
			Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Display.setTitle("Leveleditor");

    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, screenWidth, screenHeight, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		//
		init();
		
		while (!Display.isCloseRequested()) {
			 
		    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	

		    update();
		    render();
	 
		    Display.update();
		}
		for(int i = 0; i<numOfWorlds; i++){
			worlds[i].saveWorld(i);
		}
		Display.destroy();
	}
	
	static Camera camera;
	public static ScrollUI scrollUI;
	private static void init() {
		camera = new Camera(new Vector2f(0,0), 1);
		scrollUI = new ScrollUI(new Vector2f(0,0), new Vector2f(500,screenHeight), new Vector2f(50,50), new Color(20,20,20));

		for(int i = 0; i<numOfWorlds; i++){
			worlds[i] = new World(i);
		}
	}
	
	private static void update() {
		//System.out.println(p.isWalking);
		worlds[currentWorld].update();
		
		if(Mouse.isButtonDown(1)){
			camera.pos.x -= Mouse.getDX();
			camera.pos.y += Mouse.getDY();
		}
		int dwheel = Mouse.getDWheel();
		if(Mouse.getX() < scrollUI.size.x){
			if(dwheel>0){
				scrollUI.moveElements(10);
				//camera.pos.y -= (screenHeight/4);
				//camera.pos.y = ((screenHeight-screenHeight*camera.zoom/2)/2+camera.pos.x);//((screenWidth+camera.pos.y)/2)*0.01;
			} else if (dwheel<0){
				scrollUI.moveElements(-10);
			}
		} else {
			if(dwheel>0){
				camera.zoom += 0.01;
				//camera.pos.y -= (screenHeight/4);
				//camera.pos.y = ((screenHeight-screenHeight*camera.zoom/2)/2+camera.pos.x);//((screenWidth+camera.pos.y)/2)*0.01;
			} else if (dwheel<0){
				camera.zoom -= 0.01;
				//camera.pos.x -= ((screenWidth-screenWidth*camera.zoom/2)/2+camera.pos.x);
				//camera.pos.y -= ((screenHeight-screenHeight*camera.zoom/2)/2+camera.pos.x);
			}
		}
		
		//System.out.println(dwheel);
		//camera.zoom += Mouse.getDWheel()/1000;
	}

	private static void render() {
	//colored
	//textured
		//GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPushMatrix();
		GL11.glTranslatef(-camera.pos.x, -camera.pos.y, 0);
		GL11.glScalef(camera.zoom, camera.zoom, 1);
		
		worlds[currentWorld].render();
		
		GL11.glPopMatrix();
		//GL11.glDisable(GL11.GL_TEXTURE_2D);

		GL11.glPushMatrix();
		//render ui
		scrollUI.box.render();
		scrollUI.renderElements();
		GL11.glPopMatrix();
	}

	public static long getTime() {
		return System.nanoTime() / 1000000;
	}
}
