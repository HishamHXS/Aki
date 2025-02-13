package com.aki.service;

import com.aki.model.Post;
import com.aki.model.PostDTO;
import com.aki.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostDTO createPost(PostDTO postDTO) {
        Post post = new Post();
        post.setUserID(postDTO.getUserId());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());

        Post savedPost = postRepository.save(post);
        return new PostDTO(savedPost);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<PostDTO> getPostById(Long id) {
        return postRepository.findById(id).map(PostDTO::new);
    }

    public Optional<PostDTO> updatePost(Long id, PostDTO postDTO) {
        return postRepository.findById(id).map(existingPost -> {
            existingPost.setTitle(postDTO.getTitle());
            existingPost.setContent(postDTO.getContent());
            existingPost.setLikes(postDTO.getLikes());
            Post updatedPost = postRepository.save(existingPost);
            return new PostDTO(updatedPost);
        });
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
