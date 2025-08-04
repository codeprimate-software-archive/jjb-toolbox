/**
 * GridCanvasTest.java (c)2002.12.12
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2002.12.12
 */

package jjb.toolbox.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

public final class GridCanvasTest extends JFrame
{

  public GridCanvasTest()
  {
    super("GridCanvas Test");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(300,250));
    final JLabel coordinates = new JLabel(" ",JLabel.CENTER);
    final GridCanvas grid = new GridCanvas();
    grid.addMouseMotionListener(new MouseInputAdapter()
    {
      public void mouseMoved(MouseEvent me)
      {
        coordinates.setText(grid.getGridCoordinates(
          me.getPoint()).toString());
      }
    });
    getContentPane().add(grid,BorderLayout.CENTER);
    getContentPane().add(coordinates,BorderLayout.SOUTH);
    show();
  }

  public static void main(String[] args)
  {
    new GridCanvasTest();
  }

}

