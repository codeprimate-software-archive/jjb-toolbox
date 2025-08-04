/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: EDesktop.java
 * @version v1.0
 * Date: 22 July 2001
 * Modified Date: 20 October 2002
 * @since Java 2
 * @see jjb.toolbox.swing.EDesktopPane
 * @see jjb.toolbox.swing.EInternalFrame
 */

package jjb.toolbox.swing;

import java.beans.PropertyVetoException;
import javax.swing.SwingUtilities;

public interface EDesktop
{

  public static final int HORIZONTAL = SwingUtilities.HORIZONTAL;
  public static final int VERTICAL   = SwingUtilities.VERTICAL;

  /**
   * Organizes all internal frames in this desktop
   * in a cascading fashion.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void cascade() throws PropertyVetoException;

  /**
   * Closes the specified internal frame by setting
   * the Close property of the internal frame to true.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void close(EInternalFrame iframe) throws PropertyVetoException;

  /**
   * Closes the internal frame in this desktop referred
   * to by the key parameter.
   *
   * @param key is a String object referencing the internal
   * frame object displayed in this desktop via the frameHash.
   * @see close(:EInternalFrame)
   */
  public void close(String key) throws PropertyVetoException;

  /**
   * Removes all internal frames on the desktop.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void closeAll() throws PropertyVetoException;

  /**
   * Restores all iconified internal frames in this
   * desktop to their non-iconified state.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   * @see setAllIconified(:boolean)
   */
  public void deiconifyAll() throws PropertyVetoException;

  /**
   * Returns the internal frame on this desktop referred
   * to by it's key.
   *
   * @param key is a reference to an internal frame on the
   * desktop, or null if the key does not refer to any
   * internal frame on the desktop.
   */
  public EInternalFrame get(String key);

  /**
   * Iconifies all internal frames on the desktop.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void iconifyAll() throws PropertyVetoException;

  /**
   * Maximizes all internal frames on this desktop.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void maximizeAll() throws PropertyVetoException;

  /**
   * Minimizes all internal frames on this desktop.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void minimizeAll() throws PropertyVetoException;

  /**
   * Sets the internal frame next to the current internal
   * frame on the desktop to be the current internal
   * frame, and selected.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void next() throws PropertyVetoException;

  /*
   * Adds the internal frame to the desktop referenced
   * by it's title, appended with a count of internal
   * frames opened on this desktop thus far.
   *
   * @param theFrame the internal frame being added to
   * this desktop referenced by it's title and an
   * internal count stored by the desktop.  The
   * getTitle() method of the JInternalFrame class is
   * called to get it's title.
   * @see open(:EInternalFrame,:String)
   */
  public void open(EInternalFrame iframe);

  /**
   * Adds the internal frame to the desktop referenced
   * by the specified key.
   *
   * @param theFrame the internal frame being added to
   * this desktop.
   * @param key is the object referring to the internal
   * frame in the desktop.
   */
  public void open(EInternalFrame iframe,
                   String key            );

  /**
   * Sets the internal frame previous to the current internal
   * frame on the desktop to be the current internal
   * frame, and selected.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void previous() throws PropertyVetoException;

  /**
   * Restores all internal frames on the desktop to their
   * natural state (not maximized, minimized or iconified).
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void restoreAll() throws PropertyVetoException;

  /**
   * Organizes the internal frames in the desktop by
   * either splitting all visible, non-iconified
   * internal frames horizontally or veritcally.
   *
   * @param orientation is a integer specifying the
   * orientation of the split, either EDesktop.HORIZONTAL
   * or EDesktop.VERTICAL.
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   * @see split(:Orientation)
   */
  public void split(int orientation) throws PropertyVetoException;

  /**
   * Organizes the internal frames in this desktop in a
   * tiling fashion.
   *
   * @param orientation is a integer specifying the
   * orientation of the tile, either EDesktop.HORIZONTAL
   * or EDesktop.VERTICAL.
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   * @see tile(:Orientation)
   */
  public void tile(int orientation) throws PropertyVetoException;

}

