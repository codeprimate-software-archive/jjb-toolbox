/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * This class loads the resources needed by an application
 * at runtime.  Such resources include logos, icons, sounds
 * property files, etc.  This class should be extended to
 * include application specific retrieval of resources
 * using a properties file and overridding the loadResource
 * method.
 *
 * The class is thread-safe and forces keys referring to
 * the resources as well as the resources themselves to
 * be non-null when storing them in the ResourceManager.
 *
 * Only String values, which implement both the equals and
 * hashCode methods, are allowed as keys referring to 
 * resources in the ResourceManager.
 *
 * @author John J. Blum
 * File: ResourceManager.java
 * @version v1.0
 * Date: 22 April 2001
 * Modified Date: 12 May 2002
 * @since Java 1.0
 */

package jjb.toolbox.util;

import java.awt.Component;
import java.util.Hashtable;
import java.util.Map;

public abstract class ResourceManager
{

  private static Map  resources;

  static
  {
    resources = new Hashtable();
  }

  /**
   * addResource stores a reference to a resource and addresses it with
   * the key parameter (key).
   *
   * @param key:java.lang.String the key used to address the resoucre.
   * @param resource:java.lang.Object the resource Object addressed.
   */
  public static void addResource(String key,
                                 Object resource)
  {
    resources.put(key,resource);
  }

  /**
   * getResource uses the key (key) to address the resource Object
   * and return the resource to it's caller.
   *
   * @param key:java.lang.String the key used to address the resource.
   * @return a Ljava.lang.Object the resource Object addressed by key.
   */
  public static Object getResource(String key)
  {
    if (resources == null || resources.isEmpty())
      return null;

    return resources.get(key);
  }

  /**
   * loadResources initializes the data structure (Hashtable) used
   * to store a reference to a resource based on a key.
   *
   * @param targetComp is a Ljava.awt.Component object used by a
   * MediaTracker object to track images and other resources for
   * the given component.
   */
  public abstract void loadResources(Component targetComp);

}

