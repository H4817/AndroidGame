package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public final class Asteroid extends Entity {
    TextureRegion[] frames;

    Asteroid() {
        Texture tmpTexture = new Texture("images/strip_rock_type_D.png");
        final int FRAME_COLS = tmpTexture.getWidth() / tmpTexture.getHeight();
        final int FRAME_ROWS = 1;

        TextureRegion tmp[][] = TextureRegion.split(tmpTexture, tmpTexture.getWidth() / FRAME_COLS, tmpTexture.getHeight() / FRAME_ROWS);
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(0.1f, frames);
        velocity = new Vector2((float) (Math.random() % 8 - 4), (float) (Math.random() % 8 - 4));
        position = new Vector2();
    }

    void Update() {

        elapsedTime += Gdx.graphics.getDeltaTime();
        this.sprite = new Sprite(animation.getKeyFrame(elapsedTime, true));


        position.x += velocity.x;
        position.y += velocity.y;
        if (OnTheEdge()) {
            JumpToOppositeMapSide();
        }
        sprite.setX(position.x);
        sprite.setY(position.y);
    }

}
