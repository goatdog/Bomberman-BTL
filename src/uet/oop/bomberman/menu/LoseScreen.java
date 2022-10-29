package uet.oop.bomberman.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.sound.Sound;

import java.io.IOException;

public class LoseScreen {
    private Stage stage;
    public void SwitchMainMenu(javafx.event.ActionEvent event) throws IOException {
        Sound.play("Sound");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
