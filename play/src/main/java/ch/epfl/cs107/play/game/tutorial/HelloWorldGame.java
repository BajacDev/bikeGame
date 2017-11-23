package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

/**
 * Simple game, to show basic the basic architecture
 */
public class HelloWorldGame implements Game {

    // Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity body;
    
    private ImageGraphics graphics,graphics2;
    

    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        // Store context
        this.window = window;
        world = new World();
        world.setGravity(new Vector(0.0f,-9.81f));
        
        // we construct the entity
        EntityBuilder entityBuilder = world.createEntityBuilder();
        entityBuilder.setFixed(true);
        entityBuilder.setPosition(new Vector(1.f,1.5f));
        body = entityBuilder.build();
        
        
        // the entity will have 2 graphic representation
        graphics = new ImageGraphics("stone.broken.4.png",1,1);
        graphics2 = new ImageGraphics("bow.png",2,2);
        graphics2.setDepth(-0.1f);
        graphics.setParent(body);
        graphics2.setParent(body);
        
       // TO BE COMPLETED
        // Successfully initiated
        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {
        
    	world.update(deltaTime);
    	
    	graphics.draw(window);
    	graphics2.draw(window);
    	
    	window.setRelativeTransform(Transform.I.scaled(10.0f)) ;
        // The actual rendering will be done now, by the program loop
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
    
}
