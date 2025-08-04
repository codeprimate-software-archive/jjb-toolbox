/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JComboBoxTest.java
 * @version v1.0
 * Date: 18 July 2002
 * Modification Date: 18 July 2002
 * @since Java 2
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class JComboBoxTest extends JFrame
{

  public JComboBoxTest()
  {
    super("JComboBox Test");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(200,100));

    getContentPane().setLayout(new FlowLayout());
    getContentPane().add(new JComboBox(new Object[] { "One","Two","Three","Four","Five" }),BorderLayout.CENTER);
    //getContentPane().add(new JComboBox(new Object[] { "A","B","C","D","E" }),BorderLayout.CENTER);
    show();
  }

  public static void main(String[] args)
  {
    new JComboBoxTest();
  }

}

