package de.fh.stud.p2;

import de.fh.kiServer.agents.Agent;
import de.fh.pacman.PacmanAgent_2021;
import de.fh.pacman.PacmanGameResult;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.PacmanStartInfo;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;
import de.fh.pacman.enums.PacmanTileType;

import java.util.HashMap;

public class MyAgent_P2 extends PacmanAgent_2021 {

    /*
     * TODO Praktikum 2 [1]: Fügt gemäß der Aufgabenstellung neue Attribute hinzu, falls notwendig.
     */


    /**
     * Die letzte Wahrnehmung der Spielwelt
     */
    private PacmanPercept percept;

    /**
     * Das aktuell wahrgenommene 2D Array der Spielwelt
     */
    private PacmanTileType[][] view;

    /**
     * Die zuletzt empfangene Rückmeldung des Servers auf die ausgeführte Aktion
     */
    private PacmanActionEffect actionEffect;

    /**
     * Die als nächstes auszuführende Aktion
     */
    private PacmanAction nextAction;

    public MyAgent_P2(String name) {
        super(name);
    }

    public static void main(String[] args) {
        MyAgent_P2 agent = new MyAgent_P2("MyAgent");
        Agent.start(agent, "127.0.0.1", 5000);
    }

    @Override
    public PacmanAction action(PacmanPercept percept, PacmanActionEffect actionEffect) {
        /*
         * Aktuelle Wahrnehmung des Agenten, bspw. Position der Geister und Zustand aller Felder der Welt.
         */
        PacmanTileType[] l = {PacmanTileType.WALL};

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

        //Beispiel:
        //Gebe den aktuellen Zustand der Welt aus
        String view_row = "";
        System.out.println("Weltgröße: " + view.length + "*" + view[0].length);
        for (int x = 0; x < view[0].length; x++) {
            for (int y = 0; y < view.length; y++) {
                view_row += " " + view[y][x];
            }
            System.out.println(view_row);
            view_row = "";
        }
        System.out.println("-------------------------------");

        /*
         * Die möglichen zurückzugebenden PacmanActions sind:
         * PacmanAction.GO_EAST
         * PacmanAction.GO_NORTH
         * PacmanAction.GO_SOUTH
         * PacmanAction.GO_WEST
         * PacmanAction.QUIT_GAME
         * PacmanAction.WAIT
         */

        //Nachdem das Spiel gestartet wurde, geht der Agent nach Osten
        if (actionEffect == PacmanActionEffect.GAME_INITIALIZED) {
            nextAction = PacmanAction.GO_EAST;
        }
        if (actionEffect == PacmanActionEffect.BUMPED_INTO_WALL) {

            if (view[percept.getPosX()][percept.getPosY() + 1] == PacmanTileType.DOT) {
                nextAction = PacmanAction.GO_SOUTH;

            } else if (view[percept.getPosX() - 1][percept.getPosY()] == PacmanTileType.DOT) {
                nextAction = PacmanAction.GO_WEST;

            } else if (view[percept.getPosX()][percept.getPosY() - 1] == PacmanTileType.DOT) {
                nextAction = PacmanAction.GO_NORTH;

            } else if (view[percept.getPosX() + 1][percept.getPosY()] == PacmanTileType.DOT) {
                nextAction = PacmanAction.GO_EAST;
            }
        }


        /*
         * TODO Praktikum 2 [1]: Erweitern Sie diese action-Methode gemäß der Aufgabenstellung.
         */

        return nextAction;
    }

    @Override
    protected void onGameStart(PacmanStartInfo startInfo) {

    }

    @Override
    protected void onGameover(PacmanGameResult gameResult) {

    }
}
