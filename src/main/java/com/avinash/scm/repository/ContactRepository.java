package com.avinash.scm.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.avinash.scm.entites.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

	//implement pagination...
	
	@Query("from Contact as c where c.user.id = :userId")
	// currentpage page
	// contact per page - 5
	public Page<Contact> findContactsByUser(@Param("userId") Long long1,Pageable pageable);
	
	
	@Query("delete from Contact as c where c.cid = :cid")
	public void deleteById(@Param("cid") Integer cid);
	
}
