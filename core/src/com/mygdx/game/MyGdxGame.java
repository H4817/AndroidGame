package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Player protagonist;
    private TouchPad touchPad;
    private ArrayList<EasyEnemy> enemies;

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

    private void Update() {
        UpdatePlayer();
        UpdateEnemies();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("background.jpg");
        protagonist = new Player(new Vector2(1300, 944));
        touchPad = new TouchPad();
        touchPad.create();
        enemies = new ArrayList<EasyEnemy>();
        enemies.add(new EasyEnemy(new Vector2(100, 400)));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        Update();
        batch.end();
        touchPad.render();
    }
}
