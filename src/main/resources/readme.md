Spring Boot pagination
last modified July 6, 2020

Spring Boot pagination tutorial shows how to paginate data in a Spring application.

Spring is a popular Java application framework and Spring Boot is an evolution of Spring that helps create stand-alone, production-grade Spring based applications easily.

Pagination
Pagination is the process of dividing data into suitable chunks to save resources.

PagingAndSortingRepository
PagingAndSortingRepository is an extension of CrudRepository to provide additional methods to retrieve entities using pagination and sorting.

Spring Boot paginate example
In the following application, we create a simple Spring Boot Restful application which allows to paginate data.

pom.xml
src
├───main
│   ├───java
│   │   └───com
│   │       └───zetcode
│   │           │   Application.java
│   │           ├───controller
│   │           │       MyController.java
│   │           ├───model
│   │           │       Country.java
│   │           ├───repository
│   │           │       CountryRepository.java
│   │           └───service
│   │                   CountryService.java
│   │                   ICountryService.java
│   └───resources
│           application.properties
│           import.sql
└───test
└───java
This is the project structure.

pom.xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
http://maven.apache.org/xsd/maven-4.0.0.xsd">
<modelVersion>4.0.0</modelVersion>

    <groupId>com.zetcode</groupId>
    <artifactId>repositoryex</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.2.RELEASE</version>
    </parent>

    <dependencies>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
This is the Maven build file. The h2 dependency includes the H2 database driver.

Spring Boot starters are a set of convenient dependency descriptors which greatly simplify Maven configuration. The spring-boot-starter-parent has some common configurations for a Spring Boot application. The spring-boot-starter-web enables web applications, both classic and RESTFul. The spring-boot-starter-web-freemarker is a starter for building web applications with Freemarker template engine. It uses Tomcat as the default embedded container. The spring-boot-starter-data-jpa is a starter for using Spring Data JPA with Hibernate.

The spring-boot-maven-plugin provides Spring Boot support in Maven, allowing us to package executable JAR or WAR archives. Its spring-boot:run goal runs the Spring Boot application.

resources/application.properties
spring.main.banner-mode=off
spring.jpa.database=h2
spring.jpa.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
In the application.properties file we write various configuration settings of a Spring Boot application. With the banner-mode property we turn off the Spring banner.

The JPA database value specifies the target database to operate on. We specify the Hibernate dialect, org.hibernate.dialect.H2Dialect in our case. The ddl-auto is the data definition language mode; the create-drop option automatically creates and drops the database schema. The H2 database is run in memory.

resources/import.sql
INSERT INTO countries(name, population) VALUES('China', 1382050000);
INSERT INTO countries(name, population) VALUES('India', 1313210000);
INSERT INTO countries(name, population) VALUES('USA', 324666000);
INSERT INTO countries(name, population) VALUES('Indonesia', 260581000);
INSERT INTO countries(name, population) VALUES('Brazil', 207221000);
INSERT INTO countries(name, population) VALUES('Pakistan', 196626000);
INSERT INTO countries(name, population) VALUES('Nigeria', 186988000);
INSERT INTO countries(name, population) VALUES('Bangladesh', 162099000);
INSERT INTO countries(name, population) VALUES('Russia', 146838000);
INSERT INTO countries(name, population) VALUES('Japan', 126830000);
INSERT INTO countries(name, population) VALUES('Mexico', 122273000);
INSERT INTO countries(name, population) VALUES('Philippines', 103738000);
INSERT INTO countries(name, population) VALUES('Ethiopia', 101853000);
INSERT INTO countries(name, population) VALUES('Vietnam', 92700000);
INSERT INTO countries(name, population) VALUES('Egypt', 92641000);
INSERT INTO countries(name, population) VALUES('Germany', 82800000);
INSERT INTO countries(name, population) VALUES('the Congo', 82243000);
INSERT INTO countries(name, population) VALUES('Iran', 82800000);
INSERT INTO countries(name, population) VALUES('Turkey', 79814000);
INSERT INTO countries(name, population) VALUES('Thailand', 68147000);
INSERT INTO countries(name, population) VALUES('France', 66984000);
INSERT INTO countries(name, population) VALUES('United Kingdom', 60589000);
INSERT INTO countries(name, population) VALUES('South Africa', 55908000);
INSERT INTO countries(name, population) VALUES('Myanmar', 51446000);
INSERT INTO countries(name, population) VALUES('South Korea', 68147000);
INSERT INTO countries(name, population) VALUES('Colombia', 49129000);
INSERT INTO countries(name, population) VALUES('Kenya', 47251000);
INSERT INTO countries(name, population) VALUES('Spain', 46812000);
INSERT INTO countries(name, population) VALUES('Argentina', 43850000);
INSERT INTO countries(name, population) VALUES('Ukraine', 42603000);
INSERT INTO countries(name, population) VALUES('Sudan', 41176000);
INSERT INTO countries(name, population) VALUES('Algeria', 40400000);
INSERT INTO countries(name, population) VALUES('Poland', 38439000);
INSERT INTO countries(name, population) VALUES('Canada', 37742154);
INSERT INTO countries(name, population) VALUES('Morocco', 36910560);
INSERT INTO countries(name, population) VALUES('Saudi Arabia', 34813871);
INSERT INTO countries(name, population) VALUES('Uzbekistan', 33469203);
INSERT INTO countries(name, population) VALUES('Peru', 32971854);
INSERT INTO countries(name, population) VALUES('Angola', 32866272);
INSERT INTO countries(name, population) VALUES('Malaysia', 32365999);
INSERT INTO countries(name, population) VALUES('Mozambique', 31255435);
INSERT INTO countries(name, population) VALUES('Ghana', 31072940);
INSERT INTO countries(name, population) VALUES('Yemen', 29825964);
INSERT INTO countries(name, population) VALUES('Nepal', 29136808);
INSERT INTO countries(name, population) VALUES('Venezuela', 28435940);
The schema is automatically created by Hibernate; later, the import.sql file is executed to fill the table with data.

com/zetcode/model/Country.java
package com.zetcode.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int population;

    public Country() {
    }

    public Country(Long id, String name, int population) {
        this.id = id;
        this.name = name;
        this.population = population;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return population == country.population &&
                Objects.equals(id, country.id) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, population);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Country{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", population=").append(population);
        sb.append('}');
        return sb.toString();
    }
}
This is the Country entity. Each entity must have at least two annotations defined: @Entity and @Id. Previously, we have set the ddl-auto option to create-drop which means that Hibernate will create the table schema from this entity.

@Entity
@Table(name = "countries")
public class Country {
The @Entity annotation specifies that the class is an entity and is mapped to a database table. The @Table annotation specifies the name of the database table to be used for mapping.

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
The @Id annotation specifies the primary key of an entity and the @GeneratedValue gives the generation strategy for the values of primary keys.

com/zetcode/repository/CountryRepository.java
package com.zetcode.repository;

import com.zetcode.model.Country;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {

}
CountryRepository is decorated with the @Repository annotation. By extending from the Spring PagingAndSortingRepository, we have some methods to paginate data.

com/zetcode/service/ICountryService.java
package com.zetcode.service;

import com.zetcode.model.Country;
import java.util.List;

public interface ICountryService {

    List<Country> findPaginated(int pageNo, int pageSize);
}
ICountryService contains the findPaginated() contract method. It contains two parameters: the page number and the page size.

com/zetcode/service/CountryService.java
package com.zetcode.service;

import com.zetcode.model.Country;
import com.zetcode.repository.CountryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CountryService implements ICountryService {

    @Autowired
    private CountryRepository repository;

    @Override
    public List<Country> findPaginated(int pageNo, int pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Country> pagedResult = repository.findAll(paging);

        return pagedResult.toList();
    }
}
CountryService contains the implementation of the findPaginated() method.

@Autowired
private CountryRepository repository;
CountryRepository is injected with the @Autowired annotation.

Pageable paging = PageRequest.of(pageNo, pageSize);
Page<Country> pagedResult = repository.findAll(paging);
A PageRequest is created from the supplied values and passed to the findAll() repository method.

com/zetcode/controller/MyController.java
package com.zetcode.controller;

import com.zetcode.model.Country;
import com.zetcode.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    private ICountryService countryService;

    @GetMapping("/countries/{pageNo}/{pageSize}")
    public List<Country> getPaginatedCountries(@PathVariable int pageNo, 
            @PathVariable int pageSize) {

        return countryService.findPaginated(pageNo, pageSize);
    }
}
MyController handles a request from the client.

@Autowired
private ICountryService countryService;
ICountryService is injected into the countryService field.

@GetMapping("/countries/{pageNo}/{pageSize}")
public List<Country> getPaginatedCountries(@PathVariable int pageNo,
@PathVariable int pageSize) {

    return countryService.findPaginated(pageNo, pageSize);
}
We provide the page number and page size as path variables. The values are passed to the findPaginated() service method.

com/zetcode/Application.java
package com.zetcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application  {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
Application is the entry point which sets up Spring Boot application.

$ mvn -q spring-boot:run
We run the application.

$ curl localhost:8080/countries/0/5
[{"id":1,"name":"China","population":1382050000},{"id":2,"name":"India","population":1313210000},
{"id":3,"name":"USA","population":324666000},{"id":4,"name":"Indonesia","population":260581000},
{"id":5,"name":"Brazil","population":207221000}]
We get the first page of 5 rows. The indexing starts from 0.

$ curl localhost:8080/countries/1/5
[{"id":6,"name":"Pakistan","population":196626000},{"id":7,"name":"Nigeria","population":186988000},
{"id":8,"name":"Bangladesh","population":162099000},{"id":9,"name":"Russia","population":146838000},
{"id":10,"name":"Japan","population":126830000}]
We get the next page.

In this tutorial, we have shown how to create pagination in a Spring Boot application.