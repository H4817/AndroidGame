package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by nikolaj on 1/22/17.
 */

public final class Player extends Entity {
    private Sprite heroSprite;

    Player(Texture texture, Vector2 position) {
        this.texture = texture;
        this.position = position;
        this.heroSprite = new Sprite(texture);
    }

    public Sprite GetSprite() {
        return heroSprite;
    }

    private void UpdateCoordinates() {
        if (Gdx.input.isKeyPressed(Input.Keys.A))
                this.position.x -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            this.position.x += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            this.position.y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            this.position.y += 1;
    }

    public Vector2 GetPosition() {
        return position;
    }

    public void Update() {
        UpdateCoordinates();
    }
}
