package uet.oop.bomberman.menu;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import javafx.scene.shape.Rectangle;

public class GameScreen extends BombermanGame{
    public void Game(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * BombermanGame.WIDTH, Sprite.SCALED_SIZE * (BombermanGame.HEIGHT + 1));
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        jpanel.setPanel();
        root.getChildren().add(ro);
        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (check == true) {
                    BombermanGame.delete();
                    BombermanGame.level++;
                    BombermanGame.time = 400;
                    BombermanGame.lives++;
                    createMap();
                    check = false;
                }

                if (lives == 0 || time < 0) this.stop();
                render();
                update(scene);
            }
        };
        timer.start();
    }
}
