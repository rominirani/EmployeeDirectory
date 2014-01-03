/**
 * The Employee Record is modeled in this Entity class. Currently it has the attributes as present over here
 * but it can be enhanced further depending on your needs.
 */
package com.mindstorm.employeesdirectory.entities;

public class Employee implements Comparable<Employee> {

	/**
	 * This is a unique field. This will be used to identify the record. E.g. rirani, nirani, etc
	 */
	private String userId;
	
	/**
	 * Job Title. E.g. Engineer, CEO, etc.
	 */
	private String jobTitleName;
	
	/**
	 * First Name
	 */
	private String firstName;
	
	/**
	 * Last Name
	 */
	private String lastName;
	
	/**
	 * Full Name
	 */
	private String preferredFullName;
	
	/**
	 * Employee Code. Example, some sort of Employee Number, Employee Id or Employee Code
	 */
	private String employeeCode;
	
	/**
	 * Region to which the Employee belongs.
	 */
	private String region;
	
	/**
	 * Phone Number of the contact
	 */
	private String phoneNumber;
	
	/**
	 * Email Address of the Employee
	 */
	private String emailAddress;
	
	/**
	 * Getter and Setter Methods below for all the attributes
	 * 
	 */

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getJobTitleName() {
		return jobTitleName;
	}

	public void setJobTitleName(String jobTitleName) {
		this.jobTitleName = jobTitleName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPreferredFullName() {
		return preferredFullName;
	}

	public void setPreferredFullName(String preferredFullName) {
		this.preferredFullName = preferredFullName;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Useful method on the entity class to use standard Collections.sort() method. The contact records present 
	 * in the collection will be sorted in ascending order of Full Name.
	 */
	@Override
	public int compareTo(Employee o) {
		return (this.getPreferredFullName().compareTo(o.getPreferredFullName()));
	}
}
