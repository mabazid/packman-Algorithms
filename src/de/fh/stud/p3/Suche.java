package de.fh.stud.p3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import de.fh.kiServer.util.Vector2;
import de.fh.pacman.enums.PacmanTileType;

public class Suche {
//	private LinkedList<Knoten> closedList;
//	private LinkedList<Knoten> openList;
//	
//	private Knoten startKnoten;

//	public Suche(Knoten startKnoten) {
//		this.startKnoten = startKnoten;
//	}


    private double[][] mdpArray;
    private PacmanTileType[][] view;

    private LinkedList<DotDistance> distanceList;

//	public static boolean[][] tileVisited;

    public double discount = 1;
    public double stepCost = 0.03d;

    public static int k = 30;

    public Vector2 pacPosition;

    public Suche(PacmanTileType[][] view, Vector2 pacPosition) {
        this.view = view;
        this.pacPosition = pacPosition;

        distanceList = new LinkedList<DotDistance>();
    }

    public Vector2 suche(Vector2 firstDotPos) {
//		performMdp(firstDotPos, getDistance(firstDotPos.x,firstDotPos.y, pacPosition.x, pacPosition.y) + 2);
        performMdp(firstDotPos, k);
//		printMDP();
        return getNextPosition();
    }

    /*
     * TODO Praktikum 3 [1]: Erweitern Sie diese Klasse um die notwendigen
     * Attribute und Methoden um eine Tiefensuche durchführen zu können.
     * Die Erweiterung um weitere Suchstrategien folgt in Praktikum 4.
     */

    /*
     * TODO Praktikum 4 [1]: Erweitern Sie diese Klasse um weitere Suchstrategien (siehe Aufgabenblatt)
     * zu unterstützen.
     */

    public Vector2 start() {
        buildInitialMdpArray(true);

        Vector2 newPos = new Vector2(distanceList.getFirst().x, distanceList.getFirst().y);

        return suche(newPos);
    }

    public void buildInitialMdpArray(boolean sorted) {
        mdpArray = new double[view.length][];

        for (int x = 0; x < view.length; x++) {
            mdpArray[x] = new double[view[x].length];

            for (int y = 0; y < view[x].length; y++) {

                mdpArray[x][y] = view[x][y] == PacmanTileType.GHOST || view[x][y] == PacmanTileType.GHOST_AND_DOT ? -10 : 0;

                if (view[x][y] == PacmanTileType.GHOST_AND_DOT || view[x][y] == PacmanTileType.DOT) {
                    distanceList.add(new DotDistance(x, y, getDistance(pacPosition.x, pacPosition.y, x, y)));
                }

//                if (view[x][y] == PacmanTileType.DOT) {
//                    distanceList.add(new DotDistance(x, y, getDistance(pacPosition.x, pacPosition.y, x, y)));
//                    mdpArray[x][y] = 0;
//                } else if ((view[x][y] == PacmanTileType.EMPTY)) {
//                    mdpArray[x][y] = 0;
//                } else if ((view[x][y] == PacmanTileType.GHOST_AND_DOT)) {
//                	distanceList.add(new DotDistance(x, y, getDistance(pacPosition.x, pacPosition.y, x, y)));
//                    setGhostRange(new Vector2(x, y));
//                } else if ((view[x][y] == PacmanTileType.GHOST)) {
//                    mdpArray[x][y] = -10;
//                    setGhostRange(new Vector2(x, y));
//                } 
            }
        }

        if (sorted) {
            distanceList.sort(Comparator.comparingInt(DotDistance::getDistance));
        }

        mdpArray[distanceList.getFirst().x][distanceList.getFirst().y] = 1;
    }

//	private boolean checkGhost(Vector2 pos) {
//		for(int x = -1; x <= 1; x++) {
//			for(int y = -1; y <= 1; y++) {
//				if(Math.abs(x) + Math.abs(y) == 2 
//						|| (pos.x + x) < 0 || (pos.x + x) >= mdpArray.length 
//						|| (pos.y + y) < 0 || (pos.y + y) >= mdpArray[pos.x].length) {
//						continue;
//				}
//				if(view[pos.x + x][pos.y + y] == PacmanTileType.GHOST || 
//						view[pos.x + x][pos.y + y] == PacmanTileType.GHOST_AND_DOT) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}

    public int getDistance(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }

    public boolean zielView(PacmanTileType[][] view) {
        for (int i = 0; i < view.length; i++) {
            for (int j = 0; j < view[0].length; j++) {
                if (view[i][j] == PacmanTileType.DOT) {
                    return false;
                }
            }
        }
        return true;
    }

    public void performMdp(Vector2 currentPos, int maxIteration) {

        LinkedList<Vector2> nextChilds = new LinkedList<>();

        Set<Vector2> childList = new HashSet<Vector2>();

        for (int i = 0; i < maxIteration; i++) {

            nextChilds.add(currentPos);
            childList.add(currentPos);

            for (int j = 0; j < i; j++) {
                for (Vector2 vec : expand(nextChilds)) {
                    if (childList.add(vec)) {
                        nextChilds.add(vec);
                        mdpArray[vec.x][vec.y] -= stepCost * j;
                    }
                }
            }

            //nextChilds.removeFirst();

            for (Vector2 vec : nextChilds) {
                mdpArray[vec.x][vec.y] = getBestValue(vec);
            }

            nextChilds.clear();
            childList.clear();
        }
    }

//	private boolean ghostNearBy(Vector2 pos) {
//		
//		int radius = 2; 
//		
//		for(int x = -radius; x <= radius; x++) {
//			for(int y = -radius; y <= radius; y++) {
//				if(Math.abs(x) + Math.abs(y) == (radius * 2) 
//						|| Math.abs(x) + Math.abs(y) == 0
//						|| (pos.x + x) < 0 || (pos.x + x) >= mdpArray.length 
//						|| (pos.y + y) < 0 || (pos.y + y) >= mdpArray[pos.x].length
//						|| view[pos.x + x][pos.y + y] == PacmanTileType.WALL) {
//					continue;
//				}
//				
//				if(view[pos.x + x][pos.y + y] == PacmanTileType.GHOST || view[pos.x + x][pos.y + y] == PacmanTileType.GHOST_AND_DOT) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}

    public List<Vector2> expand(List<Vector2> posList) {
        List<Vector2> nextChilds = new ArrayList<>();

        for (Vector2 vec : posList) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (Math.abs(x) + Math.abs(y) == 2
                            || Math.abs(x) + Math.abs(y) == 0
                            || (vec.x + x) < 0 || (vec.x + x) >= mdpArray.length
                            || (vec.y + y) < 0 || (vec.y + y) >= mdpArray[vec.x].length
                            || view[vec.x + x][vec.y + y] == PacmanTileType.WALL
                            || view[vec.x + x][vec.y + y] == PacmanTileType.GHOST
                            || view[vec.x + x][vec.y + y] == PacmanTileType.GHOST_AND_DOT) {
                        continue;
                    }

                    nextChilds.add(new Vector2(vec.x + x, vec.y + y));
                }
            }
        }

        return nextChilds;
    }

    public double getBestValue(Vector2 pos) {

        double currentValue = 0;

        int count = 0;

        int wallCount = 0;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {

                if (Math.abs(x) + Math.abs(y) == 2
                        || Math.abs(x) + Math.abs(y) == 0
                        || (pos.x + x) < 0 || (pos.x + x) >= mdpArray.length
                        || (pos.y + y) < 0 || (pos.y + y) >= mdpArray[pos.x].length) {
                    continue;
                }

                count++;

                if (view[pos.x + x][pos.y + y] == PacmanTileType.WALL) {
                    currentValue += mdpArray[pos.x][pos.y];
                    wallCount++;
                } else {
                    currentValue += mdpArray[pos.x + x][pos.y + y];

//					System.out.println("(" + x + "," + y + ") " + mdpValue);
                }
//				System.out.println("MDP VALUE (" + x + "," + y + ") " + mdpValue);				
            }
        }

        if (wallCount == 3) {
            return -5;
        }

        return (currentValue / count);
    }

    private Vector2 getNextPosition() {
        Vector2 currentPacmanPos = new Vector2(pacPosition.x, pacPosition.y);
        Vector2 nextPos = currentPacmanPos;

        double maxValue = -1000;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {


                if (Math.abs(x) + Math.abs(y) == 2
                        || Math.abs(x) + Math.abs(y) == 0
                        || view[currentPacmanPos.x + x][currentPacmanPos.y + y] == PacmanTileType.WALL) {
                    continue;
                }

                double value = mdpArray[currentPacmanPos.x + x][currentPacmanPos.y + y];

                Random rnd = new Random();
                int r = rnd.nextInt(2);

                if (value > maxValue) {
                    maxValue = value;
                    nextPos = new Vector2(currentPacmanPos.x + x, currentPacmanPos.y + y);
                }

                if (value == maxValue && r == 0) {
                    maxValue = value;
                    nextPos = new Vector2(currentPacmanPos.x + x, currentPacmanPos.y + y);
                }
            }
        }

        return nextPos;
    }

    private void printMDP() {
        for (int x = 0; x < mdpArray.length; x++) {
            for (int y = 0; y < mdpArray[x].length; y++) {
                System.out.print(mdpArray[y][x] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }
}
