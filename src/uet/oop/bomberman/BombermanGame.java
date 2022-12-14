package uet.oop.bomberman;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.item.Bomb_Item;
import uet.oop.bomberman.entities.item.Flame_Item;
import uet.oop.bomberman.entities.item.Speed_Item;
import uet.oop.bomberman.entities.stillEntity.Brick;
import uet.oop.bomberman.entities.stillEntity.Grass;
import uet.oop.bomberman.entities.stillEntity.Portal;
import uet.oop.bomberman.entities.stillEntity.Wall;
import uet.oop.bomberman.entities.enemies.*;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class BombermanGame extends Application {

    public static boolean hasSound = true;
    public static int WIDTH = 31;
    public static int HEIGHT = 13;
    public static boolean loseLives = false;
    public GraphicsContext gc;
    public Canvas canvas;
    public static final List<Enemy> enemies = new ArrayList<>();
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static List<Flame> flameList = new ArrayList<>();
    private static char curmap[][] = new char[HEIGHT + 1][WIDTH];
    public static int lives = 2; // số mạng
    public static int countTime =0;
    public static int time = 400; // cài time
    public static int score =0; // point
    public static boolean check = true;
    public static int level = 0;
    public static JPANEL jpanel = new JPANEL(); // để gọi các phương thức lớp JPanel (liên quan đến set up hình ảnh)

    public static AnchorPane ro = new AnchorPane(); // ro đại diện cho chức năng load các vùng cũng như ảnh text của toàn game


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
//    public void start(Stage stage) throws IOException{
//        // Tao Canvas
//        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 1));
//        gc = canvas.getGraphicsContext2D();
//
//        // Tao root container
//        Group root = new Group();
//        root.getChildren().add(canvas);
//        ro.getChildren().addAll(new Rectangle(2,3));
//        jpanel.setPanel();
//        root.getChildren().add(ro);
//        // Tao scene
//        Scene scene = new Scene(root);
//
//        // Them scene vao stage
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
//
//        AnimationTimer timer = new AnimationTimer() {
//            @Override
//            public void handle(long l) {
//                if (check == true) {
//                    check = false;
//                }
//
//                if (lives == 0 || time < 0) this.stop();
//                render();
//                update(scene);
//            }
//        };
//        timer.start();
//        createMap();
//    }

    public void start(Stage stage) throws IOException {
        Sound.play("Sound"); // nhạc nền
        ClassLoader load = Thread.currentThread().getContextClassLoader();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu.fxml"));
        Image logo = new Image("images/Bomberman-icon.png");
        stage.getIcons().add(logo);
        stage.setTitle("Bomberman Game");
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void createMap() {
        List<String> map = new ArrayList<String>();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            URL url = loader.getResource("./levels/Level" + level +".txt");
            System.out.println("current level: " + level);
            String path = url.getPath();
            File input = new File(path);
            BufferedReader scanner = new BufferedReader(new FileReader(input));
            String line = "";
            while(true) {
                line = scanner.readLine();
                if (line == null) {
                    break;
                } else {
                    map.add(line);
                }
            }
            String data[] = map.get(0).trim().split(" ");
            WIDTH = Integer.parseInt(data[2]);
            HEIGHT = Integer.parseInt(data[1]);
            for (int j = 0; j < HEIGHT ; j++) {
                for (int i = 0; i < WIDTH; i++) {
                    curmap[j + 1][i] = map.get(j+1).charAt(i);
                    switch(map.get(j + 1).charAt(i)) {
                        case '#':
                            stillObjects.add(new Wall(i, j + 1, Sprite.wall));
                            break;
                        case '*':
                            stillObjects.add(new Grass(i, j + 1, Sprite.grass));
                            stillObjects.add(new Brick(i, j + 1, Sprite.brick));
                            break;
                        case 'p':
                            stillObjects.add(new Grass(i, j + 1, Sprite.grass));
                            entities.add(new Bomber(i, j + 1, Sprite.player_right));
                            break;
                        case 'x':
                            stillObjects.add(new Grass(i, j + 1, Sprite.grass));
                            stillObjects.add(new Portal(i, j + 1, Sprite.portal));
                            stillObjects.add(new Brick(i, j + 1, Sprite.brick));
                            break;
                        case '1':
                            stillObjects.add(new Grass(i, j + 1, Sprite.grass));
                            entities.add(new Balloom(i, j + 1, Sprite.balloom_right1));
                            break;
                        case '2':
                            stillObjects.add(new Grass(i, j + 1, Sprite.grass));
                            entities.add(new Oneal(i, j + 1, Sprite.oneal_right1));
                            break;
                        case 'f':
                            stillObjects.add(new Grass(i, j + 1, Sprite.grass));
                            stillObjects.add(new Flame_Item(i, j + 1, Sprite.powerup_flames));
                            stillObjects.add(new Brick(i, j + 1, Sprite.brick));
                            break;
                        case 'b':
                            stillObjects.add(new Grass(i, j + 1, Sprite.grass));
                            stillObjects.add(new Bomb_Item(i, j + 1, Sprite.powerup_bombs));
                            stillObjects.add(new Brick(i, j + 1, Sprite.brick));
                            break;
                        case 's':
                            stillObjects.add(new Grass(i, j + 1, Sprite.grass));
                            stillObjects.add(new Speed_Item(i, j + 1, Sprite.powerup_speed));
                            stillObjects.add(new Brick(i, j + 1, Sprite.brick));
                            break;
                        case '3':
                            stillObjects.add(new Grass(i, j + 1, Sprite.grass));
                            entities.add(new Doll(i, j + 1, Sprite.doll_right1));
                            break;
                        case '4':
                            stillObjects.add(new Grass(i, j + 1, Sprite.grass));
                            entities.add(new Kondoria(i, j + 1, Sprite.kondoria_right1));
                            break;
                        default:
                            stillObjects.add(new Grass(i, j + 1, Sprite.grass));
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Can't load file!");
        }
    }

    public static void delete() {
        stillObjects.clear();
        entities.clear();
        List<Bomb> bombs = Bomber.getBombs();
        bombs.clear();
        flameList.clear();
    }

    public void update(Scene scene) {
        coutTime();
        if (countTime % 60 == 0) {
            time -- ;
        }
        //if (Bomber.lives >= 0) lives = Bomber.lives;
        jpanel.setLevel(level); // set level
        jpanel.setTimes(time); // set time
        jpanel.setPoint(score); // set điểm
        jpanel.setLives(lives); // set mạng
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update(scene);
        }
        List<Bomb> bombs = Bomber.getBombs();
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update(scene);
        }
        for (int i = 0; i < flameList.size(); i++) {
            flameList.get(i).update(scene);
        }
        try {
            stillObjects.forEach(g -> g.update(scene));
        } catch (ConcurrentModificationException e) {
            System.out.println("Get item");
        }
    }

    public void render() {
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> {
            g.render(gc);
        });
        List<Bomb> bombs = Bomber.getBombs();
        for (Bomb bomb : bombs) {
            bomb.render(gc);
        }
        flameList.forEach(g -> g.render(gc));
    }
    public void coutTime() {
        countTime++;
    }

    public static char[][] getCurmap() {
        return curmap;
    }

    public static void setGrid(int i, int j, char val) {
        curmap[i][j] = val;
    }

    public static char getGrid(int i, int j) {
        return curmap[i][j];
    }

    public static int getBomberX() {
        for (int i = 0; i < entities.size(); i++) {
            Entity tmp = entities.get(i);
            if (tmp instanceof Bomber) {
                return tmp.getX();
            }
        }
        return Sprite.SCALED_SIZE;
    }

    public static int getBomberY() {
        for (int i = 0; i < entities.size(); i++) {
            Entity tmp = entities.get(i);
            if (tmp instanceof Bomber) {
                return tmp.getY();
            }
        }
        return Sprite.SCALED_SIZE * 2;
    }
}
