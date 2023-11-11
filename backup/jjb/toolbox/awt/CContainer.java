/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * This container component is used to work around Microsoft's
 * ignorance.  They were sitting on the toilet when Sun decided
 * to change the implementation of the java.awt.Container component
 * of the AWT.  Sun has now made this a non-abstract class, which
 * means that you can instantiate an instance of the Container
 * class to create transparent panels.  However, Microshit's VM
 * throws an InstantiationError, which only occurs when a user
 * tries to create an instance of an abstract class or interface.
 * This means MicroShitForBrains forgot to reimplement their VM
 * when the Java specification changed.  Where do you want to go
 * today?  Straight to hell!  Do not go pass go and collect your
 * 200 dollars.  Burn Gates burn!!!!
 *
 * @author John J. Blum
 * File: CContainer.java
 * @version v1.0
 * Date: 15 April 2001
 * Modified Date: 17 April 2002
 * @since Java 1.0
 * @deprecated subclasses should extend the java.awt.Container class
 * instead of this class (CContainer) when implementing transparent
 * containers.  Reason stims from the fact that application
 * developers should be pushing the envelop on technology, using
 * the Core Java API classes, not reinvent the wheel, and support
 * the Java plugin technology for the latest release of the JRE.
 * Since at least the JRE 1.2, the CContainer class has been made
 * non-abstract.
 */

package jjb.toolbox.awt;

public class CContainer extends java.awt.Container
{
}

