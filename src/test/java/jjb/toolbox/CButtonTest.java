/**
 * Copyright (c) 2001, JB Innovations
 * ALL RIGHTS RESERVED
 *
 * @author John J. Blum
 * File: CButtonTest.java
 * @version v1.0
 * Date: 7 November 2001
 * Modification Date: 28 November 2001
 * @since Java 2
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import jjb.toolbox.awt.CButton;
import jjb.toolbox.awt.ImageUtil;

public class CButtonTest extends JFrame
{

  public CButtonTest()
  {
    super("CButton");

    final CButton cbutton = new CButton(ImageUtil.getUpArrowImage(new Dimension(25,25)))
    {
      public Dimension getPreferredSize()
      {
        return new Dimension(50,35);
      }
    };

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(200,100));
    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
    getContentPane().add(cbutton);
    show();
  }

  public static void main(String[] args)
  {
    new CButtonTest();
  }

}

