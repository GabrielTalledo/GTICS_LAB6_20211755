package com.example.lab6_20211755.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping(value = {"/loginForm"})
    public String loginForm(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("usuario") == null){
            return "loginWindow";
        }else{
            return "redirect:/mesa/list";
        }
    }

    @GetMapping(value = {" ","/"})
    public String notLogged(HttpServletRequest request){
        HttpSession session = request.getSession();
        return "redirect:/mesa/list";
    }

}
