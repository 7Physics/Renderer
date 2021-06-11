package fr.setphysics.renderer;

import com.jogamp.opengl.GL2;

/**
 * Sol d'une scène 3D
 */
public class Ground implements Renderable {
    private final double width;
    private final double lineGap;

    /**
     * @param size Dimension du côté du sol
     * @param lineGap Distance entre les lignes du sol
     */
    public Ground(double size, double lineGap) {
        this.width = size;
        this.lineGap = lineGap;
    }

    @Override
    public void render(GL2 gl) {
        // Création de la zone "sol" grisée
        gl.glBegin(GL2.GL_QUADS);

        // Couleur grise
        gl.glColor4f(0.3f, 0.3f, 0.3f, 0.8f);

        // Placement des points du carré
        gl.glVertex3d(width/2, 0f, width/2);
        gl.glVertex3d(width/2, 0f, -width/2);
        gl.glVertex3d(-width/2, 0f, -width/2);
        gl.glVertex3d(-width/2, 0f, width/2);
        gl.glEnd();

        // Création des lignes de la grille
        gl.glLineWidth(1);
        gl.glBegin(GL2.GL_LINES);

        // Couleur Blanche
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        // Placement de chaque ligne
        for (double i = -width/2; i <= width/2; i+= lineGap) {
            gl.glVertex3d(i, 0.01f, width/2);
            gl.glVertex3d(i, 0.01f, -width/2);

            gl.glVertex3d(width/2, 0.01f, i);
            gl.glVertex3d(-width/2, 0.01f, i);
        }
        gl.glEnd();
    }
}
