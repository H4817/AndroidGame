package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
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

    private void CreateObjects() {
        for (EllipseMapObject ellipseMapObject : mapObjects.getByType(EllipseMapObject.class)) {
            if (ellipseMapObject.getName().equals("easyEnemy")) {
                enemies.add(new EasyEnemy(new Vector2((Float) ellipseMapObject.getProperties().get("x"),
                        (Float) ellipseMapObject.getProperties().get("y"))));
            } else if (ellipseMapObject.getName().equals("player")) {
                if (protagonist == null) {
                    protagonist = new Player(new Vector2((Float) ellipseMapObject.getProperties().get("x"),
                            (Float) ellipseMapObject.getProperties().get("y")));
                } else {
                    protagonist.SetPosition(new Vector2((Float) ellipseMapObject.getProperties().get("x"),
                            (Float) ellipseMapObject.getProperties().get("y")));
                    System.out.println("not null");
                }
            }
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        touchPad = new TouchPad();
        touchPad.create();
        enemies = new ArrayList<EasyEnemy>();
        tiledMap = new TmxMapLoader().load("levels/Level_1.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        mapObjects = tiledMap.getLayers().get("objects").getObjects();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        CreateObjects();
        camera.position.set(protagonist.GetSprite().getX(), protagonist.GetSprite().getY(), 0);
        viewport = new ExtendViewport(1920, 1200, camera);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        camera.position.set(protagonist.GetSprite().getX(), protagonist.GetSprite().getY(), 0);
        camera.update();
        batch.begin();
        Update();
        batch.end();
        touchPad.render();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(width / 2f, height / 2f, 0);
    }
}
