/**
 * SortDescendingComparator.java (c) 2003.2.9
 *
 * Copyright (c) 2003, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.9
 * @see java.util.Comparator
 */

package jjb.toolbox.util;

import java.util.Comparator;

public final class SortDescendingComparator implements Comparator {

  private static final SortDescendingComparator INSTANCE =
    new SortDescendingComparator();

  private SortDescendingComparator() {
  }

  public int compare(Object obj1,
                     Object obj2 ) {
    return ((Comparable) obj2).compareTo(obj1);
  }

  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  public static final SortDescendingComparator getInstance() {
    return INSTANCE;
  }

}

