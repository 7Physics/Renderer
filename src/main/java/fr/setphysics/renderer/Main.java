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
        Camera camera = new Camera(new Position(-2, .5, 0));

        // Ajout de la scène 3D du Renderer
        final Scene3D scene3D = new Scene3D(camera);
        scene3D.setSize(1000, 650);

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
        frame.getContentPane().add(scene3D, BorderLayout.CENTER);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
