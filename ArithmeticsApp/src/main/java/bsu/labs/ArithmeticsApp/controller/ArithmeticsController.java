package bsu.labs.ArithmeticsApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ArithmeticsController {
    @GetMapping("/mainPage")
    public String mainPage(Model model) {
        return "index";
    }
}
