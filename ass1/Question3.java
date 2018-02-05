package com.victoria.graphics.assign1;

import java.util.Scanner;

public class Question3 extends GLPixelScreen {

  public Question3(int a, int b) {
    super(300, 300, 2);
    
    drawEllipse(150, 150, a, b, 0f, 1f, 0f, false);
  }

  public static void main(String[] args) {
	Scanner stdin = new Scanner(System.in);
	System.out.print("Enter the radius of the ellipse's a axis (horizontal): ");
	int a = stdin.nextInt();
	System.out.print("Enter the radius of the ellipse's b axis (vertical): ");
	int b = stdin.nextInt();
	new Question3(a, b);
  }
}
