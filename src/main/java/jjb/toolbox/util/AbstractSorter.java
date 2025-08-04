/**
 * AbstractSorter.java (c) 2003.2.9
 *
 * Copyright (c) 2003, Code Primate
 * All Right Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 * @see jjb.toolbox.util.Sorter
 * @since Java 2
 */

package jjb.toolbox.util;

import java.util.Comparator;

public abstract class AbstractSorter implements Sorter {

  private Comparator orderByComparator;

  public AbstractSorter(Comparator orderByComparator) {
    setOrderBy(orderByComparator);
  }

  /**
   * Returns the Comparator that determines the order of the
   * elements in the Sortable collection.
   *
   * @return a java.util.Comparator defining the sort order.
   */
  public Comparator getOrderBy() {
    return orderByComparator;
  }

  /**
   * Sets the order of the sort as defined by the Comparator.
   *
   * @param sortOrder is a java.util.Comparator that defines
   * the order in which the Comparable elements of this
   * Sortable collection are ordered.
   * @throws java.lang.NullPointerException if the comparator
   * used in the order by is null.
   */
  public final void setOrderBy(Comparator orderByComparator) {
    if (orderByComparator == null) {
      throw new NullPointerException("The comparator for the sort order "
        +" cannot be null!");
    }
    this.orderByComparator = orderByComparator;
  }

}

