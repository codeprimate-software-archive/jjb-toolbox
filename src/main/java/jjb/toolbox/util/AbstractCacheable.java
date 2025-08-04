/**
 * AbstractCacheable.java (c) 2002.4.17
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */

package jjb.toolbox.util;

import java.util.Date;

public abstract class AbstractCacheable implements Cacheable {

  private boolean valid;

  private int hitCount;

  protected Date expiration;
  private Date lastHit;

  private Object data;

  private final String ID;

  /**
   * The AbstractCacheable class instance is created by an object
   * extending this class for purposes of caching the data it contains.
   * Thus, a cache object cannot be created without having data.
   *
   * @param data java.lang.Object representation of the information
   * to cache.
   */
  public AbstractCacheable(Object data) {
    this(null,data);
  }

  /**
   * The AbstractCacheable class instance is created by an object
   * extending this class for purposes of caching the data it
   * contains.  Thus, a cache object cannot be created without having
   * data.  Also, the ID of the Cacheable object is specified allowing
   * the Cacheable object to identify itself to the cache, using the
   * Cache.write(:Cacheable) method.
   *
   * @param ID java.lang.String object specifying the unique identifier
   * of this Cacheable object in the cache.
   * @param data java.lang.Object representation of the information
   * to cache.
   */
  public AbstractCacheable(String ID,
                           Object data) {
    if (data == null)
      throw new NullPointerException("The data for a Cacheable object cannot be null.");

    valid = true;
    hitCount = 0;
    expiration = null;
    lastHit = null;
    this.data = data;
    this.ID = ID;
  }

  /**
   * Copy constructor for cached objects.  Called by subclasses to
   * create an instance of the AbstractCacheable class that is identical
   * to the Cacheable object parameter.  This constructor is most likely
   * used by the Cache object when inserting defensive copies of cache
   * objects.
   *
   * @param cacheObject Cacheable object to cache.
   */
  AbstractCacheable(Cacheable cacheObject) {
    if (cacheObject == null)
      throw new NullPointerException("Cache object cannot be null.");

    valid = true;
    hitCount = 0;
    expiration = null;

    final Date cacheObjectDate = cacheObject.getExpiration();

    if (cacheObjectDate != null)
      expiration = new Date(cacheObjectDate.getTime());

    lastHit = null;
    data = cacheObject.getData();
    ID = cacheObject.getID();
  }

  /**
   * getData returns the information that need to be encapsulated by this
   * Cacheable object to be cached.
   *
   * @return a java.lang.Object value representing the data cached.
   */
  public Object getData() {
    return data;
  }

  /**
   * getHitCount returns the number of times the Cacheable object was
   * accessed from the cache.
   *
   * @return a integer value specifying the number hits.
   */
  public int getHitCount() {
    return hitCount;
  }

  /**
   * getID returns the unique identifier that the Cacheable object uses
   * to identify itself to the cache.
   *
   * @return a java.lang.String object representation of the unique cache
   * identifier.
   */
  public String getID() {
    return ID;
  }

  /**
   * getLastHit returns the time stamp of the last access to this Cacheable
   * object.
   *
   * @return a java.util.Date object containing the time that Cacheable
   * object was last accessed.
   */
  public Date getLastHit() {
    return new Date(lastHit.getTime());
  }

  /**
   * incHitCount is called by the Cache object to increment the hitCount
   * instance variable everytime the Cache object recieves a read access
   * for this cached object.
   */
  final void incHitCount() {
    hitCount++;
  }

  /**
   * isValid returns whether the cached data is still valid.
   *
   * @return a boolean value indicating validity of the cached data.
   */
  public boolean isValid() {
    return valid;
  }

  /**
   * markLastHit records the current time that this cache object was accessed
   * from the cache.
   */
  final void markLastHit() {
    lastHit = new Date();
  }

  /**
   * setValid method is called by the Cache object to either validate or
   * invalidate the cache.
   *
   * @param valid is a boolean value the validity of this cache object in
   * the cache.
   */
  final void setValid(boolean valid) {
    this.valid = valid;
  }

}

