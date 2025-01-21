package bsu.labs.ArithmeticsApp.controller;

import bsu.labs.ArithmeticsApp.services.ArithmeticService;
import bsu.labs.ArithmeticsApp.utils.InputFolderCleaner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;


@Controller
@Slf4j
public class ArithmeticsController {
    private final ArithmeticService arithmeticService;
    private final InputFolderCleaner cleaner;
    @Autowired
    public ArithmeticsController(ArithmeticService arithmeticService,
                                 InputFolderCleaner cleaner) {
        this.arithmeticService = arithmeticService;
        this.cleaner = cleaner;
    }
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    @PostMapping("/process")
    public String process(@RequestParam("inputData") String inputData,
                          @RequestParam("dataType") String dataType,
                          Model model) throws IOException {
        log.info("Input data: " + inputData);
        log.info("Data type: " + dataType);
        List<String> processedData = arithmeticService.start(inputData, dataType);
        log.info("Processed data: " + processedData);
        model.addAttribute("processedData", processedData);
        return "index";
    }
    @PostMapping("/clear")
    public String clear(Model model) {
        model.addAttribute("processedData", "");
        cleaner.cleanInputFolder();
        return "index";
    }
    @ExceptionHandler(Exception.class)
    public String nullPointerException(Exception e, Model model) {
        model.addAttribute("processedData", e.getMessage());
        return "index";
    }
}
