package com.avinash.scm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.avinash.scm.entites.User;
import com.avinash.scm.helper.Message;
import com.avinash.scm.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder bCPE;
	
	@Autowired
	private UserRepository userRepository;
	
	// home handler
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home Contact Manager");
		return "home";
	}
	
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("about", "Home Contact Manager");
		return "about";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		
		model.addAttribute("user", new User());
		return "signup";
	}
	
	
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") User user, @RequestParam(value="agrement",defaultValue="false") boolean agrement, Model model, 
	BindingResult result1, HttpSession session) {
		
		try {
			if(!agrement) {
				System.out.println(agrement);	
				throw new Exception("You have not agreed the terms and condtions");
			}
			
			if(result1.hasErrors()) {
				model.addAttribute("user", user);
				System.out.println(result1.toString());
				return "signup";
			}
			
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(bCPE.encode(user.getPassword()));
			
			User result = this.userRepository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully register","alert-success"));
			System.out.println(result);
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something went wrong. " + e.getMessage(),"alert-danger"));
			return "signup";
		}
		
	}

	//handler for custom login
	@GetMapping("/signin")
	public String customLogin(Model model) {
		model.addAttribute("title", "Login page");
		return "login";
	}

	@GetMapping("/getCert")
	public String get(Model model) {
		URL basePath = Objects.requireNonNull(HomeController.class.getResource("/"));
		String CertUrl="avinash";
		ByteArrayOutputStream stream = QRCode.from(CertUrl)
				.to(ImageType.JPG).stream();
		byte[] bytes = stream.toByteArray();
		String encodeBase64 = Base64.encode(bytes);

		String imageUrl = "data:image/jpg;base64," + encodeBase64;
		model.addAttribute("imageUrl", imageUrl);


		return "certificate";
		//<img src="data:image/jpg;base64,${image}"/>
	}

}
