package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.MyGdxGame.batch;
import static com.mygdx.game.MyGdxGame.entities;


abstract class AbstractEnemy extends Entity {
    int health;
    int MAX_HEALTH;
    int shield;
    double ACCELERATION;
    double DECELERATION;
    boolean isAggro;
    int aggroDistance;
    int min_distance;
    Sprite withoutThrust;
    Sprite withThrust;
    //    AbstractEnemy enemy;
    protected long startTime = 0;
    public static ArrayList<AbstractEnemy> enemiesList = new ArrayList<AbstractEnemy>();
    private static final Map<String, ArrayList> ENEMIES_WEAPON;

    static {
        ENEMIES_WEAPON = new HashMap<String, ArrayList>();
        ENEMIES_WEAPON.put("EasyEnemy", new ArrayList<String>() {{
            add("Projectile");
        }});
        ENEMIES_WEAPON.put("MediumEnemy", new ArrayList<String>() {{
            add("Projectile");
            add("Missile");
        }});
        ENEMIES_WEAPON.put("DifficultEnemy", new ArrayList<String>() {{
            add("SmartMissile");
        }});
//        ENEMIES_WEAPON.put("MediumEnemy", "Missile");
//        ENEMIES_WEAPON.put("EasyEnemy", "Projectile");
    }
//    ArrayList<AbstractWeapon> weapon;

    private void CheckAggro(double distance) {
        if (!isAggro && (distance < aggroDistance || health != MAX_HEALTH)) {
            isAggro = true;
        }
    }

    private void FlyTowardPlayer(Vector2 playerCoordinates) {
        double distance = Math.sqrt((playerCoordinates.x - position.x) * (playerCoordinates.x - position.x) +
                (playerCoordinates.y - position.y) * (playerCoordinates.y - position.y));
        CheckAggro(distance);
        if (isAggro) {
            angle = (float) ((Math.atan2(playerCoordinates.y - position.y, playerCoordinates.x - position.x)) * 180 / Math.PI);
            if (distance > min_distance) {
                sprite.set(withThrust);
                velocity.x += (ACCELERATION * (playerCoordinates.x - position.x) / distance);
                velocity.y += (ACCELERATION * (playerCoordinates.y - position.y) / distance);
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
            sprite.setRotation(angle);
            sprite.setX(position.x);
            sprite.setY(position.y);
        }
    }

    void MakeShot() {
        if (isAggro) {
            if (TimeUtils.timeSinceMillis(startTime) > 1500) {
                for (Object object : ENEMIES_WEAPON.get(this.getClass().getSimpleName())) {
                    new ConcreteWeapon(object.toString(),
                            new Sprite(new Texture(BULLET_IMAGES.get(object.toString()))),
                            new Vector2(position), angle);
                }
                startTime = TimeUtils.millis();
            }
        }
    }

    void Draw() {
        this.GetSprite().draw(batch);
    }

    void Update(TouchPad touchPad, Vector2 playerPosition) {
        FlyTowardPlayer(playerPosition);
        MakeShot();
        Draw();
        //Shoot
    }

}
