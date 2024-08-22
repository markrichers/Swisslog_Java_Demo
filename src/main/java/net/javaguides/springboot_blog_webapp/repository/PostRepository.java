package net.javaguides.springboot_blog_webapp.repository;

import net.javaguides.springboot_blog_webapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;
public interface PostRepository extends  JpaRepository<Post, Long>{
    Optional<Post> findByUrl(String url);

    @Query(value = "SELECT * FROM posts p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.short_description) LIKE LOWER(CONCAT('%', :query, '%'))",
            nativeQuery = true)
    List<Post> searchPosts(String query);

    @Query(value = "select * from posts p where p.created_by =:userId", nativeQuery=true)
    List<Post> findPostByUser(@Param("userId") Long userId);
}
