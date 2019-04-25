package com.labyrinth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GUI {
    int posX = 0, posY = 0;
    int startPosX = 0, startPosY;
    boolean draw = false;
    private Texture front = new Texture("front.png");
    private Texture back = new Texture("back.png");

    public void draw(SpriteBatch batch) {
        if (draw) {
            batch.draw(back, startPosX - Gdx.graphics.getWidth() / 2, -startPosY + Gdx.graphics.getHeight() / 2, 100, 100);
            batch.draw(front, posX - Gdx.graphics.getWidth() / 2, -posY + Gdx.graphics.getHeight() / 2, 100, 100);
        }
    }
}
