/**
 * RecordTableImpl.java (c) 2002.4.17
 *
 * The RecordTableImpl class is the default implementation of the
 * RecordTable interface.  The record table data structure mimics
 * a spreadsheet of data referenced by columns, or a database table
 * indexed by columns.
 * 
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.22
 * @see jjb.toolbox.util.RecordTable
 * @see jjb.toolbox.util.AbstractRecordTable
 * @see java.sql.ResultSet
 */

package jjb.toolbox.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordTableImpl extends AbstractRecordTable {

  private final List recordTable;
  private final List columnNames;
  private final List columnAliases;

  /**
   * Default constructor used to create an instance of the RecordTableImpl
   * class to store/format records in a tabular data structure.  A table's
   * structure must be defined at creation time, therefore the caller specifies
   * the columns of the table in the columnNames parameter.
   *
   * @param columnNames is a [Ljava.lang.String array containing the names of
   * the columns for the table.
   * @throws Ljava.lang.NullPointerException if the columnNames parameter is
   * null.
   */
  public RecordTableImpl(String[] columnNames)
  {
    this(columnNames,null,INITIAL_CAPACITY);
  }

  /**
   * Creates an instance of the RecordTableImpl class to store and format
   * records in a tabular data structure.  The table's structure is specified
   * by the columnNames parameter defining the columns in the table.  The
   * columnNames represent a less than user friendly representation of the
   * column, therefore the columnAliases use descriptive name for the column.
   *
   * @param columnNames is a [Ljava.lang.String array containing the names of
   * the columns for the table.
   * @param columnAliases is a [Ljava.lang.String parameter containing the
   * user-friendly descriptive names for the columns.
   * @throws Ljava.lang.NullPointerException if the columnNames parameter is
   * null.
   */
  public RecordTableImpl(String[] columnNames,
                         String[] columnAliases)
  {
    this(columnNames,columnAliases,INITIAL_CAPACITY);
  }

  /**
   * Creates an instance of the RecordTableImpl class to store and format
   * records in a tabular data structure.  The table's structure is specified
   * by the columnNames parameter defining the columns in the table.  The
   * initial capacity of the table is determined by the initialCapacity
   * parameter, which is an integer index specifying the number of rows in
   * the table.
   *
   * @param columnNames is a [Ljava.lang.String array containing the names of
   * the columns for the table.
   * @param initialCapacity is an integer value specifying the number of
   * initial rows in the table.
   * @throws Ljava.lang.NullPointerException if the columnNames parameter is
   * null.
   * @throws Ljava.lang.IllegalArgumentException if the initialCapacity 
   * parameter is less-than or equal to zero.
   */
  public RecordTableImpl(String[] columnNames,
                         int      initialCapacity)
  {
    this(columnNames,null,initialCapacity);
  }

  /**
   * Creates an instance of the RecordTableImpl class to store and format
   * records in a tabular data structure.  The table's structure is specified
   * by the columnNames parameter defining the columns in the table.  The
   * columnNames represent a less than user friendly representation of the
   * column, therefore the columnAliases use descriptive name for the column.
   * Finally, the initial capacity of the table is determined by the
   * initialCapacity parameter, which is an integer index specifying the
   * number of rows in the table.
   *
   * @param columnNames is a [Ljava.lang.String array containing the names of
   * the columns for the table.
   * @param columnAliases is a [Ljava.lang.String parameter containing the
   * user-friendly descriptive names for the columns.
   * @param initialCapacity is an integer value specifying the number of
   * initial rows in the table.
   * @throws Ljava.lang.NullPointerException if the columnNames parameter is
   * null.
   * @throws Ljava.lang.IllegalArgumentException if the initialCapacity 
   * parameter is less-than or equal to zero.
   */
  public RecordTableImpl(String[] columnNames,
                         String[] columnAliases,
                         int      initialCapacity)
  {
    if (columnNames == null)
      throw new NullPointerException("The structure of the RecordTable must be defined, therefore columnNames must not be null.");

    if (initialCapacity <= 0)
      throw new IllegalArgumentException("The initialCapacity parameter must be greater-than zero.");

    recordTable = new ArrayList(initialCapacity);
    this.columnNames = Arrays.asList(columnNames);
    this.columnAliases = (columnAliases == null ? new ArrayList(columnNames.length) : 
                          Arrays.asList(columnAliases));
  }

  /**
   * Creates an instance of the RecordTableImpl class initialized
   * with the specified java.sql.ResultSet object.  It is assumed that
   * the caller has not called RS.next() on the ResultSet object.  By
   * default this RecordTable object is NOT read-only (the record
   * table can be modified).
   *
   * @param RS is a Ljava.sql.ResultSet object containing the database
   * data (results of a query) to initialize this newly created
   * RecordTable object with.
   * @throws Ljjb.toolbox.util.RecordException if an SQLException
   * occurs while working with the ResultSet object.
   * @throws Ljava.lang.NullPointerException if the ResultSet object
   * parameter is null.
   */
  public RecordTableImpl(ResultSet RS) throws RecordException
  {
    this(RS,false);
  }

  /**
   * Creates an instance of the RecordTableImpl class initialized
   * with the specified java.sql.ResultSet object.  It is assumed that
   * the caller has not called RS.next() on the ResultSet object.  The
   * caller may also specify whether the table is read-only or not by
   * setting the readOnly parameter appropriately.
   *
   * @param RS is a Ljava.sql.ResultSet object containing the database
   * data (results of a query) to initialize this newly created
   * @param readOnly is a boolean value indicating true if this table
   * is read-only.
   * RecordTable object with.
   * @throws Ljjb.toolbox.util.RecordException if an SQLException
   * occurs while working with the ResultSet object.
   * @throws Ljava.lang.NullPointerException if the ResultSet object
   * parameter is null.
   */
  public RecordTableImpl(ResultSet RS,
                         boolean   readOnly) throws RecordException
  {
    if (RS == null)
      throw new NullPointerException("The ResultSet object used to initialize the contents of this RecordTable object cannot be null.");

    try {
      final ResultSetMetaData metaData = RS.getMetaData();

      final int fetchSize    = RS.getFetchSize();
      final int rowCount     = (fetchSize > 0 ? (int) (fetchSize * 1.5) : INITIAL_CAPACITY);
      final int colCount     = metaData.getColumnCount();
      final int initialCapacity = (int) (colCount * 1.5 + 1);

      recordTable = new ArrayList(rowCount);
      columnNames = new ArrayList(initialCapacity);
      columnAliases = new ArrayList(initialCapacity);

      for (int index = 1, size = metaData.getColumnCount(); index <= size; index++) {
        columnNames.add(metaData.getColumnName(index));
        columnAliases.add(metaData.getColumnLabel(index));
      }

      while (RS.next()) {
        try {
          Record record = insertRow(); // questionable?

          for (int col = colCount; col-- > 0; )
            record.setValue(colCount-1,RS.getObject(col));
        }
        catch (ReadOnlyException ignore) {
        }
      }

      setReadOnly(readOnly);
    }
    catch (SQLException sqle) {
      throw new RecordException("Exception occurred creating and initializing the RecordTable object.",sqle);
    }
  }

  /**
   * Copy constructor used to create an instance of the RecordTable
   * object initialized with the contents from the RecordTable object
   * parameter.  This newly created RecordTable object assumes the
   * same structure as the RecordTable object parameter along with
   * the same properties (read-only).
   *
   * @param recordTable is a Ljjb.toolbox.util.RecordTable object
   * used to initialized the contents as well as the state of this
   * newly created RecordTable object.
   * @throws Ljava.lang.NullPointerException if the RecordTable
   * object parameter is null.
   */
  public RecordTableImpl(RecordTable recordTable)
  {
    this(recordTable,(recordTable != null ? recordTable.isReadOnly() : false));
  }

  /**
   * Copy constructor used to create an instance of the RecordTable
   * object initialized with the contents from the RecordTable object
   * parameter.  This newly created RecordTable object assumes the
   * same structure as the RecordTable object parameter along with
   * the same properties (read-only).
   *
   * @param recordTable is a Ljjb.toolbox.util.RecordTable object
   * used to initialized the contents as well as the state of this
   * newly created RecordTable object.
   * @throws Ljava.lang.NullPointerException if the RecordTable
   * object parameter is null.
   */
  public RecordTableImpl(RecordTable recordTable,
                         boolean       readOnly    )
  {
    if (recordTable == null)
      throw new NullPointerException("The RecordTable object parameter used to initialize the contents of this RecordTable object cannot be null.");

    int initRows = recordTable.rowCount();
    int initCols = recordTable.columnCount();

    initCols = (initCols == 0 ? 17 : initCols);
    this.recordTable = new ArrayList(initRows == 0 ? INITIAL_CAPACITY : (int) (initRows * 1.5));
    columnNames = new ArrayList(initCols);
    columnAliases = new ArrayList(initCols);

    try {
      for (int rowIndex = 0; rowIndex < initRows; rowIndex++)
        addRecord(recordTable.get(rowIndex));
    }
    catch (ReadOnlyException ignore) {
    }

    setReadOnly(readOnly);
  }

  /**
   * The TableRecord class is used by the RecordTableImpl class to
   * represent records, or rows in the table.
   */
  private final class TableRecord extends AbstractRecord {

    private final Object[] rowData;

    public TableRecord()
    {
      rowData = new Object[columnCount()];
    }

    public TableRecord(Record record)
    {
      final int columnCount = columnCount();

      rowData = new Object[columnCount];

      for (int index = columnCount; --index >= 0; )
        rowData[index] = record.getValue(columnNames.get(index).toString());
    }

    public void addField(String field,
                         Object defaultValue)
    {
      throw new UnsupportedOperationException("addField() is not allowed.  The structure of the table cannot be modified through the record.");
    }

    public boolean containsField(String field)
    {
      return columnNames.contains(field);
    }

    public boolean containsFields(String[] fields)
    {
      return columnNames.containsAll(Arrays.asList(fields));
    }

    public String getField(int index)
    {
      if (index < 0 || index >= columnCount())
        throw new IndexOutOfBoundsException(index+" is not a valid column index in this record table.");

      return columnNames.get(index).toString();
    }

    public Object getValue(int index)
    {
      if (index < 0 || index >= rowData.length)
        throw new IndexOutOfBoundsException(index+" is not a valid column index in this record table.");

      return rowData[index];
    }

    public Object getValue(String field)
    {
      int column = -1;

      if ((column = columnNames.indexOf(field)) == -1)
        return null;

      return getValue(column);
    }

    public boolean isReadOnly()
    {
      return RecordTableImpl.this.isReadOnly();
    }

    public Object removeField(String field)
    {
      throw new UnsupportedOperationException("removeField() is not allowed.  The structure of the table cannot be modified through the record.");
    }

    public void setReadOnly()
    {
      throw new UnsupportedOperationException("The read-only attribute must be set at the table level.  Individual record (rows) cannot be marked read-only.");
    }

    public Object setValue(int    index,
                           Object value ) throws ReadOnlyException
    {
      if (isReadOnly())
        throw new ReadOnlyException("setValue() is not allowed.  The RecordTable object is read-only.");

      if (index < 0 || index >= rowData.length)
        throw new IndexOutOfBoundsException(index+" is not a valid column index in this record table.");

      final Object oldValue = rowData[index];

      rowData[index] = value;

      return oldValue;
    }

    public Object setValue(String field,
                           Object value ) throws ReadOnlyException, NoSuchFieldException
    {
      int column = -1;

      if (field == null)
        return new NullPointerException("Record fields cannot be null.");

      if ((column = columnNames.indexOf(field)) == -1)
        throw new NoSuchFieldException("The Record object does not contain the "+field+" field.");

      return setValue(getColumnIndex(field),value);
    }

    public int size()
    {
      return rowData.length;
    }

  } // End of Class

  /**
   * addRecord adds the specified record object as the last row in
   * this record table.  Note, that when the record is added, the
   * columns from this record table are used to pull information
   * from the record to add to the table.  If the Record object
   * being added to this table contains only part or some of the
   * columns in this table, then information will be missing for
   * the columns in the table that the record does not contain.
   * The addRecord method does nothing to ensure the structure of
   * the Record object matches the table.
   *
   * Note too that it may be the case that the Record object
   * contains more, or other, fields that this record table does
   * not.  The extra information is ignored and will not be added
   * to the table.
   *
   * @param record is a Ljjb.toolbox.util.Record object parameter
   * to add as a row in this table.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * is read-only.
   */
  public void addRecord(Record record) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("addRecord() is not allowed.  The RecordTable object is read-only.");

    recordTable.add(new TableRecord(record));
  }

  /**
   * columnCount returns the number of columns (fields) in this
   * record table.
   *
   * @return an integer value specifying the number of columns in
   * this record table.
   */
  public int columnCount()
  {
    return columnNames.size();
  }

  /**
   * contains determines whether this record table object contains
   * the specified Record object parameter.  The contains method
   * carries out it's operation by iterating over all the records
   * in this record table comparing them to the Record object
   * parameter by calling the equals method.
   *
   * @param record is a reference to a Ljjb.toolbox.util.Record 
   * object in determining existence in this record table.
   * @return a boolean value of true if the Record object
   * parameter is a member of this record table, false otherewise.
   */
  public boolean contains(Record record)
  {
    return recordTable.contains(record);
  }

  /**
   * createRecordTable creates an instance of an empty RecordTable object
   * with a runtime type equal to this RecordTable object for which this
   * method was called.
   *
   * @param columnNames is a [Ljava.lang.String array containing the field
   * names of the columns in the new record table object returned from this
   * method.
   * @param columnAliases is a [Ljava.lang.String array containing the
   * descriptive column names (aliases) of the columns in the new record
   * table object returned from this method.
   * @return a Ljjb.toolbox.util.RecordTable object with the same runtime
   * type of the record table object for which this method is called.
   */
  RecordTable createRecordTable(String[] columnNames,
                                String[] columnAliases,
                                int      initialCapacity)
  {
    return new RecordTableImpl(columnNames,columnAliases,initialCapacity);
  }

  /**
   * difference performs a set difference operation by creating a new
   * RecordTable object and placing only Record objects in the new
   * record table that do NOT exists in the RecordTable object
   * parameter.
   *
   * @param RT is a Ljjb.toolbox.util.RecordTable object containing
   * Record objects to exclude in the set "difference" record table.
   */
  public RecordTable difference(RecordTable RT)
  {
    final RecordTableImpl differenceTable = new RecordTableImpl(getColumnNames(),getColumnAliases(),rowCount());

    final List records = new ArrayList(recordTable);

    records.removeAll(RT);
    differenceTable.addAll(records);

    return differenceTable;
  }

  /**
   * get returns the Record object at the specified row index in this
   * record table.
   *
   * @param index is a an integer row index into this record table
   * specifying the Record object to retrieve.
   * @return a Ljjb.toolbox.util.Record object containing the row
   * information in this record table for the specified row index.
   * @throws Ljava.lang.IndexOutOfBoundsException if the index parameter
   * is not a valid row index in this record table.
   */
  public Record get(int index)
  {
    if (index < 0 || index >= rowCount())
      throw new IndexOutOfBoundsException(index+" is not a valid row index in this record table.  A valid row index is between 0 and "+rowCount());

    return(Record) recordTable.get(index);
  }

  /**
   * getCell returns the value of the specified cell (row,col) in this
   * record table.
   *
   * @param row is an integer row index into this record table.
   * @param col is an integer column index into this record table.
   * @return a Ljava.lang.Object representation of the value at the 
   * specified cell in this record table, or null if not value exists.
   * @throws Ljava.lang.IndexOutOfBoundsException if either the row or
   * col index parameters are not valid row index and/or column indexes
   * in this record table.
   */
  public Object getCell(int row,
                        int col )
  {
    return((Record) recordTable.get(row)).getValue(col);
  }

  /**
   * getColumnAlias returns the column alias, or descriptive field name,
   * for the specified column index in this record table.
   *
   * @param index is an integer column index into this record table.
   * @return a Ljava.lang.String object containing the alias, or
   * descriptive field name, of the column referenced by index in this
   * record table.
   * @throws Ljava.lang.IndexOutOfBoundsException if the index parameter
   * is not a valid column in this record table.
   */
  public String getColumnAlias(int index)
  {
    if (index < 0 || index >= columnCount())
      throw new IndexOutOfBoundsException(index+" is not a valid column in this record table.  A valid column index is between 0 and "+columnCount());

    return columnNames.get(index).toString();
  }

  /**
   * getColumnAliases returns a String array containing all the
   * aliases or descriptive field names of the columns in this
   * record table object.
   *
   * @return a [Ljava.lang.String array containing all the aliases
   * for the columns in this record table.
   */
  public String[] getColumnAliases()
  {
    return(String[]) columnAliases.toArray(new String[columnAliases.size()]);
  }

  /**
   * getColumnIndex returns the column index of the column that is
   * referred to by the column String parameter.  The column String
   * parameter can either be the column's official field name or the
   * descriptive name (alias).
   *
   * @param column is a Ljava.lang.String object specifying the column,
   * either by field name or alias, for which the index should be
   * returned, or a -1 if the column does not exists.
   * @return an integer column index of the column referred to by the
   * column parameter.
   */
  public int getColumnIndex(String column)
  {
    if (column == null)
      return -1;

    int index = columnNames.indexOf(column);

    if (index == -1)
      return columnAliases.indexOf(column);
    else
      return index;
  }

  /**
   * getColumnName returns the column field name for the column indexed
   * in this record table by the index parameter.
   *
   * @param index is an integer column index in this record table of
   * the column for which the field name should be returned.
   * @return a Ljava.lang.String object containing the field name of
   * the column at index in this record table. 
   * @throws Ljava.lang.IndexOutOfBoundsException if the index parameter
   * is not a valid column in this record table.
   */
  public String getColumnName(int index)
  {
    if (index < 0 || index >= columnCount())
      throw new IndexOutOfBoundsException(index+" is not a valid column in this record table.  A valid column index is between 0 and "+columnCount());

    return columnNames.get(index).toString();
  }

  /**
   * getColumnNames returns a String array containing all the field
   * names of the columns in this record table.
   *
   * @return a [Ljava.lang.String array containing the field names
   * of the columns in this record table.
   */
  public String[] getColumnNames()
  {
    return(String[]) columnNames.toArray(new String[columnNames.size()]);
  }

  /**
   * indexOf returns the row index in this record table of the
   * Record object parameter or a -1 if the Record object
   * parameter does not exists.
   *
   * @param record is a Ljjb.toolbox.util.Record object used
   * in determining a row index in this record table, if the
   * record exists.
   * @return a row index in this record table if the Record
   * object exists, or a -1 if the Record object does not
   * exists.
   */
  public int indexOf(Record record)
  {
    return recordTable.indexOf(record);
  }

  /**
   * insert inserts the specified Record object parameter
   * into this record table at the specified index.  All
   * subsequent rows (records) in this record table are shifted
   * down one row.
   *
   * @param record is a Ljjb.toolbox.util.Record object to
   * insert into this record table.
   * @param index is an integer row index specifying where in
   * the rows of this record table to place the record.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * is read-only.
   * @throws Ljava.lang.IndexOutOfBoundsException if the integer
   * index parameter is not a valid row index in this table.
   */
  public void insert(Record record,
                     int      index  ) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("insert() is not allowed.  The record table is read-only.");

    recordTable.add(index,new TableRecord(record));
  }

  /**
   * insertRow inserts a row at the end of the record table and
   * returns a Record container object to populate the columns
   * of the row.
   *
   * @return a Ljjb.toolbox.util.Record object container to
   * populate the columns of the newly inserted row of the record
   * table.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * is read-only.
   */
  public Record insertRow() throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("insert() is not allowed.  The record table is read-only.");

    final Record record = new TableRecord();

    recordTable.add(record);

    return record;
  }

  /**
   * insertRow inserts a row at the specified row index in this
   * record table and returns a Record container object to
   * populate the columns of the row.
   *
   * @param index is an integer row index of where the new row
   * should be created.
   * @return a Ljjb.toolbox.util.Record object container to
   * populate the columns of the newly inserted row of the record
   * table.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * is read-only.
   */
  public Record insertRow(int index) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("insert() is not allowed.  The record table is read-only.");

    if (index < 0 || index >= rowCount())
      throw new IndexOutOfBoundsException(index+" is not a valid row index in this record table.  The row index must be between 0 and "+rowCount());

    final Record record = new TableRecord();

    recordTable.add(index,record);

    return record;
  }

  /**
   * intersection performs a set intersection operation on this record
   * table.  The method creates a new record table object containing
   * the result for which it adds Record objects from this table that
   * also exists in the record table parameter.
   *
   * @param recordTable is a Ljjb.toolbox.util.RecordTable object
   * used in determining the common record object between itself and
   * this record table object.
   * @return a Ljjb.toolbox.util.RecordTable object containing the
   * intersection (common records) of this and the record table
   * parameter.
   */
  public RecordTable intersection(RecordTable RT)
  {
    final RecordTableImpl intersectionTable = new RecordTableImpl(getColumnNames(),getColumnAliases(),rowCount());

    final List records = new ArrayList(recordTable);

    records.retainAll(RT);
    intersectionTable.addAll(records);

    return intersectionTable;
  }

  /**
   * remove deletes the row (record) at index from this record
   * table object.
   *
   * @param index is an integer value specifying the index of
   * a row in this record table to remove.
   * @return a boolean value of true if the row (record) was
   * successfully removed, false otherwise.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * is read-only.
   * @throws Ljava.lang.IndexOutOfBoundsException if the integer
   * index parameter is not a valid row index in this table.
   */
  public boolean remove(int index) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("remove() is not allowed.  The record table is read-only.");

    if (index < 0 || index >= rowCount())
      throw new IndexOutOfBoundsException(index+" is not a valid row index in this record table.  The row index must be between 0 and "+rowCount());

    return(recordTable.remove(index) != null);
  }

  /**
   * remove deletes the specified Record object from this table.
   * This method works by iterating over the rows (records) in the
   * table and comparing them with the Record object parameter
   * using the equals method.  If the record is found in this table
   * it is remove and a true boolean literal value is returned,
   * otherwise false is returned.
   *
   * @param record is a Ljjb.toolbox.util.Record object to remove
   * from this record table if it exists.
   * @return a boolean value of true if the record was found and
   * removed successfully, false otherwise.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * is read-only.
   */
  public boolean remove(Record record) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("remove() is not allowed.  The record table is read-only.");

    return recordTable.remove(record);
  }

  /**
   * removeAll removes all records (rows) from this table if the
   * record table is not read-only.
   *
   * @return a boolean value of true if the operation was successful,
   * false otherwise.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * is read-only.
   */
  public boolean removeAll() throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("removeAll() is not allowed.  The record table is read-only.");

    final int oldSize = rowCount();

    recordTable.clear();

    return(oldSize != rowCount());
  }

  /**
   * rowCount returns the number of rows (records) in the table
   * as a integer value.
   *
   * @return an integer value specifying the number of rows in
   * the table.
   */
  public int rowCount()
  {
    return recordTable.size();
  }

  /**
   * setColumnAlias set the column at index with the specified
   * alias, or descriptive field name.
   *
   * Note that column aliases can be set regardless of whether the
   * record table is read-only or not.
   *
   * @param index is an integer column index into this record 
   * table for which the alias is being set.
   * @parma alias is a Ljava.lang.String object containing the
   * alias to set on the column specified by index in this
   * record table.
   * @throws Ljava.lang.IndexOutOfBoundsException if the integer
   * index parameter is not a valid column index in this table.
   */
  public void setColumnAlias(int    index,
                             String alias )
  {
    if (index < 0 || index >= columnCount())
      throw new IndexOutOfBoundsException(index+" is not a valid column index in this record table.");

    columnAliases.set(index,alias);
  }

  /**
   * setColumnName sets the specified column at column index
   * in this record table to have the specified field name.
   *
   * @param index is an integer column index in this record
   * table for the column whose field name is being set.
   * @param name is a Ljava.lang.String object containing the
   * field name of the column.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * is read-only.
   * @throws Ljava.lang.IndexOutOfBoundsException if the integer
   * index parameter is not a valid column index in this table.
   * @throws Ljava.lang.NullPointerException if the specified name
   * for the column in this record table is null.
   */
  public void setColumnName(int    index,
                            String name  ) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("setColumnName() is not allowed.  The record table is read-only.");

    if (index < 0 || index >= columnCount())
      throw new IndexOutOfBoundsException(index+" is not a valid column index in this record table.");

    if (name == null)
      throw new NullPointerException("The field name for a column in this record table cannont be null.");

    columnNames.set(index,name);
  }

  /**
   * union performs a set union operation on this table to
   * along with the RecordTable object parameter to produce
   * a new RecordTable object containing the result.  This 
   * method will effectively remove any duplicates record as
   * a result of overlap in the tables.
   *
   * @param RT is a Ljjb.toolbox.util.RecordTable object
   * containing Record objects to exclude in the set
   * "difference" record table.
   * @return a Ljjb.toolbox.util.RecordTable object containing
   * the union of records between this record table and that of
   * the parameter.
   */
  public RecordTable union(RecordTable RT)
  {
    final RecordTableImpl unionTable = new RecordTableImpl(getColumnNames(),getColumnAliases(),rowCount());

    final List records = new ArrayList(recordTable);

    RT.removeAll(this);
    records.addAll(RT);
    unionTable.addAll(records);

    return unionTable;
  }

}

