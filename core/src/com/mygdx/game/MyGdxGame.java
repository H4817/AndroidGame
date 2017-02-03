package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Map;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player protagonist;
    private TouchPad touchPad;
    private ArrayList<AbstractEnemy> enemies;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
//    private Viewport viewport;

    public static Vector2 mapSize;
    public static ArrayList<Weapon> bullets;

    static final ArrayList<String> NAME_OF_ENEMIES;

    static {
        NAME_OF_ENEMIES = new ArrayList<String>();
        NAME_OF_ENEMIES.add("EasyEnemy");
        NAME_OF_ENEMIES.add("MediumEnemy");
        NAME_OF_ENEMIES.add("DifficultEnemy");
    }


    private void UpdateEnemies() {
        for (int i = 0; i < enemies.size(); ++i) {
            enemies.get(i).enemy.Update(protagonist.GetPosition());
            enemies.get(i).enemy.GetSprite().draw(batch);
            if (enemies.get(i).enemy.IsDead()) {
                enemies.remove(i);
                //TODO: ++score;
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

    private void UpdateBullets() {
        for (int i = 0; i < bullets.size(); ++i) {
            bullets.get(i).Update();
            bullets.get(i).GetSprite().draw(batch);
            if (bullets.get(i).IsDead()) {
                bullets.remove(i);
            }
        }
    }

    private void Update() {
        UpdatePlayer();
        UpdateCamera();
        UpdateEnemies();
        UpdateBullets();
    }

    private void CreateObjects() {
        MapObjects mapObjects = tiledMap.getLayers().get("objects").getObjects();
        for (EllipseMapObject ellipseMapObject : mapObjects.getByType(EllipseMapObject.class)) {
            if (NAME_OF_ENEMIES.contains(ellipseMapObject.getName())) {
                enemies.add(new ConcreteEnemy(ellipseMapObject.getName(),
                        new Vector2((Float) ellipseMapObject.getProperties().get("x"),
                        (Float) ellipseMapObject.getProperties().get("y"))));
            } else if (ellipseMapObject.getName().equals("Player")) {
                if (protagonist == null) {
                    protagonist = new Player(new Vector2((Float) ellipseMapObject.getProperties().get("x"),
                            (Float) ellipseMapObject.getProperties().get("y")));
                } else {
                    protagonist.SetPosition(new Vector2((Float) ellipseMapObject.getProperties().get("x"),
                            (Float) ellipseMapObject.getProperties().get("y")));
                }
            }
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        touchPad = new TouchPad();
        touchPad.create();
        enemies = new ArrayList<AbstractEnemy>();
        tiledMap = new TmxMapLoader().load("levels/Level_1.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        CreateObjects();
        camera.position.set(protagonist.GetSprite().getX(), protagonist.GetSprite().getY(), 0);
        camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);
        MapProperties prop = tiledMap.getProperties();
        mapSize = new Vector2(prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class),
                prop.get("height", Integer.class) * prop.get("tileheight", Integer.class));
//        viewport = new ExtendViewport(mapSize.x, mapSize.y, camera);

        bullets = new ArrayList<Weapon>();
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

}
