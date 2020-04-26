package erpeter.szakdolgozat;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

@Data
public class SolvingAlgorithm {
    private Graph currentState;
    private Point worstPoint;
    private List<MyLine> worstPointLineList;
    private Point bestPoint;                //must be on the same line with worstPoint
    private List<Point> circleOptions;
    private List<Point> lineOptions;
    private List<Point> solution;
    private List<Point> worstPoints;

    public SolvingAlgorithm(Graph startState) {
        this.currentState = startState;
        this.worstPointLineList = new ArrayList<>();
        this.circleOptions = new ArrayList<>();
        this.lineOptions = new ArrayList<>();
        this.solution = new ArrayList<>();
        this.worstPoints = new ArrayList<>();
        findWorstPoint();
        worstPointLines();
        this.bestPoint = this.worstPoint;
        findBestPoint();
    }


    public void solve() {
        double originalX = this.worstPoint.getXCoordinate();
        double originalY = this.worstPoint.getYCoordinate();

        if(this.currentState.getLineList().size() <= (3 * currentState.getPointList().size()) - 6) {

            while (currentState.getWrongness() != 0) {
                findBestPoint();

                int placeIndexLine = lineOptions.size() -1;
                int placeIndexCircle = circleOptions.size() -1;
                double minWrongnessLine = currentState.getWrongness();
                double minWrongnessCircle = currentState.getWrongness();
                creatingPointsOnLine(this.worstPoint, this.bestPoint);
                creatingCirclePoints(currentState);

                for (int i = 0; i < this.lineOptions.size(); i++) {
                    replacePoint(this.worstPoint, lineOptions.get(i).getXCoordinate(), lineOptions.get(i).getYCoordinate());
                    this.currentState.refreshWrongness();
                    if (currentState.getWrongness() < minWrongnessLine) {
                        minWrongnessLine = this.currentState.getWrongness();
                        placeIndexLine = i;
                    }
                }

                for (int i = 0; i < this.circleOptions.size(); i++) {
                    replacePoint(this.worstPoint, circleOptions.get(i).getXCoordinate(), circleOptions.get(i).getYCoordinate());
                    this.currentState.refreshWrongness();
                    if (currentState.getWrongness() < minWrongnessCircle) {
                        minWrongnessCircle = this.currentState.getWrongness();
                        placeIndexCircle = i;
                    }
                }
                if (placeIndexCircle == Integer.MAX_VALUE && placeIndexLine == Integer.MAX_VALUE) {

                    findWorstPoint();

                } else {

                    if (minWrongnessCircle + 0.4 < minWrongnessLine) {
                        replacePoint(this.worstPoint, this.circleOptions.get(placeIndexCircle).getXCoordinate(), this.circleOptions.get(placeIndexCircle).getYCoordinate());
                        System.out.println("circle");
                    } else {
                        replacePoint(this.worstPoint, this.lineOptions.get(placeIndexLine).getXCoordinate(), this.lineOptions.get(placeIndexLine).getYCoordinate());
                    }
                }

                this.currentState.refreshWrongness();
                System.out.println(this.currentState.getWrongness());
                for (int i = 0; i < currentState.getPointList().size(); i++) {
                    System.out.println(currentState.getPointList().get(i).getName() + ": " + currentState.getPointList().get(i).getPrimaryPlaceValue());
                }
                findWorstPoint();
                System.out.println(this.worstPoint.getName());
            }
        }
        else System.out.println("You can not draw in this graph on to a plane");
    }



    public void creatingCirclePoints(Graph currentStage){
        currentStage.determinateCenter();
        Point circlePoint;
        int numberOfPoints = currentStage.getPointList().size();
        double maxRad = 0.0;


        for (int i = 0; i < currentStage.getPointList().size(); i++){
            double temp;
            temp = sqrt(Math.pow(currentStage.getPointList().get(i).getXCoordinate() - currentStage.getGraphCenterX(),2) +
                    Math.pow(currentStage.getPointList().get(i).getYCoordinate() - currentStage.getGraphCenterY(),2));
            if (temp > maxRad){
                maxRad = temp;
            }
        }
        maxRad = maxRad * 1.3;
        for(int i = 0; i < numberOfPoints *2; i++){
            double alpha = 360/(double) (numberOfPoints *2);
            alpha = alpha * (i + 1);
            alpha = alpha * PI/180;
            circlePoint = new Point((currentStage.getGraphCenterX() + maxRad*cos(alpha)),
                    currentStage.getGraphCenterY() + maxRad*sin(alpha));
            circleOptions.add(circlePoint);
        }
    }

    public void creatingPointsOnLine(Point start, Point end){
        lineOptions.clear();
        double deltaX, deltaY;
        deltaX = (start.getXCoordinate() - end.getXCoordinate()) / 80;
        deltaY = (start.getYCoordinate() - end.getYCoordinate()) / 80;

        Point linePoint;

        for(int i = 0; i < 80; i++){
            linePoint = new Point(start.getXCoordinate() - (deltaX * i)+0.4, start.getYCoordinate() - (deltaY * i)+0.4);
            if(i > 0 && i < 80) {
                this.lineOptions.add(linePoint);
            }
        }
    }

    private void worstPointListFiller(){
        for (int i = 0; i < currentState.getPointList().size(); i++){
            worstPoints.add(currentState.getPointList().get(i));
        }
    }

    public void findWorstPoint(){
        int worstPos = 0;
        if(!worstPoints.isEmpty()) {
            for (int i = 0; i < worstPoints.size(); i++) {
                if (worstPoints.get(i).getPrimaryPlaceValue() == worstPoints.get(worstPos).getPrimaryPlaceValue()) {
                    if (worstPoints.get(i).getSecondaryPlaceValue() > worstPoints.get(worstPos).getSecondaryPlaceValue()) {
                        worstPos = i;
                    }
                } else if (worstPoints.get(i).getPrimaryPlaceValue() > worstPoints.get(worstPos).getPrimaryPlaceValue()) {
                    worstPos = i;
                }
            }
            this.worstPoint = worstPoints.get(worstPos);
            worstPoints.remove(this.worstPoint);
        }else{
            worstPointListFiller();
            findWorstPoint();
        }
    }

    public void worstPointLines(){
        List<MyLine> helper = new ArrayList<>();
        for(int i = 0; i < currentState.getLineList().size(); i++){
            if(currentState.getLineList().get(i).getStart().equals(this.worstPoint) || currentState.getLineList().get(i).getEnd().equals(this.worstPoint)){
                helper.add(currentState.getLineList().get(i));
            }
        }
        for (MyLine myLine : helper) {
            for (int iter = 0; iter < currentState.getLineList().size(); iter++) {
                if (!myLine.getStart().equals(currentState.getLineList().get(iter).getStart()) &&
                        !myLine.getStart().equals(currentState.getLineList().get(iter).getEnd()) &&
                        !myLine.getEnd().equals(currentState.getLineList().get(iter).getStart()) &&
                        !myLine.getEnd().equals(currentState.getLineList().get(iter).getEnd())) {
                    if (myLine.intersectLines(currentState.getLineList().get(iter))) {
                        this.worstPointLineList.add(myLine);
                    }
                }
            }
        }
    }
    public void findBestPoint(){

        for (MyLine myLine : worstPointLineList) {
            if (!this.worstPoint.equals(myLine.getStart())) {
                if (this.bestPoint.getPrimaryPlaceValue() == myLine.getStart().getPrimaryPlaceValue()
                        && this.bestPoint.getSecondaryPlaceValue() > myLine.getStart().getSecondaryPlaceValue()) {
                    this.bestPoint = myLine.getStart();
                } else if (this.bestPoint.getPrimaryPlaceValue() > myLine.getStart().getPrimaryPlaceValue()) {
                    this.bestPoint = myLine.getStart();
                }
            } else if (!this.worstPoint.equals(myLine.getEnd())) {
                if (this.bestPoint.getPrimaryPlaceValue() == myLine.getEnd().getPrimaryPlaceValue()
                        && this.bestPoint.getSecondaryPlaceValue() > myLine.getEnd().getSecondaryPlaceValue()) {
                    this.bestPoint = myLine.getEnd();
                } else if (this.bestPoint.getPrimaryPlaceValue() > myLine.getEnd().getPrimaryPlaceValue()) {
                    this.bestPoint = myLine.getEnd();
                }
            }
        }
    }

    public void replacePoint(Point point, double x, double y){
        point.setCoordinates(x,y);
    }
}
