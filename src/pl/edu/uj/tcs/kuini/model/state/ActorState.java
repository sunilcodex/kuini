package pl.edu.uj.tcs.kuini.model.state;

import java.io.Serializable;

import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.IActor;
import pl.edu.uj.tcs.kuini.model.Position;

public class ActorState implements IActor, Serializable {
	private static final long serialVersionUID = -1927504484577547043L;
	private final Position position;
	private float radius;
	private final ActorType actorType;
	private final long id;
	private final float angle;
	private final int playerId;
	
	public ActorState(IActor actor){
		position = actor.getPosition();
		radius = actor.getRadius();
		actorType = actor.getActorType();
		id = actor.getId();
		angle = actor.getAngle();
		playerId = actor.getPlayerId();
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public float getRadius() {
		return radius;
	}

	@Override
	public ActorType getActorType() {
		return actorType;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public float getAngle() {
		return angle;
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

}
