package net.javaguides.springboot_blog_webapp.service.impl;

import net.javaguides.springboot_blog_webapp.dto.CommentDto;
import net.javaguides.springboot_blog_webapp.entity.Comment;
import net.javaguides.springboot_blog_webapp.entity.Post;
import net.javaguides.springboot_blog_webapp.entity.User;
import net.javaguides.springboot_blog_webapp.mapper.CommentMapper;
import net.javaguides.springboot_blog_webapp.repository.CommentRepository;
import net.javaguides.springboot_blog_webapp.repository.PostRepository;
import net.javaguides.springboot_blog_webapp.repository.UserRepository;
import net.javaguides.springboot_blog_webapp.service.CommentService;
import net.javaguides.springboot_blog_webapp.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;



    @Override
    public void createComment(String postUrl, CommentDto commentDto){
        Post post = postRepository.findByUrl(postUrl).get();
        Comment comment = CommentMapper.mapToComment(commentDto);
        comment.setPost(post);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> findAllComments(){
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(CommentMapper::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> findCommentsByPost() {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Long userId = createdBy.getId();
        List<Comment> comments = commentRepository.findCommentByPost(userId);
        return comments.stream()
                .map((comment)-> CommentMapper.mapToCommentDto(comment))
                .collect(Collectors.toList());
    }

}
