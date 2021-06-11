package fr.setphysics.renderer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import static fr.setphysics.renderer.Settings.*;

public class SceneMouseListener implements MouseMotionListener, MouseWheelListener {
    /**
     * Sauvegarde de la dernière position de la souris (pour la rotation)
     */
    private int lastMouseX = -1, lastMouseY = -1;

    private final Scene3D scene;

    /**
     * Crée un écouteur pour gérer les actions effectuée sur la scène au mouvement de la souris.
     * @param scene Scene sur laquelle agir.
     */
    public SceneMouseListener(Scene3D scene) {
        this.scene = scene;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scene.getCamera().zoom(e.getPreciseWheelRotation()*MOUSE_SENSITIVITY_WHEEL);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if(lastMouseX > 0) {
            scene.getCamera().rotate((x-lastMouseX)/(scene.getWidth()/MOUSE_SENSITIVITY_X),
                    -(y-lastMouseY)/(scene.getHeight()/MOUSE_SENSITIVITY_Y));
        }
        lastMouseX = x;
        lastMouseY = y;
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        lastMouseX = -1;
    }
}
