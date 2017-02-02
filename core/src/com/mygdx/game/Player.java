package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import static com.mygdx.game.MyGdxGame.bullets;

final class Player extends Entity {
    private float ACCELERATION = 0.04f;
    private float DECELERATION = 0.997f;
    private Sprite withoutThrust;
    private Sprite withThrust;

    Player(Vector2 position) {
        velocity = new Vector2(0, 0);
        withoutThrust = new Sprite(new Texture("images/ship_without_thrust.png"));
        withThrust = new Sprite(new Texture("images/ship_with_thrust.png"));
        sprite = new Sprite(withoutThrust);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        this.position = position;
        sprite.setPosition(position.x, position.y);
    }

    private void UpdateCoordinates(Touchpad touchPad) {

        Vector2 v = new Vector2(touchPad.getKnobPercentX(), touchPad.getKnobPercentY());
        if (!(Math.abs(v.x) < 0.3 && Math.abs(v.y) < 0.3)) {
            sprite.set(withThrust);
            angle = v.angle();
            velocity.x += v.x * ACCELERATION;
            velocity.y += v.y * ACCELERATION;
        } else {
            velocity.x *= DECELERATION;
            velocity.y *= DECELERATION;
            sprite.set(withoutThrust);
        }
        position.x += velocity.x;
        position.y += velocity.y;
        if (OnTheEdge()) {
            JumpToOppositeMapSide();
            ReduceSpeed();
        }
        sprite.setX(position.x);
        sprite.setY(position.y);
        sprite.setRotation(angle);
    }

    void Update(TouchPad touchPad) {
        UpdateCoordinates(touchPad.GetTouchpad());
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            bullets.add(new Missile(new Sprite(new Texture("images/missile.png")),
                    new Vector2(position), angle));
        }
    }
}
