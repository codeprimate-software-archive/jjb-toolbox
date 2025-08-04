/**
 * Sorter.java (c) 2002.5.12
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 * 
 * @author John J. Blum
 * @version 2003.2.21
 * @since Java 1.0
 */

package jjb.toolbox.util;

import java.util.Comparator;

import jjb.toolbox.lang.Sortable;

public interface Sorter {

  /**
   * Returns the Comparator that determines the order of the
   * elements in the Sortable collection.
   *
   * @return a java.util.Comparator defining the sort order.
   */
  public Comparator getOrderBy();

  /**
   * Sets the order of the sort as defined by the Comparator.
   *
   * @param sortOrder is a java.util.Comparator that defines
   * the order in which the Comparable elements of this
   * Sortable collection are ordered.
   */
  public void setOrderBy(Comparator sortOrderComparator);

  /**
   * Sorts the specified collection of comparable elements.
   *
   * @param collection is a jjb.toolbox.lang.Sortable collection
   * of comparable object to sort.
   * @throws jjb.toolbox.util.SortException
   */
  public void sort(Sortable collection) throws SortException;

}

