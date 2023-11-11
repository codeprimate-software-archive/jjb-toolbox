/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: FormModelIF.java
 * @version v1.0
 * Date: 30 May 2001
 * Modified Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.form;

import java.awt.*;
import java.util.zip.*;
import jjb.toolbox.util.RecordIF;

public interface FormModelIF
{

  /**
   * ackFormChanged acknowledges that the user changed the form content in
   * some way.
   */
  public void ackFormChanged();

  /**
   * ackFormComponentChanged acknowledges that the form component content has
   * changed and that the state should be reset.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   */
  public void ackFormComponentChanged(String fieldLabel);

  /**
   * clearForm clears the forms contents.
   */
  public void clearForm();

  /**
   * clearFormComponent clears the content of the specified form component.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   */
  public void clearFormComponent(String fieldLabel);

  /**
   * containsKey determines whether the specified key is part of this
   * FormModelIF object.
   *
   * @param key:Ljava.lang.String object representing the key.
   * @return a boolean value of true if the form model does indeed
   * contain this key, false otherwise.
   */
  public boolean containsKey(String key);

  /**
   * getFormComponent returns the FormComponentIF object associated to the
   * key.
   *
   * @param key:Ljava.lang.String object representing the key to the associated
   * form component.
   * @return the Ljjb.toolbox.form.FormComponentIF object associated to the
   * specified key.
   * @see getKey
   */
  public FormComponentIF getFormComponent(String key);

  /**
   * getFormComponentValue returns the content of the specified form component.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   * @return a Ljava.lang.Object representing the contents of the form component.
   */
  public Object getFormComponentValue(String fieldLabel);

  /**
   * getFormValues returns the content of all form components in this form model
   * in a RecordIF object.
   *
   * @param recordType:Ljava.lang.Class is the type of RecordIF object to return.
   * @param keyField:Ljava.lang.String is the key value to uniquely identify
   * records of this form in a collection of records.  The key corresponds to
   * one fieldLabel of the form component that's contents is unique amongst every
   * other form data.
   * @return a Ljjb.toolbox.sql.RecordIF object containing the content of all
   * form components in this form model.
   */
  public RecordIF getFormValues(Class   recordType,
                                String  keyField   );

  /**
   * getFormValues returns the content of form components in this form model
   * in a RecordIF object.  This method allows the caller to specify whether
   * this method should return the content of all form components, or only
   * content of form components that has changed, as specified by the 
   * includeAllFields flag.
   *
   * @param recordType:Ljava.lang.Class is the type of RecordIF object to return.
   * @param keyField:Ljava.lang.String is the key value to uniquely identify
   * records of this form in a collection of records.  The key corresponds to
   * one fieldLabel of the form component that's contents is unique amongst every
   * other form data.
   * @param includeAllFields is a boolean indicating whether the content of all
   * form components should be included, or only content of those form components
   * that have changed.
   * @return a Ljjb.toolbox.sql.RecordIF object containing the content of all/modified
   * form components in this form model.
   */
  public RecordIF getFormValues(Class   recordType,
                                String  keyField,
                                boolean includeAllFields);

  /**
   * getFormValues returns the content of all form components that are referred
   * to by keys in the fieldSet parameter.
   *   
   * @param recordType:Ljava.lang.Class is the type of RecordIF object to return.
   * @param keyField:Ljava.lang.String is the key value to uniquely identify
   * records of this form in a collection of records.  The key corresponds to
   * one fieldLabel of the form component that's contents is unique amongst every
   * other form data.
   * @param fieldSet:[Ljava.lang.String array object containing keys of all
   * form components whose content should be included in the RecordIF object.
   * @return a Ljjb.toolbox.sql.RecordIF object containing the content of all
   * form components in this form model.
   */
  public RecordIF getFormValues(Class     recordType,
                                String    keyField,
                                String[]  fieldSet   );

  /**
   * getFormValues returns the content of form components that are referred to by
   * keys in the fieldSet parameter.  This method allows the caller to specify
   * whether this method should return the content of those form components, referred
   * to by keys in the fieldSet, or only content of form components referred to by
   * the fieldSet that have changed, as specified by the includeAllFields flag.
   *   
   * @param recordType:Ljava.lang.Class is the type of RecordIF object to return.
   * @param keyField:Ljava.lang.String is the key value to uniquely identify
   * records of this form in a collection of records.  The key corresponds to
   * one fieldLabel of the form component that's contents is unique amongst every
   * other form data.
   * @param fieldSet:[Ljava.lang.String array object containing keys of all
   * form components whose content should be included in the RecordIF object.
   * @param includeAllFields is a boolean indicating whether the content of all
   * referenced form components should be included, or only content of those
   * referrenced form components that have changed.
   * @return a Ljjb.toolbox.sql.RecordIF object containing the content of all
   * form components in this form model.
   */
  public RecordIF getFormValues(Class     recordType,
                                String    keyField,
                                String[]  fieldSet,
                                boolean   includeAllFields);

  /**
   * getKey returns the key object associated to the specified FormComponentIF
   * object.
   *
   * @param component:Ljjb.toolbox.form.FormComponentIF object for which the user
   * wishes the key returned.
   * @return the Ljava.lang.String object of the key associated to the specified
   * FormComponentIF object.
   * @see getFormComponent
   */
  public String getKey(FormComponentIF component);

  /**
   * getKeys returns an Object array containing all keys assocaited to
   * FormComponentIF objects in this form model.
   *
   * @return a [Ljava.lang.Object array containing the keys in this Form
   * Model.
   */
  public Object[] getKeys();

  /**
   * hasFormChanged determines whether the form content has been modified
   * by the user.
   *
   * @return a boolean value of true if the user modified the form's content,
   * false otherwise.
   */
  public boolean hasFormChanged();

  /**
   * hasFormComponentChanged determines whether a particular form component's
   * content in the form model has changed.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   * @return a boolean value of true if the content of the form component did
   * indeed change, false otherwise.
   */
  public boolean hasFormComponentChanged(String fieldLabel);

  /**
   * initFormComponent initializes the specified form component with the object
   * value.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   */
  public void initFormComponent(String fieldLabel,
                                Object value      ) throws DataFormatException;

  /**
   * isFormComponentEnabled determines for a particular form component referenced by
   * the fieldLabel parameter whether it is enabled or not.
   *
   * @param fieldLabel:Ljava.lang.String object used to refer to the form component
   * in this form model.
   * @return a boolean value of true if the form component is enabled or not.  If the
   * form component is not present in this form model, the method returns true.
   */
  public boolean isFormComponentEnabled(String fieldLabel);

  /**
   * isFormEnabled determines whether the form components within the form are
   * enabled.  All form components must be enabled for this method to return
   * true, otherwise false is returned.
   *
   * @return a boolean value indicating true if all FORM components are enabled.
   */
  public boolean isFormEnabled();

  /**
   * register tracks the specified FormComponentIF object with the
   * specified key object.
   *
   * @param key:Ljava.lang.String object used to refer to the FormComponentIF
   * object.
   * @param component:Ljjb.toolbox.form.FormComponentIF object which
   * represents a form element in the user interface.
   * @return the Ljjb.toolbox.form.FormComponentIF object back to the caller.
   * The purpose for this return is to allow the programmer to make a call
   * to this register method and add the component to the container all at
   * the same time.
   */
  public FormComponentIF register(String          key,
                                  FormComponentIF component);

  /**
   * register uses the Container object and searchs it and all sub-container
   * objects for FormComponentIF objects to register with this form model
   * It is assumed that the user called the Component.setName method to
   * specify the key to assocaite with the form component.
   *
   * @param panel:Ljava.awt.Container object containing form components to
   * register.
   */
  public void register(Container panel);

  /**
   * resetFormComponentValue restores the content of the form component to it's
   * original value.
   * NOTE: this method is not yet implemented in this version.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   * @throws a Ljjb.toolbox.util.UnsupportedOperationException object.
   */
  public void resetFormComponentValue(String fieldLabel);

  /**
   * resetFormValues resets the form elements to their original values.
   * NOTE: this method is not yet implemented in this version.
   * throws a Ljjb.toolbox.util.UnsupportedOperationException
   */
  public void resetFormValues();

  /**
   * setFormChanged sets the state of the form's content to modified, or not
   * modified.
   *
   * @param tf which is a boolean value, indicating true of the form should
   * specify that the state has changed or false if form should not specify
   * a state change.
   */
  public void setFormChanged(boolean tf);

  /**
   * setFormComponentChanged sets the state of the form component's content to 
   * modified, or not modified.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   * @param tf is a boolean value indicating true if the form component's content
   * should be flagged as modified, or false otherwise.
   */
  public void setFormComponentChanged(String  fieldLabel,
                                      boolean tf         );

  /**
   * setFormComponentEnabled sets the form component referred to by the fieldLabel
   * parameter to particular state determined by the boolean parameter.  True to
   * enable, false to disable.
   *
   * @param fieldLabel:Ljava.lang.String object to refer to the form component in
   * this form model.
   * @param tf is a boolean value indicating the state to set the form component
   * to.
   */
  public void setFormComponentEnabled(String  fieldLabel,
                                      boolean tf         );

  /**
   * setFormComponentValue sets the content of specified form component to the
   * specified value.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   * @param fieldValue:Ljava.lang.Object value representing the content for the
   * form component.
   */
  public void setFormComponentValue(String fieldLabel,
                                    Object fieldValue ) throws DataFormatException;

  /**
   * setFormEnabled enables/disables, as determined by the boolean parameter, all form
   * components managed by this form model for the ui.
   *
   * @param tf is a boolean value indicating whether to enable (true) / disable (false)
   * all form components in this form model. 
   */
  public void setFormEnabled(boolean tf);

  /**
   * setFormValues set the form element values with the contents of the RecordIF object.
   *
   * @param record:Ljjb.toolbox.sql.RecordIF object used to set the content of the
   * form.
   * @throws Ljava.util.zip.DataFormatException
   */
  public void setFormValues(RecordIF record) throws DataFormatException;

}

