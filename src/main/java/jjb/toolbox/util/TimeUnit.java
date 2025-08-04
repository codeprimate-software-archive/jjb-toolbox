/**
 * TimeUnit.java (c) 2003.2.21
 *
 * The TimeUnit class is a type-safe enum that defines constants
 * representing various units of time.  Each unit of time is
 * defined in terms of the one before it (or that has the shorter
 * duration).  This class can be used to convert to different
 * units of time from a given unit of time.
 *
 * Copyright (c) 2003, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.21
 */

package jjb.toolbox.util;

public final class TimeUnit implements Comparable {

  // Basic units in which all other units are measured.
  public static final TimeUnit MILLISECOND = new TimeUnit("millisecond");
  public static final TimeUnit SECOND = new TimeUnit("second",1000,MILLISECOND);
  public static final TimeUnit MINUTE = new TimeUnit("minute",60,SECOND);
  public static final TimeUnit HOUR = new TimeUnit("hour",60,MINUTE);
  public static final TimeUnit DAY = new TimeUnit("day",24,HOUR);
  
  // Weeks of a Month in terms of number of Days
  // NOTE: that a week represents the safest, largest unit of time for
  // which no adjusting occurs.  A year for example can be a leap year
  // and therefore is adjusted by one day.  Anything that is defined
  // in terms of years will be affected.  Months have no reference
  // point as to which month should be considered "thee" month in order
  // to define the largest, safest unit of time.
  public static final TimeUnit WEEK = new TimeUnit("week",7,DAY);

  // Months of a Year in terms of number of Days
  public static final TimeUnit JANUARY = new TimeUnit("January",31,DAY);
  public static final TimeUnit FEBRUARY = new TimeUnit("February",28,DAY);
  public static final TimeUnit MARCH = new TimeUnit("March",31,DAY);
  public static final TimeUnit APRIL = new TimeUnit("April",30,DAY);
  public static final TimeUnit MAY = new TimeUnit("May",31,DAY);
  public static final TimeUnit JUNE = new TimeUnit("June",30,DAY);
  public static final TimeUnit JULY = new TimeUnit("July",31,DAY);
  public static final TimeUnit AUGUST = new TimeUnit("August",31,DAY);
  public static final TimeUnit SEPTEMBER = new TimeUnit("September",30,DAY);
  public static final TimeUnit OCTOBER = new TimeUnit("October",31,DAY);
  public static final TimeUnit NOVEMBER = new TimeUnit("November",30,DAY);
  public static final TimeUnit DECEMBER = new TimeUnit("December",31,DAY);
  // Covers February for the Leap Year
  public static final TimeUnit EXTENDED_FEBRUARY = new TimeUnit("February",29,DAY);

  // Year in terms of number of Days
  public static final TimeUnit YEAR = new TimeUnit("Year",365,DAY);
  // Leap Year defined for every 4th, even year in which the year
  // contains 1 extra day.
  public static final TimeUnit LEAP_YEAR = new TimeUnit("Leap Year",366,DAY);

  // Special TimeUnits - note that these are not entirely accurate as
  // they do not account for leap years.
  public static final TimeUnit DECADE = new TimeUnit("Decade",10,YEAR);
  public static final TimeUnit SCORE = new TimeUnit("Score",20,YEAR);
  public static final TimeUnit CENTURY = new TimeUnit("Century",100,YEAR);
  public static final TimeUnit MILLENNIUM = new TimeUnit("Millenium",1000,YEAR);
  public static final TimeUnit OTHER_MILLENNIUM = new TimeUnit("Millenium",10,CENTURY);

  private final int inTermsOfUnitValue;

  private final String description;

  private final TimeUnit inTermsOfUnit;

  private TimeUnit(final String description) {
    this(description,1,null);
  }

  private TimeUnit(final String description,
                   final int inTermsOfUnitValue,
                   final TimeUnit inTermsOfUnit ) {
    this.description = description;
    this.inTermsOfUnitValue = inTermsOfUnitValue;
    this.inTermsOfUnit = inTermsOfUnit;
  }

  /**
   * Compares this TimeUnit with another TimeUnit to determine
   * which unit is larger.
   */
  public int compareTo(Object obj) {
    final TimeUnit timeUnit = (TimeUnit) obj;
    return new Long(getTotalUnitValue() - timeUnit.getTotalUnitValue()).intValue();
  }

  /**
   * Returns a user friendly display of the value of the specified
   * time unit in terms of another set of time units upto a
   * specified unit of time and not smaller than the downto unit
   * of time.
   *
   * @param unitValue is the number of units of the specified
   * "sourceTimeUnit" unit of time, such as 30.
   * @param sourceTimeUnit is the unit of time of the specified
   * unitValue, such as minutes.  Therefore, combining unitValue
   * and sourceTimeUnit, you would have 30 minutes.
   * @param upto is the greatest unit of time in which you want
   * the unitValue in sourceTimeUnit unit of time to be 
   * displayed.
   * @param downto is the smallest unit of time in which you want
   * the unitValue in sourceTimeUnit unit of time to be
   * displayed.
   * @throws java.lang.IllegalArgumentException if the upto unit
   * of time is larger than the downto unit of time.
   */
  public static String convertTo(final long numberOfUnits,
                                 final TimeUnit sourceTimeUnit) {
    return convertTo(numberOfUnits,sourceTimeUnit,null,null);
  }

  public static String convertTo(final long numberOfUnits,
                                 final TimeUnit sourceTimeUnit,
                                 TimeUnit upto,
                                 TimeUnit downto               ) {
    if (sourceTimeUnit == null) {
      throw new NullPointerException("The TimeUnit of the number of units"
        +" to convert must be specified!");
    }
    if (upto == null)
      upto = WEEK;
    if (downto == null)
      downto = MILLISECOND;
    if (upto.compareTo(downto) < 0) {
      throw new IllegalArgumentException("The \"upto\" TimeUnit must be "
        +" larger than the \"downto\" TimeUnit!");
    }

    TimeUnit currentUnit = upto;
    long theUnitValue = sourceTimeUnit.getTotalUnitValue(numberOfUnits);
    final StringBuffer buffer = new StringBuffer();

    while (currentUnit != null && currentUnit.compareTo(downto) >= 0
           && theUnitValue > 0) {
      long currentUnitValue = currentUnit.getTotalUnitValue();
      if (theUnitValue > currentUnitValue) {
        long value = theUnitValue / currentUnitValue;
        buffer.append(value);
        buffer.append(" ");
        buffer.append(currentUnit.getDescription());
        buffer.append(value > 1 ? "s " : " ");
        theUnitValue %= currentUnitValue;
      }
      currentUnit = currentUnit.getInTermsOfUnit();
    }
    
    if (theUnitValue > 0)
      buffer.append("remainder of ").append(theUnitValue);

    return buffer.toString();
  }

  /**
   * Determines whether this TimeUnit is equal to some other
   * Object.
   */
  public boolean equals(Object obj) {
    if (obj == this)
      return true;
    if (!(obj instanceof TimeUnit))
      return false;

    final TimeUnit timeUnit = (TimeUnit) obj;

    return getInTermsOfUnit() == timeUnit.getInTermsOfUnit()
      && getInTermsOfUnitValue() == timeUnit.getInTermsOfUnitValue();        
  }

  /**
   * Returns a total unit value for one of these TimeUnits
   * in milliseconds.
   */
  private long getTotalUnitValue() {
    return inTermsOfUnitValue * (inTermsOfUnit == null ? 1 
      : inTermsOfUnit.getTotalUnitValue());
  }

  /**
   * Returns the total unit value of a number of these
   * TimeUnits in milliseconds.
   */
  private long getTotalUnitValue(final long numberOfThisUnit) {
    return numberOfThisUnit * getTotalUnitValue();
  }

  /**
   * Returns the unit of time in which this TimeUnit is defined.
   */
  public TimeUnit getInTermsOfUnit() {
    return inTermsOfUnit;
  }

  /**
   * Returns the number of unit in which this TimeUnit is based
   * on (or defined as) with respect to another unit of time.
   * For example, if this TimeUnit represents 1 hour, then 1 hour
   * is equivalent to 60 minutes and thus, this method will
   * return 60.
   */
  public int getInTermsOfUnitValue() {
    return inTermsOfUnitValue;
  }

  /**
   * Returns a description of this TimeUnit.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Returns the value of this TimeUnit in terms of the target
   * unit of time.
   */
  public long getInTermsOf(final TimeUnit targetUnit) {
    return getInTermsOf(1,targetUnit);
  }

  /**
   * Returns the value of a number of these TimeUnits in terms
   * of the target unit of time.
   */
  public long getInTermsOf(final int numberOfThisUnit,
                           final TimeUnit timeUnit    ) {
    if (timeUnit == this)
      return numberOfThisUnit;
      
    if (timeUnit == inTermsOfUnit)
      return numberOfThisUnit * inTermsOfUnitValue;

    return (compareTo(timeUnit) > 0 ? 0 :
      numberOfThisUnit * inTermsOfUnitValue * inTermsOfUnit.getInTermsOf(timeUnit));
  }

  /**
   * Returns the computed hash code value of this TimeUnit
   * instance.
   */
  public int hashCode() {
    int result = 17;
    result = 37 * result + getInTermsOfUnitValue();
    result = 37 * result + (getInTermsOfUnit() == null ? 0
      : getInTermsOfUnit().hashCode());
    return result;
  }

  /**
   * Returns a String representation of this TimeUnit.
   */
  public String toString() {
    final StringBuffer buffer = new StringBuffer("A ");
    buffer.append(getDescription());
    buffer.append(" is defined in terms of ");
    buffer.append(inTermsOfUnitValue);
    buffer.append(" ");
    buffer.append(inTermsOfUnit.getDescription());
    buffer.append(inTermsOfUnitValue > 1 ? "s" : "");
    return buffer.toString();
  }

}

