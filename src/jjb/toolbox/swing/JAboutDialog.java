/**
 * JAboutDialog.java (c) 2001.4.17
 *
 * This class implements a frame to display information about a particular
 * application.
 * NOTE: the JAboutDialog class replaces the EAboutDialog class.
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see javax.swing.JDialog
 */

package jjb.toolbox.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import jjb.toolbox.net.BrowserIF;
import jjb.toolbox.net.URLListener;

public class JAboutDialog extends JDialog {

  private static final Dimension SIZE = new Dimension(370,125);

  private static final Font BOLD_TIMES_ROMAN_16 =
    new Font("TimesRoman",Font.BOLD,16);
  private static final Font BOLD_TIMES_ROMAN_12 =
    new Font("TimesRoman",Font.BOLD,12);

  /**
   * Constructs a new about dialog box with logo, application name,
   * version number of application, copyright of the company, and
   * the url to the company's web site.
   *
   * @param owner is a jjb.toolbox.swing.EFrame container owning
   * this popup dialog.
   * @param logo is a java.awt.Image object representing either
   * the company or application logo.
   * @param applicationName is a java.lang.String name of the
   * application for which the about dialog is about.
   * @param versionNumber is a java.lang.String version of the
   * application.
   * @param copyright java.lang.String is the companies disclosure
   * of the copyright protection.
   * @param browser:Ljjb.toolbox.net.BrowserIF object representing
   * the component in the application that has access to the web
   * browser (i.e. applet).
   * @param url java.net.URL object to the companies web site.
   */
  public JAboutDialog(JFrame owner,
                      Image logo,
                      String applicationName,
                      String versionNumber,
                      String copyright,
                      BrowserIF browser,
                      URL url                ) {
    super(owner,"About "+applicationName,true);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setResizable(false);
    setSize(SIZE);
    buildUI(logo,applicationName,versionNumber,copyright,browser,url);
  }

  /**
   * buildInformationPanel lays out the applications name, version
   * number, copyright information, and url in the about dialog box.
   *
   * @param applicationName:Ljava.lang.String name of the application
   * for which the about dialog is about.
   * @param versionNumber:Ljava.lang.String version of the application.
   * @param copyright:Ljava.lang.String is the companies disclosure of
   * the copyright protection.
   * @param browser:Ljjb.toolbox.net.BrowserIF object representing the
   * component in the application that has access to the web browser
   * (i.e. applet).
   * @param url:Ljava.net.URL object to the companies web site.
   * @return a Ljavax.swing.JPanel object containing the content.
   */
  private JPanel buildInformationPanel(String applicationName,
                                       String versionNumber,
                                       String copyright,
                                       BrowserIF browser,
                                       URL url                ) {
    final JPanel infoPanel = new JPanel(new GridLayout(5,1));

    infoPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

    JLabel tempLabel = null;

    // Application Name
    tempLabel = (JLabel) infoPanel.add(new JLabel(applicationName,JLabel.CENTER));
    tempLabel.setFont(BOLD_TIMES_ROMAN_16);
    tempLabel.setForeground(Color.black);
  
    // Version Number
    tempLabel = (JLabel) infoPanel.add(new JLabel(versionNumber,JLabel.CENTER));
    tempLabel.setFont(BOLD_TIMES_ROMAN_12);
    tempLabel.setForeground(Color.black);
  
    // Copyright
    tempLabel = (JLabel) infoPanel.add(new JLabel(copyright,JLabel.CENTER));
    tempLabel.setFont(BOLD_TIMES_ROMAN_12);
    tempLabel.setForeground(Color.black);

    infoPanel.add(new JLabel());

    // URL
    tempLabel = (JLabel) infoPanel.add(new JLabel("<html><u><font color=blue>"+url.getHost()+"</font></u></html>",JLabel.CENTER));
    tempLabel.addMouseListener(new URLListener(this,browser,url));
    tempLabel.setFont(BOLD_TIMES_ROMAN_12);

    return infoPanel;
  }

  /**
   * buildToolBar constructs a toolbar containing a close button to
   * exit the about dialog box.
   *
   * @return a javax.swing.JToolBar component containing a close
   * button.
   */
  private JToolBar buildToolBar() {
    final JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);

    toolbar.setBorder(BorderFactory.createEmptyBorder());
    toolbar.setFloatable(false);
    toolbar.setLayout(new FlowLayout());

    JButton close = (JButton) toolbar.add(new JButton("Close"));

    close.setFont(BOLD_TIMES_ROMAN_12);
    close.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        dispose();
      }
    });

    return toolbar;
  }

  /**
   * buildUI constructs the user interface to the about dialog box.
   *
   * @param logo java.awt.Image object representing either the
   * company or application logo.
   * @param applicationName java.lang.String name of the application
   * for which the about dialog is about.
   * @param versionNumber java.lang.String version of the application.
   * @param copyright java.lang.String is the companies disclosure of
   * the copyright protection.
   * @param browser jjb.toolbox.net.BrowserIF object representing the
   * component in the application that has access to the web browser
   * (i.e. applet).
   * @param url java.net.URL object to the companies web site.
   */
  private void buildUI(Image logo,
                       String applicationName,
                       String versionNumber,
                       String copyright,
                       BrowserIF browser,
                       URL url                )
  {
    final Container contentPane = getContentPane();

    setBackground(Color.lightGray);
    contentPane.setLayout(new BorderLayout());

    JLabel logoLabel = new JLabel(new ImageIcon(logo));

    logoLabel.addMouseListener(new URLListener(this,browser,url));
    contentPane.add(logoLabel,BorderLayout.WEST);
    contentPane.add(buildInformationPanel(applicationName,versionNumber,
      copyright,browser,url),BorderLayout.CENTER);
  }

}

