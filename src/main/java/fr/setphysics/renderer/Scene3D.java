package fr.setphysics.renderer;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import static fr.setphysics.renderer.Settings.*;


/**
 * La classe WorldRenderer réalise l'affichage du monde 3D créé par l'utilisateur.
 * @author pierre
 *
 */
public class Scene3D implements GLEventListener, KeyListener, MouseWheelListener, MouseMotionListener {
	/**
	 * Ratio width/height des dimensions de la fenêtre
	 */
	private float frameSizeRatio;
	private final List<Renderable> renderables = new ArrayList<>();

	/**
	 * Sauvegarde de la dernière position de la souris (pour la rotation)
	 */
	private int lastMouseX = -1, lastMouseY = -1;

	/**
	 * Dimensions de la fenêtre
	 */
	private int width, height;

	private GLU glu = new GLU();

	/**
	 * Caméra observant la scène
	 */
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

        refreshGlu(gl);

        // Clear de la scène 3D
        gl.glClearColor(0.18f, 0.3f, 0.56f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		for (Renderable renderable : renderables) {
			renderable.render(gl);
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		this.width = width;
		this.height = height;

		GL2 gl = drawable.getGL().getGL2();

		if (height <= 0)
			height = 1;

		gl.glViewport(0, 0, width, height);
		frameSizeRatio = (float) width / (float) height;
		refreshGlu(gl);
	}

	public void refreshGlu(GL2 gl) {
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(camera.getFov(), frameSizeRatio, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(camera.getX(),
				camera.getY(),
				camera.getZ(),
				camera.getLookAtX(),
				camera.getLookAtY(),
				camera.getLookAtZ(),
				0, 1, 0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Modification des attributs de la caméra selon la touche utilisée
		int code = e.getKeyCode();
		switch (code) {
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

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		camera.zoom(e.getPreciseWheelRotation()*MOUSE_SENSITIVITY_WHEEL);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if(lastMouseX > 0) {
			camera.rotate((x-lastMouseX)/(width*MOUSE_SENSITIVITY_X),
					-(y-lastMouseY)/(height*MOUSE_SENSITIVITY_Y));
		}
		lastMouseX = x;
		lastMouseY = y;
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		lastMouseX = -1;
	}
}
