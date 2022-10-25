package uet.oop.bomberman.entities.Item;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Sound.Sound;
import java.awt.*;

public class Flame_Item extends Item {

    public Flame_Item(int x, int y, Sprite sprite) {
        super( x, y, sprite);
    }

    @Override
    public void update(Scene scene) {
        if (!hasBrick()) {
            Rectangle r1 = this.getBounds();
            Rectangle r2 = new Rectangle(0, 0, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
            Bomber tmp = new Bomber(0, 0, Sprite.player_right);
            for (int i = 0; i < BombermanGame.entities.size(); i++) {
                if (BombermanGame.entities.get(i) instanceof Bomber) {
                    r2 = BombermanGame.entities.get(i).getBounds();
                    tmp = (Bomber) BombermanGame.entities.get(i);
                }
            }
            if (r1.intersects(r2)) {
                Sound.play("eat_item");
                tmp.setRadius(tmp.getRadius() + 1);
                BombermanGame.stillObjects.remove(this);
            }
        }
    }
}
