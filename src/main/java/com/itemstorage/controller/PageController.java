package com.itemstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "redirect:/main";
    }

    @GetMapping("/dictionary")
    public String dictionary() {
        return "dictionary";
    }

    @GetMapping("/instructions/user")
    public String userInstructions() {
        return "instructions/user";
    }

    @GetMapping("/instructions/admin")
    public String adminInstructions() {
        return "instructions/admin";
    }

    @GetMapping("/audit/history")
    public String auditHistory() {
        return "audit/history";
    }
}
