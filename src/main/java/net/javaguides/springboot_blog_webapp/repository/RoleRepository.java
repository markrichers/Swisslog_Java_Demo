package net.javaguides.springboot_blog_webapp.repository;

import net.javaguides.springboot_blog_webapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
