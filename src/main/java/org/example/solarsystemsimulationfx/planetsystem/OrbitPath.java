package org.example.solarsystemsimulationfx.planetsystem;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class OrbitPath extends Circle {

    public OrbitPath(int radius,Color strokeColor ,Scene scene) {
        this.setFill(Color.TRANSPARENT);
        this.setStrokeWidth(1);
        this.setStroke(strokeColor);

        this.setRadius(radius);
    }
}
