package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
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
    public static Vector2 mapSize;

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
        camera.position.set(protagonist.GetSprite().getX() + protagonist.GetSprite().getWidth() / 2,
                protagonist.GetSprite().getY() + protagonist.GetSprite().getHeight() / 2, 0);

        if (camera.position.x < 960) camera.position.x = 960;
        if (camera.position.x > mapSize.x - 960) camera.position.x = mapSize.x - 960;
        if (camera.position.y < 530) camera.position.y = 530;
        if (camera.position.y > mapSize.y - 530) camera.position.y = mapSize.y - 530;


        camera.update();
    }

    private void Update() {
        UpdatePlayer();
        UpdateCamera();
        UpdateEnemies();
    }

    private void CreateObjects() {
        MapObjects mapObjects = tiledMap.getLayers().get("objects").getObjects();
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
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        CreateObjects();
        camera.position.set(protagonist.GetSprite().getX(), protagonist.GetSprite().getY(), 0);
        camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);
        MapProperties prop = tiledMap.getProperties();
        mapSize = new Vector2(prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class),
                prop.get("height", Integer.class) * prop.get("tileheight", Integer.class));
        viewport = new ExtendViewport(mapSize.x, mapSize.y, camera);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setView(camera);
        renderer.render();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        Update();
        batch.end();
        touchPad.render();
    }

/*    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(width / 2f, height / 2f, 0);
    }*/
}
