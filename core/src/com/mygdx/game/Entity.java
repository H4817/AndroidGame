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

/**
 * Created by nikolaj on 1/22/17.
 */

abstract class Entity {
    Texture texture;
    Sprite sprite;
    Sprite withoutThrust;
    Sprite withThrust;
    Vector2 position;

    void LoadImage(String imageName) {
        texture = new Texture(imageName);
        sprite = new Sprite(texture);
        sprite.setOrigin(sprite.getWidth(), sprite.getHeight());
    }

//    Sprite LoadImage(String imageName, Texture another_texture, Sprite another_sprite) {
//        another_texture = new Texture(imageName);
//        another_sprite = new Sprite(another_texture);
//        another_sprite.setOrigin(another_sprite.getWidth(), another_sprite.getHeight());
//        return another_sprite;
//    }
//

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

}
