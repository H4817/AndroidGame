package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    //    private Texture backgroundTexture;
    private Player protagonist;
    private TouchPad touchPad;
    private ArrayList<EasyEnemy> enemies;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Viewport viewport;
    private MapObjects mapObjects;

    private void UpdateEnemies() {
        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).Update(protagonist.GetPosition());
            enemies.get(i).GetSprite().draw(batch);
            if (enemies.get(i).isDead) {
                enemies.remove(i);
            }
        }
    }

    private void UpdatePlayer() {
        protagonist.Update(touchPad);
        protagonist.GetSprite().draw(batch);
    }

    private void UpdateCamera() {

    }

    private void Update() {
        UpdatePlayer();
        UpdateCamera();
        UpdateEnemies();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
//        backgroundTexture = new Texture("images/background.jpg");
        protagonist = new Player(new Vector2(1300, 944));
        touchPad = new TouchPad();
        touchPad.create();
        enemies = new ArrayList<EasyEnemy>();
        enemies.add(new EasyEnemy(new Vector2(100, 400)));
        tiledMap = new TmxMapLoader().load("levels/Level_1.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(1980, 1050, camera);
        mapObjects = tiledMap.getLayers().get("objects").getObjects();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.set(protagonist.GetPosition().x + protagonist.GetSprite().getWidth() / 2, protagonist.GetPosition().y + protagonist.GetSprite().getHeight() / 2, 0);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        batch.begin();
        Update();
        batch.end();
        touchPad.render();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
//        camera.viewportHeight = height / 10;
//        camera.viewportWidth = width / 10;
    }
}
