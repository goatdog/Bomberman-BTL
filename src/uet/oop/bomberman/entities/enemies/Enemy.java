package uet.oop.bomberman.entities.enemies;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public abstract class Enemy extends Entity {
    protected int direction;
    protected int dx = 0, dy = 0;
    public Enemy(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }
    public void calculateMove(int speed) {
            if ((x % 32 == 0 && y % 32 == 0)) {
                Random rand = new Random();
                direction = rand.nextInt(4);
            }
            if (direction == 0) {
                dx = speed;
                dy = 0;
            } else if (direction == 1) {
                dx = -speed;
                dy = 0;
            } else if (direction == 2) {
                dy = speed;
                dx = 0;
            } else if (direction == 3) {
                dy = -speed;
                dx = 0;
            }
    }
}
