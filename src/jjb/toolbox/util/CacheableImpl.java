/**
 * CacheableImpl.java (c) 2002.4.17
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */

package jjb.toolbox.util;

import java.util.Calendar;
import java.util.Date;

public class CacheableImpl extends AbstractCacheable {

  protected long timeToLive = 0;

  /**
   * Creates an instance of the CacheableImpl class with the
   * specified data to cache.
   *
   * @param data java.lang.Object representation of the information
   * to cache.
   */
  public CacheableImpl(Object data) {
    super(null,data);
  }

  /**
   * Creates an instance of the CacheableImpl class with the
   * specified data to cache and identifier allowing this Cacheable
   * object to identify itself to the cache.
   *
   * @param ID java.lang.String object containing the unqiue cache
   * identifier.
   * @param data java.lang.Object representation of the information
   * to cache.
   */
  public CacheableImpl(String ID,
                       Object data) {
    super(ID,data);
  }

  /**
   * Copy constructor for cached objects.  Creates an instance of the
   * CacheableImpl class that is identical to the Cacheable object
   * parameter.  This constructor is most likely used by the Cache
   * object when inserting defensive copies of cache objects.
   *
   * @param cacheObject Cacheable object to cache.
   */
  CacheableImpl(Cacheable cacheObject) {
    super(cacheObject);
  }

  /**
   * getExpiration returns the expiration time stamp on this object.
   *
   * @return a java.util.Date representation of when this cached object
   * expires.
   */
  public Date getExpiration() {
    if (expiration == null)
      return null;
    return new Date(expiration.getTime());
  }

  /**
   * hasExpired returns a boolean value indicating if this cache object
   * has expired or time stamp is before the current time.
   *
   * @return a boolean value if expired, false otherwise.
   */
  public boolean hasExpired() {
    if (expiration == null)
      return false;
    return expiration.before(new Date());
  }

  /**
   * refresh refreshes the contents of this cached object, the data that
   * is cached.  The Cache object calls this method when the cached object
   * is accessed and the Cache object realizes that this cached object
   * has expired or is no longer valid.  If the refresh is successful, then
   * the data is returned to the caller requesting the cached information,
   * and the Cache object updates the cached object's hit count and last hit
   * time stamp.  If the refresh fails, then null is returned to the caller
   * requesting the cached information and the cache object is removed.
   *
   * It is also the responsibility of the refresh method to reset the time to
   * live, expiration of this cached object.  Failure to do so will always
   * result in this Cacheable object being in the state of expiration once
   * the Cacheable object has expired for the first time.  Thus, for all
   * cache purge operations, the refresh method of the Cacheable object will
   * be called regardless of time to live.
   *
   * Finally, the refresh methods has a responsibility to set the valid flag
   * to true calling the AbstractCacheable.setValid method.
   *
   * @returns a boolean value of true if the data was successfully updated,
   * false otherwise.
   */
  public boolean refresh() {
    return false;
  }

  /**
   * setExpiration sets the expiration time stamp for the cached object to
   * expire in the cache.  If milliseconds is equal to zero, than the cached
   * object will not expire.
   *
   * @param long value indicating the number of milliseconds from now until
   * the cache object will expire
   * @throws java.lang.IllegalArgumentException object if milliseconds is less
   * than zero.
   */
  public void setExpiration(long milliseconds) {
    if (milliseconds < 0)
      throw new IllegalArgumentException("milliseconds must greater than or equal to 0.");

    if (milliseconds == 0) {
      expiration = null;
      return;
    }

    final Calendar now = Calendar.getInstance();

    timeToLive = milliseconds;
    now.add(Calendar.MONTH,(int) milliseconds);
    expiration = now.getTime();
  }

  /**
   * setExpiration sets the expiration time stamp for the cached object to
   * expire in the cache.  If the expireTimeStamp parameter is null, or is
   * before the current time, then the cached object will not expire.
   *
   * @param expireTimeStamp java.util.Date object specifying the time stamp
   * to expire this cache object.
   */
  public void setExpiration(Date expireTimeStamp) {
    final Date now = new Date();

    if (expireTimeStamp == null || expireTimeStamp.before(now)) {
      expiration = null;
      return;
    }

    timeToLive = expireTimeStamp.getTime() - now.getTime();
    expiration = new Date(expireTimeStamp.getTime());
  }

}

