package erpeter.szakdolgozat;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

@Data
public class SolvingAlgorithm {
    private Graph startState;
    private Graph currentState = new Graph();
    private Point worstPoint;
    private List<MyLine> worstPointLineList;
    private Point bestPoint; //must be on the same line with worstPoint
    private List<Graph> graphOptions;
    private List<Point> circleOptions;
    private List<Point> lineOptions;
    private List<Point> solution;

    public SolvingAlgorithm(Graph startState) {
        this.startState = startState;
        this.graphOptions = new ArrayList<>();
        this.worstPointLineList = new ArrayList<>();
        this.circleOptions = new ArrayList<>();
        this.lineOptions = new ArrayList<>();
        this.solution = new ArrayList<>();
        this.currentState = this.startState;
        findWorstPoint();
        worstPointLines();
        for (MyLine myLine : worstPointLineList) {
            System.out.println(myLine.getStart().getName() + " , " + myLine.getEnd().getName());
        }
        this.bestPoint = this.worstPoint;
        findBestPoint();
        System.out.println(this.bestPoint.getName());
        creatingPointsOnLine(this.worstPoint, this.bestPoint);
    }


    public void solve(){
        this.graphOptions.add(replacePoint(500,600));
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
        System.out.println(maxRad);

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
        double deltaX, deltaY;
        deltaX = (start.getXCoordinate() - end.getXCoordinate()) / 6;
        deltaY = (start.getYCoordinate() - end.getYCoordinate()) / 6;

        Point linePoint;

        for(int i = 0; i < 6; i++){
            linePoint = new Point(start.getXCoordinate() - (deltaX * i), start.getYCoordinate() - (deltaY * i));
            this.lineOptions.add(linePoint);
        }
    }

    public void findWorstPoint(){
        int worstPos = 0;
        for (int i = 0; i < currentState.getPointList().size(); i++){
            if(currentState.getPointList().get(i).getPrimaryPlaceValue() == currentState.getPointList().get(worstPos).getPrimaryPlaceValue()){
                if(currentState.getPointList().get(i).getSecondaryPlaceValue() > currentState.getPointList().get(worstPos).getSecondaryPlaceValue()){
                    worstPos = i;
                }
            } else if (currentState.getPointList().get(i).getPrimaryPlaceValue() > currentState.getPointList().get(worstPos).getPrimaryPlaceValue()){
                worstPos = i;
            }
        }
         this.worstPoint = currentState.getPointList().get(worstPos);
    }

    public void worstPointLines(){
        List<MyLine> helper = new ArrayList<>();
        for(int i = 0; i < currentState.getLineList().size(); i++){
            if(currentState.getLineList().get(i).getStart().equals(this.worstPoint) || currentState.getLineList().get(i).getEnd().equals(this.worstPoint)){
                helper.add(currentState.getLineList().get(i));
            }
        }
        for (MyLine line : helper) {
            System.out.println(line.getStart().getName() + " , " + line.getEnd().getName());
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

    public Graph replacePoint(double x, double y){
        Graph option = new Graph();
        
        for(int i = 0; i < option.getPointList().size(); i++){
            if(option.getPointList().get(i).getName().equals(this.worstPoint)){
                option.getPointList().get(i).setCoordinates(x,y);
                System.out.println(option.getPointList().get(i).getXCoordinate() + " " + option.getPointList().get(i).getYCoordinate());
            }
        }
        return option;
    }
}
