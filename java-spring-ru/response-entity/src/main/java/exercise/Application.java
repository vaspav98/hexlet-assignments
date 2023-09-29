package exercise;

import java.util.List;
import java.util.Optional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> index() {
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(posts.size()))
                .body(posts);
    }

    @GetMapping("/posts/{slug}")
    public ResponseEntity<Post> show(@PathVariable String slug) {
        Optional<Post> maybePost = posts.stream()
                .filter(p -> p.getSlug().equals(slug))
                .findAny();
        if (maybePost.isPresent()) {
            Post post = maybePost.get();
            return ResponseEntity.ok()
                    .body(post);
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> create(@RequestBody Post newPost) {
        posts.add(newPost);
        return ResponseEntity.status(201)
                .body(newPost);
    }

    @PutMapping("/posts/{slug}")
    public ResponseEntity<Post> update(@PathVariable String slug, @RequestBody Post data) {
        Optional<Post> maybePost = posts.stream()
                .filter(p -> p.getSlug().equals(slug))
                .findAny();
        if (maybePost.isPresent()) {
            Post post = maybePost.get();
            post.setSlug(data.getSlug());
            post.setTitle(data.getTitle());
            post.setBody(data.getBody());
            return ResponseEntity.ok()
                    .body(data);
        }
        return ResponseEntity.status(204).body(data);
    }


    // END

    @DeleteMapping("/posts/{slug}")
    public void destroy(@PathVariable String slug) {
        posts.removeIf(p -> p.getSlug().equals(slug));
    }
}











