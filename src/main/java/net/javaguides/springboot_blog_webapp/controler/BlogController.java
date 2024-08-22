package net.javaguides.springboot_blog_webapp.controler;

import net.javaguides.springboot_blog_webapp.dto.CommentDto;
import net.javaguides.springboot_blog_webapp.dto.PostDto;
import net.javaguides.springboot_blog_webapp.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BlogController {

    private PostService postService;

    public BlogController(PostService postService) {
        this.postService = postService;
    }

    // handler method to handle http://localhost:8080/
    @GetMapping("/")
    public String viewBlogPosts(Model model) {
        List<PostDto> postResponse = postService.findAllPosts();
        // pass the parameter to the link espression
        model.addAttribute("postsResponse", postResponse);
        // return the template view_post  HTML
        return "blog/view_posts";
    }

    // handler method to handle view post request
    @GetMapping("/post/{postUrl}")
    private String showPost(@PathVariable("postUrl") String postUrl,
                            Model model) {
        PostDto post = postService.findPostByUrl(postUrl);
        CommentDto commentDto = new CommentDto();
        // pass the parameter to the link espression
        model.addAttribute("post", post);
        model.addAttribute("comment", commentDto);
        // return the template blog_post  HTML template
        return "blog/blog_post";
    }
    // handler method to handle blog search bar
    // localhost:8080/page/search?query=java
    @GetMapping("/page/search")
    public String searchPosts(@RequestParam(value = "query") String query,
                              Model model){
        List<PostDto> postsResponse = postService.searchPosts(query);
        // pass the parameter to the link espression
        model.addAttribute("postsResponse", postsResponse);
        // return the template view_post  HTML template
        return "blog/view_posts";
    }

}
