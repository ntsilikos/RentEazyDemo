package com.RentEazy.demo.Contoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String testControl() {
        return "Hello world!";
    }

}
