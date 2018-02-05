package com.victoria.graphics.assign1;

import java.util.Scanner;

public class Question2 extends GLPixelScreen {
  private static final long serialVersionUID = 1L;

  public Question2(int r) {
    super(300, 300, 2);
    
    // Bresenham's Circles in Red
    drawCircle(100, 100, r, 1f, 0f, 0f, false);
    drawCircle(100, 200, r, 1f, 0f, 0f, true);
    
    // Brute-Force Circluse in Blue
    drawCircleBrute(200, 100, r, 0f, 0f, 1f, false);
    drawCircleBrute(200, 200, r, 0f, 0f, 1f, true);
  }

  public static void main(String[] args) {
	Scanner stdin = new Scanner(System.in);
	System.out.println("");
	System.out.print("Enter the radii of the circles to draw (will intersect at >50): ");
	int r = stdin.nextInt();
    new Question2(r);
  }
}
