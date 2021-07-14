#JPA Query docs

## Custom Finder Method/Derived Query Methods
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference



@Query Annotaion provide write sql query

1 JPQL
2 navtive query

	--JPQL OR HQL--
	@Query("select u FROM User u")
	public List<User> getAllUser();
	
	--parameterized query JPQL OR HQL--
	@Query("select u FROM User u WHERE u.name =:n and u.city =:c")
	public List<User> getUserByName(@Param("n") String name, @Param("c") String city);
	
	---Native Query--
	@Query(value="select * from User", nativeQuery = true)
	public List<User> getUsers();
	