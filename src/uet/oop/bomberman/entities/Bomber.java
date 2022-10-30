package uet.oop.bomberman.entities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.stillEntity.Brick;
import uet.oop.bomberman.entities.stillEntity.Portal;
import uet.oop.bomberman.entities.stillEntity.Wall;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.enemies.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.menu.GameScreen;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    private int bombRemain; // khai báo biến số bom dự trữ

    private int maxBomb;
    private int bombDelay; // thời gian delay giữa các lần đặt bom
    private boolean placeBombCommand = false; // quản lý về việc đặt bom( trả về true or false)

    private boolean isAllowedGoToBomb = false;
    // quản lý về viêệc đi xuyên qua bom trả về true or false

    private static final List<Bomb> bombs = new ArrayList<>(); // khai báo list quản lý bom
    private int radius; // biến bán kính nổ
    private boolean is_Move = false;
    public static int lives; // số mạng
    private int BomberSpeed = 2;
    protected int dx = 0, dy = 0;

    private int timeAfterDie = 0; // thời gian sau khi chết

    public Bomber(int x, int y, Sprite sprite) {
        super( x, y, sprite);
        setMaxBomb(1);
        setRadius(1);
        lives = BombermanGame.lives;
    }

    public static List<Bomb> getBombs() { // trả về list bomb
        return bombs;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public int getMaxBomb() {
        return maxBomb;
    }

    public void setMaxBomb(int maxBomb) {
        this.maxBomb = maxBomb;
    }

    public void setBomberSpeed(int bomberSpeed) {
        BomberSpeed = bomberSpeed;
    }

    public int getBomberSpeed() {
        return BomberSpeed;
    }

    public void setBombRemain(int bombRemain) {
        this.bombRemain = bombRemain;
    }

    public void setKey(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()) {
                    case UP:
                    case W:
                        dy = -BomberSpeed;
                        break;
                    case DOWN:
                    case S:
                        dy = BomberSpeed;
                        break;
                    case LEFT:
                    case A:
                        dx = -BomberSpeed;
                        break;
                    case RIGHT:
                    case D:
                        dx = BomberSpeed;
                        break;
                    case SPACE:
                        placeBombCommand = true;
                        break;
                    case P:
                        GameScreen.isRunning = !GameScreen.isRunning;
                        break;
                    case M:
                        BombermanGame.hasSound = !BombermanGame.hasSound;
                        if (BombermanGame.hasSound == false) Sound.stop("Sound");
                        else Sound.play("Sound");
                        break;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {
                switch(keyEvent.getCode()) {
                    case UP:
                    case W:
                        dy = 0;
                        break;
                    case DOWN:
                    case S:
                        dy = 0;
                        break;
                    case LEFT:
                    case A:
                        dx = 0;
                        break;
                    case RIGHT:
                    case D:
                        dx = 0;
                        break;
                    case SPACE:
                        placeBombCommand = false;
                        break;
                }
            }
        });
    }

    public void die() {

        if (timeAfterDie == 20) {
            BombermanGame.loseLives = true;
            BombermanGame.lives--;// kể từ sau khi bom nổ 20 đơn vị thời gian thì mạng giảm đi 1
            BombermanGame.score = 0;
            if (BombermanGame.lives > 0) Sound.play("bomberman_die");
        }
        if (timeAfterDie <= 45) {
            sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2,
                    Sprite.player_dead3, timeAfterDie, 20);
            img = sprite.getFxImage();
        } else {
            BombermanGame.entities.remove(this);
            if (lives >= 0) BombermanGame.entities.add(new Bomber(1, 2, Sprite.player_right));
        }
    }

    public void placeBomb() {
        System.out.println("bomb remain: " + bombRemain);
        if (bombDelay == 0) {
            int xB = (int) Math.round((x) / (double) Sprite.SCALED_SIZE); // toa do bom
            int yB = (int) Math.round((y) / (double) Sprite.SCALED_SIZE); // toa do bom
            for (Bomb bomb : bombs) { // duyet list bombs
                if (xB * Sprite.SCALED_SIZE == bomb.getX() && yB * Sprite.SCALED_SIZE == bomb.getY()) return;
            }
            for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
                if (BombermanGame.stillObjects.get(i) instanceof Portal) {
                    Portal p = (Portal) BombermanGame.stillObjects.get(i);
                    if (p.x / Sprite.SCALED_SIZE == xB && p.y / Sprite.SCALED_SIZE == yB) return;
                }
            }
            Bomb tmp = new Bomb(xB, yB, Sprite.bomb, radius);
            if (bombs.size() < maxBomb) {
                bombs.add(tmp); // tao bom va add vao list bom
                Sound.play("set_bomb"); // âm đặt bom
                tmp.setAllowedGoToBomb(true); // xuyen qua bom tra ve true
            }
            bombDelay = 20;
            //if (bombRemain > 0) bombRemain--; //tru di luong bom du tru sua khi da dat
        }
    }

    public void optimize(Entity other) {
        int this_top = y;
        int this_bottom = y + sprite.get_realHeight() * 2;
        int this_left = x;
        int this_right = x + sprite.get_realWidth() * 2;
        int other_top = other.y;
        int other_bottom = other.y + other.sprite.get_realHeight() * 2;
        int other_left = other.x;
        int other_right = other.x + other.sprite.get_realWidth() * 2;
        if (dx > 0 && this.checkCollision(other)) {
            if (other_bottom - this_top <= 4 * BomberSpeed) y += other_bottom - this_top;
            if (this_bottom - other_top <= 4 * BomberSpeed) y -= this_bottom - other_top;
        }
        if (dx < 0 && this.checkCollision(other)) {
            if (other_bottom - this_top <= 4 * BomberSpeed) y += other_bottom - this_top;
            if (this_bottom - other_top <= 4 * BomberSpeed) y -= this_bottom - other_top;
        }
        if (dy > 0 && this.checkCollision(other)) {
            if (this_right - other_left <= 4 * BomberSpeed) x -= this_right - other_left;
            if (other_right - this_left <= 4 * BomberSpeed) x += other_right - this_left;
        }
        if (dy < 0 && this.checkCollision(other)) {
            if (this_right - other_left <= 4 * BomberSpeed) x -= this_right - other_left;
            if (other_right - this_left <= 4 * BomberSpeed) x += other_right - this_left;
        }
    }

    public void move() {
        x += dx;
        y += dy;
        if (dx != 0 || dy != 0) {
            is_Move = true;
        } else {
            is_Move = false;
        }
        for (int i = 0; i < BombermanGame.stillObjects.size(); i++) {
            if (BombermanGame.stillObjects.get(i) instanceof Brick || BombermanGame.stillObjects.get(i) instanceof Wall) {
                optimize(BombermanGame.stillObjects.get(i));
                if(this.checkCollision(BombermanGame.stillObjects.get(i))) {
                    x -= dx;
                    y -= dy;
                }
            } else if(BombermanGame.stillObjects.get(i) instanceof Portal) {
                if(BombermanGame.entities.size() == 1 && BombermanGame.entities.get(0) instanceof Bomber
                        && this.checkCollision(BombermanGame.stillObjects.get(i)) && !((Portal) BombermanGame.stillObjects.get(i)).hasBrick()) {
                    if (BombermanGame.loseLives == true && BombermanGame.level < 4) Sound.play("level_up");
                    BombermanGame.check = true;
                    //check = true;
                }
            }
        }
        for (int i = 0; i < BombermanGame.entities.size(); i++) {
            if (BombermanGame.entities.get(i) instanceof Enemy) {
                optimize(BombermanGame.entities.get(i));
                if (this.checkCollision(BombermanGame.entities.get(i))) {
                    x -= dx;
                    y -= dy;
                    if (BombermanGame.entities.get(i) instanceof Enemy) {
                        setAlive(false);
                    }
                }
            }
        }
        int cnt = 0;
        for (int i = 0; i < bombs.size(); i++) {
             if (this.checkCollision(bombs.get(i)) && bombs.get(i).isAllowedGoToBomb() == false) {
                optimize(bombs.get(i));
                if (this.checkCollision(bombs.get(i))) {
                    x -= dx;
                    y -= dy;
                }
            }
        }
    }

    public void setCurrentSprite() {
        if (dx > 0) {
            this.sprite = Sprite.player_right;
            if (is_Move) this.sprite = Sprite.movingSprite(Sprite.player_right_1,
                    Sprite.player_right_2, selfcount, 20);
        }
        if (dx < 0) {
            this.sprite = Sprite.player_left;
            if (is_Move) this.sprite = Sprite.movingSprite(Sprite.player_left_1,
                    Sprite.player_left_2, selfcount, 20);
        }
        if (dy > 0) {
            this.sprite = Sprite.player_down;
            if (is_Move) this.sprite = Sprite.movingSprite(Sprite.player_down_1,
                    Sprite.player_down_2, selfcount, 20);
        }
        if (dy < 0) {
            this.sprite = Sprite.player_up;
            if (is_Move) this.sprite = Sprite.movingSprite(Sprite.player_up_1,
                    Sprite.player_up_2, selfcount, 20);
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
        if (bombDelay > 0) bombDelay--;
        setKey(scene);
        count();
        if (!isAlive()) {
            timeAfterDie++;
            die();
            dx = dy = 0;
        }
        move();
        for (int i = 0; i < BombermanGame.entities.size(); i++) {
            if (BombermanGame.entities.get(i) instanceof Enemy) {
                Enemy tmp = (Enemy) BombermanGame.entities.get(i);
                //tmp.changepos();
            }
        }
        if (placeBombCommand) {
            placeBomb();
        }
        for (int i = 0; i < bombs.size(); i++) { // duyệt cái list của bomb
            Bomb bomb = bombs.get(i);
            if (!bomb.isAlive()) {
                bombs.remove(bomb);
            }
        }
    }
}
