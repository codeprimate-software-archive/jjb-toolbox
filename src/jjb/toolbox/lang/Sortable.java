/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: Sortable.java
 * @version v1.0
 * Date: 22 April 2001
 * Modification Date: 12 May 2002
 * @since Java 1.0
 */

package jjb.toolbox.lang;

public interface Sortable
{

  /**
   * getElementAt returns the element in this Sortable
   * object at the specified index.
   *
   * @param index is an integer index into the Sortable
   * object's collection of objects.
   * @return a Ljava.lang.Object element at the
   * specified index.
   */
  public Object getElementAt(int index);

  /**
   * setElement sets the specified object element to be
   * the element at the specified index in the Sortable
   * object's collection of objects.
   *
   * @param object:Ljava.lang.Object element to place in
   * the collection objects.
   * @param index is an integer index of where to place
   * the object element in the collection of objects.
   * @throws Ljava.lang.Exception
   */
  public void setElementAt(Object object,
                           int    index  ) throws Exception;

  /**
   * size returns the number of elements in the SotableIF
   * object's collection of objects.
   *
   * @return a integer value of the number of objects in
   * this collection.
   */
  public int size();

}

