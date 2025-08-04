/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: EButtonTest.java
 * @version v1.0
 * Date: 14 October 2002
 * Modification Date: 14 October 2002
 * @see jjb.toolbox.swing.EButton
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import jjb.toolbox.swing.EButton;

public class EButtonTest extends JFrame
{
  public EButtonTest()
  {
    super("EButton Test");

    final EButton test = new EButton("Test");

    test.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent ae)
      {
        JOptionPane.showMessageDialog(EButtonTest.this,"Hello!");
      }
    });

    final JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);

    toolbar.setFloatable(false);
    toolbar.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
    toolbar.add(test);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(200,100));
    getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
    getContentPane().add(toolbar);
    show();
  }

  public static void main(String[] args)
  {
    new EButtonTest();
  }
}

