package uet.oop.bomberman.entities.Bomb;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Entity {
    private int timeCounter = 0;

    public void setTimeCounter(int timeCounter) {
        this.timeCounter = timeCounter;
    }

    public int getTimeCounter() {
        return timeCounter;
    }

    int radius;
    public Bomb(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        //setLayer(2);// chi so va cham cua bomb
        for (int i = 0; i < BombermanGame.entities.size(); i++) {
            Entity tmp = BombermanGame.entities.get(i);
            if (tmp instanceof Enemy) {
                Enemy e = (Enemy) tmp;
                e.board[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE] = 'B';
            }
        }
    }

    public Bomb(int xUnit, int yUnit, Sprite sprite, int radius) {
        super(xUnit, yUnit, sprite);
        //setLayer(2);// chi so va cham cua bomb
        this.radius = radius; // cai chi so ban kinh no
        for (int i = 0; i < BombermanGame.entities.size(); i++) {
            Entity tmp = BombermanGame.entities.get(i);
            if (tmp instanceof Enemy) {
                Enemy e = (Enemy) tmp;
                e.board[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE] = 'B';
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    @Override
    public void update(Scene scene) {
        timeCounter++;
        if(timeCounter == 120) {// thoi gian bom se no sau khi cai dat
            explodeUpgrade();
        }
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeCounter, 60).getFxImage();// load ảnh bom truoc khi no


    }
    public void explodeUpgrade() {
        Flame e = new Flame(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE); // tao flame khi bom no
        e.setRadius(radius); // ban kinh no;
        e.render_explosion(); // chay cac chuc nang cua flame
        alive = false; // bom bien mat
        for (int i = 0; i < BombermanGame.entities.size(); i++) {
            Entity tmp = BombermanGame.entities.get(i);
            if (tmp instanceof Enemy) {
                Enemy en = (Enemy) tmp;
                en.board[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE] = ' ';
            }
        }
    }
}
