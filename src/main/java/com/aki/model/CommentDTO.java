package com.aki.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private long id;
    private long userId;
    private long postId;
    private String content;
    private int likes;
    private LocalDateTime createdAt;

    public CommentDTO(Comment comment) {
        setId(comment.getId());
        setUserId(comment.getUserID());
        setPostId(comment.getPostID());
        setContent(comment.getContent());
        setLikes(comment.getLikes());
        setCreatedAt(comment.getCreatedAt());
    }
}
