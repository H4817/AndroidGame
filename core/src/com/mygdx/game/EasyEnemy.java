package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by nikolaj on 1/25/17.
 */

final class EasyEnemy extends AbstractEnemy {
    EasyEnemy(Vector2 position) {
        LoadImage("EasyEnemyGreenThrust.png");
        texture = new Texture("EasyEnemy.png");
        withoutThrust = new Sprite(texture);
        texture = new Texture("EasyEnemyGreenThrust.png");
        withThrust = new Sprite(texture);
        isAggro = false;
        velocity = new Vector2(0, 0);
        min_distance = 500;
        aggroDistance = 70;
        health = 100;
        MAX_HEALTH = 100;
        this.position = position;
        sprite.setPosition(position.x, position.y);
        ACCELERATION = 0.004f;
        DECELERATION = 0.997f;
    }
}
