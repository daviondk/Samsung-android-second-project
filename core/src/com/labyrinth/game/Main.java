package com.labyrinth.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Main extends Actor {
    int posX = 0;
    int posY = 0;
    int w = (int) (1920 * Labyrinth.scale);
    int h = (int) (1080 * Labyrinth.scale);
    private Texture texture = new Texture("main.png");

    Main() {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, posX, posY, w, h);
    }
}
