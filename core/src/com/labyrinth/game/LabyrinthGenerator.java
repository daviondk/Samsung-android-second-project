package com.labyrinth.game;

import java.util.Random;

public class LabyrinthGenerator {
    private final boolean WALL = true;
    private final boolean FREE = false;
    private Random random = new Random();

    public boolean[][] generate(int n, int m, int xStart, int xEnd, int yEnd) {

        final boolean OLD = true;
        final boolean NEW = false;
        boolean[][] labyrinth = new boolean[n][m];
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                if (x == 0 || y == 0 || x == n - 1 || y == m - 1) {
                    labyrinth[x][y] = WALL;
                } else if (x % 2 == 0 || y % 2 == 0) {
                    labyrinth[x][y] = WALL;
                }
            }
        }
        boolean[][] used = new boolean[n][m];
        for (boolean[] line : used) {
            for (boolean cur : line) {
                cur = NEW;
            }
        }
        dfs(labyrinth, used, n, m, 1, 1);
        return labyrinth;
    }

    private void dfs(boolean[][] labyrinth, boolean[][] used,
                     int n, int m, int curx, int cury) {
        used[curx][cury] = true;
        int pos = getRandPosition(used, n, m, curx, cury);
        while (pos != 0) {
            switch (pos) {
                case 1:
                    labyrinth[curx][cury + 1] = FREE;
                    dfs(labyrinth, used, n, m, curx, cury + 2);
                    break;
                case 2:
                    labyrinth[curx + 1][cury] = FREE;
                    dfs(labyrinth, used, n, m, curx + 2, cury);
                    break;
                case 3:
                    labyrinth[curx][cury - 1] = FREE;
                    dfs(labyrinth, used, n, m, curx, cury - 2);
                    break;
                case 4:
                    labyrinth[curx - 1][cury] = FREE;
                    dfs(labyrinth, used, n, m, curx - 2, cury);
                    break;
            }
            pos = getRandPosition(used, n, m, curx, cury);
        }
    }

    private int getRandPosition(boolean[][] used, int n, int m,
                                int curx, int cury) {
        boolean UP = false, LEFT = false, RIGHT = false, DOWN = false;
        int count = 0;
        if (cury - 2 > 0 && !used[curx][cury - 2]) {
            DOWN = true;
            count++;
        }
        if (curx - 2 > 0 && !used[curx - 2][cury]) {
            LEFT = true;
            count++;
        }
        if (cury + 2 < m - 1 && !used[curx][cury + 2]) {
            UP = true;
            count++;
        }
        if (curx + 2 < n - 1 && !used[curx + 2][cury]) {
            RIGHT = true;
            count++;
        }
        if (count > 0) {
            int rand = random.nextInt(count) + 1;
            if (UP) {
                rand--;
                if (rand == 0) {
                    return 1;
                }
            }
            if (RIGHT) {
                rand--;
                if (rand == 0) {
                    return 2;
                }
            }
            if (DOWN) {
                rand--;
                if (rand == 0) {
                    return 3;
                }
            }
            return 4;
        }
        return 0;
    }
}
