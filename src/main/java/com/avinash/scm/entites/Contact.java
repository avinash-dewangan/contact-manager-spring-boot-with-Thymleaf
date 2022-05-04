package com.avinash.scm.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="tbl_contact")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cid;
	
	@NotBlank(message = "Name must be required")
	@Size(min = 2, max = 20, message = "between 2 to 20")
	private String cName;
	private String cNicName;
	private String cWork;
	private String cEmail;
	private String cPhone;
	private String cImage;
	
	@Column(length = 1000)
	private String description;
	
	@ManyToOne
	private User user;

	public Contact(java.lang.Integer cid, java.lang.String cName, java.lang.String cNicName, java.lang.String cWork, java.lang.String cEmail, java.lang.String cPhone, java.lang.String cImage, java.lang.String description, User user) {
		this.cid = cid;
		this.cName = cName;
		this.cNicName = cNicName;
		this.cWork = cWork;
		this.cEmail = cEmail;
		this.cPhone = cPhone;
		this.cImage = cImage;
		this.description = description;
		this.user = user;
	}

	public Contact() {

	}

	public java.lang.Integer getCid() {
		return cid;
	}

	public void setCid(java.lang.Integer cid) {
		this.cid = cid;
	}

	public java.lang.String getcName() {
		return cName;
	}

	public void setcName(java.lang.String cName) {
		this.cName = cName;
	}

	public java.lang.String getcNicName() {
		return cNicName;
	}

	public void setcNicName(java.lang.String cNicName) {
		this.cNicName = cNicName;
	}

	public java.lang.String getcWork() {
		return cWork;
	}

	public void setcWork(java.lang.String cWork) {
		this.cWork = cWork;
	}

	public java.lang.String getcEmail() {
		return cEmail;
	}

	public void setcEmail(java.lang.String cEmail) {
		this.cEmail = cEmail;
	}

	public java.lang.String getcPhone() {
		return cPhone;
	}

	public void setcPhone(java.lang.String cPhone) {
		this.cPhone = cPhone;
	}

	public java.lang.String getcImage() {
		return cImage;
	}

	public void setcImage(java.lang.String cImage) {
		this.cImage = cImage;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
