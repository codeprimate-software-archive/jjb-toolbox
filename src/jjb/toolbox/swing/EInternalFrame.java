/**
 * EInternalFrame.java (c) 2002.4.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.1
 * @see javax.swing.JInternalFrame
 * @since Java 2
 */

package jjb.toolbox.swing;

import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;

public abstract class EInternalFrame extends JInternalFrame {

  private EInternalFrame next;
  private EInternalFrame previous;

  /**
   * Constructs a new EInternalFrame window, setting next and
   * previous internal frame references, the window's title,
   * whether the internal frame can be resized and closed, if
   * the internal frame can be maximized and iconified, and
   * the layer in the desktop that the internal frame will
   * appear.
   *
   * @param desktop is the Ljjb.toolbox.swing.EDesktopIF object
   * containing this internal frame.
   * @param next is a Ljjb.toolbox.swing.EInternalFrame object
   * referring to the next internal frame after this internal
   * frame in the sequence of internal frames in the desktop.
   * @param previous is a Ljjb.toolbox.swing.EInternalFrame
   * object referring to the previous internal frame before
   * this internal frame in the sequence of internal frames
   * in the desktop.
   * @param windowTitle is a Ljava.lang.String containing
   * title of the internal frame.
   * @param resiable determines whether the internal frame
   * can be resized by the user.
   * @param closable determines whether the user can close
   * the internal frame on the desktop.
   * @param maximizable determines whether the user can
   * maximize the internal frame.
   * @param iconifiable determines whether the internal
   * frame can iconified.
   * @param layer is a Ljava.lang.Integer object value
   * specifying which layer in the desktop the window
   * will reside.
   */
  public EInternalFrame(EInternalFrame next,
                        EInternalFrame previous,
                        String         windowTitle,
                        boolean        resizable,
                        boolean        closable,
                        boolean        maximizable,
                        boolean        iconifiable,
                        Integer        layer       ) {
    super(windowTitle,resizable,closable,maximizable,iconifiable);
    this.next = next;
    this.previous = previous;
    setLayer(layer);
  }

  /**
   * Constructs a new EInternalFrame window, setting next and
   * previous internal frame references, the internal frame's
   * title, whether the internal frame can be resized and
   * closed and if the internal frame can be maximized and
   * iconified.
   *
   * @param desktop is the Ljjb.toolbox.swing.EDesktopIF object
   * containing this internal frame.
   * @param next is a Ljjb.toolbox.swing.EInternalFrame object
   * referring to the next internal frame after this internal
   * frame in the sequence of internal frames in the desktop.
   * @param previous is a Ljjb.toolbox.swing.EInternalFrame
   * object referring to the previous internal frame before
   * this internal frame in the sequence of internal frames
   * in the desktop.
   * @param windowTitle is a Ljava.lang.String containing
   * title of the internal frame.
   * @param resiable determines whether the internal frame
   * can be resized by the user.
   * @param closable determines whether the user can close
   * the internal frame on the desktop.
   * @param maximizable determines whether the user can
   * maximize the internal frame.
   * @param iconifiable determines whether the internal
   * frame can iconified.
   */
  public EInternalFrame(EInternalFrame next,
                        EInternalFrame previous,
                        String         windowTitle,
                        boolean        resizable,
                        boolean        closable,
                        boolean        maximizable,
                        boolean        iconifiable ) {
    this(next,previous,windowTitle,resizable,closable,maximizable,iconifiable,
      JLayeredPane.PALETTE_LAYER);
  }

  /**
   * Constructs a new EInternalFrame window, setting the
   * window's title, whether the window can be resized
   * and closed, if the window can be maximized and
   * iconified.
   *
   * @param windowTitle is a Ljava.lang.String containing
   * title of the internal frame.
   * @param resiable determines whether the internal frame
   * can be resized by the user.
   * @param closable determines whether the user can close
   * the internal frame on the desktop.
   * @param maximizable determines whether the user can
   * maximize the internal frame.
   * @param iconifiable determines whether the internal
   * frame can iconified.
   */
  public EInternalFrame(String  windowTitle,
                        boolean resizable,
                        boolean closable,
                        boolean maximizable,
                        boolean iconifiable ) {
    this(null,null,windowTitle,resizable,closable,maximizable,iconifiable,
      JLayeredPane.PALETTE_LAYER);
  }

  /**
   * Constructs a new EInternalFrame window, setting the
   * window's title.
   *
   * @param windowTitle is a Ljava.lang.String object
   * containing the title of the internal frame.
   */
  public EInternalFrame(String windowTitle) {
    this(null,null,windowTitle,true,true,true,true,JLayeredPane.PALETTE_LAYER);
  }

  /**
   * getNext method returns the next EInternalFrame after this
   * internal frame in the sequence of internal frames in the
   * desktop.
   *
   * @return a Ljjb.toolbox.swing.EInternalFrame object which
   * refers to the internal frame after this internal frame in
   * the desktop.
   */
  public EInternalFrame getNext() {
    return next;
  }

  /**
   * getPrevious method returns the previous EInternalFrame before
   * this internal frame in the sequence of internal frames on the
   * desktop.
   *
   * @return a Ljjb.toolbox.swing.EInternalFrame object which refers
   * to the internal frame before this internal frame in the desktop.
   */
  public EInternalFrame getPrevious() {
    return previous;
  }

  /**
   * init method re-intializes the state of the EInternalFrame and
   * clears the form if a form model is in place.
   */
  public void init() {
  }

  /**
   * setNext method sets the next EInternalFrame reference in the
   * sequence of internal frames after this internal frame.
   *
   * @param next is a Ljjb.toolbox.swing.EInternalFrame object
   * referring to the next internal frame in the desktop after
   * this internal frame.
   */
  public void setNext(EInternalFrame next) {
    this.next = next;
  }

  /**
   * setNextPrevious method is called to set both the next & previous
   * EInternalFrame references in the sequence of internal frames in
   * the desktop referred to by the desktop instance variable.
   *
   * @param next is a Ljjb.toolbox.swing.EInternalFrame object
   * referring to the next internal frame in the desktop after this
   * internal frame.
   * @param previous is a Ljjb.toolbox.swing.EInternalFrame object
   * referring to the previous internal frame in the desktop before
   * this internal frame.
   */
  public void setNextPrevious(EInternalFrame next,
                              EInternalFrame previous) {
    this.next = next;
    this.previous = previous;
  }

  /**
   * setPrevious method sets the previous EInternalFrame reference
   * in the sequence of internal frames before this internal frame.
   *
   * @param previous is a Ljjb.toolbox.swing.EInternalFrame object
   * referring to the previous internal frame in the desktop before
   * this internal frame.
   */
  public void setPrevious(EInternalFrame previous) {
    this.previous = previous;
  }

}

