/**
 * Copyright (c) 2002, SunMicrosystems
 * ALL RIGHTS RESERVED
 *
 * This class was taken from the Core Java 2 Volume II - Advanced
 * Features book, Prentice Hall, 2000
 * ISBN 0-13-081934-4
 * Chapter 6 - Advanced Swing
 *
 * @author Cay S. Horstmann & Gary Cornell
 * Modified By: John J. Blum
 * File: TableModelFactory.java
 * @version v1.0.1
 * Date: 3 March 2001
 * Modification Date: 19 March 2002
 * @since Java 2
 */

package org.java.swing.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public final class TableModelFactory
{

  private TableModelFactory()
  {
  }

  /**
   * The ResultSetTableModel class is the base class for the
   * scrolling and the caching result set table model. It stores
   * the result set and its metadata.
   */
  private static abstract class ResultSetTableModel extends AbstractTableModel
  {
    private ResultSet          rs;
    private ResultSetMetaData  rsmd;

    public ResultSetTableModel(ResultSet aResultSet)
    {
      rs = aResultSet;

      try
      {
        rsmd = rs.getMetaData();
      }
      catch (SQLException e)
      {
        System.out.println("Error " + e);
      }
    }

    public int getColumnCount()
    {
      try
      {
        return rsmd.getColumnCount();
      }
      catch (SQLException e)
      {
        System.out.println("Error " + e);
        return 0;
      }
    }

    public String getColumnName(int c)
    {
      try
      {
        return rsmd.getColumnName(c + 1);
      }
      catch (SQLException e)
      {
        System.out.println("Error " + e);
        return "";
      }
    }

    protected ResultSet getResultSet()
    {
      return rs;
    }
  } // End of ResultSetTableModel class

  /**
   * The CachingResultSetTableModel class caches the result set data;
   * it can be used if scrolling cursors are not supported
   */
  private static final class CachingResultSetTableModel extends ResultSetTableModel
  {
    private List cache;

    public CachingResultSetTableModel(ResultSet aResultSet)
    {
      super(aResultSet);

      try
      {
        cache = new ArrayList();

        int cols = getColumnCount();

        ResultSet rs = getResultSet();

        /**
         * Place all data in an array list of Object[] arrays.
         * We don't use an Object[][] because we don't know
         * how many rows are in the result set.
         */
        while (rs.next())
        {
          Object[] row = new Object[cols];

          for (int j = 0; j < row.length; j++)
            row[j] = rs.getObject(j + 1);

          cache.add(row);
        }
      }
      catch (SQLException e)
      {
        System.out.println("Error " + e);
      }
    }

    public int getRowCount()
    {
      return cache.size();
    }

    public Object getValueAt(int r, int c)
    {
      if (r < cache.size())
        return((Object[])cache.get(r))[c];
      else
        return null;
    }
  } // End of CachingResultSetTableModel class

  /**
   * The ScrollingResultSetTableModel class uses a scrolling cursor,
   * a JDBC 2 feature.
   */
  private static final class ScrollingResultSetTableModel extends ResultSetTableModel
  {
    public ScrollingResultSetTableModel(ResultSet aResultSet)
    {
      super(aResultSet);
    }

    public int getRowCount()
    {
      try
      {
        ResultSet rs = getResultSet();

        rs.last();

        return rs.getRow();
      }
      catch (SQLException e)
      {
        System.out.println("Error " + e);
        return 0;
      }
    }

    public Object getValueAt(int r,
                             int c )
    {
      try
      {
        ResultSet rs = getResultSet();

        rs.absolute(r + 1);

        return rs.getObject(c + 1);
      }
      catch (SQLException e)
      {
        System.out.println("Error " + e);
        return null;
      }
    }
  } // End of ScrollingResultSetTableModel class

  /**
   * The SortFilterTableModel class.
   */
  private static final class SortFilterTableModel extends AbstractTableModel
  {
    private int sortColumn;

    private Row[] rows;

    private TableModel model;

    public SortFilterTableModel(TableModel m)
    {
      model = m;
      rows = new Row[model.getRowCount()];

      for (int i = 0; i < rows.length; i++)
      {
        rows[i] = new Row();
        rows[i].index = i;
      }
    }
     
    /**
     * The Row class holds the index of the model row Rows
     * are compared by looking at the model row entries in
     * the sort column.
     */
    private class Row implements Comparable
    {
      public int index;

      public int compareTo(Object other)
      {
        Row otherRow = (Row) other;

        Object a = model.getValueAt(index, sortColumn);
        Object b = model.getValueAt(otherRow.index, sortColumn);

        if (a instanceof Comparable)
          return ((Comparable)a).compareTo(b);
        else
          return index - otherRow.index;
       }
    } // End of Row class

    public void addMouseListener(final JTable table)
    {
      table.getTableHeader().addMouseListener(new MouseAdapter()
      {
        public void mouseClicked(MouseEvent event)
        {
          // check for double click
          if (event.getClickCount() < 2)
            return;

          // find column of click and
          int tableColumn = table.columnAtPoint(event.getPoint());

          // translate to table model index and sort
          int modelColumn = table.convertColumnIndexToModel(tableColumn);
          
          sort(modelColumn);
        }
      });
    }
  
    public void sort(int c)
    {
      sortColumn = c;
      Arrays.sort(rows);
      fireTableDataChanged();
    }

    /**
     * Compute the moved row for the three methods that access
     * model elements
     */
    public Object getValueAt(int r, int c)
    {
      return model.getValueAt(rows[r].index,c);
    }
  
    public boolean isCellEditable(int r, int c)
    {
      return model.isCellEditable(rows[r].index,c);
    }
  
    public void setValueAt(Object aValue, int r, int c)
    {
      model.setValueAt(aValue,rows[r].index,c);
    }
  
    /**
     * Delegate all remaining methods to the model
     */
    public int getRowCount()
    {
      return model.getRowCount();
    }
  
    public int getColumnCount()
    {
      return model.getColumnCount();
    }
  
    public String getColumnName(int c)
    {
      return model.getColumnName(c);
    }
  
    public Class getColumnClass(int c)
    {
      return model.getColumnClass(c);
    }
  } // End of SortFilterTableModel class

  public static final TableModel getCachingResultSetTableModel(ResultSet RS)
  {
    return new CachingResultSetTableModel(RS);
  }

  public static final TableModel getScrollingResultSetTableModel(ResultSet RS)
  {
    return new ScrollingResultSetTableModel(RS);
  }

  public static final TableModel getSortFilterTableModel(TableModel TM)
  {
    return new SortFilterTableModel(TM);
  }

}

