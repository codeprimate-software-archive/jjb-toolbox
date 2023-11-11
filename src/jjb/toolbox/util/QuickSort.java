/**
 * QuickSort.java (c) 2002.5.12
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

public class QuickSort extends AbstractSorter {

  private static final int DEFAULT_THRESHOLD = 10;

  private static final InsertionSort INSERTION_SORT = new InsertionSort();

  private int threshold;

  /**
   * Creates a QuickSort object to sort a collection of
   * objects using the Quick Sort algorithm.  Defaults
   * to ascending order with a threshold of 10 elements.
   */
  public QuickSort() {
    this(SortAscendingComparator.getInstance(),DEFAULT_THRESHOLD);
  }

  /**
   * Constructs a new instance of the QuickSort class
   * and sets the sort order either ascending or descending
   * as specified by the sortOrder parameter for the
   * elements in the Sortable object.
   *
   * @param sortOrder is a boolean value indicating true
   * to sort the collection of objects in ascending order,
   * or false for descending order.
   */
  public QuickSort(Comparator orderByComparator) {
    this(orderByComparator,DEFAULT_THRESHOLD);
  }

  /**
   * Constructs a new instance of the QuickSort class and
   * sets the threshold by which the QuickSort class relies
   * on another sorting algorithm to finish sorting the
   * elements of the Sortable object.  The reason quickSort
   * uses INSERTION_SORT is that quickSort actually degrades
   * in performance due to the recursive calls on a small
   * number of elements.
   *
   * @param threshold is integer value specifying the
   * number of remaining unsorted elements that must exist
   * before QuickSort will rely on the other sorting
   * algorithm.
   */
  public QuickSort(int threshold) {
    this(SortAscendingComparator.getInstance(),threshold);
  }

  /**
   * Constructs a new instance of the QuickSort class and
   * sets the sort order and threshold for this object.
   *
   * @param sortOrder is a boolean value indicating true
   * to sort the collection of objects in ascending order,
   * or false for descending order
   * @param threshold is integer value specifying the
   * number of remaining unsorted elements that must exist
   * before QuickSort will rely on the other sorting
   * algorithm.
   */
  public QuickSort(Comparator orderByComparator,
                   int threshold                  ) {
    super(orderByComparator);
    this.threshold = threshold;
    INSERTION_SORT.setOrderBy(orderByComparator);
  }

  /**
   * Returns the value of the threshold.
   */
  public int getThreshold() {
    return threshold;
  }

  /**
   * quickSort implements Hoare's "QuickSort" algorithm for
   * sorting (in ascending or descending order) Sortable
   * objects.  This method implements the QuickSort recursively.
   * The method should be called with beginIndex == 0 and
   * endIndex == Sortable.size()-1.
   * NOTE: To speed up the sort even more, the recursive calls
   * could be incorporated into a thread (not that it would not
   * matter much on a single processor, but on multi-processor
   * systems, this routine would smoke).
   *
   * @param collection:Ljjb.toolbox.lang.Sortable object for
   * which the class has been designated to sort.
   * @param beginIndex is the begin index into the rows of this
   * collection to start sorting elements.
   * @param endIndex is the end index into the rows of this
   * collection to stop sorting elements.
   * @throws Ljava.lang.Exception
   */
  public void quickSort(Sortable collection,
                        int beginIndex,
                        int endIndex        )
      throws SortException {
    if (beginIndex >= endIndex)
      return;
    if ((endIndex - beginIndex) < threshold) {
      INSERTION_SORT.insertionSort(collection,beginIndex,endIndex);
      return;
    }

    Object pivot = collection.getElementAt(beginIndex);

    int bindex = beginIndex + 1;
    int eindex = endIndex;
    int compareValue;

    while (bindex < eindex) {
      // Find a value greater than the pivot value.
      HIGH: while (bindex < eindex) {
        if (getOrderBy().compare(collection.getElementAt(bindex),pivot) > 0)
          break HIGH;
        ++bindex;
      }

      // Find a value less than the pivot value.
      LOW: while (eindex >= bindex) {
        if (getOrderBy().compare(collection.getElementAt(eindex),pivot) < 0)
          break LOW;
        --eindex;
      }

      if (bindex < eindex)
        swap(collection,bindex,eindex);
    }

    // Swap Pivot
    swap(collection,beginIndex,eindex);
    // Recurse
    quickSort(collection,beginIndex,eindex-1);
    quickSort(collection,eindex+1,endIndex);
  }

  /**
   * Sets the minimum number of elements to apply the other sorting algorithm.
   *
   * @param threshold is an integer value specifying
   * the number of elements that must remain to apply with
   * another sorting algorithm.
   */
  public void setThreshold(int threshold) {
    threshold = Math.abs(threshold);
  }

  /**
   * sort method is called to sort a collection of sortable
   * objects using the Quick Sort algorithm.
   *
   * @param collecion:Ljjb.toolbox.lang.Sortable object in
   * which to sort using the insertion sort algorithm.
   * @throws Ljava.lang.Exception
   */
  public void sort(Sortable collection) throws SortException {
    quickSort(collection,0,collection.size()-1);
  }

  /**
   * swap is a commonly used routine by the Quick Sort algorithm
   * for interchanging the order of two elements.
   *
   * @param index1 is the index of the first ordered element to
   * be switched for the last element.
   * @param index2 is the index of the last ordered element to
   * be switched for the first element.
   * @throws Ljava.lang.Exception
   */
  private void swap(Sortable collection,
                    int index1,
                    int index2          )
      throws SortException {
    try {
      Object tempObj = collection.getElementAt(index1);
      collection.setElementAt(collection.getElementAt(index2),index1);
      collection.setElementAt(tempObj,index2);
    }
    catch (Exception e) {
      throw new SortException("Failed to sort Sortable collection!",e);
    }
  }

}

