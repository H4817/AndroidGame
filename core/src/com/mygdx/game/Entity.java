package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.particles.values.RectangleSpawnShapeValue;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.Texture;

import static com.mygdx.game.MyGdxGame.mapSize;

/**
 * Created by nikolaj on 1/22/17.
 */

abstract class Entity {
    Sprite sprite;
    Vector2 position;
    Vector2 velocity;
    float angle;
    boolean isDead;

    void LoadImage(String imageName) {
        sprite = new Sprite(new Texture(imageName));
        sprite.setOrigin(sprite.getWidth(), sprite.getHeight());
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

    public void SetDead(boolean dead) {
        isDead = dead;
    }

}
