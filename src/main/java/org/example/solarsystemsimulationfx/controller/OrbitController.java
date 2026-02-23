package org.example.solarsystemsimulationfx.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.transform.Rotate;
import org.example.solarsystemsimulationfx.planetsystem.OrbitPath;
import org.example.solarsystemsimulationfx.pivots.PivotGroup;
import org.example.solarsystemsimulationfx.planetsystem.Planet;

public class OrbitController {
    private final Planet planet;
    private final OrbitPath orbitPath;
    private final Group pivotGroup;
    public OrbitController(Planet planet, OrbitPath orbitPath, PivotGroup pivotGroup) {
        this.planet = planet;
        this.orbitPath = orbitPath;
        this.pivotGroup = pivotGroup;
    }

    public void orbitPlanet() {
        Rotate pivotRotation = new Rotate(Math.random() * 360, Rotate.Y_AXIS);
        pivotGroup.getTransforms().addAll(pivotRotation);
        var timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                pivotRotation.setAngle(pivotRotation.getAngle() + planet.getOrbitSpeed());
            }
        };
        timer.start();
    }
}
