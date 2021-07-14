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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
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
}
