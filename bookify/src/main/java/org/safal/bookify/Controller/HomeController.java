package org.safal.bookify.Controller;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController 
{
    @GetMapping("/home")    
    public String home(Model model)
    {
        return "home";
    }
    
}
