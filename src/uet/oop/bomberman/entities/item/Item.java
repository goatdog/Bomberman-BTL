package uet.oop.bomberman.entities.item;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.stillEntity.Brick;
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
