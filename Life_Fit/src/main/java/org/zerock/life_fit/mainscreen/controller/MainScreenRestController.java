package org.zerock.life_fit.mainscreen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainScreenRestController {

    @GetMapping("/mainscreen/keyword")
    public String mainScreen() {
        return "asdf";
    }
}
