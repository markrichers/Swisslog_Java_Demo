package net.javaguides.springboot_blog_webapp.controler;

import jakarta.validation.Valid;
import net.javaguides.springboot_blog_webapp.dto.RegistrationDto;
import net.javaguides.springboot_blog_webapp.entity.User;
import net.javaguides.springboot_blog_webapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //mapping to correct login
    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }
    //handler method to hander user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){

        // this object holds form data
        // add the dto object to model
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        // return the template register HTML
        return "register";
    }
    //handler method to handler user registration form and form submit request
    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult result,
                           Model model){
        User existingUser = userService.findByEmail(user.getEmail());
        if(existingUser != null && existingUser.getEmail() != null && existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null, "There is already a user with same email");
        }
        if(result.hasErrors()){
            model.addAttribute("user",user);
            return "register";
        }
            userService.saveUser(user);
        // return the template registersucess HTML
            return "redirect:/register?success";
    }

}
