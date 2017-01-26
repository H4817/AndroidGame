package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Player protagonist;
    private EasyEnemy easyEnemy;
    private TouchPad touchPad;

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("background.jpg");
        protagonist = new Player(new Vector2(1300, 944));
        touchPad = new TouchPad();
        touchPad.create();
        easyEnemy = new EasyEnemy(new Vector2(100, 400));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        protagonist.Update(touchPad);
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        protagonist.GetSprite().draw(batch);
        easyEnemy.GetSprite().draw(batch);
        batch.end();
        touchPad.render();
        easyEnemy.Update(protagonist);
    }
}
