/**
 * LFUCache.java (c) 2002.4.17
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */

package jjb.toolbox.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class LFUCache extends AbstractCache {

  private int maxSize;

  private final Map cache;

  /**
   * Creates an instance of the LFUCache object to cache objects.  The cache
   * is set to the default maximum size.
   */
  public LFUCache() {
    this(DEFAULT_CACHE_SIZE);
  }

  /**
   * Creates an instance of the LFUCache object to cache object up to the 
   * given max size before it enforces the Least Recently Used replacement
   * strategy.  If the max size is set to 0, then the default value of
   * Cache.DEFAULT_CACHE_SIZE (1024) is used.
   *
   * @param maxSize is an integer specifying the number of cached objects
   * that can exists in the cache at any given time.
   */
  public LFUCache(int maxSize) {
    if (maxSize < 0)
      throw new IllegalArgumentException("The maximum size of the cache must be greater than or equal to 0.");

    this.maxSize = (maxSize == 0 ? DEFAULT_CACHE_SIZE : maxSize);
    cache = new HashMap((int) (this.maxSize * 1.5),0.75f);
  }

  /**
   * Copy constructor.  Creates an instance of the LFUCache class from an
   * existing Cache object, copy the contents of the old cache to this
   * cache.  This constructor is for the purpose of switching caching
   * strategies at runtime.
   *
   * @param cache Cache object containing the cached objects.
   */
  public LFUCache(Cache oldCache) {
    maxSize = oldCache.getMaxSize();

    if (maxSize < 0)
      throw new IllegalArgumentException("The maximum size of the cache must be greater than or equal to 0.");

    maxSize = (maxSize == 0 ? DEFAULT_CACHE_SIZE : maxSize);
    cache = new HashMap((int) (maxSize * 1.5),0.75f);

    String key = null;

    for (Iterator keys = oldCache.getKeys(); keys.hasNext(); ) {
      key = keys.next().toString();
      cache.put(key,oldCache.readCacheable(key));
    }
  }

  private final class LFUComparator implements Comparator {

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     *
     * The implementor must ensure that <tt>sgn(compare(x, y)) ==
     * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>compare(x, y)</tt> must throw an exception if and only
     * if <tt>compare(y, x)</tt> throws an exception.)<p>
     *
     * The implementor must also ensure that the relation is transitive:
     * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
     * <tt>compare(x, z)&gt;0</tt>.<p>
     *
     * Finally, the implementer must ensure that <tt>compare(x, y)==0</tt>
     * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
     * <tt>z</tt>.<p>
     *
     * It is generally the case, but <i>not</i> strictly required that
     * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
     * any comparator that violates this condition should clearly indicate
     * this fact.  The recommended language is "Note: this comparator
     * imposes orderings that are inconsistent with equals."
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * 	       first argument is less than, equal to, or greater than the
     *	       second.
     * @throws ClassCastException if the arguments' types prevent them from
     * 	       being compared by this Comparator.
     */
    public int compare(Object o1,
                       Object o2 ) {
      final Cacheable co1 = (Cacheable) o1;
      final Cacheable co2 = (Cacheable) o2;

      final int hitCntDiff = co1.getHitCount() - co2.getHitCount();

      if (hitCntDiff != 0)
        return hitCntDiff;

      final Date lastHit1 = co1.getLastHit();
      final Date lastHit2 = co2.getLastHit();

      if (lastHit1.before(lastHit2))
        return -1;
      else if (lastHit2.before(lastHit1))
        return 1;

      final Date expire1 = co1.getExpiration();
      final Date expire2 = co2.getExpiration();

      if (expire1 == null || expire2 == null)
        return 0;
      else
        return(expire1.before(expire2) ? -1 : 1);
    }

    /**
     *
     * Indicates whether some other object is &quot;equal to&quot; this
     * Comparator.  This method must obey the general contract of
     * <tt>Object.equals(Object)</tt>.  Additionally, this method can return
     * <tt>true</tt> <i>only</i> if the specified Object is also a comparator
     * and it imposes the same ordering as this comparator.  Thus,
     * <code>comp1.equals(comp2)</code> implies that <tt>sgn(comp1.compare(o1,
     * o2))==sgn(comp2.compare(o1, o2))</tt> for every object reference
     * <tt>o1</tt> and <tt>o2</tt>.<p>
     *
     * Note that it is <i>always</i> safe <i>not</i> to override
     * <tt>Object.equals(Object)</tt>.  However, overriding this method may,
     * in some cases, improve performance by allowing programs to determine
     * that two distinct Comparators impose the same order.
     *
     * @param   obj   the reference object with which to compare.
     * @return  <code>true</code> only if the specified object is also
     *		a comparator and it imposes the same ordering as this
     *		comparator.
     * @see     java.lang.Object#equals(java.lang.Object)
     * @see java.lang.Object#hashCode()
     */
    public boolean equals(Object obj) {
      return super.equals(obj);
    }

  } // End of LFUComparator Class

  /**
   * purgeCache using the Least Frequently Used algorithm to clean the cache
   * of Cacheable objects that have expired, are no longer valid, and when
   * the cache is near peak size.
   */
  protected void purgeCache() {
    Cacheable purgeObject = null;

    for (Iterator cacheableObjects = getCacheables(); cacheableObjects.hasNext(); ) {
      Cacheable cacheObject = (Cacheable) cacheableObjects.next();

      if (cacheObject.hasExpired() || !cacheObject.isValid()) {
        if (!cacheObject.refresh())
          cache.remove(cacheObject.getID());
      }
    }

    if (cache.size() > maxSize) {
      final List cacheList = new ArrayList(cache.values());

      // Least Frequently Used Cacheable objects will be at the 
      // front of the List using the LFUComparator.
      Collections.sort(cacheList,new LFUComparator());

      for (int index = 0; index < (cache.size() - maxSize); index++)
        cache.remove(((Cacheable) cacheList.get(index)).getID());
    }

    if (cache.size() == 0)
      purging = false;
  }

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
    if (cacheObject == null)
      return false;
    return cache.containsValue(cacheObject);
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
    if (key == null)
      return false;
    return cache.containsKey(key);
  }

  /**
   * getCacheables returns an Iterator object over the Cacheable objects
   * in this cache.
   *
   * @return a java.util.Iterator object used in iterating over all Cacheable
   * objects in the cache.
   */
  public Iterator getCacheables() {
    return new CacheIterator(cache.values().iterator());
  }

  /**
   * getKeys returns an Iterator object over the keys that map to Cacheable
   * objects in this cache.
   *
   * @return a java.util.Iterator object iterating over all keys in the cache
   * mapping to Cacheable objects.
   */
  public Iterator getKeys() {
    return new CacheIterator(cache.keySet().iterator());
  }

  /**
   * getMaxSize returns the maximum number of Cacheable objects that this
   * cache will hold.
   *
   * @return in integer value of the cache's maximum size (buckets).
   */
  public int getMaxSize() {
    return maxSize;
  }

  /**
   * getSize returns the current size of the cache.  The size of the cache
   * is determined by the number of Cacheable objects cached in this cache
   * at any given time.
   *
   * @return a integer value specifying the current size of the cache.
   */
  public int getSize() {
    return cache.size();
  }

  /**
   * readCacheable returns a Cacheable object that is referenced in the cache
   * by the key parameter.  If the key is null, or if the key does not refer to
   * a Cacheable object, than null is returned.  If the Cacheable object exists
   * but is no longer valid or has expired, than the CacheablIF object's refresh
   * method is called.  If the refresh succeeds, then the Cacheable object is
   * returned, otherwise null is returned and the Cacheable object is removed
   * from the cache.
   *
   * @param key:Ljava.lang.String object referring to the Cacheable object of
   * interests in cache.
   * @return a L.Cacheable object referred to by the key parameter, or null
   * if it does not refer to a Cacheable object.
   */
  public Cacheable readCacheable(String key) {
    AbstractCacheable cacheObject = (AbstractCacheable) cache.get(key);

    if (cacheObject == null)
      return null;

    if (!cacheObject.isValid() || cacheObject.hasExpired()) {
      if (cacheObject.refresh()) {
        cacheObject.incHitCount();
        cacheObject.markLastHit();
        cacheObject.setValid(true);
      }
      else {
        cache.remove(key);
        cacheObject = null;
      }
    }

    return cacheObject;
  }

  /**
   * setMaxSize sets the maximum number of Cacheable objects that can be
   * contained in this cache (number of buckets).
   *
   * @param maxSize is an integer value specifying the maximum number of
   * Cacheable objects (buckets) to store in this cache.
   */
  public void setMaxSize(int maxSize) {
    this.maxSize = maxSize;
  }

  /**
   * writeCacheable stores the specified Cacheable object in this cache.
   *
   * @param cacheObject:L.Cacheable object to store in this cache and to be
   * shared by other modules of the application.
   * @throws java.lang.NullPointerException if the key (unique identifier)
   * of this Cacheable object is null.
   * @see Cacheable.getID()
   */
  public void writeCacheable(Cacheable cacheObject) {
    writeCacheable(cacheObject.getID(),cacheObject);
  }

  /**
   * writeCacheable stores the specified Cacheable object in this cache,
   * referenced by the key parameter.
   *
   * @param cacheObject:L.Cacheable object to store in this cache and to be
   * shared by other modules of the application.
   * @throws java.lang.NullPointerException if the key (unique identifier)
   * parameter is null.
   */
  public void writeCacheable(String key,
                             Cacheable cacheObject) {
    if (key == null)
      throw new NullPointerException("Cache keys cannot be null.");

    if (!purging) {
      purging = true;

      final Thread purgeTx = new Thread(new PurgeCacheTx());

      purgeTx.setPriority(Thread.MAX_PRIORITY);
      purgeTx.start();
    }

    cache.put(key,new CacheableImpl(cacheObject));
  }

}

