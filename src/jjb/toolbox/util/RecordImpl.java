/**
 * RecordImpl.java (c) 2002.4.17
 *
 * This is the default implementation of the Record interface which extends
 * the AbstractRecord class.  Note, that in some instances, the default
 * implementations in the AbstractRecord class are overridden to provide a
 * much more efficient implemenation based on the data structure used.
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.22
 * @see jjb.toolbox.util.Record;
 * @see jjb.toolbox.util.AbstractRecord;
 */

package jjb.toolbox.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordImpl extends AbstractRecord {

  private final List  fieldList;

  private final Map   record;

  /**
   * Creates an instance of the RecordImpl class as a modifyiable
   * Record object and no identity field specified.
   */
  public RecordImpl()
  {
    fieldList = new ArrayList(INITIAL_CAPACITY);
    record = new HashMap(INITIAL_CAPACITY);
  }

  /**
   * Creates an instance of the RecordImpl class and initializes
   * the contents of this Record object using the Map object
   * parameter.
   *
   * @param t java.util.Map used to initialize the contents of
   * this Record object. 
   * @throws java.lang.IllegalArgumentException if the key field
   * does not exist in the Map object, or if the keyField was
   * specified and the Map is null.
   */
  public RecordImpl(Map t)
  {
    this(t,false,null);
  }

  /**
   * Creates an instance of the RecordImpl class and initializes
   * the contents of this Record object using the Map object
   * parameter.  The read-only parameter also specifies whether this
   * Record object is modifyiable or is read-only.
   *
   * @param t java.util.Map used to initialize the contents of
   * this Record object. 
   * @param readOnly is a boolean value indicating true is this
   * Record object cannot be modified.
   * @throws java.lang.IllegalArgumentException if the key field
   * does not exist in the Map object, or if the keyField was
   * specified and the Map is null.
   */
  public RecordImpl(Map     t,
                       boolean readOnly)
  {
    this(t,readOnly,null);
  }

  /**
   * Creates an instance of the RecordImpl class and initializes
   * the contents of this Record object using the Map object
   * parameter as well as setting the identity field of this Record
   * object to the keyField parameter.  The keyField parameter should
   * specify the identity field, whose value, when compared to another
   * Record object, distinguishes this Record object from others.
   *
   * @param t java.util.Map used to initialize the contents of
   * this Record object. 
   * @param keyField is a java.lang.String object specifying the
   * identity field of this Record object.
   * @throws java.lang.IllegalArgumentException if the key field
   * does not exist in the Map object, or if the keyField was
   * specified and the Map is null.
   */
  public RecordImpl(Map    t,
                       String keyField)
  {
    this(t,false,keyField);
  }

  /**
   * Creates an instance of the RecordImpl class and initializes
   * the contents of this Record object using the Map object
   * parameter, specifying whether this Record object can be
   * modified or is read-only, and setting the identity field of
   * this Record object.  The keyField parameter should
   * specify the identity field, whose value, when compared to
   * another Record object, distinguishes this Record object
   * from others.
   *
   * @param t java.util.Map used to initialize the contents of
   * this Record object. 
   * @param readOnly is a boolean value indicating true is this
   * Record object cannot be modified.
   * @param keyField is a java.lang.String object specifying the
   * identity field of this Record object.
   * @throws java.lang.IllegalArgumentException if the key field
   * does not exist in the Map object, or if the keyField was
   * specified and the Map is null.
   */
  public RecordImpl(Map     t,
                       boolean readOnly,
                       String  keyField )
  {
    if (keyField != null && (t == null || !t.containsKey(keyField)))
      throw new IllegalArgumentException(keyField+" does not exist in the Map.  The identity field must be a valid key in this Record object.");

    final int initialCapacity = (int) (t != null ? (t.size() * 1.5) : INITIAL_CAPACITY);

    fieldList = new ArrayList(initialCapacity);
    record = new HashMap(initialCapacity);

    if (t != null)
      putAll(t);

    try {
      setKeyField(keyField);
    }
    catch (ReadOnlyException ignore) {
      // Exception will not occur
    }

    setReadOnly(readOnly);
  }

  /**
   * Copy constructor for this class, used to create an instance
   * of the RecordImpl class that is initialized to the given
   * Record object.  Note that this newly created Record
   * object will be read-only if the Record object parameter
   * is read-only (This Record object inherits the properties
   * of the Record object parameter).
   *
   * @param record is a Ljjb.toolbox.util.Record object used in
   * initializing this newly created instance.
   */
  public RecordImpl(Record record)
  {
    this(record,(record != null ? record.isReadOnly() : false));
  }

  /**
   * Copy constructor for this class, used to create an instance
   * of the RecordImpl class that is initialized to the given
   * Record object.
   *
   * @param record is a Ljjb.toolbox.util.Record object used in
   * initializing this newly created instance.
   * @param readOnly is a boolean value indicating true is this
   * Record object cannot be modified.
   */
  public RecordImpl(Record record,
                       boolean  readOnly)
  {
    final int initialCapacity = (record == null ? INITIAL_CAPACITY : (int) (record.size() * 1.5));

    this.record = new HashMap(initialCapacity);
    fieldList = new ArrayList(initialCapacity);

    if (record != null)
      putAll(record);

    try {
      if (record != null)
        setKeyField(record.getKeyField());
    }
    catch (ReadOnlyException ignore) {
    }

    setReadOnly(readOnly);
  }

  /**
   * addField adds the specified field along with a default value
   * for the field to this Record object, thus modifying the
   * structure of the record.  Note, that only Ljava.lang.Strings
   * are valid field types.
   * addField
   *
   * @param field is a Ljava.lang.String representation of the record
   * field (key).
   * @throws a jjb.toolbox.util.ReadOnlyException if this Record
   * is read-only.
   * @throws Ljava.lang.UnsupportedOperationException if the
   * addField operation is not allowed or unsuppored.
   */
  public void addField(String field,
                       Object defaultValue) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("addField() is not allowed.  This Record object is read-only.");

    if (field == null)
      throw new NullPointerException("Record object fields cannot be null.");

    modCount++;
    fieldList.add(field);
    record.put(field,defaultValue);
  }

  /**
   * containsField determines whether this Record object has the
   * field parameter.
   *
   * @param field is a Ljava.lang.String representation of the field
   * to test for membership in this Record object.
   * @returns a boolean value if this Record object contains the
   * specified field.
   */
  public boolean containsField(String field)
  {
    return record.containsKey(field);
  }

  /**
   * getField returns the field for the specified column index.
   * Indexes are between 0 (inclusive) and the cardinality of
   * this Record object.
   *
   * @param index is a integer column index in this Record
   * object for the specified field.
   * @throws Ljava.lang.IndexOutOfBoundsException if the column
   * index specified is not between 0 (inclusive) and size().
   */
  public String getField(int index)
  {
    if (index < 0 || index >= size())
      throw new IndexOutOfBoundsException(index+" is not a valid index specifying a field (column) in this Record object.");

    return fieldList.get(index).toString();
  }

  /**
   * getValue returns the value for the spcified column index
   * (field).
   *
   * @param index is a integer column index for which the value
   * is returned.
   * @return a Ljava.lang.Object representing the value of the
   * column at index.
   * @throws Ljava.lang.IndexOutOfBoundsException if the column
   * index specified is not between 0 (inclusive) and size().
   */
  public Object getValue(int index)
  {
    return record.get(fieldList.get(index).toString());
  }

  /**
   * getValue returns the value for the spcified field.  If the
   * Record object does not contain this field, the method
   * will return null.
   *
   * @param fiels is a Ljava.lang.String representation of the
   * field (column) for which the value is returned.
   * @return a Ljava.lang.Object representing the value of the
   * field.
   */
  public Object getValue(String field)
  {
    return record.get(field);
  }

  /**
   * removeField removes the specified field at index from
   * this Record and returns the value of the field.
   *
   * @param index integer column index specifying the field
   * from this Record object to remove.
   * @return the Ljava.lang.Object value of the field being
   * removed from this Record object.
   * @throws Ljjb.toolbox.util.ReadOnlyException object if
   * the record is read-only.
   * @throws Ljava.lang.IndexOutOfBoundsException if the column
   * index specified is not between 0 (inclusive) and size().
   * @throws Ljava.lang.UnsupportedOperationException if the
   * removeField operation is not allowed or unsuppored.
   */
  public Object removeField(int index) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("removeField() is not allowed.  The Record object is read-only.");

    modCount++;
    return record.remove(fieldList.get(index).toString());
  }

  /**
   * removeField removes the specified field this Record and
   * returns the value of the field.
   *
   * @param field is a Ljava.lang.String representation of the
   * field to remove from this Record object.
   * @return the Ljava.lang.Object value of the field being
   * removed from this Record object.
   * @throws Ljjb.toolbox.util.ReadOnlyException object if
   * the record is read-only.
   * @throws Ljava.lang.UnsupportedOperationException if the
   * removeField operation is not allowed or unsuppored.
   */
  public Object removeField(String field) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("removeField() is not allowed.  This Record object is read-only.");

    modCount++;
    return record.remove(field);
  }

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
   * @throws Ljava.lang.IndexOutOfBoundsException if the column
   * index specified is not between 0 (inclusive) and size().
   */
  public Object setValue(int    index,
                         Object value ) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("setValue() is not allowed.  The Record object is read-only.");

    return record.put(fieldList.get(index).toString(),value);
  }

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
   * @throws Ljava.lang.NullPointerException if the field
   * parameter is null.
   * @throws Ljava.lang.NoSuchFieldException if the Record
   * object does not contain the field for which the caller
   * is setting a value for.
   */
  public Object setValue(String field,
                         Object value ) throws ReadOnlyException, NoSuchFieldException
  {
    if (isReadOnly())
      throw new ReadOnlyException("setValue() is not allowed.  The Record object is read-only.");

    if (field == null)
      return new NullPointerException("Record fields cannot be null.");

    if (!containsField(field))
      throw new NoSuchFieldException("The Record object does not contain the "+field+" field.");

    return record.put(field,value);
  }

  /**
   * size returns the number of fields contained by this Record
   * object.
   *
   * @return a integer value specifying the number of fields in
   * this Record object.
   */
  public int size()
  {
    return record.size();
  }

}

