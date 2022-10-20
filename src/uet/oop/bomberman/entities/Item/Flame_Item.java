package uet.oop.bomberman.entities.Item;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame_Item extends Entity {

    public Flame_Item(int x, int y, Sprite sprite) {
        super( x, y, sprite);
    }

    @Override
    public void update(Scene scene) {

    }
}
