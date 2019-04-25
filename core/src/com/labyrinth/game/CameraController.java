package com.labyrinth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class CameraController implements InputProcessor {

    private int dtx;
    private int dty;
    private OrthographicCamera camera;
    private int pointer = Integer.MIN_VALUE;
    private Level level;

    CameraController(OrthographicCamera camera, Level level) {
        this.level = level;
        this.camera = camera;
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

    private void stopCamMovement() {
        pointer = Integer.MIN_VALUE;
    }

    private void startCamMovement(int screenX, int screenY, int pointer) {
        this.pointer = pointer;
        dtx = screenX;
        dty = screenY;
    }

    private void moveCamera(int screenX, int screenY) {
        int dx = dtx - screenX;
        int dy = dty - screenY;
        camera.position.add(dx, -dy, 0);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer == this.pointer) {
            stopCamMovement();
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int mid = Gdx.graphics.getWidth() / 2;


        if (level.first || (screenX > mid && this.pointer == Integer.MIN_VALUE)) {
            startCamMovement(screenX, screenY, pointer);
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer == this.pointer) {
            moveCamera(screenX, screenY);
            dtx = screenX;
            dty = screenY;
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
