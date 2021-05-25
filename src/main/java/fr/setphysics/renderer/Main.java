package fr.setphysics.renderer;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import fr.setphysics.common.geom.Position;
import fr.setphysics.common.geom.shape.Cuboid;


import java.awt.*;

import javax.swing.JFrame;

/**
 * Classe de lancement du moteur 3D 7Physics. Seule la création d'une
 * fenêtre et la mise en place d'OpenGL doivent apparaître ici.
 * @author 7Physics
 *
 */
public class Main {
	/**
	 * Lancement de l'application et création de la fenêtre d'exécution
	 * @param args: Aucun paramètre n'est actuellement supporté
	 */
    public static void main( String[] args ) {
        // Récupération des informations liées au contexte OpenGL
        final GLProfile profile = GLProfile.get( GLProfile.GL2 );
        GLCapabilities capabilities = new GLCapabilities( profile );

        // Création d'un canvas OpenGL (équivalent à un JPanel)
        final GLCanvas glcanvas = new GLCanvas( capabilities );
        glcanvas.setSize(1000, 650);
        
        // Ajout de la scène 3D du Renderer
        Camera camera = new Camera(new Position(-2, .5, 0));
        final Scene3D scene3D = new Scene3D(camera);
        glcanvas.addGLEventListener(scene3D);
		glcanvas.addKeyListener(scene3D.getKeyListener());
		glcanvas.addMouseWheelListener(scene3D);
		glcanvas.addMouseMotionListener(scene3D);

        scene3D.addObject(new Object3D(new Position(.5, .5, 0),
                new Cuboid(.1, .1, 1),
                Color.RED,
                Color.WHITE)
        );

        scene3D.addObject(new Object3D(new Position(0, .5, .5),
                new Cuboid(.1, .1, 1),
                Color.GREEN,
                Color.WHITE)
        );

        // Création de la frame
        final JFrame frame = new JFrame ("7Physics");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(glcanvas, BorderLayout.CENTER);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        FPSAnimator fps = new FPSAnimator(glcanvas, 300);
        fps.start();
    }
}
