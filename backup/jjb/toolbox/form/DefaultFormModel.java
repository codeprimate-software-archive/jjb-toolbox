/**
 * Copyright (c) 2001, Code Primate
 * All Rights Reserved
 *
 * @author John J. Blum
 * File: DefaultFormModel.java
 * @version v1.0
 * Date: 30 May 2001
 * Modification Date: 17 April 2002
 * @since Java 1.0
 */

package jjb.toolbox.form;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.zip.*;
import jjb.toolbox.util.DefaultRecord;
import jjb.toolbox.util.RecordIF;

public class DefaultFormModel implements FormModelIF
{

  private Hashtable   formComponents;

  /**
   * Constructs an empty DefaultFormModel component.
   */
  public DefaultFormModel()
  {
    formComponents = new Hashtable();
  }

  /**
   * ackFormChanged acknowledges that the user changed the form content in
   * some way.
   */
  public void ackFormChanged()
  {
    Enumeration elements = formComponents.elements();

    while (elements.hasMoreElements())
      ((FormComponentIF) elements.nextElement()).ackStateChanged();
  }

  /**
   * ackFormComponentChanged acknowledges that the form component content has
   * changed and that the state should be reset.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   */
  public void ackFormComponentChanged(String fieldLabel)
  {
    FormComponentIF formComponent = (FormComponentIF) formComponents.get(fieldLabel);

    if (formComponent != null)
      formComponent.ackStateChanged();
  }

  /**
   * buildRecord constructs a RecordIF object of the particular type determined by
   * the recordType parameter to this method of all the information contained within
   * this form model.
   *
   * @param recordType:Ljava.lang.Class object specifying the type of RecordIF object
   * to create.
   * @return a Ljjb.toolbox.sql.RecordIF object containing the content of the form.
   */
  private RecordIF buildRecord(Class recordType)
  {
    try
    {
      Constructor _init = recordType.getConstructor(new Class[] {});

      return (RecordIF) _init.newInstance(new Object[] {});
    }
    catch (Exception e)
    {
      return new DefaultRecord();
    }
  }

  /**
   * clearForm clears the forms contents.
   */
  public void clearForm()
  {
    Enumeration keys = formComponents.keys();

    while (keys.hasMoreElements())
    {
      FormComponentIF formComponent = (FormComponentIF) formComponents.get((String) keys.nextElement());

      formComponent.clearData();
    }
  }

  /**
   * clearFormComponent clears the content of the specified form component.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   */
  public void clearFormComponent(String fieldLabel)
  {
    FormComponentIF component = (FormComponentIF) formComponents.get(fieldLabel);

    if (component != null)
      component.clearData();
  }

  /**
   * containsKey determines whether the specified key is part of this
   * FormModelIF object.
   *
   * @param key:Ljava.lang.String object representing the key.
   * @return a boolean value of true if the form model does indeed
   * contain this key, false otherwise.
   */
  public boolean containsKey(String key)
  {
    return formComponents.containsKey(key);
  }

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
  public FormComponentIF getFormComponent(String key)
  {
    return (FormComponentIF) formComponents.get(key);
  }

  /**
   * getFormComponentValue returns the content of the specified form component.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   * @return a Ljava.lang.Object representing the contents of the form component.
   */
  public Object getFormComponentValue(String fieldLabel)
  {
    FormComponentIF formComponent = (FormComponentIF) formComponents.get(fieldLabel);

    return (formComponent == null ? null : formComponent.getData());
  }

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
                                String  keyField   )
  {
    return getFormValues(recordType,keyField,true);
  }

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
                                boolean includeAllFields)
  {
    try
    {
      RecordIF record = buildRecord(recordType);
  
      record.setKeyField(keyField);
  
      Enumeration keys = formComponents.keys();
  
      while (keys.hasMoreElements())
      {
        String field = keys.nextElement().toString();
  
        FormComponentIF formComponent = (FormComponentIF) formComponents.get(field);
  
        if (includeAllFields || formComponent.hasStateChanged())
          record.addField(field,formComponent.getData());
      }
  
      return record;
    }
    catch (Exception ignore)
    {
      return null;
    }
  }

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
                                String[]  fieldSet   )
  {
    return getFormValues(recordType,keyField,fieldSet,true);
  }

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
                                boolean   includeAllFields)
  {
    try
    {
      RecordIF record = buildRecord(recordType);
  
      record.setKeyField(keyField);
  
      for (int index = fieldSet.length; --index >= 0; )
      {
        FormComponentIF formComponent = (FormComponentIF) formComponents.get(fieldSet[index]);
  
        if (formComponent == null) continue;
  
        if (includeAllFields || formComponent.hasStateChanged())
          record.addField(fieldSet[index],formComponent.getData());
      }
  
      return record;
    }
    catch (Exception ignore)
    {
      return null;
    }
  }

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
  public String getKey(FormComponentIF component)
  {
    Enumeration keys = formComponents.keys();

    while (keys.hasMoreElements())
    {
      String key = keys.nextElement().toString();

      Object formComponent = formComponents.get(key);

      if (formComponent.equals(component))
        return key;
    }

    return null;
  }

  /**
   * getKeys returns an Object array containing all keys assocaited to
   * FormComponentIF objects in this form model.
   *
   * @return a [Ljava.lang.Object array containing the keys in this Form
   * Model.
   */
  public Object[] getKeys()
  {
    Object[] keys = new Object[formComponents.size()];

    Enumeration ekeys = formComponents.keys();

    int index = 0;

    while (ekeys.hasMoreElements())
      keys[index++] = ekeys.nextElement().toString();

    return keys;
  }

  /**
   * hasFormChanged determines whether the form content has been modified
   * by the user.
   *
   * @return a boolean value of true if the user modified the form's content,
   * false otherwise.
   */
  public boolean hasFormChanged()
  {
    Enumeration elements = formComponents.elements();

    while (elements.hasMoreElements())
    {
      FormComponentIF formComponent = (FormComponentIF) elements.nextElement();

      if (formComponent.hasStateChanged())
        return true;
    }

    return false;
  }

  /**
   * hasFormComponentChanged determines whether a particular form component's
   * content in the form model has changed.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   * @return a boolean value of true if the content of the form component did
   * indeed change, false otherwise.
   */
  public boolean hasFormComponentChanged(String fieldLabel)
  {
    FormComponentIF formComponent = (FormComponentIF) formComponents.get(fieldLabel);

    if (formComponent != null)
      return formComponent.hasStateChanged();

    return false;
  }

  /**
   * initFormComponent initializes the specified form component with the object
   * value.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   */
  public void initFormComponent(String fieldLabel,
                                Object value      ) throws DataFormatException
  {
    FormComponentIF formComponent = (FormComponentIF) formComponents.get(fieldLabel);

    if (formComponent != null)
      formComponent.init(value);
  }

  /**
   * isFormComponentEnabled determines for a particular form component referenced by
   * the fieldLabel parameter whether it is enabled or not.
   *
   * @param fieldLabel:Ljava.lang.String object used to refer to the form component
   * in this form model.
   * @return a boolean value of true if the form component is enabled or not.  If the
   * form component is not present in this form model, the method returns true.
   */
  public boolean isFormComponentEnabled(String fieldLabel)
  {
    FormComponentIF formComponent = (FormComponentIF) formComponents.get(fieldLabel);

    return (formComponent == null ? false : formComponent.isEnabled());
  }

  /**
   * isFormEnabled determines whether the form components within the form are
   * enabled.  All form components must be enabled for this method to return
   * true, otherwise false is returned.
   *
   * @return a boolean value indicating true if all FORM components are enabled.
   */
  public boolean isFormEnabled()
  {
    Enumeration enum = formComponents.elements();

    while (enum.hasMoreElements())
    {
      if (!((FormComponentIF) enum.nextElement()).isEnabled())
        return false;
    }

    return true;
  }

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
  public FormComponentIF register(String           key,
                                  FormComponentIF  component)
  {
    formComponents.put(key,component);

    return component;
  }

  /**
   * register uses the Container object and searchs it and all sub-container
   * objects for FormComponentIF objects to register with this form model
   * It is assumed that the user called the Component.setName method to
   * specify the key to assocaite with the form component.
   *
   * @param panel:Ljava.awt.Container object containing form components to
   * register.
   */
  public void register(Container panel)  
  {
    Component[] components = panel.getComponents();

    for (int index = components.length; --index >= 0; )
    {
      Component component = components[index];

      if (component instanceof Container) register((Container) component);

      if (component instanceof FormComponentIF)
      {
        String key = component.getName();

        if (key == null)
          throw new NullPointerException("FormDataModel.registerFormComponents - keys are used to map form components to their database fields.  Use the Components.setName method to specify a key.");

        formComponents.put(key,component);
      }
    }
  }

  /**
   * resetFormComponentValue restores the content of the form component to it's
   * original value.
   * NOTE: this method is not yet implemented in this version.
   *
   * @param fieldLabel:Ljava.lang.String is the key to the associated form component
   * in the form model.
   * @throws a Ljjb.toolbox.util.UnsupportedOperationException object.
   */
  public void resetFormComponentValue(String fieldLabel)
  {
    throw new UnsupportedOperationException("This method is not implemented in this version.");
  }

  /**
   * resetFormValues resets the form elements to their original values.
   * NOTE: this method is not yet implemented in this version.
   * throws a Ljjb.toolbox.util.UnsupportedOperationException
   */
  public void resetFormValues()
  {
    throw new UnsupportedOperationException("This method is not implemented in this version.");
  }

  /**
   * setFormChanged sets the state of the form's content to modified, or not
   * modified.
   *
   * @param tf which is a boolean value, indicating true of the form should
   * specify that the state has changed or false if form should not specify
   * a state change.
   */
  public void setFormChanged(boolean tf)
  {
    Enumeration elements = formComponents.elements();

    while (elements.hasMoreElements())
      ((FormComponentIF) elements.nextElement()).setStateChanged(tf);
  }

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
                                      boolean tf         )
  {
    FormComponentIF formComponent = (FormComponentIF) formComponents.get(fieldLabel);

    if (formComponent != null)
      formComponent.setStateChanged(tf);
  }

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
                                      boolean tf         )
  {
    FormComponentIF formComponent = (FormComponentIF) formComponents.get(fieldLabel);

    if (formComponent != null)
      formComponent.setEnabled(tf);
  }

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
                                    Object fieldValue ) throws DataFormatException
  {
    FormComponentIF formComponent = (FormComponentIF) formComponents.get(fieldLabel);

    if (formComponent != null)
      formComponent.setData(fieldValue);
  }

  /**
   * setFormEnabled enables/disables, as determined by the boolean parameter, all form
   * components managed by this form model for the ui.
   *
   * @param tf is a boolean value indicating whether to enable (true) / disable (false)
   * all form components in this form model. 
   */
  public void setFormEnabled(boolean tf)
  {
    Enumeration enum = formComponents.elements();

    while (enum.hasMoreElements())
      ((FormComponentIF) enum.nextElement()).setEnabled(tf);
  }

  /**
   * setFormValues set the form element values with the contents of the RecordIF object.
   *
   * @param record:Ljjb.toolbox.sql.RecordIF object used to set the content of the
   * form.
   * @throws Ljava.util.zip.DataFormatException
   */
  public void setFormValues(RecordIF record) throws DataFormatException
  {
    Object[] fields = record.getFields();

    for (int index = fields.length; --index >= 0; )
    {
      FormComponentIF formComponent = (FormComponentIF) formComponents.get(fields[index].toString());

      if (formComponent == null)
        continue;

      formComponent.setData(record.getValue(fields[index].toString()));
    }
  }

}

