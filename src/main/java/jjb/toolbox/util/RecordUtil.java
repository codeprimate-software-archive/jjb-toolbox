/**
 * RecordUtil.java (c) 2002.4.17
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.22
 */

package jjb.toolbox.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jjb.toolbox.util.StringUtil;

public class RecordUtil {

  private static DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

  /**
   * formatValue formats the data from a record object for an
   * SQL statement.
   *
   * @param value java.lang.Object the value from the record
   * to format based on the type of value.  Note that the type
   * of value should match that of the field in the database.
   * @return a java.lang.String the is formatted for the SQL
   * statement.
   */
  public static String formatValue(Object value) {
    if (value instanceof String) 
      return "'"+StringUtil.replace(value.toString(),"'","''")+"'";
    if (value instanceof Date) 
      return "'"+dateFormat.format((Date) value)+"'";
    if (value instanceof Byte) 
      return value.toString();
    if (value instanceof Short) 
      return value.toString();
    if (value instanceof Integer) 
      return value.toString();
    if (value instanceof Long) 
      return value.toString();

    if (value instanceof String[]) {
      StringBuffer list = new StringBuffer();

      String[] listValues = (String[]) value;

      list.append("(");

      if (listValues.length > 0)
        list.append("'").append(listValues[0]).append("'");
      else
        list.append("''");

      for (int itemIndex = listValues.length; --itemIndex > 0; )
        list.append(", '").append(StringUtil.replace(listValues[itemIndex],"'","''")).append("'");

      list.append(")");

      return list.toString();
    }

    if (value instanceof Integer[]) {
      StringBuffer list = new StringBuffer();

      Integer[] listValues = (Integer[]) value;

      list.append("(");

      if (listValues.length > 0)
        list.append(listValues[0]);

      for (int itemIndex = listValues.length; --itemIndex > 0; )
        list.append(", ").append(listValues[itemIndex]);

      list.append(")");

      return list.toString();
    }

    return null;
  }

}

