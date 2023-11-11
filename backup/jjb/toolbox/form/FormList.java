/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FormList.java
 * @version v1.0
 * Date: 30 May 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.form;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.zip.*;

public class FormList extends java.awt.List implements FormComponentIF
{

  private boolean   stateChanged  = false;

  private Vector    listItems     = new Vector();

  /**
   * Creates a new instance of the FormList component.
   */
  public FormList()
  {
    this(4,false);
  }

  /**
   * Creates a new scrolling list initialized with the
   * specified number of visible lines.
   *
   * @param rows is an integer value specifying the number of
   * visible rows (lines) in the list.
   */
  public FormList(int rows)
  {
    this(rows,false);
  }

  /**
   * Creates a new scrolling list initialized to display the
   * specified number of rows.
   *
   * @param rows is an integer value specifying the number of
   * visible rows (lines) in the list.
   * @param multipleMode is boolean value indicating true is more
   * than one item in the list can be selected at a time, false
   * otherwise.
   */
  public FormList(int     rows,
                  boolean multipleMode)
  {
    super(rows,multipleMode);

    addItemListener(new ItemListener()
    {
      public void itemStateChanged(ItemEvent ie)
      {
        int state = ie.getStateChange();

        if (state == ItemEvent.SELECTED || state == ItemEvent.DESELECTED)
        {
          stateChanged = true;
        }
      }
    });
  }

  /**
   * ackStateChanged is called by the form model to acknowledge that the
   * state of this component has changed.
   */
  public void ackStateChanged()
  {
    stateChanged = false;
  }

  /**
   * add adds the item to the end of the list.
   *
   * @param item:Ljava.lang.String object encapsulating the information
   * to add to the list.
   */
  public void add(String item)
  {
    listItems.addElement(item);
    super.add(item);
  }

  /**
   * add inserts the item into the list at the  specified index.
   *
   * @param item:Ljava.lang.String object encapsulating the information
   * to add to the list.
   * @param index is an integer index into the list where the item
   * should be inserted.
   */
  public void add(String  item,
                  int     index)
  {
    listItems.insertElementAt(item,index);
    super.add(item,index);
  }

  /**
   * clearData removes information from the form component.
   */
  public void clearData()
  {
    int[] indexes = getSelectedIndexes();

    for (int index = indexes.length; --index >= 0; )
      deselect(indexes[index]);

    stateChanged = false;
  }

  /**
   * focusGained is called when this component receives focus in the user
   * interface.
   *
   * @param fe:Ljava.awt.event.FocusEvent object encapsulating information
   * about the focus gained event.
   */
  public void focusGained(FocusEvent fe) {}

  /**
   * focusLost is called when this component loses focus in the user interface.
   * This method is useful when performing user input validation.
   *
   * @param fe:Ljava.awt.event.FocusEvent object encapsulating information
   * about the focus lost event.
   */
  public void focusLost(FocusEvent fe) {}

  /**
   * getData returns the information contained in this component that
   * was entered by the user.
   *
   * @return a Ljava.lang.Object representation of the information contained
   * in the form component.
   */
  public Object getData()
  {
    return getSelectedItems();
  }

  /**
   * hasStateChanged determines whether the user has modified the information
   * contained in this form component.
   *
   * @return a boolean value indicating that whether state of this form
   * component has changed.
   */
  public boolean hasStateChanged()
  {
    return stateChanged;
  }

  /**
   * init is called to initialize this component.
   *
   * @param config:Ljava.lang.Object object used to configure this
   * form component.
   */
  public void init(Object config) throws DataFormatException
  {
    if (config == null)
      removeAll();
    else if (config instanceof String[])
    {
      removeAll();

      String[] items = (String[]) config;

      for (int index = 0; index < items.length; index++)
      {
        String item = items[index];

        addItem(item);
        listItems.addElement(item);
      }
    }
    else
      throw new DataFormatException(config.getClass().getName()+" is an invalid data format to initialize the FormList component.");
  }

  /**
   * remove deletes the item at the specified position in the list.
   *
   * @param position is an integer index specifying the location
   * in the list of the item to remove.
   */
  public void remove(int position)
  {
    listItems.remove(position);
    super.remove(position);
  }

  /**
   * remove deletes the specified item from the list.
   *
   * @param item:Ljava.lang.String object representing the item in
   * the list to remove.
   */
  public void remove(String item)
  {
    listItems.remove(item);
    super.remove(item);
  }

  /**
   * removeAll removes all items from the list.
   */
  public void removeAll()
  {
    listItems.clear();
    super.removeAll();
  }

  /**
   * setData sets information in this component with the specified data object
   * using the form model.
   *
   * @param data:Ljava.lang.Object the information to set the data of this
   * form component.
   */
  public void setData(Object data) throws DataFormatException
  {
    if (data instanceof String)
      select(listItems.indexOf(data.toString()));
    else
      throw new DataFormatException("Data must be in the format of String to select an item in the list.");

    stateChanged = false;
  }

  /**
   * setStateChanged sets the state of this form component to the specified
   * boolean parameter.
   *
   * @param tf is a boolean value indicating the state for which this form
   * component should be set.
   */
  public void setStateChanged(boolean tf)
  {
    stateChanged = tf;
  }

}

