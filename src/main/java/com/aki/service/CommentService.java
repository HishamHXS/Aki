package com.aki.service;

import com.aki.model.Comment;
import com.aki.model.CommentDTO;
import com.aki.repository.CommentRepository;
import io.micrometer.observation.Observation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setPostId(commentDTO.getPostId());
        comment.setUserId(commentDTO.getUserId());
        comment.setContent(commentDTO.getContent());

        Comment savedComment = commentRepository.save(comment);
        return new CommentDTO(savedComment);
    }

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CommentDTO> getCommentById(Long id) {
        return commentRepository.findById(id).map(CommentDTO::new);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId)
                .filter(comments -> !comments.isEmpty())
                .map(comments -> comments.stream()
                    .map(CommentDTO::new)
                    .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public Optional<CommentDTO> updateComment(Long id, CommentDTO commentDTO) {
        return commentRepository.findById(id).map(existingComment -> {
            existingComment.setContent(commentDTO.getContent());
            existingComment.setLikes(commentDTO.getLikes());
            Comment updatedComment = commentRepository.save(existingComment);
            return new CommentDTO(updatedComment);
        });
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
