package erpeter.szakdolgozat;
import lombok.Data;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

@Data
public class MyLine {
    private Point start;
    private Point end;
    private List<Point> intersections = new ArrayList<>();
    private Line2D line;



    public MyLine(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.line = new Line2D.Double(this.start.getXCoordinate(),this.start.getYCoordinate(),
                this.end.getXCoordinate(), this.end.getYCoordinate());
    }

    public boolean intersectLines(MyLine line){
        if(this.line.intersectsLine(line.line)){
//            intersections.add(intersectionPoint(line));
            return true;
        }
        return false;
    }

    public Point intersectionPoint(MyLine line){
        double x, y;
        if(!line.vertical() && !this.vertical()) {

            double slopeOne = (this.end.getYCoordinate() - this.start.getYCoordinate()) /
                    (this.end.getXCoordinate() - this.start.getXCoordinate());              //slop of line this

            double slopeTwo = (line.end.getYCoordinate() - line.start.getYCoordinate()) /
                    (line.end.getXCoordinate() - line.start.getXCoordinate());              //slope of parameter line

            double yInclineOne = ((((-1) * this.normalX())*(- this.start.getXCoordinate()))/this.normalY()) + this.start.getYCoordinate();
            double yInclineTwo = ((((-1) * line.normalX())*(- line.start.getXCoordinate()))/line.normalY()) + line.start.getYCoordinate();

            x = (yInclineTwo - yInclineOne) / (slopeOne - slopeTwo);
            y = (slopeOne * x + yInclineOne);

            return new Point(x, y); //returning the intersection point
        }
        //two case when one of the line is vertical (the slope is infinite in those cases)
        else if (line.vertical()) {
            x =  line.start.getXCoordinate();
            y = ((((-1) * this.normalX())*(x - this.start.getXCoordinate()))/this.normalY()) + this.start.getYCoordinate();
            return new Point(x, y);
        } else {
            x = this.start.getXCoordinate();
            y = ((((-1) * line.normalX())*(x - line.start.getXCoordinate()))/line.normalY()) + line.start.getYCoordinate();
            return new Point(x, y);
        }

    }

    public void allIntersections(){
        for(int i = 0; i < intersections.size(); i++){
            System.out.println(getIntersections().get(i).getXCoordinate() + " " + getIntersections().get(i).getYCoordinate());
        }
    }

    private boolean vertical(){
        return this.start.getXCoordinate() == this.end.getXCoordinate();
    }

    private double normalX(){
        return (this.start.getYCoordinate() - this.end.getYCoordinate());
    }

    private double normalY(){
        return -(this.start.getXCoordinate() - this.end.getXCoordinate());
    }
}
