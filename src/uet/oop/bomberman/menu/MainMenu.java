package uet.oop.bomberman.menu;

import javafx.scene.Node;
import javafx.stage.Stage;

public class MainMenu {
    private Stage stage;
    public void SwitchPlayScreen(javafx.event.ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        GameScreen gameScreen = new GameScreen();
        gameScreen.Game(stage);
    }
}
