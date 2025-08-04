/**
 * SearchFilter.java (c) 2002.4.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 * @see jjb.toolbox.util.Searcher
 */

package jjb.toolbox.util;

public interface SearchFilter {

  /**
   * Compares the object parameter of the Searchable collection to
   * the criteria defined by this method to detemine if the object
   * matches the criteria, has a net value less than the criteria,
   * or a net value more than the criteria.
   *
   * @param obj java.lang.Object of the Searchable collection used
   * to match against the criteria in the method.
   * @return an integer value of 0 for equals, less than 0 for less
   * than, and greater than 0 for greater than.
   */
  public int compareCriteria(Object obj);

  /**
   * Compares the object parameter in the Searchable collection for
   * equality with the criteria defined in this method.
   *
   * @param obj java.lang.Object contained in the Searchable collection
   * used to match against the criteria in the method.
   * @return a boolean value of true if the collection object matched
   * the criteria exactly, false otherwise.
   */
  public boolean matchesCriteria(Object obj);

}

