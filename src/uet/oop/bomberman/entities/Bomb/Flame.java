package uet.oop.bomberman.entities.Bomb;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.StillEntity.Brick;
import uet.oop.bomberman.entities.StillEntity.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public class Flame extends Entity {
    private int left;
    private int right;
    private int top;
    private int down;
    private int radius;
    private int size = Sprite.SCALED_SIZE;
    private int direction; // lựa chọn
    private int time = 0; // thoi gian flame ton tai

    public Flame(int x, int y, Sprite _sprite, int direction) {
        super(x, y, _sprite);
        this.direction = direction;
    }

    public Flame(int x, int y, Sprite _sprite) {
        super(x, y, _sprite);
        this.radius = 1;
    }

    public Flame(int x, int y) {
        super(x, y, Sprite.explosion_horizontal);
    }

    public void setRadius(int radius) {
        this.radius = radius;// cài bán kính flame
    }

    @Override
    public void update(Scene scene) { // phương thức kết thúc vụ nổ
        if (time < 20) {// thoi gian flame keo dai
            time++;
            setImg();
        } else
            BombermanGame.flameList.remove(this);

    }

    public void render_explosion() { //chạy hết các phương thúc nổ -> hiển thị vụ nổ lên game

        Right();
        Left();
        Top();
        Down();
        create_explosion();
    }
    // phương thức tạo vụ nổ (về mặt Hình Ảnh

    private void create_explosion() {
        BombermanGame.flameList.add(new Flame(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.bomb_exploded, 0));
        for (int i = 0; i < right; i++) {
            Flame e = new Flame(x + size * (i + 1), y);
            e.x = e.x / Sprite.SCALED_SIZE;
            e.y = e.y / Sprite.SCALED_SIZE;
            if (i == right - 1) {
                e.sprite = Sprite.explosion_horizontal_right_last;
                e.img = e.sprite.getFxImage();
                e.direction = 2;
            } else {
                e.sprite = Sprite.explosion_horizontal;
                e.img = e.sprite.getFxImage();
                e.direction = 1;
            }
            BombermanGame.flameList.add(e); // list quản lý flame sẽ thêm "phần tử" flame mới này vào
        }

        for (int i = 0; i < left; i++) {
            Flame e = new Flame(x - size * (i + 1), y);
            e.x = e.x / Sprite.SCALED_SIZE;
            e.y = e.y / Sprite.SCALED_SIZE;
            if (i == left - 1) {
                e.sprite = Sprite.explosion_horizontal_left_last;
                e.img = e.sprite.getFxImage();
                e.direction = 3;
            } else {
                e.sprite = Sprite.explosion_horizontal;
                e.img = e.sprite.getFxImage();
                e.direction = 1;
            }
            BombermanGame.flameList.add(e); // list quản lý flame sẽ thêm "phần tử" flame mới này vào
        }

        for (int i = 0; i < top; i++) {
            Flame e = new Flame(x, y - size * (i + 1));
            e.x = e.x / Sprite.SCALED_SIZE;
            e.y = e.y / Sprite.SCALED_SIZE;
            if (i == top - 1) {
                e.sprite = Sprite.explosion_vertical_top_last;
                e.img = e.sprite.getFxImage();
                e.direction = 5;
            } else {
                e.sprite = Sprite.explosion_vertical;
                e.img = e.sprite.getFxImage();
                e.direction = 4;
            }
            BombermanGame.flameList.add(e); // list quản lý flame sẽ thêm "phần tử" flame mới này vào
        }

        for (int i = 0; i < down; i++) {
            Flame e = new Flame(x, y + size * (i + 1));
            e.x = e.x / Sprite.SCALED_SIZE;
            e.y = e.y / Sprite.SCALED_SIZE;
            if (i == right - 1) {
                e.sprite = Sprite.explosion_vertical_down_last;
                e.img = e.sprite.getFxImage();
                e.direction = 6;
            } else {
                e.sprite = Sprite.explosion_vertical;
                e.img = e.sprite.getFxImage();
                e.direction = 4;
            }
            BombermanGame.flameList.add(e); // list quản lý flame sẽ thêm "phần tử" flame mới này vào
        }
    }

    // các phương thức nổ theo từng hướng (về mặt chức năng bị chặn bởi tường và xuyên qua brick)
    private void Right() {
        for (int i = 0; i < radius; i++) {
            Rectangle ex_right = new Rectangle(x + size * (i + 1), y, size, size); // tạo bound(hit box) cho flame sử dụng để tham gia các va chạm
            if (collisionType(ex_right) instanceof Wall) {
                right = i; // neu flame va cham tuong thi flame bi chan
                return;
            } else if(collisionType(ex_right) instanceof Brick) {
                right = i + 1;// neu flame va cham brick thi khong bi chan
                return;
            }
            right = i + 1;
        }
    }
    // cac truong hop duoi tuong tu right
    private void Top() {
        for (int i = 0; i < radius; i++) {
            Rectangle ex_top = new Rectangle(x , y - size * (i + 1), size, size); // tạo bound(hit box) cho flame sử dụng để tham gia các va chạm
            if (collisionType(ex_top) instanceof Wall) {
                top = i; // neu flame va cham tuong thi flame bi chan
                return;
            } else if(collisionType(ex_top) instanceof Brick) {
                top = i + 1;
                return;
            }
            top = i + 1;
        }
    }

    private void Left() {
        for (int i = 0; i < radius; i++) {
            Rectangle ex_left = new Rectangle(x - size * (i + 1), y, size, size); // tạo bound(hit box) cho flame sử dụng để tham gia các va chạm
            if (collisionType(ex_left) instanceof Wall) {
                left = i; // neu flame va cham tuong thi flame bi chan
                return;
            } else if(collisionType(ex_left) instanceof Brick) {
                left = i + 1;
                return;
            }
            left = i + 1;
        }
    }

    private void Down() {
        for (int i = 0; i < radius; i++) {
            Rectangle ex_down = new Rectangle(x , y + size * (i + 1), size, size); // tạo bound(hit box) cho flame sử dụng để tham gia các va chạm
            if (collisionType(ex_down) instanceof Wall) {
                down = i; // neu flame va cham tuong thi flame bi chan
                return;
            } else if(collisionType(ex_down) instanceof Brick) {
                down = i + 1;
                return;
            }
            down = i + 1;
        }
    }


    private static Object collisionType(Rectangle r) {
        for (Entity e : BombermanGame.stillObjects) { // duyệt tất cả các thực thể
            Rectangle r2 = e.getBounds();// tạo bao cho all thực thể
            if (r.intersects(r2)) {// nếu thực thể va chạm flame thì trả về thực thể
                return e;
            }
        }
        return r;// trả về kiểu dữ liệu rectangle của obj (bound của đối tượng)

    }

    // Tạo phương thức lựa chọn các ảnh để hiển thị
    private void setImg() {
        switch (direction) {
            case 0:
                sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.brick_exploded2, time, 20 );
                img = sprite.getFxImage();
                break;
            case 1:
                sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1
                        ,Sprite.explosion_horizontal2,time,20);
                img = sprite.getFxImage();
                break;
            case 2:
                sprite = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1
                        ,Sprite.explosion_horizontal_right_last2, time,20);
                img = sprite.getFxImage();
                break;
            case 3:
                sprite = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1
                        ,Sprite.explosion_horizontal_left_last2, time,20);
                img = sprite.getFxImage();
                break;
            case 4:
                sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1
                        ,Sprite.explosion_vertical2, time,20);
                img = sprite.getFxImage();
                break;
            case 5:
                sprite = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1
                        ,Sprite.explosion_vertical_top_last2, time,20);
                img = sprite.getFxImage();
                break;
            case 6:
                sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1
                        ,Sprite.explosion_vertical_down_last2, time,20);
                img = sprite.getFxImage();
                break;
        }
    }

}
