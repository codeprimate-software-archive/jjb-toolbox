/**
 * RecordTable.java (c) 2002.5.12
 *
 * The record table data structure mimics a spreadsheet of data
 * referenced by columns, or a database table indexed by columns.
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.22
 * @see jjb.toolbox.util.AbstractRecordTable
 * @see jjb.toolbox.util.Record
 * @see jjb.toolbox.util.RecordTableImpl
 * @see jjb.toolbox.lang.Searchable
 * @see jjb.toolbox.lang.Sortable
 * @see java.io.Serializable
 * @see java.util.Collection
 */

package jjb.toolbox.util;

import java.awt.Dimension;
import java.sql.ResultSetMetaData;
import java.util.Collection;

import jjb.toolbox.lang.Searchable;
import jjb.toolbox.lang.Sortable;

public interface RecordTable extends Collection {//, Searchable, Serializable, Sortable

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
  public void addRecord(Record record) throws ReadOnlyException;

  /**
   * addAll adds all the Record objects contained in the RecordTable
   * object parameter to this RecordTable object.
   *
   * @param recordTable is a Ljjb.toolbox.util.RecordTable object
   * containing the records to append to the end of this record table.
   */
  public void addAll(RecordTable recordTable) throws ReadOnlyException;

  /**
   * columnCount returns the number of columns (fields) in this
   * record table.
   *
   * @return an integer value specifying the number of columns in
   * this record table.
   */
  public int columnCount();

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
  public boolean contains(Record record);

  /**
   * difference performs a set difference operation by creating a new
   * RecordTable object and placing only Record objects in the new
   * record table that do NOT exists in the RecordTable object
   * parameter.
   *
   * @param RT is a Ljjb.toolbox.util.RecordTable object containing
   * Record objects to exclude in the set "difference" record table.
   */
  public RecordTable difference(RecordTable recordTable);

  /**
   * dimension returns the dimension of the record table as a Dimension
   * object, containing both the width and height of the record table.
   *
   * @return a Ljava.awt.Dimension object specifying the width (number
   * of columns) and height (number of rows) in the record table.
   */
  public Dimension dimension();

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
  public Record get(int index);

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
  public Record get(Object keyFieldValue);

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
                        int col );

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
  public String getColumnAlias(int index);

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
  public String getColumnAlias(String name) throws NoSuchFieldException;

  /**
   * getColumnAliases returns a String array containing all the
   * aliases or descriptive field names of the columns in this
   * record table object.
   *
   * @return a [Ljava.lang.String array containing all the aliases
   * for the columns in this record table.
   */
  public String[] getColumnAliases();

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
  public int getColumnIndex(String column);

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
  public String getColumnName(int index);

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
  public String getColumnName(String alias);

  /**
   * getColumnNames returns a String array containing all the field
   * names of the columns in this record table.
   *
   * @return a [Ljava.lang.String array containing the field names
   * of the columns in this record table.
   */
  public String[] getColumnNames();

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
  public ResultSetMetaData getMetaData();

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
  public int indexOf(Object keyFieldValue);

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
  public int indexOf(Record record);

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
                     int      index  ) throws ReadOnlyException;

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
  public void insertCol(String column) throws ReadOnlyException;

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
                        int    index  ) throws ReadOnlyException;

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
  public Record insertRow() throws ReadOnlyException;

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
  public Record insertRow(int index) throws ReadOnlyException;

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
  public RecordTable intersection(RecordTable recordTable);

  /**
   * isReadOnly returns whether the contents of this RecordTable
   * object can be modified or not (is read-only).
   *
   * @return a boolean value of true if the record table cannot be
   * modified, false otherwise.
   */
  public boolean isReadOnly();

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
  public boolean remove(int index) throws ReadOnlyException;

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
  public boolean _remove(Object keyFieldValue) throws ReadOnlyException;

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
  public boolean remove(Record record) throws ReadOnlyException;

  /**
   * removeAll clears all records (rows) from this record table object.
   *
   * @return a boolean value of true if the record table has been cleared
   * successfully, false otherwise.
   * @throws Ljjb.toolbox.util.ReadOnlyException if the record table
   * cannot be modified, is read-only.
   */
  public boolean removeAll() throws ReadOnlyException;

  /**
   * rowCount returns the number of rows (records) in the table
   * as a integer value.
   *
   * @return an integer value specifying the number of rows in
   * the table.
   */
  public int rowCount();

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
                      Object value) throws ReadOnlyException;

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
                             String alias );

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
                             String alias) throws NoSuchFieldException;

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
                            String name  ) throws ReadOnlyException;

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
                            String name  ) throws ReadOnlyException;

  /**
   * setReadOnly determines whether or not this record table can
   * be modified.
   *
   * @param readOnly is a boolean value indicating true if this
   * record table cannot be modified, false otherwise.
   * @throws Ljava.lang.UnsupportedOperationException if the setReadOnly
   * operation is not allowed or unsuppored.
   */
  public void setReadOnly(boolean readOnly);

  /**
   * size returns the number of row (records) in this record table.
   * size is implemented by calling the rowCount method.
   *
   * @return an integer value indicating the number of rows (or
   * records) in this RecordTable object.
   */
  public int size();

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
                                int height);

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
                                Dimension  dim);

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
                                int[] cols );

  /**
   * toTabular creates a two-dimensional object array of exact
   * dimension to the record table containing all values from
   * the record table
   *
   * @return a [[Ljava.lang.Object array containing the values
   * in this record table.
   */
  public Object[][] toTabular();

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
  public Object[][] toTabular(String[] fieldSet);

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
  public RecordTable union(RecordTable recordTable);

}

