package org.fenggui.example;

import javax.media.opengl.GL;

/**
 * Renders a 3D model of the Feng character. This class runs with JOGL only.
 * 
 * @author Johannes, last edited by $Author: schabby $, $Date: 2006-10-05 03:37:07 +0200 (Thu, 05 Oct 2006) $
 * @version $Revision: 28 $
 */
public class FengCharacterRenderer {

	GL gl = null;
	int displayListId = -1;
	
	public FengCharacterRenderer(GL gl)
	{
		this.gl = gl;
	}
	
	public void display()
	{
		if(displayListId == -1)
		{
			displayListId = gl.glGenLists(1);
			gl.glNewList(displayListId, GL.GL_COMPILE);
			drawFengCharacter();
			gl.glEndList();
		}
		else
		{
			gl.glCallList(displayListId);
		}
	}
	
    private void drawFengCharacter()
    {
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, new float[] {0.3f, 0.3f, 0.3f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, new float[] {1f, 0.3f, 0.3f, 1.0f}, 0);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, new float[] {11f}, 0);
        gl.glColor3f(1, 1, 0);
        
        gl.glTranslatef(-446/2, 0, 0);

        draw(1);
        drawOutline(outlineArray1, -1);
        drawOutline(outlineArray2, -1);
        drawOutline(outlineArray3, 1);
        drawOutline(outlineArray4, 1);
        gl.glTranslatef(0, 0, -10);
        draw(-1);
        gl.glTranslatef(0, 0, 10);
    }
    
    private void drawOutline(int[] array, double k)
    {
    	float[] a = new float[3];
    	float[] b = new float[3];
    	float[] c = new float[3];
    	
        gl.glBegin(GL.GL_QUAD_STRIP);
        
        for(int i=0;i < array.length;i=i+2)
        {
        	if(i >= 0 && i < array.length-3)
        	{
        		a[0] = array[i+2] - array[i];
        		a[1] = array[i+3] - array[i+1];
        		a[2] = 0;
        		
        		b[0] = 0;
        		b[1] = 0;
        		b[2] = -1;
        		
        		crossProduct(a, b, c);
        		
        		double d = Math.sqrt(Math.pow(c[0], 2)+Math.pow(c[1], 2)+Math.pow(c[2], 2));
        		
        		c[0] /= d*k;
        		c[1] /= d*k;
        		c[2] /= d*k;
        	}
       	
        	gl.glNormal3fv(c, 0);
        	gl.glVertex3d(array[i], 439-array[i+1], -10);
        	
        	gl.glNormal3fv(c, 0);
        	gl.glVertex3d(array[i], 439-array[i+1], 0);
        }
        
        gl.glEnd();
    }
    
    
    public static void crossProduct(float[] a, float[] b, float[] toBeFilled)
    {
    	toBeFilled[0] = a[1]*b[2] - a[2]*b[1];
    	toBeFilled[1] = a[2]*b[0] - a[0]*b[2];
    	toBeFilled[2] = a[0]*b[1] - a[1]*b[0];
    }
    
    private void draw(float normalZ)
    {
        gl.glBegin(GL.GL_TRIANGLE_STRIP);
        
        for(int i=0;i < array1.length;i=i+2)
        {
        	gl.glNormal3f(0, 0, normalZ);
        	gl.glVertex3d(array1[i], 439-array1[i+1], 0);
        	
        }
        
        gl.glEnd();
        
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        for(int i=0;i < fanArray.length;i=i+2)
        {
        	gl.glVertex3d(fanArray[i], 439-fanArray[i+1], 0);
        	
        }
        gl.glEnd();
        
        gl.glBegin(GL.GL_TRIANGLE_STRIP);
        
        for(int i=0;i < innerTriangleArray.length;i=i+2)
        {
        	gl.glVertex3d(innerTriangleArray[i], 439-innerTriangleArray[i+1], 0);
        	
        }
        
        gl.glEnd();
        
        gl.glBegin(GL.GL_TRIANGLE_STRIP);
        
        for(int i=0;i < innerTriangleArray1.length;i=i+2)
        {
        	gl.glVertex3d(innerTriangleArray1[i], 439-innerTriangleArray1[i+1], 0);
        }
        
        gl.glEnd();
        
        gl.glBegin(GL.GL_TRIANGLE_STRIP);
        
        for(int i=0;i < innerTriangleArray2.length;i=i+2)
        {
        	gl.glVertex3d(innerTriangleArray2[i], 439-innerTriangleArray2[i+1], 0);
        }
        
        gl.glEnd();
        
        gl.glBegin(GL.GL_TRIANGLE_STRIP);
        
        for(int i=0;i < innerTriangleArray3.length;i=i+2)
        {
        	gl.glVertex3d(innerTriangleArray3[i], 439-innerTriangleArray3[i+1], 0);
        }
        
        gl.glEnd();
        
        gl.glBegin(GL.GL_TRIANGLE_STRIP);
        
        for(int i=0;i < innerTriangleArray4.length;i=i+2)
        {
        	gl.glVertex3d(innerTriangleArray4[i], 439-innerTriangleArray4[i+1], 0);
        }
        
        gl.glEnd();
        
        gl.glBegin(GL.GL_TRIANGLE_STRIP);
        
        for(int i=0;i < innerTriangleArray5.length;i=i+2)
        {
        	gl.glVertex3d(innerTriangleArray5[i], 439-innerTriangleArray5[i+1], 0);
        }
        
        gl.glEnd();
        
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        
        for(int i=0;i < innerTriangleFanArray1.length;i=i+2)
        {
        	gl.glVertex3d(innerTriangleFanArray1[i], 439-innerTriangleFanArray1[i+1], 0);
        }
        
        gl.glEnd();
    }
    
    
	public static final int[] array1 =
		new int[] {
		0, 435,
		5, 439,
		
		24, 407,
		32, 414,
		
		46, 374,
		62, 381,
		
		58, 350,
		74, 363,

		65, 330,
		87, 335,
		
		73, 290,
		97, 296,
		
		77, 244,
		102, 250,
		
		76, 4,
		102, 28,
		
		105, 18,
		334, 28,
		
		326, 19,
		345, 0,

		375, 26,
		334, 28,
		
		358, 38,
		334, 255,
		358, 250,
		
		340,305,
		366,301,
		348,336,
		375,332,
		362,368,
		386,355,
		378,391,
		412,386,
		406,417
	};
	
	public static final int[] fanArray =
		new int[] {
			422, 389,
			412, 386,
			406, 417,
			423, 427,
			440, 433,
			446, 428,
			440, 414,
			436, 397,
			436, 377,
			439, 349,
			446, 321,
			440, 319,
			412, 386 
		};
	
	public static final int[] innerTriangleArray =
		new int[] {
			291, 52,
			319, 82,
			274, 61,
			299, 82,
			222, 76,
			225, 93,
			185, 84,
			201, 97,
			121, 94,
			122, 101
		};
	
	public static final int[] innerTriangleArray1 =
		new int[] {
			225, 93,
			201, 97,
			225, 163,
			201, 163,
			201, 172,
			156, 163,
			154, 172,
			128, 148,
			154, 250,
			130, 213,
			154, 258,
			128, 283,
			154, 270,
			154, 258
		};
	
	public static final int[] innerTriangleArray2 =
		new int[] {
			225, 163,
			201, 172,
			225, 163,
			272, 172,
			271, 163,
			271, 163,
			285, 146,
			272, 172,
			311, 170,
			294, 179,
			272, 172,
			296, 259,
			272, 273,
			272, 172
		};	
	
	public static final int[] innerTriangleArray3 =
		new int[] {
			201, 172,
			225, 172,
			201, 250,
			225, 250,
			201, 258,
			225, 250,
			154, 258,
			154, 250
		};	
	
	public static final int[] innerTriangleArray4 =
		new int[] {
			272, 250,
			272, 258,
			225, 250,
			225, 258,
			201, 258,
			225, 350,
			201, 352,
			149, 385,
			106, 359,
			132, 391,
			124, 396,
			106, 359
		};
	
	public static final int[] innerTriangleArray5 =
		new int[] {
			225, 350,
			149, 385,
			303, 353,
			299, 344,
			225, 350
		};
	
	public static final int[] innerTriangleFanArray1 =
		new int[] {
			325, 355,
			269, 290,
			274, 287,
			323, 338,
			333, 352,
			337, 361,
			337, 373,
			335, 380,
			331, 389,
			325, 394,
			319, 394,
			315, 387,
			303, 353,
			299, 344,
			284, 315,
			269, 290
		};
	
	public static final int[] outlineArray1 =
		new int[] {
			0, 435,
			24, 407,
			46, 374,
			58, 350,
			65, 330,
			73, 290,
			77, 244,
			76, 4,
			105, 18,
			326, 19,
			345, 0,
			375, 26,
			358, 38,
			358, 250,
			366, 301,
			375, 332,
			386, 355,
			412, 386,
			440, 319,
			446, 321,
			439, 349,
			436, 377,
			436, 397,
			440, 414,
			446, 428,
			440, 433,
			423, 427,
			406, 417,
			378, 391,
			362, 368,
			348, 336,
			340, 305,
			334, 255,
			334, 28,
			102, 28,
			102, 250,
			97, 296,
			87, 335,
			74, 363,
			62, 381,
			32, 414,
			5, 439,
			0, 435
		};
	
	public static final int[] outlineArray2 =
		new int[] {
			121, 94,
			185, 84,
			222, 76,
			274, 61,
			291, 52,
			319, 82,
			299, 82,
			225, 93,
			225, 163,
			271, 163,
			285, 146,
			311, 170,
			294, 179,
			296, 259,
			272, 273,
			272, 258,
			225, 258,
			225, 350,
			299, 344,
			284, 315,
			269, 290,
			274, 287,
			323, 338,
			333, 352,
			337, 361,
			337, 373,
			335, 380,
			331, 389,
			325, 394,
			319, 394,
			315, 387,
			303, 353,
			149, 385,
			132, 391,
			124, 396,
			105, 359,
			201, 352,
			201, 258,
			154, 258,
			154, 270,
			128, 283,
			130, 213,
			128, 148,
			156, 163,
			201, 163,
			201, 97,
			122, 101,
			121, 94
	};
	
	public static final int[] outlineArray3 =
		new int[] {
			154, 250,
			154, 172,
			201, 172,
			201, 250,
			154, 250
		};
	
	public static final int[] outlineArray4 =
		new int[] {
			225, 172,
			272, 172,
			272, 250,
			225, 250,
			225, 172
		};	
}
