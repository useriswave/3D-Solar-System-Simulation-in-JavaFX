package org.example.solarsystemsimulationfx.main;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import org.example.solarsystemsimulationfx.controller.OrbitController;
import org.example.solarsystemsimulationfx.planetsystem.Galaxy;
import org.example.solarsystemsimulationfx.planetsystem.OrbitPath;
import org.example.solarsystemsimulationfx.pivots.PivotGroup;
import org.example.solarsystemsimulationfx.planetsystem.Planet;
import org.example.solarsystemsimulationfx.planetsystem.PlanetarySystem;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private final Rotate rotateX = new Rotate(20, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Camera cam = new PerspectiveCamera();
        cam.setTranslateZ(-1000);

        Galaxy milkyWay = new Galaxy();

        Group world = new Group();
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        world.getChildren().add(ambientLight);
        world.getChildren().add(milkyWay);
        world.getTransforms().addAll(rotateX, rotateY);


        Planet sun = new Planet(100, 0.5f, 0);

        Image sunMap = new Image(Objects.requireNonNull(getClass()
                .getResource("/org/example/solarsystemsimulationfx/diffusion/sun.jpg")).toExternalForm(), 2048, 1024, true, true);
        PhongMaterial sunMaterial = new PhongMaterial();
        sunMaterial.setDiffuseMap(sunMap);
        sun.setMaterial(sunMaterial);

        PivotGroup sunPivot = new PivotGroup(sun);

        Group solarSystem = new Group(sunPivot);
        Scene scene = new Scene(root, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);


        double[] planetRadiusList = {7.0, 11.0, 11.5, 8.5, 36.0, 32.5, 21.5, 21.0};
        int[] orbitPathRadiusList = {150, 200, 270, 350, 550, 750, 950 , 1150};
        float[] orbitSpeedList = {0.3f,0.22f, 0.19f, 0.15f, 0.083f, 0.062f, 0.043f, 0.034f};
        float[] rotateSpeedList = {0.00078f, 0.00046f, 0.119f, 0.062f, 3.214f, 2.536f, 0.064f, 0.693f};
        Image[] maps = {
                new Image(Objects.requireNonNull(getClass()
                .getResource("/org/example/solarsystemsimulationfx/diffusion/mercury.jpg")).toExternalForm(), 2048, 1024, true, true),

                new Image(Objects.requireNonNull(getClass()
                        .getResource("/org/example/solarsystemsimulationfx/diffusion/venus.jpg")).toExternalForm(), 2048, 1024, true, true),

                new Image(Objects.requireNonNull(getClass()
                        .getResource("/org/example/solarsystemsimulationfx/diffusion/earth_daytime.jpg")).toExternalForm(), 2048, 1024, true, true),

                new Image(Objects.requireNonNull(getClass()
                        .getResource("/org/example/solarsystemsimulationfx/diffusion/mars.jpg")).toExternalForm(), 2048, 1024, true, true),

                new Image(Objects.requireNonNull(getClass()
                        .getResource("/org/example/solarsystemsimulationfx/diffusion/jupiter.jpg")).toExternalForm(), 2048, 1024, true, true),

                new Image(Objects.requireNonNull(getClass()
                        .getResource("/org/example/solarsystemsimulationfx/diffusion/saturn.jpg")).toExternalForm(), 2048, 1024, true, true),

                new Image(Objects.requireNonNull(getClass()
                        .getResource("/org/example/solarsystemsimulationfx/diffusion/uranus.jpg")).toExternalForm(), 2048, 1024, true, true),

                new Image(Objects.requireNonNull(getClass()
                        .getResource("/org/example/solarsystemsimulationfx/diffusion/neptune.jpg")).toExternalForm(), 2048, 1024, true, true)
        };

        Color[] pathColorList = {Color.GRAY, Color.PALEGOLDENROD, Color.DODGERBLUE, Color.TOMATO, Color.BURLYWOOD, Color.LIGHTYELLOW, Color.LIGHTBLUE, Color.CORNFLOWERBLUE};

        final int planetCount = 8;
        for(int i = 0; i < planetCount; i++) {
            var planet = createPlanetarySystem(planetRadiusList[i], orbitPathRadiusList[i], rotateSpeedList[i],orbitSpeedList[i], pathColorList[i], scene);
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseMap(maps[i]);
            planet.getPlanet().setMaterial(material);
            solarSystem.getChildren().add(planet.getRoot());

        }
        world.getChildren().addAll(solarSystem);
        root.getChildren().add(world);

        scene.setCamera(cam);

        world.translateXProperty().bind(scene.widthProperty().divide(2));
        world.translateYProperty().bind(scene.heightProperty().divide(2));

        initMouseMovement(scene, cam);

        stage.setScene(scene);
        stage.setTitle("Wave Solar System Simulation");
        stage.show();
    }

    private PlanetarySystem createPlanetarySystem(double planetRadius, int orbitPathRadius, float rotationSpeed, float orbitSpeed, Color strokeColor, Scene scene) {
        var planet = new Planet(planetRadius, rotationSpeed, orbitSpeed);
        planet.setOrbitSpeed(orbitSpeed);

        var orbitPath = new OrbitPath(orbitPathRadius, strokeColor, scene);
        orbitPath.setMouseTransparent(true);
        orbitPath.setRotationAxis(Rotate.X_AXIS);
        orbitPath.setRotate(90);


        var pivotGroup = new PivotGroup(planet, orbitPathRadius);

        var orbitController = new OrbitController(planet, orbitPath, pivotGroup);
        orbitController.orbitPlanet();

        return new PlanetarySystem(planet, new Group(orbitPath, pivotGroup));
    }

    private void initMouseMovement(Scene scene, Camera camera) {
        // rotate on drag
        scene.setOnMousePressed((MouseEvent event) -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = rotateX.getAngle();
            anchorAngleY = rotateY.getAngle();

        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            rotateX.setAngle(anchorAngleX + (event.getSceneY() - anchorY) * 0.5);
            rotateY.setAngle(anchorAngleY - (event.getSceneX() - anchorX) * 0.5);
        });

        scene.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.1;
            double deltaY = event.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 1 / zoomFactor;
            }

            // Moving along z axis
            double newZ = camera.getTranslateZ() * zoomFactor;
            newZ = Math.max(newZ, -5000);
            newZ = Math.min(newZ, -200);
            camera.setTranslateZ(newZ);
        });
    }
}