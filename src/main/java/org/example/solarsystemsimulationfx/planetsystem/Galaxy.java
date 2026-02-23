package org.example.solarsystemsimulationfx.planetsystem;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Sphere;

import java.util.Objects;

public class Galaxy extends Sphere {



    public Galaxy() {
        this.setRadius(4000);
        Image galaxyMap = new Image(
                Objects.requireNonNull(getClass()
                                .getResource("/org/example/solarsystemsimulationfx/diffusion/milky_way.jpg"))
                        .toExternalForm(),
                4096, 2048, true, true
        );

        PhongMaterial galaxyMaterial = new PhongMaterial();
        galaxyMaterial.setDiffuseMap(galaxyMap);
        this.setMaterial(galaxyMaterial);

        this.setCullFace(CullFace.FRONT);
    }
}
