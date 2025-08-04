/**
 * SortAscendingComparator.java (c) 2003.2.9
 *
 * Copyright (c) 2003, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see java.util.Comparator
 */
 
package jjb.toolbox.util;

import java.util.Comparator;

public final class SortAscendingComparator implements Comparator {

  private static final SortAscendingComparator INSTANCE =
    new SortAscendingComparator();

  private SortAscendingComparator() {
  }

  public int compare(Object obj1,
                     Object obj2 ) {
    return ((Comparable) obj1).compareTo(obj2);
  }

  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  public static final SortAscendingComparator getInstance() {
    return INSTANCE;
  }

}

