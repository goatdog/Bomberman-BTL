package uet.oop.bomberman.entities.enemies;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.StillEntity.Brick;
import uet.oop.bomberman.entities.StillEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Balloom extends Enemy {
    private static final int BalloomSpeed = 1;
    private boolean balloomCollision = false;

    public Balloom(int x, int y, Sprite sprite) {
        super( x, y, sprite);
        sprite = Sprite.balloom_left1;
    }

    public void move() {
        calculateMove(BalloomSpeed);
        x += dx;
        y += dy;
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if (BombermanGame.stillObjects.get(i) instanceof Brick || BombermanGame.stillObjects.get(i) instanceof Wall) {
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
            this.sprite = Sprite.movingSprite(Sprite.balloom_right1,
                    Sprite.balloom_right2, Sprite.balloom_right3, selfcount, 20);
        }
        if (dx < 0 || dy < 0) {
            this.sprite = Sprite.movingSprite(Sprite.balloom_left1,
                    Sprite.balloom_left2, Sprite.balloom_left3, selfcount, 20);
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
            timeCounter++;
            dx = dy = 0;
            die(Sprite.balloom_dead, 100);
            System.out.println(timeCounter);
        }
        move();
    }
}
