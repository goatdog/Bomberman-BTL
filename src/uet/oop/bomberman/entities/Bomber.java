package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {
    private int dx = 0, dy = 0;
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

    public void move() {
        x += dx;
        y += dy;
        if (dx != 0 || dy != 0) {
            is_Move = true;
        } else {
            is_Move = false;
        }
    }
    @Override
    public void update(Scene scene) {
        setKey(scene);
        move();
    }
}
