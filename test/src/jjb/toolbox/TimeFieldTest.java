/**
 * Copyright (c) 2001, JB Innovations
 * ALL RIGHTS RESERVED
 *
 * @author John J. Blum
 * File: TimeFieldTest.java
 * @version beta1.0
 * Date: 19 November 2001
 * Modification Date: 19 November 2001
 * @since Java 2
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import jjb.toolbox.swing.JTimeField;

public class TimeFieldTest extends JFrame
{

  public TimeFieldTest()
  {
    super("JTimeField");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(200,100));
    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
    getContentPane().add(new JTimeField());
    show();
  }

  public static void main(String[] args)
  {
    new TimeFieldTest();
  }

}

