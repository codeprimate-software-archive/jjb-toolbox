/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * Modeled after the Ljava.util.Comparator interface in the Java 2 Platform.
 * This object is for use when the Java 2 platform is not available.  For
 * example, when writing applets that conform to browser's implementation
 * of the VM, Ljava.util.Comparator class of the Java 2 platform is not available.
 * Thus, this class is an actor which stands in for the Java 2 class.  In all
 * other cases, the Java 2 platform class Ljava.util.Comparator should be used.
 *
 * @author John J. Blum
 * File: ComparatorIF.java
 * @version v1.0
 * Date: 22 April 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.util;

public interface ComparatorIF
{

  /**
   * Compares its two arguments for order. Returns a negative integer, zero, or a
   * positive integer as the first argument is less than, equal to, or greater than
   * the second.
   *
   * The implementor must ensure that sgn(compare(x, y)) == -sgn(compare(y, x)) for
   * all x and y. (This implies that compare(x, y) must throw an exception if and only
   * if compare(y, x) throws an exception.)
   *
   * The implementor must also ensure that the relation is transitive:
   * ((compare(x, y)>0) && (compare(y, z)>0)) implies compare(x, z)>0.
   *
   * Finally, the implementer must ensure that compare(x, y)==0 implies that
   * sgn(compare(x, z))==sgn(compare(y, z)) for all z.
   *
   * It is generally the case, but not strictly required that
   * (compare(x, y)==0) == (x.equals(y)). Generally speaking, any comparator that
   * violates this condition should clearly indicate this fact. The recommended language
   * is "Note: this comparator imposes orderings that are inconsistent with equals."
   *
   * @param obj1:Ljava.lang.Object
   * @param obj2:Ljava.lang.Object
   * @return a negative integer, zero, or a positive integer as the first argument is
   * less than, equal to, or greater than the second.
   * @throw ClassCastException - if the arguments' types prevent them from being
   * compared by this Comparator.
   */
  public int compare(Object obj1,
                     Object obj2 );

  /**
   * Indicates whether some other object is "equal to" this Comparator. This method must
   * obey the general contract of Object.equals(Object). Additionally, this method can
   * return true only if the specified Object is also a comparator and it imposes the
   * same ordering as this comparator. Thus, comp1.equals(comp2) implies that
   * sgn(comp1.compare(obj1, obj2))==sgn(comp2.compare(obj1, obj2)) for every object reference
   * obj1 and obj2.
   * Note that it is always safe not to override Object.equals(Object). However,
   * overriding this method may, in some cases, improve performance by allowing programs
   * to determine that two distinct Comparators impose the same order.
   *
   * @param obj:Ljava.lang.Object used to compare for equality with this object.
   * @return true only if the specified object is also a comparator and it imposes the
   * same ordering as this comparator.
   */
  public boolean equals(Object obj);

}

