package de.fh.stud.p3;

import java.util.*;

import de.fh.stud.p1.Knoten;

public class Suche {
    // PacmanTileType[][] myView;
    Knoten startKnoten;
    Knoten zielKnoten;
    Set<Knoten> closedList;
    LinkedList<Knoten> openList;

    public Suche(Knoten startKnoten) {
        this.startKnoten = startKnoten;
        openList = new LinkedList<>();
        closedList = new HashSet<>();
    }

    public Knoten startTiefenSuche() {
        openList.add(startKnoten);

        while (!openList.isEmpty()) {

            Knoten temp = openList.removeLast();

            closedList.add(temp);

            if (temp.hasDots()) {
                for (Knoten k : temp.expand()) {
                    if (!closedList.contains(k))
                        openList.add(k);
                }
            } else {
                return temp;
            }
        }
        return null;
    }

    public Knoten startBreitenSuche() {
        openList.add(startKnoten);

        while (!openList.isEmpty()) {

            Knoten temp = openList.removeLast();

            closedList.add(temp);

            if (temp.hasDots()) {
                for (Knoten k : temp.expand()) {
                    if (!closedList.contains(k))
                        openList.addFirst(k);
                }
            } else {
                return temp;
            }
        }
        return null;
    }

    public Knoten startUCS() {
        openList.add(startKnoten);

        while (!openList.isEmpty()) {
            Knoten temp = openList.removeFirst();

            closedList.add(temp);

            if (temp.hasDots()) {
                for (Knoten k : temp.expandUCS()) {
                    if (!closedList.contains(k))
                        openList.add(k);
                }
                openList.sort(Comparator.comparingInt(Knoten::getCost));
            } else {
                return temp;
            }
        }
        return null;
    }

    public Knoten startGreedySuche() {
        openList.add(startKnoten);

        while (!openList.isEmpty()) {

            Knoten temp = openList.removeFirst();

            closedList.add(temp);

            if (temp.hasDots()) {
                for (Knoten k : temp.expand()) {
                    if (!closedList.contains(k))
                        openList.add(k);
                }
                openList.sort(Comparator.comparingInt(Knoten::countDots));
            } else {
                return temp;
            }
        }
        return null;
    }

    public Knoten startAStern() {
        openList.add(startKnoten);

        while (!openList.isEmpty()) {

            Knoten temp = openList.removeFirst();

            closedList.add(temp);

            if (temp.hasDots()) {
                for (Knoten k : temp.expandUCS()) {
                    if (!closedList.contains(k))
                        openList.add(k);
                }
                openList.sort(Comparator.comparingInt(Knoten::countHeuristic));
            } else {
                return temp;
            }
        }
        return null;
    }

    public int getClosedList() {
        return closedList.size();
    }

    public int getOpenList() {
        return openList.size();
    }

}
