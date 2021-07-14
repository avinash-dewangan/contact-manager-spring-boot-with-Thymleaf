package com.avinash.scm.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="tbl_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Name must be required")
	@Size(min = 2, max = 20, message = "between 2 to 20")
	private String name;
	
	@Column(unique = true)
	private String email;

	private String password;
	private String role;
	private boolean enabled;
	private String imageUrl;
	
	@Column(length = 50)
	private String about;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="user", orphanRemoval = true)
	private List<Contact> contacts = new ArrayList<>();
}
