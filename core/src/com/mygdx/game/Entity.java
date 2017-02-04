package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.MyGdxGame.mapSize;

abstract class Entity {
    Sprite sprite;
    Vector2 position;
    Vector2 velocity;
    float angle;
    boolean isDead;
    static final Map<String, String> EXPLOSIONS;

    static {
        EXPLOSIONS = new HashMap<String, String>();
        EXPLOSIONS.put("Player", "images/Exp_type_B1.png");
        EXPLOSIONS.put("Missile", "images/Exp_type_A1.png");
        EXPLOSIONS.put("SmartMissile", "images/Exp_type_C1.png");
    }

    static final Map<String, String> BULLET_IMAGES;

    static
    {
        BULLET_IMAGES = new HashMap<String, String>();
        BULLET_IMAGES.put("Projectile", "images/BluePlasmaBullet.png");
        BULLET_IMAGES.put("Missile", "images/missile.png");
        BULLET_IMAGES.put("SmartMissile", "images/SmartMissile.png");
    }

    public void ReduceSpeed() {
        velocity.x *= 0.35;
        velocity.y *= 0.35;
    }

    boolean OnTheEdge() {
        return (position.x < 0) || (position.y < 0) || (position.x > mapSize.x) || (position.y > mapSize.y);
    }

    void JumpToOppositeMapSide() {
        if (position.x < 0) {
            position.x = mapSize.x;
        }
        if (position.y < 0) {
            position.y = mapSize.y;
        }
        if (position.x > mapSize.x) {
            position.x = 0;
        }
        if (position.y > mapSize.y) {
            position.y = 0;
        }
    }

    void CreateExplosion(String imageName) {
        if (imageName != null) {
            this.sprite = new Sprite(new Texture(imageName));
//            this.sprite.setRegion(128, 0, 128, 128);
//            this.sprite.setX(position.x);
//            this.sprite.setY(position.y);
            System.out.println( sprite.getHeight());
            float elapsedTime = 0.f;
            Animation animation = new Animation(0.2f, TextureRegion.split(new Texture(imageName), 128, 128)[0]);
            elapsedTime += Gdx.graphics.getDeltaTime();
            this.sprite = new Sprite(animation.getKeyFrame(elapsedTime));
            this.sprite.setX(position.x);
            this.sprite.setY(position.y);
        }
    }

    public Sprite GetSprite() {
        return sprite;
    }

    public void SetSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Vector2 GetPosition() {
        return position;
    }

    public void SetPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 GetVelocity() {
        return velocity;
    }

    public void SetVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float GetAngle() {
        return angle;
    }

    public void SetAngle(float angle) {
        this.angle = angle;
    }

    public boolean IsDead() {
        return isDead;
    }

    public void SetDead() {
        isDead = true;
    }

}
