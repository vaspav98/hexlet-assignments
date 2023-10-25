package exercise.controller;

import exercise.ResourceNotFoundException;
import exercise.dto.CommentDto;
import exercise.model.Comment;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.module.ResolutionException;


@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    // BEGIN
    @GetMapping("/{postId}/comments")
    public Iterable<Comment> index(@PathVariable Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public Comment show(@PathVariable Long postId, @PathVariable Long commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));
    }

    @PostMapping("/{postId}/comments")
    public Comment create(@PathVariable Long postId, @RequestBody CommentDto data) {
        Comment comment = new Comment();
        comment.setContent(data.content());

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        comment.setPost(post);
        return commentRepository.save(comment);
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public Comment update(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentDto data) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));
        comment.setContent(data.content());
        return commentRepository.save(comment);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public void delete(@PathVariable Long postId, @PathVariable Long commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));
        commentRepository.delete(comment);
    }
    // END
}
