package com.labyrinth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class Menu implements Screen {
    Labyrinth game;
    private OrthographicCamera camera;
    private boolean anim = false;
    private float dt = 0;
    private Stage menu;
    private Button play;
    private Main main;
    private Batch batch;

    private Slider slider1;
    private Slider slider2;
    private Slider slider3;
    private Slider slider4;
    private Slider slider5;

    Menu(Labyrinth game) {

        batch = new SpriteBatch();

        float aspectRatio = ((float) (Gdx.graphics.getHeight() * 1920)) / ((float) (Gdx.graphics.getWidth() * 1080));
        if ((float) (Gdx.graphics.getWidth() / Gdx.graphics.getHeight()) > (float) (16 / 9))
            camera = new OrthographicCamera(1920, 1080 * aspectRatio);
        else camera = new OrthographicCamera(1920 / aspectRatio, 1080);

        this.game = game;

        Skin skin = new Skin(Gdx.files.internal("default/skin/uiskin.json"));


        menu = new Stage();


        play = new Button();
        main = new Main();
        menu.addActor(main);
        menu.addActor(play);


        slider1 = new Slider(1f, 10f, 0.5f, false, skin);
        slider1.setBounds(Gdx.graphics.getWidth() / 2 - 256 * Labyrinth.scale, Gdx.graphics.getHeight() / 2 - (64 + 110) * Labyrinth.scale, 512 * Labyrinth.scale, 128 * Labyrinth.scale);

        slider2 = new Slider(5f, 100f, 1f, false, skin);
        slider2.setBounds(Gdx.graphics.getWidth() / 2 - 256 * Labyrinth.scale, Gdx.graphics.getHeight() / 2 - (64 + 120 + 64) * Labyrinth.scale, 512 * Labyrinth.scale, 128 * Labyrinth.scale);

        slider3 = new Slider(5f, 100f, 1f, false, skin);
        slider3.setBounds(Gdx.graphics.getWidth() / 2 - 256 * Labyrinth.scale, Gdx.graphics.getHeight() / 2 - (64 + 130 + 128) * Labyrinth.scale, 512 * Labyrinth.scale, 128 * Labyrinth.scale);

        slider4 = new Slider(0f, 1f, 1f, false, skin);
        slider4.setBounds(Gdx.graphics.getWidth() / 2 - 256 * Labyrinth.scale, Gdx.graphics.getHeight() / 2 - (64 + 140 + 192) * Labyrinth.scale, 512 * Labyrinth.scale, 128 * Labyrinth.scale);

        slider5 = new Slider(0f, 30f, 0.5f, false, skin);
        slider5.setBounds(Gdx.graphics.getWidth() / 2 - 256 * Labyrinth.scale, Gdx.graphics.getHeight() / 2 - (64 + 155 + 256) * Labyrinth.scale, 512 * Labyrinth.scale, 128 * Labyrinth.scale);

        menu.addActor(slider1);
        menu.addActor(slider2);
        menu.addActor(slider3);
        menu.addActor(slider4);
        menu.addActor(slider5);

        main.posX = (Gdx.graphics.getWidth() / 2 - main.w / 2);
        main.posY = (Gdx.graphics.getHeight() / 2 - main.h / 2);

        play.posX = (Gdx.graphics.getWidth() / 2 - play.w / 2);
        play.posY = (Gdx.graphics.getHeight() / 2 - play.h / 2);

        InputMultiplexer in = new InputMultiplexer();
        in.addProcessor(menu);
        Gdx.input.setInputProcessor(in);
    }

    @Override
    public void show() {
    }

    private void getButton() {
        if (Gdx.input.justTouched()) {
            if (play.inButton()) {
                anim = true;
            }
        }
    }

    @Override
    public void render(float delta) {
        if (dt >= 0.5f) {
            Labyrinth.speedMultiplier = slider1.getValue();
            Labyrinth.n = (int) slider2.getValue();
            Labyrinth.m = (int) slider3.getValue();
            Labyrinth.isFoggy = (slider4.getValue() <= 0);
            Labyrinth.fogMode = (slider4.getValue() <= 0);
            Labyrinth.givenTime = slider5.getValue();

            dispose();
            game.setScreen(new Level(game));
        }
        if (anim) {
            dt += delta;
            play.button.update(delta);
        } else {
            getButton();
        }

        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();
        menu.act(delta);
        menu.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
