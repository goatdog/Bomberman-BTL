package uet.oop.bomberman.entities.stillEntity;

import javafx.scene.Scene;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Entity {

    public Portal(int x, int y, Sprite sprite) {
        super( x, y, sprite);
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

    @Override
    public void update(Scene scene) {

    }
}

