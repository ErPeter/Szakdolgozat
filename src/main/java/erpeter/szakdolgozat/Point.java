package erpeter.szakdolgozat;


import lombok.Getter;
import lombok.Setter;

public class Point {
    @Getter
    private double xCoordinate;
    @Getter
    private double yCoordinate;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private int primaryPlaceValue; //Primary indication of how good is the place of the point (0 is the best value)
    @Getter @Setter
    private int secondaryPlaceValue; //Secondary indication of how good is the place of the point

    public Point(double xCoordinate, double yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.primaryPlaceValue = 0;
        this.secondaryPlaceValue = 0;
    }

    public void setCoordinates(double xCoordinate, double yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
}
