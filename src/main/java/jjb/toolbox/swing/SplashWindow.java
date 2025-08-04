/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 * 
 * @author John J. Blum
 * File: SplashWindow.java
 * @version v1.0
 * Date: 22 April 2001
 * Modification Date: 18 July 2002
 * @since Java 2
 */

package jjb.toolbox.swing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SplashWindow extends JWindow implements Runnable
{

  private int     pause;

  private JLabel  logoLabel;

  /**
   * Creates an instance of the SplashWindow class by calling
   * the parent constructor to set ownership, uses the Image
   * logo to construct a label, and the sets up a window
   * listener to close the SplashWindow before pause number
   * of seconds if clicked on by the user.
   *
   * @param owner is a Ljava.awt.Frame the parent frame which
   * created an instance of the SplashWindow.
   * @param logo is a Ljava.awt.Image the image to display on
   * the screen.
   * @param numberOfSeconds an integer number of seconds to
   * display the splash window.
   */
  public SplashWindow(Frame owner,
                      Image logo,
                      int   numberOfSeconds)
  {
    super(owner);

    addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent me)
      {
        setVisible(false);
        dispose();
      }
    });

    pause = numberOfSeconds;
    getContentPane().add((logoLabel = new JLabel(new ImageIcon(logo))),BorderLayout.CENTER);
    pack();
  }

  /**
   * run is called by a Thread created by the parent application.
   * Run is responsible for centering and displaying the
   * SplashWindow on the user's desktop for pause number of
   * seconds.
   */
  public void run()
  {
    Dimension screenSize  = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension logoSize    = logoLabel.getPreferredSize();

    setLocation((screenSize.width/2) - (logoSize.width/2),(screenSize.height/2) - (logoSize.height/2));
    setVisible(true);

    try
    {
      Thread.sleep(pause*1000);
    }
    catch (InterruptedException ignore)
    {
    }

    setVisible(false);
    dispose();
  }

}

