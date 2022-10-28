package uet.oop.bomberman.entities.item;

import javafx.scene.Scene;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.awt.*;

public class Bomb_Item extends Item {

    public Bomb_Item(int x, int y, Sprite sprite) {
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
                System.out.println("max bomb: " + tmp.getMaxBomb());
                tmp.setMaxBomb(tmp.getMaxBomb() + 1);
                System.out.println("max bomb: " + tmp.getMaxBomb());
                for (int i = 0; i < BombermanGame.entities.size(); i++) {
                    Entity other = BombermanGame.entities.get(i);
                    if (other instanceof Enemy) {
                        Enemy e = (Enemy) other;
                        e.board[y / Sprite.SCALED_SIZE][x / Sprite.SCALED_SIZE] = ' ';
                    }
                }
                BombermanGame.stillObjects.remove(this);
            }
        }
    }
}
