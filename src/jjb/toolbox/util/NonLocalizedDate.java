/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: NonLocalizedDate.java
 * @version v1.0
 * Date: 24 July 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.util;

import java.io.*;
import java.text.*;
import java.util.*;

public class NonLocalizedDate extends Date
{

  /**
   * Allocates a NonLocalizedDate object and initializes it so that it represents
   * the time at which it was allocated, measured to the nearest millisecond.
   */
  public NonLocalizedDate()
  {
    super(System.currentTimeMillis());
  }

  /**
   * Allocates a NonLocalizedDate object and initializes it to represent the specified
   * number of milliseconds since the standard base time known as "the epoch",
   * namely January 1, 1970, 00:00:00 GMT.
   *
   * @param millis is a long value representing the number of milliseconds from the epoch.
   */
  public NonLocalizedDate(long millis)
  {
    super(millis);
  }

  /**
   * readObject is the method called during serialization to stream this object over the
   * wire.  The default method, Date.readObject defined in the super class, streams the
   * number of milliseconds at the point where it was created to it's desination, thus
   * adjusted for timezone since the same long value represents a different time across
   * timezones dependent upon your localality.  This new readObject method preserves this
   * point in time by adding or subtracting the difference across timezones.  Thus a
   * date/time created in one timezone represents the same date/time in another timezone.
   *
   * @param in:Ljava.io.ObjectInputStream used to read the Non-Localized Date object from
   * the input stream.
   * @throws Ljava.lang.ClassNotFoundException
   * @throws Ljava.io.IOException
   */
  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException
  {
    //String timeZoneID = in.readObject().toString();
    int originOffset = in.readInt();

    long milliseconds = in.readLong();

    //TimeZone originTimeZone      = TimeZone.getTimeZone(timeZoneID);
    TimeZone destinationTimeZone = TimeZone.getDefault();

    //milliseconds = milliseconds + (originTimeZone.getRawOffset() - destinationTimeZone.getRawOffset());
    milliseconds = milliseconds + (originOffset - destinationTimeZone.getRawOffset());
    setTime(milliseconds);
  }

  /**
   * toString prints the Date object in the following format month/day/year.
   *
   * @return a Ljava.lang.String representation of the Date object.
   */
  public String toString()
  {
    return toString("MM/dd/yyyy");
  }

  /**
   * toString prints the Date object in the specified format as determined by
   * the format parameter.
   *
   * @param format:Ljava.lang.String object of the formatted Date.
   * @return a Ljava.lang.String representation of the Date object.
   */
  public String toString(String format)
  {
    if (format == null)
      return super.toString();

    DateFormat dateFormat = new SimpleDateFormat(format);

    return dateFormat.format(this);
  }

  /**
   * writeObject first writes the raw offset of the originating time zone and then serializes
   * the long value representing the number of milliseconds from the epoch at that particular
   * timezone.
   *
   * @param out:Ljava.io.ObjectOutputSteam is used write this Non-Localized Date object over
   * the wire.
   * @throws Ljava.io.IOException
   */
  private void writeObject(ObjectOutputStream out) throws IOException
  {
    //out.writeObject(TimeZone.getDefault().getID());
    out.writeInt(TimeZone.getDefault().getRawOffset());
    out.writeLong(getTime());
  }

}

