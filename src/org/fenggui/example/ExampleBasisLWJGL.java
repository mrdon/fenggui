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
 * $Id: ExampleBasisLWJGL.java 287 2007-05-26 23:02:23Z schabby $
 */
package org.fenggui.example;

import org.fenggui.render.lwjgl.EventHelper;
import org.fenggui.render.lwjgl.LWJGLBinding;
import org.lwjgl.LWJGLException;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Examplary LWJGL app that shows the 'Everyhing' example.
 * 
 * 
 * @todo Comment this class... #
 * 
 * @author Johannes Schaback, last edited by $Author: schabby $, $Date: 2007-05-27 01:02:23 +0200 (So, 27 Mai 2007) $
 * @version $Revision: 287 $
 */
public class ExampleBasisLWJGL {

	org.fenggui.Display desk = null;
	private int lastButtonDown = -1;
	
	
	public static void main(String[] args) {
		new ExampleBasisLWJGL().execute();
		System.exit(0);
	}

	private void readBufferedKeyboard() {
		
	      //check keys, buffered
	      Keyboard.poll();

	      while (Keyboard.next()) 
	      {
	    	  if(Keyboard.getEventKeyState()) // if pressed
	    	  {
	    		  desk.fireKeyPressedEvent(EventHelper.mapKeyChar(), EventHelper.mapEventKey());
	    		  
//	    		 XXX: dirty hack to make TextEditor usable again on LWJGL. This needs to be solved nicer in the future!
	    		  desk.fireKeyTypedEvent(EventHelper.mapKeyChar()); 
	    	  } 
	    	  else 
	    	  { 
	    		  desk.fireKeyReleasedEvent(EventHelper.mapKeyChar(), EventHelper.mapEventKey());
	    	  }
	        }


	}
	
	  /**
	   * reads a mouse in buffered mode
	   */
	  private void readBufferedMouse() {
		  int x = Mouse.getX();
		  int y = Mouse.getY();
		  
		  boolean hitGUI = false;
		  
		  
		  // @todo the click count is not considered in LWJGL! #
		  
		  if(lastButtonDown != -1 && Mouse.isButtonDown(lastButtonDown)) {
			  hitGUI |= desk.fireMouseDraggedEvent(x, y, 
					  EventHelper.getMouseButton(lastButtonDown));
		  } else {			  
			  if(Mouse.getDX() != 0 || Mouse.getDY() != 0)
				  hitGUI |= desk.fireMouseMovedEvent(x, y);
			  
			  if(lastButtonDown != -1) {
				  hitGUI |= desk.fireMouseReleasedEvent(x, y, 
						  EventHelper.getMouseButton(lastButtonDown), 1);
				  lastButtonDown = -1;
			  }
			    while(Mouse.next()) {
			        if(Mouse.getEventButton() != -1 && Mouse.getEventButtonState()) {
			        	lastButtonDown = Mouse.getEventButton();
			        	hitGUI |= desk.fireMousePressedEvent(x, y, 
			        			EventHelper.getMouseButton(lastButtonDown), 1);
			        }
			        int wheel = Mouse.getEventDWheel(); 
			        if(wheel != 0) {
			        	hitGUI |= desk.fireMouseWheel(x, y, wheel > 0, 1);
			        }
			      } 
		  }
	  }  
	    
	    
	
	
	/**
	 * 
	 */
	public void execute() {
		try {
			initEverything();
		} catch (LWJGLException le) {
			le.printStackTrace();
			System.out.println("Failed to initialize Gears.");
			return;
		}

		mainLoop();

		destroy();
	}

	/**
	 * 
	 */
	private void destroy() {
		Display.destroy();
	}

	public void mainLoop() {
		while (!Display.isCloseRequested()) {
		
			readBufferedKeyboard();
			readBufferedMouse();
			
			glRender();
			Display.update();
		}
	}
	
	/**
	 * 
	 */
	private void glRender() {
        GL11.glLoadIdentity();
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        

        GLU.gluLookAt(10, 8, 8, 0, 0, 0, 0, 0, 1);
        
       // GL11.glRotatef(rotAngle, 1f, 1f, 1);
      //  GL11.glColor3f(0.42f, 0.134f, 0.44f);
        
       // rotAngle += 0.5;
		
		
		// draw GUI stuff
		desk.display();
		
		
		// hmm, i think we need to query the mouse pointer and
		// keyboard here and call the according
		// desk.mouseMoved, desk.keyPressed, etc.
		// methods...
		
	}

	/**
	 * 
	 */
	public final void initEverything() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(800, 600));
		Display.setTitle("Gears");
		Display.create();
		
        glInit(800, 600);
		
        // initialize keyboard
        Keyboard.create();
        
        // build the gui
        buildGUI();
	}
	
	
	public void buildGUI()
	{
		// init. the LWJGL Binding
		LWJGLBinding binding = new LWJGLBinding();
		
		// init the root Widget, that spans the whole
		// screen (i.e. the OpenGL context within the
		// Microsoft XP Window)
		desk = new org.fenggui.Display(binding);
		// build a simple test FengGUI-Window
		Everything everything = new Everything();
		everything.buildGUI(desk);
	}
	
	private void glInit(int width, int height) {
		// Go into orthographic projection mode.
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluOrtho2D(0, width, 0, height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glViewport(0, 0, width, height);
		
		//set clear color to ... ugly
		GL11.glClearColor(0.1f, 0.5f, 0.2f, 0.0f);
		//sync frame (only works on windows )   => THAT IS NOT TRUE (ask Rainer)
		Display.setVSyncEnabled(false);
	}

	public org.fenggui.Display getDesk() {
		return desk;
	}
}
