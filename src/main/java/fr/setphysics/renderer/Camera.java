package fr.setphysics.renderer;

import fr.setphysics.common.geom.Position;
import fr.setphysics.common.geom.Positionable;
import fr.setphysics.common.geom.Vec3;

/**
 * Caméra visualisant une scène 3D
 * Contient les valeurs de rotation selon les 3 axes et les valeurs de déplacement linéaire selon les 3 axes.
 * Ces valeurs sont utilisées afin de manipuler la caméra
 */
public class Camera implements Positionable {
    private final Position position;
    private double fov = 45;

    /**
     * Crée une nouvelle caméra
     * @param position Position de la caméra
     */
    public Camera(Position position) {
        this.position = position;
    }

    /**
     * @return Position de la caméra
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return Nniveau de zoom de la caméra (field of view).
     */
    public double getFov() {
        return fov;
    }

    /**
     * Définit le niveau de zoom (field of view).
     * @param fov Niveau de zoom de la céméra.
     */
    public void setFov(double fov) {
        this.fov = fov;
    }

    /**
     * Incrémente le niveau de zoom.
     * @param factor Incrément du niveau de zoom de la céméra.
     */
    public void zoom(double factor) {
        fov += factor;
    }

    /**
     * Zoom d'un niveau.
     */
    public void zoom() {
        fov--;
    }

    /**
     * Dezoom d'un niveau.
     */
    public void dezoom() {
        fov++;
    }

    /**
     * Déplace la caméra vers devant
     * @param offset Distance de déplacement
     */
    public void moveForward(double offset) {
        Vec3 vec3Offset = getPosition().getCoords().add(getLookAt().scale(-1));
        getPosition().translate(vec3Offset.scale(-offset));
    }

    /**
     * Déplace la caméra vers derrière.
     * @param offset Distance de déplacement
     */
    public void moveBackward(double offset) {
        moveForward(-offset);
    }

    /**
     * Déplace la caméra vers la droite
     * @param offset Distance de déplacement
     */
    public void moveRight(double offset) {
        getPosition().translateX(Math.cos(position.getHorizontalAngle()+Math.PI/2)*offset);
        getPosition().translateZ(Math.sin(position.getHorizontalAngle()+Math.PI/2)*offset);
    }

    /**
     * Déplace la caméra vers la gauche
     * @param offset Distance de déplacement
     */
    public void moveLeft(double offset) {
        moveRight(-offset);
    }

    /**
     * @return Coordonnée X de l'endroit regardé (à un mettre de distance du centre de la caméra).
     */
    public double getLookAtX() {
        return position.getX()
                + Math.cos(position.getHorizontalAngle()) * Math.cos(position.getVerticalAngle());
    }

    /**
     * @return Coordonnée Y de l'endroit regardé (à un mettre de distance du centre de la caméra).
     */
    public double getLookAtY() {
        return position.getY()
                + Math.sin(position.getVerticalAngle());
    }

    /**
     * @return Coordonnée Z de l'endroit regardé (à un mettre de distance du centre de la caméra).
     */
    public double getLookAtZ() {
        return position.getZ()
                + Math.sin(position.getHorizontalAngle()) * Math.cos(position.getVerticalAngle());
    }

    /**
     * @return Vecteur position de l'endroit regardé (à un mettre de distance du centre de la caméra).
     */
    public Vec3 getLookAt() {
        return new Vec3(getLookAtX(), getLookAtY(), getLookAtZ());
    }

    /**
     * Tourne l'objet verticalement.
     * @param angleRadians Angle en radians.
     */
    public void rotateVertical(double angleRadians) {
        getPosition().rotateVertical(angleRadians);
    }

    /**
     * Tourne l'objet horizontalement.
     * @param angleRadians Angle en radians.
     */
    public void rotateHorizontal(double angleRadians) {
        getPosition().rotateHorizontal(angleRadians);
    }
}
