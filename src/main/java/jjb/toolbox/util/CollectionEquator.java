/**
 * CollectionEquator.java (c) 2002.10.2
 *
 * This class is an implemenation of the Equator interface
 * used to compare the contents of one java.util.Collection
 * to another using the equality relation.  The individual
 * elements of the Collection are compared for equality
 * using an implementation of the Equator interface.  If a
 * Equator implementation for comparing elements in a
 * Collection is not specified, an instance of the
 * DefaultEquator class is used.
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see Equator
 * @see DefaultEquator 
 */

package jjb.toolbox.util;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class CollectionEquator implements Equator {

  private final Equator elementEquator;

  /**
   * Instantiates an instance of the CollectionEquator class
   * to compare two Collections for equality.
   */
  public CollectionEquator() {
    this(null);
  }

  /**
   * Instantiates an instance of the CollectionEquator class
   * with the specified Equator object used to compare
   * elements of the Collection objects for equality.
   */
  public CollectionEquator(Equator elementEquator) {
    this.elementEquator = (elementEquator == null ?
      DefaultEquator.getInstance() : elementEquator);
  }

  /**
   * Compares the contents of the two Collection objects
   * for equality.  The two Collection objects are equal
   * if and only if the Collection objects contain the
   * same number of elements and elements in the first
   * Collection object equals an element in the other
   * Collection object as defined by elementEquator.
   *
   * @param collection1 is the Collection operand being
   * compared for equality to collection2.
   * @param collection2 is the Collection operand being
   * compared for equality to collection1.
   * @return a boolean value of true if collection1
   * equals collection2 defined by the element Equator
   * and whether the Collection objects are the same size.
   * @throws java.lang.ClassCastException if either 
   * parameter is not of type java.util.Collection.
   * @throws java.util.ConcurrentModificationException if
   * the Collection objects are modified during the
   * operation of the areEqual method.
   * @throws java.lang.NullPointerException if either
   * Collection object parameter is null.
   */
  public boolean areEqual(Object collection1,
                          Object collection2 ) {
    if (collection1 == null || collection2 == null) {
      throw new NullPointerException("The Collection object parameters"
        + " cannot be null.");
    }

    // Throws java.lang.ClassCastException if the runtime type
    // of the object parameters are not Collection objects.
    final Collection c1 = (Collection) collection1;
    final Collection c2 = (Collection) collection2;
    final int size2 = c2.size();

    // Simple check, obviously the two Collection objects would
    // not be equal if they did not have the same number of
    // elements.
    if (c1.size() != size2)
      return false;

    for (Iterator it = c1.iterator(); it.hasNext(); ) {
      // Make sure the second Collection object has not been
      // concurrently modified by another thread.
      if (c2.size() != size2) {
        throw new ConcurrentModificationException("The contents of the"
          + "Collection parameter have changed.");
      }

      if (!contains(c2,it.next()))
        return false;
    }

    return true;
  }

  /**
   * contains determines whether the specified collection
   * contains the specified element according to
   * elementEquator.
   *
   * @param collection object used in dertermining if the
   * element is a member of the Collection.
   * @param element is the object being tested for membership
   * in the collection, defined by elementEquator.
   * @return a boolean value of true if the element is a
   * member of the Collection, false otherwise.
   * @throws java.util.ConcurrentModificationException if the
   * Collection object is modified during operation of the
   * contains method.
   */
  private boolean contains(Collection collection,
                           Object element        ) {
    for (Iterator it = collection.iterator(); it.hasNext(); ) {
      if (elementEquator.areEqual(element,it.next()))
        return true;
    }
    
    return false;
  }

}

