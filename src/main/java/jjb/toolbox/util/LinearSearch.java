/**
 * LinearSearch.java (c) 2002.5.12
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 * @see jjb.toolbox.lang.Searchable
 * @see jjb.toolbox.util.Searcher
 */

package jjb.toolbox.util;

import jjb.toolbox.lang.Searchable;

public class LinearSearch extends AbstractSearcher {

  /**
   * Constructs a new LinearSearch object with the specified
   * filter.  The filter is used to determine whether objecs
   * in the collection match the search criteria.  The
   * LinearSearch object traverses the collection linearly
   * testing each object for membership, and then adding it
   * to the overall set of results if the criteria holds.
   *
   * @param filter jjb.toolbox.util.SearchFilterIF used to
   * determine set membership of the objects in the collection
   * matching the search criteria.
   */
  public LinearSearch(SearchFilter filter) {
    super(filter);
  }

  /**
   * Searches the given collection for objects the match
   * the criteria specified by the SearchFilter object.
   *
   * @param collection jjb.toolbox.lang.Searchable collection
   * to search.
   */
  public Object search(Searchable collection) {
    Searchable matchedElements = collection.newInstance();

    for (int index = collection.size(); --index >= 0; ) {
      Object element = collection.getElementAt(index);

      if (getSearchFilter().matchesCriteria(element)) {
        try {
          matchedElements.addElement(element);
        }
        catch (Exception ignore) {
        }
      }
    }

    return matchedElements;
  }

}

