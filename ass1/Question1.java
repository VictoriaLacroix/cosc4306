package com.victoria.graphics.assign1;

public class Question1 extends GLPixelScreen {
  private static final long serialVersionUID = 1L;

  public Question1() {
	// Create a GLPixel Screen in 300x300 resolution, and make every pixel 2x as 
	// large to emphasize them
	super(300, 300, 2);
	// Blue background
	drawQuad(0, 0, 300, 300, 0f, 0f, 1f, true);
	// Head
	drawCircle(150, 150, 100, 1f, 1f, 0f, true);
	// Open Mouth
	drawEllipse(150, 190, 60, 20, 0f, 0f, 0f, true);
	// Cover top half of the open mouth
	drawQuad(90, 170, 210, 190, 1f, 1f, 0f, true);
	// right eye (using the brute-force solution)
	drawCircleBrute(110, 100, 11, 0f, 0f, 0f, true);
	// left eye (using bresenham's algorithm)
	drawCircle(190, 100, 10, 0f, 0f, 0f, true);
	
	repaint();
  }

  public static void main(String[] args) {
	new Question1();
  }
}
