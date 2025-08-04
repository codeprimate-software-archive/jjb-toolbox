/**
 * AbstractSearcher.java (c) 2003.3.5
 *
 * Copyright (c) 2003, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 * @see jjb.toolbox.util.Searcher
 */

package jjb.toolbox.util;

public abstract class AbstractSearcher implements Searcher {

  private SearchFilter filter;
  
  public AbstractSearcher(final SearchFilter filter) {
    setSearchFilter(filter);
  }

  /**
   * Returns the specified search filter criteria used by a
   * Searcher implementation to match elements in a Searchable
   * Collection.
   *
   * @return an instance of the SearchFilter interface used to
   * filter elements of a Searchable Collection.
   */
  public SearchFilter getSearchFilter() {
    return filter;
  }

  /**
   * Sets the search filtering criteria when looking for elements
   * in the Searchable Collection.
   *
   * @param filter is a SearchFilter impelementation specifying
   * criteria in order to qualify elements of a Searchable
   * Collections during a search operation.
   * @throws java.lang.NullPointerException if the filter parameter
   * is null.
   */
  public final void setSearchFilter(final SearchFilter filter) {
    if (filter == null)
      throw new NullPointerException("The search filter cannot be null!");
    this.filter = filter;
  }

}

