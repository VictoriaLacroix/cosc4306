package com.victoria.graphics.assign1;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public abstract class GLPixelScreen extends JFrame implements GLEventListener, ActionListener, MouseListener {
  /** Eclipse really wants this constant to be defined. */
  public static final long serialVersionUID = 1L;

  private int width, height, scale;
  /**
   * Array representing RGB subpixels
   */
  private float[][][] pixels;
  protected GL2 gl;

  /**
   * Constructor sets up a JFrame with an OpenGL canvas.
   */
  public GLPixelScreen(int w, int h, int s) {
    // JFrame
    super("Hello, GL!");
    
    // Set up variables
    width = w;
    height = h;
    scale = s;
    pixels = new float[width][height][3];

    // GL
    GLProfile profile = GLProfile.get(GLProfile.GL2);
    GLCapabilities capabilities = new GLCapabilities(profile);
    GLCanvas canvas = new GLCanvas(capabilities);
    //new Animator(canvas).start();
    canvas.addGLEventListener(this);

    // JFrame methods
    setName("Hello, GL!");
    getContentPane().add(canvas);
    setSize(width * scale, height * scale);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setVisible(true);
    addMouseListener(this);
    pack();

    // GL
    canvas.requestFocusInWindow();
  }

  @Override
  public void init(GLAutoDrawable d) {
    gl = d.getGL().getGL2();
    gl.glClearColor(0f, 0f, 0f, 1f);
  }

  @Override
  public void reshape(GLAutoDrawable d, int x, int y, int w, int h) {
    gl.glViewport(0, 0, w, h);
  }
  
  @Override
  public void display(GLAutoDrawable d) {
	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	
    for(int y = 0; y < height; y++) {
      for(int x = 0; x < width; x++) {
    	renderPx(x, y);
      }
    }
	
	gl.glFlush();
  }

  @Override
  public void dispose(GLAutoDrawable d) { }

  /**
   * Draws a GL Quad at coordinate (x,y) from the top-left to emulate a pixel.
   * Colors are determined by the pixels[][][] array defined during construction.
   * @param x   The x-coordinate to draw to.
   * @param y   The y-coordinate to draw to.
   * @param r   Red intensity
   * @param g   Green intensity
   * @param b   Blue intensity
   */
  private void renderPx(int x, int y) {
    // if coord is outside draw area, don't draw.
    if(x < 0 || y < 0 || x >= width || y >= height) {
      return;
    }

    /*
     * One can feasibly change the PX_WIDTH and PX_HEIGHT constants, and this
     * method should automatically compensate for the new "resolution".
     */
    // Calculate the GL coordinates from the integer coordinates.
    float abs_x1 = 2f*((float)x/width)-1f;
    float abs_y1 = 2f*((float)(height - y)/height)-1f;
    float abs_x2 = 2f*((float)(x + 1)/width)-1f;
    float abs_y2 = 2f*((float)(height - y - 1)/height)-1f;

    // Draw a solid square
    gl.glBegin(GL2.GL_QUADS);
	float r = pixels[x][y][0];
	float g = pixels[x][y][1];
	float b = pixels[x][y][2];
    gl.glColor3f(r, g, b);
    gl.glVertex2f(abs_x1, abs_y1);
    gl.glVertex2f(abs_x2, abs_y1);
    gl.glVertex2f(abs_x2, abs_y2);
    gl.glVertex2f(abs_x1, abs_y2);
    gl.glEnd();
  }
  
  @Override
  public void actionPerformed(ActionEvent ae) { }
  
  @Override
  public void mouseClicked(MouseEvent me) {
	System.exit(0);
  }
  
  @Override
  public void mouseEntered(MouseEvent me) { }

  @Override
  public void mouseExited(MouseEvent me) { }

  @Override
  public void mousePressed(MouseEvent me) { }

  @Override
  public void mouseReleased(MouseEvent me) { }
  
  public void drawPx(int x, int y) {
	drawPx(x, y, 1f, 1f, 1f);
  }

  public void drawPx(int x, int y, float r, float g, float b) {
	// if coord is outside draw area, don't draw.
    if(x < 0 || y < 0 || x >= width || y >= height) {
      return;
    }
    pixels[x][y][0] = r;
    pixels[x][y][1] = g;
    pixels[x][y][2] = b;
  }

  public void drawLine(int x1, int y1, int x2, int y2) {
    drawLine(x1, y1, x2, y2, 1f, 1f, 1f);
  }
  
  /**
   * Bresenham's line drawing algorithm.
   * @param x1 x-coordinate of starting point
   * @param y1 y-coordinate of starting point
   * @param x2 x-coordinate of ending point
   * @param y2 y-coordinate of ending-point
   * @param r  red intensity
   * @param g  green intensity
   * @param b  blue intensity
   */
  public void drawLine(int x1, int y1, int x2, int y2, float r, float g, float b) {
	int x = x1;
	int y = y1;
	int dx = x2 - x1;
	int dy = y2 - y1;
	// incrementors for diagonal choice
	int ix1 = (dx > 0) ? 1: -1;
	int iy1 = (dy > 0) ? 1: -1;
	// incrementors for non-diagonal choice (one is =0)
	int ix2 = ix1; // may be changed later
	int iy2 = 0;   // may be changed later
	// find the longer and shorter dimensions
	// an increment over the longer dimension will be done when a vertical/horizontal
	// choice is made in the algorithm
	int longer = Math.abs(dx);
	int shorter = Math.abs(dy);
	// check if the shorter dimension is actually the shorter dimension
	if(shorter > longer) {
	  // if not, correct the vars and the incrementors
	  longer = Math.abs(dy);
	  shorter = Math.abs(dx);
	  ix2 = 0;
	  iy2 = iy1;
	}
	int d = longer/2;
	// iterate over the longer dimension
	for(int i = 0; i <= longer; i++) {
	  drawPx(x, y, r, g, b);
	  // increment the shorter dimension
	  d += shorter;
	  // determine whether to go diagonally or horizontally/vertically on the next pixel
	  if(d >= longer) {
		// choose the diagonal, and decrement the counter
		d -= longer;
		x += ix1;
		y += iy1;
	  } else {
		// choose the non-diagonal
		// one of ix2 or iy2 will be 0
		x += ix2;
		y += iy2;
	  }
	}
  }

  /**
   * Draws a quad. (Either a square or a rectangle - this algorithm doesn't
   * implement anything fancier)
   * @param x1 	    x-coordinate of starting point
   * @param y1      y-coordinate of starting point
   * @param x2      x-coordinate of ending point
   * @param y2      y-coordinate of ending point
   * @param r       red intensity
   * @param g       green intensity
   * @param b       blue intensity
   * @param filled  whether the quad should be filled
   */
  public void drawQuad(int x1, int y1, int x2, int y2, float r, float g, float b, boolean filled) {
	int dy = y2 - y1;
	dy = (dy > 0) ? dy : -dy;
	if(filled) {
	  for(int y = 0; y < dy; y++) {
		int ly = (y1 < y2) ? y1 : y2;
		drawLine(x1, y + ly, x2, y + ly, r, g, b);
	  }
	} else {
	  drawLine(x1, y1, x2, y1);
	  drawLine(x2, y1, x2, y2);
	  drawLine(x2, y2, x1, y2);
	  drawLine(x1, y2, x1, y1);
	}
  }
  
  public void drawCircle(int xc, int yc, int r) {
	drawCircle(xc, yc, r, 1f, 1f, 1f, false);
  }
  
  public void drawCircle(int xc, int yc, int r, boolean filled) {
	drawCircle(xc, yc, r, 1f, 1f, 1f, filled);
  }
  
  public void drawCircle(int xc, int yc, int r, float red, float green, float blue, boolean filled) {
	drawEllipse(xc, yc, r, r, red, green, blue, filled);
  }
  
  /**
   * Draws a white outline of an ellipse
   */
  public void drawEllipse(int xc, int yc, int a, int b) {
	drawEllipse(xc, yc, a, b, 1f, 1f, 1f, false);
  }

  /**
   * Draws a filled while ellipse
   */
  public void drawEllipse(int xc, int yc, int a, int b, boolean filled) {
	drawEllipse(xc, yc, a, b, 1f, 1f, 1f, filled);
  }

  /**
   * Implementation of Bresenham's algorithm for an ellipse. Draws an ellipse
   * with the (x,y) coordinate representing the center of the ellipse, and the
   * a and b axis representing the width and height of the ellipse,
   * respectively.
   * @param xc  The x-coordinate to center at.
   * @param yc  The y-coordinate to center at.
   * @param a   The size of the a-axis (width) of the ellipse.
   * @param b   The size of the b-axis (height) of the ellipse.
   */
  public void drawEllipse(int xc, int yc, int a, int b, float red, float green, float blue, boolean filled) {
    // Axis diameters
    int da = a * a;
    int db = b * b;

    // Starting s ('sigma') values for region a and region b.
    final int sa = (2 * da + db * (1 - 2 * a));
    final int sb = (2 * db + da * (1 - 2 * b));

    // Region 1 and it's mirrors
    for(int x = 0, y = b, s = sb; db * x <= da * y + 1; x++) {
      if(filled) {
    	drawLine(xc - x, yc - y, xc + x, yc - y, red, green, blue);
    	drawLine(xc - x, yc + y, xc + x, yc + y, red, green, blue);
      } else {
    	drawPx(xc + x, yc + y, red, green, blue);
        drawPx(xc - x, yc + y, red, green, blue);
        drawPx(xc + x, yc - y, red, green, blue);
        drawPx(xc - x, yc - y, red, green, blue);  
      }
      if(s >= 0) {
        s += (4 * da) * (1 - y);
        y--;
      }
      s += db * ((4 * x) + 6);
    }
    // Region 2 and it's mirrors
    for(int x = a, y = 0, s = sa; da * y <= db * x + 1; y++) {
      if(filled) {
       	drawLine(xc - x, yc - y, xc + x, yc - y, red, green, blue);
       	drawLine(xc - x, yc + y, xc + x, yc + y, red, green, blue);
      } else {
        drawPx(xc + x, yc + y, red, green, blue);
        drawPx(xc - x, yc + y, red, green, blue);
        drawPx(xc + x, yc - y, red, green, blue);
        drawPx(xc - x, yc - y, red, green, blue);
      }
      if(s >= 0) {
        s += (4 * db) * (1 - x);
        x--;
      }
      s += da * ((4 * y) + 6);
    }
  }
  
  public void drawCircleBrute(int xc, int yc, int r) {
	drawCircleBrute(xc, yc, r, 1f, 1f, 1f, false);
  }
  
  public void drawCircleBrute(int xc, int yc, int r, float red, float green, float blue) {
	drawCircleBrute(xc, yc, r, red, blue, green, false);
  }
  
  public void drawCircleBrute(int xc, int yc, int r, boolean filled) {
	drawCircleBrute(xc, yc, r, 1f, 1f, 1f, filled);
  }
  
  /**
   * Draws a circle using the brute-force algorithm
   * @param xc      x-coordinate to center on
   * @param yc      y-coordinate to center on
   * @param r       radius
   * @param red     red intensity
   * @param green   green intensity
   * @param blue    blue intensity
   * @param filled  whether to fill the circle with color
   */
  public void drawCircleBrute(int xc, int yc, int r, float red, float green, float blue, boolean filled) {
	float pi = (float)Math.PI;
	float dt = 0.0001f * pi;
	for(float i = 0; i <= 2 * pi; i += dt) {
	  // add .5 to the radii here for smoother circles
	  int x = (int)(((double)r + 0.5d) * Math.sin(i));
	  int y = (int)(((double)r + 0.5d) * Math.cos(i));
	  if(filled) {
		drawLine(xc - x, yc + y, xc + x, yc + y, red, green, blue);
	  } else {
		drawPx(xc + x, yc + y, red, green, blue);
	  }
	}
  }
}

