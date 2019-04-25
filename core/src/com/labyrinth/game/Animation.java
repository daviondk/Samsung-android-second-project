package com.labyrinth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> txtrgns;
    private float maxdt;
    private float dtsum;
    private float timeOut;
    private int size;
    private int curFrNo;


    Animation(TextureRegion region, int size, float timeOut) {
        if (Gdx.graphics.getHeight() > Gdx.graphics.getWidth()) {
            txtrgns = new Array<TextureRegion>();
            int frameWidth = region.getRegionWidth() / size;
            for (int i = 0; i < size; i++) {
                txtrgns.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
            }
            this.size = size;
            this.timeOut = timeOut;
            maxdt = timeOut / size;
            curFrNo = 0;
        } else {
            txtrgns = new Array<TextureRegion>();
            int imgH = region.getRegionHeight() / size;
            for (int i = 0; i < size; i++) {
                txtrgns.add(new TextureRegion(region, 0, i * imgH, region.getRegionWidth(), imgH));
            }
            this.size = size;
            this.timeOut = timeOut;
            maxdt = timeOut / size;
            curFrNo = 0;

        }
    }

    public void update(float dt) {
        dtsum += dt;
        if (dtsum > maxdt) {
            curFrNo++;
            dtsum = 0;
        }
        if (curFrNo >= size)
            curFrNo = 0;
    }

    public TextureRegion getCurFrNo() {
        return txtrgns.get(curFrNo);
    }
}