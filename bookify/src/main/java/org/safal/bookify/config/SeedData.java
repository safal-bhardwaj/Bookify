package org.safal.bookify.config;

import org.safal.bookify.models.Book;
import org.safal.bookify.models.Users;
import org.safal.bookify.services.BookService;
import org.safal.bookify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    private UserService usersService;
   

    @Override
    public void run(String... args) throws Exception {
       
        List<Users> users = usersService.getAll();
       
        if(users.size() == 0)
        {
            Users user1 = new Users();
            user1.setEmail("test@gmail.com");
            user1.setPassword("test");
            user1.setName("test");
            usersService.save(user1);
        }
             
    }



   
    
    
}
