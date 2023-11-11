/*
 * Editable.java (c) 2002.5.12
 *
 * Objects whose classes implement the Editable interface
 * are modifyable.  This interface helps applications
 * distinguish between read-only and editable data objects
 * or value objects.
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.23
 */

package jjb.toolbox.lang;

import jjb.toolbox.swing.ItemEditor;

public interface Editable {

  /**
   * Saves all changes to the current data/value object.
   */
  public void commitChanges();

  /**
   * Returns the object responsible for managing the editing
   * behavior to modify and unmodify an object.
   *
   * @returns a jjb.toolbox.swing.ItemEditorIF object used
   * create the user interface for editing the object.
   */
  public ItemEditor getEditor();

  /**
   * Called if the user is indecisive and wishes to reapply
   * the undone changes and apply the modifications.
   */
  public void redoChanges();

  /**
   * Called if the user wishes undo any modifications he or
   * she made to the object.
   */
  public void undoChanges();

}

