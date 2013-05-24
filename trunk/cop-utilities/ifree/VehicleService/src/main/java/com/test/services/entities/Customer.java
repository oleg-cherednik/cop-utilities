package com.test.services.entities;

import java.util.Date;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.test.services.jaxb.adapter.JaxBDateAdapter;

@XmlRootElement(name = "customer")
public class Customer extends Entity {
	/*
	 * DB table fields `id` varchar(45) NOT NULL, `first_name` varchar(45)
	 * DEFAULT NULL, `last_name` varchar(45) DEFAULT NULL, `phone` varchar(45)
	 * DEFAULT NULL, `mail` varchar(45) DEFAULT NULL, `adress` varchar(45)
	 * DEFAULT NULL, `contract_id` varchar(45) DEFAULT NULL,
	 * `contract_expire_date` date DEFAULT NULL
	 */

	private String firstName;
	private String lastName;
	private String phone;
	private String mail;
	private String address;
	private String contractId;
	private Date contractExpireDate;	

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@XmlJavaTypeAdapter(JaxBDateAdapter.class)
	public Date getContractExpireDate() {
		return contractExpireDate;
	}

	public void setContractExpireDate(Date contractExpireDate) {
		this.contractExpireDate = contractExpireDate;
	}
}
