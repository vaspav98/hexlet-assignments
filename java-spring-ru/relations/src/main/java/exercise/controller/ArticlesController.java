package exercise.controller;

import exercise.dto.ArticleDto;
import exercise.model.Article;
import exercise.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticlesController {

    private final ArticleRepository articleRepository;

    @GetMapping(path = "")
    public Iterable<Article> getArticles() {
        return articleRepository.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArticle(@PathVariable long id) {
        articleRepository.deleteById(id);
    }

    // BEGIN
    @PostMapping("")
    public Article create(@RequestBody ArticleDto data) {
        Article article = new Article();
        article.setName(data.getName());
        article.setBody(data.getBody());
        article.setCategory(data.getCategory());
        return articleRepository.save(article);
    }

    @PatchMapping("/{id}")
    public Article update(@PathVariable long id, @RequestBody ArticleDto data) {
        Article article = articleRepository.findById(id);
        article.setName(data.getName());
        article.setBody(data.getBody());
        article.setCategory(data.getCategory());
        return articleRepository.save(article);
    }

    @GetMapping("/{id}")
    public Article show(@PathVariable long id) {
        return articleRepository.findById(id);
    }
    // END
}
