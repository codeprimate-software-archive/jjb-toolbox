/**
 * CAboutDialog.java (c) 2002.7.20
 *
 * This class implements a dialog to display information
 * "about" a particular application.
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @since Java 1.0
 */

package jjb.toolbox.awt;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import jjb.toolbox.awt.WindowUtil;
import jjb.toolbox.net.BrowserIF;
import jjb.toolbox.net.URLListener;

public class CAboutDialog extends Dialog {

  public static final Color MAROON = new Color(176,0,0);

  private static final Dimension SIZE = new Dimension(500,150);

  private static final Font BOLD_TIMES_ROMAN_16 =
    new Font("TimesRoman",Font.BOLD,16);
  private static final Font BOLD_TIMES_ROMAN_12 =
    new Font("TimesRoman",Font.BOLD,12);

  /**
   * Creates a new instance of the CAboutDialog class.
   * Initializes state appropriately to present information
   * about an application, for example.
   *
   * @param owner is a Ljava.awt.Frame is the application
   * window that the dialog is  associated to.
   * @param logo is a Ljava.awt.Image is the logo for the
   * application/company.
   * @param applicationName is a Ljava.lang.String is the
   * name of the application.
   * @param versionNumber:Ljava.lang.String is the version
   * of the application.
   * @param copyright is a Ljava.lang.String owner/producer
   * of the application.
   * @param browser is a Ljjb.toolbox.net.BrowserIF object
   * that represents the browser application (i.e. Netscape,
   * or Internet Explorer).
   * @param url is a Ljava.net.URL object to the company's
   * web site or the application's support web page.
   */
  public CAboutDialog(Frame owner,
                      Image logo,
                      String applicationName,
                      String versionNumber,
                      String copyright,
                      BrowserIF browser,
                      URL url                ) {
    super(owner,"About "+applicationName,true);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        dispose();
      }
    });

    setSize(SIZE);
    buildUI(logo,applicationName,versionNumber,copyright,browser,url);
  }

  /**
   * buildUI manages the layout & presentation of the user
   * interface widgets.
   */
  private void buildUI(Image logo,
                       String applicationName,
                       String versionNumber,
                       String copyright,
                       BrowserIF browser,
                       URL url                ) {
    setLayout(new BorderLayout());
    setBackground(Color.lightGray);
    add(new Label(),BorderLayout.WEST); // For Logo
    add(buildInformationPanel(applicationName,versionNumber,copyright,
      browser,url),BorderLayout.CENTER);
    add(buildToolBar(),BorderLayout.SOUTH);
  }

  /**
   * buildInformationPanel constructs the interface for the
   * textual information in the about dialog.
   */
  private Panel buildInformationPanel(String applicationName,
                                      String versionNumber,
                                      String copyright,
                                      BrowserIF browser,
                                      URL url                ) {
    Panel infoPanel = new Panel(new GridLayout(4,1));

    Label tempLabel = null;

    tempLabel = (Label) infoPanel.add(new Label(applicationName,Label.CENTER));
    tempLabel.setFont(BOLD_TIMES_ROMAN_16);
  
    tempLabel = (Label) infoPanel.add(new Label(versionNumber,Label.CENTER));
    tempLabel.setFont(BOLD_TIMES_ROMAN_12);
  
    tempLabel = (Label) infoPanel.add(new Label(copyright,Label.CENTER));
    tempLabel.setFont(BOLD_TIMES_ROMAN_12);
    tempLabel.setForeground(MAROON);

    tempLabel = (Label) infoPanel.add(new Label(url.getHost(),Label.CENTER));
    tempLabel.setFont(BOLD_TIMES_ROMAN_12);
    tempLabel.setForeground(Color.blue);
    tempLabel.addMouseListener(new URLListener(this,browser,url));

    return infoPanel;
  }

  /**
   * buildToolBar builds a toolbar with button components
   * to close the about dialog.
   */
  private Panel buildToolBar() {
    Panel toolbar = new Panel();

    Button close = (Button) toolbar.add(new Button("Close"));

    close.setFont(BOLD_TIMES_ROMAN_12);
    close.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        dispose();
      }
    });

    return toolbar;
  }

}

