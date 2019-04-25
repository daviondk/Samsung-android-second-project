package com.labyrinth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Button extends Actor {
    Animation button = new Animation(new TextureRegion(new Texture("startAnim.png")), 3, 0.5f);
    int posX = Gdx.graphics.getWidth() / 2 - 256;
    int posY = Gdx.graphics.getHeight() / 2 - 64;
    int w = (int) (512 * Labyrinth.scale);
    int h = (int) (128 * Labyrinth.scale);
    private Texture texture = new Texture("start.png");
    private boolean anim = false;

    public boolean inButton() {
        return Gdx.input.getX() >= posX && Gdx.input.getX() <= posX + w
                && Gdx.input.getY() >= posY && Gdx.input.getY() <= posY + h;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!anim) {
            batch.draw(texture, posX, posY, w, h);
        } else {
            batch.draw(button.getCurFrNo(), posX, posY, w, h);
        }
    }
}
