package com.labyrinth.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

public class Player extends Actor {
    public Vector2 pos, realPos;
    private short way = 0;
    private double speed = 50;
    private Animation leftAnimation, rightAnimation, downAnimation, upAnimation;
    private Vector2 curCell;
    private Random random;
    private Texture texture;
    private Level level;

    Player(Level level) {
        this.level = level;
        pos = getRandPos();
        curCell = pos;
        level.map.dissipateFog((int) pos.x, (int) pos.y, 5);
        realPos = level.map.getIsfromCell(pos);
        texture = new Texture("player/boy.png");
        upAnimation = new Animation(new TextureRegion(new Texture("player/up.png")), 6, 0.5f / (float) Labyrinth.speedMultiplier);
        rightAnimation = new Animation(new TextureRegion(new Texture("player/right.png")), 6, 0.5f / (float) Labyrinth.speedMultiplier);
        downAnimation = new Animation(new TextureRegion(new Texture("player/down.png")), 6, 0.5f / (float) Labyrinth.speedMultiplier);
        leftAnimation = new Animation(new TextureRegion(new Texture("player/left.png")), 6, 0.5f / (float) Labyrinth.speedMultiplier);
        random = new Random();
    }

    private Vector2 getRandPos() {
        int x = 0, y = 0;
        random = new Random();
        while (level.map.map[x][y] && (x != level.map.xEnd && y != level.map.yEnd)) {
            x = random.nextInt(level.map.x - 2) + 1;
            y = random.nextInt(level.map.y - 2) + 1;
        }
        return new Vector2(x, y);
    }

    public void upPressed() {
        way = 1;
    }

    public void rightPressed() {
        way = 2;
    }

    public void downPressed() {
        way = 3;
    }

    public void leftPressed() {
        way = 4;
    }

    public void unPressed() {
        way = 0;
    }

    private boolean inFreeCart(Vector2 cur) {
        Vector2 vec = level.map.getCellFromCart(cur);
        return level.map.inCell((int) vec.x, (int) vec.y) && !level.map.map[(int) vec.x][(int) vec.y];
    }

    public Vector2 getCellCoordinates() {
        return level.map.getCell(realPos);
    }

    private void goUp() {
        Vector2 newPos = Map.getCoordinatesFromIs(realPos);
        newPos.y += speed;
        if (inFreeCart(newPos)) {
            realPos = Map.getIsometric(newPos);
        }
    }

    private void goDown() {
        Vector2 newPos = Map.getCoordinatesFromIs(realPos);
        newPos.y -= speed;
        if (inFreeCart(newPos)) {
            realPos = Map.getIsometric(newPos);
        }
    }


    private void goRight() {
        Vector2 newPos = Map.getCoordinatesFromIs(realPos);
        newPos.x += speed;
        if (inFreeCart(newPos)) {
            realPos = Map.getIsometric(newPos);
        }
    }


    private void goLeft() {
        Vector2 newPos = Map.getCoordinatesFromIs(realPos);
        newPos.x -= speed;
        if (inFreeCart(newPos)) {
            realPos = Map.getIsometric(newPos);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (way > 0) {
            Vector2 prevCell = curCell;
            curCell = level.map.getCell(realPos);
            if (curCell.x != prevCell.x || curCell.y != prevCell.y) {
                if (curCell.x == level.map.xEnd && curCell.y == level.map.yEnd) {
                    level.end = true;
                }
                level.map.dissipateFog((int) curCell.x, (int) curCell.y, 5);
            }
            switch (way) {
                case 1:
                    goUp();
                    batch.draw(upAnimation.getCurFrNo(), realPos.x, realPos.y);
                    break;
                case 2:
                    goRight();
                    batch.draw(rightAnimation.getCurFrNo(), realPos.x, realPos.y);
                    break;
                case 3:
                    goDown();
                    batch.draw(downAnimation.getCurFrNo(), realPos.x, realPos.y);
                    break;
                case 4:
                    goLeft();
                    batch.draw(leftAnimation.getCurFrNo(), realPos.x, realPos.y);
                    break;
            }
        } else {
            batch.draw(texture, realPos.x, realPos.y);
        }
    }

    public void setSpeed(double speed) {
        this.speed = Labyrinth.speedMultiplier * speed;
    }

    public void drawAnimation(float delta) {
        if (way > 0) {
            switch (way) {
                case 1:
                    upAnimation.update(delta);
                    break;
                case 2:
                    rightAnimation.update(delta);
                    break;
                case 3:
                    downAnimation.update(delta);
                    break;
                case 4:
                    leftAnimation.update(delta);
                    break;
            }
        }
    }
}
