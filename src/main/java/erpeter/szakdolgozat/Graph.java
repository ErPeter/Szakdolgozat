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


    public Graph (int identity) {

        Connection connection = DatabaseHelper.createConnection();

        var query = "SELECT * FROM graph where ID = " + identity;


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {


                this.ID = resultSet.getInt("id");
                this.points= resultSet.getString("points");
                this.line = resultSet.getString("line");
                this.graphCenterX = resultSet.getInt("centerx");
                this.graphCenterY = resultSet.getInt("centery");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        DatabaseHelper.closeConnection(connection);

            Point point;
            String[] points = this.points.split(",");
            char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
            for(int i = 0; i < points.length; i++){
                String[] coordinates;
                coordinates = points[i].split("&");
                point = new Point(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
                String pointID = Character.toString(alphabet[i]);
                point.setName(pointID);
                pointList.add(point);
            }

            MyLine myLine;
            String[] lines = line.split(",");
            for(int i = 0; i < lines.length; i++){
                String[] endAndStartPoint = lines[i].split("");
                myLine = new MyLine(getPointFromList(endAndStartPoint[0]), getPointFromList(endAndStartPoint[1]));
                lineList.add(myLine);
            }
    }

    private Point getPointFromList(String pointName){
        for (int i = 0; i < pointList.size(); i++){
            String name = pointList.get(i).getName();
            if (pointName.equals(name)){
                return pointList.get(i);
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return ID + " Points: " + points + " Lines: " + line + " graph center x coordinate : "+ graphCenterX + " graph center y coordinate : " + graphCenterY;
    }
}
