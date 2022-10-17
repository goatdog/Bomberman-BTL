package uet.oop.bomberman.entities.enemies;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public abstract class Enemy extends Entity {
    public char[][] board = BombermanGame.getCurmap();
    protected int direction;
    protected int dx = 0, dy = 0;
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
                for (int k = 0; k < 4; k++) {
                    if (y / scale + vy[k] >= 0 && y / scale + vy[k] < BombermanGame.HEIGHT && x / scale + vx[k] >= 0 && x / scale + vx[k] < BombermanGame.WIDTH) {
                        if (board[y / scale + vy[k]][x / scale + vx[k]] == symbol) {
                            board[y / scale + vy[k]][x / scale + vx[k]] = ' ';
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
}
