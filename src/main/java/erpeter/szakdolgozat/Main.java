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
        Graph valami = new Graph(1);
        valami.determinateCenter();
        System.out.println(valami.getGraphCenterX() + "," + valami.getGraphCenterY());




        SolvingAlgorithm solv = new SolvingAlgorithm(valami);

        solv.creatingCirclePoints(valami);
        solv.solve();
        for (int i = 0; i < solv.getGraphOptions().get(0).getPointList().size(); i++){
            System.out.println("name: " + solv.getGraphOptions().get(0).getPointList().get(i).getName() +
                                " X: "+ solv.getGraphOptions().get(0).getPointList().get(i).getXCoordinate() +
                                " Y: " + solv.getGraphOptions().get(0).getPointList().get(i).getYCoordinate());
        }
        System.out.println(solv.getCurrentState().getPointList().get(0).getXCoordinate() + " "+ solv.getCurrentState().getPointList().get(0).getYCoordinate());
        System.out.println(solv.getWorstPoint().getName() + " " + solv.getWorstPoint().getXCoordinate() + " " + solv.getWorstPoint().getYCoordinate());

        Circle circle = new Circle(solv.getCurrentState().getPointList().get(0).getXCoordinate(),solv.getCurrentState().getPointList().get(0).getYCoordinate(), 3);
        root.getChildren().add(circle);

        Line line;
        for(int i = 0; i < solv.getGraphOptions().get(0).getLineList().size(); i++){
            line = new Line(solv.getGraphOptions().get(0).getLineList().get(i).getStart().getXCoordinate(),
                    solv.getGraphOptions().get(0).getLineList().get(i).getStart().getYCoordinate(),
                    solv.getGraphOptions().get(0).getLineList().get(i).getEnd().getXCoordinate(),
                    solv.getGraphOptions().get(0).getLineList().get(i).getEnd().getYCoordinate());
            root.getChildren().add(line);
        }


        stage.setScene(new Scene(root, 1000, 1000));
        stage.show();
    }

    public static void main(String[] args) {




        launch(args);

    }
}
