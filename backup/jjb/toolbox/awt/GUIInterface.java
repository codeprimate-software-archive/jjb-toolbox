/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: GUIInterface.java
 * @version v1.0.1
 * Date: 22 April 2001
 * Modification Date: 20 July 2002
 * @since Java 1.0
 * @see jjb.toolbox.swing.UIInterface
 * @deprecated wasted memory resource
 */

package jjb.toolbox.awt;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

public interface GUIInterface
{

  public static final Color  DARKBLUE		 = new Color(90,80,133);
  public static final Color  LIGHTYELLOW = new Color(247,247,184);
  public static final Color  MAROON      = new Color(176,0,0);
  public static final Color  NAVYBLUE    = new Color(0,0,176);
  public static final Color  GRASSGREEN  = new Color(0,0,176);

  public static final Cursor  DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
  public static final Cursor  HAND_CURSOR    = new Cursor(Cursor.HAND_CURSOR);
  public static final Cursor  WAIT_CURSOR    = new Cursor(Cursor.WAIT_CURSOR);

  //public static final Dimension  DEFAULT_BUTTON_SIZE = new Dimension(70,22);

  public static final Font  BOLD_TIMES_ROMAN_10 = new Font("TimesRoman",Font.BOLD,10);
  public static final Font  BOLD_TIMES_ROMAN_12 = new Font("TimesRoman",Font.BOLD,12);
  public static final Font  BOLD_TIMES_ROMAN_14 = new Font("TimesRoman",Font.BOLD,14);
  public static final Font  BOLD_TIMES_ROMAN_16 = new Font("TimesRoman",Font.BOLD,16);

  public static final Insets EMPTY_BORDER_INSETS2 = new Insets(2,2,2,2);
  public static final Insets EMPTY_BORDER_INSETS5 = new Insets(5,5,5,5);

}

