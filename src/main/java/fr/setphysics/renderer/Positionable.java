package fr.setphysics.renderer;

import fr.setphysics.common.geom.Position;
import fr.setphysics.common.geom.Vec3;

/**
 * Représente un objet possédant une position.
 * Sucre syntaxique pour appeler les méthodes de changement de position sur l'objet lui-même plutôt que sur son objet Position.
 */
public interface Positionable {
    /**
     * Retourne la position de l'objet
     * @return Position de l'objet
     */
    Position getPosition();

    /**
     * Renvoi l'angle de l'objet sur l'abscisse
     * @return horizontalAngle
     */
    default double getHorizontalAngle() {
        return getPosition().getHorizontalAngle();
    }

    /**
     * Renvoi l'angle de l'objet sur l'ordonnée
     * @return verticalAngle
     */
    default double getVerticalAngle() {
        return getPosition().getVerticalAngle();
    }

    /**
     * Changer la valeur des angles
     * @param horizontalAngle double
     * @param verticalAngle double
     */
    default void setAngles(double horizontalAngle, double verticalAngle) {
        getPosition().setAngles(horizontalAngle, verticalAngle);
    }

    /**
     * Renvoi une copie du vecteur coordonnées de l'objet
     * @return Coordonnées de l'objet
     */
    default Vec3 getCoords() {
        return getPosition().getCoords().clone();
    }

    /**
     * Renvoie l'abscisse de l'objet
     * @return Abscisse de l'objet
     */
    default double getX() {
        return getPosition().getX();
    }

    /**
     * Renvoie l'ordonnée de l'objet
     * @return Ordonnée de l'objet
     */
    default double getY() {
        return getPosition().getY();
    }

    /**
     * Renvoie la profondeur de l'objet
     * @return Profondeur de l'objet
     */
    default double getZ() {
        return getPosition().getZ();
    }

    /**
     * Change les coordonnées de l'objet
     * @param coords Vec3
     */
    default void setCoords(Vec3 coords) {
        getPosition().setCoords(coords);
    }

    /**
     * Translate la position de l'objet
     * @param translation translation
     */
    default void translate(Vec3 translation) {
        getPosition().translate(translation);
    }

    /**
     * Translate la position de l'objet sur l'axe X
     * @param offset Décalage
     */
    default void translateX(double offset) {
        getPosition().translateX(offset);
    }

    /**
     * Translate la position de l'objet sur l'axe Y
     * @param offset Décalage
     */
    default void translateY(double offset) {
        getPosition().translateY(offset);
    }

    /**
     * Translate la position de l'objet sur l'axe X
     * @param offset Décalage
     */
    default void translateZ(double offset) {
        getPosition().translateZ(offset);
    }

    /**
     * Tourne l'objet verticalement.
     * @param angle Angle en degrés.
     */
    default void rotateVertical(double angle) {
        getPosition().rotateVertical(angle);
    }

    /**
     * Tourne l'objet horizontalement.
     * @param angle Angle en degrés.
     */
    default void rotateHorizontal(double angle) {
        getPosition().rotateHorizontal(angle);
    }

    /**
     * Tourne l'objet.
     * @param hAngle Angle horizontal en degrés.
     * @param vAngle Angle vertical en degrés.
     */
    default void rotate(double hAngle, double vAngle) {
        getPosition().rotate(hAngle, vAngle);
    }
}
