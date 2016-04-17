/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import java.security.Principal;

import javax.validation.Valid;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.FeedSourceEntity;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.service.IFeedSourceService;
import org.push.simplefeed.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author push
 *
 */
@Controller
@RequestMapping("/source")
public class SourceController {
    private static Logger logger = LogManager.getLogger(SourceController.class);
    private IFeedSourceService feedSourceService;
    private IUserService userService;


    @Autowired
    public void setFeedSourceService(IFeedSourceService feedSourceService) {
        this.feedSourceService = feedSourceService;
    }
    
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    
    
    private void validateFeedSourceUrl(String feedSourceUrl, Errors errors) {
        if (!UrlValidator.getInstance().isValid(feedSourceUrl)) {
            errors.rejectValue("url", "validation.url");       
        } else if (!feedSourceService.isSupported(feedSourceUrl)) {
            errors.rejectValue("url", "validation.unsupportedFeedSource");            
        }
    }
    
    

    @RequestMapping(method = GET)
    public String showSources(Model uiModel, Principal principal) {
        logger.debug("showSources");
        UserEntity user = userService.findByEmail(principal.getName());
        uiModel.addAttribute("feedSources", user.getFeedSources());
        if (!uiModel.containsAttribute("newFeedSource")) {
            uiModel.addAttribute("newFeedSource", new FeedSourceEntity());
        }
        return "source/list";
    }
    

    @RequestMapping(method = POST)
    public String addFeedSource(@ModelAttribute("newFeedSource") FeedSourceEntity newFeedSource, 
            BindingResult bindingResult, Model uiModel, RedirectAttributes redirectAttributes) {
        logger.debug("addFeedSource");
        validateFeedSourceUrl(newFeedSource.getUrl(), bindingResult);
        if (bindingResult.hasErrors()) {
            logger.error("Error when validate feed source (url=" + newFeedSource.getUrl() + ")\n"
                    + bindingResult.toString());
            redirectAttributes.addFlashAttribute("newFeedSource", newFeedSource);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.newFeedSource", bindingResult);
            return "redirect:/source";
        }

        feedSourceService.fillBlank(newFeedSource);
        redirectAttributes.addFlashAttribute("feedSource", newFeedSource);
        return "redirect:/source/add";
    }


    @RequestMapping(value = "/add", method = GET)
    public String showAddFeedSourceForm(Model uiModel) {
        logger.debug("showAddFeedSourceForm");
        if (!uiModel.containsAttribute("feedSource")) {
            FeedSourceEntity feedSource = feedSourceService.getBlank();
            uiModel.addAttribute("feedSource", feedSource);
            logger.debug("Added blank feedSource to uiModel");
        }
        return "source/edit";
    }
    
    
    @RequestMapping(value = "/edit/{id}", method = GET)
    public String showEditFeedSourceForm(@PathVariable("id") Long id, Model uiModel, Principal principal) {
        logger.debug("showEditFeedSourceForm (id=" + id + ")");
        UserEntity user = userService.findByEmail(principal.getName());
        FeedSourceEntity feedSource = feedSourceService.findById(id);
        if ((feedSource != null) && (feedSource.getUser().equals(user))) {
            uiModel.addAttribute("feedSource", feedSource);
        } else {
            logger.error("Feed source (feedSource.id=" + id + ") not found for user (user.id=" + 
                    user.getId() + ")");
        }
        return "source/edit";
    }
        
    
    @RequestMapping(value = {"/add", "/edit/{id}"}, method = POST)
    public String saveFeedSource(@ModelAttribute("feedSource") @Valid FeedSourceEntity feedSource,
            BindingResult bindingResult, Model uiModel, Principal principal) {
        logger.debug("saveFeedSource");
        validateFeedSourceUrl(feedSource.getUrl(), bindingResult);
        if (bindingResult.hasErrors()) {
            logger.error("Error when validate feed source (" + feedSource + ")\n"
                    + bindingResult.toString());
            return "source/edit";
        }

        UserEntity user = userService.findByEmail(principal.getName());
        feedSourceService.save(feedSource, user);
        logger.debug("Added/updated feed source (" + feedSource + ")");
        return "redirect:/source";
    }

    
    @RequestMapping(value = "/delete/{id}", method = DELETE)
    @ResponseBody
    public boolean deleteFeedSource(@PathVariable("id") Long id) {
        logger.debug("deleteFeedSource (id=" + id + ")");
        return feedSourceService.delete(id);
    }
    
}
