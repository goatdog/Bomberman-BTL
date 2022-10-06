package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemies.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static int WIDTH = 31;
    public static int HEIGHT = 13;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException{
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update(scene);
            }
        };
        timer.start();
        createMap();
    }

    public void createMap() {
        /*for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }
                else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }*/
        List<String> map = new ArrayList<String>();
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            FileReader input = new FileReader("C:\\Users\\dell\\IdeaProjects\\bomberman-starter-starter-2\\res\\levels\\Level1.txt");
            BufferedReader scanner = new BufferedReader(input);
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
                    switch(map.get(j + 1).charAt(i)) {
                        case '#':
                            stillObjects.add(new Wall(i, j, Sprite.wall));
                            break;
                        case '*':
                            stillObjects.add(new Grass(i, j, Sprite.grass));
                            entities.add(new Brick(i, j, Sprite.brick));
                            break;
                        case 'p':
                            stillObjects.add(new Grass(i, j, Sprite.grass));
                            entities.add(new Bomber(i, j, Sprite.player_right));
                            break;
                        case 'x':
                            stillObjects.add(new Grass(i, j, Sprite.grass));
                            stillObjects.add(new Portal(i, j, Sprite.portal));
                            entities.add(new Brick(i, j, Sprite.brick));
                            break;
                        case '1':
                            stillObjects.add(new Grass(i, j, Sprite.grass));
                            entities.add(new Balloom(i, j, Sprite.balloom_right1));
                            break;
                        case '2':
                            stillObjects.add(new Grass(i, j, Sprite.grass));
                            entities.add(new Oneal(i, j, Sprite.oneal_right1));
                            break;
                        case 'f':
                            stillObjects.add(new Grass(i, j, Sprite.grass));
                            entities.add(new Flame_Item(i, j, Sprite.powerup_flames));
                            entities.add(new Brick(i, j, Sprite.brick));
                            break;
                        default:
                            stillObjects.add(new Grass(i, j, Sprite.grass));
                            break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Can't load file!");
        }
    }

    public void update(Scene scene) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update(scene);
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> {
            g.render(gc);
        });
    }
}
