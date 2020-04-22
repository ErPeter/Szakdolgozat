package erpeter.szakdolgozat;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Graph {

    private int ID;
    private String points;
    private String line;
    private int graphCenterX;
    private int graphCenterY;
    private List<Point> pointList = new ArrayList<>();
    private List<MyLine> lineList = new ArrayList<>();

    public Graph() {
    }

    public Graph createGraph(int graphNumber){
        Graph graph = new Graph();
        Point point;
        String[] points = find(graphNumber).getPoints().split(",");
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for(int i = 0; i < points.length; i++){
            String[] coordinates;
            String pointID = Character.toString(alphabet[i]);
            coordinates = points[i].split("&");
            point = new Point(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
            point.setName(pointID.toUpperCase());
            pointList.add(point);
        }
        for (Point value : pointList) {
            System.out.println(value.getName() + value.getXCoordinate() + " , " + value.getYCoordinate());
        }
        //String[] lines;
        return graph;
    }

    private Graph find(int identity) {

        Connection connection = DatabaseHelper.createConnection();

        String query = "SELECT * FROM graph where ID = " + identity;


        Graph graph = new Graph();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {


                int ID = resultSet.getInt("id");
                String points = resultSet.getString("points");
                String line = resultSet.getString("line");
                int centerX = resultSet.getInt("centerx");
                int centerY = resultSet.getInt("centery");

                graph.setID(ID);
                graph.setPoints(points);
                graph.setLine(line);
                graph.setGraphCenterX(centerX);
                graph.setGraphCenterY(centerY);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        DatabaseHelper.closeConnection(connection);

        System.out.println(graph);

        return graph;
    }


    @Override
    public String toString() {
        return String.format(ID + " Points: " + points + " Lines: " + line + " graph center x coordinate : "+ graphCenterX + " graph center y coordinate : " + graphCenterY);
    }
}
