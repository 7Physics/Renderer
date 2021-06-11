package fr.setphysics.renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import fr.setphysics.common.geom.Position;
import fr.setphysics.common.geom.Shape;


/**
 * La classe WorldRenderer réalise l'affichage du monde 3D créé par l'utilisateur.
 * @author pierre
 *
 */
public class Scene3D extends GLCanvas implements GLEventListener, Iterable<Object3D> {
	/**
	 * Ratio width/height des dimensions de la fenêtre
	 */
	private float frameSizeRatio;
	private final List<Renderable> renderables = new ArrayList<>();

	private final SceneKeyListener keyListener = new SceneKeyListener(this);
	private final SceneMouseListener mouseListener = new SceneMouseListener(this);

	private final GLU glu = new GLU();

	/**
	 * Caméra observant la scène
	 */
	private final Camera camera;

	/**
	 * Crée une nouvelle scène 3D vue par la caméra passée en paramètre
	 * @param camera Caméra observant la scène
	 * @param ground Sol de la scune
	 */
	public Scene3D(Camera camera, Ground ground) {
		super(new GLCapabilities( GLProfile.get( GLProfile.GL2 ) ));
		this.camera = camera;
		renderables.add(ground);

		addGLEventListener(this);
		addKeyListener(keyListener);
		addMouseWheelListener(mouseListener);
		addMouseMotionListener(mouseListener);

		FPSAnimator fps = new FPSAnimator(this, 300);
		fps.start();
	}

	/**
	 * Crée une nouvelle scène 3D vue par la caméra passée en paramètre
	 * @param camera Caméra observant la scène
	 */
	public Scene3D(Camera camera) {
		this(camera, new Ground(4, .2));
	}

	/**
	 * @return Caméra visualisant la scèene
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Initialisation OpenGL
	 */
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

	/**
	 * Loop de rendu
	 */
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

	/**
	 * Lorsque les dimensions de la scène sont modifiées.
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		super.reshape(x, y, width, height);

		GL2 gl = drawable.getGL().getGL2();

		if (height <= 0)
			height = 1;

		gl.glViewport(0, 0, width, height);
		frameSizeRatio = (float) width / (float) height;
		refreshGlu(gl);
	}

	/**
	 * Met à jour le contexte GLU (Perspective, position de la caméra...)
	 * @param gl Contexte OpenGL
	 */
	private void refreshGlu(GL2 gl) {
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


	/**
	 * Ajoute un objet 3D à la scène
	 * @param object3D Objet 3D à ajouter
	 * @return L'objet 3D ajouté (pour pouvoir appeler ses méthodes sur la même ligne)
	 */
	public Object3D addObject(Object3D object3D) {
		this.renderables.add(object3D);
		return object3D;
	}

	/**
	 * Sucre syntaxique : Crée un nouvel objet 3D avec les paramètres passés et l'ajoute à la scène.
	 * @param shape Forme de l'objet
	 * @param position Position de l'objet
	 * @param color Couleur de l'objet
	 * @param edgeColor Couleur des arêtes de l'objet
	 * @return Objet 3D nouvellement ajouté
	 * @see Object3D#Object3D(Shape, Position, Color, Color)
	 */
	public Object3D addObject(Shape shape, Position position, Color color, Color edgeColor) {
		return addObject(new Object3D(shape, position, color, edgeColor));
	}

	/**
	 * Sucre syntaxique : Crée un nouvel objet 3D avec les paramètres passés et l'ajoute à la scène.
	 * @param shape Forme de l'objet
	 * @param position Position de l'objet
	 * @param color Couleur de l'objet
	 * @return Objet 3D nouvellement ajouté
	 * @see Object3D#Object3D(Shape, Position, Color)
	 */
	public Object3D addObject(Shape shape, Position position, Color color) {
		return addObject(new Object3D(shape, position, color));
	}

	/**
	 * Sucre syntaxique : Crée un nouvel objet 3D avec les paramètres passés et l'ajoute à la scène.
	 * @param shape Forme de l'objet
	 * @param position Position de l'objet
	 * @return Objet 3D nouvellement ajouté
	 * @see Object3D#Object3D(Shape, Position)
	 */
	public Object3D addObject(Shape shape, Position position) {
		return addObject(new Object3D(shape, position));
	}

	/**
	 * Sucre syntaxique : Crée un nouvel objet 3D avec les paramètres passés et l'ajoute à la scène.
	 * @param shape Forme de l'objet
	 * @return Objet 3D nouvellement ajouté
	 * @see Object3D#Object3D(Shape)
	 */
	public Object3D addObject(Shape shape) {
		return addObject(new Object3D(shape));
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
}
