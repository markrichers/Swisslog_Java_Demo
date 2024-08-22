package net.javaguides.springboot_blog_webapp.service;

import net.javaguides.springboot_blog_webapp.dto.RegistrationDto;
import net.javaguides.springboot_blog_webapp.entity.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    User findByEmail(String email);
}
