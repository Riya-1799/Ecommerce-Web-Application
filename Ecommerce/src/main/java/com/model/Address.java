package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Address {

	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int addressid;

	@Column(name = "address_line_1", length = 300, nullable = false)
	private String addressline1;

	@Column(name = "address_line_2", length = 300)
	private String addressline2;

	@Column(name = "city", length = 75, nullable = false)
	private String City;

	@Column(name = "country", length = 75, nullable = false)
	private String country;

	@ManyToOne(optional = false)
	@JoinColumn(name = "userid", nullable = false)
	private LocalUser localuser;

	public int getAddressid() {
		return addressid;
	}

	public void setAddressid(int addressid) {
		this.addressid = addressid;
	}

	public String getAddressline1() {
		return addressline1;
	}

	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}

	public String getAddressline2() {
		return addressline2;
	}

	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public LocalUser getLocaluser() {
		return localuser;
	}

	public void setLocaluser(LocalUser localuser) {
		this.localuser = localuser;
	}
}
