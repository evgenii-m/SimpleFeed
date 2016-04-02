/**
 * 
 */
package org.push.simplefeed.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.push.simplefeed.model.entity.UserEntity;
import org.push.simplefeed.model.service.IUserService;
import org.push.simplefeed.validator.UserFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author push
 * 
 */
@Controller
@RequestMapping("/")
public class IndexController {
    private static Logger logger = LogManager.getLogger(IndexController.class);
    private MessageSource messageSource;
    private IUserService userService;
    @Autowired
    private UserFormValidator userFormValidator;
    
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(userFormValidator);
    }
    
    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
    
    
    @RequestMapping(method = GET)
    public String index() {
        return "index";
    }
    
    
    @RequestMapping(value = "/loginfail")
    public String loginFail(Model uiModel, Locale locale) {
        logger.info("Login fail");
        uiModel.addAttribute("message", messageSource.getMessage("index.loginFailMessage",
                new Object[]{}, locale));
        return "index";
    }
    
    
    @RequestMapping(value = "/register", method = GET)
    public String showRegisterForm(Model uiModel) {
        UserEntity user = new UserEntity();
        uiModel.addAttribute("user", user);
        return "register";
    }
    
    // TODO: add confirm password
    @RequestMapping(value = "/register", method = POST)
    public String register(@ModelAttribute("user") @Validated UserEntity user, BindingResult bindingResult, 
            Model uiModel) {
        if (bindingResult.hasErrors()) {
            logger.error("Error when validate user (" + user + ")\n" 
                    + bindingResult.toString());
            return "register";
        }

//        userService.save(user);
        logger.debug("Added user (" + user + ")");
        return "redirect:/feed";
    }
    
    
}
