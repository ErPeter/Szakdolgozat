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


        Line line;
        for(int i = 0; i < valami.getLineList().size(); i++){
            line = new Line(valami.getLineList().get(i).getStart().getXCoordinate(),
                            valami.getLineList().get(i).getStart().getYCoordinate(),
                            valami.getLineList().get(i).getEnd().getXCoordinate(),
                            valami.getLineList().get(i).getEnd().getYCoordinate());
            root.getChildren().add(line);
        }

        SolvingAlgorithm solv = new SolvingAlgorithm(valami);

        solv.creatingCirclePoints(valami);

        Circle circleCenter = new Circle(valami.getGraphCenterX(),valami.getGraphCenterY(), 3);
        root.getChildren().add(circleCenter);

        double rad = 3;
        Circle circle;
        for(int i = 0; i < solv.getCircleOptions().size(); i++){
            System.out.println(solv.getCircleOptions().get(i).getXCoordinate() + " , " +solv.getCircleOptions().get(i).getYCoordinate());
            circle = new Circle(solv.getCircleOptions().get(i).getXCoordinate(),solv.getCircleOptions().get(i).getYCoordinate(), rad);
            root.getChildren().add(circle);
        }

        stage.setScene(new Scene(root, 1000, 1000));
        stage.show();
    }

    public static void main(String[] args) {




        launch(args);

    }
}
