/**
 * Cache.java (c) 2002.4.17
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */

package jjb.toolbox.util;

import java.util.Iterator;

public interface Cache {

  public static final int DEFAULT_CACHE_SIZE = 1024;

  public boolean containsCacheable(Cacheable cacheObject);

  public boolean containsKey(String key);

  public Iterator getCacheables();

  public Iterator getKeys();

  public int getMaxSize();

  public int getSize();

  public void invalidateCache();

  public Cacheable readCacheable(String key);

  public void setMaxSize(int maxSize);

  public void writeCacheable(Cacheable cacheObject);

  public void writeCacheable(String key,
                             Cacheable cacheObject);

  public void writeObject(String key,
                          Object cacheObject);

}

