package erpeter.szakdolgozat;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();
        Graph valami = new Graph(5);
        valami.determinateCenter();

        SolvingAlgorithm solv = new SolvingAlgorithm(valami);

        solv.creatingCirclePoints(valami);
        solv.solve();

        Circle circle = new Circle(solv.getCurrentState().getPointList().get(0).getXCoordinate(),solv.getCurrentState().getPointList().get(0).getYCoordinate(), 3);
        root.getChildren().add(circle);

        Line line;
        for(int i = 0; i < solv.getCurrentState().getLineList().size(); i++){
            line = new Line(solv.getCurrentState().getLineList().get(i).getStart().getXCoordinate(),
                    solv.getCurrentState().getLineList().get(i).getStart().getYCoordinate(),
                    solv.getCurrentState().getLineList().get(i).getEnd().getXCoordinate(),
                    solv.getCurrentState().getLineList().get(i).getEnd().getYCoordinate());
            root.getChildren().add(line);
        }


        stage.setScene(new Scene(root, 1000, 1000));
        stage.show();
    }

    public static void main(String[] args) {

        launch(args);

    }
}
