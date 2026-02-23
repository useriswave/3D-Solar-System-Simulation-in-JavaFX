package org.example.solarsystemsimulationfx.planetsystem;

import javafx.scene.Group;

public class PlanetarySystem {
    private final Planet planet;
    private final Group root;

    public PlanetarySystem(Planet planet, Group root) {
        this.planet = planet;
        this.root = root;
    }

    public Planet getPlanet() {
        return planet;
    }

    public Group getRoot() {
        return root;
    }
}