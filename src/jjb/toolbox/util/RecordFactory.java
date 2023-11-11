/**
 * RecordFactory.java (c) 2002.4.17
 *
 * The RecordFactory class replaces the RecordSetUtil class.
 *
 * Copyright (c) 2002, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * @version 2003.3.5
 * @see jjb.toolbox.util.Record
 * @see jjb.toolbox.util.RecordImpl
 * @see jjb.toolbox.util.RecordTable
 * @see jjb.toolbox.util.RecordTableImpl
 */

package jjb.toolbox.util;

import java.sql.ResultSet;
import java.util.Map;

public class RecordFactory {

  /**
   * Constructor used by subclasses to create an instance of the
   * RecordFactory class in order to extend the functionality of
   * this class. Since the class is an utility class, the
   * protected modifier on the default constructor prevents
   * instances of this class from being created.
   */
  protected RecordFactory()
  {
  }

  /**
   * createEmptyRecord creates and returns a Record object with
   * no fields or associated values, hence empty.
   *
   * @return an empty Ljjb.toolbox.util.Record object.
   */
  public static Record createEmptyRecord()
  {
    return new RecordImpl();
  }

  /**
   * buildRecord constructs a new Record object initialized with
   * the contents of the Map object parameter.  The newly created
   * Record object will be modifiable with no key field specified.
   *
   * @param t is a Ljava.util.Map object used to set the contents
   * of the Record object created by this method.
   * @return a Ljjb.toolbox.util.Record object containing the
   * contents of the Map object.
   */
  public static Record buildRecord(Map t)
  {
    return buildRecord(t,false,null);
  }

  /**
   * This overloaded buildRecord method constructs a new Record
   * object initialized with the contents of the Map object parameter
   * allowing this record's modifyiable attribute to be specified 
   * with the readOnly parameter.  No key field is specified in this
   * record.
   *
   * @param t is a Ljava.util.Map object used to set the contents
   * of the Record object created by this method.
   * @param readOnly is a boolean value determining whether this
   * record can be modified or not.
   * @return a Ljjb.toolbox.util.Record object containing the
   * contents of the Map object.
   */
  public static Record buildRecord(Map     t,
                                   boolean readOnly)
  {
    return buildRecord(t,readOnly,null);
  }

  /**
   * This overloaded buildRecord method constructs a new Record
   * object initialized with the contents of the Map object
   * parameter and the designated key field.  The newly created
   * record object is modifyiable.
   *
   * @param t is a Ljava.util.Map object used to set the contents
   * of the Record object created by this method.
   * @param keyField is a Ljava.lang.String specifying the identity
   * (unique) field of the record object.
   * @return a Ljjb.toolbox.util.Record object containing the
   * contents of the Map object.
   * @throws java.lang.IllegalArgumentException if the key field
   * does not exist in the Map object, or if the keyField was
   * specified and the Map is null.
   */
  public static Record buildRecord(Map    t,
                                   String keyField)
  {
    return buildRecord(t,false,keyField);
  }

  /**
   * This overloaded buildRecord method constructs a new Record
   * object initialized with the contents of the Map object
   * parameter and the designated key field.  The record's 
   * modifyiable attribute is specified with the read-only boolean
   * parameter.
   *
   * @param t is a Ljava.util.Map object used to set the contents
   * of the Record object created by this method.
   * @param readOnly is a boolean value determining whether this
   * record can be modified or not.
   * @param keyField is a Ljava.lang.String specifying the identity
   * (unique) field of the record object.
   * @return a Ljjb.toolbox.util.Record object containing the
   * contents of the Map object.
   * @throws java.lang.IllegalArgumentException if the key field
   * does not exist in the Map object, or if the keyField was
   * specified and the Map is null.
   */
  public static Record buildRecord(Map     t,
                                   boolean readOnly,
                                   String  keyField )
  {
    return new RecordImpl(t,readOnly,keyField);
  }

  /**
   * buildRecordTable constructs a RecordTable Collection
   * object from the ResultSet object, obtained from running a
   * database query (SQL statement) and used to initialize the
   * RecordTable object returned from this method.
   *
   * @param RS is a Ljava.sql.ResultSet object, obtained from running
   * a SQL statement against a databse, and used to initialize the
   * RecordTable object contents.
   * @return a Ljjb.toolbox.sql.RecordTable object containng the
   * contents of the ResultSet object.
   */
  public static RecordTable buildRecordTable(ResultSet RS) throws RecordException
  {
    return buildRecordTable(RS,false);
  }

  /**
   * buildRecordTable constructs a RecordTable Collection
   * object from the ResultSet object, obtained from running a
   * database query (SQL statement) and used to initialize the
   * RecordTable object returned from this method.
   *
   * @param RS is a Ljava.sql.ResultSet object, obtained from running
   * a SQL statement against a databse, and used to initialize the
   * RecordTable object contents.
   * @param readOnly is a boolean parameter determining whether
   * this RecordTable object can be modified or not.
   * @return a Ljjb.toolbox.sql.RecordTable object containng the
   * contents of the ResultSet object.
   * @throws a Ljjb.toolbox.util.RecordException if the record table
   * cannot be constructed, due to an exception condition.
   */
  public static RecordTable buildRecordTable(ResultSet RS,
                                             boolean   readOnly) throws RecordException
  {
    return new RecordTableImpl(RS,readOnly);
  }

  /**
   * buildDetailedRecordTable creates an instance of the
   * DetailedRecordTable class initialized with the contents of
   * the given ResultSet object.  This  record table class also
   * stores meta-data information about the columns of this
   * record table.  The record table object returned from this
   * method is modifyiable.
   *
   * @param RS is a Ljava.sql.ResultSet object, obtained from running
   * a SQL statement against a databse, and used to initialize the
   * RecordTable object contents.
   * @return a Ljjb.toolbox.util.DetailedRecordTable object containing
   * the contents of the ResultSet object and containing meta-data
   * information about the columns in the record table.
   * @throws a Ljjb.toolbox.util.RecordException if the record table
   * cannot be constructed, due to an exception condition.
   */
  public static RecordTable buildMetaDataRecordTable(ResultSet RS) throws RecordException
  {
    return buildMetaDataRecordTable(RS,false);
  }

  /**
   * buildDetailedRecordTable creates an instance of the
   * DetailedRecordTable class initialized with the contents of
   * the given ResultSet object.  This  record table class also
   * stores meta-data information about the columns of this
   * record table.  The record table's modifyiable attribute is
   * specified with the readOnly parameter.
   *
   * @param RS is a Ljava.sql.ResultSet object, obtained from running
   * a SQL statement against a databse, and used to initialize the
   * RecordTable object contents.
   * @param readOnly is a boolean parameter determining whether
   * this RecordTable object can be modified or not.
   * @return a Ljjb.toolbox.util.DetailedRecordTable object containing
   * the contents of the ResultSet object and containing meta-data
   * information about the columns in the record table.
   * @throws a Ljjb.toolbox.util.RecordException if the record table
   * cannot be constructed, due to an exception condition.
   */
  public static RecordTable buildMetaDataRecordTable(ResultSet RS,
                                                     boolean   readOnly) throws RecordException
  {
    return new MetaDataRecordTable(RS,readOnly);
  }

}

