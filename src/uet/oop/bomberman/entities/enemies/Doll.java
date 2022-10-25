package uet.oop.bomberman.entities.enemies;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.StillEntity.Brick;
import uet.oop.bomberman.entities.StillEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Doll extends Enemy {
    private static final int DollSpeed = 2;
    private boolean dollCollision = false;

    public Doll(int x, int y, Sprite sprite) {
        super( x, y, sprite);
        sprite = Sprite.doll_left1;
    }

    @Override
    public void calculateMove(int speed) {
        int BomberX = BombermanGame.getBomberX();
        int BomberY = BombermanGame.getBomberY();
        if (BomberX / Sprite.SCALED_SIZE != x / Sprite.SCALED_SIZE
                && BomberY / Sprite.SCALED_SIZE != y / Sprite.SCALED_SIZE) {
            super.calculateMove(speed);
        } else {
            int a = 0, b = 0;
            for (int i = 1; i <= BombermanGame.HEIGHT; i++) {
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
            dis[a][b] = 0;
            if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
                dfs(board, a, b);
                int rx = x, ry = y;
                x = getBoardX();
                y = getBoardY();
                if (x >= 0 && x + 1 < BombermanGame.WIDTH && dis[y][x] - dis[y][x + 1] == 1) {
                    dx = speed + 1;
                    dy = 0;
                } else if (x - 1 >= 0 && x < BombermanGame.WIDTH && dis[y][x] - dis[y][x - 1] == 1) {
                    dx = -speed - 1;
                    dy = 0;
                } else if (y >= 0 && y + 1 < BombermanGame.HEIGHT && dis[y][x] - dis[y + 1][x] == 1) {
                    dy = speed + 1;
                    dx = 0;
                } else if (y - 1 >= 0 && y < BombermanGame.HEIGHT && dis[y][x] - dis[y - 1][x] == 1) {
                    dy = -speed - 1;
                    dx = 0;
                } else {
                    x = rx;
                    y = ry;
                    super.calculateMove(speed);
                }
                x = rx;
                y = ry;
            }
        }
    }

    public void move() {
        calculateMove(DollSpeed);
        x += dx;
        y += dy;
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if (BombermanGame.stillObjects.get(i) instanceof Brick || BombermanGame.stillObjects.get(i) instanceof Wall) {
                if(this.checkCollision(BombermanGame.stillObjects.get(i))) {
                    x -= dx;
                    y -= dy;
                }
            }
        }
        List<Bomb> bombs = Bomber.getBombs();
        for (int i = 0; i < bombs.size(); i++) {
            if (this.checkCollision(bombs.get(i)) && bombs.get(i).isAllowedGoToBomb() == false) {
                x -= dx;
                y -= dy;
            }
        }
    }

    public void setCurrentSprite() {
        if (dx > 0 || dy > 0) {
            this.sprite = Sprite.movingSprite(Sprite.doll_right1,
                    Sprite.doll_right2, Sprite.doll_right3, selfcount, 20);
        }
        if (dx < 0 || dy < 0) {
            this.sprite = Sprite.movingSprite(Sprite.doll_left1,
                    Sprite.doll_left2, Sprite.doll_left3, selfcount, 20);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        img = sprite.getFxImage();
        gc.drawImage(img, x, y);
    }

    @Override
    public void update(Scene scene) {
        setCurrentSprite();
        if (!isAlive()) {
            timeCounter++;
            dx = dy = 0;
            die(Sprite.doll_dead, 100);
            System.out.println(timeCounter);
        }
        move();
    }
}
