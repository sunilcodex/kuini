package pl.edu.uj.tcs.kuini.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.util.Log;

import pl.edu.uj.tcs.kuini.model.actions.IGlobalAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class LiveState implements ILiveState {
	private List<ILiveActor> actors;
	private Map<Integer, ILivePlayer> playersById;
	private List<ILiveActor> actorsToAdd;
	private final float width;
	private final float height;
	private long lastActorId = -1;
	private int lastPlayerId = 0;
	private IActorOrderer orderer;
	private IGlobalAction globalAction;
	private final IActorWatcher actorWatcher;
	
	public LiveState(float width, float height,
			IActorOrderer orderer, IGlobalAction globalAction, IActorWatcher actorWatcher) {
		this.actors = new LinkedList<ILiveActor>();
		this.playersById = new LinkedHashMap<Integer, ILivePlayer>();
		this.actorsToAdd = new LinkedList<ILiveActor>();
		this.width = width;
		this.height = height;
		this.orderer = orderer;
		this.globalAction = globalAction;
		this.actorWatcher = actorWatcher;
		playersById.put(-1, new Player(-1, "FOOD", PlayerColor.GOLD, 0, 0));
	}

	@Override
	public List<IActor> getActorStates() {
		return new ArrayList<IActor>(actors);
	}

	@Override
	public Map<Integer, IPlayer> getPlayerStatesById() {
		return new LinkedHashMap<Integer, IPlayer>(playersById);
	}

	@Override
	public List<ILiveActor> getLiveActors() {
		return actors;
	}

	@Override
	public Map<Integer, ILivePlayer> getLivePlayersById() {
		return playersById;
	}

	@Override
	public void addActor(ILiveActor actor) {
		actorsToAdd.add(actor);
	}

	@Override
	public void addPlayer(ILivePlayer player) {
		playersById.put(player.getId(), player);
	}


	@Override
	public List<ILiveActor> getNeighbours(Position position, float radius) {
		return actorWatcher.getNeighbours(position, radius);
	}

	@Override
	public void nextTurn(float elapsedTime) {
		//long time = System.nanoTime();
		for(ILiveActor actor : orderer.orderActors(actors)){
			actor.performAction(elapsedTime, this);
			actorWatcher.updatePosition(actor);
		}
		globalAction.performAction(elapsedTime, this);
		for(ILiveActor actor : actorsToAdd)
			actorWatcher.addActor(actor);
		actors.addAll(actorsToAdd);
		actorsToAdd.clear();
		Set<ILiveActor> actorsToRemove = new HashSet<ILiveActor>();
		for(ILiveActor actor : actors){
			if(actor.isDead()){
				actorsToRemove.add(actor);
				actorWatcher.removeActor(actor);
			}
		}
		actors.removeAll(actorsToRemove);
		//Log.d("TIME", "Next turn computed in "+(((double)(System.nanoTime()-time))/1000000000)+
		//		"s (actors:"+actors.size()+")");
	}

	@Override
	public void doCommand(Command command) {
		int selectedActors = 0;
		Position start = command.getStart();
		for(ILiveActor actor : getNeighbours(start, command.getRadius())){
			if(actor.getPlayerId() == command.getPlayerId()){
				actor.setPath(command.getPath());
				selectedActors++;
			}
		}
		Log.d("COMMAND", "Command received: "+command+" ("+selectedActors+" actors)");
	}

	@Override
	public long nextActorId() {
		lastActorId++;
		return lastActorId;
	}

	@Override
	public int nextPlayerId() {
		lastPlayerId++;
		return lastPlayerId;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public int getFoodPlayerId() {
		return -1;
	}
}
