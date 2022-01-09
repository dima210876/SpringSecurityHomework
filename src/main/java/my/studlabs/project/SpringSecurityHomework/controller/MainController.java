package my.studlabs.project.SpringSecurityHomework.controller;

import my.studlabs.project.SpringSecurityHomework.model.User;
import my.studlabs.project.SpringSecurityHomework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController
{
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) { this.userService = userService; }

    @PostConstruct
    public void setup()
    {
        if (userService.getAll().size() == 0)
        {
            List<User> users = List.of(
                    new User("alex.semenov@gmail.com", "alex12345", "Александр", "user"),
                    new User("igor.menshikov@gmail.com", "igor12345", "Игорь", "user"),
                    new User("elena.osinenko@gmail.com", "elena12345", "Елена", "admin"),
                    new User("dmitriy.kumichev@gmail.com", "dmitriy12345", "Дмитрий", "admin")
            );
            users.forEach(userService::register);
        }
    }

    @GetMapping("/")
    public String mainPage(Model model)
    {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model)
    {
        return "login";
    }

    @GetMapping("/userList")
    public String userList(Model model, @AuthenticationPrincipal User currentUser)
    {
        model.addAttribute("currentUser", currentUser);
        List<User> users = userService.getAll();
        model.addAttribute("userList", users);
        return "userList";
    }
}
