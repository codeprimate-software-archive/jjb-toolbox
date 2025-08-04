/**
 * Searchable.java (c) 2001.4.22
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 * @see jjb.toolbox.util.Searcher
 */

package jjb.toolbox.lang;

public interface Searchable {

  /**
   * Adds an element to the Searchable object.
   *
   * @param element java.lang.Object element to add to
   * the Searchable object (collection).
   * @throws java.lang.Exception
   */
  public void addElement(Object element) throws Exception;

  /**
   * getElementAt returns the element in this Searchable
   * object at the specified index.
   *
   * @param index is an integer index into the Searchable
   * object's collection of objects.
   * @return a java.lang.Object element at the specified
   * index.
   */
  public Object getElementAt(int index);

  /**
   * newInstance constructs a new instance of the Searchable
   * object's type.
   *
   * @return a jjb.toolbox.lang.Searchable object instance.
   */
  public Searchable newInstance();

  /**
   * size returns the number of elements in the Searchable
   * object's collection of objects.
   *
   * @return a integer value of the number of objects in
   * this collection.
   */
  public int size();

}

