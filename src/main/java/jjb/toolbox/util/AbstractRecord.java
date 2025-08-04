/**
 * AbstractRecord.java (c) 2002.4.17
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * The AbstractRecord class provides a default implementation of the Map
 * interface as well as the Comparable interface.  Also, certain features
 * of a Record object common to all Record objects are also implemented
 * in this class.
 * 
 * @author John J. Blum
 * @version 2003.2.22
 * @see jjb.toolbox.util.Record
 * @see jjb.toolbox.util.RecordImpl;
 */

package jjb.toolbox.util;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public abstract class AbstractRecord implements Record {

  protected static final int INITIAL_CAPACITY = 117;

  private boolean readOnly;

  protected transient int modCount;

  private Collection values;

  private Set entrySet;
  private Set keySet;

  private String keyField;

  /**
   * Default constructor used by subclasses to create an instance
   * of the AbstractRecord class to initialize the common state of
   * the Record object.
   */
  public AbstractRecord() {
    readOnly = false;
    modCount = 0;
    keyField = null;
  }

  /**
   * The EmptyRecordIterator class is the default implementation of the
   * Iterator interface when no field are contained within this Record
   * object.
   */
  private static final class EmptyRecordIterator implements Iterator {
    private static final EmptyRecordIterator INSTANCE = new EmptyRecordIterator();

    private EmptyRecordIterator() {
    }

    public static final EmptyRecordIterator getInstance() {
      return INSTANCE;
    }

    public boolean hasNext() {
      return false;
    }

    public Object next() {
      throw new NoSuchElementException("The Record object contains no field-to-value mappings.");
    }

    public void remove() {
      throw new IllegalStateException("No element exists to be removed.");
    }
  }

  /**
   * The RecordEntry class represents field-to-value mapping in
   * the Record object.
   */
  private final class RecordEntry implements Map.Entry {
    private final int fieldIndex;

    public RecordEntry(int fieldIndex) {
      this.fieldIndex = fieldIndex;
    }

    public boolean equals(Object o) {
      if (o == this)
        return true;
      if (!(o instanceof RecordEntry))
        return false;

      final RecordEntry recEntry = (RecordEntry) o;

      Object value = getValue();

      return getKey().equals(recEntry.getKey()) && 
      (value == null ? (recEntry.getValue() == null) : value.equals(recEntry.getValue()));
    }

    public Object getKey() {
      return getField(fieldIndex);
    }

    public Object getValue() {
      return AbstractRecord.this.getValue(fieldIndex);
    }

    public int hashCode() {
      final Object value = getValue();
      return getKey().hashCode() ^ (value == null ? 0 : value.hashCode());
    }

    public Object setValue(Object value) {
      try {
        final Object oldValue = AbstractRecord.this.setValue(fieldIndex,value);
        return oldValue;
      }
      catch (ReadOnlyException roe) {
        throw new UnsupportedOperationException("setValue() is not allowed.  The Record object is read-only.");
      }
    }

    public String toString() {
      return getKey().toString() + "=" + ((String) getValue());
    }
  }

  /**
   * The RecordIterator class is an Iterator object over the fields and
   * values of this Record object.
   */
  private final class RecordIterator implements Iterator {

    public static final byte RECORD_ENTRY = 0;
    public static final byte KEYS = 1;
    public static final byte VALUES = 2;

    private boolean lastReturned  = false;

    private int expectedModCount = modCount;
    private int index = 0;
    private int size = size();
    private final int type;

    public RecordIterator()
    {
      this(RECORD_ENTRY);
    }

    public RecordIterator(byte type)
    {
      this.type = type;
    }

    public boolean hasNext()
    {
      return(index < size);
    }

    public Object next()
    {
      if (index >= size) {
        lastReturned = false;;
        throw new NoSuchElementException("The Record object contains no more field-to-value mappings.");
      }

      lastReturned = true;

      return(type == KEYS ? getField(index++) : (type == VALUES ? getValue(index++) : new RecordEntry(index++)));
    }

    public void remove()
    {
      if (!lastReturned)
        throw new IllegalStateException("No element specified to be remove.");

      if (expectedModCount != modCount)
        throw new ConcurrentModificationException("The Record object has been modified concurrently and this iterator is in an inconsistent state.");

      try {
        removeField(index-1);
        lastReturned = false;
        expectedModCount++;
      }
      catch (ReadOnlyException roe) {
        throw new UnsupportedOperationException("remove() is not allowed.  The Record object is read-only.");
      }
    }

  } // End of Class

  /**
   * addField adds the specified field to this Record object,
   * thus modifying the structure of the record.  Note, that only
   * Ljava.lang.Strings are valid field types.
   *
   * @param field is a Ljava.lang.String representation of the record
   * field (key).
   * @throws a jjb.toolbox.util.ReadOnlyException if this Record
   * is read-only.
   * @throws Ljava.lang.UnsupportedOperationException if the
   * addField operation is not allowed or unsuppored.
   */
  public void addField(String field) throws ReadOnlyException
  {
    addField(field,null);
  }

  /**
   * clear removes all mappings from this map (optional operation).
   *
   * @throws UnsupportedOperationException clear is not supported
   * by this map.
   */
  public void clear()
  {
    try {
      final String[] fields = getFields();

      for (int index = fields.length; --index >= 0; )
        removeField(fields[index]);
    }
    catch (ReadOnlyException ignore) {
      throw new UnsupportedOperationException("clear() is not allowed.  The Record object is read-only.");
    }
  }

  /**
   * compareTo compares this Record object to the Object parameter,
   * which also must be a Record object, determining which object
   * is before, the same, or after the other denoted by < 0, == 0,
   * and > 0.
   *
   * Note: this class has a natural ordering that is inconsistent with
   * equals.  This condition only occurs when the Record objects
   * keyFields differ and a comparison would be inaccurate.  The result
   * in this default implementation of comparing two Record objects
   * with different keyFields is to return 0 (equal).  This is to
   * ensure stability if comparing the same two Record objects by
   * another field proves to be feasible, this will preserve the
   * previous ordering on the objects.
   *
   * @param o is a java.lang.Object (Record object) used to compare
   * in ordering with this Record object.
   * @return a integer value of < 0 if this Record object should
   * come before the Object parameter, == 0 if this Record object
   * has the same position as the Object parameter, or > 0  if this
   * Record object should come after the specified Object parameter.
   * @throws a java.lang.ClassCastException if the Object parameter is
   * not a valid Record object.
   */
  public int compareTo(Object o)
  {
    if (keyField == null)
      return -1;

    final Record ro = (Record) o;

    if (!keyField.equals(ro.getKeyField()))
      return 0;

    return((Comparable) getValue(keyField)).compareTo(ro.getValue(ro.getKeyField()));
  }

  /**
   * containsFields determines whether all fields in the String array
   * are fields of this Record object.
   *
   * @param fields is a [Ljava.lang.String array of fields to test
   * for membership in this Record object.
   * @return a boolean value of true if all fields in the String array
   * are fields of this Record object, false otherwise.
   */
  public boolean containsFields(String[] fields)
  {
    if (fields == null || fields.length == 0)
      return false;

    for (int index = fields.length; --index >= 0; ) {
      if (containsField(fields[index]))
        return false;
    }

    return true;
  }

  /**
   * containsKey returns true if this map contains a mapping for
   * the specified key.
   *
   * @param key key whose presence in this map is to be tested.
   * @return true if this map contains a mapping for the specified
   * key.
   * @throws ClassCastException if the key is of an inappropriate
   * type for this map.
   * @throws NullPointerException if the key is null and this map
   * does not not permit null keys.
   */
  public boolean containsKey(Object key)
  {
    if (key == null)
      throw new NullPointerException("Record objects do not permit null keys (fields).");

    return containsField((String) key);
  }

  /**
   * containsValue returns true if this map maps one or more keys
   * to the specified value. More formally, returns true if and
   * only if this map contains at least one mapping to a value v
   * such that (value==null ? v==null : value.equals(v)). This
   * operation will probably require time linear in the map size
   * for most implementations of the Map interface.
   *
   * @param value value whose presence in this map is to be tested.
   * @return true if this map maps one or more keys to the specified
   * value.
   */
  public boolean containsValue(Object value)
  {
    for (int index = size(); --index >= 0; ) {
      if (getValue(index).equals(value))
        return true;
    }

    return false;
  }

  /**
   * entrySet returns a set view of the mappings contained in this
   * map. Each element in the returned set is a Map.Entry. The set
   * is backed by the map, so changes to the map are reflected in
   * the set, and vice-versa. If the map is modified while an
   * iteration over the set is in progress, the results of the
   * iteration are undefined. The set supports element removal,
   * which removes the corresponding mapping from the map, via the
   * Iterator.remove, Set.remove, removeAll, retainAll and clear
   * operations. It does not support the add or addAll operations.
   *
   * @return a set view of the mappings contained in this map.
   */
  public Set entrySet()
  {
    if (entrySet == null) {
      entrySet = new AbstractSet()
      {
        public void clear()
        {
          AbstractRecord.this.clear();
        }

        public Iterator iterator()
        {
          return getRecordIterator();
        }

        public boolean remove(Object o)
        {
          if (!(o instanceof Map.Entry))
            return false;

          try {
            removeField(((Map.Entry) o).getKey().toString());
          }
          catch (ReadOnlyException roe) {
            throw new UnsupportedOperationException("remove() is not allowed.  The Record object is read-only.");
          }

          return true;
        }

        public int size()
        {
          return AbstractRecord.this.size();
        }
      };
    }

    return entrySet;
  }

  /**
   * equals compares the specified object with this map for equality.
   * Returns true if the given object is also a map and the two Maps
   * represent the same mappings. More formally, two maps t1 and t2
   * represent the same mappings if
   *
   *   t1.entrySet().equals(t2.entrySet()).
   * 
   * This ensures that the equals method works properly across
   * different implementations of the Map interface.
   *
   * @param o object to be compared for equality with this map.
   * @return true if the specified object is equal to this map.
   */
  public boolean equals(Object o)
  {
    if (this == o)
      return true;

    if (!(o instanceof Record))
      return false;

    Record ro = (Record) o;

    return entrySet().equals(ro.entrySet());
  }

  /**
   * get returns the value to which this map maps the specified key.
   * Returns null if the map contains no mapping for this key. A
   * return value of null does not necessarily indicate that the map
   * contains no mapping for the key; it's also possible that the map
   * explicitly maps the key to null. The containsKey operation may
   * be used to distinguish these two cases.
   *
   * @param key key whose associated value is to be returned.
   * @return the value to which this map maps the specified key, or
   * null if the map contains no mapping for this key.
   * @throws ClassCastException if the key is of an inappropriate
   * type for this map.
   * @throws NullPointerException if the key is null and this map
   * does not not permit null keys.
   */
  public Object get(Object key)
  {
    return getValue((String) key);
  }

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
  public boolean getBooleanValue(int index)
  {
    return((Boolean) getValue(index)).booleanValue();
  }

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
  public boolean getBooleanValue(String field)
  {
    return((Boolean) getValue(field)).booleanValue();
  }

  /**
   * getByteValue returns the value of the field at index as a byte
   * primitive value.
   *
   * @param index is an integer value specifying the column (field)
   * to return the value for.
   * @return a primitive byte value for the value of the field at
   * index.
   * @throws java.lang.ClassCastException if the value of the field
   * at index is not a java.lang.Byte value.
   */
  public byte getByteValue(int index)
  {
    return((Byte) getValue(index)).byteValue();
  }

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
  public byte getByteValue(String field)
  {
    return((Byte) getValue(field)).byteValue();
  }

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
  public char getCharValue(int index)
  {
    return((Character) getValue(index)).charValue();
  }

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
  public char getCharValue(String field)
  {
    return((Character) getValue(field)).charValue();
  }

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
  public double getDoubleValue(int index)
  {
    return((Double) getValue(index)).doubleValue();
  }

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
  public double getDoubleValue(String field)
  {
    return((Double) getValue(field)).doubleValue();
  }

  /**
   * getFields returns an ordered java.lang.String array of all the
   * fields mapping to values in this Record object.  The fields
   * are ordered according to the addField calls that were invoked
   * on this Record object.
   *
   * @return a [java.lang.String array of the fields in this Record
   * object.
   */
  public String[] getFields()
  {
    final String[] fields = new String[size()];

    for (int index = fields.length; --index >= 0; )
      fields[index] = getField(index);

    return fields;
  }

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
  public float getFloatValue(int index)
  {
    return((Float) getValue(index)).floatValue();
  }

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
  public float getFloatValue(String field)
  {
    return((Float) getValue(field)).floatValue();
  }

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
  public int getIntValue(int index)
  {
    return((Integer) getValue(index)).intValue();
  }

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
  public int getIntValue(String field)
  {
    return((Integer) getValue(field)).intValue();
  }

  /**
   * getKeyField returns the indentity field of this Record object.
   * The identity field of a Record object uniquely identifies this
   * Record object from other Record ojbects.
   *
   * @return a java.lang.String object representation of the key field.
   */
  public String getKeyField()
  {
    return keyField;
  }

  /**
   * getKeyValue returns the value associated to the key field in this
   * Record object.  The value should be unique amongst a set of
   * related Record objects, as in a java.sql.ResultSet.
   *
   * @return a java.lang.Object of the value associated to the key field
   * of this Record object.
   */
  public Object getKeyValue()
  {
    return getValue(keyField);
  }

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
  public long getLongValue(int index)
  {
    return((Long) getValue(index)).longValue();
  }

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
  public long getLongValue(String field)
  {
    return((Long) getValue(field)).longValue();
  }

  /**
   * getRecordIterator returns an Iterator object for iterating over
   * the field/value pairs of this Record object.
   *
   * @return a Ljava.util.Iterator object that iterates the Record's
   * fields and corresponding values.
   */
  private Iterator getRecordIterator()
  {
    if (isEmpty())
      return EmptyRecordIterator.getInstance();
    else
      return new RecordIterator();
  }

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
  public short getShortValue(int index)
  {
    return((Short) getValue(index)).shortValue();
  }

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
  public short getShortValue(String field)
  {
    return((Short) getValue(field)).shortValue();
  }

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
  public String getStringValue(int index)
  {
    return getValue(index).toString();
  }

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
  public String getStringValue(String field)
  {
    return getValue(field).toString();
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
    return getValue(getField(index));
  }

  /**
   * hashCode returns the hash code value for this map. The hash code
   * of a map is defined to be the sum of the hashCodes of each entry
   * in the map's entrySet view. This ensures that t1.equals(t2)
   * implies that t1.hashCode()==t2.hashCode() for any two maps t1
   * and t2, as required by the general contract of Object.hashCode.
   *
   * @return the hash code value for this map.
   */
  public int hashCode()
  {
    int hashCode = 0;

    for (Iterator mapEntries = entrySet().iterator(); mapEntries.hasNext(); )
      hashCode += ((Map.Entry) mapEntries.next()).hashCode();

    return hashCode;
  }

  /**
   * isEmpty returns true if this map contains no key-value mappings.
   *
   * @return true if this map contains no key-value mappings.
   */
  public boolean isEmpty()
  {
    return(size() == 0);
  }

  /**
   * isReadOnly returns whether or not this Record object can be
   * modified through it's mutator methods, both in the Map and the
   * Record interfaces.
   *
   * @return a boolean value indicating true if this Record object
   * is read-only, false otherwise.
   */
  public boolean isReadOnly()
  {
    return readOnly;
  }

  /**
   * keySet Returns a set view of the keys contained in this map. The
   * set is backed by the map, so changes to the map are reflected in
   * the set, and vice-versa. If the map is modified while an
   * iteration over the set is in progress, the results of the
   * iteration are undefined. The set supports element removal, which
   * removes the corresponding mapping from the map, via the
   * Iterator.remove, Set.remove, removeAll retainAll, and clear
   * operations. It does not support the add or addAll operations.
   *
   * @return a set view of the keys contained in this map.
   */
  public Set keySet()
  {
    if (keySet == null) {
      keySet = new AbstractSet()
      {
        public void clear()
        {
          AbstractRecord.this.clear();
        }

        public boolean contains(Object o)
        {
          if (o == null)
            return false;

          return containsField(o.toString());
        }

        public Iterator iterator()
        {
          return new RecordIterator(RecordIterator.KEYS);
        }

        public boolean remove(Object o)
        {
          final int oldSize = size();

          try {
            removeField((String) o);
          }
          catch (ReadOnlyException roe) {
            throw new UnsupportedOperationException("remove() is not allowed.  The Record object is read-only.");
          }

          return(oldSize != size());
        }

        public int size()
        {
          return AbstractRecord.this.size();
        }
      };
    }

    return keySet;
  }

  /**
   * put associates the specified value with the specified key in this
   * map (optional operation). If the map previously contained a mapping
   * for this key, the old value is replaced.
   *
   * @param key key with which the specified value is to be associated.
   * @param value value to be associated with the specified key.
   * @return previous value associated with specified key, or null if
   * there was no mapping for key. A null return can also indicate that
   * the map previously associated null with the specified key, if the
   * implementation supports null values.
   * @throws UnsupportedOperationException if the put operation is not
   * supported by this map.
   * @throws ClassCastException if the class of the specified key or
   * value prevents it from being stored in this map.
   * @throws IllegalArgumentException if some aspect of this key or
   * value prevents it from being stored in this map.
   * @throws NullPointerException this map does not permit null keys or
   * values, and the specified key or value is null
   */
  public Object put(Object key,
                    Object value)
  {
    try {
      final String strKey = (String) key;

      final Object oldValue = getValue(strKey);

      addField(strKey,value);

      return oldValue;
    }
    catch (ReadOnlyException ignore) {
      throw new UnsupportedOperationException("put() is not allowed.  The Record object is read-only.");
    }
  }

  /**
   * putAll copies all of the mappings from the specified map to this map
   * (optional operation). These mappings will replace any mappings that
   * this map had for any of the keys currently in the specified map.
   *
   * @param t mappings to be stored in this map.
   * @throws UnsupportedOperationException if the put operation is not
   * supported by this map.
   * @throws ClassCastException if the class of the specified key or
   * value prevents it from being stored in this map.
   * @throws IllegalArgumentException if some aspect of this key or
   * value prevents it from being stored in this map.
   * @throws NullPointerException this map does not permit null keys or
   * values, and the specified key or value is null
   */
  public void putAll(Map t)
  {
    try {
      Object key = null;

      for (Iterator entries = t.keySet().iterator(); entries.hasNext(); ) {
        key = entries.next();
        addField(key.toString(),t.get(key));
      }
    }
    catch (ReadOnlyException ignore) {
      throw new UnsupportedOperationException("putAll() is not allowed.  The Record object is read-only.");
    }
  }

  /**
   * remove removes the mapping for this key from this map if present
   * (optional operation).
   *
   * @param key key whose mapping is to be removed from the map.
   * @return previous value associated with specified key, or null if
   * there was no mapping for key. A null return can also indicate that
   * the map previously associated null with the specified key, if the
   * implementation supports null values.
   * @throws UnsupportedOperationException if the remove method is not
   * supported by this map.
   */
  public Object remove(Object key)
  {
    try {
      return removeField((String) key);
    }
    catch (ReadOnlyException ignore) {
      throw new UnsupportedOperationException("remove() is not allowed.  The Record object is read-only.");
    }
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
    return removeField(getField(index));
  }

  /**
   * setKeyField sets the field specified by the keyField parameter to be
   * the key field (identity field) for this Record object.
   *
   * @param keyField is a java.lang.String object specifying the indentity
   * field for this Record object.
   * @throws java.lang.IllegalArgumentException if the field specified by
   * the keyField parameter is not a field in this Record object.
   */
  public void setKeyField(String keyField) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("setKeyField() is not allowed.  The Record object is read-only.");

    if (!containsField(keyField))
      throw new IllegalArgumentException(keyField+" does not exist in this Record object.");

    this.keyField = keyField;
  }

  /**
   * setReadOnly sets whether this Record object should be modified or
   * not.
   *
   * @param rwx is a boolean value indicating true if this Record object
   * should not be modified, false if this Record object can be modified.
   * @throws Ljava.lang.UnsupportedOperationException if the setReadOnly
   * operation is not allowed or unsuppored.
   */
  public void setReadOnly(boolean rwx)
  {
    readOnly = rwx;
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
    try {
      return setValue(getField(index),value);
    }
    catch (NoSuchFieldException ignore) {
      // Exception will not happen, will throw a
      // Ljava.lang.IndexOutOfBoundsException instead.
      return null;
    }
  }

  /**
   * toString returns the String representation of this Record
   * object.
   *
   * @return a Ljava.lang.String representation of this Record
   * object.
   */
  public String toString()
  {
    final StringBuffer buffer = new StringBuffer();

    buffer.append("[");

    RecordEntry recEntry = null;

    for (final Iterator i = getRecordIterator(); i.hasNext(); ) {
      recEntry = (RecordEntry) i.next();
      buffer.append(recEntry.getKey().toString()).append("=").append(recEntry.getValue().toString());

      if (i.hasNext())
        buffer.append(";");
    }

    buffer.append("]");

    return buffer.toString();
  }

  /**
   * values returns a collection view of the values contained in this
   * map. The collection is backed by the map, so changes to the map are
   * reflected in the collection, and vice-versa. If the map is modified
   * while an iteration over the collection is in progress, the results
   * of the iteration are undefined. The collection supports element
   * removal, which removes the corresponding mapping from the map, via
   * the Iterator.remove, Collection.remove, removeAll, retainAll and
   * clear operations. It does not support the add or addAll operations
   *
   * @return a collection view of the values contained in this map.
   */
  public Collection values()
  {
    if (values == null) {
      values = new AbstractCollection()
      {
        public void clear()
        {
          AbstractRecord.this.clear();
        }

        public boolean contains(Object value)
        {
          return containsValue(value);
        }

        public Iterator iterator()
        {
          return new RecordIterator(RecordIterator.VALUES);
        }

        public int size()
        {
          return AbstractRecord.this.size();
        }
      };
    }

    return values;
  }

}

