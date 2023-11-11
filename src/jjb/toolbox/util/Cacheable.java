/**
 * Cacheable.java (c) 2002.4.17
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */

package jjb.toolbox.util;

import java.util.Date;

public interface Cacheable {

  public Object getData();

  public Date getExpiration();

  public int getHitCount();

  public String getID();

  public Date getLastHit();

  public boolean hasExpired();

  public boolean isValid();

  public boolean refresh();

  public void setExpiration(long milliseconds);

  public void setExpiration(Date timeStamp);

}

