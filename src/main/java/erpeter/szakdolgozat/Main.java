package erpeter.szakdolgozat;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import lombok.Getter;


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
        solve.solve();

        Line line;
        for (int i = 0; i < solve.getCurrentState().getLineList().size(); i++) {
            line = new Line(solve.getCurrentState().getLineList().get(i).getStart().getXCoordinate(),
                    solve.getCurrentState().getLineList().get(i).getStart().getYCoordinate(),
                    solve.getCurrentState().getLineList().get(i).getEnd().getXCoordinate(),
                    solve.getCurrentState().getLineList().get(i).getEnd().getYCoordinate());
            group.getChildren().add(line);
        }

        Scene scene = new Scene(group, 1000, 1000);

        subStage.setScene(scene);
        subStage.show();
    }
}

public class Main extends Application {

    @Getter
    public int graph = 3;

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
