package net.javaguides.springboot_blog_webapp.service;


import net.javaguides.springboot_blog_webapp.dto.CommentDto;

import java.util.List;
public interface CommentService {
    void createComment(String postUrl, CommentDto commentDto);
    List<CommentDto> findAllComments();
    void deleteComment(Long commentID);

    List<CommentDto> findCommentsByPost();
}
