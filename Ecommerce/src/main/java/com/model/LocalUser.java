package com.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "Local User")
public class LocalUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid", nullable = false)
	private long userid;

	@Column(nullable = false, unique = true)
	private String username;
	
	@JsonIgnore
	@Column(nullable = false, length = 1000)
	private String password;

	@Column(nullable = false, unique = true, length = 320)
	private String email;

	@Column(nullable = false)
	private String firstname;

	@Column(nullable = false)
	private String lastname;

	@JsonIgnore
	@OneToMany(mappedBy = "localuser", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Address> address = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy("id desc")
	private List<VerificationToken> verificationtoken = new ArrayList<>();
	
	public Boolean getEmailverified() {
		return emailverified;
	}

	public void setEmailverified(Boolean emailverified) {
		this.emailverified = emailverified;
	}

	@Column(name="email_verified", nullable = false)
	private Boolean emailverified = false;

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public List<VerificationToken> getVerificationtoken() {
		return verificationtoken;
	}

	public void setVerificationtoken(List<VerificationToken> verificationtoken) {
		this.verificationtoken = verificationtoken;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

}