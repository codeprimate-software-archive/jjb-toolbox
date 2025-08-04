/**
 * Record.java (c) 2002.4.17
 *
 * A Record object is a collection (an ordered map) of fields constituting
 * the structure of the record.  In the usual meaning of the term record, we
 * think of a database record, which is a group of related fields origninating
 * from a single database table.  The notion of record as defined by this
 * interface type also applies to records in the context of SQL and database
 * transactions.  A RecordIF object, can for example, be derived from a
 * <b>java.sql.ResultSet</b> object.  However, we extend the definition of a
 * record by meaning any group of related fields regardless of origin.
 * Therefore, a RecordIF object could be obtained from a query
 * (java.sql.ResultSet object) which selected information from multiple tables
 * via a join.
 * <br>
 * To ensure correct behavior of a record object, only non-mutable objects can
 * be used as fields.  Since there is no way to ensure that a class is
 * non-mutable, the only valid field type for a record is a java.lang.String
 * object.  However, since the RecordIF interface exentds the java.util.Map
 * interface, methods declared by java.util.Map, such as put(Object key,
 * Object value), allowing keys to be other than String objects will have their
 * toString method called on the Object parameter, and the resulting String
 * used as the field for the value supplied, or not supplied.
 * <br>
 * As a final note, to distinguish the RecordIF interface from the java.util.Map
 * interface, it should be emphasized that fields (keys) for a RecordIF object
 * are ordered according to the order specified, either through calling the
 * addField method repeatedly, or through the structure defined by some other
 * data struture.  If the data structure is another java.util.Map object, then
 * the order is determined by the set of keys view calling the keySet method.
 * If the data structure is a java.sql.ResultSet object, then the order of the
 * fields is determined by the query (SQL) that generated the java.sql.ResultSet
 * object.
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.22
 * @see java.util.Map
 * @see jjb.toolbox.util.AbstractRecord;
 * @see jjb.toolbox.util.RecordImpl;
 */

package jjb.toolbox.util;

import java.io.Serializable;
import java.util.Map;

public interface Record extends Comparable, Map, Serializable {

  /**
   * addField adds the specified field to this RecordIF object,
   * thus modifying the structure of the record.  Note, that only
   * Ljava.lang.Strings are valid field types.
   *
   * @param field is a Ljava.lang.String representation of the record
   * field (key).
   * @throws a jjb.toolbox.util.ReadOnlyException if this RecordIF
   * is read-only.
   * @throws Ljava.lang.UnsupportedOperationException if the
   * addField operation is not allowed or unsuppored.
   */
  public void addField(String field) throws ReadOnlyException;

  /**
   * addField adds the specified field along with a default value
   * for the field to this RecordIF object, thus modifying the
   * structure of the record.  Note, that only Ljava.lang.Strings
   * are valid field types.
   * addField
   *
   * @param field is a Ljava.lang.String representation of the record
   * field (key).
   * @throws a jjb.toolbox.util.ReadOnlyException if this RecordIF
   * is read-only.
   * @throws Ljava.lang.UnsupportedOperationException if the
   * addField operation is not allowed or unsuppored.
   */
  public void addField(String field,
                       Object defaultValue) throws ReadOnlyException;

  /**
   * containsField determines whether this RecordIF object has the
   * field parameter.
   *
   * @param field is a Ljava.lang.String representation of the field
   * to test for membership in this RecordIF object.
   * @returns a boolean value if this RecordIF object contains the
   * specified field.
   */
  public boolean containsField(String field);

  /**
   * containsFields determines whether all fields in the String array
   * are fields of this RecordIF object.
   *
   * @param fields is a [Ljava.lang.String array of fields to test
   * for membership in this RecordIF object.
   * @return a boolean value of true if all fields in the String array
   * are fields of this RecordIF object, false otherwise.
   */
  public boolean containsFields(String[] fields);

  /**
   * getBooleanValue returns the value of the field at index as a
   * boolean primitive value.
   *
   * @param index is an integer value specifying the column (field)
   * to return the value for.
   * @return a primitive boolean value for the value of the field at
   * index.
   * @throws java.lang.ClassCastException if the value of the field
   * at index is not a java.lang.Boolean value.
   */
  public boolean getBooleanValue(int index);

  /**
   * getBooleanValue returns the value of the field (key) as a boolean
   * primitive value.
   *
   * @param field is a String object specifying the key of the Record
   * to retrieve a value for.
   * @return a primitive boolean value for the value of the specified
   * field.
   * @throws java.lang.ClassCastException if the value's Object type
   * for the specified field in the Record is not a java.lang.Boolean.
   */
  public boolean getBooleanValue(String field);

  /**
   * getByteValue returns the value of the field (key) as a byte
   * primitive value.
   *
   * @param index is an integer value specifying the column (field)
   * to return the value for.
   * @return a primitive char value for the value of the field at
   * index.
   * @throws java.lang.ClassCastException if the value's Object type
   * for the specified field in the Record is not a java.lang.Byte.
   */
  public byte getByteValue(int index);

  /**
   * getByteValue returns the value of the field at index as a byte
   * primitive value.
   *
   * @param field is a String object specifying the key of the Record
   * to retrieve a value for.
   * @return a primitive byte value for the value of the specified
   * field.
   * @throws java.lang.ClassCastException if the value of the field
   * at index is not a java.lang.Byte value.
   */
  public byte getByteValue(String field);

  /**
   * getCharValue returns the value of the field at index as a char
   * primitive value.
   *
   * @param index is an integer value specifying the column (field)
   * to return the value for.
   * @return a primitive char value for the value of the field at
   * index.
   * @throws java.lang.ClassCastException if the value of the field
   * at index is not a java.lang.Character value.
   */
  public char getCharValue(int index);

  /**
   * getCharValue returns the value of the field (key) as a char
   * primitive value.
   *
   * @param field is a String object specifying the key of the Record
   * to retrieve a value for.
   * @return a primitive char value for the value of the specified
   * field.
   * @throws java.lang.ClassCastException if the value's Object type
   * for the specified field in the Record is not a java.lang.Character.
   */
  public char getCharValue(String field);

  /**
   * getDoubleValue returns the value of the field at index as a double
   * primitive value.
   *
   * @param index is an integer value specifying the column (field)
   * to return the value for.
   * @return a primitive double value for the value of the field at
   * index.
   * @throws java.lang.ClassCastException if the value of the field
   * at index is not a java.lang.Double value.
   */
  public double getDoubleValue(int index);

  /**
   * getDoubleValue returns the value of the field (key) as a double
   * primitive value.
   *
   * @param field is a String object specifying the key of the Record
   * to retrieve a value for.
   * @return a primitive double value for the value of the specified
   * field.
   * @throws java.lang.ClassCastException if the value's Object type
   * for the specified field in the Record is not a java.lang.Double.
   */
  public double getDoubleValue(String field);

  /**
   * getField returns the field for the specified column index.
   * Indexes are between 0 (inclusive) and the cardinality of
   * this RecordIF object.
   *
   * @param index is a integer column index in this RecordIF
   * object for the specified field.
   * @throws Ljava.lang.IndexOutOfBoundsException if the column
   * index specified is not between 0 (inclusive) and size().
   */
  public String getField(int index);

  /**
   * getFields returns an ordered java.lang.String array of all the
   * fields mapping to values in this RecordIF object.  The fields
   * are ordered according to the addField calls that were invoked
   * on this RecordIF object.
   *
   * @return a [java.lang.String array of the fields in this RecordIF
   * object.
   */
  public String[] getFields();

  /**
   * getFloatValue returns the value of the field at index as a float
   * primitive value.
   *
   * @param index is an integer value specifying the column (field)
   * to return the value for.
   * @return a primitive float value for the value of the field at
   * index.
   * @throws java.lang.ClassCastException if the value of the field
   * at index is not a java.lang.Float value.
   */
  public float getFloatValue(int index);

  /**
   * getFloatValue returns the value of the field (key) as a float
   * primitive value.
   *
   * @param field is a String object specifying the key of the Record
   * to retrieve a value for.
   * @return a primitive float value for the value of the specified
   * field.
   * @throws java.lang.ClassCastException if the value's Object type
   * for the specified field in the Record is not a java.lang.Float.
   */
  public float getFloatValue(String field);

  /**
   * getIntValue returns the value of the field at index as a int
   * primitive value.
   *
   * @param index is an integer value specifying the column (field)
   * to return the value for.
   * @return a primitive int value for the value of the field at
   * index.
   * @throws java.lang.ClassCastException if the value of the field
   * at index is not a java.lang.Integer value.
   */
  public int getIntValue(int index);

  /**
   * getIntValue returns the value of the field (key) as a int
   * primitive value.
   *
   * @param field is a String object specifying the key of the Record
   * to retrieve a value for.
   * @return a primitive int value for the value of the specified
   * field.
   * @throws java.lang.ClassCastException if the value's Object type
   * for the specified field in the Record is not a java.lang.Integer.
   */
  public int getIntValue(String field);

  /**
   * getKeyField returns the indentity field of this RecordIF object.
   * The identity field of a RecordIF object uniquely identifies this
   * RecordIF object from other RecordIF ojbects.
   *
   * @return a java.lang.String object representation of the key field.
   */
  public String getKeyField();

  /**
   * getKeyValue returns the value associated to the key field in this
   * RecordIF object.  The value should be unique amongst a set of
   * related RecordIF objects, as in a java.sql.ResultSet.
   *
   * @return a java.lang.Object of the value associated to the key field
   * of this RecordIF object.
   */
  public Object getKeyValue();

  /**
   * getLongValue returns the value of the field at index as a long
   * primitive value.
   *
   * @param index is an integer value specifying the column (field)
   * to return the value for.
   * @return a primitive long value for the value of the field at
   * index.
   * @throws java.lang.ClassCastException if the value of the field
   * at index is not a java.lang.Long value.
   */
  public long getLongValue(int index);

  /**
   * getLongValue returns the value of the field (key) as a long
   * primitive value.
   *
   * @param field is a String object specifying the key of the Record
   * to retrieve a value for.
   * @return a primitive long value for the value of the specified
   * field.
   * @throws java.lang.ClassCastException if the value's Object type
   * for the specified field in the Record is not a java.lang.Long.
   */
  public long getLongValue(String field);

  /**
   * getShortValue returns the value of the field at index as a short
   * primitive value.
   *
   * @param index is an integer value specifying the column (field)
   * to return the value for.
   * @return a primitive short value for the value of the field at
   * index.
   * @throws java.lang.ClassCastException if the value of the field
   * at index is not a java.lang.Short value.
   */
  public short getShortValue(int index);

  /**
   * getShortValue returns the value of the field (key) as a short
   * primitive value.
   *
   * @param field is a String object specifying the key of the Record
   * to retrieve a value for.
   * @return a primitive short value for the value of the specified
   * field.
   * @throws java.lang.ClassCastException if the value's Object type
   * for the specified field in the Record is not a java.lang.Short.
   */
  public short getShortValue(String field);

  /**
   * getStringValue returns the value of the field at index as a
   * java.lang.String value.
   *
   * @param index is an integer value specifying the column (field)
   * to return the value for.
   * @return a java.lang.String representation of the value of the
   * field at index.
   * @throws java.lang.ClassCastException if the value of the field
   * at index is not a java.lang.String value.
   */
  public String getStringValue(int index);

  /**
   * getStringValue returns the value of the field (key) as a
   * java.lang.String value.
   *
   * @param field is a String object specifying the key of the Record
   * to retrieve a value for.
   * @return a java.lang.String representation of the value of the 
   * specified field.
   * @throws java.lang.ClassCastException if the value's Object type
   * for the specified field in the Record is not a java.lang.String.
   */
  public String getStringValue(String field);

  /**
   * getValue returns the value for the spcified column index
   * (field).
   *
   * @param index is a integer column index for which the value
   * is returned.
   * @return a Ljava.lang.Object representing the value of the
   * column at index.
   */
  public Object getValue(int index);

  /**
   * getValue returns the value for the spcified field.
   *
   * @param fiels is a Ljava.lang.String representation of the
   * field (column) for which the value is returned.
   * @return a Ljava.lang.Object representing the value of the
   * field.
   */
  public Object getValue(String field);

  /**
   * isReadOnly returns whether or not this RecordIF object can be
   * modified through it's mutator methods, both in the Map and the
   * RecordIF interfaces.
   *
   * @return a boolean value indicating true if this RecordIF object
   * is read-only, false otherwise.
   */
  public boolean isReadOnly();

  /**
   * removeField removes the specified field at index from
   * this Record and returns the value of the field.
   *
   * @param index integer column index specifying the field
   * from this RecordIF object to remove.
   * @return the Ljava.lang.Object value of the field being
   * removed from this RecordIF object.
   * @throws Ljjb.toolbox.util.ReadOnlyException object if
   * the record is read-only.
   * @throws Ljava.lang.IndexOutOfBoundsException if the column
   * index specified is not between 0 (inclusive) and size().
   * @throws Ljava.lang.UnsupportedOperationException if the
   * removeField operation is not allowed or unsuppored.
   */
  public Object removeField(int index) throws ReadOnlyException;

  /**
   * removeField removes the specified field this Record and
   * returns the value of the field.
   *
   * @param field is a Ljava.lang.String representation of the
   * field to remove from this RecordIF object.
   * @return the Ljava.lang.Object value of the field being
   * removed from this RecordIF object.
   * @throws Ljjb.toolbox.util.ReadOnlyException object if
   * the record is read-only.
   * @throws Ljava.lang.UnsupportedOperationException if the
   * removeField operation is not allowed or unsuppored.
   */
  public Object removeField(String field) throws ReadOnlyException;

  /**
   * setKeyField sets the field specified by the keyField parameter to be
   * the key field (identity field) for this RecordIF object.
   *
   * @param keyField is a java.lang.String object specifying the indentity
   * field for this RecordIF object.
   * @throws java.lang.IllegalArgumentException if the field specified by
   * the keyField parameter is not a field in this RecordIF object.
   */
  public void setKeyField(String keyField) throws ReadOnlyException;

  /**
   * setReadOnly sets whether this RecordIF object should be modified or
   * not.
   *
   * @param rwx is a boolean value indicating true if this RecordIF object
   * should not be modified, false if this RecordIF object can be modified.
   * @throws Ljava.lang.UnsupportedOperationException if the setReadOnly
   * operation is not allowed or unsuppored.
   */
  public void setReadOnly(boolean rwx);

  /**
   * setValue sets the value of the field at index to the
   * specified value parameter.
   *
   * @param index is the column index of the field for which
   * the value is set.
   * @param value is a Ljava.lang.Object parameter containing
   * the new value of the field.
   * @return a Ljava.lang.Object containing the old value 
   * mapped to this field.
   * @throws Ljjb.toolbox.util.ReadOnlyException object if
   * the record is read-only.
   */
  public Object setValue(int index,
                         Object value ) throws ReadOnlyException;

  /**
   * setValue sets the value of the field to the specified
   * value parameter.
   *
   * @param field is a Ljava.lang.String representation of the
   * field to set the value for.
   * @param value is a Ljava.lang.Object parameter containing
   * the new value of the field.
   * @return a Ljava.lang.Object containing the old value 
   * mapped to this field.
   * @throws Ljjb.toolbox.util.ReadOnlyException object if
   * the record is read-only.
   * @throws Ljava.lang.NoSuchFieldException if the RecordIF
   * object does not contain the field for which the caller
   * is setting a value for.
   */
  public Object setValue(String field,
                         Object value ) throws ReadOnlyException, NoSuchFieldException;

  /**
   * size returns the number of fields contained by this RecordIF
   * object.
   *
   * @return a integer value specifying the number of fields in
   * this RecordIF object.
   */
  public int size();

}

