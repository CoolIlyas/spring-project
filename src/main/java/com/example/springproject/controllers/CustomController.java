package com.example.springproject.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для изучения способов передачи данных в контроллере.
 */
@RestController
public class CustomController {

    @PostMapping ("api/test={var1}")
    public String test(@PathVariable("var1") String path1,
                       @RequestParam(name = "var2", required = false) String path2,
                       @RequestBody String body,
                       @RequestHeader("head") String header) {
        return "path1 = " + path1 + ", path2 = " + path2 + ", body = " + body + ", header = " + header;
    }
}
