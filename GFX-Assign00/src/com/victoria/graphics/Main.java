package com.victoria.graphics;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.JFrame;

public class Main extends JFrame implements GLEventListener {
  public static final long serialVersionUID = 1L;

  private static final int WIDTH  = 800;
  private static final int HEIGHT = 600;

  public Main() {
    super("Hello, GL!");
    GLProfile profile = GLProfile.get(GLProfile.GL4);
    GLCapabilities capabilities = new GLCapabilities(profile);
    GLCanvas canvas = new GLCanvas(capabilities);
    canvas.addGLEventListener(this);

    this.setName("Hello, GL!");
    this.getContentPane().add(canvas);
    this.setSize(WIDTH, HEIGHT);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setVisible(true);
    canvas.requestFocusInWindow();
    canvas.
  }

  public void play() { }

  @Override
  public void display(GLAutoDrawable d) {
    GL4 gl = d.getGL().getGL4();
    gl.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);

    // graphics code

    gl.glFlush();
  }

  @Override
  public void dispose(GLAutoDrawable d) { }

  @Override
  public void init(GLAutoDrawable d) {
    GL4 gl = d.getGL().getGL4();
    gl.glClearColor(0.392f, 0.392f, 0.392f, 1.0f);
  }

  @Override
  public void reshape(GLAutoDrawable d, int x, int y, int w, int h) { }

  public static void main(String[] args) {
    new Main().play();
  }
}

