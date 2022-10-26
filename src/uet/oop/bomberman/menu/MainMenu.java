package uet.oop.bomberman.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {
    private Stage stage;
    public void SwitchPlayScreen(javafx.event.ActionEvent event) {
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
