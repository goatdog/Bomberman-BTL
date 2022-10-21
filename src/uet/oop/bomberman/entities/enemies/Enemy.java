package uet.oop.bomberman.entities.enemies;

import javafx.scene.Scene;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public abstract class Enemy extends Entity {
    public char[][] board = BombermanGame.getCurmap();
    protected int direction;
    protected int dx = 0, dy = 0;

    protected int timeCounter = 0;

    public int getBoardX() {
        if (x % Sprite.SCALED_SIZE == 0) return x / Sprite.SCALED_SIZE;
        else return x / Sprite.SCALED_SIZE + 1;
    }

    public int getBoardY() {
        if (y % Sprite.SCALED_SIZE == 0) return y / Sprite.SCALED_SIZE;
        else return y / Sprite.SCALED_SIZE + 1;
    }

    public Enemy(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    public void calculateMove(int speed) {
        if ((x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0)) {
            Random rand = new Random();
            direction = rand.nextInt(4);
            if (direction == 0) {
                dx = speed;
                dy = 0;
            } else if (direction == 1) {
                dx = -speed;
                dy = 0;
            } else if (direction == 2) {
                dy = speed;
                dx = 0;
            } else if (direction == 3) {
                dy = -speed;
                dx = 0;
            }
        }
    }

    public void changepos(char symbol) {
        int scale = Sprite.SCALED_SIZE;
        if ((x) % scale == 0 && (y) % scale == 0) {
            if (board[y / scale][x / scale] == ' ') {
                board[y / scale][x / scale] = symbol;
                for (int k = 0; k < 8; k++) {
                    if (y / scale + spvy[k] >= 0 && y / scale + spvy[k] < BombermanGame.HEIGHT && x / scale + spvx[k] >= 0 && x / scale + spvx[k] < BombermanGame.WIDTH) {
                        if (board[y / scale + spvy[k]][x / scale + spvx[k]] == symbol) {
                            board[y / scale + spvy[k]][x / scale + spvx[k]] = ' ';
                        }
                    }
                }
            }
        }
        int BomberX = BombermanGame.getBomberX();
        int BomberY = BombermanGame.getBomberY();
        if (board[BomberY / scale][BomberX / scale] == ' ') {
            board[BomberY / scale][BomberX / scale] = 'p';
            for (int k = 0; k < 8; k++) {
                if (BomberY / scale + spvy[k] >= 0 && BomberY / scale + spvy[k] < BombermanGame.HEIGHT && BomberX / scale + spvx[k] >= 0 && BomberX / scale + spvx[k] < BombermanGame.WIDTH) {
                    if (board[BomberY / scale + spvy[k]][BomberX / scale + spvx[k]] == 'p') {
                        board[BomberY / scale + spvy[k]][BomberX / scale + spvx[k]] = ' ';
                    }
                }
            }
        }
    }

    public void die(Sprite _sprite) {
        if (timeCounter <= 45) {
            sprite = _sprite;
        } else if (timeCounter <= 75) {
            sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, timeCounter, 20);
        } else {
            BombermanGame.entities.remove(this);
        }
    }
}
