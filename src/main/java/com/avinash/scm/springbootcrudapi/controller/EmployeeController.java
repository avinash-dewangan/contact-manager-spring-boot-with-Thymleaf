package com.avinash.scm.springbootcrudapi.controller;

import com.avinash.scm.springbootcrudapi.model.Employee;
import com.avinash.scm.springbootcrudapi.service.EmployeeService;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;


	@GetMapping("/employee")
	public List<Employee> get(){
		return employeeService.get();
	}

	@PostMapping("/employee")
	public Employee save(@RequestBody Employee employeeObj) {
		employeeService.save(employeeObj);
		return employeeObj;
	}

	@GetMapping("/employee/{id}")
	public Employee get(@PathVariable int id) {
		Employee employeeObj = employeeService.get(id);
		if(employeeObj== null) {
			throw new RuntimeException("Employee with id: "+id +"is not found");
		}
		return employeeObj;
	}

	@GetMapping("/employee/{id}/{url}")
	public String get(@PathVariable String id, @PathVariable String url) {
		URL basePath = Objects.requireNonNull(EmployeeController.class.getResource("/"));

		String currentDirectory = System.getProperty("user.dir");
		System.out.println("The current working directory is " + currentDirectory);
		String CertUrl= url+"="+id+"&DuplicateCopy=0";
		//path where we want to get QR Code
		String path = currentDirectory + id+".png";

		ByteArrayOutputStream stream = QRCode.from(CertUrl)
				.to(ImageType.JPG).stream();
		try {
			FileOutputStream fileStream = new FileOutputStream(new File(
					path));
			fileStream.write(stream.toByteArray());
			fileStream.flush();
			fileStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
}

	@DeleteMapping("/employee/{id}")
	public String delete(@PathVariable int id) {
		employeeService.delete(id);
		return "Employee has been deleted with id :"+id;
	}

	@PutMapping("/employee")
	public Employee Update(@RequestBody Employee employeeObj) {
		employeeService.save(employeeObj);
		return employeeObj;
	}


}
