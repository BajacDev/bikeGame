package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class SimpleCrateGame implements Game {
	
	// Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity block,crate;
    
    private ImageGraphics blockGraphics,crateGraphics;
    

    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        // Store context
        this.window = window;
        
        world = new World();
        world.setGravity(new Vector(0.0f,-9.81f));
        
        EntityBuilder entBuild = world.createEntityBuilder();
        entBuild.setPosition(new Vector(1.0f,0.5f));
        entBuild.setFixed(true);
        block = entBuild.build();
        
        EntityBuilder entBuild2 = world.createEntityBuilder();
        entBuild2.setPosition(new Vector(0.2f,4.0f));
        entBuild2.setFixed(false);
        crate = entBuild2.build();
        
        PartBuilder blockBuild = block.createPartBuilder();
        PartBuilder crateBuild = crate.createPartBuilder();
        
        Polygon poly = new Polygon(
        		new Vector(0.f ,0.f),
        		new Vector(1.f ,0.f),
        		new Vector(1.f ,1.f),
        		new Vector(0.f ,1.f));
        
        blockBuild.setShape(poly);
        crateBuild.setShape(poly);
        
        blockBuild.build();
        crateBuild.build();
        
        blockGraphics = new ImageGraphics("stone.broken.4.png",1,1);
        blockGraphics.setParent(block);
        
        crateGraphics = new ImageGraphics("box.4.png",1,1);
        crateGraphics.setParent(crate);
        
        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {
        
    	
    	world.update(deltaTime);
    	
    	crateGraphics.draw(window);
    	blockGraphics.draw(window);
    	
    	window.setRelativeTransform(Transform.I.scaled(10.f));
        // The actual rendering will be done now, by the program loop
    	
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
}
