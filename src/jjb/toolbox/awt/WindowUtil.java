/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * The WindowUtil class is used by applications/applets to center
 * frames and dialog boxes about the desktop environment and
 * application frames respectively.  The WindowUtil class is also
 * a utility class appropriat for any other window operations
 * that are not standard in the Java Platform API.
 *
 * An instance of the WindowUtil class cannot be created since
 * this is a utility class.  The class is also effectively final
 * and therefore, cannot be subclassed.
 *
 * @author John J. Blum
 * File: WindowUtil.java
 * @version v1.0
 * Date: 22 April 2001
 * Modification Date: 23 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.awt;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public final class WindowUtil
{

  /**
   * Default constructor for WindowUtil class which is private to
   * enforce non-instantiability.  A utility class by definition
   * consists only of functions and class level functionality.
   */
  private WindowUtil()
  {
  }

  /**
   * getDesktopLocation returns a screen location, as a Ljava.awt.Point
   * object, to position the frame of an application on the user's
   * desktop of the windows environment (OS).  Note that the positions
   * signifies the top left corner of the frame.
   *
   * @param windowSize is a Ljava.awt.Dimension object specifying the
   * size of the application frame.
   * @return a Ljava.awt.Point location in the desktop environement
   * (screen) to position the application frame.
   */
  public static Point getDesktopLocation(Dimension windowSize)
  {
    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    return new Point((screenSize.width/2 - windowSize.width/2),
                     (screenSize.height/2 - windowSize.height/2));
  }

  /**
   * getDialogLocation returns a screen location, as a Ljava.awt.Point
   * object, to position the dialog centered within the application
   * frame that spawned the dialog box.
   *
   * @param frame is a Ljava.awt.Container object referencing the
   * parent frame to the dialog box, for which the dialog box will
   * be centered about.
   * @param dialogSize is a Ljava.awt.Dimension object specifying 
   * the width and height of the dialog box.
   * @return a Ljava.awt.Point location in the desktop environment
   * (screen) that centers the dialog box about it's parent frame.
   */
  public static Point getDialogLocation(Container frame,
                                        Dimension dialogSize)
  {
    final Point location = frame.getLocationOnScreen();

    final Dimension appSize = frame.getSize();

    int xCenter = appSize.width / 2;
    int yCenter = appSize.height / 2;
    int xOffset = - (dialogSize.width / 2);
    int yOffset = - (dialogSize.height / 2);

    return new Point(location.x + (xCenter + xOffset),location.y + (yCenter + yOffset));
  }

}

