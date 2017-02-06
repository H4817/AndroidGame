package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

class ConcreteWeapon extends AbstractWeapon {

    ConcreteWeapon(String name, Sprite sprite, Vector2 position, float angle) {

        if (name.equals("Projectile")) {
            listOfBullets.add(new Projectile(sprite, position, angle));
        } else if (name.equals("Missile")) {
            listOfBullets.add(new Missile(sprite, position, angle));
        } else if (name.equals("SmartMissile")) {
            listOfBullets.add(new SmartMissile(sprite, position, angle));
        }

    }

    ConcreteWeapon() {
    }

}

final class Projectile extends ConcreteWeapon {
    Projectile(Sprite sprite, Vector2 position, float angle) {
        this.playerPos = new Vector2(position);
        this.sprite = sprite;
        this.sprite.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        this.position = new Vector2(position.x + (float) (50 * Math.cos(angle * Math.PI / 180)),
                position.y + (float) (50 * Math.sin(angle * Math.PI / 180)));

        this.velocity = new Vector2();
        this.angle = angle;
        this.speed = 20;
        this.distance = 1700;
    }
}

class Missile extends ConcreteWeapon {
    Missile(Sprite sprite, Vector2 position, float angle) {
        this.sprite = sprite;
        this.sprite.setOrigin(this.sprite.getWidth() / 2, this.sprite.getHeight() / 2);
        this.playerPos = new Vector2(position);
//        this.position = new Vector2(position.x + (float)(40 * Math.cos(angle * Math.PI / 180)),
//                position.y + (float)(50 * Math.sin(angle * Math.PI / 180)));
//        this.position = new Vector2(position.rotate((float)(angle * Math.PI / 180)));

        Vector2 direction = new Vector2((float) Math.cos(angle * 0.017453f) * speed,
                (float) Math.sin(angle * 0.017453f) * speed);

//        this.position = new Vector2(position).add(direction.scl(1000));
        this.velocity = new Vector2();
//        this.position = new Vector2(
//                (float)(position.x * Math.cos(angle)) - (float)(position.y * Math.sin(angle)),
//                (float)(position.x * Math.sin(angle)) + (float)(position.y * Math.cos(angle)));
        this.position = new Vector2(position);
        this.angle = angle;
        this.speed = 10;
        this.distance = 1000;
    }

}

final class SmartMissile extends Missile {
    SmartMissile(Sprite sprite, Vector2 position, float angle) {
        super(sprite, position, angle);
        this.position = position;
        velocity = new Vector2(5, 5);
    }

//    @Override
//    public void Update() {
//
//    }
}
