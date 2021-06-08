package fr.setphysics.renderer;

import com.jogamp.opengl.GL2;
import fr.setphysics.common.geom.Bounds;
import fr.setphysics.common.geom.Position;
import fr.setphysics.common.geom.Shape;
import fr.setphysics.common.geom.Vec3;

import java.awt.*;
import java.util.List;

public class Object3D implements Renderable, Positionable {
    private final Position position;
    private final Shape shape;
    private Color color;
    private Color edgeColor;

    public Object3D(Shape shape, Position position, Color color, Color edgeColor) {
        this.shape = shape;
        this.position = position;
        this.color = color;
        this.edgeColor = edgeColor;
    }

    public Object3D(Shape shape, Position position, Color color) {
        this(shape, position, color, null);
    }

    public Object3D(Shape shape, Position position) {
        this(shape, position, Color.GRAY, Color.WHITE);
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
        gl.glBegin(GL2.GL_QUADS);

        List<Vec3> vertices = shape.getVertices();

        Bounds bounds = shape.getBounds();
        // Dessin du carré
        for (Vec3 vertex : vertices) {
            double factor = (vertex.getY()-bounds.getMinY())/ bounds.getHeight();
            gl.glColor4d((color.getRed()/255f)*factor, (color.getGreen()/255f)*factor, (color.getBlue()/255f)*factor, color.getAlpha()/255f);
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
            vertex = vertex.clone();
            vertex.addX(vertex.getX()/Math.abs(vertex.getX())*0.002);
            vertex.addY(vertex.getY()/Math.abs(vertex.getY())*0.002);
            vertex.addZ(vertex.getZ()/Math.abs(vertex.getZ())*0.002);
            vertex = position.getCoords().add(vertex.clone());
            if(last != null) {
                gl.glVertex3d(last.getX(), last.getY(), last.getZ());
                gl.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
            }
            last = vertex;
        }
        if(last != null) {
            gl.glVertex3d(last.getX(), last.getY(), last.getZ());
            Vec3 vertex = position.getCoords().add(vertices.get(0).clone());
            vertex.addX(vertex.getX()/Math.abs(vertex.getX())*0.002);
            vertex.addY(vertex.getY()/Math.abs(vertex.getY())*0.002);
            vertex.addZ(vertex.getZ()/Math.abs(vertex.getZ())*0.002);
            gl.glVertex3d(vertex.getX(), vertex.getY(), vertex.getZ());
        }
        gl.glEnd();

    }
}
