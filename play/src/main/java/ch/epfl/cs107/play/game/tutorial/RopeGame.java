package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class RopeGame implements Game {
	
	// Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity block,ball;
    
    private ImageGraphics blockGraphics;
    private ShapeGraphics ballGraphics;
    

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
        entBuild2.setPosition(new Vector(0.3f,4.f));
        entBuild2.setFixed(false);
        ball = entBuild2.build();
        
        PartBuilder blockBuild = block.createPartBuilder();
        PartBuilder ballBuild = ball.createPartBuilder();
        
        float blockWidth = 1.f;
        float blockHeight = 1.f;
        
        Polygon blocPolygon = new Polygon(
        		new Vector(0.f ,0.f),
        		new Vector(blockWidth ,0.f),
        		new Vector(blockWidth ,blockHeight),
        		new Vector(0.f ,blockHeight));
        
        Circle circle = new Circle(0.6f);
        
        blockBuild.setShape(blocPolygon);
        ballBuild.setShape(circle);
        
        blockBuild.build();
        ballBuild.build();
        
        blockGraphics = new ImageGraphics("stone.broken.4.png",1,1);
        blockGraphics.setParent(block);
        
        ballGraphics = new ShapeGraphics(circle,Color.BLUE, Color.RED, 0.1f,1.f,0);
        ballGraphics.setParent(ball);
        
        RopeConstraintBuilder ropeConstraintBuilder = world.createRopeConstraintBuilder();
        ropeConstraintBuilder.setFirstEntity(block);
        ropeConstraintBuilder.setFirstAnchor(new Vector(blockWidth/2, blockHeight/2));
        ropeConstraintBuilder.setSecondEntity(ball);
        ropeConstraintBuilder.setSecondAnchor(Vector.ZERO);
        ropeConstraintBuilder.setMaxLength(3.0f);
        ropeConstraintBuilder.setInternalCollision(true);
        ropeConstraintBuilder.build();

        
        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {
        
    	
    	world.update(deltaTime);
    	
    	ballGraphics.draw(window);
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
