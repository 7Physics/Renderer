package fr.setphysics.renderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class WorldRenderer implements GLEventListener, KeyListener {
	private GLU glu = new GLU();
	private float rotateX, rotateY, rotateZ;
	private float zoom, transX, transY;

	@Override
	public void init(GLAutoDrawable drawable) {
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
        gl.glLoadIdentity();
        gl.glTranslatef(transX, transY, zoom-5.0f);
        gl.glRotatef(rotateX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateY, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateZ, 0.0f, 0.0f, 1.0f);

        
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glClearColor(0.195f, 0.590f, 0.656f, 1.0f);

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glBegin(GL2.GL_QUADS);
        
        gl.glColor4f(0.3f, 0.3f, 0.3f, 0.8f);
        gl.glVertex3f(2.0f, 0f, 2.0f);
        gl.glVertex3f(2.0f, 0f, -2.0f);
        gl.glVertex3f(-2.0f, 0f, -2.0f);
        gl.glVertex3f(-2.0f, 0f, 2.0f);
        gl.glEnd();

        gl.glBegin(GL2.GL_LINES);
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
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
		if (e.getKeyChar() == 'a') {
			zoom += 0.1f;
		} else if (e.getKeyChar() == 'e') {
			zoom -= 0.1f;
		} else if (e.getKeyChar() == 'd') {
			transX += 0.1f;
		} else if (e.getKeyChar() == 'q') {
			transX -= 0.1f;
		} else if (e.getKeyChar() == 's') {
			transY += 0.1f;
		} else if (e.getKeyChar() == 'z') {
			transY -= 0.1f;
		} else if (e.getKeyChar() == 'o') {
			rotateX += 1.0f;
		} else if (e.getKeyChar() == 'p') {
			rotateX -= 1.0f;
		} else if (e.getKeyChar() == 'l') {
			rotateY += 1.0f;
		} else if (e.getKeyChar() == 'm') {
			rotateY -= 1.0f;
		} else if (e.getKeyChar() == ';') {
			rotateZ += 1.0f;
		} else if (e.getKeyChar() == ':') {
			rotateZ -= 1.0f;
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