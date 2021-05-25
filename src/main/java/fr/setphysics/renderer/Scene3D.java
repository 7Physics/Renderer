package fr.setphysics.renderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import fr.setphysics.common.geom.Position;


/**
 * La classe WorldRenderer réalise l'affichage du monde 3D créé par l'utilisateur.
 * @author pierre
 *
 */
public class Scene3D implements GLEventListener, KeyListener {
	private final List<Renderable> renderables = new ArrayList<>();

	private GLU glu = new GLU();

	/**
	 * Attributs représentants les valeurs de rotation selon les 3 axes.
	 * Ces valeurs sont utilisées afin de manipuler la caméra.
	 */
	private float rotateZ;

	private Camera camera;
	private Position cameraPosition;

	public Scene3D(Camera camera) {
		this.camera = camera;
		this.cameraPosition = camera.getPosition();
		renderables.add(new Ground(4, .2));
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// Récupération du contexte OpenGL
		final GL2 gl = drawable.getGL().getGL2();

        // Activation de la transparence
        gl.glEnable(GL2.GL_BLEND);
		gl.glEnable(GL2.GL_LINE_SMOOTH);
		gl.glEnable(GL2.GL_POLYGON_SMOOTH);
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
        gl.glTranslated(cameraPosition.getX(), cameraPosition.getY(), camera.getZoomFactor()-5.0f);
        gl.glRotated(cameraPosition.getHorizontalAngle(), 1.0f, 0.0f, 0.0f);
        gl.glRotated(cameraPosition.getVerticalAngle(), 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateZ, 0.0f, 0.0f, 1.0f);

        // Clear de la scène 3D
        gl.glClearColor(0.18f, 0.3f, 0.56f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		for (Renderable renderable : renderables) {
			renderable.render(gl);
		}
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
			this.camera.zoom();
			break;
		case 'e': // ZOOM OUT
			this.camera.dezoom();
			break;
		case 'q': // Déplacement vers la gauche
			cameraPosition.translateX(.1);
			break;
		case 'd': // Déplacement vers la droite
			cameraPosition.translateX(-.1);
			break;
		case 's': // Déplacement vers le bas
			cameraPosition.translateY(.1);
			break;
		case 'z': // Déplacement vers le haut
			cameraPosition.translateY(-.1);
			break;
		case 'o': // Rotation vers le haut
			cameraPosition.rotateVertical(1);
			break;
		case 'p': // Rotation vers le bas
			cameraPosition.rotateVertical(-1);
			break;
		case 'l': // Rotation vers la gauche
			camera.getPosition().rotateHorizontal(1);
			break;
		case 'm': // Rotation vers la droite
			camera.getPosition().rotateHorizontal(-1);
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

	public void addObject(Object3D object3D) {
		this.renderables.add(object3D);
	}

	public void removeObject(Object3D object3D) {
		this.renderables.remove(object3D);
	}
}
