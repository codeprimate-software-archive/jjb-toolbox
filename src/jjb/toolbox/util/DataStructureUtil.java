/**
 * DataStructureUtil.java (c) 2002.4.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */

package jjb.toolbox.util;

import java.util.Comparator;
import java.util.Vector;

public class DataStructureUtil {

  protected static final Comparator ORDER_BY =
    SortAscendingComparator.getInstance();

  /**
   * Default constructor for this class.  Having the constructor be protected
   * is a special form of the Singleton Creational Pattern.  This prevents other
   * class from instantiating an instance. It allows the class loader to load
   * the class when a reference to one this class's static methods is made.
   * However, if a class wishes to extend this class, then an instance of this
   * class is created for each instance of the subclass.
   */
  protected DataStructureUtil() {
  }

  /**
   * insertionSort sorts the elements of the specified Vector object
   * using the insertion sort algorithm.
   *
   * @param items java.util.Vector object of the collection of elements
   * to sort.
   */
  public static void insertionSort(Vector items) {
    int size = items.size();

    for (int i = 1; i < size; i++) {
      Object x = items.elementAt(i);
      int j = i - 1;

      while (j >= 0 && ORDER_BY.compare(x,items.elementAt(j)) < 0) {
        items.setElementAt(items.elementAt(j),j+1);
        j--;
      }

      items.setElementAt(x,j+1);
    }
  }

  /**
   * setDifference returns the difference of the two Vectors by taking
   * elements form set1 that are not in set2.
   *
   * @param set1 java.util.Vector object to pull elements from.
   * @Parma set2 java.util.Vector object to compare equal elements
   * in set1.
   */
  public static Vector setDifference(Vector set1,
                                     Vector set2 ) {
    Vector set = new Vector();

    int size = set1.size();

    for (int index = 0; index < size; index++) {
      Object element = set1.elementAt(index);
      if (!set2.contains(element))
        set.addElement(element);
    }

    return set;
  }

  /**
   * setIntersection returns the intersection of the two Vectors by
   * taking elements from set1 that are also in set2.
   *
   * @param set1 java.util.Vector object to pull elements from.
   * @Parma set2 java.util.Vector object to compare equal elements
   * in set1.
   */
  public static Vector setIntersection(Vector set1,
                                       Vector set2 ) {
    Vector set = new Vector();

    int size = set1.size();

    for (int index = 0; index < size; index++) {
      Object element = set1.elementAt(index);
      if (set2.contains(element))
        set.addElement(element);
    }

    return set;
  }

  /**
   * setUnion combines both Vectors into one Vector removing duplicate
   * elements.
   *
   * @param set1 java.util.Vector object to pull elements from.
   * @Parma set2 java.util.Vector object to compare equal elements
   * in set1.
   */
  public static Vector setUnion(Vector set1,
                                Vector set2 ) {
    Vector set = new Vector();
    int size = set1.size();

    for (int index = 0; index < size; index++)
      set.addElement(set1.elementAt(index));

    size = set2.size();

    for (int index = 0; index < size; index++) {
      Object element = set2.elementAt(index);
      if (!set.contains(element))
        set.addElement(element);
    }

    return set;
  }

}

