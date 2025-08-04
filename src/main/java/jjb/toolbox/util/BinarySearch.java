/**
 * BinarySearch.java (c) 2002.5.12
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

public class BinarySearch extends AbstractSearcher {

  /**
   * Constructs a new BinarySearch object with the specified
   * filter. The filter is used to locate the object in the
   * collection matching the criteria as defined by the filter.
   * Note, that the BinarySearch class assumes that the
   * elements of the set are ordered.  If not, the results of
   * the search are undetermined.
   *
   * @param filter: jjb.toolbox.util.SearchFilterIF used to
   * determine set membership of the objects in the collection
   * matching the search criteria.
   */
  public BinarySearch(SearchFilter filter) {
    super(filter);
  }

  /**
   * Performs binary search against a collection of objects
   * that are Searchable.  It is assumed that the collection
   * of objects are presorted when this method is called.
   *
   * @param collection jjb.toolbox.land.Searchable object in
   * which to search, and that implements the Searchable
   * interface.
   * @param startIndex is an integer value specifying the
   * beginning index into the data structure to begin the
   * search.
   * @param endIndex is an integer value specifying the end
   * index into the date structure to stop searching for the
   * desired object.
   * @return a java.lang.Object representing the found
   * element in the collection or null if the element is not
   * found.
   * @see search
   */
  public Object binarySearch(Searchable collection,
                             int startIndex,
                             int endIndex          ) {
    if (startIndex >= endIndex)
      return null;

    int compareIndex = (int) Math.round(
      ((double) (endIndex + startIndex)) / 2.0);
    Object element = collection.getElementAt(compareIndex);
    int compareResult = getSearchFilter().compareCriteria(element);

    if (compareResult == 0)
      return element;
    else if (compareResult < 0)
      return binarySearch(collection,startIndex,compareIndex-1);
    else
      return binarySearch(collection,compareIndex+1,endIndex);
  }

  /**
   * Default method invoked to perform a binary searh operation
   * It uses the default begin and end indexes into the data
   * structure.
   *
   * @param collection jjb.toolbox.land.Searchable object in
   * which to search, and that implements the Searchable
   * interface.
   * @return a java.lang.Object representing the found element
   * in the collection or null if the element is not found.
   * @see binarySearch
   */
  public Object search(Searchable collection) {
    return binarySearch(collection,0,collection.size());
  }

}

