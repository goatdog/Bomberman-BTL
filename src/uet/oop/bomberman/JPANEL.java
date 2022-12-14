package uet.oop.bomberman;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import static javafx.scene.paint.Color.BLACK;
public class JPANEL extends AnchorPane{
    public static Label labelLevel; // hiển thị chữ level trên thanh thông tin

    public static Label labelTime; // hiển thị chữ time trên thanh thông tin

    public static Label labelPoint; // hiển thị chữ point trên thanh thông tin

    public static Label labelLives; // hiển thị chữ live trên thanh thông tin

    public JPANEL() {
        labelLevel = new Label("LEVEL: " + BombermanGame.level);
        labelLevel.setLayoutX(100);
        labelLevel.setLayoutY(1);
        labelLevel.setFont(Font.font(18));
        labelLevel.setTextFill(BLACK);
        labelTime = new Label("TIME : "+BombermanGame.time); // cài time
        // chỉnh tọa độ xếp thông tin về time
        labelTime.setLayoutX(300);
        labelTime.setLayoutY(1);
        labelTime.setFont(Font.font(18));
        labelTime.setTextFill(BLACK);
        // chỉnh tọa độ xếp thông tin về point
        labelPoint = new Label("POINT : "+ BombermanGame.score);
        labelPoint.setLayoutX(600);
        labelPoint.setLayoutY(1);
        labelPoint.setFont(Font.font(18));
        labelPoint.setTextFill(BLACK);
        // chỉnh tọa độ xếp thông tin về lives
        labelLives = new Label("LIVES : "+ BombermanGame.lives);
        labelLives.setLayoutX(800);
        labelLives.setLayoutY(1);
        labelLives.setFont(Font.font(18));
        labelLives.setTextFill(BLACK);
    }
    public static void setPanel() {
        BombermanGame.ro.getChildren().addAll(labelLevel,labelTime,labelPoint,labelLives);
    }
    public void setLevel(int t) {
        labelLevel.setText("LEVEL : " + t);
    }

    public void setTimes(int t) {
        labelTime.setText("TIMES : "+t);
    }

    public void setPoint(int t) {
        labelPoint.setText("POINT : "+t);
    }

    public void setLives(int t) {
        labelLives.setText("LIVES : "+t);
    }

    public void delete() {
        BombermanGame.ro.getChildren().removeAll(labelLives,labelLevel,labelTime,labelPoint);
    }
}