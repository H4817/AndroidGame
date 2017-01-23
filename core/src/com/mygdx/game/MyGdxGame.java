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
    SpriteBatch batch;
    Texture backgroundTexture;
    Texture playerTexture;
    Player protagonist;
    TouchPad touchPad;

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("background.jpg");
        playerTexture = new Texture("8888.png");
        protagonist = new Player(playerTexture, new Vector2(10, 10));
        touchPad = new TouchPad();
        touchPad.create();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        protagonist.Update();
        batch.begin();
        batch.draw(backgroundTexture, 0, 0);
        batch.draw(protagonist.GetSprite(), protagonist.GetPosition().x, protagonist.GetPosition().y);
        batch.end();
        touchPad.render();
    }
}
