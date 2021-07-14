https://www.youtube.com/watch?v=rqQ602S3cdc&list=PL0zysOflRCelmjxj-g4jLr3WKraSU_e8q&index=28

# Thymeleaf Template Engine

## It is java server side Template engine

Thymeleaf is a modern server-side Java template engine for both web and standalone environments, capable of processing HTML, XML, JavaScript, CSS and even plain text;

Thymeleaf's main goal is to to provide an elegant and highly maintainable way of creating templates.

Mostly used to generate html views for web applications.

Example: index.html

HTML + Thymeleaf Expression

1 we can access java code, object and spring beans.

Thmeleaf Engine will parse Thymeleaf Template

#Example:
<p th:text "${name}">

    Note: name --> Java Data Generate

    <p>Avinash</p>
    

#for loop
<li th:each="n,status: ${names}">
		<span 
		th:style="${status.odd} ? 'background-color:yellow':' '" 
		th:text="${status.index}+' '+${n}"></span>
</li> 

#Elvis Operator

<h2>User is <span th:text="${isActive}?'ACTIVE' : 'NOT ACTIVE' "></span></h2>


#if -unless

th:if  =>ture

th:unless  =>false


#switch -case   

	 <div th:switch="${list.size()}">
	    <h2 th:case="'0'">List content zero element</h2>
	    <h2 th:case="'1'">List content one element</h2>
	    <h2 th:case="*">List content more then one element</h2>
	    
	 </div>

#Fragment
fragment is part of small element or block

include, update, insert

<footer id="replace" th:replace="footer::footer1(${title},${subtitle})">
	</footer>
	
<!-- 	<footer id="insert" th:insert="footer::footer1">
	</footer>
	
	<footer id="include" th:include="footer::footer1">
	</footer> -->
	
	
#Inherit Template

<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org" th:fragment="layout(content)">
<head>
	<meta charset="UTF-8" />
	<title>Base page</title>
	<link rel="stylesheet" th:href="@{/style.css}" />
</head>
<body>
    <h1>This common header</h1>
    
	<div th:replace="${content}"></div>
	
	<h1>This common Footer</h1>
</body>
</html>




