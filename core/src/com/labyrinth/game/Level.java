package com.labyrinth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Level implements Screen {

    private final int x = Math.round(18 * Gdx.graphics.getWidth() / Gdx.graphics.getHeight());
    private final int y = 54;
    public byte[][] Map = new byte[x][y];
    public boolean first = true;
    Actor selectedActor;
    Labyrinth game;
    boolean end = false;
    Map map;
    byte a = 0;
    private Stage stage;
    private Camera uiCam = new OrthographicCamera();
    private Vector3 touchPoint;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private float dtsum;
    private Player player;
    private float aspectRatio = ((float) (Gdx.graphics.getHeight() * 1920)) / ((float) (Gdx.graphics.getWidth() * 1080));
    private GUI gui = new GUI();

    Level(Labyrinth game) {
        this.game = game;
        if (!Labyrinth.fogMode) {
            first = false;
        } else {
            Labyrinth.isFoggy = false;
        }
        touchPoint = new Vector3();
    }

    public void update() {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        }
    }

    @Override
    public void show() {
        batch = new SpriteBatch();


        if ((float) (Gdx.graphics.getWidth() / Gdx.graphics.getHeight()) > (float) (16 / 9))
            camera = new OrthographicCamera(1920, 1080 * aspectRatio);
        else camera = new OrthographicCamera(1920 / aspectRatio, 1080);


        if ((float) (Gdx.graphics.getWidth() / Gdx.graphics.getHeight()) > (float) (16 / 9))
            uiCam = new OrthographicCamera(1920, 1080 * aspectRatio);
        else uiCam = new OrthographicCamera(1920 / aspectRatio, 1080);

        uiCam.position.set(new Vector3(960, 540, 0));


        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
        Gdx.input.setInputProcessor(stage);

        map = new Map(this);
        //stage.addActor(map);

        player = new Player(this);
        camera.position.set(player.realPos.x, player.realPos.y, 0);

        selectedActor = player;
        InputMultiplexer in = new InputMultiplexer();


        CameraController controller = new CameraController(camera, this);
        MovementController mController = new MovementController(player, gui, this);


        in.addProcessor(controller);
        in.addProcessor(mController);
        in.addProcessor(stage);


        Gdx.input.setInputProcessor(in);
        dtsum = 0;

    }


    @Override
    public void render(float delta) {
        if (end) {
            end = false;
            dispose();
            game.setScreen(new Menu(game));

        }

        if (first && Labyrinth.fogMode) {
            if (dtsum < Labyrinth.givenTime) {
                dtsum += delta;
            } else {
                camera.position.set(player.realPos.x, player.realPos.y, 0);
                first = false;
                Labyrinth.isFoggy = true;
            }
        }
        player.drawAnimation(delta);

        GL20 gl = Gdx.gl;
        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(camera.combined);
        camera.update();
        update();

        stage.act(delta);
        stage.draw();
        map.draw(stage.getBatch(), 1);

        batch.setProjectionMatrix(uiCam.combined);
        batch.begin();
        gui.draw(batch);
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
