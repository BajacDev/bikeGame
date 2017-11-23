package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class GameEntity implements Actor {

	private Entity entity;
	private ActorGame game;
	
	GameEntity(ActorGame game, boolean fixed, Vector position) throws NullPointerException,IllegalArgumentException {
		
		this.game = game;
		
		EntityBuilder entBuild = game.createEntityBuilder();
		entBuild.setPosition(position);
		entBuild.setFixed(fixed);
        entity = entBuild.build();
	}
	
	GameEntity(ActorGame game, boolean fixed) {
		this(game,fixed,Vector.ZERO);
	}
		
	public void destroy(){
		entity.destroy();
	}
	
	protected Entity getEntity() {
		return entity;
	}
	
	protected ActorGame getOwner(){
		return game;
	}

}
