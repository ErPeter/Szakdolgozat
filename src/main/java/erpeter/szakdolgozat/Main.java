package erpeter.szakdolgozat;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

class NewStageA {
    Main main = new Main();

    NewStageA() {
        Graph graph = new Graph(main.getGraph());

        Graph aAlgorithmGraph = new Graph(main.getGraph());
        SolvingAlgorithm solveA = new SolvingAlgorithm(graph);

        solveA.creatingCirclePoints(aAlgorithmGraph);
        long startTime = System.nanoTime();
        graph = solveA.solveWithA();
        long endTime = System.nanoTime();
        System.out.println("Time of A algorithm: ");
        long time = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println(time);



        Stage subStage = new Stage();
        subStage.setTitle("Graph on a plane with A");
        Group group = new Group();

        graph.determinateCenter();


        Line line;
        for (int i = 0; i < graph.getLineList().size(); i++) {
            line = new Line(graph.getLineList().get(i).getStart().getXCoordinate(),
                    graph.getLineList().get(i).getStart().getYCoordinate(),
                    graph.getLineList().get(i).getEnd().getXCoordinate(),
                    graph.getLineList().get(i).getEnd().getYCoordinate());
            group.getChildren().add(line);
        }

        Circle point;
        for (int i = 0; i < graph.getPointList().size(); i++){
            point = new Circle(graph.getPointList().get(i).getXCoordinate(),
                    graph.getPointList().get(i).getYCoordinate(), 3);
            group.getChildren().add(point);
        }

        Scene scene = new Scene(group, 1000, 1000);

        subStage.setScene(scene);
        subStage.show();
    }
}

class NewStage {
    Main main = new Main();

    NewStage() {
        Stage subStage = new Stage();
        subStage.setTitle("Graph on a plane");
        Graph graph = new Graph(main.getGraph());

        Group group = new Group();

        graph.determinateCenter();


        SolvingAlgorithm solve = new SolvingAlgorithm(graph);

        solve.creatingCirclePoints(graph);

        long startTime = System.nanoTime();
        solve.solve();
        long endTime = System.nanoTime();
        System.out.println("Time of solve algorithm: ");
        long time = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println(time);

        Line line;
        for (int i = 0; i < solve.getCurrentState().getLineList().size(); i++) {
            line = new Line(solve.getCurrentState().getLineList().get(i).getStart().getXCoordinate(),
                    solve.getCurrentState().getLineList().get(i).getStart().getYCoordinate()+55,
                    solve.getCurrentState().getLineList().get(i).getEnd().getXCoordinate(),
                    solve.getCurrentState().getLineList().get(i).getEnd().getYCoordinate()+55);
            group.getChildren().add(line);
        }

        Circle point;
        for (int i = 0; i < solve.getCurrentState().getPointList().size(); i++){
            point = new Circle(solve.getCurrentState().getPointList().get(i).getXCoordinate(),solve.getCurrentState().getPointList().get(i).getYCoordinate()+55, 3);
            group.getChildren().add(point);
        }

        Scene scene = new Scene(group, 1000, 1000);

        Button button = new Button("Solve");
        group.getChildren().add(button);

        button.setOnAction(event -> new NewStageA());

        subStage.setScene(scene);
        subStage.show();
    }
}

public class Main extends Application {

    @Getter
    public int graph = 19;

    @Override
    public void start(Stage stage) throws Exception {


        Group root = new Group();
        Graph graph = new Graph(this.graph);


        graph.determinateCenter();

        Line line;
        for (int i = 0; i < graph.getLineList().size(); i++) {
            line = new Line(graph.getLineList().get(i).getStart().getXCoordinate(),
                    graph.getLineList().get(i).getStart().getYCoordinate(),
                    graph.getLineList().get(i).getEnd().getXCoordinate(),
                    graph.getLineList().get(i).getEnd().getYCoordinate());
            root.getChildren().add(line);
        }

        Circle point;
        for (int i = 0; i < graph.getPointList().size(); i++){
            point = new Circle(graph.getPointList().get(i).getXCoordinate(),
                    graph.getPointList().get(i).getYCoordinate(), 3);
            root.getChildren().add(point);
        }

        Button button = new Button("Solve");
        root.getChildren().add(button);

        button.setOnAction(event -> new NewStage());
        stage.setScene(new Scene(root, 1000, 1000));
        stage.show();



    }

    public static void main(String[] args) {

        launch(args);

    }
}


