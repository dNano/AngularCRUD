package com.example.person.model;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/*
 * This is a full person/contact DTO. This is for reference as AngularCRUD only uses the first and last name.
 */

/**
 * Data object representing an individual.
 *
 * @author jshea
 */
@XmlRootElement
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	private Integer userId			= null;
	private String firstName		= "";
	private String lastName			= "";
	private String address			= "";
	private String city				= "";
	private String addrState		= "";
	private String zip				= "";
	private String homePhone		= "";
	private String workPhone		= "";
	private String mobilePhone		= "";
	private String email				= "";
	private Date	birthdayDate	= null;	//	@XmlTransient for JAXB, @Transient for gson
	private String birthday			= "";
	private int		children			= 0;		// Number of children. A sad excuse to get an int involved


	/**
	 * Constructors
	 */
	public Person() { }

	public Person(String firstName, String lastName,
			  String address, String city, String state, String zip,
			  String hPhone, String wPhone, String mPhone,
			  String email, Date birthdayDate, int children) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.addrState = state;
		this.zip = zip;
		this.homePhone = hPhone;
		this.workPhone = wPhone;
		this.mobilePhone = mPhone;
		this.email = email;
		setBirthdayDate(birthdayDate);
		this.children = children;
	}

	public Person(String firstName, String lastName,
			  String address, String city, String state, String zip,
			  String hPhone, String wPhone, String mPhone,
			  String email, String birthday, int children) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.addrState = state;
		this.zip = zip;
		this.homePhone = hPhone;
		this.workPhone = wPhone;
		this.mobilePhone = mPhone;
		this.email = email;
		setBirthday(birthday);
		this.children = children;
	}

	public Person(Person p) {
		this.firstName = p.getFirstName();
		this.lastName = p.getLastName();
		this.address = p.getAddress();
		this.city = p.getCity();
		this.addrState = p.getState();
		this.zip = p.getZip();
		this.homePhone = p.getHomePhone();
		this.workPhone = p.getWorkPhone();
		this.mobilePhone = p.getMobilePhone();
		this.email = p.getEmail();
		this.birthday = p.getBirthday();
		this.children = p.getChildren();
	}


	// Id
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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

	// Home phone number
	public String getHomePhone() {
		return homePhone;
	}
	public String getHomePhoneFormatted() {
		return formatPhoneNumber(homePhone);
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = stripPhoneNumber(homePhone);
	}

	// Work phone number
	public String getWorkPhone() {
		return workPhone;
	}
	public String getWorkPhoneFormatted() {
		return formatPhoneNumber(workPhone);
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = stripPhoneNumber(workPhone);
	}

	// Mobile phone number
	public String getMobilePhone() {
		return mobilePhone;
	}
	public String getMobilePhoneFormatted() {
		return formatPhoneNumber(mobilePhone);
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = stripPhoneNumber(mobilePhone);
	}

	// Address
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
	   this.address = (address!=null?address:"");
	}

	// City
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
	   this.city = (city!=null?city:"");
	}

	// State
	public String getState() {
		return addrState;
	}
	public void setState(String state) {
	   this.addrState = (state!=null?state:"");
	}

	// Zip
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
	   this.zip = (zip!=null?zip:"");
	}

	// Email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
	   this.email = (email!=null?email:"");
	}

	// Birthday Date
	@XmlTransient	// for JAXB - We'll serialize the string format not the Date field
	@Transient		// for gson
	public Date getBirthdayDate() {
		return birthdayDate;
	}
	public void setBirthdayDate(Date birthdayDate) {
	   this.birthdayDate = birthdayDate;

		this.birthday = DATE_FORMATTER.format(birthdayDate);
	}


	// Birthday String format
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		// Check that it's in the correct yyyy-MM-dd format. If so
		// save both Date() and String versions.
		try {
			this.birthdayDate = DATE_FORMATTER.parse(birthday);
			this.birthday = birthday;
		}
		catch (ParseException ex) {
			throw new IllegalArgumentException("Birthday (" + birthday + ") isn't a valid date format (yyyy-MM-dd)");
		}
	}


	// Children
	public int getChildren() {
		return children;
	}
	public void setChildren(int children) {
	   this.children = children;
	}


	/* Utility methods */

	public String getName() {
		return this.getProperCase(firstName+" "+lastName);
	}
	public String getFullName() {
		return getName();
	}

	public String getFullAddress() {
		// TODO this needs to be validated with all variations of fields addr, city,
		// state, zip being filled in & blank, to ensure formatting is correct
		return
			getAddress() + "\n " +
			getCity() +
			(getCity().equals("") || (getState()+getZip()).equals("")?" ":", ") +
			getState() + " " +
			getZip();
	}


	private String stripPhoneNumber(String phoneNumber) {
		String tmpPhone = phoneNumber;

		if (tmpPhone==null || tmpPhone.equals("")) { return ""; }
		tmpPhone = tmpPhone.replace(" ", "");
		tmpPhone = tmpPhone.replace("-", "");
		tmpPhone = tmpPhone.replace(".", "");
		tmpPhone = tmpPhone.replace("(", "");
		tmpPhone = tmpPhone.replace(")", "");
		//phoneNumber.replaceAll("[\s-\.()]", "");
		return tmpPhone;
	}


	private String formatPhoneNumber(String phoneNumber) {
		if (phoneNumber==null || phoneNumber.equals("")) {
			return phoneNumber;
		}
		else if (phoneNumber.length()==7) {
			return phoneNumber.substring(0, 3 )+ "-" + phoneNumber.substring(3, 7);
		}
		return "(" + phoneNumber.substring(0, 3) + ") " + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6, 10);
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
		return userId + ", " + firstName + ", " + lastName + ", " +
			address + ", " + city + ", " + addrState + ", " + zip + ", " +
			homePhone + ", " + mobilePhone + ", " + workPhone + ", " +
			email + ", " + birthday + ", " + children;
	}

}