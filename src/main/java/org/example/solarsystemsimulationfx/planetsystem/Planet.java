package org.example.solarsystemsimulationfx.planetsystem;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class Planet extends Sphere {

    private float rotationSpeed;
    private float orbitSpeed;

    public Planet(double radius) {this.setRadius(radius);};

    public Planet(double radius, float rotationSpeed, float orbitSpeed) {
        this.rotationSpeed = rotationSpeed;
        this.orbitSpeed = orbitSpeed;
        this.setRadius(radius);
        planetaryRotation(this);
    }

    private void planetaryRotation(Planet p) {
        Rotate planetRotate = new Rotate(0, Rotate.Y_AXIS);
        p.getTransforms().add(planetRotate);
        var timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                planetRotate.setAngle(planetRotate.getAngle() + 0.2);
            }
        };
        timer.start();
    }

    public float getOrbitSpeed() {
        return orbitSpeed;
    }

    public void setOrbitSpeed(float orbitSpeed) {
        this.orbitSpeed = orbitSpeed;
    }
}
