package fr.setphysics.renderer;

import fr.setphysics.common.geom.Position;
import fr.setphysics.common.geom.shape.Cuboid;
import fr.setphysics.common.geom.shape.Sphere;


import java.awt.*;
import java.util.Random;

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

        scene3D.addObject(new Cuboid(.1, .1, 1), new Position(.5, .5, 0), Color.RED, Color.BLACK);

//        scene3D.addObject(new Cuboid(.1, .1, 1), new Position(0, .5, .5), Color.GREEN, Color.BLACK);

        Random r = new Random();

        for(int i = 0; i < 25; i++) {
            scene3D.addObject(new Sphere(r.nextDouble()/2, 3), new Position(r.nextDouble()*2-1, 1, r.nextDouble()*2-1), new Color(0x2E, 0x9A, 0xC9, 255));
        }
        // Création de la frame
        final JFrame frame = new JFrame ("7Physics");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(scene3D, BorderLayout.CENTER);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
