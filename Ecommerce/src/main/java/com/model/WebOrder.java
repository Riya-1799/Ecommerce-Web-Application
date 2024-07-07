package com.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class WebOrder {

	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalUser getUser() {
		return user;
	}

	public void setUser(LocalUser user) {
		this.user = user;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<WebOrderQuantities> getQuntities() {
		return quntities;
	}

	public void setQuntities(List<WebOrderQuantities> quntities) {
		this.quntities = quntities;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "userid", nullable = false)
	private LocalUser user;

	@ManyToOne(optional = false)
	@JoinColumn(name = "addressid", nullable = false)
	private Address address;

	@OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<WebOrderQuantities> quntities = new ArrayList<>();

}
