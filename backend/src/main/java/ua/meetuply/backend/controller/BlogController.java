package ua.meetuply.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.meetuply.backend.formbean.BlogCommentForm;
import ua.meetuply.backend.formbean.BlogPostForm;
import ua.meetuply.backend.model.BlogComment;
import ua.meetuply.backend.model.BlogPost;
import ua.meetuply.backend.service.BlogCommentService;
import ua.meetuply.backend.service.BlogPostService;
import ua.meetuply.backend.validator.BlogCommentValidator;
import ua.meetuply.backend.validator.BlogPostValidator;

import java.util.List;

@RequestMapping("api")
@Controller
public class BlogController {

    @Autowired
    private BlogPostValidator blogPostValidator;

    @Autowired
    BlogPostService blogPostService;

    @Autowired
    BlogCommentService blogCommentService;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        // Form target
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == BlogPostForm.class) {
            dataBinder.setValidator(blogPostValidator);
        }
    }

    @RequestMapping("/blogPosts")
    public String viewBlogPosts(Model model) {

        List<BlogPost> list = blogPostService.getBlogPosts();
        model.addAttribute("blogPosts", list);
        return "blog/listBlogPostsPage";
    }

    @RequestMapping(value = "/newBlogPost", method = RequestMethod.GET)
    public String viewNewBlogPost(Model model) {
        BlogPostForm form = new BlogPostForm();
        model.addAttribute("blogPostForm", form);
        return "blog/newBlogPostPage";
    }

    @RequestMapping(value = "/newBlogPost", method = RequestMethod.POST)
    public String saveNewBlogPost(Model model, //
                                  @ModelAttribute("blogPostForm") @Validated BlogPostForm blogPostForm, //
                                  BindingResult result, //
                                  final RedirectAttributes redirectAttributes) {

        // Validate result
        if (result.hasErrors()) {
            return "blog/newBlogPostPage";
        }
        BlogPost bp= null;
        try {
            bp = blogPostService.createBlogPost(blogPostForm);
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "blog/newBlogPostPage";
        }

        redirectAttributes.addFlashAttribute("flashBlogPost", bp);

        return "redirect:/blogPosts";
    }

//   @RequestMapping(value = "/email", method = RequestMethod.GET)
//   @ResponseBody
//   public String currentUserName(Principal principal) {
//      return principal.getName();
//   }


    //Blog comment section


    @Autowired
    private BlogCommentValidator blogCommentValidator;

    @RequestMapping(value = "/blogPosts/{postid}", method = RequestMethod.GET)
    public String viewCurrentBlogPost(@PathVariable("postid") int postid, Model model) {
        BlogPost post = blogPostService.getBlogPostById(postid);
        model.addAttribute("blogPost", post);

        List<BlogComment> list = blogCommentService.getBlogCommentsByPostId(postid);
        model.addAttribute("blogComments", list);

        BlogCommentForm form = new BlogCommentForm();
        model.addAttribute("blogCommentForm", form);
        return "blog/currentBlogPostPage";
    }

    @RequestMapping(value = "/postComment/{postid}", method = RequestMethod.POST)
    public String saveNewBlogComment(@PathVariable("postid") long postid,
                                     Model model, //
                                     @ModelAttribute("blogCommentForm") @Validated BlogCommentForm blogCommentForm, //
                                     BindingResult result, //
                                     final RedirectAttributes redirectAttributes) {

        // Validate result
        if (result.hasErrors()) {
            return "blogPosts/{postid}";
        }
        BlogComment bc= null;
        try {
            bc = blogCommentService.createBlogComment(blogCommentForm);
        }
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "blogPosts";
        }

        redirectAttributes.addFlashAttribute("flashBlogComment", bc);

        return "redirect:/blogPosts/{postid}";
    }
}