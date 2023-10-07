package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private PostDTO toPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setBody(post.getBody());

        List<Comment> comments = commentRepository.findByPostId(post.getId());
        List<CommentDTO> commentsDTO = comments.stream()
                .map(c -> toCommentDTO(c))
                .toList();

        postDTO.setComments(commentsDTO);

        return postDTO;
    }

    private CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

    @GetMapping("")
    public List<PostDTO> index() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> result = posts.stream()
                .map(p -> toPostDTO(p))
                .toList();
        return result;
    }

    @GetMapping("/{id}")
    public PostDTO show(@PathVariable long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        PostDTO postDTO = toPostDTO(post);
        return postDTO;
    }

}
// END
