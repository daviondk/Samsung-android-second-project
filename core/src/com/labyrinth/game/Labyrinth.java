package com.labyrinth.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Labyrinth extends Game {
    public static double speedMultiplier = 5;
    public static int n = 20;
    public static int m = 20;
    public static boolean isFoggy = true;
    public static boolean fogMode = true;
    public static float givenTime = 0;
    public static float scale;

    @Override
    public void create() {
        if (Gdx.graphics.getWidth() / Gdx.graphics.getHeight() > 16 / 9) {
            scale = (float) Gdx.graphics.getWidth() / 1920;
        } else {
            scale = (float) Gdx.graphics.getHeight() / 1080;
        }
        setScreen(new Menu(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }
}
