package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ua.meetuply.backend.model.BlogComment;
import ua.meetuply.backend.model.BlogPost;
import ua.meetuply.backend.service.AppUserService;
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

    @Autowired
    AppUserService appUserService;

    @GetMapping()
    public @ResponseBody Iterable<BlogPost> getAllBlogPosts() {
        return blogPostService.getBlogPosts();
    }

    @GetMapping("/{post-id}")
    public BlogPost getBlogPost(@PathVariable("post-id") Integer blogPostId) {
        return blogPostService.getBlogPostById(blogPostId);
    }

    @GetMapping("/user/{user-id}/{start-row}/{end-row}")
    public @ResponseBody Iterable<BlogPost> getBlogPostsByUserId(@PathVariable("user-id") Integer userId,
                                                                 @PathVariable("start-row") Integer startRow,
                                                                 @PathVariable("end-row") Integer endRow) {
        return blogPostService.getBlogPostByUserId(startRow,endRow,userId);
    }

    @GetMapping("/{filter}/{start-row}/{end-row}")
    public @ResponseBody Iterable<BlogPost> getBlogPostsChunk(@PathVariable("start-row") Integer startRow,
                                                              @PathVariable("end-row") Integer endRow,
                                                              @PathVariable("filter") String filter) {
        return blogPostService.getBlogPostsChunk(startRow,endRow,filter);
    }

    @PostMapping()
    public ResponseEntity createBlogPost(@Valid @RequestBody BlogPost blogPost){
        if (blogPost.getBlogPostContent().length()<=0 || blogPost.getBlogPostTitle().length()<=0)
            return ResponseEntity.noContent().build();
        blogPostService.createBlogPost(blogPost);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{post-id}")
    public ResponseEntity updateBlogPost(@PathVariable("post-id") Integer blogPostId,
                                                   @RequestBody BlogPost blogPost) {
        if (!(appUserService.getCurrentUser().getRole().getRoleName().equals("admin")))
            return ResponseEntity.badRequest().build();
        if (blogPostService.getBlogPostById(blogPostId) == null) {
            return ResponseEntity.badRequest().build();
        }
        blogPost.setBlogPostId(blogPostId);
        blogPostService.updateBlogPost(blogPost);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{post-id}")
    public ResponseEntity deleteBlogPost(@PathVariable("post-id") Integer blogPostId){
        if (!(appUserService.getCurrentUser().getRole().getRoleName().equals("admin")))
            return ResponseEntity.badRequest().build();
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

    @GetMapping("/{post-id}/comments/{startRow}/{endRow}")
    public @ResponseBody
    Iterable<BlogComment> getBlogPostsChunk(@PathVariable("post-id") Integer blogPostId, @PathVariable("startRow") Integer startRow,@PathVariable("endRow") Integer endRow) {
        return blogCommentService.getBlogCommentsChunk(blogPostId,startRow,endRow);
    }

    @GetMapping("/comments/{comment-id}")
    public BlogComment getBlogComment(@PathVariable("comment-id") Integer blogCommentId) {
        return blogCommentService.getBlogCommentById(blogCommentId);
    }

    @PostMapping("/{post-id}/comments")
    public ResponseEntity createBlogComment(@PathVariable("post-id") Integer blogPostId,
                                                            @Valid @RequestBody BlogComment blogComment){
        if (blogComment.getBlogCommentContent().length()<=0)
            return ResponseEntity.noContent().build();
        blogCommentService.createBlogComment(blogComment, blogPostId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/comments/{comment-id}")
    public ResponseEntity updateBlogComment(@PathVariable("comment-id") Integer blogCommentId,
                                                      @RequestBody BlogComment blogComment) {
        if (!(appUserService.getCurrentUser().getRole().getRoleName().equals("admin")))
            return ResponseEntity.badRequest().build();
        if (blogCommentService.getBlogCommentById(blogCommentId) == null) {
            return ResponseEntity.badRequest().build();
        }
        blogComment.setBlogCommentId(blogCommentId);
        blogCommentService.updateBlogComment(blogComment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity deleteBlogComment(@PathVariable("comment-id") Integer blogCommentId){
        if (!(appUserService.getCurrentUser().getRole().getRoleName().equals("admin")))
            return ResponseEntity.badRequest().build();
        if (blogCommentService.getBlogCommentById(blogCommentId) == null) {
            return ResponseEntity.badRequest().build();
        }
        blogCommentService.deleteBlogComment(blogCommentId);
        return ResponseEntity.ok().build();
    }

    //endregion
}
