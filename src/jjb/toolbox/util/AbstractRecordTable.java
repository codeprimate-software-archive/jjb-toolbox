/**
 * AbstractRecordTable.java (c) 2002.4.17
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * The record table data structure mimics a spreadsheet of data
 * referenced by columns, or a database table indexed by columns.
 *
 * @author John J. Blum
 * @version 2003.2.22
 * @see jjb.toolbox.util.RecordTable
 * @see jjb.toolbox.util.RecordTableImpl
 */

package jjb.toolbox.util;

import java.awt.Dimension;
import java.sql.ResultSetMetaData;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractRecordTable extends AbstractCollection
    implements RecordTable {

  protected static final int INITIAL_CAPACITY = 771;

  private boolean  readOnly;

  protected int    modCount;

  /**
   * Create a new instance of the RecordTable class to store/display
   * records in a table data structure.
   */
  public AbstractRecordTable()
  {
    readOnly = false;
    modCount = 0;
  }

  /**
   * Ensures that this collection contains the specified element
   * (optional operation). Returns true if the collection changed as a
   * result of the call. (Returns false if this collection does not
   * permit duplicates and already contains the specified element.)
   * Collections that support this operation may place limitations on
   * what elements may be added to the collection. In particular, some
   * collections will refuse to add null elements, and others will
   * impose restrictions on the type of elements that may be added.
   * Collection classes should clearly specify in their documentation
   * any restrictions on what elements may be added.
   *
   * @param o is a Ljava.lang.Object whose presence in this collection
   * is to be ensured.
   * @throws L.java.lang.UnsupportedOperationException if add is not
   * supported by this collection.
   * @throws Ljava.lang.ClassCastException if the class of the
   * specified element prevents it from being added to this collection.
   * @throws Ljava.lang.IllegalArgumentException if some aspect of this
   * element prevents it from being added to this collection.
   */
  public boolean add(Object o)
  {
    try {
      if (o == null)
        throw new NullPointerException("The Record object parameter cannot be null when adding it to the record table.");

      if (!(o instanceof Record))
        throw new IllegalArgumentException(o.getClass().getName()+" is not a valid record type to add to this record table.");

      final Record rec = (Record) o;

      addRecord(rec);

      return true;
    }
    catch (ReadOnlyException roe) {
      throw new UnsupportedOperationException("add() is not allowed.  The RecordTable object is read-only.");
    }
  }

  /**
   * addAll adds all the Record objects contained in the RecordTable
   * object parameter to this RecordTable object.
   *
   * @param recordTable is a Ljjb.toolbox.util.RecordTable object
   * containing the records to append to the end of this record table.
   * @throws Ljjb.toolbox.util.ReadOnlyException if this record table
   * is read-only.
   */
  public void addAll(RecordTable recordTable) throws ReadOnlyException
  {
    for (int index = 0, size = recordTable.size(); index < size; index++)
      addRecord(recordTable.get(index));
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
  abstract RecordTable createRecordTable(String[] columnNames,
                                         String[] columnAliases,
                                         int      initialCapacity);

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
    final RecordTable differenceTable = createRecordTable(RT.getColumnNames(),RT.getColumnAliases(),RT.size());

    Record record = null;

    for (int index = 0, size = size(); index < size; index++) {
      record = get(index);

      try {
        if (!RT.contains(record))
          differenceTable.addRecord(record);
      }
      catch (ReadOnlyException ignore) {
      }
    }

    return differenceTable;
  }

  /**
   * dimension returns the dimension of the record table as a Dimension
   * object, containing both the width and height of the record table.
   *
   * @return a Ljava.awt.Dimension object specifying the width (number
   * of columns) and height (number of rows) in the record table.
   */
  public Dimension dimension()
  {
    return new Dimension(rowCount(),columnCount());
  }

  /**
   * get returns a Record object (row) from this table which has
   * the specified key field value.  Note, this method does it's work
   * by calling the indexOf(String keyFieldValue) to return the 
   * row index of the record having the key field value and then calls
   * the get(int index) method to return the Record object.
   *
   * @param keyFieldValue is a Ljava.lang.Object containing the value
   * of the key field that a record in this record table must have.
   * @return a Ljjb.toolbox.util.Record object representation of
   * the row in the record table containing the specified key field
   * value.
   */
  public Record get(Object keyFieldValue)
  {
    final int index = indexOf(keyFieldValue);

    if (index == -1)
      return null;

    return get(index);
  }

  /**
   * getColumnAlias returns the descriptive name of the column for the
   * specified field name of the column in the record table.  This
   * method performs by calling the getColumnIndex method to return
   * field name index (column) in this record table.  If a valid index
   * is returned, the method proceeds by calling the
   * getColumnAlias(int index) method to return the column's alias.
   * If the column alias is passed to this method, the column alias
   * is returned.
   *
   * @param name is a Ljava.lang.String representation of the field
   * name for the column in this record table.
   * @return a Ljava.lang.String representation of the columns descriptive
   * name.
   * @throws Ljava.lang.NoSuchFieldException if the name parameter is
   * not a valid field (column) in this record table.
   */
  public String getColumnAlias(String name) throws NoSuchFieldException
  {
    final int index = getColumnIndex(name);

    if (index == -1)
      throw new NoSuchFieldException(name+" is not a valid column in this record table.");

    return getColumnAlias(index);
  }

  /**
   * getColumnName returns the field name (column) of the record table
   * having the specified alias.  If the alias is null or not mapped
   * to a field in this table, than a null value is returned.  This
   * method functions by first calling the getColumnIndex method to
   * return the alias's column index in this table.  If a valid index
   * is returned, the method proceeds by calling the
   * getColumnName(int index) method to return the field name of the
   * column in this record table.
   *
   * @param alias is a Ljava.lang.String representation of the alias
   * descriptive name for the column whose field name should be 
   * returned by this method.
   * @return a Ljava.lang.String representation of the field name
   * for the column with the given alias.
   */
  public String getColumnName(String alias)
  {
    final int index = getColumnIndex(alias);

    if (index == -1)
      return null;

    return getColumnName(index);
  }

  /**
   * getMetaData retrieves the number, types and properties of this
   * RecordTable object's columns.
   *
   * @return a Ljava.sql.ResultSetMetaData object containing the
   * description of this RecordTable object's columns.
   * @throws Ljava.lang.UnsupportedOperationException if the
   * particular RecordTable object does not implmentent this
   * method.
   */
  public ResultSetMetaData getMetaData()
  {
    throw new UnsupportedOperationException("getMetaData() is not implemented in this RecordTable object.");
  }

  /**
   * indexOf returns an integer index of the row (Record object)
   * in this record table with the specified key field value.
   *
   * @param keyFieldValue is a Ljava.lang.String representation of 
   * the key field value that a row in the record table must have
   * in order for a row index to be returned.
   * @return an integer index of the row (Record object) that
   * has the specified key field value.
   */
  public int indexOf(Object keyFieldValue)
  {
    Record record = null;

    for (int index = rowCount(); --index >= 0; ) {
      record = get(index);

      if (record.getValue(record.getKeyField()).equals(keyFieldValue))
        return index;
    }

    return -1;
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
  public abstract int indexOf(Record record);

  /**
   * insertCol inserts the specified column as the last column in this
   * record table, initializing all field values in the individual
   * records (rows) to null values for this column.
   * (optional operation)
   *
   * @param column is a Ljava.lang.String field name of the column to
   * add as the last column of this record table.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * is read-only.
   * @throws Ljava.lang.UnsupportedOperationException if the
   * implementation does not provide an implementation (support) for
   * this method.
   */
  public void insertCol(String column) throws ReadOnlyException
  {
    throw new UnsupportedOperationException("insertCol() is not supported by the "+getClass().getName()+" implementation.");
  }

  /**
   * insertCol inserts the specified column at the specified index
   * in this record table, initializing all field values in the
   * individual records (rows) to null values for this column.
   * (optional operation)
   *
   * @param column is a Ljava.lang.String field name of the column
   * to add as the last column of this record table.
   * @param index is an integer column index of where the column
   * will be placed in this record table.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record
   * table is read-only.
   * @throws Ljava.lang.IndexOutOfBoundsException if the index
   * parameter is not a valid column index in this record table.
   * @throws Ljava.lang.UnsupportedOperationException if the
   * implementation does not provide an implementation (support)
   * for this method.
   */
  public void insertCol(String column,
                        int    index  ) throws ReadOnlyException
  {
    throw new UnsupportedOperationException("insertCol() is not supported by the "+getClass().getName()+" implementation.");
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
    final RecordTable intersectionTable = createRecordTable(RT.getColumnNames(),RT.getColumnAliases(),RT.size());

    Record record = null;

    for (int index = 0, size = size(); index < size; index++) {
      record = get(index);

      try {
        if (RT.contains(record))
          intersectionTable.addRecord(record);
      }
      catch (ReadOnlyException roe) {
      }
    }

    return intersectionTable;
  }

  /**
   * isReadOnly returns whether the contents of this RecordTable
   * object can be modified or not (is read-only).
   *
   * @return a boolean value of true if the record table cannot be
   * modified, false otherwise.
   */
  public boolean isReadOnly()
  {
    return readOnly;
  }

  /**
   * iterator returns an iterator over the elements in this collection.
   * There are no guarantees concerning the order in which the elements
   * are returned (unless this collection is an instance of some class
   * that provides a guarantee).
   *
   * @return an Iterator over the elements in this collection.
   */
  public Iterator iterator()
  {
    return new Iterator()
    {

      private boolean    lastReturned = false;

      private int        index            = 0;
      private final int  size             = size();
      private int        expectedModCount = 0;

      public boolean hasNext()
      {
        return(index < size);
      }

      public Object next()
      {
        try {
          return get(index++);
        }
        catch (IndexOutOfBoundsException ignore) {
          lastReturned = false;
          throw new NoSuchElementException("No more records to traverse.");
        }
      }

      public void remove()
      {
        if (!lastReturned)
          throw new IllegalStateException("Unable to remove record.");

        if (expectedModCount != modCount)
          throw new ConcurrentModificationException("The record table has been modified independently of this iterator.");

        try {
          AbstractRecordTable.this.remove(index-1);
          expectedModCount++;
        }
        catch (ReadOnlyException roe) {
          throw new UnsupportedOperationException(roe.getMessage());
        }
      }

    };
  }

  /**
   * _remove removes the row (record) in this record table having the
   * specified key field value for the registered key field of the
   * record.  This method functions by first calling the indexOf()
   * method on the keyFieldValue to return a row in the record table
   * whose key field has the specified value.  The method then
   * proceeds by calling the remove(int index) method to remove the
   * targeted row (record).
   *
   * @param keyFieldValue is a Ljava.lang.Object value containing
   * the key field value that the particular row (or record) to be
   * removed must have.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * cannot be modified, is read-only.
   */
  public boolean _remove(Object keyFieldValue) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("remove() is not allowed.  The record table is read-only.");

    int index = indexOf(keyFieldValue);

    if (index == -1)
      return false;

    return remove(index);
  }

  /**
   * remove removes the specified Record object (row) from this record
   * table.
   *
   * @param record is a Ljjb.toolbox.util.Record object referencing the
   * row/record to remove from this table.
   * @return a true boolean value to indicated the record/row was removed
   * successfully, false otherwise.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * cannot be modified, is read-only.
   */
  public boolean remove(Record record) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("remove() is not allowed.  The record table is read-only.");

    final int index = indexOf(record);

    if (index == -1)
      return false;

    return remove(index);
  }

  /**
   * removeAll clears all records (rows) from this record table object.
   *
   * @return a boolean value of true if the record table has been cleared
   * successfully, false otherwise.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * cannot be modified, is read-only.
   */
  public boolean removeAll() throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("removeAll() is not allowed.  The record table is read-only.");

    final int oldSize = size();

    clear();

    return(oldSize != size());
  }

  /**
   * setCell sets the individual table cell to the specified value in
   * this record table.
   *
   * @param row is an integer index into the rows of this table.
   * @param col is an integer index into the columns of this table.
   * @param value is an Ljava.lang.Object containing the value to
   * set the value of the cell to.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * cannot be modified, is read-only.
   * @throws Ljava.lang.IndexOutOfBoundsException if the row and/or
   * col indexes are invalid.
   */
  public void setCell(int    row,
                      int    col,
                      Object value) throws ReadOnlyException
  {
    if (isReadOnly())
      throw new ReadOnlyException("setCell() is not allowed.  The record table is read-only.");

    ((Record) get(row)).setValue(col,value);
  }

  /**
   * setColumnAlias sets the column referenced by the column name
   * in this record table with the specified alias.  The method
   * operates by calling the getColumnIndex(String column) on the
   * column name and then calling the setColumnAlias(int index,
   * String alias) with the non-negative index.  If the index is
   * negative, then the field name does not exist and this method
   * throws a NoSuchFieldException.
   *
   * Note that column aliases can be set regardless of whether the
   * record table is read-only or not.
   *
   * @param name is a Ljava.lang.String object specifying the field
   * name in this record table.
   * @param alias is a Ljava.lang.String object specifying the alias
   * or descriptive name to assign to the column referenced by the
   * field name.
   * @throws Ljava.lang.NoSuchFieldException if the name parameter is
   * not a valid field (column) in this record table.
   */
  public void setColumnAlias(String name,
                             String alias) throws NoSuchFieldException
  {
    final int index = getColumnIndex(name);

    if (index == -1)
      throw new NoSuchFieldException(name+" is not a valid column in this record table.");

    setColumnAlias(index,alias);
  }

  /**
   * setColumnName sets the specified column referenced by the
   * column alias in this record table to have the specified
   * field name.  This method operates by calling the
   * getColumnIndex(String column) on the column alias and then
   * calling the setColumnName(int index, String alias) with
   * the non-negative index.  If the index is negative, then the
   * method returns and does nothing.
   *
   * @param alias is a Ljava.lang.String object containing the
   * column alias for the column in which to set the field name
   * for.
   * @param name is a Ljava.lang.String object containing the
   * field name of the column.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * is read-only.
   * @throws Ljava.lang.NullPointerException if the specified name
   * for the column in this record table is null.
   */
  public void setColumnName(String alias,
                            String name  ) throws ReadOnlyException
  {
    final int index = getColumnIndex(alias);

    if (index == -1)
      return;

    setColumnName(index,name);
  }

  /**
   * setReadOnly determines whether or not this record table can
   * be modified.
   *
   * @param readOnly is a boolean value indicating true if this
   * record table cannot be modified, false otherwise.
   * @throws Ljava.lang.UnsupportedOperationException if the setReadOnly
   * operation is not allowed or unsuppored.
   */
  public void setReadOnly(boolean readOnly)
  {
    this.readOnly = readOnly;
  }

  /**
   * size returns the number of row (records) in this record table.
   * size is implemented by calling the rowCount method.
   *
   * @return an integer value indicating the number of rows (or
   * records) in this RecordTable object.
   */
  public int size()
  {
    return rowCount();
  }

  /**
   * subTable constructs a subtable from this record table object
   * begining with the specified cell, indexed by the row and col
   * integer index parameters specifying the cell, and spanning
   * for the width number of columns and height number of rows.
   * If the row + height parameter is greater than rowCount(),
   * than rowCount() is considered the upperbound on the rows
   * traversed to populate the new subtable.  If col + width is
   * greater than colCount(), than colCount() is considered the
   * upperbound on the columns traversed to populate the new
   * subtable.  Not IndexOutOfBoundsException will be thrown.
   *
   * @param row is an integer index specifying the row in this
   * record table that the subtable will begin at.
   * @param col is an integer index specifying the column in
   * this record table that the subtable will begin at.
   * @param width is an integer value specifying the number of
   * columns in the new subtable.
   * @param height is an integer value specifying the number of
   * rows in the new subtable.
   * @return a Ljjb.toolbox.util.RecordTable object containing
   * the subtable from the original record table with width
   * number of columns and height number of rows begin at the
   * cell indexed at (row, col).
   */
  public RecordTable subTable(int row,
                              int col,
                              int width,
                              int height)
  {
    final int rowSize = Math.min(row+height,rowCount());
    final int colSize = Math.min(col+width,columnCount());

    final RecordTable subTbl = createRecordTable(getColumnNames(),getColumnAliases(),(int) (rowSize * 1.5));

    for (int rowIndex = row; rowIndex < rowSize; rowIndex++) {
      for (int colIndex = col; colIndex < colSize; colIndex++) {
        try {
          subTbl.setCell((rowIndex - row),(colIndex - col),getCell(row,col));
        }
        catch (ReadOnlyException ignore) {
        }
      }
    }

    return subTbl;
  }

  /**
   * subTable constructs a subtable from this record table object
   * begining with the specified cell, indexed by the row and col
   * integer index parameters specifying the cell, and spanning 
   * this record table with the width and height attributes of the
   * Dimension object.  Note that this method operates by calling
   * the subTable(int row, int col, int width, int height) method
   * with the respective values.
   *
   * @param row is an integer index specifying the row in this
   * record table that the subtable will begin at.
   * @param col is an integer index specifying the column in
   * this record table that the subtable will begin at.
   * @param width is an integer value specifying the number of
   * columns in the new subtable.
   * @param dim is a Ljava.awt.Dimension object containing the
   * dimensions of the new subtable, where width specifies the
   * number of columns and height specifies the number of rows.
   * @return a Ljjb.toolbox.util.RecordTable object containing
   * the subtable from the original record table with the specified
   * Dimension that begins at the cell indexed at (row, col).
   */
  public RecordTable subTable(int        row,
                              int        col,
                              Dimension  dim)
  {
    return subTable(row,col,dim.width,dim.height);
  }

  /**
   * subTable constructs a subtable containing the specified rows
   * and columns from this record table object.
   *
   * @param rows is an integer array containing row indexes into
   * this record table object.
   * @param cols is an integer array containging column indexes
   * into this record table object.
   * @retuns a Ljjb.toolbox.util.RecordTable object containing
   * the specified rows and columns from this original record
   * table object.
   * @throws Ljava.lang.IndexOutOfBoundsException if any index
   * in either the rows or columns integer arrays is not a valid
   * row or column index in this record table object.
   */
  public RecordTable subTable(int[] rows,
                              int[] cols )
  {
    final RecordTable subTbl = createRecordTable(getColumnNames(),getColumnAliases(),(int) (rows.length * 1.5));

    for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
      for (int colIndex = 0; colIndex < cols.length; colIndex++) {
        try {
          subTbl.setCell(rowIndex,colIndex,getCell(rows[rowIndex],cols[colIndex]));
        }
        catch (ReadOnlyException ignore) {
        }
      }
    }

    return subTbl;
  }

  /**
   * toString returns a String representation of the RecordTable
   * object and it's contents.
   *
   * @return a Ljava.lang.String representatio of this RecordTable
   * object.
   */
  public String toString()
  {
    final StringBuffer buffer = new StringBuffer();

    buffer.append("{");

    for (final Iterator i = iterator(); i.hasNext(); ) {
      buffer.append(i.next().toString());

      if (i.hasNext())
        buffer.append(",");
    }

    buffer.append("}");

    return buffer.toString();
  }

  /**
   * toTabular creates a two-dimensional object array of exact
   * dimension to the record table containing all values from
   * the record table
   *
   * @return a [[Ljava.lang.Object array containing the values
   * in this record table.
   */
  public Object[][] toTabular()
  {
    final int rows = rowCount();
    final int cols = columnCount();

    final Object[][] table = new Object[rows][cols];

    for (int rowIndex = rows; --rowIndex >= 0; ) {
      for (int colIndex = cols; --colIndex >= 0; )
        table[rowIndex][colIndex] = getCell(rowIndex,colIndex);
    }

    return table;
  }

  /**
   * toTabular creates a two-dimensional object array with the
   * specified fields (columns) from the record table pulling
   * all values from the record table for those columns.
   *
   * @param fieldSet is a [Ljava.lang.String array specifying
   * the fields (columns) from the record table to retrieve
   * values for the two-dimensional Object array.
   * @return a [[Ljava.lang.Object array containing the values
   * in this record table.
   */
  public Object[][] toTabular(String[] fieldSet)
  {
    final int rows = rowCount();

    final Object[][] table = new Object[rows][fieldSet.length];

    Record record = null;

    for (int rowIndex = rows; --rowIndex >= 0; ) {
      record = get(rowIndex);

      for (int colIndex = fieldSet.length; --colIndex >= 0; )
        table[rowIndex][colIndex] = record.getValue(fieldSet[colIndex]);
    }

    return table;
  }

  /**
   * union performs a set union operation on this table to
   * along with the RecordTable object parameter to produce
   * a new RecordTable object containing the result.  This 
   * method will effectively remove any duplicates record as
   * a result of overlap in the tables.  The union method
   * does it's work first by adding all the records from the
   * RT RecordTable parameter, then performing a difference
   * between this record table and that of the parameter and
   * adding the result from the difference to the result.
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
    final RecordTable unionTable = createRecordTable(RT.getColumnNames(),RT.getColumnAliases(),RT.size());

    try {
      unionTable.addAll(RT);
      unionTable.addAll(difference(RT));
    }
    catch (ReadOnlyException roe) {
    }

    return unionTable;
  }

}

