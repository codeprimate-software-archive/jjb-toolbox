/**
 * RecordTableModel.java (c) 2002.4.17
 *
 * The RecordTableModel class replaces the ETableModel class.
 *
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @auther John J. Blum
 * @version 2003.2.22
 * @see javax.swing.table.TableModel
 */

package jjb.toolbox.swing.table;

import javax.swing.table.AbstractTableModel;

import jjb.toolbox.util.RecordTable;

public class RecordTableModel extends AbstractTableModel {

  private RecordTable  recordTable;

  /**
   * Creates a new, default, empty RecordTableModel object.
   * This table model contains no data.
   */
  public RecordTableModel()
  {
    this(null);
  }

  /**
   * Creates a new instance of the RecordTableModel initialized
   * with the given RecordTable data structure.
   *
   * @param recordTable:Ljjb.toolbox.sql.RecordTable the
   * record table data structure used to display information
   * in an instance of the JTable class.
   */
  public RecordTableModel(RecordTable recordTable)
  {
    this.recordTable = recordTable;
  }

  // addTableModelListener(Ljavax.swing.table.TableModelListener)
  // is implemented by the AbstractTableModel class.

  /**
   * getColumnClass returns the object type of the column
   * identified by the integer index parameter.
   *
   * @param c an integer specifying the column index into the
   * table.
   * @return a Ljava.lang.Class object specifying the type of
   * the data contained in the column indexed by int c.
   */
  public Class getColumnClass(int c)
  {
    if (recordTable == null)
      return null;

    return recordTable.getColumnAlias(c).getClass();
  }

  /**
   * getColumnCount returns the number of columns in this table.
   *
   * @return an integer specifying the number of columns.
   */
  public int getColumnCount()
  {
    if (recordTable == null)
      return 0;

    return recordTable.columnCount();
  }

  /**
   * getColumnName returns the column name at the specified
   * column index into this table.
   *
   * @param column is an integer index specifying a column.
   * @return a Ljava.lang.String object representation of the
   * column name at the given index.
   */
  public String getColumnName(int column) 
  {
    if (recordTable == null)
      return null;

    return recordTable.getColumnAlias(column);
  }

  /**
   * getRowCount returns the number of rows in the table
   * object.
   *
   * @return an integer value indicating the number of
   * rows in this table.
   */
  public int getRowCount()
  { 
    if (recordTable == null)
      return 0;

    return recordTable.rowCount();
  }

  /**
   * getValueAt returns the value at the specified cell in
   * the table.
   *
   * @param row the row index into this table.
   * @param col the column index into this table.
   * @return a Ljava.lang.Object representation of the value
   * at this cell in the table.
   */
  public Object getValueAt(int row,
                           int col )
  {
    if (recordTable == null)
      return null;

    return recordTable.getCell(row,col);
  }

  /**
   * isCellEditable returns whether the information the cell
   * of this table  object can be modified.
   *
   * @param row the row index into this table.
   * @param col the column index into this table.
   * @return a boolean indicating whether the user can modify
   * the value at the cell specified by the (row,col) index
   * in the table.
   */
  public boolean isCellEditable(int row,
                                int col )
  { 
    if (recordTable == null)
      return false;

    return !recordTable.isReadOnly();
  }

  // removeTableModelListener(Ljavax.swing.table.TableModelListener)
  // is implemented by the AbstractTableModel class.

  /**
   * setValueAt sets the corresponding cell at (row,column) index to
   * the value of the "value" parameter.
   *
   * @param value:Ljava.lang.Object the object value representation.
   * @param row the row index into this table.
   * @param col the column index into this table.
   * @throws Ljava.lang.NullPointerException
   */
  public void setValueAt(Object value, 
                         int    row,
                         int    column )
  { 
    if (recordTable == null)
      throw new NullPointerException("The data for this table is null, please call setTableData before setting cell values.");

    try {
      recordTable.setCell(row,column,value);
    }
    catch (Exception ignore) {
    }
  }

}

