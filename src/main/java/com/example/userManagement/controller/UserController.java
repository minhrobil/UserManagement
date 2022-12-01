package com.example.userManagement.controller;

import com.example.userManagement.dto.UserDTO;
import com.example.userManagement.exception.UserException;
import com.example.userManagement.model.User;
import com.example.userManagement.request.LoginRequest;
import com.example.userManagement.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public String showHomePage(Model model, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(userDTO != null){
            model.addAttribute("user", userDTO);
        }
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model){
        model.addAttribute("loginRequest",new LoginRequest("",""));
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute LoginRequest loginRequest, BindingResult result, HttpSession session){
        if(result.hasErrors()){
            return "login";
        }
        User user;
        try {
            user = userService.login(loginRequest.email(),loginRequest.password());
            session.setAttribute("user", new UserDTO(user.getId(), user.getFullname(), user.getEmail()));
            return "redirect:/";
        } catch (UserException ex){
            switch (ex.getMessage()){
                case "User is not found":
                    result.addError(new FieldError("loginRequest", "email", "Email is not exist"));
                    break;
                case "User is not activated":
                    result.addError(new FieldError("loginRequest", "email", "User is not activated"));
                    break;
                case "Password is incorrect":
                    result.addError(new FieldError("loginRequest", "password", "Password is incorrect"));
                    break;
            }
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterPage(){
        return "register";
    }

    @GetMapping("/foo")
    public String foo(){
        throw new UserException("Some error");
    }

    @GetMapping("/admin")
    public String showAdminPage(HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if(userDTO != null){
            return "admin";
        }else{
            return "redirect:/";
        }
    }
}
