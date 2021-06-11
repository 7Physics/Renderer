package fr.setphysics.renderer;

import com.jogamp.opengl.GL2;
import fr.setphysics.common.geom.Bounds;
import fr.setphysics.common.geom.Position;
import fr.setphysics.common.geom.Shape;
import fr.setphysics.common.geom.Vec3;

import java.awt.*;
import java.util.List;

public class Object3D implements Renderable, Positionable {
    private Position position;
    private Shape shape;

    private Color topColor;
    private Color bottomColor;

    private Color edgeColor;

    /**
     * Crée un nouvel objet 3D en vue de l'ajouter à une {@link fr.setphysics.renderer.Scene3D}
     * @param shape Forme de l'objet
     * @param position Position du milieu l'objet
     * @param color Couleur de l'objet (la couleur du haut sera 33% plus clair et celle du bas 33% plus foncée).
     * @param edgeColor Couleur des arêtes de l'objet
     */
    public Object3D(Shape shape, Position position, Color color, Color edgeColor) {
        this.shape = shape;
        this.position = position;
        this.edgeColor = edgeColor;
        setColor(color);
    }

    /**
     * Crée un nouvel objet 3D en vue de l'ajouter à une {@link fr.setphysics.renderer.Scene3D}
     * @param shape Forme de l'objet
     * @param position Position du milieu l'objet
     * @param color Couleur de l'objet (la couleur du haut sera 33% plus clair et celle du bas 33% plus foncée).
     */
    public Object3D(Shape shape, Position position, Color color) {
        this(shape, position, color, null);
    }

    /**
     * Crée un nouvel objet 3D en vue de l'ajouter à une {@link fr.setphysics.renderer.Scene3D}
     * @param shape Forme de l'objet
     * @param position Position du milieu l'objet
     */
    public Object3D(Shape shape, Position position) {
        this(shape, position, Color.GRAY);
    }

    /**
     * Crée un nouvel objet 3D en vue de l'ajouter à une {@link fr.setphysics.renderer.Scene3D}
     * @param shape Forme de l'objet
     */
    public Object3D(Shape shape) {
        this(shape, new Position(0, 0, 0));
    }


    /**
     * @return Position de l'objet
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * @return Forme de l'objet
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * @return Couleur globale de l'objet (moyenne entre sa couleur en haut et celle en bas).
     */
    public Color getColor() {
        return new Color(
                (topColor.getRed() + bottomColor.getRed()) / 2,
                (topColor.getGreen() + bottomColor.getGreen()) / 2,
                (topColor.getBlue() + bottomColor.getBlue()) / 2,
                (topColor.getAlpha() + bottomColor.getAlpha()) / 2
        );
    }

    /**
     * @return Couleur des arêtes ou null si invisibles.
     */
    public Color getEdgeColor() {
        return edgeColor;
    }

    /**
     * Modifie la forme de l'objet
     * @return this
     */
    public Object3D setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    /**
     * Modifie la position de l'objet.
     * @return this
     */
    public Object3D setPosition(Position position) {
        this.position = position;
        return this;
    }

    /**
     * Modifie la couleur de l'objet
     * @param color Nouvelle couleur (la couleur du haut sera 33% plus clair et celle du bas 33% plus foncée).
     * @return this
     */
    public Object3D setColor(Color color) {
        // Stockés en attributs pour éviter les calculs inutiles :
        topColor = new Color((int) Math.min(color.getRed() * 1.33, 255), (int) Math.min(color.getGreen() * 1.33, 255),
                (int) Math.min(color.getBlue() * 1.33, 255), color.getAlpha());
        bottomColor = new Color((int) (color.getRed() * 0.66), (int) (color.getGreen() * 0.66), (int) (color.getBlue() * 0.66),
                color.getAlpha());
        return this;
    }

    /**
     * Modifie la couleur de l'objet en spécifiant le dégradé voulu.
     * @param topColor Couleur du haut de la forme.
     * @param bottomColor Couleur du bas de la forme
     * @return this
     */
    public Object3D setColor(Color topColor, Color bottomColor) {
        this.topColor = topColor;
        this.bottomColor = bottomColor;
        return this;
    }

    /**
     * Modifie la couleur des arêtes de le forme.
     * @param edgeColor Nouvelle couleur ou null pour ne pas afficher les arêtes.
     * @return this
     */
    public Object3D setEdgeColor(Color edgeColor) {
        this.edgeColor = edgeColor;
        return this;
    }

    /**
     * Effectue le rendu de l'objet sur le contexte OpenGL précisé.
     * @param gl Contexte OpenGL
     */
    @Override
    public void render(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);

        List<Vec3> vertices = shape.getVertices();

        Bounds bounds = shape.getBounds();
        // Dessin du carré
        for (Vec3 vertex : vertices) {
            double factor = (vertex.getY() - bounds.getMinY()) / bounds.getHeight();
            gl.glColor4d((topColor.getRed() / 255f) * factor + (bottomColor.getRed() / 255f) * (1 - factor),
                    (topColor.getGreen() / 255f) * factor + (bottomColor.getGreen() / 255f) * (1 - factor),
                    (topColor.getBlue() / 255f) * factor + (bottomColor.getBlue() / 255f) * (1 - factor),
                    (topColor.getAlpha() / 255f) * factor + (bottomColor.getAlpha() / 255f) * (1 - factor));
            vertex = position.getCoords().add(vertex);
            gl.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
        }
        gl.glEnd();

        if(edgeColor == null) {
            return;
        }

        // Dessin des aretes

        gl.glLineWidth(2);
        gl.glBegin(GL2.GL_LINES);
        gl.glColor4f(edgeColor.getRed()/255f, edgeColor.getGreen()/255f, edgeColor.getBlue()/255f, edgeColor.getAlpha()/255f);
        Vec3 last = null;
        for (Vec3 vertex : vertices) {
            vertex = increase(vertex.clone(), 0.002f);
            vertex = position.getCoords().add(vertex);

            if(last != null) {
                gl.glVertex3d(last.getX(), last.getY(), last.getZ());
                gl.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
            }
            last = vertex;
        }
        if(last != null) {
            gl.glVertex3d(last.getX(), last.getY(), last.getZ());
            Vec3 vertex = increase(position.getCoords().add(vertices.get(0)), 0.002f);
            gl.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
        }
        gl.glEnd();

    }

    private Vec3 increase(Vec3 vertex, float factor) {
        if(vertex.getX() != 0)
            vertex.addX(vertex.getX()/Math.abs(vertex.getX())*factor);
        if(vertex.getY() != 0)
            vertex.addY(vertex.getY()/Math.abs(vertex.getY())*factor);
        if(vertex.getZ() != 0)
            vertex.addZ(vertex.getZ()/Math.abs(vertex.getZ())*factor);
        return vertex;
    }
}
