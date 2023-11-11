/**
 * Copyright (c) 2001, JB Innovations
 * ALL RIGHTS RESERVED
 *
 * @author John J. Blum
 * File: UserProfile.java
 * @version v1.0
 * Date: 22 April 2001
 * Modification Date: 25 August 2001
 * @since Java 1.0
 */

package jjb.toolbox.contacts;

import java.awt.*;
import java.io.*;
import java.util.*;

public class UserProfile implements Serializable
{

  private int       ID;

  private int       zip,
                    businessZip;

  private Image     photo;

  private String    firstName,
                    lastName,
                    middleName,
                    title,
                    contactName,
                    username,
                    address,
                    city,
                    state,
                    phone,
                    cellPhone,
                    fax,
                    email;

  private String    businessName,
                    businessAddress,
                    businessCity,
                    businessState,
                    businessPhone,
                    businessFax,
                    businessEmail;

  /**
   * Creates a new, empty user profile class to store information
   * about a person.
   */
  public UserProfile()
  {
    ID = 0;
  }

  /**
   * clone creates an identical UserProfile object.
   */
  public Object clone() throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException("The user cannot be cloned!");
  }

  /**
   * equals compares this UserProfil with another UserProfile object
   * to determine equality.
   *
   * @param obj:Ljava.lang.Object UserProfile used to compare with this
   * UserProfile object in testing for equality.
   * @return a boolean value of true to indicate that these objects are
   * the same UserProfile.
   */
  public boolean equals(Object obj)
  {
    if (!(obj instanceof UserProfile))
      return false;

    UserProfile userProfile = (UserProfile) obj;

    return (userProfile.ID == ID);
  }

  /**
   * getAddress returns the person's home street address.
   *
   * @return a Ljava.lang.String object of the person's home street
   * address.
   */
  public String getAddress()
  {
    return address;
  }

  /**
   * getBusinessAddress returns the street address of the person's business.
   *
   * @return a Ljava.lang.String object of the street address of the person's
   * business.
   */
  public String getBusinessAddress()
  {
    return businessAddress;
  }

  /**
   * getBusinessCity returns the city in which the person's place of business
   * resides.
   *
   * @return a Ljava.lang.String object of the city of business.
   */
  public String getBusinessCity()
  {
    return businessCity;
  }

  /**
   * getBusinessEmail returns the internal email address of the 
   * person's place of business.
   *
   * @return a Ljava.lang.String object of the business email address.
   */
  public String getBusinessEmail()
  {
    return businessEmail;
  }

  /**
   * getBusinessFax returns the business's fax number.
   *
   * @return a Ljava.lang.String object of the business fax number.
   */
  public String getBusinessFax()
  {
    return businessFax;
  }

  /**
   * getBusinessName returns the name of the business for which the person
   * works or is associated with.
   *
   * @return a Ljava.lang.String object the person's place of business.
   */
  public String getBusinessName()
  {
    return businessName;
  }

  /**
   * getBusinessPhone returns the business phone number.
   *
   * @return a Ljava.lang.String object of the business phone number.
   */
  public String getBusinessPhone()
  {
    return businessPhone;
  }

  /**
   * getBusinessState returns the state of the city in which the person's place
   * of business resides.
   *
   * @return a Ljava.lang.String object of the business's state.
   */
  public String getBusinessState()
  {
    return businessState;
  }

  /**
   * getBusinessZip returns the zip code of the person's place of
   * business.
   *
   * @return a integer value of the zip code of the person's place
   * of business.
   */
  public int getBusinessZip()
  {
    return businessZip;
  }

  /**
   * getCellPhone returns the person's cell phone number.
   *
   * @return a Ljava.lang.String object of the person's cell phone
   * number.
   */
  public String getCellPhone()
  {
    return cellPhone;
  }

  /**
   * getCity returns the person's city
   *
   * @return a Ljava.lang.String object of the user's city.
   */
  public String getCity()
  {
    return city;
  }

  /**
   * getContactName returns the formal name by which the person is
   * commonly referred to in business.
   *
   * @return a Ljava.lang.String object of the person's contact name,
   * usually a person's formal business name.
   */
  public String getContactName()
  {
    if (lastName == null && firstName == null)
      return null;

    StringBuffer contactName = new StringBuffer();

    contactName.append((title == null ? "" : title));

    if (title != null)
      contactName.append(" ");

    contactName.append((firstName == null ? "" : firstName));

    if (contactName.length() > 0 && firstName != null)
      contactName.append(" ");

    contactName.append((middleName == null ? "" : middleName));

    if (contactName.length() > 0 && middleName != null)
      contactName.append(" ");

    contactName.append((lastName == null ? "" : lastName));

    return contactName.toString().trim();
  }

  /**
   * getEmail returns the person's email address.
   *
   * @return a Ljava.lang.String object of the person's email address.
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * getFax returns the person's fax number.
   *
   * @return a Ljava.lang.String of the person's fax number.
   */
  public String getFax()
  {
    return fax;
  }

  /**
   * getFirstName returns the person's first name.
   *
   * @return a Ljava.lang.String object of the person's first name.
   */
  public String getFirstName()
  {
    return firstName;
  }

  /**
   * getID returns the unique identifier of the UserProfile record.
   *
   * @return a integer value of the UserProfile ID.
   */
  public int getID()
  {
    return ID;
  }

  /**
   * getLastName returns the person's last name.
   *
   * @return a Ljava.lang.String object of the person's last name.
   */
  public String getLastName()
  {
    return lastName;
  }

  /**
   * getMiddleName returns the person's middle name or initial.
   *
   * @return a Ljava.lang.String object of the person's middle name,
   * or initial.
   */
  public String getMiddleName()
  {
    return middleName;
  }

  /**
   * getPhone returns the person's home phone number.
   *
   * @return a Ljava.lang.String object of the person's phone number.
   */
  public String getPhone()
  {
    return phone;
  }

  /**
   * getPhoto returns the person's photograph.
   *
   * @return a Ljava.awt.Image of the person's photograph.
   */
  public Image getPhoto()
  {
    return photo;
  }

  /**
   * getState returns the person's state.
   *
   * @return a Ljava.lang.String object of the person's state.
   */
  public String getState()
  {
    return state;
  }

  /**
   * getTitle returns the title by which the user is commonly referred.
   *
   * @return a Ljava.lang.String object of the person's title.
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * getUsername returns the person's username, or handle on the Internet.
   *
   * @return a Ljava.lang.String object of the person's username, alias
   * name on the net.
   */
  public String getUsername()
  {
    return username;
  }

  /**
   * getZip returns the person's zip code.
   *
   * @return a Ljava.lang.String object of the person's zip code.
   */
  public int getZip()
  {
    return zip;
  }

  /**
   * loadProfile loads user information from a file specified by the
   * path and name of the file.
   *
   * @param filename:Ljava.lang.String is a directory path and filename
   * the user profile on disk.
   * @return a Ljjb.toolbox.contacts.UserProfile object containing the
   * contents of the user profile file.
   * @throws Ljava.lang.ClassNotFoundException
   * @throws Ljava.io.FileNotFoundException
   * @throws Ljava.io.IOException
   * @throws Ljava.io.StreamCorruptedException
   */
  public static UserProfile loadProfile(String filename) throws ClassNotFoundException, FileNotFoundException, IOException, StreamCorruptedException
  {
    FileInputStream fis = new FileInputStream(filename);

    UserProfile user = loadProfile(fis);

    fis.close();

    return user;
  }

  /**
   * loadProfile loads user information from a file specified by the
   * input stream object used to read from the file.
   *
   * @param fis:Ljava.io.FileInputStream object used to read the contents
   * the user profile file.
   * @return a Ljjb.toolbox.contacts.UserProfile object containing the
   * contents of the user profile file.
   * @throws Ljava.lang.ClassNotFoundException
   * @throws Ljava.io.IOException
   * @throws Ljava.io.StreamCorruptedException
   */
  public static UserProfile loadProfile(FileInputStream fis) throws ClassNotFoundException, IOException, StreamCorruptedException
  {
    ObjectInputStream objInStream = new ObjectInputStream(fis);

    UserProfile user = (UserProfile) objInStream.readObject();

    objInStream.close();

    return user;
  }

  /**
   * saveProfile save the content of this UserProfile object to a file on
   * disk.
   *
   * @param filename:Ljava.lang.String is the directory path and filename
   * of the file to write the user profile information.
   * @throws Ljava.io.FileNotFoundException
   * @throws Ljava.io.IOException
   */
  public void saveProfile(String filename) throws FileNotFoundException, IOException
  {
    FileOutputStream fos = new FileOutputStream(filename);

    saveProfile(fos);
    fos.close();
  }

  /**
   * saveProfile save the content of this UserProfile object to a file on
   * disk.
   *
   * @param fos:Ljava.io.FileOutputStream is the output stream object used to
   * write contents from this UserProfile object to file.
   * @throws Ljava.io.IOException
   */
  public void saveProfile(FileOutputStream fos) throws IOException
  {
    ObjectOutputStream objOutStream = new ObjectOutputStream(fos);

    objOutStream.writeObject(this);
    objOutStream.close();
  }

  /**
   * setAddress sets the person's street address.
   *
   * @param address:Ljava.lang.String object containing the person's home
   * street address.
   */
  public void setAddress(String address)
  {
    this.address = address;
  }

  /**
   * setBusinessAddress sets the street address of the person's place
   * of business.
   *
   * @param businessAddress:Ljava.lang.String object containing the business
   * street address.
   */
  public void setBusinessAddress(String businessAddress)
  {
    this.businessAddress = businessAddress;
  }

  /**
   * setBusinessCity sets the city in which the person's place of business
   * resides.
   *
   * @param businessCity:Ljava.lang.String object of the business's residing
   * city.
   */
  public void setBusinessCity(String businessCity)
  {
    this.businessCity = businessCity;
  }

  /**
   * setBusinessEmail sets the company email address (contact) of the person's
   * location of business.
   *
   * @param businessEmail:Ljava.lang.String object of the companies corporate
   * email address.
   */
  public void setBusinessEmail(String businessEmail)
  {
    this.businessEmail = businessEmail;
  }

  /**
   * setBusinessFax sets the company fax number for the person's location of
   * business.
   *
   * @param businessFax:Ljava.lang.String object of the business's fax number.
   */
  public void setBusinessFax(String businessFax)
  {
    this.businessFax = businessFax;
  }

  /**
   * setBusinessName sets the name of the person's location of business.
   *
   * @param businessName:Ljava.lang.String object of the business name.
   */
  public void setBusinessName(String businessName)
  {
    this.businessName = businessName;
  }

  /**
   * setBusinessPhone sets the phone number to the person's location of
   * business.
   *
   * @param businessPhone:Ljava.lang.String object containing the business's
   * phone number.
   */
  public void setBusinessPhone(String businessPhone)
  {
    this.businessPhone = businessPhone;
  }

  /**
   * setBusinessState sets the state in which the person's location of business
   * resides.
   *
   * @param businessState:Ljava.lang.String object of the business's state.
   */
  public void setBuisnessState(String businessState)
  {
    this.businessState = businessState;
  }

  /**
   * setBusinessZip sets the zip code of the person's location of business.
   *
   * @param businessZip is an integer value specifying the business's zip code.
   */
  public void setBusinessZip(int businessZip)
  {
    this.businessZip = businessZip;
  }

  /**
   * setCellPhone sets the person's cell phone number.
   *
   * @param cellPhone:Ljava.lang.String object of the person's cell phone
   * number.
   */
  public void setCellPhone(String cellPhone)
  {
    this.cellPhone = cellPhone;
  }

  /**
   * setCity sets the city of the person's residence.
   *
   * @param city:Ljava.lang.String object of the person's resident city.
   */
  public void setCity(String city)
  {
    this.city = city;
  }

  /**
   * setEmail sets the person's email address.
   *
   * @param email:Ljava.lang.String object of the person's email address.
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   * setFax sets the person's fax number.
   *
   * @param fax:Ljava.lang.String object of the person's personal
   * fax number.
   */
  public void setFax(String fax)
  {
    this.fax = fax;
  }

  /**
   * setFirstName sets the person's first name.
   *
   * @param firstName:Ljava.lang.String object of the person's first
   * name.
   */
  public void setFirstName(String firstName)
  {
    this.firstName = ((firstName != null && firstName.equals("")) ? null : firstName);
  }

  /**
   * setID sets the specified integer value to be the unique ID of
   * the UserProfile record.  For a person, this would normally be
   * their SSN.
   *
   * @param ID is an integer value specifying the unique identifier of
   * the UserProfile record.
   */
  public void setID(int ID)
  {
    this.ID = ID;
  }

  /**
   * setLastName sets the person's last name.
   *
   * @param lastName:Ljava.lang.String object of the person's last
   * name.
   */
  public void setLastName(String lastName)
  {
    this.lastName = ((lastName != null && lastName.equals("")) ? null : lastName);
  }

  /**
   * setMiddleName sets the person's middle name or initial.
   *
   * @param middleName:Ljava.lang.String object of the person's middle
   * name or initial.
   */
  public void setMiddleName(String middleName)
  {
    this.middleName = ((middleName != null && middleName.equals("")) ? null : middleName);
  }

  /**
   * setPhone sets the person's phone number.
   *
   * @param phone:Ljava.lang.String object of the person's phone number.
   */
  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  /**
   * setPhoto sets the person's photograph.
   *
   * @param photo:Ljava.awt.Image of the person's photograph.
   */
  public void setPhoto(Image photo)
  {
    this.photo = photo;
  }

  /**
   * setState sets the state in which the person resides.
   *
   * @param state:Ljava.lang.String object of the state where the person
   * resides.
   */
  public void setState(String state)
  {
    this.state = state;
  }

  /**
   * setTitle sets the person's title.
   *
   * @param title:Ljava.lang.String object of the person's title.
   */
  public void setTitle(String title)
  {
    this.title = ((title != null && title.equals("")) ? null : title);
  }

  /**
   * setUsername sets the person's handle, or alias name for the net.
   *
   * @param user:Ljava.lang.String object of the person's handle, or
   * alias name.
   */
  public void setUsername(String user)
  {
    username = user;
  }

  /**
   * setZip set the person's zip code.
   *
   * @param zip is an integer value of the person's zip code.
   */
  public void setZip(int zip)
  {
    this.zip = zip;
  }

  /**
   * toString prints the contents of the user's profile as a String.
   *
   * @return a Ljava.lang.String object of the user's profile.
   */
  public String toString()
  {
    StringBuffer toStr = new StringBuffer();

    toStr.append("ID: ").append(ID).append("\n");
    toStr.append("Contact Name: ").append(getContactName()).append("\n");
    toStr.append("username: ").append(username).append("\n");
    toStr.append("First Name: ").append(firstName).append("\n");
    toStr.append("Middle Name: ").append(middleName).append("\n");
    toStr.append("Last Name: ").append(lastName).append("\n");
    toStr.append("Address: ").append(address).append("\n");
    toStr.append("City: ").append(city).append("\n");
    toStr.append("State: ").append(state).append("\n");
    toStr.append("Zip: ").append(zip).append("\n");
    toStr.append("Phone: ").append(phone).append("\n");
    toStr.append("Cell Phone: ").append(cellPhone).append("\n");
    toStr.append("Fax: ").append(fax).append("\n");
    toStr.append("Email: ").append(email).append("\n");
    toStr.append("Business Name: ").append(businessName).append("\n");
    toStr.append("Business Address: ").append(businessAddress).append("\n");
    toStr.append("Business City: ").append(businessCity).append("\n");
    toStr.append("Business State: ").append(businessState).append("\n");
    toStr.append("Business Zip: ").append(businessZip).append("\n");
    toStr.append("Business Phone: ").append(businessPhone).append("\n");
    toStr.append("Business Fax: ").append(businessFax).append("\n");
    toStr.append("Business Email: ").append(businessEmail).append("\n");

    return toStr.toString();
  }

}

