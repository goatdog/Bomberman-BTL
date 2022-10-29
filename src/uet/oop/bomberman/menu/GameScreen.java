package uet.oop.bomberman.menu;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.awt.*;
import java.io.IOException;

import javafx.scene.shape.Rectangle;

public class GameScreen extends BombermanGame{
    public static boolean isRunning = true;

    public static int openCountforPauseScreen = 1;
    public static int openCountforThisScreen = 1;

    public static int openCountforWinScreen = 1;

    public static int openCountforLoseScreen = 1;
    public void Game(Stage stage) {
        delete();
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

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (isRunning) {
                    openCountforPauseScreen = 1;
                    if (openCountforThisScreen == 1) {
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        openCountforThisScreen--;
                    }
                    if (check == true) {
                        if (((loseLives == false && level > 0) || level == 4) && openCountforWinScreen == 1) {
                            Sound.stop("Sound");
                            Sound.play("Victory");
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WinScreen.fxml"));
                            try {
                                Parent root2 = loader.load();
                                Scene scene2 = new Scene(root2);
                                stage.setScene(scene2);
                                stage.show();
                                openCountforWinScreen--;
                            } catch (IOException e) {
                                System.out.println("Can't load screen");
                            }
                        } else {
                            loseLives = false;
                            BombermanGame.delete();
                            BombermanGame.level++;
                            BombermanGame.time = 400;
                            BombermanGame.lives++;
                            createMap();
                            check = false;
                        }
                    }

                    if ((lives == 0 || time < 0) && openCountforLoseScreen == 1) {
                        Sound.stop("Sound");
                        Sound.play("Failure");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoseScreen.fxml"));
                        try {
                            Parent root2 = loader.load();
                            Scene scene2 = new Scene(root2);
                            stage.setScene(scene2);
                            stage.show();
                            openCountforLoseScreen--;
                        } catch (IOException e) {
                            System.out.println("Can't load screen");
                        }
                    }
                    render();
                    update(scene);
                } else {
                    openCountforThisScreen = 1;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/PauseScreen.fxml"));
                    try {
                        if (openCountforPauseScreen == 1) {
                            Parent root2 = loader.load();
                            Scene scene2 = new Scene(root2);
                            stage.setScene(scene2);
                            stage.show();
                            openCountforPauseScreen--;
                        }
                    } catch (IOException e) {
                        System.out.println("Can't load screen");
                    }
                }
            }
        };
        timer.start();
    }
}
