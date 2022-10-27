package uet.oop.bomberman.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.sound.Sound;

import java.io.IOException;

public class PauseScreen {
    private Stage stage;

    public void SoundSwitch(javafx.event.ActionEvent event) throws IOException {
        if (BombermanGame.hasSound == true)  {
            Sound.stop("Sound");
            BombermanGame.hasSound = false;
        }
        else {
            Sound.play("Sound");
            BombermanGame.hasSound = true;
        }
    }

    public void SwitchBackToGame(javafx.event.ActionEvent event) throws IOException {
        GameScreen.isRunning = true;
    }

    public void SwitchBackToMenu(javafx.event.ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
