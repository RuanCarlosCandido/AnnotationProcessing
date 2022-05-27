package org.models;

import org.models.annotations.Init;
import org.models.annotations.JSONElement;
import org.models.annotations.JSONSerializable;

@JSONSerializable
public class Person {

	@JSONElement
	private String firstName;

	@JSONElement
	private String lastName;

	@JSONElement(key = "personAge")
	private String age;

	private String address;

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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Init
	private void initNames() {
		this.firstName = (this.firstName != null)
				? this.firstName.substring(0, 1).toUpperCase() + this.firstName.substring(1)
				: "";
		this.lastName = (this.lastName != null)
				? this.lastName.substring(0, 1).toUpperCase() + this.lastName.substring(1)
				: "";
	}

}
