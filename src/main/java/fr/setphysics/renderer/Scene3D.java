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

	private Camera camera;

	public Scene3D(Camera camera) {
		this.camera = camera;
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

        glu.gluLookAt(camera.getX(),
				camera.getY(),
				camera.getZ(),
				camera.getLookAtX(),
				camera.getLookAtY(),
				camera.getLookAtZ(),
				0, 1, 0);

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
		int code = e.getKeyCode();
		switch (code) {
		case KeyEvent.VK_A: // ZOOM IN
			this.camera.zoom();
			break;
		case KeyEvent.VK_E: // ZOOM OUT
			this.camera.dezoom();
			break;
		case KeyEvent.VK_Q: // Déplacement vers la gauche
			camera.moveLeft(.05);
			break;
		case KeyEvent.VK_D: // Déplacement vers la droite
			camera.moveRight(.05);
			break;
		case KeyEvent.VK_S: // Déplacement vers derrière
			camera.moveBackward(.05);
			break;
		case KeyEvent.VK_Z: // Déplacement vers devant
			camera.moveForward(.05);
			break;
		case KeyEvent.VK_O: // Rotation vers le haut
			camera.rotateVertical(.02);
			break;
		case KeyEvent.VK_P: // Rotation vers le bas
			camera.rotateVertical(-.02);
			break;
		case KeyEvent.VK_L: // Rotation vers la gauche
			camera.getPosition().rotateHorizontal(-.02);
			break;
		case KeyEvent.VK_M: // Rotation vers la droite
			camera.getPosition().rotateHorizontal(.02);
			break;
		case KeyEvent.VK_SHIFT:
			camera.translateY(-.05);
			break;
		case KeyEvent.VK_SPACE:
			camera.translateY(.05);
			break;
		default:
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
}
