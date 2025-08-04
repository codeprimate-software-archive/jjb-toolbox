/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: EDesktopPane.java
 * @version v1.0
 * Date: 22 July 2001
 * Modification Date: 20 October 2002
 * @since Java 2
 * @see javax.swing.JDesktopPane
 * @see jjb.toolbox.swing.EDesktop
 */

package jjb.toolbox.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class EDesktopPane extends JDesktopPane
  implements EDesktop
{

  private int frameColumn;
  private int frameCount;
  private int frameX;
  private int frameY;

  private ActionListener cascadeListener;
  private ActionListener closeAllListener;
  private ActionListener closeListener;
  private ActionListener deiconifyAllListener;
  private ActionListener iconifyAllListener;
  private ActionListener horizontalSplitListener;
  private ActionListener horizontalTileListener;
  private ActionListener maximizeAllListener;
  private ActionListener minimizeAllListener;
  private ActionListener nextListener;
  private ActionListener previousListener;
  private ActionListener restoreAllListener;
  private ActionListener verticalSplitListener;
  private ActionListener verticalTileListener;

  private Color backgroundColor;

  private EInternalFrame currentFrame;
  private EInternalFrame lastFrame;

  private FocusListener frameFocusListener;

  private Image backgroundImage;

  private InternalFrameListener frameListener;

  private final Map frameHash;

  /**
   * Creates an instanc of the EDesktopPane class with default
   * properties for background color and no image within the
   * desktop.
   */
  public EDesktopPane()
  {
    this(null,new Color(66,66,66));
  }

  /**
   * Creeates and instance of the EDesktopPane class initialized
   * by setting the backgroundImage and backgroundColor properties
   * of the desktop
   *
   * @param backgroundImage the image centered in the background
   * of the desktop.
   * @param backgroundColor is the background color of the
   * desktop.
   */
  public EDesktopPane(Image backgroundImage,
                      Color backgroundColor )
  {
    frameColumn = 0;
    frameCount = 0;
    frameX = 0;
    frameY = 0;
    this.backgroundColor = backgroundColor;
    currentFrame = null;
    lastFrame = null;
    this.backgroundImage = backgroundImage;
    frameHash = new HashMap();

    initShortcutMenu();
    putClientProperty("JDesktopPane.dragMode","outline");

    addKeyListener(new KeyAdapter()
    {
      /**
       * keyPressed is called when the user presses a key while in the
       * desktop to cycle through the internal frames on the desktop.
       * By holding down the control key and pressing and releasing the
       * tab key, a user cycles to the next internal frame after the
       * current internal frame in the desktop.  By holding down the
       * control key in in combination with the shift while pressing
       * and releasing the tab key, the user moves to the previous
       * internal frame in the desktop.
       *
       * @param ke:Ljava.awt.event.KeyEvent encapsulating information
       * about the key pressed.
       */
      public void keyPressed(KeyEvent ke)
      {
        try
        {
          if (ke.isControlDown() && ke.getKeyCode() == KeyEvent.VK_TAB)
            next();

          if (ke.isControlDown() && ke.isShiftDown() && ke.getKeyCode() == KeyEvent.VK_TAB)
            previous();
        }
        catch (PropertyVetoException pve)
        {
          pve.printStackTrace(System.err);
        }
      }
    });
  }

  /**
   * The Orientation class is a type-safe enum expressing
   * orientation of the internal frames on this desktop.
   */
  public static final class Orientation
  {
    public static final Orientation HORIZONTAL =
      new Orientation(SwingConstants.HORIZONTAL);
    public static final Orientation VERTICAL   =
      new Orientation(SwingConstants.VERTICAL);

    private final int orientation;

    private Orientation(int orientation)
    {
      this.orientation = orientation;
    }

    public boolean equals(Object obj)
    {
      if (this == obj)
        return true;

      if (!(obj instanceof Orientation))
        return false;

      return (orientation == ((Orientation) obj).orientation);
    }

    public static final Orientation getOrientation(int orientation)
    {
      if (orientation == SwingConstants.HORIZONTAL)
        return HORIZONTAL;
      else if (orientation == SwingConstants.VERTICAL)
        return VERTICAL;
      else
        return HORIZONTAL;
    }

    public int hashCode()
    {
      return 37*17+orientation;
    }

    public String toString()
    {
      return (orientation == SwingConstants.HORIZONTAL ?
        "Horizontal Orientation" : "Vertical Orientation");
    }
  }

  private final class SplitListener implements ActionListener
  {
    private final Orientation orientation;

    /**
     * Creates an instance of the SplitListener class to
     * split the visible internal frames of this desktop
     * either horizontally or vertically.
     */
    public SplitListener(Orientation orientation)
    {
      this.orientation = orientation;
    }

    /**
     * Invoked when an ActionEvent is triggered, representing
     * a request by the user to split internal frames on the
     * desktop.
     *
     * @param ae is the ActionEvent encapsulating the user's
     * request to split the internal frames on the desktop.
     */
    public void actionPerformed(ActionEvent ae)
    {
      try
      {
        split(orientation);
      }
      catch (PropertyVetoException pve)
      {
        pve.printStackTrace(System.err);
      }
    }
  }

  private final class TileListener implements ActionListener
  {
    private final Orientation orientation;

    /**
     * Creates an instance of the TileWindowListener class to
     * tile the visible internal frames in this desktop 
     * horizontally or vertically.
     */
    public TileListener(Orientation orientation)
    {
      this.orientation = orientation;
    }

    /**
     * Invoked when an ActionEvent is triggered, representing
     * a request by the user to tile internal frames on the
     * desktop.
     *
     * @param ae is the ActionEvent encapsulating the user's
     * request to tile the internal frames on the desktop.
     */
    public void actionPerformed(ActionEvent ae)
    {
      try
      {
        tile(orientation);
      }
      catch (PropertyVetoException pve)
      {
        pve.printStackTrace(System.err);
      }
    }
  }

  /**
   * Organizes all internal frames in this desktop
   * in a cascading fashion.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void cascade() throws PropertyVetoException
  {
    final JInternalFrame[] frames = getAllFrames();

    // Nothing to do... No Frames!
    if (frames.length == 0)
      return;

    final int desktopHeight = getHeight();
    final int desktopWidth  = getWidth();
    final int frameHeight   = desktopHeight / 2;
    final int frameWidth    = desktopWidth / 2;

    JInternalFrame theFrame = null;

    int frameDistance = frames[0].getHeight()
      - frames[0].getContentPane().getHeight();
    int col = 0;
    int x   = 0;
    int y   = 0;

    for (int index = 0; index < frames.length; index++)
    {
      theFrame = frames[index];

      if (theFrame.isVisible() && !theFrame.isIcon())
      {
        theFrame.setMaximum(false);
        theFrame.reshape(x,y,frameWidth,frameHeight);
        theFrame.moveToFront();

        x += frameDistance;
        y += frameDistance;

        if (x + frameWidth > desktopWidth)
        {
          x = (++col * frameDistance);
          x = (x + frameWidth > desktopWidth ? 0 : x);
          y = 0;
        }

        if (y + frameHeight > desktopHeight)
        {
          x = (++col * frameDistance);
          y = 0;
        }
      }
    }
  }

  /**
   * Closes the internal frame specified by the object
   * reference.
   *
   * @param frame the internal frame being closed and
   * removed from this desktop.
   */
  private void _close(final EInternalFrame theFrame)
  {
    final int numFrame = 
      getComponentCountInLayer(theFrame.getLayer());

    if (numFrame > 0)
    {
      final EInternalFrame previousFrame = theFrame.getPrevious();
      final EInternalFrame nextFrame     = theFrame.getNext();

      previousFrame.setNext(nextFrame);
      nextFrame.setPrevious(previousFrame);

      if (theFrame == lastFrame)
        lastFrame = previousFrame;

      currentFrame = previousFrame;
      currentFrame.show();
    }
    else
       currentFrame = lastFrame = null;

    theFrame.removeFocusListener(getFrameFocusListener());
    theFrame.removeInternalFrameListener(getFrameListener());
    theFrame.setNextPrevious(null,null);
    theFrame.setVisible(false);
  }

  /**
   * Closes the specified internal frame by setting
   * the Close property of the internal frame to true.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void close(final EInternalFrame theFrame)
    throws PropertyVetoException
  {
    if (theFrame == null)
      return;

    theFrame.setClosed(true);
  }

  /**
   * Closes the internal frame in this desktop referred
   * to by the key parameter.
   *
   * @param key is a String object referencing the internal
   * frame object displayed in this desktop via the frameHash.
   * @see close(:EInternalFrame)
   */
  public void close(final String key)
    throws PropertyVetoException
  {
    close((EInternalFrame) frameHash.get(key));
  }

  /**
   * Removes all internal frames on the desktop.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void closeAll()
   throws PropertyVetoException
  {
    final JInternalFrame[] frameList = getAllFrames();
    for (int index = frameList.length; --index >= 0; )
      (frameList[index]).setClosed(true);
    frameX = frameY = frameColumn = 0;
  }

  /**
   * Restores all iconified internal frames in this
   * desktop to their non-iconified state.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   * @see setAllIconified(:boolean)
   */
  public void deiconifyAll()
    throws PropertyVetoException
  {
    setAllIconified(false);
  }

  /**
   * Returns the internal frame on this desktop referred
   * to by it's key.
   *
   * @param key is a reference to an internal frame on the
   * desktop, or null if the key does not refer to any
   * internal frame on the desktop.
   */
  public EInternalFrame get(String key)
  {
    return (EInternalFrame) frameHash.get(key);
  }

  /**
   * Returns a ActionListener for positioning the
   * internal frames on this desktop in an cascading
   * fashion.
   *
   * @return a java.awt.event.ActionListener for
   * cascading internal frames on the desktop.
   */
  public ActionListener getCascadeListener()
  {
    if (cascadeListener == null)
    {
      cascadeListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          try
          {
            cascade();
          }
          catch (PropertyVetoException pve)
          {
            pve.printStackTrace(System.err);
          }
        }
      };
    }

    return cascadeListener;
  }

  /**
   * Returns a ActionListener for closing all
   * internal frames on the desktop.
   *
   * @return a java.awt.event.ActionListener for
   * closing all internal frames on the desktop.
   */
  public ActionListener getCloseAllListener()
  {
    if (closeAllListener == null)
    {
      closeAllListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          try
          {
            closeAll();
          }
          catch (PropertyVetoException pve)
          {
            pve.printStackTrace(System.err);
          }
        }
      };
    }

    return closeAllListener;
  }

  /**
   * Returns a ActionListener for closing the 
   * currently selected internal frame on this
   * desktop.
   *
   * @reurn a java.awt.event.ActionListener for
   * closing the current internal frame on the
   * desktop.
   */
  public ActionListener getCloseListener()
  {
    if (closeListener == null)
    {
      closeListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          try
          {
            close(currentFrame);
          }
          catch (PropertyVetoException pve)
          {
            pve.printStackTrace(System.err);
          }
        }
      };
    }

    return closeListener;
  }

  /**
   * Returns a ActionListener for deiconifying all
   * internal frames on the desktop.
   *
   * @return a java.awt.event.ActionListener for
   * deiconifying all internal frames on the desktop.
   */
  public ActionListener getDeiconifyAllListener()
  {
    if (deiconifyAllListener == null)
    {
      deiconifyAllListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          try
          {
            deiconifyAll();
          }
          catch (PropertyVetoException pve)
          {
            pve.printStackTrace(System.err);
          }
        }
      };
    }

    return deiconifyAllListener;
  }

  /**
   * Returns a ActionListener that iconifies all
   * internal frames on the desktop.
   *
   * @return a java.awt.event.ActionListener for
   * iconifying all internal frames on the desktop.
   */
  public ActionListener getIconifyAllListener()
  {
    if (iconifyAllListener == null)
    {
      iconifyAllListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          try
          {
            iconifyAll();
          }
          catch (PropertyVetoException pve)
          {
            pve.printStackTrace(System.err);
          }
        }
      };
    }

    return iconifyAllListener;
  }

  /**
   * Returns a FocuseListener for determining which
   * of the internal frames on the desktop has received
   * the focus.
   *
   * @return a java.awt.event.FocusListener for listening
   * for internal frame focus events on the desktop.
   */
  private FocusListener getFrameFocusListener()
  {
    if (frameFocusListener == null)
    {
      frameFocusListener = new FocusAdapter()
      {
        public void focusGained(FocusEvent fe)
        {
          currentFrame = (EInternalFrame) fe.getSource();
        }
      };
    }

    return frameFocusListener;
  }

  /**
   * Returns a InternalFrameListener responsible for 
   * notifying the desktop that the user has requested
   * a close action of the internal frame.
   *
   * @return a javax.swing.event.InternalFrameListener
   * responsible for notifying the desktop of close
   * actions invoked on it's internal frames.
   */
  private InternalFrameListener getFrameListener()
  {
    if (frameListener == null)
    {
      frameListener = new InternalFrameAdapter()
      {
        public void internalFrameClosed(InternalFrameEvent ife)
        {
          _close((EInternalFrame) ife.getSource());
        }
      };
    }

    return frameListener;
  }

  /**
   * Returns a ActionListener to maximize all internal
   * frames on this desktop.
   *
   * @return a java.awt.event.ActionListener that
   * maximizes all internal frames on the desktop.
   */
  public ActionListener getMaximizeAllListener()
  {
    if (maximizeAllListener == null)
    {
      maximizeAllListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          try
          {
            maximizeAll();
          }
          catch (PropertyVetoException pve)
          {
            pve.printStackTrace(System.err);
          }
        }
      };
    }

    return maximizeAllListener;
  }

  /**
   * Returns a ActionListener to minimize all internal
   * frames on this desktop.
   *
   * @return a java.awt.event.ActionListener that
   * minimizes all internal frames in the desktop.
   */
  public ActionListener getMinimizeAllListener()
  {
    if (minimizeAllListener == null)
    {
      minimizeAllListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          try
          {
            minimizeAll();
          }
          catch (PropertyVetoException pve)
          {
            pve.printStackTrace(System.err);
          }
        }
      };
    }

    return minimizeAllListener;
  }

  /**
   * Returns a ActionListener to set the next
   * interal frame in the sequence on this
   * desktop to have focus.
   *
   * @return a java.awt.event.ActionListener to
   * set the next internal frame on the desktop
   * to have focus.
   */
  public ActionListener getNextListener()
  {
    if (nextListener == null)
    {
      nextListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          try
          {
            next();
          }
          catch (PropertyVetoException pve)
          {
            pve.printStackTrace(System.err);
          }
        }
      };
    }

    return nextListener;
  }

  /**
   * Returns a ActionListener to set the previous
   * interal frame in the sequence on this
   * desktop to have focus.
   *
   * @return a java.awt.event.ActionListener to
   * set the previous internal frame on the desktop
   * to have focus.
   */
  public ActionListener getPreviousListener()
  {
    if (previousListener == null)
    {
      previousListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          try
          {
            previous();
          }
          catch (PropertyVetoException pve)
          {
            pve.printStackTrace(System.err);
          }
        }
      };
    }

    return previousListener;
  }

  /**
   * Returns a ActionListener to restore all
   * internal frames on the desktop to a normal
   * state (neither iconified, nor maximized.
   *
   * @return a java.awt.event.ActionListener to
   * restore all internal frames in the desktop.
   */
  public ActionListener getRestoreAllListener()
  {
    if (restoreAllListener == null)
    {
      restoreAllListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent ae)
        {
          try
          {
            restoreAll();
          }
          catch (PropertyVetoException pve)
          {
            pve.printStackTrace(System.err);
          }
        }
      };
    }

    return restoreAllListener;
  }

  /**
   * Returns a ActionListener object to split the
   * internal frames on the desktop vertically or
   * horizontally to share the realstate of the
   * desktop.
   *
   * @return a java.awt.event.ActionListener to
   * split the internal frames on the desktop.
   */
  public ActionListener getSplitListener(Orientation orientation)
  {
    if (orientation == Orientation.HORIZONTAL)
    {
      if (horizontalSplitListener == null)
        horizontalSplitListener = new SplitListener(orientation);

      return horizontalSplitListener;
    }
    else
    {
      if (verticalSplitListener == null)
        verticalSplitListener = new SplitListener(orientation);

      return verticalSplitListener;
    }
  }

  /**
   * Returns a ActionListener object to tile the
   * internal frames on the desktop vertically or
   * horizontally to share the realstate of the
   * desktop.
   *
   * @return a java.awt.event.ActionListener to
   * tile the internal frames on the desktop.
   */
  public ActionListener getTileListener(Orientation orientation)
  {
    if (orientation == Orientation.HORIZONTAL)
    {
      if (horizontalTileListener == null)
        horizontalTileListener = new TileListener(orientation);

      return horizontalTileListener;
    }
    else
    {
      if (verticalTileListener == null)
        verticalTileListener = new TileListener(orientation);

      return verticalTileListener;
    }
  }

  /**
   * Initializes the shortcut menu for this desktop.
   */
  private void initShortcutMenu()
  {
    final JPopupMenu shortcutMenu = new JPopupMenu();

    JMenuItem menuItem = null;

    menuItem = shortcutMenu.add(new JMenuItem("Next"));
    menuItem.addActionListener(getNextListener());
    menuItem = shortcutMenu.add(new JMenuItem("Previous"));
    menuItem.addActionListener(getPreviousListener());
    shortcutMenu.addSeparator();
    menuItem = shortcutMenu.add(new JMenuItem("Close All"));
    menuItem.addActionListener(getCloseAllListener());

    addMouseListener(new MouseAdapter()
    {
      public void mouseReleased(MouseEvent me)
      {
        if (me.isPopupTrigger())
          shortcutMenu.show(EDesktopPane.this,me.getX(),me.getY());
      }
    });
  }

  /**
   * Iconifies all internal frames on the desktop.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void iconifyAll()
    throws PropertyVetoException
  {
    setAllIconified(true);
  }

  /**
   * Sets the background of this desktop to transparent.
   *
   * @return a false boolean value.
   */
  public boolean isOpaque()
  {
    return false;
  }

  /**
   * Maximizes all internal frames on this desktop.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void maximizeAll()
    throws PropertyVetoException
  {
    setAllMaximized(true);
  }

  /**
   * Minimizes all internal frames on this desktop.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void minimizeAll()
    throws PropertyVetoException
  {
    setAllMaximized(false);
  }

  /**
   * Sets the internal frame next to the current internal
   * frame on the desktop to be the current internal
   * frame, and selected.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void next()
    throws PropertyVetoException
  {
    if (currentFrame == null)
      return;

    final EInternalFrame nextFrame = currentFrame.getNext();
    nextFrame.setSelected(true);
    currentFrame = nextFrame;
  }

  /**
   * Adds the internal frame to the desktop referenced
   * by the specified key.
   *
   * @param theFrame the internal frame being added to
   * this desktop.
   * @param key is the object referring to the internal
   * frame in the desktop.
   */
  public void open(final EInternalFrame theFrame,
                   final String key              )
  {
    if (theFrame == null)
    {
      throw new NullPointerException("The internal frame being added "
        +" to this desktop cannot be null.");
    }

    if (key == null)
    {
      throw new NullPointerException("The key referrencing the internal "
        + "frame on this desktop cannot be null.");
    }

    final EInternalFrame firstFrame = 
      (this.lastFrame == null ? theFrame : this.lastFrame.getNext());
    final EInternalFrame lastFrame =
      (this.lastFrame == null ? theFrame : this.lastFrame);

    // Setup Desktop Links and Listeners
    firstFrame.setPrevious(theFrame);
    lastFrame.setNext(theFrame);
    theFrame.setNextPrevious(firstFrame,lastFrame);
    currentFrame = this.lastFrame = theFrame;
    theFrame.addFocusListener(getFrameFocusListener());
    theFrame.addInternalFrameListener(getFrameListener());

    // Reference and Add Internal Frame to Desktop
    frameHash.put(key,theFrame);
    add(theFrame,new Integer(theFrame.getLayer()));

    // Initialize the Internal Frame and Display It.
    theFrame.init();
    setFrameLocation(theFrame);
    theFrame.show();
  }

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
  public void open(final EInternalFrame theFrame)
  {
    open(theFrame,theFrame.getTitle()+frameCount++);
  }

  /**
   * paint is used by the Java VM to paint the desktop on
   * screen.
   *
   * @param g Graphics object used to perform paint
   * operations.
   */
  public void paint(Graphics g)
  {
    if (backgroundImage != null)
    {
      g.drawImage(backgroundImage,
        (int)(this.getWidth()/2-backgroundImage.getWidth(this)/2),
          (int)(this.getHeight()/2-backgroundImage.getHeight(this)/2),
            backgroundImage.getWidth(this),backgroundImage.getHeight(this),
              this);
    }

    // This needs work!
    super.paint(g);
    super.getParent().setBackground((backgroundColor == null ?
      Color.black : backgroundColor));
  }

  /**
   * Sets the internal frame previous to the current internal
   * frame on the desktop to be the current internal
   * frame, and selected.
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void previous()
    throws PropertyVetoException
  {
    if (currentFrame == null)
      return;

    final EInternalFrame previousFrame = currentFrame.getPrevious();
    previousFrame.setSelected(true);
    currentFrame = previousFrame;
  }

  /**
   * Restores all internal frames on the desktop to their
   * natural state (not maximized, minimized or iconified).
   *
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void restoreAll()
    throws PropertyVetoException
  {
    final JInternalFrame[] frames = getAllFrames();
    for (int index = frames.length; --index >= 0; )
    {
      frames[index].setIcon(false);
      frames[index].setMaximum(false);
    }
  }

  /**
   * Sets all internal frames on the desktop to their
   * iconified state depending on the boolean value
   * iconify.
   *
   * @param iconify is a boolean value indicating
   * if the internal frames on the desktop should be
   * set to icons.
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  private void setAllIconified(boolean iconify)
    throws PropertyVetoException
  {
    final JInternalFrame[] frames = getAllFrames();
    for (int index = frames.length; --index >= 0; )
      frames[index].setIcon(iconify);
  }

  /**
   * Sets all internal frames in the desktop to their
   * maximized state depending on the boolean value
   * maximize.
   *
   * @param maximize is a boolean value specifying
   * whether all internal frames should be maximized.
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void setAllMaximized(boolean maximize)
    throws PropertyVetoException
  {
    final JInternalFrame[] frames = getAllFrames();
    for (int index = frames.length; --index >= 0; )
      frames[index].setMaximum(maximize);
  }

  /**
   * Positions the internal frame on this desktop when the
   * frame is first opened.
   *
   * @param theFrame is the internal frame being positioned
   * on this desktop.
   */
  private void setFrameLocation(final JInternalFrame theFrame)
  {
    final int frameHeight = theFrame.getHeight();
    final int frameWidth = theFrame.getWidth();
    final int moveDistance = frameHeight
      - theFrame.getContentPane().getHeight();

    if (frameX + frameWidth > getWidth() ||
        frameY + frameHeight > getHeight())
    {
      frameX = frameY = 0;
    }

    theFrame.setLocation(frameX,frameY);
    frameX += moveDistance;
    frameY += moveDistance;
  }

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
  public void split(final int orientation)
    throws PropertyVetoException
  {
    split(Orientation.getOrientation(orientation));
  }

  /**
   * Organizes the internal frames in the desktop by
   * either splitting all visible, non-iconified
   * internal frames horizontally or veritcally.
   *
   * @param orientation is a integer specifying the
   * orientation of the split, either
   * Orientation.HORIZONTAL or Orientation.VERTICAL.
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void split(final Orientation orientation)
    throws PropertyVetoException
  {
    final JInternalFrame[] frames = getAllFrames();

    if (frames.length == 0)
      return;

    int frameCount = 0;

    for (int index = frames.length; --index >= 0; )
    {
      if (frames[index].isVisible() && !frames[index].isIcon())
        frameCount++;
    }

    int frameHeight = 0;
    int frameWidth  = 0;

    if (orientation == Orientation.HORIZONTAL)
    {
      frameHeight = getHeight() / frameCount;
      frameWidth = getWidth();
    }
    else
    {
      frameHeight = getHeight();
      frameWidth = getWidth() / frameCount;
    }

    int x = 0;
    int y = 0;

    for (int index = 0; index < frames.length; index++)
    {
      frames[index].setMaximum(false);
      frames[index].reshape(x,y,frameWidth,frameHeight);

      if (orientation == Orientation.HORIZONTAL)
        y += frameHeight;
      else
        x += frameWidth;
    }
  }

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
  public void tile(final int orientation)
    throws PropertyVetoException
  {
    tile(Orientation.getOrientation(orientation));
  }

  /**
   * Organizes the internal frames in this desktop in a
   * tiling fashion.
   *
   * @param orientation is a integer specifying the
   * orientation of the tile, either
   * Orientation.HORIZONTAL or Orientation.VERTICAL.
   * @throws java.beans.PropertyVetoException if a
   * PropertyChangeListener vetos the action.
   */
  public void tile(Orientation orientation)
    throws PropertyVetoException
  {
    final JInternalFrame[] frames = getAllFrames();

    if (frames.length == 0)
      return;

    int frameCount = 0;

    for (int index = frames.length; --index >= 0; )
    {
      if (frames[index].isVisible() && !frames[index].isIcon())
        frameCount++;
    }

    int rows = 0;
    int cols = 0;
    int extra = 0;

    if (orientation == Orientation.HORIZONTAL)
    {
      rows = (int) Math.sqrt(frameCount);
      cols = frameCount / rows;
      extra = frameCount % rows;
    }
    else
    {
      cols = (int) Math.sqrt(frameCount);
      rows = frameCount / cols;
      extra = frameCount % cols;
    }

    int frameWidth  = getWidth() / cols;
    int frameHeight = getHeight() / rows;
    int r = 0;
    int c = 0;

    for (int i = 0; i < frames.length; i++)
    {
      if (frames[i].isVisible() && !frames[i].isIcon())
      {
        frames[i].setMaximum(false);
        frames[i].reshape(c*frameWidth,r*frameHeight,frameWidth,frameHeight);
        r++;

        if (r == rows)
        {
          r = 0;
          c++;

          if (c == cols - extra)
            frameHeight = getHeight() / (++rows);
        }
      }
    }
  }

}

