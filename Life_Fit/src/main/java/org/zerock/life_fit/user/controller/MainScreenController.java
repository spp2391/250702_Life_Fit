package org.zerock.life_fit.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainScreenController {

    @GetMapping("mainscreen/main")
    public String mainScreen(){
        return "mainscreen/main";
    }
}
