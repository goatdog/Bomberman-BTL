package uet.oop.bomberman.entities.StillEntity;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private int timeCounter = 0;

    public Brick(int x, int y, Sprite sprite) {
        super( x, y, sprite);
    }

    @Override
    public void update(Scene scene) {
        if(!isAlive()) { // đại diện cho tốc độ load ảnh (ở đây cụ thể là load lúc biến mất)
            if(timeCounter < 45 ) {
                timeCounter ++;
                img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, timeCounter, 45).getFxImage();
            } else
                BombermanGame.stillObjects.remove(this); // xoa brick
        }
    }
}