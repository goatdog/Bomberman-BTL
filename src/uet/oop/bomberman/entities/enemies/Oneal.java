package uet.oop.bomberman.entities.enemies;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.stillEntity.Brick;
import uet.oop.bomberman.entities.stillEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Oneal extends Enemy {
    private static final int OnealSpeed = 2;
    private boolean onealCollision = false;
    private int vx[] = {1, -1, 0, 0};
    private int vy[] = {0, 0, 1, -1};
    public Oneal(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        sprite = Sprite.oneal_right1;
    }

    @Override
    public void calculateMove(int speed) {
        int a = BombermanGame.getBomberY() / Sprite.SCALED_SIZE, b = BombermanGame.getBomberX() / Sprite.SCALED_SIZE;
        for (int i = 1; i <= BombermanGame.HEIGHT; i++) {
            for (int j = 0; j < BombermanGame.WIDTH; j++) {
                dis[i][j] = 100000;
                //System.out.print(board[i][j]);
            }
            //System.out.print("\n");
        }
        if (Math.abs(a - y / Sprite.SCALED_SIZE) <= BombermanGame.HEIGHT / 2 && Math.abs(b - x / Sprite.SCALED_SIZE) <= BombermanGame.WIDTH / 3) {
            dis[a][b] = 0;
            if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
                dfs(board, a, b);
                System.out.println("Bomber: " + a + " " + b);
                int rx = x, ry = y;
                x = getBoardX();
                y = getBoardY();
                if (x >= 0 && x + 1 < BombermanGame.WIDTH && dis[y][x] - dis[y][x + 1] == 1) {
                    dx = speed;
                    dy = 0;
                } else if (x - 1 >= 0 && x < BombermanGame.WIDTH && dis[y][x] - dis[y][x - 1] == 1) {
                    dx = -speed;
                    dy = 0;
                } else if (y >= 0 && y + 1 < BombermanGame.HEIGHT && dis[y][x] - dis[y + 1][x] == 1) {
                    dy = speed;
                    dx = 0;
                } else if (y - 1 >= 0 && y < BombermanGame.HEIGHT && dis[y][x] - dis[y - 1][x] == 1) {
                    dy = -speed;
                    dx = 0;
                } else {
                    x = rx;
                    y = ry;
                    super.calculateMove(speed / 2);
                }
                x = rx;
                y = ry;
            }
        } else {
            int scale = Sprite.SCALED_SIZE;
            super.calculateMove(speed / 2);
            return;
        }
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
        img = sprite.getFxImage();
        gc.drawImage(img, x, y);
    }

    @Override
    public void update(Scene scene) {
        setCurrentSprite();
        if (!isAlive()) {
            timeCounter++;
            dx = dy = 0;
            die(Sprite.oneal_dead, 200);
            System.out.println(timeCounter);
        }
        move();
    }
}

