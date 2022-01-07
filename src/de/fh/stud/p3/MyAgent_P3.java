package de.fh.stud.p3;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import de.fh.kiServer.agents.Agent;
import de.fh.kiServer.util.Vector2;
import de.fh.pacman.PacmanAgent_2021;
import de.fh.pacman.PacmanGameResult;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.PacmanStartInfo;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;
import de.fh.pacman.enums.PacmanTileType;

public class MyAgent_P3 extends PacmanAgent_2021 {

	/**
	 * Die aktuelle Wahrnehmung der Spielwelt
	 */
	private PacmanPercept percept;
	/**
	 * Die empfangene Rückmeldung des Servers auf die zuletzt ausgeführte Aktion
	 */
	private PacmanActionEffect actionEffect;
	private PacmanTileType[][] view;

	private PacmanAction nextAction;
	
	public MyAgent_P3(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		MyAgent_P3 agent = new MyAgent_P3("MyAgent");
		Agent.start(agent, "127.0.0.1", 5000);
	}

	@Override
	public PacmanAction action(PacmanPercept percept, PacmanActionEffect actionEffect) {
		/*
		 * Aktuelle Wahrnehmung des Agenten, bspw. Position der Geister und Zustand aller Felder der Welt.
		 */
		this.percept = percept;
		
		/*
         * Aktuelle Rückmeldung des Server auf die letzte übermittelte Aktion.
         * 
         * Alle möglichen Serverrückmeldungen:
         * PacmanActionEffect.GAME_INITIALIZED
         * PacmanActionEffect.GAME_OVER
         * PacmanActionEffect.BUMPED_INTO_WALL
         * PacmanActionEffect.MOVEMENT_SUCCESSFUL
         * PacmanActionEffect.DOT_EATEN
		 */
		this.actionEffect = actionEffect;
		
		/*
         * percept.getView() enthält die aktuelle Felderbelegung in einem 2D Array
		 * 
         * Folgende Felderbelegungen sind möglich:
		 * PacmanTileType.WALL;
         * PacmanTileType.DOT
         * PacmanTileType.EMPTY
         * PacmanTileType.PACMAN
         * PacmanTileType.GHOST
         * PacmanTileType.GHOST_AND_DOT
		 */
		this.view = percept.getView();
		
		
		//Gebe den aktuellen Zustand der Welt aus
		/*
		String view_row = "";
		System.out.println("viewsize: " + view.length + "*" + view[0].length);
		for (int x = 0; x < view[0].length; x++) {
			for (int y = 0; y < view.length; y++) {
				view_row += " " + view[y][x];
			}
			System.out.println(view_row);
			view_row = "";
		}
		System.out.println("-------------------------------");
		*/
		
		
		/*
		 * Die möglichen zurückzugebenden PacmanActions sind:
		 * PacmanAction.GO_EAST
		 * PacmanAction.GO_NORTH
		 * PacmanAction.GO_SOUTH
		 * PacmanAction.GO_WEST
		 * PacmanAction.QUIT_GAME
		 * PacmanAction.WAIT
		 */

		Suche suche = new Suche(this.view, percept.getPosition());
		
		if(suche.zielView(this.view)) {
			nextAction = PacmanAction.QUIT_GAME;
		}

		nextAction = calcNextAction(percept.getPosition(), suche.start());
					
		return nextAction;
	}
	
	private PacmanAction calcNextAction(Vector2 pos_old, Vector2 pos_new) {		
		int x = pos_old.x - pos_new.x;
		int y = pos_old.y - pos_new.y;

//		System.out.println("pos x,y : " + x + ", " + y);
//		System.out.println("pos old : " + pos_old.x + ", " + pos_old.y);
//		System.out.println("pos new : " + pos_new.x + ", " + pos_new.y);
		
		if (x == 0 && y == 1) {
			return PacmanAction.GO_NORTH;
		}
		if (x == -1 && y == 0) {
			return PacmanAction.GO_EAST;
		}
		if (x == 0 && y == -1) {
			return PacmanAction.GO_SOUTH;
		}
		if (x == 1 && y == 0) {
			return PacmanAction.GO_WEST;
		}
		if (x == 0 && y == 0) {
			return PacmanAction.WAIT;
		}
		
		return null;
	}

	@Override
	protected void onGameStart(PacmanStartInfo startInfo) {
		
	}

	@Override
	protected void onGameover(PacmanGameResult gameResult) {
		
	}
	
}
