package com.aki.service;

import com.aki.model.Post;
import com.aki.model.PostDTO;
import com.aki.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post post;
    private PostDTO postDTO;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(1L);
        post.setUserID(12L);
        post.setContent("Test content");
        post.setLikes(0);

        postDTO = new PostDTO(post);
    }

    @Test
    void testCreatePost() {
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDTO createdPost = postService.createPost(postDTO);

        assertNotNull(createdPost);
        assertEquals(post.getContent(), createdPost.getContent());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void testGetAllPosts() {
        when(postRepository.findAll()).thenReturn(List.of(post));

        List<PostDTO> posts = postService.getAllPosts();

        assertFalse(posts.isEmpty());
        assertEquals(1, posts.size());
        assertEquals(post.getContent(), posts.getFirst().getContent());
    }

    @Test
    void testGetPostById() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        Optional<PostDTO> retrievedPost = postService.getPostById(1L);

        assertTrue(retrievedPost.isPresent());
        assertEquals(post.getContent(), retrievedPost.get().getContent());
    }

    @Test
    void testUpdatePost() {
        PostDTO updatedPostDTO = new PostDTO();
        updatedPostDTO.setContent("Updated content");
        updatedPostDTO.setLikes(10);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Optional<PostDTO> updatedPost = postService.updatePost(1L, updatedPostDTO);

        assertTrue(updatedPost.isPresent());
        assertEquals("Updated content", updatedPost.get().getContent());
        assertEquals(10, updatedPost.get().getLikes());
    }

    @Test
    void testDeletePost() {
        doNothing().when(postRepository).deleteById(1L);

        postService.deletePost(1L);

        verify(postRepository, times(1)).deleteById(1L);
    }
}
