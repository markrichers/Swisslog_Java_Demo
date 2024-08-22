package net.javaguides.springboot_blog_webapp.controler;

import jakarta.validation.Valid;
import net.javaguides.springboot_blog_webapp.dto.CommentDto;
import net.javaguides.springboot_blog_webapp.dto.PostDto;
import net.javaguides.springboot_blog_webapp.service.PostService;
import net.javaguides.springboot_blog_webapp.service.CommentService;
import net.javaguides.springboot_blog_webapp.util.ROLE;
import net.javaguides.springboot_blog_webapp.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private PostService postService;
    private CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/admin/posts")
    public String posts(Model model){
        System.out.println("Entering posts method");
        String role = SecurityUtils.getRole();
        List<PostDto> posts = null;
        if(ROLE.ROLE_ADMIN.name().equals(role)){
           posts = postService.findAllPosts();
        }else{
            posts = postService.findPostsByUser();
        }
        model.addAttribute("posts", posts);
        System.out.println("Exiting posts method");
        return "admin/posts";
    }
    // handler method to handle list comments request
    @GetMapping("/admin/posts/comments")
    public String postComments(Model model){
        String role = SecurityUtils.getRole();
        List<CommentDto> comments = null;
        if(ROLE.ROLE_ADMIN.name().equals(role)){
            comments = commentService.findAllComments();
        }else{
            comments = commentService.findCommentsByPost();
        }
        model.addAttribute("comments", comments);
        return "admin/comments";
    }
    // handler method to handle delete comment request
    @GetMapping("/admin/posts/comments/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteComment(commentId);
        return "redirect:/admin/posts/comments";
    }
    // handler method to handle new post request
    @GetMapping("admin/posts/newpost")
    public String newPostForm(Model model){
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "admin/create_post";
    }

    @PostMapping("/admin/posts")
    public String createdPost(@Valid @ModelAttribute PostDto postDto,
                              BindingResult result,
                              Model model) {
        if(result.hasErrors()){
            model.addAttribute("post", postDto);
            return "admin/create_post";
        }
        postDto.setUrl(getUrl((postDto.getTitle())));
        postService.createPost(postDto);
        return "redirect:/admin/posts";
    }

    // handler method to handle edit post request
    @GetMapping("/admin/posts/{postId}/edit")
    public String editPostForm(@PathVariable("postId") Long postID, Model model){
        PostDto postDto = postService.findPostById(postID);
        model.addAttribute("post", postDto);
        return "admin/edit_post";
    }

    // handler method to handle edit post form submit request
    @PostMapping("/admin/posts/{postId}")
    public String updatePost(@PathVariable("postId")Long postId,
                             @Valid @ModelAttribute PostDto post,
                             BindingResult result,
                             Model model){
        if(result.hasErrors()){
            model.addAttribute("post", post);
            return "admin/edit_post";
        }

        post.setId(postId);
        postService.updatePost(post);
        return "redirect:/admin/posts";
    }

    //handler method to handle delete
    @GetMapping("/admin/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId)
    {
        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }


    // handler method to handle view post request
    @GetMapping("/admin/posts/{postUrl}/view")
    public String viewPost(@PathVariable("postUrl") String postUrl,
                           Model model){
        PostDto postDto = postService.findPostByUrl(postUrl);
        model.addAttribute("post", postDto);
        return "admin/view_post";

    }
    // handler method to handle search blog
    // localhost:8080/admin/post/search?query=java
    @GetMapping("/admin/posts/search")
    public String searchPosts(@RequestParam(value = "query", required = true) String query,
                              Model model) {
            List<PostDto> posts = postService.searchPosts(query);
            model.addAttribute("posts", posts);
//            model.addAttribute("searchQuery", query);
        return "admin/posts"; // Make sure this matches your view name exactly
    }


    private static String getUrl(String postTitle){
        //OOPs Concept Explained Java
        // oops-concepts-explained-in-java
        String title = postTitle.trim().toLowerCase();
        String url = title.replaceAll("\\s+", "-");
        url = url.replaceAll("[^A-Za-z0-9]", "-");
        return url;
    }
}
