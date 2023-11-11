/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @auther John J. Blum
 * File: CommManagerFactory.java
 * @version v1.0
 * Date: 17 April 2001
 * Modified Date: 17 April 2002
 * @since Java 1.0
 * @see jjb.toolbox.net.http.HTTPCommManager
 * @see jjb.toolbox.net.rmi.RMICommManager
 * @see jjb.toolbox.net.talk.TalkCommManager
 */

package jjb.toolbox.net;

import java.lang.reflect.Constructor;
import java.util.Observer;

public final class CommManagerFactory
{

  /**
   * Private default constructor to support non-instantiability.  The class
   * is made final in support of immutability.
   */
  private CommManagerFactory()
  {
  }

  /**
   * createCommManager is a factory method used to create an instance of a
   * subclass of the CommManagerIF class.  This method uses the Class and
   * Constructor classes, and the name of the communication manager to
   * construct the instance.
   *
   * @param className is a Ljava.lang.String the class name of the
   * CommManagerIF object to instantiate.
   * @param observer is a Ljava.util.Observer object that will receive
   * notifications from the CommManagerIF object.
   * @return jjb.toolbox.CommMananger
   * @throws Ljjb.toolbox.net.CommException if a communication problem
   * occurs.
   */
  public static CommManagerIF createCommManager(String    className,
                                                Observer  observer  ) throws CommException
  {
    try
    {
      Class clazz = null;

      try
      {
        clazz = Class.forName(className);

        Constructor constructor = clazz.getConstructor(new Class[] { Observer.class });

        return (CommManagerIF) constructor.newInstance(new Object[] { observer });
      }
      catch (ClassNotFoundException cnfe)
      {
        throw new NoSuchCommManagerException("No communication manager named " + className + " exists.");
      }
      catch (NoSuchMethodException nsme)
      {
        return (CommManagerIF) clazz.newInstance();
      }
    }
    // Catch java.lang.InstantiationException
    // Catch java.alng.IllegalAccessException
    catch (Exception e)
    {
      throw new CommException(e);
    }
  }

}

