package com.uverwolf.auth.controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uverwolf.auth.models.User;
import com.uverwolf.auth.services.UserService;
import com.uverwolf.auth.validations.UserValidator;

@Controller
public class Users {
	@Autowired
	UserService servicios;
	@Autowired
	UserValidator userValidator;
    @GetMapping("/registration")
    public String registerForm(@Valid @ModelAttribute("user") User user) {
        return "registrationPage.jsp";
    }
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result,Model modelo, HttpSession session) {
    	
    	userValidator.validate(user, result);
    	
    	if(result.hasErrors()) {
    		return "registrationPage.jsp";
    	}else {
    		servicios.saveUserWithAdminRole(user);
    		return "redirect:/login";
    	}
    }
    
    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", servicios.findByUsername(username));
        model.addAttribute("usuarios", servicios.todos());
        return "adminPage.jsp";
    }
    @RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "loginPage.jsp";
    }
    @RequestMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
        // 1
        String username = principal.getName();
        model.addAttribute("currentUser", servicios.findByUsername(username));
        return "homePage.jsp";
    }
}
