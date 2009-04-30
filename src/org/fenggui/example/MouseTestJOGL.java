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
 * Created on 2005-3-1
 * $Id: MouseTestJOGL.java 114 2006-12-12 22:06:22Z schabby $
 */
package org.fenggui.example;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.JFrame;

import org.fenggui.Button;
import org.fenggui.Display;
import org.fenggui.FengGUI;
import org.fenggui.Label;
import org.fenggui.composites.Window;
import org.fenggui.layout.BorderLayoutData;
import org.fenggui.layout.StaticLayout;
import org.fenggui.render.jogl.EventHelper;
import org.fenggui.render.jogl.JOGLBinding;
import org.fenggui.util.Spacing;

import com.sun.opengl.util.Animator;

/**
 * This class demonstrates proper mouse event handling. 
 * Usually the helper class EventBinding
 * is used to forward all evens (from mouse and keyboard) to joglui. The price
 * you havwe to pay for the simplicity is that you lose the information whether
 * the mouse hits the GUI or not.
 * In this class we want to show how to determine if a click hits a GUI
 * component or not but NOT using EventBining.
 * 
 * This is especially usefull if you use joglui as an overlay over
 * your (maybe picking enabled) scene.
 * A click on a GUI component is not supposed to have an impact
 * on the underlying scene as a click in the scene is not meant to be processed
 * by the GUI. But both share the same mouse. The trick is, that each method that
 * handels mouse events returns a boolean value if a GUI component was hit or
 * not.
 * 
 * @todo Comment this class... #
 * 
 * @author Johannes Schaback, last edited by $Author: schabby $, $Date: 2006-12-12 23:06:22 +0100 (Tue, 12 Dec 2006) $
 * @version $Revision: 114 $
 */
public class MouseTestJOGL extends JFrame implements GLEventListener, MouseListener, MouseMotionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GL gl;
    private GLCanvas canvas;
    private Animator anim;
    private int frameWidth, frameHeight;
    double scale = 1;
    Window filesFrame = null;
    Display display;
    
    public MouseTestJOGL(GraphicsDevice device) {
    	canvas = new GLCanvas();

        canvas.addGLEventListener(this);
        
        display = FengGUI.createDisplay(new JOGLBinding(canvas));
        
        getContentPane().add(canvas, java.awt.BorderLayout.CENTER);
        setSize(500, 500);
        setVisible(true);

        // We register this class as our mouse handler in the canvas. That means
        // that each mouse event that occurs on the canvas is forwarded by a method
        // call to the corresponding method in this class
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        
        anim = new Animator(canvas);            // calls display() periodically
        anim.start();        
        
        this.addWindowListener(new WindowListener() {
            public void windowOpened(WindowEvent arg0) {         }

            public void windowClosing(WindowEvent arg0) {
                anim.stop();
                setVisible(false);
                System.exit(0);
            }

            public void windowClosed(WindowEvent arg0) {
            }

            public void windowIconified(WindowEvent arg0) {
            }

            public void windowDeiconified(WindowEvent arg0) {
            }

            public void windowActivated(WindowEvent arg0) {
            }

            public void windowDeactivated(WindowEvent arg0) {
            }});

    }    
    
    public static void main(String[] args) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
    
        new MouseTestJOGL(device);
        //desk.setVisible(true);
    
    }

    private void buildBigFrame() {
        filesFrame = FengGUI.createDialog(display);
        filesFrame.setX(10);
        filesFrame.setY(10);
        filesFrame.setTitle("Big Frame");
        filesFrame.setSize(300, 300);
        filesFrame.getContentContainer().setLayoutManager(new org.fenggui.layout.BorderLayout());
        
        filesFrame.getContentContainer().getAppearance().setPadding(new Spacing(10,10));        
        
        final Label label = FengGUI.createLabel(filesFrame.getContentContainer(), "This is label in the north");
        label.setLayoutData(BorderLayoutData.NORTH);
        
        Label westLabel = FengGUI.createLabel(filesFrame.getContentContainer(), "West");
        westLabel.setLayoutData(BorderLayoutData.WEST);
        
        Button button = FengGUI.createButton(filesFrame.getContentContainer(),"This looks like a button");
        button.setLayoutData(BorderLayoutData.SOUTH);
        
        final Label eastLabel = FengGUI.createLabel(filesFrame.getContentContainer(), "Double Click me!");
        eastLabel.setLayoutData(BorderLayoutData.EAST);
        
        display.layout();
        StaticLayout.center(filesFrame, display);
    }
    
    /* (non-Javadoc)
     * @see net.java.games.jogl.GLEventListener#init(net.java.games.jogl.GLDrawable)
     */
    public void init(GLAutoDrawable arg0) {
        gl = arg0.getGL();
        
        gl.glClearColor(0.2f, 0.3f, 1f, 0.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        //buildTest();
        buildBigFrame();
    }


    
    /* (non-Javadoc)
     * @see net.java.games.jogl.GLEventListener#display(net.java.games.jogl.GLDrawable)
     */
    public void display(GLAutoDrawable arg0) {
        
        gl.glLoadIdentity();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        /*
        glu.gluLookAt(eye[0], eye[1], eye[2], 
                    view[0], view[1], view[2], 
                    upVector[0], upVector[1], upVector[2]);
        */
        gl.glOrtho(0, frameWidth, 0, frameHeight, -1, 999999);
     
        
    
        gl.glColor3f(0f,1f,0f);

        gl.glTranslatef(0,0, -100);
        
        display.display();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {
        gl.glViewport(0, 0, width, height);
        frameWidth = width;
        frameHeight = height;
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity(); 
        
    }

    /* (non-Javadoc)
     * @see net.java.games.jogl.GLEventListener#displayChanged(net.java.games.jogl.GLDrawable, boolean, boolean)
     */
    public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
               
    }

    public void mouseClicked(MouseEvent arg0) {
    }

    /**
     * Invoked when a mouse button just went down.
     */
    public void mousePressed(MouseEvent me) {
        boolean hitGUI = display.fireMousePressedEvent(me.getX(), display.getHeight()-me.getY(), 
        		EventHelper.getMouseButton(me), me.getClickCount());
        
        if(!hitGUI) {
            System.out.println("MousePressed on underlying scene: "+me);
        }
    }

    /**
     * Invoked when a previously pressed mouse button gets released.
     */
    public void mouseReleased(MouseEvent me) {
        @SuppressWarnings("unused") 
        boolean hitGUI = display.fireMouseReleasedEvent(me.getX(), display.getHeight()-me.getY(), 
        		EventHelper.getMouseButton(me), me.getClickCount());
    }

    /**
     * Invoked if the mouse cursor enters to canvas. FengGUI is not
     * very interested in this event.
     */
    public void mouseEntered(MouseEvent arg0) {
        
    }

    /**
     * 
     */
    public void mouseExited(MouseEvent arg0) {
        
    }

    /**
     * Invoked if the user moves the mouse and presses a mouse button
     * at the same time.
     */
    public void mouseDragged(MouseEvent me) {
        @SuppressWarnings("unused") 
        boolean hitGUI = display.fireMouseDraggedEvent(me.getX(), display.getHeight()-me.getY(), 
        		EventHelper.getMouseButton(me));
    }

    /**
     * Invoked when ever the mouse is moved over the canas.
     */
    public void mouseMoved(MouseEvent me) {
        @SuppressWarnings("unused") 
        boolean hitGUI = display.fireMouseMovedEvent(me.getX(), display.getHeight()-me.getY());
    }

}

