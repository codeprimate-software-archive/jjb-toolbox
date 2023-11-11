/**
 * Records.java (c) 2002.4.17
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.22
 * @see jjb.toolbox.util.Record
 * @see jjb.toolbox.util.RecordTable
 * @see jjb.toolbox.util.RecordImpl
 * @see jjb.toolbox.util.RecordTableImpl
 */

package jjb.toolbox.util;

import java.awt.Dimension;
import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Records {

  /**
   * Constructor used by subclasses to create an instance of
   * the Records class in order to extend the functionality
   * of this class. Since the class is an utility class, the
   * protected modifier on the default constructor prevents
   * instances of this class from being created.
   */
  protected Records() {
  }

  /**
   * SynchronizedCollection
   * Copyright (c) 1997 - 2000, Sun Microsystems, Inc.
   * Code borrowed from the java.util.Collections.java source file.
   */
  private static class SynchronizedCollection implements Collection, Serializable {
    // use serialVersionUID from JDK 1.2.2 for interoperability
    private static final long serialVersionUID = 3053995032091335093L;

    Collection c; // Backing Collection

    Object mutex; // Object on which to synchronize

    public SynchronizedCollection(Collection c)
    {
      if (c == null)
        throw new NullPointerException();

      this.c = c;
      mutex = this;
    }
    public SynchronizedCollection(Collection c,
                                  Object     mutex)
    {
      this.c = c;
      this.mutex = mutex;
    }
    public int size()
    {
      synchronized(mutex)
      {
        return c.size();
      }
    }
    public boolean isEmpty()
    {
      synchronized(mutex)
      {
        return c.isEmpty();
      }
    }
    public boolean contains(Object o)
    {
      synchronized(mutex)
      {
        return c.contains(o);
      }
    }
    public Object[] toArray()
    {
      synchronized(mutex)
      {
        return c.toArray();
      }
    }
    public Object[] toArray(Object[] a)
    {
      synchronized(mutex)
      {
        return c.toArray(a);
      }
    }
    public Iterator iterator()
    {
      return c.iterator(); // Must be manually synched by user!
    }
    public boolean add(Object o)
    {
      synchronized(mutex)
      {
        return c.add(o);
      }
    }
    public boolean remove(Object o)
    {
      synchronized(mutex)
      {
        return c.remove(o);
      }
    }
    public boolean containsAll(Collection coll)
    {
      synchronized(mutex)
      {
        return c.containsAll(coll);
      }
    }
    public boolean addAll(Collection coll)
    {
      synchronized(mutex)
      {
        return c.addAll(coll);
      }
    }
    public boolean removeAll(Collection coll)
    {
      synchronized(mutex)
      {
        return c.removeAll(coll);
      }
    }
    public boolean retainAll(Collection coll)
    {
      synchronized(mutex)
      {
        return c.retainAll(coll);
      }
    }
    public void clear()
    {
      synchronized(mutex)
      {
        c.clear();
      }
    }
    public String toString()
    {
      synchronized(mutex)
      {
        return c.toString();
      }
    }
  }

  /**
   * SynchronizedSet
   * Copyright (c) 1997 - 2000, Sun Microsystems, Inc.
   * Code borrowed from the java.util.Collections.java source file.
   */
  private static class SynchronizedSet extends SynchronizedCollection implements Set {
    public SynchronizedSet(Set s)
    {
      super(s);
    }
    public SynchronizedSet(Set    s,
                           Object mutex)
    {
      super(s,mutex);
    }
    public boolean equals(Object o)
    {
      synchronized(mutex)
      {
        return c.equals(o);
      }
    }
    public int hashCode()
    {
      synchronized(mutex)
      {
        return c.hashCode();
      }
    }
  }

  private static final class SynchronizedRecord implements Record {
    private Collection values;

    private Object     mutex;

    private Record   record;

    private Set        keySet;
    private Set        entrySet;

    public SynchronizedRecord(Record record)
    {
      this.record = record;
      mutex = this;
    }
    public SynchronizedRecord(Record record,
                              Object   mutex  )
    {
      this.record = record;
      this.mutex  = mutex;
    }
    public void addField(String field) throws ReadOnlyException
    {
      synchronized(mutex)
      {
        record.addField(field);
      }
    }
    public void addField(String field,
                         Object value) throws ReadOnlyException
    {
      synchronized(mutex)
      {
        record.addField(field,value);
      }
    }
    public void clear()
    {
      synchronized(mutex)
      {
        record.clear();
      }
    }
    public int compareTo(Object o)
    {
      synchronized(mutex)
      {
        return record.compareTo(o);
      }
    }
    public boolean containsField(String field)
    {
      synchronized(mutex)
      {
        return record.containsField(field);
      }
    }
    public boolean containsFields(String[] fields)
    {
      synchronized(mutex)
      {
        return record.containsFields(fields);
      }
    }
    public boolean containsKey(Object key)
    {
      synchronized(mutex)
      {
        return record.containsKey(key);
      }
    }
    public boolean containsValue(Object value)
    {
      synchronized(mutex)
      {
        return record.containsValue(value);
      }
    }
    public Set entrySet()
    {
      synchronized(mutex)
      {
        if (entrySet == null)
          entrySet = new SynchronizedSet(record.entrySet(),mutex);

        return entrySet;
      }
    }
    public boolean equals(Object o)
    {
      synchronized(mutex)
      {
        return record.equals(o);
      }
    }
    public Object get(Object key)
    {
      synchronized(mutex)
      {
        return record.get(key);
      }
    }
    public boolean getBooleanValue(int index)
    {
      synchronized(mutex)
      {
        return record.getBooleanValue(index);
      }
    }
    public boolean getBooleanValue(String field)
    {
      synchronized(mutex)
      {
        return record.getBooleanValue(field);
      }
    }
    public byte getByteValue(int index)
    {
      synchronized(mutex)
      {
        return record.getByteValue(index);
      }
    }
    public byte getByteValue(String field)
    {
      synchronized(mutex)
      {
        return record.getByteValue(field);
      }
    }
    public char getCharValue(int index)
    {
      synchronized(mutex)
      {
        return record.getCharValue(index);
      }
    }
    public char getCharValue(String field)
    {
      synchronized(mutex)
      {
        return record.getCharValue(field);
      }
    }
    public double getDoubleValue(int index)
    {
      synchronized(mutex)
      {
        return record.getDoubleValue(index);
      }
    }
    public double getDoubleValue(String field)
    {
      synchronized(mutex)
      {
        return record.getDoubleValue(field);
      }
    }
    public String getField(int index)
    {
      synchronized(mutex)
      {
        return record.getField(index);
      }
    }
    public String[] getFields()
    {
      synchronized(mutex)
      {
        return record.getFields();
      }
    }
    public float getFloatValue(int index)
    {
      synchronized(mutex)
      {
        return record.getFloatValue(index);
      }
    }
    public float getFloatValue(String field)
    {
      synchronized(mutex)
      {
        return record.getFloatValue(field);
      }
    }
    public int getIntValue(int index)
    {
      synchronized(mutex)
      {
        return record.getIntValue(index);
      }
    }
    public int getIntValue(String field)
    {
      synchronized(mutex)
      {
        return record.getIntValue(field);
      }
    }
    public String getKeyField()
    {
      synchronized(mutex)
      {
        return record.getKeyField();
      }
    }
    public Object getKeyValue()
    {
      synchronized(mutex)
      {
        return record.getKeyValue();
      }
    }
    public long getLongValue(int index)
    {
      synchronized(mutex)
      {
        return record.getLongValue(index);
      }
    }
    public long getLongValue(String field)
    {
      synchronized(mutex)
      {
        return record.getLongValue(field);
      }
    }
    public short getShortValue(int index)
    {
      synchronized(mutex)
      {
        return record.getShortValue(index);
      }
    }
    public short getShortValue(String field)
    {
      synchronized(mutex)
      {
        return record.getShortValue(field);
      }
    }
    public String getStringValue(int index)
    {
      synchronized(mutex)
      {
        return record.getStringValue(index);
      }
    }
    public String getStringValue(String field)
    {
      synchronized(mutex)
      {
        return record.getStringValue(field);
      }
    }
    public Object getValue(int index)
    {
      synchronized(mutex)
      {
        return record.getValue(index);
      }
    }
    public Object getValue(String field)
    {
      synchronized(mutex)
      {
        return record.getValue(field);
      }
    }
    public int hashCode()
    {
      synchronized(mutex)
      {
        return record.hashCode();
      }
    }
    public boolean isEmpty()
    {
      synchronized(mutex)
      {
        return record.isEmpty();
      }
    }
    public boolean isReadOnly()
    {
      synchronized(mutex)
      {
        return record.isReadOnly();
      }
    }
    public Set keySet()
    {
      synchronized(mutex)
      {
        if (keySet == null)
          keySet = new SynchronizedSet(record.keySet(),mutex);

        return keySet;
      }
    }
    public Object put(Object key,
                      Object value)
    {
      synchronized(mutex)
      {
        return record.put(key,value);
      }
    }
    public void putAll(Map t)
    {
      synchronized(mutex)
      {
        record.putAll(t);
      }
    }
    public Object remove(Object key)
    {
      synchronized(mutex)
      {
        return record.remove(key);
      }
    }
    public Object removeField(int index) throws ReadOnlyException
    {
      synchronized(mutex)
      {
        return record.removeField(index);
      }
    }
    public Object removeField(String field) throws ReadOnlyException
    {
      synchronized(mutex)
      {
        return record.removeField(field);
      }
    }
    public void setKeyField(String keyField) throws ReadOnlyException
    {
      synchronized(mutex)
      {
        record.setKeyField(keyField);
      }
    }
    public void setReadOnly(boolean rwx)
    {
      synchronized(mutex)
      {
        record.setReadOnly(rwx);
      }
    }
    public Object setValue(int    index,
                           Object value) throws ReadOnlyException
    {
      synchronized(mutex)
      {
        return record.setValue(index,value);
      }
    }
    public Object setValue(String field,
                           Object value) throws ReadOnlyException,NoSuchFieldException
    {
      synchronized(mutex)
      {
        return record.setValue(field,value);
      }
    }
    public int size()
    {
      synchronized(mutex)
      {
        return record.size();
      }
    }
    public Collection values()
    {
      synchronized(mutex)
      {
        if (values == null)
          values = new SynchronizedCollection(record.values(),mutex);

        return values;
      }
    }
  }

  /**
   * synchronizedRecord
   */
  public static Record synchronizedRecord(final Record record)
  {
    return new SynchronizedRecord(record);
  }

  /**
   * synchronizedRecordTable
   */
  public static RecordTable synchronizedRecordTable(final RecordTable recordTable)
  {
    return new RecordTable()
    {
      public boolean add(Object o)
      {
        synchronized(this)
        {
          return recordTable.add(o);
        }
      }
      public boolean addAll(Collection c)
      {
        synchronized(this)
        {
          return recordTable.addAll(c);
        }
      }
      public void addAll(RecordTable rt) throws ReadOnlyException
      {
        synchronized(this)
        {
          recordTable.addAll(rt);
        }
      }
      public void addRecord(Record record) throws ReadOnlyException
      {
        synchronized(this)
        {
          recordTable.addRecord(record);
        }
      }
      public void clear()
      {
        synchronized(this)
        {
          recordTable.clear();
        }
      }
      public int columnCount()
      {
        synchronized(this)
        {
          return recordTable.columnCount();
        }
      }
      public boolean contains(Object o)
      {
        synchronized(this)
        {
          return recordTable.contains(o);
        }
      }
      public boolean contains(Record record)
      {
        synchronized(this)
        {
          return recordTable.contains(record);
        }
      }
      public boolean containsAll(Collection c)
      {
        synchronized(this)
        {
          return recordTable.containsAll(c);
        }
      }
      public RecordTable difference(RecordTable rt)
      {
        synchronized(this)
        {
          return recordTable.difference(rt);
        }
      }
      public Dimension dimension()
      {
        synchronized(this)
        {
          return recordTable.dimension();
        }
      }
      public boolean equals(Object o)
      {
        synchronized(this)
        {
          return recordTable.equals(o);
        }
      }
      public Record get(int index)
      {
        synchronized(this)
        {
          return recordTable.get(index);
        }
      }
      public Record get(Object keyFieldValue)
      {
        synchronized(this)
        {
          return recordTable.get(keyFieldValue);
        }
      }
      public Object getCell(int row,
                            int col)
      {
        synchronized(this)
        {
          return recordTable.getCell(row,col);
        }
      }
      public String getColumnAlias(int index)
      {
        synchronized(this)
        {
          return recordTable.getColumnAlias(index);
        }
      }
      public String getColumnAlias(String name) throws NoSuchFieldException
      {
        synchronized(this)
        {
          return recordTable.getColumnAlias(name);
        }
      }
      public String[] getColumnAliases()
      {
        synchronized(this)
        {
          return recordTable.getColumnAliases();
        }
      }
      public int getColumnIndex(String column)
      {
        synchronized(this)
        {
          return recordTable.getColumnIndex(column);
        }
      }
      public String getColumnName(int index)
      {
        synchronized(this)
        {
          return recordTable.getColumnName(index);
        }
      }
      public String getColumnName(String alias)
      {
        synchronized(this)
        {
          return recordTable.getColumnName(alias);
        }
      }
      public String[] getColumnNames()
      {
        synchronized(this)
        {
          return recordTable.getColumnNames();
        }
      }
      public ResultSetMetaData getMetaData()
      {
        // Method is returning static information therefore,
        // this method does not need to be synchronized.
        return recordTable.getMetaData();
      }
      public int hashCode()
      {
        synchronized(this)
        {
          return recordTable.hashCode();
        }
      }
      public int indexOf(Object keyFieldValue)
      {
        synchronized(this)
        {
          return recordTable.indexOf(keyFieldValue);
        }
      }
      public int indexOf(Record record)
      {
        synchronized(this)
        {
          return recordTable.indexOf(record);
        }
      }
      public void insert(Record record,
                         int      index  ) throws ReadOnlyException
      {
        synchronized(this)
        {
          recordTable.insert(record,index);
        }
      }
      public void insertCol(String column) throws ReadOnlyException
      {
        synchronized(this)
        {
          recordTable.insertCol(column);
        }
      }
      public void insertCol(String column,
                            int    index ) throws ReadOnlyException
      {
        synchronized(this)
        {
          recordTable.insertCol(column,index);
        }
      }
      public Record insertRow() throws ReadOnlyException
      {
        synchronized(this)
        {
          return new SynchronizedRecord(recordTable.insertRow(),this);
        }
      }
      public Record insertRow(int index) throws ReadOnlyException
      {
        synchronized(this)
        {
          return new SynchronizedRecord(recordTable.insertRow(index),this);
        }
      }
      public RecordTable intersection(RecordTable rt)
      {
        synchronized(this)
        {
          return recordTable.intersection(rt);
        }
      }
      public boolean isEmpty()
      {
        synchronized(this)
        {
          return recordTable.isEmpty();
        }
      }
      public boolean isReadOnly()
      {
        synchronized(this)
        {
          return recordTable.isReadOnly();
        }
      }
      public Iterator iterator()
      {
        return recordTable.iterator();
      }
      public boolean remove(int index) throws ReadOnlyException
      {
        synchronized(this)
        {
          return recordTable.remove(index);
        }
      }
      public boolean remove(Object o)
      {
        synchronized(this)
        {
          return recordTable.remove(o);
        }
      }
      public boolean _remove(Object keyFieldValue) throws ReadOnlyException
      {
        synchronized(this)
        {
          return recordTable._remove(keyFieldValue);
        }
      }
      public boolean remove(Record record) throws ReadOnlyException
      {
        synchronized(this)
        {
          return recordTable.remove(record);
        }
      }
      public boolean removeAll() throws ReadOnlyException
      {
        synchronized(this)
        {
          return recordTable.removeAll();
        }
      }
      public boolean removeAll(Collection c)
      {
        synchronized(this)
        {
          return recordTable.removeAll(c);
        }
      }
      public boolean retainAll(Collection c)
      {
        synchronized(this)
        {
          return recordTable.retainAll(c);
        }
      }
      public int rowCount()
      {
        synchronized(this)
        {
          return recordTable.rowCount();
        }
      }
      public void setCell(int    row,
                          int    col,
                          Object value) throws ReadOnlyException
      {
        synchronized(this)
        {
          recordTable.setCell(row,col,value);
        }
      }
      public void setColumnAlias(int    index,
                                 String alias)
      {
        synchronized(this)
        {
          recordTable.setColumnAlias(index,alias);
        }
      }
      public void setColumnAlias(String name,
                                 String alias) throws NoSuchFieldException
      {
        synchronized(this)
        {
          recordTable.setColumnAlias(name,alias);
        }
      }
      public void setColumnName(int    index,
                                String name  ) throws ReadOnlyException
      {
        synchronized(this)
        {
          recordTable.setColumnName(index,name);
        }
      }
      public void setColumnName(String alias,
                                String name) throws ReadOnlyException
      {
        synchronized(this)
        {
          recordTable.setColumnName(alias,name);
        }
      }
      public void setReadOnly(boolean readOnly)
      {
        synchronized(this)
        {
          recordTable.setReadOnly(readOnly);
        }
      }
      public int size()
      {
        synchronized(this)
        {
          return recordTable.size();
        }
      }
      public RecordTable subTable(int row,
                                  int col,
                                  int width,
                                  int height)
      {
        synchronized(this)
        {
          return recordTable.subTable(row,col,width,height);
        }
      }
      public RecordTable subTable(int       row,
                                  int       col,
                                  Dimension dim )
      {
        synchronized(this)
        {
          return recordTable.subTable(row,col,dim);
        }
      }
      public RecordTable subTable(int[] rows,
                                  int[] cols)
      {
        synchronized(this)
        {
          return recordTable.subTable(rows,cols);
        }
      }
      public Object[] toArray()
      {
        synchronized(this)
        {
          return recordTable.toArray();
        }
      }
      public Object[] toArray(Object a[])
      {
        synchronized(this)
        {
          return recordTable.toArray(a);
        }
      }
      public Object[][] toTabular()
      {
        synchronized(this)
        {
          return recordTable.toTabular();
        }
      }
      public Object[][] toTabular(String[] fieldSet)
      {
        synchronized(this)
        {
          return recordTable.toTabular(fieldSet);
        }
      }
      public RecordTable union(RecordTable rt)
      {
        synchronized(this)
        {
          return recordTable.union(rt);
        }
      }
    };
  }

  /**
   * unmodifiableRecord wraps a Record object inside another
   * Record object that cannot be modified.  The isReadOnly
   * method always returns true and calling the setReadOnly
   * method throws an UnsupportedOperationException.
   *
   * @param record is a Ljjb.toolbox.util.Record object to
   * convert to an absolute read-only record.
   * @return a Ljjb.toolbox.util.Record object encapsulating
   * the original Record object with read-only capabilities.
   */
  public static Record unmodifiableRecord(Record record)
  {
    return new RecordImpl(record)
    {
      public boolean isReadOnly()
      {
        return true;
      }
      public void setReadOnly(boolean readOnly)
      {
        throw new UnsupportedOperationException("setReadOnly() is not allowed.  The Record object is unmodifiable.");
      }
    };
  }

  /**
  * unmodifiableRecordTable wraps a RecordTable object inside
  * another RecordTable object that cannot be modified.  The
  * isReadOnly method always returns true and calling the
  * setReadOnly method results in throwing an
  * UnsupportedOperationException.
  *
  * @param record is a Ljjb.toolbox.util.RecordTable object
  * to convert to an absolute read-only record table.
  * @return a Ljjb.toolbox.util.RecordTable object encapsulating
  * the original RecordTable object with read-only capabilities.
   */
  public static RecordTable unmodifiableRecordTable(RecordTable recordTable)
  {
    return new RecordTableImpl(recordTable)
    {
      public boolean isReadOnly()
      {
        return true;
      }
      public void setReadOnly(boolean readOnly)
      {
        throw new UnsupportedOperationException("setReadOnly() is not allowed.  The RecordTable object is unmodifiable.");
      }
    };
  }

}

