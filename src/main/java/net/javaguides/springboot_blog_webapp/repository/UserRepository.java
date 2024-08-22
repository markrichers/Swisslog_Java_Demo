package net.javaguides.springboot_blog_webapp.repository;

import net.javaguides.springboot_blog_webapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // CRUD method to perform CRUD datbase from JPARepository
    User findByEmail(String Email);

}
