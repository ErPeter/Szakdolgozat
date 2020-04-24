package erpeter.szakdolgozat;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

@Data
public class SolvingAlgorithm {
    private Graph startStage;
    private Graph currentStage;
    private List<Graph> graphOptions;
    private List<Point> circleOptions;
    private List<Point> lineOptions;
    private List<Point> solution;

    public SolvingAlgorithm(Graph startStage) {
        this.startStage = startStage;
        this.graphOptions = new ArrayList<>();
        this.circleOptions = new ArrayList<>();
        this.lineOptions = new ArrayList<>();
        this.solution = new ArrayList<>();
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
}
