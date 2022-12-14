package uet.oop.bomberman.entities.bomb;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

public class Bomb extends Entity {
    private int timeCounter = 0;

    private boolean isAllowedGoToBomb;

    public void setAllowedGoToBomb(boolean allowedGoToBomb) {
        isAllowedGoToBomb = allowedGoToBomb;
    }

    public boolean isAllowedGoToBomb() {
        return isAllowedGoToBomb;
    }

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
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeCounter, 60).getFxImage();// load ???nh bom truoc khi no
        int tmp = 0;
        for (int i = 0; i < BombermanGame.entities.size(); i++) {
            if (!this.checkCollision(BombermanGame.entities.get(i))) {
                tmp++;
            }
        }
        if (tmp == BombermanGame.entities.size() && isAllowedGoToBomb == true) isAllowedGoToBomb = false;

    }
    public void explodeUpgrade() {
        Flame e = new Flame(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE); // tao flame khi bom no
        e.setRadius(radius); // ban kinh no;
        e.render_explosion(); // chay cac chuc nang cua flame
        alive = false; // bom bien mat
        Sound.play("bom_explode");
        for (int i = 0; i < BombermanGame.entities.size(); i++) {
            Entity tmp = BombermanGame.entities.get(i);
            if (tmp instanceof Enemy) {
                Enemy en = (Enemy) tmp;
                en.board[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE] = ' ';
            }
        }
    }
}
