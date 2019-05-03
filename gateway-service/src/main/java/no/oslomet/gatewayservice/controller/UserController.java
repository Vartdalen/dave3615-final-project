package no.oslomet.gatewayservice.controller;

import no.oslomet.gatewayservice.model.User;
import no.oslomet.gatewayservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public String saveUser(@ModelAttribute("user") User newUser) {
        newUser.setRole("USER");
        userService.saveUser(newUser);
        return "redirect:/";
    }
}