package uet.oop.bomberman.entities.enemies;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Entity {
    private static final int BalloomSpeed = 1;

    public Balloom(int x, int y, Sprite sprite) {
        super( x, y, sprite);
        sprite = Sprite.balloom_left1;
    }

    public void move() {
        calculateMove();
        if (direction == 0) {
            dx = BalloomSpeed;
            dy = 0;
        }
        else if(direction == 1) {
            dx = -BalloomSpeed;
            dy = 0;
        }
        else if(direction == 2) {
            dy = BalloomSpeed;
            dx = 0;
        }
        else if(direction == 3) {
            dy = -BalloomSpeed;
            dx = 0;
        }
        System.out.println(dx + " " + dy);
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
        for (int i = 0; i < BombermanGame.entities.size(); i++) {
            if (BombermanGame.entities.get(i) instanceof Brick
                    && this.checkCollision(BombermanGame.entities.get(i))) {
                x -= dx;
                y -= dy;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        move();
        Image cur = sprite.getFxImage();
        gc.drawImage(cur, x, y);
    }

    @Override
    public void update(Scene scene) {}
}
