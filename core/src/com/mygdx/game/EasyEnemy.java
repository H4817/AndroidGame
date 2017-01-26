package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by nikolaj on 1/25/17.
 */

public final class EasyEnemy extends AbstractEnemy {
    EasyEnemy(Vector2 position) {
        LoadImage("EasyEnemyGreenThrust.png");
        isAggro = false;
        velocity = new Vector2(0, 0);
        min_distance = 550;
        aggroDistance = 700;
        health = 100;
        MAX_HEALTH = 100;
        this.position = position;
        sprite.setPosition(position.x, position.y);
        ACCELERATION = 0.007;
        DECELERATION = 0.001;
    }
}
