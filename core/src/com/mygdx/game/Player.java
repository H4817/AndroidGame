package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public final class Player extends Entity {
    private float angle;
    private Vector2 speed;
    private float ACCELERATION = 0.019f;
    private float DECELERATION = 0.997f;

    Player(Vector2 position) {
        speed = new Vector2(0, 0);
        LoadImage("ship_without_thrust.png");
        texture = new Texture("ship_without_thrust.png");
        withoutThrust = new Sprite(texture);
        texture = new Texture("ship_with_thrust.png");
        withThrust = new Sprite(texture);
        this.position = position;
        sprite.setPosition(position.x, position.y);
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public float getAngle() {
        return angle;
    }

    private void UpdateCoordinates(Touchpad touchPad) {

        Vector2 v = new Vector2(touchPad.getKnobPercentX(), touchPad.getKnobPercentY());
        if (!(Math.abs(v.x) < 0.3 && Math.abs(v.y) < 0.3)) {
            sprite.set(withThrust);
            angle = v.angle();
            speed.x +=  v.x * ACCELERATION;
            speed.y +=  v.y * ACCELERATION;
            position.x += speed.x;
            position.y += speed.y;
            sprite.setX(position.x);
            sprite.setY(position.y);
            sprite.setRotation(angle);
        }
        else {
            speed.x *= DECELERATION;
            speed.y *= DECELERATION;
            position.x += speed.x;
            position.y += speed.y;
            sprite.set(withoutThrust);
            sprite.setX(position.x);
            sprite.setY(position.y);
            sprite.setRotation(angle);
        }
    }

    public void Update(TouchPad touchPad) {
        UpdateCoordinates(touchPad.GetTouchpad());
    }
}
