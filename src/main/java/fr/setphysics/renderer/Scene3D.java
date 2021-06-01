package fr.setphysics.renderer;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
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
public class Scene3D implements GLEventListener, MouseWheelListener, MouseMotionListener, Iterable<Object3D> {
	/**
	 * Ratio width/height des dimensions de la fenêtre
	 */
	private float frameSizeRatio;
	private final List<Renderable> renderables = new ArrayList<>();

	private SceneKeyListener keyListener = new SceneKeyListener(this);

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

	public Camera getCamera() {
		return camera;
	}

	public SceneKeyListener getKeyListener() {
		return keyListener;
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// Récupération du contexte OpenGL
		final GL2 gl = drawable.getGL().getGL2();

        // Activation de la transparence
        gl.glEnable(GL2.GL_BLEND);
		gl.glEnable(GL2.GL_LINE_SMOOTH);
		gl.glEnable(GL2.GL_POLYGON_SMOOTH);
		gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		keyListener.update();
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
			camera.rotate((x-lastMouseX)/(width/MOUSE_SENSITIVITY_X),
					-(y-lastMouseY)/(height/MOUSE_SENSITIVITY_Y));
		}
		lastMouseX = x;
		lastMouseY = y;
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		lastMouseX = -1;
	}

	@Override
	public Iterator<Object3D> iterator() {
		return new ObjectIterator(this);
	}
	
	private class ObjectIterator implements Iterator<Object3D>{
		private Scene3D scene;
		private int index;
		
		public ObjectIterator(Scene3D scene) {
			this.scene = scene;
			this.index = 1;
		}

		@Override
		public boolean hasNext() {
			return index < this.scene.renderables.size();
		}

		@Override
		public Object3D next() {
			Object3D res = (Object3D) this.scene.renderables.get(this.index);
			this.index++;
			return res;
		}
		
	}

	public void removeObject(Object3D object3D) {
		this.renderables.remove(object3D);
	}
}
