package erpeter.szakdolgozat;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Line line = new Line(10,10,30,30);

        //Creating a Group
        Group root = new Group();
        root.getChildren().add(line);

        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {

        Graph valami = new Graph(1);

        launch(args);

    }
}
