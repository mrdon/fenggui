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
 * $Id: ViewPortExampleJOGL.java 264 2007-04-16 12:34:05Z bbeaulant $
 */
package org.fenggui.example;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import org.fenggui.Display;
import org.fenggui.ViewPort;
import org.fenggui.composites.Window;
import org.fenggui.event.IViewPortPaintListener;
import org.fenggui.render.Graphics;
import org.fenggui.render.jogl.EventBinding;
import org.fenggui.render.jogl.JOGLBinding;

import com.sun.opengl.util.GLUT;

/**
 * Demonstrates a <code>TextField</code> Widget.
 * 
 * 
 * @author Johannes Schaback
 * 
 */
@SuppressWarnings("serial")
public class ViewPortExampleJOGL extends ExampleBasisJOGL {

    private Display desk;

    public static void main(String[] artgs) 
    {
    	ViewPortExampleJOGL e = new ViewPortExampleJOGL();
    	e.setSize(500, 500);
    	e.setVisible(true);
    }
    
    private void buildFrame() {
    	Window w = new Window(true, false, false, true);
    	desk.addWidget(w);
    	w.setSize(300, 300);
    	w.setX(100);
    	w.setY(100);
    	//StaticLayout.center(w, desk);
    	ViewPort vp = new ViewPort();
    	w.getContentContainer().addWidget(vp);
    	//vp. setBackground(new PlainBackground(Color.BLACK_HALF_OPAQUE));
    	
    	vp.setViewPortPaintListener(new CubeDrawer());
    }

    class CubeDrawer implements IViewPortPaintListener {
    	GLUT glut = null;
    	
    	
    	public CubeDrawer() {
    		glut = new GLUT();
    	}
    	
		public void paint(Graphics g, int width, int height) {
			GL gl = getGL();
			GLU glu = getGLU();
			
			gl.glDisable(GL.GL_DEPTH_TEST); // this is unfortunate, but necessary :(
			
            gl.glMatrixMode(GL.GL_PROJECTION);
            gl.glLoadIdentity();
            glu.gluPerspective(45, (double)width/(double)height, 4, 100);
            gl.glMatrixMode(GL.GL_MODELVIEW);
            gl.glLoadIdentity();

            gl.glColor3f(1, 1, 1);
            glu.gluLookAt(10, 6, 8, 0, 0, 0, 0, 0, 1);
            glut.glutWireCube(6);
            gl.glFlush();
		}
    	
    }
    
	public void buildGUI() {
		desk = new Display(new JOGLBinding(getCanvas()));
		setDisplay(desk);
		new EventBinding(getCanvas(), desk);
		buildFrame();
		
		desk.layout();
	}

	public String getExampleName() {
		return "ViewPort Example";
	}

	public String getExampleDescription() {
		return "ViewPort Example";
	}

}
