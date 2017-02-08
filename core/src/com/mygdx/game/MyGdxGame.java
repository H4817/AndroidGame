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

import static com.mygdx.game.AbstractEnemy.enemiesList;
import static com.mygdx.game.AbstractWeapon.listOfBullets;

public class MyGdxGame extends ApplicationAdapter {
    static SpriteBatch batch;
    private Player protagonist;
    private TouchPad touchPad;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public static Vector2 mapSize;
    Asteroid asteroid;
    public static ArrayList<Entity> entities;

    static final ArrayList<String> NAME_OF_ENEMIES;

    static {
        NAME_OF_ENEMIES = new ArrayList<String>();
        NAME_OF_ENEMIES.add("EasyEnemy");
        NAME_OF_ENEMIES.add("MediumEnemy");
        NAME_OF_ENEMIES.add("DifficultEnemy");
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

        for (int i = 0; i < entities.size(); ++i) {
            entities.get(i).Update(touchPad, protagonist.GetPosition());
            if (entities.get(i).IsDead()) {
                entities.remove(i);
            }
//            if (entities.get(i).GetSprite().getBoundingRectangle().overlaps(protagonist.GetSprite().getBoundingRectangle())) {
//                System.out.println("CollideWithPlayer");
//            }
        }

        for (int i = 0; i < listOfBullets.size(); ++i) {
            listOfBullets.get(i).Update(touchPad, protagonist.GetPosition());

            for (int j = 0; j < entities.size(); j++) {
                if (listOfBullets.get(i).GetSprite().getBoundingRectangle().overlaps(entities.get(j).GetSprite().getBoundingRectangle())
                        && entities.get(j).getClass().getSimpleName().equals("Player")) {
                    if (listOfBullets.get(i).getClass().getSimpleName().equals("Projectile")) {
                        listOfBullets.get(i).SetDead();
                    } else {
                        listOfBullets.get(i).CreateExplosion();
                    }
                    protagonist.setHealth(protagonist.getHealth() - listOfBullets.get(i).getDamage());
                }
            }

            if (listOfBullets.get(i).IsDead()) {
                listOfBullets.remove(i);
            }
        }
        UpdateCamera();
    }

    private void CreateObjects() {
        MapObjects mapObjects = tiledMap.getLayers().get("objects").getObjects();
        for (EllipseMapObject ellipseMapObject : mapObjects.getByType(EllipseMapObject.class)) {
            if (NAME_OF_ENEMIES.contains(ellipseMapObject.getName())) {
                new ConcreteEnemy(ellipseMapObject.getName(),
                        new Vector2((Float) ellipseMapObject.getProperties().get("x"),
                                (Float) ellipseMapObject.getProperties().get("y")));
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
        entities.add(protagonist);
        entities.addAll(enemiesList);
    }

    @Override
    public void create() {
        entities = new ArrayList<Entity>();
        batch = new SpriteBatch();
        touchPad = new TouchPad();
        touchPad.create();
        tiledMap = new TmxMapLoader().load("levels/Level_4.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        CreateObjects();
        camera.position.set(protagonist.GetSprite().getX(), protagonist.GetSprite().getY(), 0);
        camera.translate(camera.viewportWidth / 2, camera.viewportHeight / 2);
        MapProperties prop = tiledMap.getProperties();
        mapSize = new Vector2(prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class),
                prop.get("height", Integer.class) * prop.get("tileheight", Integer.class));

        asteroid = new Asteroid();
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
