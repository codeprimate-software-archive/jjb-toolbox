/**
 * MetaDataRecordTable.java (c) 2002.4.17
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.2.22
 * @see jjb.toolbox.util.RecordTable
 * @see jjb.toolbox.util.AbstractRecordTable
 * @see jjb.toolbox.util.RecordTableImpl
 */

package jjb.toolbox.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MetaDataRecordTable extends RecordTableImpl {

  private final boolean[] autoIncrement;
  private final boolean[] caseSensitive;
  private final boolean[] currency;
  private final boolean[] definitelyWritable;
  private final boolean[] _readOnly;
  private final boolean[] searchable;
  private final boolean[] signed;
  private final boolean[] writable;

  private final int[] columnDisplaySize;
  private final int[] columnType;
  private final int[] nullable;
  private final int[] precision;
  private final int[] scale;

  private final String[] catalogName;
  private final String[] columnClassName;
  private final String[] columnTypeName;
  private final String[] schemaName;
  private final String[] tableName;

  private ResultSetMetaData  metaData;

  /**
   * Creates an instance of the DetailedRecordTable class
   * initialized with the contents of the given ResultSet
   * object and as a modifiable data structure.  This 
   * record table class also stores meta-data information
   * about the columns of this record table.
   *
   * @param RS is a Ljava.sql.ResultSet object containing
   * the content to initialize this RecordTable object
   * with.
   */
  public MetaDataRecordTable(ResultSet RS) throws RecordException {
    this(RS,false);
  }

  /**
   * Creates an instance of the DetailedRecordTable class
   * initialized with the contents of the given ResultSet
   * object.  The readOnly attribute determines whether
   * this record table can be modified or not.  This 
   * record table class also stores meta-data information
   * about the columns of this record table.
   *
   * @param RS is a Ljava.sql.ResultSet object containing
   * the content to initialize this RecordTable object
   * with.
   * @param readOnly is a boolean value determining whether
   * this RecordTable object is modifiable or not.
   */
  public MetaDataRecordTable(ResultSet RS,
                             boolean readOnly)
      throws RecordException {
    super(RS,readOnly);

    try {
      final ResultSetMetaData metaData = RS.getMetaData();

      final int colCount = columnCount();

      autoIncrement      = new boolean[colCount];
      caseSensitive      = new boolean[colCount];
      currency           = new boolean[colCount];
      definitelyWritable = new boolean[colCount];
      _readOnly          = new boolean[colCount];
      searchable         = new boolean[colCount];
      signed             = new boolean[colCount];
      writable           = new boolean[colCount];

      columnDisplaySize = new int[colCount];
      columnType        = new int[colCount];
      nullable          = new int[colCount];
      precision         = new int[colCount];
      scale             = new int[colCount];

      catalogName     = new String[colCount];
      columnClassName = new String[colCount];
      columnTypeName  = new String[colCount];
      schemaName      = new String[colCount];
      tableName       = new String[colCount];

      for (int colIndex = colCount; --colIndex > 0; ) {
        autoIncrement[colIndex] = metaData.isAutoIncrement(colIndex);
        caseSensitive[colIndex] = metaData.isCaseSensitive(colIndex);
        currency[colIndex] = metaData.isCurrency(colIndex);
        definitelyWritable[colIndex] = metaData.isDefinitelyWritable(colIndex);
        nullable[colIndex] = metaData.isNullable(colIndex);
        _readOnly[colIndex] = metaData.isReadOnly(colIndex);
        searchable[colIndex] = metaData.isSearchable(colIndex);
        signed[colIndex] = metaData.isSigned(colIndex);
        writable[colIndex] = metaData.isWritable(colIndex);
        columnDisplaySize[colIndex] = metaData.getColumnDisplaySize(colIndex);
        columnType[colIndex] = metaData.getColumnType(colIndex);
        precision[colIndex] = metaData.getPrecision(colIndex);
        scale[colIndex] = metaData.getScale(colIndex);
        catalogName[colIndex] = metaData.getCatalogName(colIndex);
        columnClassName[colIndex] = metaData.getColumnClassName(colIndex);
        columnTypeName[colIndex] = metaData.getColumnTypeName(colIndex);
        schemaName[colIndex] = metaData.getSchemaName(colIndex);
        tableName[colIndex] = metaData.getTableName(colIndex);
      }
    }
    catch (SQLException sqle) {
      throw new RecordException(sqle);
    }
  }

  private final class RecordTableMetaData implements ResultSetMetaData {

    /**
     * check verifies that the column index is valid in this
     * RecordTable object.
     */
    private int check(int column) {
      if (column <= 0 || column > columnCount())
        throw new IndexOutOfBoundsException(column+" is not a valid column index in this RecordTable object.  Valid column indexes are from 1 to "+columnCount());

      return column;
    }

    /**
     * Gets the designated column's table's catalog name.
     */
    public String getCatalogName(int column) {
      return catalogName[check(column)-1];
    }

    /**
     * <p>Returns the fully-qualified name of the Java class
     * whose instances are manufactured if the method
     * <code>ResultSet.getObject</code> is called to retrieve
     * a value from the column.  <code>ResultSet.getObject</code>
     * may return a subclass of the class returned by this method.
     */
    public String getColumnClassName(int column) {
      return columnClassName[check(column)-1];
    }

    /**
     * Returns the number of columns in this <code>RecordTable</code>
     * object.
     */
    public int getColumnCount() {
      return columnCount();
    }

    /**
     * Indicates the designated column's normal maximum width
     * in characters.
     */
    public int getColumnDisplaySize(int column) {
      return columnDisplaySize[check(column)-1];
    }

    /**
     * Gets the designated column's suggested title for use in
     * printouts and displays.
     */
    public String getColumnLabel(int column) {
      return getColumnAlias(check(column)-1);
    }

    /**
     * Get the designated column's name.
     */
    public String getColumnName(int column) {
      return getColumnName(check(column)-1);
    }

    /**
     * Retrieves the designated column's SQL type.
     */
    public int getColumnType(int column) {
      return columnType[check(column)-1];
    }

    /**
     * Retrieves the designated column's database-specific type
     * name.
     */
    public String getColumnTypeName(int column) {
      return columnTypeName[check(column)-1];
    }

    /**
     * Get the designated column's number of decimal digits.
     */
    public int getPrecision(int column) {
      return precision[check(column)-1];
    }

    /**
     * Gets the designated column's number of digits to right
     * of the decimal point.
     */
    public int getScale(int column) {
      return scale[check(column)-1];
    }

    /**
     * Get the designated column's table's schema.
     */
    public String getSchemaName(int column) {
      return schemaName[check(column)-1];
    }

    /**
     * Gets the designated column's table name.
     */
    public String getTableName(int column) {
      return tableName[check(column)-1];
    }

    /**
     * Indicates whether the designated column is automatically numbered,
     * thus read-only.
     */
    public boolean isAutoIncrement(int column) {
      return autoIncrement[check(column)-1];
    }

    /**
     * Indicates whether a column's case matters.
     */
    public boolean isCaseSensitive(int column) {
      return caseSensitive[check(column)-1];
    }

    /**
     * Indicates whether the designated column is a cash value.
     */
    public boolean isCurrency(int column) {
      return currency[check(column)-1];
    }

    /**
     * Indicates whether a write on the designated column will definitely
     * succeed.
     */
    public boolean isDefinitelyWritable(int column) {
      return definitelyWritable[check(column)-1];
    }

    /**
     * Indicates the nullability of values in the designated column.
     */
    public int isNullable(int column) {
      return nullable[check(column)-1];
    }

    /**
     * Indicates whether the designated column is definitely not
     * writable.
     */
    public boolean isReadOnly(int column) {
      return _readOnly[check(column)-1];
    }

    /**
     * Indicates whether the designated column can be used in a
     * where clause.
     */
    public boolean isSearchable(int column) {
      return searchable[check(column)-1];
    }

    /**
     * Indicates whether values in the designated column are
     * signed numbers.
     */
    public boolean isSigned(int column) {
      return signed[check(column)-1];
    }

    /**
     * Indicates whether it is possible for a write on the designated
     * column to succeed.
     */
    public boolean isWritable(int column) {
      return writable[check(column)-1];
    }

  } // End of Class

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
    if (metaData == null)
      metaData = new RecordTableMetaData();
    return metaData;
  }

}

