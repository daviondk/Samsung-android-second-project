package com.labyrinth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.MathUtils.clamp;

public class MovementController implements InputProcessor {

    public GUI gui;

    private int dtx;
    private int dty;
    private int pointer = Integer.MIN_VALUE;
    private Player player;
    private int maxDelta = Gdx.graphics.getWidth() / 12;
    private Level level;

    MovementController(Player player, GUI gui, Level level) {
        this.level = level;
        this.player = player;
        this.gui = gui;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    private void stopCharacterMovement() {
        pointer = Integer.MIN_VALUE;
        gui.draw = false;
        player.unPressed();
    }

    private void initCharacterMovement(int screenX, int screenY, int pointer) {
        this.pointer = pointer;
        dtx = screenX;
        dty = screenY;
        gui.startPosX = screenX;
        gui.startPosY = screenY;
        gui.posX = screenX;
        gui.posY = screenY;
        gui.draw = true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer == this.pointer) {
            stopCharacterMovement();
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int mid = Gdx.graphics.getWidth() / 2;


        if (!level.first && screenX <= mid && this.pointer == Integer.MIN_VALUE) {
            initCharacterMovement(screenX, screenY, pointer);
        }

        return false;
    }

    private void moveCharacter(int screenX, int screenY) {
        int dx;
        int dy;
        dx = screenX - dtx;
        dy = screenY - dty;
        dx *= -1;
        dy *= -1;
        double speed = clamp(Math.sqrt(dx * dx + dy * dy) * .05, 0, 1);
        if (dx > 0) {
            if (dy > 0) {
                player.upPressed();
            } else {
                player.leftPressed();
            }
        } else {
            if (dy > 0) {
                player.rightPressed();
            } else {
                player.downPressed();
            }
        }
        player.setSpeed(speed);
    }

    private int vecLength(int startPosX, int startPosY, int endPosX, int endPosY) {
        return (int) Math.sqrt((endPosX - startPosX) * (endPosX - startPosX)
                + (endPosY - startPosY) * (endPosY - startPosY));
    }

    private Vector2 getPos(int startPosX, int startPosY, int curPosX, int curPosY) {
        int curLength = vecLength(startPosX, startPosY, curPosX, curPosY);
        if (curLength <= maxDelta) {
            return new Vector2(curPosX, curPosY);
        } else {
            return new Vector2((int) (startPosX + (curPosX - startPosX) * ((float) maxDelta / (float) curLength)),
                    (int) (startPosY + (curPosY - startPosY) * ((float) maxDelta / (float) curLength)));
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer == this.pointer) {
            Vector2 guiPos = getPos(gui.startPosX, gui.startPosY,
                    screenX, screenY);
            gui.posX = (int) guiPos.x;
            gui.posY = (int) guiPos.y;
            moveCharacter(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
