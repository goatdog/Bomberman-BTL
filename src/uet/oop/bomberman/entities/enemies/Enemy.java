package uet.oop.bomberman.entities.enemies;

import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public abstract class Enemy extends Entity {
    public char[][] board = BombermanGame.getCurmap();
    protected int dis[][] = new int[BombermanGame.HEIGHT + 1][BombermanGame.WIDTH + 1];
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

    public void changepos() {
        int scale = Sprite.SCALED_SIZE;
        /*if ((x) % scale == 0 && (y) % scale == 0) {
            if (board[y / scale][x / scale] == ' ') {
                board[y / scale][x / scale] = symbol;
                for (int k = 0; k < 8; k++) {
                    if (y / scale + spvy[k] >= 1 && y / scale + spvy[k] <= BombermanGame.HEIGHT && x / scale + spvx[k] >= 0 && x / scale + spvx[k] < BombermanGame.WIDTH) {
                        if (board[y / scale + spvy[k]][x / scale + spvx[k]] == symbol) {
                            board[y / scale + spvy[k]][x / scale + spvx[k]] = ' ';
                        }
                    }
                }
            }
        }*/
        int BomberX = BombermanGame.getBomberX();
        int BomberY = BombermanGame.getBomberY();
        if (board[BomberY / scale][BomberX / scale] == ' ') {
            board[BomberY / scale][BomberX / scale] = 'p';
            for (int k = 0; k < 8; k++) {
                if (BomberY / scale + spvy[k] >= 1 && BomberY / scale + spvy[k] <= BombermanGame.HEIGHT && BomberX / scale + spvx[k] >= 0 && BomberX / scale + spvx[k] < BombermanGame.WIDTH) {
                    if (board[BomberY / scale + spvy[k]][BomberX / scale + spvx[k]] == 'p') {
                        board[BomberY / scale + spvy[k]][BomberX / scale + spvx[k]] = ' ';
                    }
                }
            }
        }
    }

    public void dfs(char board[][], int i, int j) {
        if (i == getBoardY() && j == getBoardX()) return;
        for (int k = 0; k < 4; k++) {
            if (i + vy[k] < 1 || j + vx[k] < 0 || i + vy[k] > BombermanGame.HEIGHT || j + vx[k] >= BombermanGame.WIDTH) continue;
            if (board[i + vy[k]][j + vx[k]] != '*' && board[i + vy[k]][j + vx[k]] != '#' && board[i + vy[k]][j + vx[k]] != 'B'
                    && board[i + vy[k]][j + vx[k]] != 'b' && board[i + vy[k]][j + vx[k]] != 'f' && board[i + vy[k]][j + vx[k]] != 's'
                    && board[i + vy[k]][j + vx[k]] != 'x') {
                if (dis[i + vy[k]][j + vx[k]] > dis[i][j] + 1) {
                    dis[i + vy[k]][j + vx[k]] = dis[i][j] + 1;
                    dfs(board, i + vy[k], j + vx[k]);
                }
            }
        }
    }

    public void bfs(char board[][], int i, int j) {
        Queue<Pair<Integer,Integer>> q = new LinkedList<>();
        q.add(new Pair<Integer,Integer>(i, j));
        while(!q.isEmpty()) {
            int n = q.peek().getKey();
            int m = q.peek().getValue();
            if (n == getBoardY() && m == getBoardX()) return;
            q.remove();
            for (int k = 0; k < 4; k++) {
                if (n + vy[k] < 1 || m + vx[k] < 0 || n + vy[k] > BombermanGame.HEIGHT || m + vx[k] >= BombermanGame.WIDTH) continue;
                if (board[n + vy[k]][m + vx[k]] != '*' && board[n + vy[k]][m + vx[k]] != '#' && board[n + vy[k]][m + vx[k]] != 'B'
                        && board[n + vy[k]][m + vx[k]] != 'b' && board[n + vy[k]][m + vx[k]] != 'f' && board[n + vy[k]][m + vx[k]] != 's'
                        && board[n + vy[k]][m + vx[k]] != 'x') {
                    if (dis[n + vy[k]][m + vx[k]] > dis[n][m] + 1) {
                        dis[n + vy[k]][m + vx[k]] = dis[n][m] + 1;
                        q.add(new Pair<>(n + vy[k], m + vx[k]));
                    }
                }
            }
        }
    }

    public void die(Sprite _sprite, int score) {
        if (timeCounter == 5) Sound.play("eat_item");
        if (timeCounter <= 45) {
            sprite = _sprite;
        } else if (timeCounter <= 75) {
            sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, timeCounter, 20);
        } else {
            if (timeCounter == 76) {
                BombermanGame.score += score;
            }
            BombermanGame.entities.remove(this);
        }
    }
}
