/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: KeyEventUtil.java
 * @version v1.0
 * Date: 14 November 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.awt.event;

import java.awt.event.KeyEvent;

public final class KeyEventUtil
{

  /**
   * Private default constructor to support non-instantiability.
   */
  private KeyEventUtil()
  {
  }

  /**
   * isAlphabetic determines whether the key pressed by the user
   * was an alphabetic key (letters a..z or A..Z).
   *
   * @param keyCode is a unique integer value (key code)
   * representing the keyboard key.
   * @return a boolean value of true if the key pressed was
   * alphabetic, false otherwise.
   */
  public static boolean isAlphabetic(int keyCode)
  {
    switch (keyCode)
    {
    case KeyEvent.VK_A:
    case KeyEvent.VK_B:
    case KeyEvent.VK_C:
    case KeyEvent.VK_D:
    case KeyEvent.VK_E:
    case KeyEvent.VK_F:
    case KeyEvent.VK_G:
    case KeyEvent.VK_H:
    case KeyEvent.VK_I:
    case KeyEvent.VK_J:
    case KeyEvent.VK_K:
    case KeyEvent.VK_L:
    case KeyEvent.VK_M:
    case KeyEvent.VK_N:
    case KeyEvent.VK_O:
    case KeyEvent.VK_P:
    case KeyEvent.VK_Q:
    case KeyEvent.VK_R:
    case KeyEvent.VK_S:
    case KeyEvent.VK_T:
    case KeyEvent.VK_U:
    case KeyEvent.VK_V:
    case KeyEvent.VK_W:
    case KeyEvent.VK_X:
    case KeyEvent.VK_Y:
    case KeyEvent.VK_Z:
      return true;
    default:
      return false;
    }
  }

  /**
   * isAlphaNumeric determines whether the key pressed by the user
   * was either an alphabetic charater or a numeric value.
   *
   * @param keyCode is a unique integer value (key code)
   * representing the keyboard key.
   * @return a boolean value of true if the key pressed was
   * alphanumeric, false otherwise.
   */
  public static boolean isAlphaNumeric(int keyCode)
  {
    return (isAlphabetic(keyCode) || isDigitKey(keyCode));
  }

  /**
   * isDigitKey determines whether the user pressed a numeric key
   * on the key board (standard keys, or numeric key pad).
   *
   * @param keyCode is a unique integer value (key code)
   * representing the keyboard key.
   * @return a boolean value of true if the key pressed was
   * numeric, false otherwise.
   */
  public static boolean isDigitKey(int keyCode)
  {
    switch (keyCode)
    {
    case KeyEvent.VK_0:
    case KeyEvent.VK_1:
    case KeyEvent.VK_2:
    case KeyEvent.VK_3:
    case KeyEvent.VK_4:
    case KeyEvent.VK_5:
    case KeyEvent.VK_6:
    case KeyEvent.VK_7:
    case KeyEvent.VK_8:
    case KeyEvent.VK_9:
    case KeyEvent.VK_NUMPAD0:
    case KeyEvent.VK_NUMPAD1:
    case KeyEvent.VK_NUMPAD2:
    case KeyEvent.VK_NUMPAD3:
    case KeyEvent.VK_NUMPAD4:
    case KeyEvent.VK_NUMPAD5:
    case KeyEvent.VK_NUMPAD6:
    case KeyEvent.VK_NUMPAD7:
    case KeyEvent.VK_NUMPAD8:
    case KeyEvent.VK_NUMPAD9:
      return true;
    default:
      return false;
    }
  }

}

