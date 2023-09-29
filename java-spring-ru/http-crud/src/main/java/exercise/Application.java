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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Post> index(@RequestParam(defaultValue = "10") int limit,
                            @RequestParam(defaultValue = "1") int page) {
        return posts.stream()
                .dropWhile(p -> posts.indexOf(p) < (page - 1) * limit)
                .limit(limit)
                .toList();
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> show(@PathVariable String id) {
        Optional<Post> post = posts.stream()
                .filter(p -> p.getSlug().equals(id))
                .findAny();
        return post;
    }

    @PostMapping("/posts")
    public Post create(@RequestBody Post newPost) {
        posts.add(newPost);
        return newPost;
    }

    @PutMapping("/posts/{id}")
    public Post update(@PathVariable String id, @RequestBody Post data) {
        Optional<Post> maybePost = posts.stream()
                .filter(p -> p.getSlug().equals(id))
                .findAny();
        if (maybePost.isPresent()) {
            Post post = maybePost.get();
            post.setSlug(data.getSlug());
            post.setTitle(data.getTitle());
            post.setBody(data.getBody());
        }
        return data;
    }

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getSlug().equals(id));
    }
    // END
}













