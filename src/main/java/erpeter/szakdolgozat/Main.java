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
        Line line2 = new Line(30,10,10,30);
        Line line3 = new Line(8,25,40,10);

//            Setting the properties to a line
//        line.setStartX(100.0);
//        line.setStartY(150.0);
//        line.setEndX(500.0);
//        line.setEndY(150.0);

        //Creating a Group
        Group root = new Group();
        root.getChildren().add(line);
        root.getChildren().add(line2);
        root.getChildren().add(line3);


        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        Point pointStart = new Point(0, 0);
        Point pointEnd = new Point(30, 30);

        Point secondStart = new Point(30, 0);
        Point secondEnd = new Point(0, 30);

        Point thirdStart = new Point(8, 25);
        Point thirdEnd = new Point(40, 10);

        MyLine lineONE = new MyLine(pointStart,pointEnd);
        MyLine lineTWO = new MyLine(secondStart,secondEnd);
        MyLine lineThere = new MyLine(thirdStart,thirdEnd);

        System.out.println(lineONE.intersectLines(lineTWO));
        System.out.println(lineONE.intersectLines(lineThere));
        lineONE.allIntersections();


        launch(args);

    }
}
