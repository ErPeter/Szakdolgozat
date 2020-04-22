package erpeter.szakdolgozat;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        Graph valami = new Graph(2);

        Line line;
        for(int i = 0; i < valami.getLineList().size(); i++){
            line = new Line(valami.getLineList().get(i).getStart().getXCoordinate(),
                            valami.getLineList().get(i).getStart().getYCoordinate(),
                            valami.getLineList().get(i).getEnd().getXCoordinate(),
                            valami.getLineList().get(i).getEnd().getYCoordinate());
            root.getChildren().add(line);
        }

        stage.setScene(new Scene(root, 1000, 1000));
        stage.show();
    }

    public static void main(String[] args) {



        launch(args);

    }
}
