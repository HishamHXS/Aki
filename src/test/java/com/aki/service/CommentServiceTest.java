package com.aki.service;

import com.aki.model.Comment;
import com.aki.model.CommentDTO;
import com.aki.repository.CommentRepository;
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
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;
    private CommentDTO commentDTO;

    @BeforeEach
    void setUp() {
        comment = new Comment();
        comment.setId(1L);
        comment.setPostID(1L);
        comment.setUserID(12L);
        comment.setContent("Test content");
        comment.setLikes(0);

        commentDTO = new CommentDTO(comment);
    }

    @Test
    void testCreateComment() {
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDTO createdComment = commentService.createComment(commentDTO);

        assertNotNull(createdComment);
        assertEquals(comment.getContent(), createdComment.getContent());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void testGetAllComments() {
        when(commentRepository.findAll()).thenReturn(List.of(comment));

        List<CommentDTO> comments = commentService.getAllComments();

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
        assertEquals(comment.getContent(), comments.getFirst().getContent());
    }

    @Test
    void testGetCommentById() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Optional<CommentDTO> retrievedComment = commentService.getCommentById(1L);

        assertTrue(retrievedComment.isPresent());
        assertEquals(comment.getContent(), retrievedComment.get().getContent());
    }

//    @Test
//    void testGetCommentsByPostId() {
//        when(commentRepository.findById(1L)).thenReturn(List.of(comment));
//
//        List<CommentDTO> comments = commentService.getCommentsByPostId(1L);
//
//        assertFalse(comments.isEmpty());
//        assertEquals(1, comments.size());
//        assertEquals(comment.getContent(), comments.get(0).getContent());
//    }

    @Test
    void testUpdateComment() {
        CommentDTO updatedCommentDTO = new CommentDTO();
        updatedCommentDTO.setContent("Updated content");
        updatedCommentDTO.setLikes(10);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Optional<CommentDTO> updatedComment = commentService.updateComment(1L, updatedCommentDTO);

        assertTrue(updatedComment.isPresent());
        assertEquals("Updated content", updatedComment.get().getContent());
        assertEquals(10, updatedComment.get().getLikes());
    }

    @Test
    void testDeleteComment() {
        doNothing().when(commentRepository).deleteById(1L);

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }
}
