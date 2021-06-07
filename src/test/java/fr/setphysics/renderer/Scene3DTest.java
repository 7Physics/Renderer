package fr.setphysics.renderer;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import fr.setphysics.common.geom.Position;
import fr.setphysics.common.geom.Vec3;
import fr.setphysics.common.geom.shape.Cuboid;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CompletableFuture;

// Test de la scène 3D
public class Scene3DTest {

    private static Camera camera = new Camera(new Position(Vec3.ZERO()));
    private static JFrame frame;
    private static Scene3D scene;

    @BeforeAll
    public static void setUp() {

        // Récupération des informations liées au contexte OpenGL
        final GLProfile profile = GLProfile.get( GLProfile.GL2 );
        GLCapabilities capabilities = new GLCapabilities( profile );

        // Création d'un canvas OpenGL (équivalent à un JPanel)
        final GLCanvas glcanvas = new GLCanvas( capabilities );
        glcanvas.setSize(1000, 650);

        // Ajout de la scène 3D du Renderer
        scene = new Scene3D(camera);
        glcanvas.addGLEventListener(scene);
        glcanvas.addKeyListener(scene);

        // Création de la frame
        frame = new JFrame ("7Physics");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(glcanvas, BorderLayout.CENTER);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        FPSAnimator fps = new FPSAnimator(glcanvas, 300);
        fps.start();
    }

    @Test
    public synchronized void removeObjectFromScene() throws InterruptedException {
        frame.setTitle("Remove object from scene");
        Object3D cube = new Object3D(new Position(Vec3.ZERO().addY(.25)),
                new Cuboid(0.5, 0.5, 0.5));
        scene.addObject(cube);
        Thread.sleep(1000);
        scene.removeObject(cube);
        Thread.sleep(1000);
    }
}
