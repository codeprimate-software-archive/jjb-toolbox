/**
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: JStatusBarTest.java
 * @version v1.0
 * Date: 15 July 2002
 * Modification Date: 15 July 2002
 * @since Java 2
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import jjb.toolbox.swing.JStatusBar;

public class JStatusBarTest extends JFrame
{

  public JStatusBarTest()
  {
    super("JStatusBar Test");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(200,85));
    buildUI();
    show();
  }

  private void buildUI()
  {
    final JStatusBar statusBar = new JStatusBar();

    statusBar.setMessage("Ready");

    Container cont = getContentPane();

    cont.add(statusBar,BorderLayout.CENTER);

    JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);

    toolbar.setBorder(BorderFactory.createEmptyBorder());
    toolbar.setFloatable(false);
    toolbar.setLayout(new FlowLayout(FlowLayout.CENTER));
    cont.add(toolbar,BorderLayout.SOUTH);

    JButton start = (JButton) toolbar.add(new JButton("start"));

    start.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent ae)
      {
        statusBar.begin();
      }
    });

    toolbar.addSeparator();

    JButton stop = (JButton) toolbar.add(new JButton("stop"));

    stop.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent ae)
      {
        statusBar.end();
      }
    });
  }

  public static void main(String[] args)
  {
    new JStatusBarTest();
  }

}

