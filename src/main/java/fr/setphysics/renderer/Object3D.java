package fr.setphysics.renderer;

import com.jogamp.opengl.GL2;
import fr.setphysics.common.geom.Position;
import fr.setphysics.common.geom.Shape;
import fr.setphysics.common.geom.Vec3;
import fr.setphysics.common.logger.Logger;

import java.awt.*;
import java.util.List;

public class Object3D implements Renderable, Positionable {
    private final Position position;
    private final Shape shape;
    private Color color;
    private Color edgeColor;

    public Object3D(Position position, Shape shape, Color color, Color edgeColor) {
        this.position = position;
        this.shape = shape;
        this.color = color;
        this.edgeColor = edgeColor;
    }

    public Object3D(Position position, Shape shape, Color color) {
        this(position, shape, color, null);
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

    public void setColor(Color color) {
        this.color = color;
    }

    public void setEdgeColor(Color edgeColor) {
        this.edgeColor = edgeColor;
    }

    public Color getEdgeColor() {
        return edgeColor;
    }

    @Override
    public void render(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);

        List<Vec3> vertices = shape.getVertices();


        // Dessin du carré
        gl.glColor4f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, color.getAlpha()/255f);
        for (Vec3 vertex : vertices) {
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
