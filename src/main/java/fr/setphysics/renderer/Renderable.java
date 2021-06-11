package fr.setphysics.renderer;

import com.jogamp.opengl.GL2;

public interface Renderable {
    /**
     * Effectue le rendu de l'objet sur le contexte OpenGL précisé.
     * @param gl Contexte OpenGL
     */
    void render(GL2 gl);
}
