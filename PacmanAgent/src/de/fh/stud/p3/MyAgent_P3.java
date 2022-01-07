package de.fh.stud.p3;

import de.fh.kiServer.agents.Agent;
import de.fh.pacman.PacmanAgent_2021;
import de.fh.pacman.PacmanGameResult;
import de.fh.pacman.PacmanPercept;
import de.fh.pacman.PacmanStartInfo;
import de.fh.pacman.enums.PacmanAction;
import de.fh.pacman.enums.PacmanActionEffect;
import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p1.Knoten;

import java.util.LinkedList;
import java.util.Scanner;

public class MyAgent_P3 extends PacmanAgent_2021 {

    /**
     * Die aktuelle Wahrnehmung der Spielwelt
     */
    private PacmanPercept percept;
    /**
     * Die empfangene Rückmeldung des Servers auf die zuletzt ausgeführte Aktion
     */
    private PacmanActionEffect actionEffect;
    /**
     * Das aktuell wahrgenommene 2D Array der Spielwelt
     */
    private PacmanTileType[][] view;
    /**
     * Der gefundene Lösungknoten der Suche
     */
    private Knoten loesungsKnoten;

    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    private Knoten startKnoten;
    private PacmanAction nextAction;
    private LinkedList<PacmanAction> actions = new LinkedList<>();

    public MyAgent_P3(String name) {
        super(name);
    }

    public static void main(String[] args) {
        MyAgent_P3 agent = new MyAgent_P3("MyAgent");
        Agent.start(agent, "127.0.0.1", 5000);
    }

    @Override
    public PacmanAction action(PacmanPercept percept, PacmanActionEffect actionEffect) {
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

        /*String view_row = "";
        System.out.println("viewsize: " + view.length + "*" + view[0].length);
        for (int x = 0; x < view[0].length; x++) {
            for (int y = 0; y < view.length; y++) {
                view_row += " " + view[y][x];
            }
            System.out.println(view_row);
            view_row = "";
        }
        System.out.println("-------------------------------");*/

        if (loesungsKnoten == null) {

            scannerOutput();
            calculateActions();
            System.out.println("Number of Actions: " + YELLOW + actions.size() + RESET);
        }

        if (loesungsKnoten != null) {
            if (actions.isEmpty()) {
                nextAction = PacmanAction.QUIT_GAME;
            } else {
                nextAction = actions.removeLast();
            }
            //System.out.println(nextAction);
        } else {
            nextAction = PacmanAction.QUIT_GAME;
        }
        return nextAction;
    }

    private void calculateActions() {

        //LinkedList<PacmanAction> actions = new LinkedList<>();
        Knoten previous = loesungsKnoten.getLast();
        Knoten current = loesungsKnoten;

        while (previous != null) {
            if ((current.getX() + 1 == previous.getX()) && current.getY() == previous.getY()) {
                actions.add(PacmanAction.GO_WEST);

            } else if ((current.getY() + 1 == previous.getY()) && (current.getX() == previous.getX())) {
                actions.add(PacmanAction.GO_NORTH);

            } else if ((current.getY() - 1 == previous.getY()) && (current.getX() == previous.getX())) {
                actions.add(PacmanAction.GO_SOUTH);

            } else if ((current.getX() - 1 == previous.getX() && current.getY() == previous.getY())) {
                actions.add(PacmanAction.GO_EAST);
            }
            current = previous;
            previous = previous.getLast();
        }
    }

    @Override
    protected void onGameStart(PacmanStartInfo startInfo) {

    }

    @Override
    protected void onGameover(PacmanGameResult gameResult) {

    }

    public void scannerOutput() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a number from 1 to 5 to select a search method:" + "\n" + "1. Tiefensuche" + "\n"
                + "2. Breitensuche" + "\n" + "3. Uniform Cost Search (UCS)" + "\n" + "4. Greedy Search" + "\n" + "5. A* Search");
        System.out.println(RED + "0. Exit" + RESET);

        int x = input.nextInt();

        if (x == 0) {
            nextAction = PacmanAction.QUIT_GAME;
        } else if (x == 1) {
            startKnoten = new Knoten(view, percept.getPosX(), percept.getPosY(), null);
            Suche s = new Suche(startKnoten);
            loesungsKnoten = s.startTiefenSuche();
            System.out.println("Elements in ClosedList: " + YELLOW + s.getClosedList() + RESET);
            System.out.println("Elements in OpenList: " + YELLOW + s.getOpenList() + RESET);

        } else if (x == 2) {
            startKnoten = new Knoten(view, percept.getPosX(), percept.getPosY(), null);
            Suche s = new Suche(startKnoten);
            loesungsKnoten = s.startBreitenSuche();
            System.out.println("Elements in ClosedList: " + YELLOW + s.getClosedList() + RESET);
            System.out.println("Elements in OpenList: " + YELLOW + s.getOpenList() + RESET);
        } else if (x == 3) {
            startKnoten = new Knoten(view, percept.getPosX(), percept.getPosY(), 0, null);
            Suche s = new Suche(startKnoten);
            loesungsKnoten = s.startUCS();
            System.out.println("Elements in ClosedList: " + YELLOW + s.getClosedList() + RESET);
            System.out.println("Elements in OpenList: " + YELLOW + s.getOpenList() + RESET);
        } else if (x == 4) {
            startKnoten = new Knoten(view, percept.getPosX(), percept.getPosY(), null);
            Suche s = new Suche(startKnoten);
            loesungsKnoten = s.startGreedySuche();
            System.out.println("Elements in ClosedList: " + YELLOW + s.getClosedList() + RESET);
            System.out.println("Elements in OpenList: " + YELLOW + s.getOpenList() + RESET);
        } else if (x == 5) {
            x = 0;
            startKnoten = new Knoten(view, percept.getPosX(), percept.getPosY(), null);
            Suche s = new Suche(startKnoten);
            loesungsKnoten = s.startAStern();
            System.out.println("Elements in ClosedList: " + YELLOW + s.getClosedList() + RESET);
            System.out.println("Elements in OpenList: " + YELLOW + s.getOpenList() + RESET);
        } else {
            System.out.println(YELLOW + "!!Invalid input!!" + RESET);
            scannerOutput();
        }
    }

}
