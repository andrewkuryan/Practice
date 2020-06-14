package org.fekz115.task8.controller.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fekz115.task8.domain.User;
import org.fekz115.task8.service.UserService;
import org.fekz115.task8.service.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

    private final UserService userService;

    private final Logger logger = LogManager.getLogger();

    public ViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "index"})
    public String index(Model model){
        model.addAttribute("page", "index");
        return "common_page";
    }

    @GetMapping("registration")
    public String registration(Model model) {
        model.addAttribute("page", "registration");
        return "common_page";
    }

    @PostMapping("registration")
    public String postRegistration(
            User user,
            Model model
    ) {
        try {
            userService.register(user);
            model.addAttribute("message", "Registration Successful");
            model.addAttribute("page", "login");
        } catch (ServiceException e) {
            model.addAttribute("exception", e);
            model.addAttribute("page", "registration");
        }
        return "common_page";
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("page", "login");
        return "common_page";
    }

}
