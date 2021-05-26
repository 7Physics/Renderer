package fr.setphysics.renderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class SceneKeyListener implements KeyListener {
    private Map<Integer, Consumer<Camera>> keyActions = new HashMap<>();
    private Set<Integer> pressedKeys = new HashSet<>();
    private final Scene3D scene;

    public SceneKeyListener(Scene3D scene) {
        this.scene = scene;
        keyActions.put(KeyEvent.VK_Q, camera -> camera.moveLeft(.025));
        keyActions.put(KeyEvent.VK_D, camera -> camera.moveRight(.025));
        keyActions.put(KeyEvent.VK_Z, camera -> camera.moveForward(.025));
        keyActions.put(KeyEvent.VK_S, camera -> camera.moveBackward(.025));
        keyActions.put(KeyEvent.VK_SPACE, camera -> camera.translateY(.025));
        keyActions.put(KeyEvent.VK_SHIFT, camera -> camera.translateY(-.025));
    }

    public void update() {
        final Camera camera = scene.getCamera();
        for (Integer key : pressedKeys) {
            Consumer<Camera> action = keyActions.get(key);
            if(action != null) {
                action.accept(camera);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        this.pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.pressedKeys.remove(e.getKeyCode());
    }
}
