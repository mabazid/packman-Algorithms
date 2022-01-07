package de.fh.stud.p3;

import de.fh.kiServer.util.Vector2;

public class Mdp {
    public double[][] mdpArray = new double[5][5];
    public static boolean[][] tileVisited = new boolean[5][5];

    public double discount = 1;
    public double stepCost = 0.01d;

    public static int k = 10;

    public static void main(String[] args) {
        for (int i = 0; i < k; i++) {
            // performMdp();

            tileVisited = new boolean[5][5];
        }
    }

    public void performMdp(Vector2 currentPos) {
        Vector2 localPos = currentPos;

        tileVisited[currentPos.x][currentPos.y] = true;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {

                if (Math.abs(x) + Math.abs(y) == 2
                        || Math.abs(x) + Math.abs(y) == 0
                        || (localPos.x + x) >= mdpArray.length || (localPos.x + x) < 0
                        || (localPos.y + y) >= mdpArray[x].length || (localPos.y + y) < 0) {
                    continue;
                }

                mdpArray[localPos.x + x][localPos.y + y] = getBestValue(new Vector2(localPos.x + x, localPos.y + y));

                if (!tileVisited[localPos.x + x][localPos.y + y]) {
                    performMdp(new Vector2(localPos.x + x, localPos.y + y));
                }
            }
        }
    }

    public double getBestValue(Vector2 pos) {

        double currentValue = 1;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {

                if (Math.abs(x) + Math.abs(y) == 2
                        || Math.abs(x) + Math.abs(y) == 0) {
                    continue;
                }

                double mdpValue = 0d;

                if ((pos.x + x) >= mdpArray.length || (pos.x + x) < 0
                        || (pos.y + y) >= mdpArray[x].length || (pos.y + y) < 0) {
                    mdpValue = discount * mdpArray[pos.x][pos.y] - stepCost;
                } else {
                    mdpValue = discount * mdpArray[pos.x + x][pos.y + y] - stepCost;
                }

                currentValue = mdpValue > currentValue ? mdpValue : currentValue;
            }
        }

        return currentValue;
    }
}
