package fr.setphysics.renderer;

import fr.setphysics.common.geom.Position;

/**
 * Contient les valeurs de rotation selon les 3 axes et les valeurs de déplacement linéaire selon les 3 axes.
 * Ces valeurs sont utilisées afin de manipuler la caméra
 */
public class Camera {
    private final Position position;
    private double zoomFactor;

    public Camera(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void zoom() {
        this.zoomFactor += .1;
    }

    public void dezoom() {
        this.zoomFactor -= .1;
    }

    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }
}
