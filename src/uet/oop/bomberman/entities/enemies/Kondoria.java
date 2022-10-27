package uet.oop.bomberman.entities.enemies;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.stillEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Kondoria extends Enemy {
    private static final int KondoriaSpeed = 1;
    private int lives = 2;
    private boolean canLoseLives = true;

    private int loseLivesDelay = 20;

    private boolean kondoriaCollision = false;

    public Kondoria(int x, int y, Sprite sprite) {
        super( x, y, sprite);
        sprite = Sprite.kondoria_left1;
    }

    public void move() {
        calculateMove(KondoriaSpeed);
        x += dx;
        y += dy;
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if (BombermanGame.stillObjects.get(i) instanceof Wall) {
                if(this.checkCollision(BombermanGame.stillObjects.get(i))) {
                    x -= dx;
                    y -= dy;
                }
            }
        }
        List<Bomb> bombs = Bomber.getBombs();
        for (int i = 0; i < bombs.size(); i++) {
            if (this.checkCollision(bombs.get(i)) && bombs.get(i).isAllowedGoToBomb() == false) {
                x -= dx;
                y -= dy;
            }
        }
    }

    public void setCurrentSprite() {
        if (dx > 0 || dy > 0) {
            this.sprite = Sprite.movingSprite(Sprite.kondoria_right1,
                    Sprite.kondoria_right2, Sprite.kondoria_right3, selfcount, 20);
        }
        if (dx < 0 || dy < 0) {
            this.sprite = Sprite.movingSprite(Sprite.kondoria_left1,
                    Sprite.kondoria_left2, Sprite.kondoria_left3, selfcount, 20);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        img = sprite.getFxImage();
        gc.drawImage(img, x, y);
    }

    @Override
    public void update(Scene scene) {
        setCurrentSprite();
        if (!isAlive()) {
            if (lives == 0) {
                timeCounter++;
                dx = dy = 0;
            }
            die(Sprite.kondoria_dead, 400);
        } else {
            if (loseLivesDelay > 0) {
                loseLivesDelay--;
            }
        }
        move();
    }

    @Override
    public void die(Sprite sprite, int score) {
        super.die(sprite, score);
        if (timeCounter == 0) {
            if (loseLivesDelay < 20 && lives > 0) {
                lives--;
                loseLivesDelay = 25;
            }
            if (lives > 0) this.setAlive(true);
            System.out.println("lives: " + lives);
        }
    }
}
