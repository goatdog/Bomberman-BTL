package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    private boolean is_Move = false;
    private static final int BomberSpeed = 2;
    public Bomber(int x, int y, Sprite sprite) {
        super( x, y, sprite);
    }

    public void setKey(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()) {
                    case UP:
                    case W:
                        dy = -BomberSpeed;
                        break;
                    case DOWN:
                    case S:
                        dy = BomberSpeed;
                        break;
                    case LEFT:
                    case A:
                        dx = -BomberSpeed;
                        break;
                    case RIGHT:
                    case D:
                        dx = BomberSpeed;
                        break;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()) {
                    case UP:
                    case W:
                        dy = 0;
                        break;
                    case DOWN:
                    case S:
                        dy = 0;
                        break;
                    case LEFT:
                    case A:
                        dx = 0;
                        break;
                    case RIGHT:
                    case D:
                        dx = 0;
                        break;
                }
            }
        });
    }

    public void optimize(Entity other) {
        int this_top = y;
        int this_bottom = y + sprite.get_realHeight() * 2;
        int this_left = x;
        int this_right = x + sprite.get_realWidth() * 2;
        int other_top = other.y;
        int other_bottom = other.y + other.sprite.get_realHeight() * 2;
        int other_left = other.x;
        int other_right = other.x + other.sprite.get_realWidth() * 2;
        if (dx > 0 && this.checkCollision(other)) {
            if (other_bottom - this_top <= 2 * BomberSpeed) y += other_bottom - this_top;
            if (this_bottom - other_top <= 2 * BomberSpeed) y -= this_bottom - other_top;
        }
        if (dx < 0 && this.checkCollision(other)) {
            if (other_bottom - this_top <= 2 * BomberSpeed) y += other_bottom - this_top;
            if (this_bottom - other_top <= 2 * BomberSpeed) y -= this_bottom - other_top;
        }
        if (dy > 0 && this.checkCollision(other)) {
            if (this_right - other_left <= 2 * BomberSpeed) x -= this_right - other_left;
            if (other_right - this_left <= 2 * BomberSpeed) x += other_right - this_left;
        }
        if (dy < 0 && this.checkCollision(other)) {
            if (this_right - other_left <= 2 * BomberSpeed) x -= this_right - other_left;
            if (other_right - this_left <= 2 * BomberSpeed) x += other_right - this_left;
        }
    }

    public void move() {
        x += dx;
        y += dy;
        if (dx != 0 || dy != 0) {
            is_Move = true;
        } else {
            is_Move = false;
        }
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if (BombermanGame.stillObjects.get(i) instanceof Wall) {
                optimize(BombermanGame.stillObjects.get(i));
                if(this.checkCollision(BombermanGame.stillObjects.get(i))) {
                    x -= dx;
                    y -= dy;
                }
            }
        }
        for (int i = 0; i < BombermanGame.entities.size(); i++) {
            optimize(BombermanGame.entities.get(i));
            if (this.checkCollision(BombermanGame.entities.get(i))) {
                x -= dx;
                y -= dy;
            }
        }
    }
    @Override
    public void update(Scene scene) {
        setKey(scene);
        move();
    }
}
