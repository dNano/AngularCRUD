package com.example.person.model;


import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data object representing an individual.
 *
 * @author jshea
 */
@XmlRootElement
public class Person implements Serializable {
   private static final long serialVersionUID = 1L;

   private Integer id         = null;
   private String firstName   = "";
   private String lastName      = "";


   /**
    * Constructors
    */
   public Person() { }

   public Person(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
   }

   public Person(Integer id, String firstName, String lastName) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
   }

   public Person(Person p) {
      this.id = p.getId();
      this.firstName = p.getFirstName();
      this.lastName = p.getLastName();
   }


   // Id
   public Integer getId() {
      return id;
   }
   public void setId(Integer userId) {
      this.id = userId;
   }

   // First Name
   public String getFirstName() {
      return firstName;
   }
   public void setFirstName(String firstName) {
      this.firstName = (firstName!=null?firstName:"");
   }

   // Last Name
   public String getLastName() {
      return lastName;
   }
   public void setLastName(String lastName) {
      this.lastName = (lastName!=null?lastName:"");
   }



   /* Utility methods */

   public String getName() {
      return this.getProperCase(firstName+" "+lastName);
   }
   public String getFullName() {
      return getName();
   }



   /**
    * Capitalize first letter of every word.
    * @param str
    * @return
    */
   protected String getProperCase(String str) {
      StringBuffer filter;
      String retVal;

      if (str==null || str.equals("")) {
         retVal = str;
      }
      else {
         str = str.trim().toLowerCase();

         // Convert the String to a StringBuffer for faster manipulation
         filter = new StringBuffer(str.length());
         char c;
         // Loop through each character in the String.
         for (int i=0; i < str.length(); i++) {
            c = str.charAt(i);
            if (i == 0 || str.charAt(i - 1) == ' ') {
               filter.append(String.valueOf(c).toUpperCase());
            }
            else {
               filter.append(c);
            }
         }
         retVal = filter.toString();
      }
      return retVal;

   }


   @Override
   public String toString() {
      return id + ", " + firstName + ", " + lastName;
   }

}