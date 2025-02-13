package com.aki.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private long id;
    private long userId;
    private String title;
    private String content;
    private int likes;
    private LocalDateTime createdAt;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.userId = post.getUserID();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likes = post.getLikes();
        this.createdAt = post.getCreatedAt();
    }
}
