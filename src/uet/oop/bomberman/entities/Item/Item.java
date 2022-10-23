package uet.oop.bomberman.entities.Item;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.StillEntity.Brick;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Item extends Entity {
    public Item(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    public boolean hasBrick() {
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if (BombermanGame.stillObjects.get(i) instanceof Brick) {
                Entity tmp = BombermanGame.stillObjects.get(i);
                if (tmp.getX() == x && tmp.getY() == y) return true;
            }
        }
        return false;
    }

}
