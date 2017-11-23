package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public abstract class ActorGame implements Game{
	
	private ArrayList<Actor> actor;
	private World world;
	private FileSystem fileSystem;
	private Window window;
	
	// Viewport properties
	private Vector viewCenter ;
	private Vector viewTarget ;
	private Positionable viewCandidate ;
	
	private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f ;
	private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f ;
	private static final float VIEW_SCALE = 10.0f ;
	
	private void updateCamera(float deltaTime) {
		// Update expected viewport center 
		if (viewCandidate != null) { 
			viewTarget = viewCandidate.getPosition().
					add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION)); 
		}
		// Interpolate with previous location 
		float ratio = (float)Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime);
		viewCenter = viewCenter.mixed(viewTarget, ratio);
		
		// Compute new viewport 
		Transform viewTransform = Transform.I.scaled(VIEW_SCALE).translated(viewCenter);
		window.setRelativeTransform(viewTransform);

	}
	
	public void setViewCandidate(Positionable newViewCandidate) {
		viewCandidate = newViewCandidate;
	}
	
	public Keyboard getKeyboard(){
		return window.getKeyboard() ;
	}
	
	public Canvas getCanvas(){
		return window ;
	}
	
	public EntityBuilder createEntityBuilder() {
		return world.createEntityBuilder();
	}
	
	public boolean begin(Window window, FileSystem fileSystem){

		this.window = window;
        world = new World();
        
		viewCenter = Vector.ZERO;
		viewTarget = Vector.ZERO;
        
		return true;
	}
	
	public void update (float deltaTime) {
		world.update(deltaTime);
		
		for(Actor currActor : actor)
			currActor.update(deltaTime);
		
		updateCamera(deltaTime);
		
		for(Actor currActor : actor)
			currActor.draw(window);
	}
	
	public void end() {
		
	}


	
}
