/*
 * FengGUI - Java GUIs in OpenGL (http://www.fenggui.org)
 * 
 * Copyright (c) 2005, 2006 FengGUI Project
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details:
 * http://www.gnu.org/copyleft/lesser.html#TOC3
 * 
 * $Id: HelloWorld.java 264 2007-04-16 12:34:05Z bbeaulant $
 */
package org.fenggui.example;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.JFrame;

import org.fenggui.Display;
import org.fenggui.FengGUI;
import org.fenggui.Label;
import org.fenggui.composites.Window;
import org.fenggui.layout.Alignment;
import org.fenggui.render.jogl.EventBinding;
import org.fenggui.render.jogl.JOGLBinding;

import com.sun.opengl.util.Animator;



/**
 * The probably simplest runnable FengGUI example in the known
 * part of the galaxy.
 * 
 * @author Johannes Schaback 

 */
@SuppressWarnings("serial")
public class HelloWorld extends JFrame implements GLEventListener {

	// the GL class of JOGL
	private GL gl;
	
	// the canvas on which the OpenGL will draw his stuff. We keep
	// it as a field because we need the canvas to instantiate the
	// JOGL binding.
    private GLCanvas canvas = null;
	
    // The root of the Widget tree.
    private Display display = null;

    /**
     * Creates the HelloWorld example app.
     *
     */
    public HelloWorld()
    {
    	canvas = new GLCanvas();

        canvas.addGLEventListener(this);
        
        getContentPane().add(canvas, java.awt.BorderLayout.CENTER);
        setSize(500, 300);
        setTitle("FengGUI - Hello FengGUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        // we use the Animator to constantly update
        // the screen
        Animator animator = new Animator(canvas);
        animator.start();
    }

    /**
     * Build the GUI.
     */
    public void buildGUI() {
        display = FengGUI.createDisplay(new JOGLBinding(canvas));
        
        new EventBinding(canvas, display);
        
        Window w = new Window(true, false, false, true);
        Label l = new Label("Hello World!!");
        l.getAppearance().setAlignment(Alignment.MIDDLE);
        
        w.getContentContainer().addWidget(l);
        
        
        w.setXY(50, 50);
        w.setSize(200, 100);
        
        w.layout();
        
        
        
        display.addWidget(w);
    } 
    
    public void display(GLAutoDrawable arg0)
    {
        gl.glLoadIdentity();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        // pass the control over the OpenGL context to FengGUI so that
        // it can render the GUI.
        display.display();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    
    /**
     * JOGL callback method
     */
    public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
    	// does nothing
    }

    /**
     * JOGL callback method
     */
    public void init(GLAutoDrawable drawable)
    {
        gl = drawable.getGL();
        gl.glClearColor(1.0f, 0.8f, 0.2f, 0.0f);
        
        // we build the GUI in the render thread! This is important
        // because textures can only be processed in the rendering
        // thread
        buildGUI();
    }

    /**
     * Entrance point to the HelloWorld example.
     */
    public static void main(String[] args) {
    	new HelloWorld();
    }
    
}
