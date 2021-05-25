package fr.setphysics.renderer;

import fr.setphysics.common.geom.Position;
import fr.setphysics.common.geom.Vec3;

/**
 * Contient les valeurs de rotation selon les 3 axes et les valeurs de déplacement linéaire selon les 3 axes.
 * Ces valeurs sont utilisées afin de manipuler la caméra
 */
public class Camera implements Positionable {
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

    public void moveForward(double offset) {
        Vec3 vec3Offset = getPosition().getCoords().add(getLookAt().scale(-1));
        getPosition().translate(vec3Offset.scale(-offset));
    }

    public void moveBackward(double offset) {
        moveForward(-offset);
    }

    public void moveRight(double offset) {
        getPosition().translateX(Math.cos(position.getHorizontalAngle()+Math.PI/2)*offset);
        getPosition().translateZ(Math.sin(position.getHorizontalAngle()+Math.PI/2)*offset);
    }

    public void moveLeft(double offset) {
        moveRight(-offset);
    }

    public double getLookAtX() {
        return position.getX()
                + Math.cos(position.getHorizontalAngle()) * Math.cos(position.getVerticalAngle());
    }

    public double getLookAtY() {
        return position.getY()
                + Math.sin(position.getVerticalAngle());
    }

    public double getLookAtZ() {
        return position.getZ()
                + Math.sin(position.getHorizontalAngle()) * Math.cos(position.getVerticalAngle());
    }

    public Vec3 getLookAt() {
        return new Vec3(getLookAtX(), getLookAtY(), getLookAtZ());
    }
}
