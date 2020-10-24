package erpeter.szakdolgozat;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Graph implements Serializable {

    @Getter
    @Setter
    private int ID;

    @Getter
    @Setter
    private double wrongness;

    @Getter
    @Setter
    private double cost = 0;

    private String points;
    private String line;
    @Getter
    private double graphCenterX;
    @Getter
    private double graphCenterY;

    @Getter
    @Setter
    private List<Point> pointList = new ArrayList<>();

    @Getter
    @Setter
    private List<MyLine> lineList = new ArrayList<>();

    //crates the graph from the database and sets every variable into the start position
    public Graph() {
    }

    public Graph(int identity) {
        start(identity);
    }

    private void start(int identity) {

        Connection connection = DatabaseHelper.createConnection();

        var query = "SELECT * FROM graph where ID = " + identity;


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                this.ID = resultSet.getInt("id");
                this.points = resultSet.getString("points");
                this.line = resultSet.getString("line");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseHelper.closeConnection(connection);

        Point point;
        String[] points = this.points.split(",");
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        for (int i = 0; i < points.length; i++) {

            String[] coordinates;
            coordinates = points[i].split("&");
            point = new Point(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
            String pointID = Character.toString(alphabet[i]);
            point.setName(pointID);
            pointList.add(point);
        }

        MyLine myLine;
        String[] lines = line.split(",");

        for (String s : lines) {

            String[] endAndStartPoint = s.split("");
            myLine = new MyLine(getPointFromList(endAndStartPoint[0]), getPointFromList(endAndStartPoint[1]));
            lineList.add(myLine);
        }
        calculatingPrimaryPlaceValues();
        calculatingSecondaryPlaceValues();
        calculateWrongness();
    }

    //    Calculates how wrong is the graph, by summing the primary values and add 0.secondary value of all points of the graph.
    public void calculateWrongness() {
        this.wrongness = 0;
        int prim = 0;
        double sec = 0.0;
        int helper;
        for (Point point : pointList) {
            prim += point.getPrimaryPlaceValue();
            sec += point.getSecondaryPlaceValue();
        }
        helper = (int) (Math.log10(sec) + 1);
        if (sec != 0) {
            sec = sec / (Math.pow(10, helper));
        }
        this.wrongness = (double) prim + sec;
    }

    public void refreshWrongness() {

        for (Point point : pointList) {

            point.setPrimaryPlaceValue(0);
            point.setSecondaryPlaceValue(0);
        }
        for (MyLine myLine : lineList) {
            myLine.refreshLine();
        }
        calculatingPrimaryPlaceValues();
        calculatingSecondaryPlaceValues();
        calculateWrongness();
    }

    //finds the point with the ID of searched pointName
    private Point getPointFromList(String pointName) {
        for (Point point : pointList) {

            String name = point.getName();

            if (pointName.equals(name)) {
                return point;
            }
        }
        return null;
    }

    //Calculates the primary value for every point
    private void calculatingPrimaryPlaceValues() {
        Point start;
        Point end;

        for (int i = 0; i < lineList.size(); i++) {

            start = lineList.get(i).getStart();
            end = lineList.get(i).getEnd();
            for (MyLine myLine : lineList) {

                if ((!start.equals(myLine.getStart())) && !start.equals(myLine.getEnd())) {

                    if ((!end.equals(myLine.getStart())) && !end.equals(myLine.getEnd())) {

                        if (lineList.get(i).intersectLines(myLine)) {

                            lineList.get(i).getStart().setPrimaryPlaceValue(lineList.get(i).getStart().getPrimaryPlaceValue() + 1);
                            lineList.get(i).getEnd().setPrimaryPlaceValue(lineList.get(i).getEnd().getPrimaryPlaceValue() + 1);
                        }
                    }
                }
            }
        }
    }


    //Calculates the secondary value for every point
    private void calculatingSecondaryPlaceValues() {
        Point point;
        for (Point value : this.pointList) {
            point = value;
            for (MyLine myLine : this.lineList) {
                if (point.equals(myLine.getStart())) {
                    point.setSecondaryPlaceValue(point.getSecondaryPlaceValue() + myLine.getEnd().getPrimaryPlaceValue());
                }
                if (point.equals(myLine.getEnd())) {
                    point.setSecondaryPlaceValue(point.getSecondaryPlaceValue() + myLine.getStart().getPrimaryPlaceValue());
                }
            }
        }
    }


    public void determinateCenter() {
        double x = 0, y = 0;

        for (Point point : this.pointList) {
            x += point.getXCoordinate();
            y += point.getYCoordinate();
        }

        x /= this.pointList.size();
        y /= this.pointList.size();


        this.graphCenterX = x;
        this.graphCenterY = y;
    }

    public void print(){
        for (int i = 0; i < pointList.size(); i++){
            double x = pointList.get(i).getXCoordinate();
            double y = pointList.get(i).getYCoordinate();
            String name = pointList.get(i).getName();
            System.out.println(name + ": " + x + ", " + y);
        }
    }


    @Override
    public String toString() {
        return ID + " Points: " + points + " Lines: " + line + " graph center x coordinate : " + graphCenterX + " graph center y coordinate : " + graphCenterY;
    }
}
