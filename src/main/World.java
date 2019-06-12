package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector2f;

public class World {
	
	List<TexturedGameEntity> texturedGameEntities = new ArrayList<TexturedGameEntity>();
	List<AnimatedGameEntity> animatedGameEntities = new ArrayList<AnimatedGameEntity>();
	List<TexturedPhysicsGameEntity> texturedPhysicsEntities = new ArrayList<TexturedPhysicsGameEntity>();
	List<AnimatedPhysicsGameEntity> animatedPhysicsEntities = new ArrayList<AnimatedPhysicsGameEntity>();
	public int levelWidth;
	private static TexturedGameEntity background;
	float frictionGround = 1.005f;
	
	public World(int worldNumber){
		readWorld(worldNumber);
		
		String folderPath = "res/world" + worldNumber;
		File folder = new File(folderPath);
	    for (final File fileEntry : folder.listFiles()) {
	    	String fileName = fileEntry.getName();
	    	if(fileName.endsWith(".png")){
	    		String combinedPath = folderPath + "/" + fileName;
	            BufferedImage bimg = null;
				try {
					bimg = ImageIO.read(new File(combinedPath));
					System.out.println(bimg.getWidth());
				} catch (IOException e) {
					e.printStackTrace();
				}
	            Main.scrollUI.addElement(new TexturedGameEntity(new Vector2f(0,0), new  Vector2f(bimg.getWidth(),bimg.getHeight())
	            		, worldNumber, combinedPath.substring(folderPath.length()+1,combinedPath.length() - 4)));
	    	}
	    		
	    }
	}	
	
	public void update() {
		
		for(AnimatedPhysicsGameEntity a:animatedPhysicsEntities){
			a.updateState(Direction.RIGHT);
			a.updateActive();
		}
		for(AnimatedGameEntity a:animatedGameEntities){
			a.updateState(Direction.RIGHT);
			a.updateActive();
		}
		
		for(TexturedGameEntity t:texturedGameEntities){
			t.updateActive();
		}
		
		for(TexturedPhysicsGameEntity tp:texturedPhysicsEntities){
			tp.updateActive();
		}
	}
	
	public void render() {
		background.render();
		for(TexturedGameEntity t:texturedGameEntities){
			t.render();
		}
		for(AnimatedGameEntity a:animatedGameEntities){
			a.render();
		}
		for(TexturedPhysicsGameEntity tp:texturedPhysicsEntities){
			tp.render();
		}
		for(AnimatedPhysicsGameEntity ap:animatedPhysicsEntities){
			ap.render();
		}
	}

	public void saveWorld(int worldNumber){
		try {
			Writer wr = new FileWriter("res/world" + worldNumber + "/world.txt");
			wr.write("settings"+",");
				wr.write(String.valueOf(10000));
			wr.write("\n"+"init"+",");
//				wr.write(String.valueOf(texturedGameEntities.size())+",");
//				wr.write(String.valueOf(animatedGameEntities.size())+",");
//				wr.write(String.valueOf(texturedPhysicsEntities.size())+",");
//				wr.write(String.valueOf(animatedPhysicsEntities.size()));
			for(TexturedGameEntity t:texturedGameEntities){
				wr.write("\n"+"textured"+",");
				wr.write((int)t.pos.x+","+(int)t.pos.y+",");
				wr.write((int)t.size.x+","+(int)t.size.y+",");
				wr.write(t.worldnumber + "," + t.name);
			}
			for(AnimatedGameEntity a:animatedGameEntities){
				wr.write("\n"+"animated"+",");
				wr.write((int)a.pos.x+","+(int)a.pos.y+",");
				wr.write((int)a.size.x+","+(int)a.size.y+",");
				wr.write(a.worldnumber + "," + a.name + ",");
				wr.write(a.getState()+","+a.getStateCount()+","+a.getSecPerState());
			}
			for(TexturedPhysicsGameEntity t:texturedPhysicsEntities){
				wr.write("\n"+"texturedphysics"+",");
				wr.write((int)t.pos.x+","+(int)t.pos.y+",");
				wr.write((int)t.size.x+","+(int)t.size.y+",");
				wr.write(t.worldnumber + "," + t.name+",");
				wr.write((int)t.vel.x+","+(int)t.vel.y+",");
				wr.write((int)t.acc.x+","+(int)t.acc.y);
			}
			for(AnimatedPhysicsGameEntity a:animatedPhysicsEntities){
				wr.write("\n"+"animatedphsyics"+",");
				wr.write((int)a.pos.x+","+(int)a.pos.y+",");
				wr.write((int)a.size.x+","+(int)a.size.y+",");
				wr.write(a.worldnumber + "," + a.name + ",");
				wr.write(a.getState()+","+a.getStateCount()+","+a.getSecPerState() + ",");
				wr.write((int)a.vel.x+","+(int)a.vel.y+",");
				wr.write((int)a.acc.x+","+(int)a.acc.y+",");
				wr.write(String.valueOf((int)a.friction));
			}
			 // write to file
			wr.flush();
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void readWorld(int worldNumber) {
		Path path = Paths.get("res/world" + worldNumber + "/world.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Reading World: " + worldNumber);
		//read line by line
		while(scanner.hasNextLine()){
		    //process each line
		    String line = scanner.nextLine();
		    String[]object = line.split(",");
		    switch (object[0]) {
		    case "settings":
		    	levelWidth = Integer.parseInt(object[1]);
		    	break;
		    case "init":
		    	System.out.println("boi antichegg");
//		    	texturedGameEntities = new TexturedGameEntity[Integer.parseInt(object[1])];
//		    	animatedGameEntities = new AnimatedGameEntity[Integer.parseInt(object[2])];
//		    	texturedPhysicsEntities = new TexturedPhysicsGameEntity[Integer.parseInt(object[3])];
//		    	animatedPhysicsEntities = new AnimatedPhysicsGameEntity[Integer.parseInt(object[4])];
				background = new TexturedGameEntity(new Vector2f(0,0), new Vector2f(levelWidth, Main.screenHeight), worldNumber, "background");
		    	break;
			case "textured":
				texturedGameEntities.add(new TexturedGameEntity(new Vector2f(Integer.parseInt(object[1]),Integer.parseInt(object[2])), //pos
						new Vector2f(Integer.parseInt(object[3]), Integer.parseInt(object[4])), //size
						Integer.parseInt(object[5]), object[6])); //worldnumber, path
				break;
			case "animated":
				animatedGameEntities.add(new AnimatedGameEntity(new Vector2f(Integer.parseInt(object[1]),Integer.parseInt(object[2])),
						new Vector2f(Integer.parseInt(object[3]), Integer.parseInt(object[4])),
						Integer.parseInt(object[5]), object[6],
						Integer.parseInt(object[7]), Integer.parseInt(object[8]), Float.parseFloat(object[9]))); //animation
				break;
			case "texturedphysics":
				texturedPhysicsEntities.add(new TexturedPhysicsGameEntity(new Vector2f(Integer.parseInt(object[1]),Integer.parseInt(object[2])),
						new Vector2f(Integer.parseInt(object[3]), Integer.parseInt(object[4])),
						Integer.parseInt(object[5]), object[6],
						new Vector2f(Float.parseFloat(object[7]), Float.parseFloat(object[8])),
						new Vector2f(Float.parseFloat(object[9]), Float.parseFloat(object[10])))); //
				break;
			case "animatedphsyics":
				animatedPhysicsEntities.add(new AnimatedPhysicsGameEntity(new Vector2f(Integer.parseInt(object[1]),Integer.parseInt(object[2])),
						new Vector2f(Integer.parseInt(object[3]), Integer.parseInt(object[4])),
						Integer.parseInt(object[5]), object[6],
						Integer.parseInt(object[7]), Integer.parseInt(object[8]), Float.parseFloat(object[9]),
						new Vector2f(Float.parseFloat(object[10]), Float.parseFloat(object[11])),
						new Vector2f(Float.parseFloat(object[12]), Float.parseFloat(object[13])),
						Float.parseFloat(object[14])));
				break;
			default:
				System.out.println("Error reading file");
				break;
			}
		System.out.println(line);
		}
		scanner.close();
	}
}
