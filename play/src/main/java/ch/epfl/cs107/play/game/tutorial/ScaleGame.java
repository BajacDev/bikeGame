package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;

import com.sun.glass.events.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ScaleGame implements Game {
	
	// Store context
    private Window window;
    
    // We need our physics engine
    private World world;
    
    // And we need to keep references on our game objects
    private Entity block,ball,plank;
    
    private ImageGraphics blockGraphics,plankGraphics,ballGraphics;
    

    // This event is raised when game has just started
    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
    	
        this.window = window;
        
        world = new World();
        world.setGravity(new Vector(0.0f,-9.81f));
        
        // we build the entities
        EntityBuilder blockEntBuild = world.createEntityBuilder();
        blockEntBuild.setPosition(new Vector(-5.f,-1.f));
        blockEntBuild.setFixed(true);
        block = blockEntBuild.build();
        
        EntityBuilder ballEntBuild = world.createEntityBuilder();
        ballEntBuild.setPosition(new Vector(0.5f,4.0f));
        ballEntBuild.setFixed(false);
        ball = ballEntBuild.build();
        
        EntityBuilder plankEntBuild = world.createEntityBuilder();
        plankEntBuild.setPosition(new Vector(-2.5f,0.8f));
        plankEntBuild.setFixed(false);
        plank = plankEntBuild.build();
        
        // we prepare shapes
        
        float blockWidth = 10.f;
        float blockHeight = 1.f;
        float plankWidth = 5.f;
        float plankHeight = .2f;
        float ballRadius = 0.5f;
        		
        Polygon blockPolygon = new Polygon(
        		new Vector(0.f ,0.f),
        		new Vector(blockWidth ,0.f),
        		new Vector(blockWidth ,blockHeight),
        		new Vector(0.f ,blockHeight));
        
        Polygon plankPolygon = new Polygon(
        		new Vector(0.f ,0.f),
        		new Vector(plankWidth ,0.f),
        		new Vector(plankWidth ,plankHeight),
        		new Vector(0.f ,plankHeight));
        
        Circle circle = new Circle(ballRadius);
        
        // we build the parts
        PartBuilder blockPartBuild = block.createPartBuilder();
        blockPartBuild.setShape(blockPolygon);
        blockPartBuild.build();

        PartBuilder ballPartBuild = ball.createPartBuilder();
        ballPartBuild.setShape(circle);
        ballPartBuild.build();
        
        PartBuilder plankPartBuild = plank.createPartBuilder();
        plankPartBuild.setShape(plankPolygon);
        plankPartBuild.build();
        
        
        // we set graphics
        blockGraphics = new ImageGraphics("stone.broken.4.png",blockWidth,blockHeight);
        blockGraphics.setParent(block);
        
        ballGraphics = new ImageGraphics("explosive.11.png",2*ballRadius,2*ballRadius,new Vector(ballRadius,ballRadius));
        ballGraphics.setParent(ball);
        
        plankGraphics = new ImageGraphics("wood.3.png",plankWidth,plankHeight);
        plankGraphics.setParent(plank);

        
        
        // setting of constraint
        RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder();
        revoluteConstraintBuilder.setFirstEntity(block);
        revoluteConstraintBuilder.setFirstAnchor(new Vector(blockWidth/2, (blockHeight*7)/4));
        revoluteConstraintBuilder.setSecondEntity(plank);
        revoluteConstraintBuilder.setSecondAnchor(new Vector(plankWidth/2, plankHeight/2));
        revoluteConstraintBuilder.setInternalCollision(true);
        revoluteConstraintBuilder.build();
        
        return true;
    }

    // This event is called at each frame
    @Override
    public void update(float deltaTime) {

        // The actual rendering will be done now, by the program loop
    	
    	if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
    		ball.applyAngularForce(10.0f); 
    	}
    	else if (window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
    		ball.applyAngularForce(-10.0f); 
    	}
    	if (window.getMouse().getLeftButton().isPressed() ){
    		ball.setPosition(window.getMouse().getPosition());
    	}
    	
    	world.update(deltaTime);
    	
    	ballGraphics.draw(window);
    	blockGraphics.draw(window);
    	plankGraphics.draw(window);
    	
    	window.setRelativeTransform(Transform.I.scaled(10.f));
    	
    }

    // This event is raised after game ends, to release additional resources
    @Override
    public void end() {
        // Empty on purpose, no cleanup required yet
    }
}
