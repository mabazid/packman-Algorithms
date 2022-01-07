package de.fh.stud.p1;

import de.fh.pacman.enums.PacmanTileType;

import java.util.HashMap;

public class BaumTest {


    public static void main(String[] args) {
        HashMap<PacmanTileType, String> states = new HashMap<PacmanTileType, String>();
        states.put(PacmanTileType.WALL, "WALL");
        states.put(PacmanTileType.EMPTY, "Empty");
        states.put(PacmanTileType.DOT, "DOT");

        //Anfangszustand nach Aufgabe
        PacmanTileType[][] view = {
                {PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL},
                {PacmanTileType.WALL, PacmanTileType.EMPTY, PacmanTileType.DOT, PacmanTileType.WALL},
                {PacmanTileType.WALL, PacmanTileType.DOT, PacmanTileType.WALL, PacmanTileType.WALL},
                {PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL, PacmanTileType.WALL}
        };
        //Startposition des Pacman
        int posX = 1, posY = 1;
        /*
         * TODO Praktikum 1 [3]: Baut hier basierend auf dem gegebenen
         * Anfangszustand (siehe view, posX und posY) den Suchbaum auf.
         */
    }


}
