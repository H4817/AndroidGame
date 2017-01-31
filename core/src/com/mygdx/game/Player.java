package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

final class Player extends Entity {
    private float angle;
    private Vector2 speed;
    private float ACCELERATION = 0.019f;
    private float DECELERATION = 0.997f;
    private Sprite withoutThrust;
    private Sprite withThrust;

    Player(Vector2 position) {
        speed = new Vector2(0, 0);
        withoutThrust = new Sprite(new Texture("images/ship_without_thrust.png"));
        withThrust = new Sprite(new Texture("images/ship_with_thrust1.png"));
        sprite = new Sprite(withoutThrust);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
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
        }
        else {
            speed.x *= DECELERATION;
            speed.y *= DECELERATION;
            sprite.set(withoutThrust);
        }
        position.x += speed.x;
        position.y += speed.y;
        JumpToOppositeMapSide();
        sprite.setX(position.x);
        sprite.setY(position.y);
        sprite.setRotation(angle);
    }

    void Update(TouchPad touchPad) {
        UpdateCoordinates(touchPad.GetTouchpad());
    }
}
