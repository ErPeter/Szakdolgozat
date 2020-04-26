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
        this.line = new Line2D.Double(start.getXCoordinate(),start.getYCoordinate(),
                this.end.getXCoordinate(), end.getYCoordinate());
    }

    public void refreshLine(){
        this.line.setLine(start.getXCoordinate(),start.getYCoordinate(),end.getXCoordinate(),end.getYCoordinate());
    }

    public boolean intersectLines(MyLine line){
        if(this.line.intersectsLine(line.line)){
            return true;
        }
        return false;
    }

}
