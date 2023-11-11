/**
 * AbstractCache.java (c) 2002.4.17
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.22
 */

package jjb.toolbox.util;

import java.util.Iterator;

public abstract class AbstractCache implements Cache {

  protected boolean purging = false;

  protected final class CacheIterator implements Iterator {

    private final Iterator iter;

    /**
     * Creates an instance of the CacheIterator class.
     */
    public CacheIterator(Iterator iter) {
      this.iter = iter;
    }

    /**
     * Returns <tt>true</tt> if the iteration has more elements. (In other
     * words, returns <tt>true</tt> if <tt>next</tt> would return an element
     * rather than throwing an exception.)
     *
     * @return <tt>true</tt> if the iterator has more elements.
     */
    public boolean hasNext() {
      return iter.hasNext();
    }

    /**
     * Returns the next element in the interation.
     *
     * @return the next element in the iteration.
     * @exception NoSuchElementException iteration has no more elements.
     */
    public Object next() {
      return iter.next();
    }

    /**
     *
     * Removes from the underlying collection the last element returned by the
     * iterator (optional operation).  This method can be called only once per
     * call to <tt>next</tt>.  The behavior of an iterator is unspecified if
     * the underlying collection is modified while the iteration is in
     * progress in any way other than by calling this method.
     *
     * @exception UnsupportedOperationException if the <tt>remove</tt>
     *		  operation is not supported by this Iterator.
    
     * @exception IllegalStateException if the <tt>next</tt> method has not
     *		  yet been called, or the <tt>remove</tt> method has already
     *		  been called after the last call to the <tt>next</tt>
     *		  method.
     */
    public void remove() {
      throw new java.lang.UnsupportedOperationException("The remove operation is not allowed external to the cache.");
    }

  } // End of CacheIterator Class

  protected final class PurgeCacheTx implements Runnable {

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    public void run() {
      while (purging) {
        purgeCache();

        try {
          Thread.sleep(5000); // 5 seconds
        }
        catch (InterruptedException ignore) {
        }
      }
    }

  } // End of PurgeCache Class

  /**
   * containsCacheable searches the cache for the cacheObject parameter
   * and returns true if the Cacheable object exists, false otherwise.
   *
   * @param cacheObject Cacheable object used in determining existence
   * in this cache.
   * @return a boolean value indicating whether the cacheObject exists in
   * this cache or not.
   */
  public boolean containsCacheable(Cacheable cacheObject) {
    for (final Iterator iter = getCacheables(); iter.hasNext(); ) {
      if (iter.next().equals(cacheObject))
        return true;
    }
    return false;
  }

  /**
   * containsKey searches the cache for the key parameter and returns true
   * if the key maps to a Cacheable object in this cache.
   *
   * @param key java.lang.String object in question of whether it maps to a
   * Cacheable object.
   * @return a boolean value indicating whether the key maps to a Cacheable
   * object or not.
   */
  public boolean containsKey(String key) {
    for (final Iterator iter = getKeys(); iter.hasNext(); ) {
      if (iter.next().equals(key))
        return true;
    }
    return false;
  }

  /**
   * invalidateCache invalidates all Cacheable objects in the cache causing
   * them to be garbage collected.
   */
  public void invalidateCache() {
    AbstractCacheable cacheObject = null;
    for (Iterator cache = getCacheables(); cache.hasNext(); ) {
      cacheObject = (AbstractCacheable) cache.next();
      cacheObject.setValid(false);
    }
  }

  /**
   * purgeCache cleans the cache by removing expired and invalid cache
   * objects from the cache.
   */
  protected abstract void purgeCache();

  /**
   * writeObject stores the cacheObject parameter in the cache referenced
   * by the key.  This method creates a CacheableImpl object to hold
   * the Object parameter and is set not to expire.
   *
   * @param key java.lang.String object reference to the cacheObject in
   * the cache.
   * @param cacheObject java.lang.Object parameter to store in the cache
   * as a CacheableImpl object.
   */
  public void writeObject(String key,
                          Object cacheObject) {
    writeCacheable(key,new CacheableImpl(key,cacheObject));
  }

}

