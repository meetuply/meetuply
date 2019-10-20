package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.BlogComment;
import ua.meetuply.backend.model.BlogPost;
import ua.meetuply.backend.service.BlogCommentService;
import ua.meetuply.backend.service.BlogPostService;

import javax.validation.Valid;

@RequestMapping("api/blog")
@Transactional
@RestController
public class BlogController {

    //region Posts

    @Autowired
    BlogPostService blogPostService;

    @GetMapping("/posts")
    public @ResponseBody Iterable<BlogPost> getAllBlogPosts() {
        return blogPostService.getBlogPosts();
    }

    @GetMapping("/{post-id}")
    public BlogPost getBlogPost(@PathVariable("post-id") Integer blogPostId) {
        return blogPostService.getBlogPostById(blogPostId);
    }

    @PostMapping("/create")
    public ResponseEntity<BlogPost> createNewBlogPost(@Valid @RequestBody BlogPost blogPost){
        blogPostService.createBlogPost(blogPost);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{post-id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable("post-id") Integer blogPostId,
                                                   @RequestBody BlogPost blogPost) {
        if (blogPostService.getBlogPostById(blogPostId) == null) {
            return ResponseEntity.badRequest().build();
        }
        blogPostService.updateBlogPost(blogPost);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{post-id}")
    public ResponseEntity<BlogPost> deleteBlogPost(@PathVariable("post-id") Integer blogPostId){
        if (blogPostService.getBlogPostById(blogPostId) == null) {
            return ResponseEntity.badRequest().build();
        }
        blogPostService.deleteBlogPost(blogPostId);
        return ResponseEntity.ok().build();
    }
    //endregion

    //region Comments
    @Autowired
    BlogCommentService blogCommentService;

    @GetMapping("/{post-id}/comments")
    public @ResponseBody Iterable<BlogComment> getBlogPostComments(@PathVariable("post-id") Integer blogPostId) {
        return blogCommentService.getBlogCommentsByPostId(blogPostId);
    }

    @GetMapping("/comments/{comment-id}")
    public BlogComment getBlogComment(@PathVariable("comment-id") Integer blogCommentId) {
        return blogCommentService.getBlogCommentById(blogCommentId);
    }

    @PostMapping("/{post-id}/comments/create")
    public ResponseEntity<BlogComment> createNewBlogComment(@PathVariable("post-id") Integer blogPostId,
                                                            @Valid @RequestBody BlogComment blogComment){
        blogCommentService.createBlogComment(blogComment, blogPostId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/comments/{comment-id}")
    public ResponseEntity<BlogComment> updateBlogComment(@PathVariable("comment-id") Integer blogCommentId,
                                                      @RequestBody BlogComment blogComment) {
        if (blogCommentService.getBlogCommentById(blogCommentId) == null) {
            return ResponseEntity.badRequest().build();
        }
        blogCommentService.updateBlogComment(blogComment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity<BlogComment> deleteBlogComment(@PathVariable("comment-id") Integer blogCommentId){
        if (blogCommentService.getBlogCommentById(blogCommentId) == null) {
            ResponseEntity.badRequest().build();
        }
        blogCommentService.deleteBlogComment(blogCommentId);
        return ResponseEntity.ok().build();
    }

    //endregion
}
