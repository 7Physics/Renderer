package fr.setphysics.renderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;


/**
 * La classe WorldRenderer réalise l'affichage du monde 3D créé par l'utilisateur.
 * @author pierre
 *
 */
public class WorldRenderer implements GLEventListener, KeyListener {
	private GLU glu = new GLU();

	/**
	 * Attributs représentants les valeurs de rotation selon les 3 axes.
	 * Ces valeurs sont utilisées afin de manipuler la caméra.
	 */
	private float rotateX, rotateY, rotateZ;
	
	/**
	 * Attributs représentants les valeurs de déplacement linéaire selon les 3 axes.
	 * Ces valeurs sont utilisées afin de manipuler la caméra
	 */
	private float zoom, transX, transY;

	@Override
	public void init(GLAutoDrawable drawable) {
		// Récupération du contexte OpenGL
		final GL2 gl = drawable.getGL().getGL2();

        // Activation de la transparence
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// Récupération du contexte OpenGL
		final GL2 gl = drawable.getGL().getGL2();
        gl.glLoadIdentity();
        
        // Déplacement de la caméra selon les entrées utilisateurs
        gl.glTranslatef(transX, transY, zoom-5.0f);
        gl.glRotatef(rotateX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateY, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateZ, 0.0f, 0.0f, 1.0f);

        // Clear de la scène 3D
        gl.glClearColor(0.195f, 0.590f, 0.656f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        // Création de la zone "sol" grisée
        gl.glBegin(GL2.GL_QUADS);

        // Couleur grise
        gl.glColor4f(0.3f, 0.3f, 0.3f, 0.8f);

        // Placement des points du carré
        gl.glVertex3f(2.0f, 0f, 2.0f);
        gl.glVertex3f(2.0f, 0f, -2.0f);
        gl.glVertex3f(-2.0f, 0f, -2.0f);
        gl.glVertex3f(-2.0f, 0f, 2.0f);
        gl.glEnd();

        // Création des lignes de la grille
        gl.glBegin(GL2.GL_LINES);

        // Couleur Blanche
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        
        // Placement de chaque ligne
        for(int i = 0; i <= 20; i++) {
            gl.glVertex3f((i/5f)-2f, 0f, 2f);
            gl.glVertex3f((i/5f)-2f, 0f, -2f);

            gl.glVertex3f(2f, 0f, (i/5f)-2f);
            gl.glVertex3f(-2f, 0f, (i/5f)-2f);
        }
        gl.glEnd();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();

		if (height <= 0)
			height = 1;

		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		glu.gluPerspective(45.0f, h, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Modification des attributs de la caméra selon la touche utilisée
		switch (e.getKeyChar()) {
		case 'a': // ZOOM IN
			zoom += 0.1f;
			break;
		case 'e': // ZOOM OUT
			zoom -= 0.1f;
			break;
		case 'q': // Déplacement vers la gauche
			transX += 0.1f;
			break;
		case 'd': // Déplacement vers la droite
			transX -= 0.1f;
			break;
		case 's': // Déplacement vers le bas
			transY += 0.1f;
			break;
		case 'z': // Déplacement vers le haut
			transY -= 0.1f;
			break;
		case 'o': // Rotation vers le haut
			rotateX += 1.0f;
			break;
		case 'p': // Rotation vers le bas
			rotateX -= 1.0f;
			break;
		case 'l': // Rotation vers la gauche
			rotateY += 1.0f;
			break;
		case 'm': // Rotation vers la droite
			rotateY -= 1.0f;
			break;
		case ';': // Rotation de la scène sur elle-même
			rotateZ += 1.0f;
			break;
		case ':': // Rotation de la scène sur elle-même
			rotateZ -= 1.0f;
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
