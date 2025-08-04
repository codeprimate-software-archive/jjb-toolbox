/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * The BoundedTextFieldListener class replaces the BoundedInputFieldSizeListener
 * class for brevity in name.
 *
 * @author John J. Blum
 * File: BoundedInputFieldSizeListener.java
 * @version v1.0
 * Date: 22 July 2001
 * Modified Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.awt;

import java.awt.TextComponent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BoundedTextFieldListener extends KeyAdapter
{

  private int            maxLength;

  private TextComponent  comp;

  /**
   * Creates an instance of the BoundedTextFieldListener class to
   * monitor text components for text entered and bound the number
   * of characters entered to maxLength.
   *
   * @param comp is a Ljava.awt.TextComponent object to listen for
   * text entered.
   * @param maxLength is an integer specifying the maximum number
   * of allowed characters that the text component should hold.
   */
  public BoundedTextFieldListener(TextComponent comp,
                                  int           maxLength)
  {
    this.maxLength = maxLength;
    this.comp = comp;
  }

  /**
   * keyPressed handles events associated with typing keys.
   * Specifically, the keyEvent method handles key pressed
   * events in the text component of interest to keep the 
   * number of characters entered from surpassing the
   * maximum length, as recorded by this class when it was
   * initialized.
   *
   * @param ke:Ljava.awt.event.KeyEvent object encapsulating
   * the key event.
   * @throws a Ljava.lang.RuntimeException when the max length
   * has been reached to notify the client.
   */
  public void keyPressed(KeyEvent ke)
  {
    int length = comp.getText().length();

    if (length > maxLength)
    {
      int keyCode = ke.getKeyCode();
      if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE)
        return;
      else
      {
        ke.consume();
        // I should not be throwing an instance of RuntimeException here, but what can I do?
        // This violates the contract of the keyPressed method of the KeyListener interface.
        throw new RuntimeException("You have exceeded the maximum length of "+maxLength+" characters.\nPlease refine you text body.");
      }
    }
  }

}

