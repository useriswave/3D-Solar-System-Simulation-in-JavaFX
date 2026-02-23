package org.example.solarsystemsimulationfx.pivots;

import javafx.scene.Group;
import javafx.scene.Scene;
import org.example.solarsystemsimulationfx.planetsystem.Planet;

public class PivotGroup extends Group{

    private final Planet planet;

    public PivotGroup(Planet planet, int pathRadius) {
        this.planet = planet;
        planet.setTranslateX(pathRadius);
        this.getChildren().addAll(planet);
    }

    public PivotGroup(Planet planet) {
        this.planet = planet;
        this.getChildren().addAll(planet);
    }

    public void centerPlanet(Scene scene) {
        this.translateXProperty().bind(scene.widthProperty().divide(2));
        this.translateYProperty().bind(scene.heightProperty().divide(2));
    }
}
