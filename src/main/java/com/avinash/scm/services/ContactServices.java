package com.avinash.scm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avinash.scm.entites.Contact;
import com.avinash.scm.repository.ContactRepository;

@Service
@Transactional
public class ContactServices {
	
	@Autowired
	private ContactRepository contactRepository;
	
	public void deleteByContact(Integer id) {
		this.contactRepository.deleteById(id);
	}

}
