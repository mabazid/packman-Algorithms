package de.fh.stud.p1;

import de.fh.pacman.enums.PacmanTileType;
import de.fh.stud.p3.DotDistance;

import java.util.*;

public class Knoten implements Comparable<Knoten> {

    PacmanTileType[][] myView;
    Knoten last;

    int cost;
    int dots;
    int x;
    int y;
    int heuristic;

    public Knoten(PacmanTileType[][] view, int x, int y, Knoten last) {
        this.last = last;
        this.x = x;
        this.y = y;

        myView = new PacmanTileType[view.length][view[0].length];

        for (int i = 0; i < view.length; i++) {
            for (int j = 0; j < view[i].length; j++) {
                myView[i][j] = view[i][j];
            }
        }
        this.dots = countDots();
        this.heuristic = countHeuristic();

        myView[x][y] = PacmanTileType.EMPTY;
    }

    public Knoten(PacmanTileType[][] view, int x, int y, int cost, Knoten last) {
        this(view, x, y, last);
        this.cost = cost;
    }


    public PacmanTileType[][] getMyView() {
        return this.myView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Knoten knoten = (Knoten) o;
        return x == knoten.x &&
                y == knoten.y &&
                Arrays.deepEquals(myView, knoten.myView);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(x, y);
        result = 31 * result + Arrays.deepHashCode(myView);
        return result;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCost() {
        return cost;
    }

    public Knoten getLast() {
        return last;
    }

    public List<Knoten> expand() {
        List<Knoten> kinder = new LinkedList<>();
        if (myView[x][y - 1] != PacmanTileType.WALL) {
            Knoten kind = new Knoten(myView, x, y - 1, this);
            kinder.add(kind);
        }
        if (myView[x][y + 1] != PacmanTileType.WALL) {
            Knoten kind = new Knoten(myView, x, y + 1, this);
            kinder.add(kind);
        }
        if (myView[x - 1][y] != PacmanTileType.WALL) {
            Knoten kind = new Knoten(myView, x - 1, y, this);
            kinder.add(kind);
        }
        if (myView[x + 1][y] != PacmanTileType.WALL) {
            Knoten kind = new Knoten(myView, x + 1, y, this);
            kinder.add(kind);
        }
        return kinder;
    }

    public List<Knoten> expandUCS() {
        List<Knoten> kinder = new LinkedList<>();
        if (myView[x][y - 1] != PacmanTileType.WALL) {
            Knoten kind = new Knoten(myView, x, y - 1, cost + 1, this);
            kinder.add(kind);
        }
        if (myView[x][y + 1] != PacmanTileType.WALL) {
            Knoten kind = new Knoten(myView, x, y + 1, cost + 1, this);
            kinder.add(kind);
        }
        if (myView[x - 1][y] != PacmanTileType.WALL) {
            Knoten kind = new Knoten(myView, x - 1, y, cost + 1, this);
            kinder.add(kind);
        }
        if (myView[x + 1][y] != PacmanTileType.WALL) {
            Knoten kind = new Knoten(myView, x + 1, y, cost + 1, this);
            kinder.add(kind);
        }
        return kinder;
    }


    public boolean hasDots() {
        for (int i = 0; i < myView.length; i++) {
            for (int j = 0; j < myView[i].length; j++) {
                if (myView[i][j] == PacmanTileType.DOT) {
                    return true;
                }
            }
        }
        return false;
    }

    public int countHeuristic() {
        return countDots() + getCost();
    }

    public int countDots() {
        int result = 0;
        for (int i = 0; i < myView.length; i++) {
            for (int j = 0; j < myView[i].length; j++) {
                if (myView[i][j] == PacmanTileType.DOT) {
                    result++;
                }
            }
        }
        return result;

    }

    @Override
    public int compareTo(Knoten o) {
        if (this.getCost() == o.getCost())
            return 0;
        if (this.getCost() < o.getCost())
            return 1;
        return -1;
    }
}