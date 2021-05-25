package fr.setphysics.renderer;

import com.jogamp.opengl.GL2;
import fr.setphysics.common.geom.Position;
import fr.setphysics.common.geom.Shape;
import fr.setphysics.common.geom.Vec3;

import java.awt.*;
import java.util.List;

public class Object3D implements Renderable, Positionable {
    private final Position position;
    private final Shape shape;
    private final Color color;
    private final Color edgeColor;

    public Object3D(Position position, Shape shape, Color color, Color edgeColor) {
        this.position = position;
        this.shape = shape;
        this.color = color;
        this.edgeColor = edgeColor;
    }

    public Object3D(Position position, Shape shape) {
        this(position, shape, Color.GRAY, Color.WHITE);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void render(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);

        List<Vec3> vertices = shape.getVertices();

        // Dessin du carré
        gl.glColor4f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f);
        for (Vec3 vertex : vertices) {
            vertex = position.getCoords().add(vertex);
            gl.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
        }
        gl.glEnd();

        // Dessin des arêtes
        gl.glLineWidth(2);
        gl.glBegin(GL2.GL_LINES);
        gl.glColor4f(edgeColor.getRed()/255f, edgeColor.getGreen()/255f, edgeColor.getBlue()/255f, edgeColor.getAlpha()/255f);
        Vec3 last = null;
        for (Vec3 vertex : vertices) {
            vertex = position.getCoords().add(vertex);
            if(last != null) {
                gl.glVertex3d(last.getX(), last.getY(), last.getZ());
                gl.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
            }
            last = vertex;
        }
        if(last != null) {
            gl.glVertex3d(last.getX(), last.getY(), last.getZ());
            Vec3 vertex = position.getCoords().add(vertices.get(0));
            gl.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
        }
        gl.glEnd();

    }
}