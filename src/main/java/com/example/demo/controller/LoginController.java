package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.DuplicateValidateException;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@RequiredArgsConstructor
@Controller
@Slf4j
public class LoginController {

    private final UserService userService;

    @GetMapping("/")
    public String index(){
        log.info("index");
        return "index";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "users/login";
    }

    @GetMapping("/login/result")
    public String loginSuccess(){
        return "users/loginSuccess";
    }

    @GetMapping("/denied")
    public String denied(){
        return "denied";
    }

    @GetMapping("/logout/result")
    public String logout(){
        return "users/logoutSuccess";
    }
    //회원가입
    @GetMapping("/signup")
    public String form(Model model){
        model.addAttribute("userDTO", new UserDTO());
        return "users/signup";
    }

    //회원가입 결과
    @PostMapping("/signup")
    public String createUser(@Valid UserDTO dto, BindingResult result, Model model) throws DuplicateValidateException{
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            System.out.println("회원 비밀번호 다시 확인");
            model.addAttribute("userDTO", dto);
            return "users/signup";
        }
        if(result.hasErrors()){
            return "users/signup";
        }

        userService.create(dto.getUsername(), dto.getPassword(), dto.getCountry());
        return "redirect:/";
    }

    @GetMapping("/userInfo")
    public String userInfo(){
        return "users/userinfo";
    }

}