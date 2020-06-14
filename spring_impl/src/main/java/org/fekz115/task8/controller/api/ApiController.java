package org.fekz115.task8.controller.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fekz115.task8.domain.Product;
import org.fekz115.task8.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserService userService;

    private final Logger logger = LogManager.getLogger();

    public ApiController(UserService userService) {
        this.userService = userService;
    }

}
