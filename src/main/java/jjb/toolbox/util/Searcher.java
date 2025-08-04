/**
 * Searcher.java (c) 2002.5.12
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 & @see jjb.toolbox.lang.Searchable
 */

package jjb.toolbox.util;

import jjb.toolbox.lang.Searchable;

public interface Searcher {

  /**
   * Searches for a specified element defined by
   * a filter in an implementing class within the
   * Searchable object's collection of objects.
   *
   * @param collection jjb.toolbox.lang.Searchable object
   * collection to perform the element search.
   * @return a java.lang.Object element if the element is
   * found, null otherwise.
   */
  public Object search(Searchable collection);

}

