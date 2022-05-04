package com.avinash.scm.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.avinash.scm.entites.Contact;
import com.avinash.scm.entites.User;
import com.avinash.scm.helper.Message;
import com.avinash.scm.repository.ContactRepository;
import com.avinash.scm.repository.UserRepository;
import com.avinash.scm.services.ContactServices;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private ContactServices contactServices;

	// method for every handler
	@ModelAttribute
	public void addCommandData(Model model, Principal principal) {
		String username = principal.getName();
		System.out.println(username);

		// get the user name and email
		User user = userRepository.getUserByUserName(username);
		System.out.println(user);

		model.addAttribute("user", user);
	}

	@GetMapping("/index")
	public String dashboard(Model model, Principal principal) {
		return "user/user_dashboard";
	}

	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		model.addAttribute("title", "Add Contact");
		model.addAttribute(new Contact());
		return "user/add_contact_form";
	}

	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImg") MultipartFile mfile,
			Principal principal, HttpSession session) {

		try {
			String name = principal.getName();
			User user = this.userRepository.getUserByUserName(name);

			// upload image and processing
			if (!mfile.isEmpty()) {
				// upload the file to folder
				contact.setcImage(mfile.getOriginalFilename());

				File savefile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + mfile.getOriginalFilename());

				Files.copy(mfile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				System.out.println("img upload");
			} else {
				contact.setcImage("contact.png");
			}

			contact.setUser(user);
			user.getContacts().add(contact);

			this.userRepository.save(user);
			System.out.println(contact);
			session.setAttribute("message", new Message("added Successfully", "alert-success"));

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Not submitted", "alert-danger"));

		}
		return "user/add_contact_form";
	}

	// show contacts handler

	/**
	 * per page = 5[n] current page = 0[page]
	 * 
	 * 
	 */

	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Model model, Principal principal) {

		model.addAttribute("title", "Show Contacts");

		// have to send contacts list
		String name = principal.getName();
		User user = this.userRepository.getUserByUserName(name);

		Pageable pageable = PageRequest.of(page, 2);

		Page<Contact> contacts = this.contactRepository.findContactsByUser(user.getId(), pageable);

		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());

		return "user/show_contacts";
	}

	// show contact details
	@GetMapping("/{cid}/contact")
	public String showContactDetail(@PathVariable("cid") Integer cid, Model model, Principal principal) {

		try {
			System.out.println(cid);

			Optional<Contact> contactOpt = this.contactRepository.findById(cid);

			if (contactOpt.isPresent()) {
				Contact contact = contactOpt.get();

				String name = principal.getName();
				User user = this.userRepository.getUserByUserName(name);

				if (user.getId() == contact.getUser().getId()) {
					model.addAttribute("contact", contact);
					model.addAttribute("title", contact.getcName());

				} else {
					model.addAttribute("Permission", "You don't have permission to see this contact");
				}
			} else {
				model.addAttribute("NotFound", "Data Not Found");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "user/contact_detail";
	}

	// delete contact
	// problem not resolve TODO

	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cid, Principal principal, Model model,
			HttpSession httpSession) {

		try {
			Contact contact = this.contactRepository.findById(cid).get();

			if (contact != null) {
				User user = this.userRepository.getUserByUserName(principal.getName());

				if (user.getId() == contact.getUser().getId()) {
					user.getContacts().remove(contact);
					this.userRepository.save(user);

					// delete pic
					// delete old pic
					File deleteFile = new ClassPathResource("static/img").getFile();
					File file1 = new File(deleteFile, contact.getcImage());
					file1.delete();

					httpSession.setAttribute("message", new Message("Contact deleted successfully", "alert-success"));
				}
			}

		} catch (Exception e) {
			e.getStackTrace();
		}

		return "redirect:/user/show-contacts/0";

	}

	// update form
	@PostMapping("/update-contact-form/{cid}")
	public String UpdateContactForm(@PathVariable("cid") Integer cid, Principal principal, Model model,
			HttpSession httpSession) {

		Contact contact = this.contactRepository.findById(cid).get();

		model.addAttribute("title", "Update Form");
		model.addAttribute("contact", contact);

		return "user/update_contact_form";
	}

	// update contact update handler
	@RequestMapping(value = "/process-contact-update", method = RequestMethod.POST)
	public String updateContactHandler(@ModelAttribute Contact contact, @RequestParam("profileImg") MultipartFile mfile,
			Principal principal, HttpSession session) {

		try {

			// old contact
			Contact oldContact = this.contactRepository.findById(contact.getCid()).get();

			if (!mfile.isEmpty()) {
				// rewrite
				// delete old pic
				File deleteFile = new ClassPathResource("static/img").getFile();
				File file1 = new File(deleteFile, oldContact.getcImage());
				file1.delete();

				// update new pic

				File savefile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(savefile.getAbsolutePath() + File.separator + mfile.getOriginalFilename());

				Files.copy(mfile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				contact.setcImage(mfile.getOriginalFilename());

			} else {
				contact.setcImage(oldContact.getcImage());
			}

			User user = this.userRepository.getUserByUserName(principal.getName());

			contact.setUser(user);
			this.contactRepository.save(contact);

			session.setAttribute("message", new Message("Updated Successfully...", "alert-success"));

		} catch (Exception e) {

		}

		return "redirect:/user/" + contact.getCid() + "/contact";
	}
	
	
	//your profile handler
	@GetMapping("/profile")
	public String yourProfile() {
		return "user/profile";
	}

}
