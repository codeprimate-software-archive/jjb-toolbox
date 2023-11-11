/**
 * Copyright (c) 2001, JB Innovations
 * ALL RIGHTS RESERVED
 *
 * @author John J. Blum
 * File: SSNFieldTest.java
 * @version beta1.0
 * Date: 7 November 2001
 * Modification Date: 19 November 2001
 * @since Java 2
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import jjb.toolbox.swing.JSsnField;

public class SSNFieldTest extends JFrame
{

  public SSNFieldTest()
  {
    super("JSsnField");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(200,100));
    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
    getContentPane().add(new JSsnField());
    show();
  }

  public static void main(String[] args)
  {
    new SSNFieldTest();
  }

}

