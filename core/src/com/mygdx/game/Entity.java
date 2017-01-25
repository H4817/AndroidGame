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
    Vector2 position;

    void LoadImage(String imageName) {
        texture = new Texture(imageName);
        sprite = new Sprite(texture);
        sprite.setOrigin(sprite.getWidth(), sprite.getHeight());
    }
}
