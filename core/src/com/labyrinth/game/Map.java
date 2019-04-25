package com.labyrinth.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

public class Map extends Actor {
    int x;
    int y;
    boolean[][] map;
    float aspectRatio;
    int xEnd = 0, yEnd = 0;
    private boolean[][] fogMap;
    private Sprite[][] sprites;
    private float scale = 100;
    private Sprite[][] spritesUp;
    private Texture texture;
    private Texture texture1;
    private Sprite end;
    private Level level;

    Map(Level level) {
        this.level = level;
        x = Labyrinth.n;
        y = Labyrinth.m;
        LabyrinthGenerator lg = new LabyrinthGenerator();
        map = lg.generate(x, y, 1, 1, 1);

        fogMap = new boolean[x][y];

        sprites = new Sprite[x][y];
        spritesUp = new Sprite[x][y];
        texture = new Texture("trava1.png");
        texture1 = new Texture("elochki.png");

        Random random = new Random();
        while (map[xEnd][yEnd]) {
            xEnd = random.nextInt(x - 2) + 1;
            yEnd = random.nextInt(y - 2) + 1;
        }

        for (int x = 0; x < this.x; x++) {
            for (int y = 0; y < this.y; y++) {
                sprites[x][y] = new Sprite(texture);
                Vector2 cur = getIsometric(new Vector2(scale * x, scale * y));
                sprites[x][y].setPosition(cur.x, cur.y);
                sprites[x][y].setSize(scale, scale);
                if (map[x][y]) {
                    spritesUp[x][y] = new Sprite(texture1);
                    spritesUp[x][y].setPosition(cur.x, cur.y);
                    spritesUp[x][y].setSize(scale * 1.5f, scale * 1.5f);
                }
                if (x == xEnd && y == yEnd) {
                    end = new Sprite(new Texture("end.png"));
                    end.setPosition(cur.x, cur.y);
                    end.setSize(scale, scale);
                }
            }
        }
    }

    public static Vector2 getIsometric(Vector2 coordinates) {
        return new Vector2(coordinates.x - coordinates.y,
                (coordinates.x + coordinates.y) / 2);
    }

    public static Vector2 getCoordinatesFromIs(Vector2 isometric) {
        return new Vector2((2 * isometric.y + isometric.x) / 2,
                (2 * isometric.y - isometric.x) / 2);
    }

    public Vector2 getCell(Vector2 coordinates) {
        Vector2 curPos = getCoordinatesFromIs(coordinates);
        return new Vector2((int) (curPos.x / scale), (int) (curPos.y / scale));
    }

    public Vector2 getCellFromCart(Vector2 coordinates) {
        return new Vector2((int) (coordinates.x / scale), (int) (coordinates.y / scale));
    }

    public Vector2 getIsfromCell(Vector2 cell) {
        return new Vector2(getIsometric(new Vector2(scale * cell.x, scale * cell.y)));
    }

    public boolean inCell(int x, int y) {
        return (x >= 0 && x < this.x && y >= 0 && y < this.y);
    }

    public void dissipateFog(int x, int y, int radius) {
        int curX, curY, borderX;
        for (int curYR = -radius; curYR <= radius; curYR++) {
            if (curYR == 0) {
                borderX = radius;
            } else {
                borderX = (int) Math.ceil(Math.abs((float) (radius) / (float) (curYR)));
            }
            for (int curXR = -borderX; curXR <= borderX; curXR++) {
                curX = x + curXR;
                curY = y + curYR;

                if (inCell(curX, curY)) {
                    fogMap[curX][curY] = true;
                }
            }
        }
    }

    private boolean isDrawable(int x, int y) {
        return !Labyrinth.isFoggy || fogMap[x][y];
    }

    private void drawGameScene(Batch batch, float parentAlpha) {
        batch.begin();
        Vector2 playerPos;
        for (int z = 0; z < this.y; z++) {
            for (int x = 0; x < this.x; x++) {
                if (isDrawable(x, z)) {
                    sprites[x][z].draw(batch);
                }
            }
        }
        for (int y = this.y - 1; y >= 0; y--) {
            for (int x = this.x - 1; x >= 0; x--) {
                if (isDrawable(x, y) && map[x][y]) {
                    spritesUp[x][y].draw(batch);
                }
                playerPos = ((Player) level.selectedActor).getCellCoordinates();

                if (xEnd == x && y == yEnd) {
                    end.draw(batch);
                }

                if (playerPos.x == x && playerPos.y == y) {
                    level.selectedActor.draw(batch, parentAlpha);
                }
            }
        }
        batch.end();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawGameScene(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {

    }
}
