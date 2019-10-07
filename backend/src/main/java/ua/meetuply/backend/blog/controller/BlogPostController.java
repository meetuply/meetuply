package ua.meetuply.backend.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.meetuply.backend.blog.dao.BlogPostDAO;
import ua.meetuply.backend.blog.formbean.BlogPostForm;
import ua.meetuply.backend.blog.model.BlogPost;
import ua.meetuply.backend.blog.validator.BlogPostValidator;

@Controller
public class BlogPostController {

    @Autowired
    private BlogPostDAO blogPostDAO;

    @Autowired
    private BlogPostValidator blogPostValidator;

    // Set a form validator
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

    @RequestMapping("/")
    public String viewHome(Model model) {
        return "welcomePage";
    }

    @RequestMapping("/blogPosts")
    public String viewMembers(Model model) {

        List<BlogPost> list = blogPostDAO.getBlogPosts();

        model.addAttribute("blogPosts", list);

        return "listBlogPostsPage";
    }

    @RequestMapping(value = "/newPost", method = RequestMethod.GET)
    public String viewNewBlogPost(Model model) {

        BlogPostForm form = new BlogPostForm();

        model.addAttribute("blogPostForm", form);

        return "newBlogPostPage";
    }

    @RequestMapping(value = "/newPost", method = RequestMethod.POST)
    public String saveNewBlogPost(Model model, //
                               @ModelAttribute("blogPostForm") @Validated BlogPostForm blogPostForm, //
                               BindingResult result, //
                               final RedirectAttributes redirectAttributes) {

        // Validate result
        if (result.hasErrors()) {
            return "newBlogPostPage";
        }
        BlogPost bp= null;
        try {
            bp = blogPostDAO.createBlogPost(blogPostForm);
        }
        // Other error!!
        catch (Exception e) {
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "newBlogPostPage";
        }

        redirectAttributes.addFlashAttribute("flashBlogPost", bp);

        return "redirect:/listBlogPosts";
    }

}
