/**
 * InsertionSort.java (c) 2002.5.12
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 * @see jjb.toolbox.util.Sorter
 */

package jjb.toolbox.util;

import java.util.Comparator;

import jjb.toolbox.lang.Sortable;

public class InsertionSort extends AbstractSorter {

  /**
   * Constructs a new InsertionSort object used to sort
   * in ascending order a collection of objects using
   * the insertion sort algorithm.
   */
  public InsertionSort() {
    this(SortAscendingComparator.getInstance());
  }

  /**
   * Constructs a new instance of the InsertionSort class
   * and sets the sort order to ascending if true,
   * descending if false.
   *
   * @param sortOrder is a boolean value indicating
   * true to sort ascending or false to sort descending.
   */
  public InsertionSort(Comparator orderByComparator) {
    super(orderByComparator);
  }

  /**
   * This method implements the "insertion sort" algorithm
   * to sort a collection of objects.  This class is used
   * by the QuickSort class to sort the remaining data less
   * than the sortThreshold.
   *
   * @param beginIndex is the begin row index in the
   * collection where the insertion sort starts sorting
   * elements.
   * @param endIndex is the end row index in the collection
   * where the insertion sort stops sorting elements.
   * @throws Ljava.lang.Exception
   */
  public void insertionSort(Sortable collection,
                            int beginIndex,
                            int endIndex        )
      throws SortException {
    try {
      for (int i = beginIndex+1; i <= endIndex; i++) {
        Object x = collection.getElementAt(i);
        Object y = null;

        int j = i - 1;

        while (j >= beginIndex 
               && getOrderBy().compare(x,(y = collection.getElementAt(j))) < 0) {
          collection.setElementAt(y,j+1);
          j--;
        }

        collection.setElementAt(x,j+1);
      }
    }
    catch (Exception e) {
      throw new SortException("Failed to sort Sortable collection!",e);
    }
  }

  /**
   * sort method is called to sort a collection of sortable
   * objects using the insertion sort algorithm.
   *
   * @param collecion:Ljjb.toolbox.lang.Sortable object in
   * which to sort using the insertion sort algorithm.
   * @throws Ljava.lang.Exception
   */
  public void sort(Sortable collection) throws SortException {
    insertionSort(collection,0,collection.size()-1);
  }

}

