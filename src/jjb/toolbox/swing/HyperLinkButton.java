/**
 * HyperLinkButton.java (c) 2002.7.20
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */

package jjb.toolbox.swing;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class HyperLinkButton extends JButton {

  public static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
  public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);

  /**
   * Creates an instance of the HyperLinkButton class used
   * to mimic a hyperlink as seen in a Web browser.
   *
   * @param alinkImg is the java.awt.Image used when the
   * button is pressed.
   * @param raisedImg is a java.awt.Image used when the
   * user positions the mouse over the hyperlink button.
   */
  public HyperLinkButton(Image alinkImg,
                         Image raisedImg) {
    this(alinkImg,raisedImg,Toolkit.getDefaultToolkit().createImage(
      new FilteredImageSource(alinkImg.getSource(),new GrayFilter(true,50))));
  }

  /**
   * Creates an instance of the HyperLinkButton class used
   * to mimic a hyperlink as seen in a Web browser.
   *
   * @param alinkImg is the Ljava.awt.Image used when the
   * button is pressed.
   * @param raisedImg is a Ljava.awt.Image used when the
   * user positions the mouse over the hyperlink button.
   * @param brighten a boolean value of true if the pixels
   * should be brightened on the grayed image.
   * @param grayScale is an int in the range 0..100 that
   * determines the percentage of gray, where 100 is the
   * darkest gray, and 0 is the lightest.
   */
  public HyperLinkButton(Image alinkImg,
                         Image raisedImg,
                         boolean brighten,
                         int grayScale    ) {
    this(alinkImg,raisedImg,Toolkit.getDefaultToolkit().createImage
      (new FilteredImageSource(alinkImg.getSource(),
      new GrayFilter(brighten,grayScale))));
  }

  /**
   * Creates an instance of the HyperLinkButton class used
   * to mimic a hyperlink as seen in a Web browser.
   *
   * @param alinkImg is the java.awt.Image used when the
   * button is pressed.
   * @param raisedImg is a java.awt.Image used when the
   * user positions the mouse over the hyperlink button.
   * @param grayedImage is a java.awt.Image used when the
   * button is inactive, meaning the user does not have
   * the mouse button over or pressed on the
   * HyperLinkButton.
   */
  public HyperLinkButton(Image alinkImg,
                         Image raisedImg,
                         Image grayedImg ) {
    super(new ImageIcon(grayedImg));
    addMouseListener(new MouseHandler());
    setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
    setPressedIcon(new ImageIcon(alinkImg));
    setRolloverIcon(new ImageIcon(raisedImg));
  }

  private final class MouseHandler extends MouseAdapter {
    /**
     * mouseEntered raises the button's border.
     *
     * @param me java.awt.event.MouseEvent object.
     */
    public void mouseEntered(MouseEvent me) {
      if (isEnabled())
        setCursor(HAND_CURSOR);
    }

    /**
     * mouseExited sets the button's border to empty.
     *
     * @param me java.awt.event.MouseEvent object.
     */
    public void mouseExited(MouseEvent me) {
      setCursor(DEFAULT_CURSOR);
    }
  }

}

