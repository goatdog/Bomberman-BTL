package uet.oop.bomberman.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;

import java.io.IOException;

public class MainMenu {
    private Stage stage;
    public void SwitchPlayScreen(javafx.event.ActionEvent event) {
        GameScreen.isRunning = true;
        GameScreen.openCountforThisScreen = GameScreen.openCountforPauseScreen = 1;
        GameScreen.time = 400;
        GameScreen.level = 0;
        GameScreen.lives = 2;
        GameScreen.score = 0;
        GameScreen.check = true;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GameScreen gameScreen = new GameScreen();
        gameScreen.Game(stage);
    }

    public void SwitchControlScreen(javafx.event.ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ControlScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void ExitGame(javafx.event.ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
