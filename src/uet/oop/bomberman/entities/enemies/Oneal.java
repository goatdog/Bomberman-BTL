package uet.oop.bomberman.entities.enemies;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Oneal extends Enemy {
    private static final int OnealSpeed = 2;
    private boolean onealCollision = false;
    private int dis[][] = new int[BombermanGame.HEIGHT][BombermanGame.WIDTH];
    private int vx[] = {1, -1, 0, 0};
    private int vy[] = {0, 0, 1, -1};
    public Oneal(int x, int y, Sprite sprite) {
        super( x, y, sprite);
        sprite = Sprite.oneal_right1;
    }

    public void dfs(char board[][], int i, int j) {
        if (i == getBoardY() && j == getBoardX()) return;
        for (int k = 0; k < 4; k++) {
            if (i + vy[k] < 0 || j + vx[k] < 0 || i + vy[k] >= BombermanGame.HEIGHT || j + vx[k] >= BombermanGame.WIDTH) continue;
            if (board[i + vy[k]][j + vx[k]] != '*' && board[i + vy[k]][j + vx[k]] != '#') {
                if (dis[i + vy[k]][j + vx[k]] > dis[i][j] + 1) {
                    dis[i + vy[k]][j + vx[k]] = dis[i][j] + 1;
                    dfs(board, i + vy[k], j + vx[k]);
                }
            }
        }
    }

    @Override
    public void calculateMove(int speed) {
        int a = 0, b = 0;
        for (int i = 0; i < BombermanGame.HEIGHT; i++) {
            for (int j = 0; j < BombermanGame.WIDTH; j++) {
                dis[i][j] = 100000;
                if (board[i][j] == 'p'){
                    a = i;
                    b = j;
                }
                //System.out.print(board[i][j]);
            }
            //System.out.print("\n");
        }
        if (Math.abs(a - y / Sprite.SCALED_SIZE) <= BombermanGame.HEIGHT / 2 && Math.abs(b - x / Sprite.SCALED_SIZE) <= BombermanGame.WIDTH / 3) {
            dis[a][b] = 0;
            if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
                dfs(board, a, b);
                int rx = x, ry = y;
                x = getBoardX();
                y = getBoardY();
                System.out.println(dis[y][x] + " " + dis[y][x + 1] + " " + dis[y][x - 1] + " " + dis[y + 1][x] + " " + dis[y - 1][x]);
                System.out.println(board[y][x] + " " + y + " " + x);
                if (x >= 0 && x + 1 < BombermanGame.WIDTH && dis[y][x] - dis[y][x + 1] == 1) {
                    dx = speed;
                    dy = 0;
                    System.out.println("right");
                } else if (x - 1 >= 0 && x < BombermanGame.WIDTH && dis[y][x] - dis[y][x - 1] == 1) {
                    dx = -speed;
                    dy = 0;
                    System.out.println("left");
                } else if (y >= 0 && y + 1 < BombermanGame.HEIGHT && dis[y][x] - dis[y + 1][x] == 1) {
                    dy = speed;
                    dx = 0;
                    System.out.println("down");
                } else if (y - 1 >= 0 && y < BombermanGame.HEIGHT && dis[y][x] - dis[y - 1][x] == 1) {
                    dy = -speed;
                    dx = 0;
                    System.out.println("up");
                }
                x = rx;
                y = ry;
            }
        } else {
            int scale = Sprite.SCALED_SIZE;
            super.calculateMove(speed / 2);
            return;
        }
        System.out.println(dx + " " + dy);
    }

    public void move() {
        this.calculateMove(OnealSpeed);
//        if (!onealCollision) this.calculateMove(OnealSpeed);
//        else {
//            super.calculateMove(OnealSpeed);
//            onealCollision = false;
//        }
        x += dx;
        y += dy;
        changepos('2');
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if (BombermanGame.stillObjects.get(i) instanceof Wall) {
                if(this.checkCollision(BombermanGame.stillObjects.get(i))) {
                    x -= dx;
                    y -= dy;
                    changepos('2');
                }
            }
        }
        for (int i = 0; i < BombermanGame.entities.size(); i++) {
            if (BombermanGame.entities.get(i) instanceof Brick
                    && this.checkCollision(BombermanGame.entities.get(i))) {
                x -= dx;
                y -= dy;
                changepos('2');
            }
            /*if (BombermanGame.entities.get(i) instanceof Enemy
                    && this.checkCollision(BombermanGame.entities.get(i))) {
                while (x % 32 != 0 || y % 32 != 0) {
                    x -= dx;
                    y -= dy;
                }
            }*/
        }
    }

    public void setCurrentSprite() {
        if (dx > 0 || dy > 0) {
            this.sprite = Sprite.movingSprite(Sprite.oneal_right1,
                    Sprite.oneal_right2, Sprite.oneal_right3, selfcount, 20);
        }
        if (dx < 0 || dy < 0) {
            this.sprite = Sprite.movingSprite(Sprite.oneal_left1,
                    Sprite.oneal_left2, Sprite.oneal_left3, selfcount, 20);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        setCurrentSprite();
        Image cur = sprite.getFxImage();
        gc.drawImage(cur, x, y);
    }

    @Override
    public void update(Scene scene) {
        move();
    }
}

