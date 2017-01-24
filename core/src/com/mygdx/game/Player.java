package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public final class Player extends Entity {
    private Sprite heroSprite;
    private float angle;
    private static final float speed = 5;

    Player(Texture texture, Vector2 position) {
        this.texture = texture;
        this.position = position;
        heroSprite = new Sprite(texture);
        heroSprite.setOrigin(heroSprite.getWidth(), heroSprite.getHeight());
    }

    public static float getSpeed() {
        return speed;
    }

    public Sprite GetSprite() {
        return heroSprite;
    }

    public float getAngle() {
        return angle;
    }

    private void UpdateCoordinates(Touchpad touchPad) {

        Vector2 v = new Vector2(touchPad.getKnobPercentX(), touchPad.getKnobPercentY());
        if (!(Math.abs(v.x) < 0.3 && Math.abs(v.y) < 0.3)) {
            angle = v.angle();
        }
        heroSprite.setX(heroSprite.getX() + touchPad.getKnobPercentX() * speed);
        heroSprite.setY(heroSprite.getY() + touchPad.getKnobPercentY() * speed);
        heroSprite.setRotation(angle);
    }

    public Vector2 GetPosition() {
        return position;
    }

    public void Update(TouchPad touchPad) {
        UpdateCoordinates(touchPad.GetTouchpad());
    }
}
