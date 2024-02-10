package org.safal.bookify.Controller;

import java.util.List;
import java.util.Optional;

import org.safal.bookify.exceptions.UserAlreadyExistException;
import org.safal.bookify.exceptions.UserNotFoundException;
import org.safal.bookify.exceptions.WrongPasswordException;
import org.safal.bookify.models.Users;
import org.safal.bookify.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UserController 
{
    @Autowired
   private UserService userService ;
   @GetMapping("/users")  
public List <Users> retriveAllUsers()  
{  
return userService.getAll();  
}

@GetMapping("/users/{id}")  
public  Optional<Users> retriveUserById(@PathVariable long id)  
{  
return userService.getById(id);  
}

@PostMapping("/users/login")
public Optional<Users> login(@RequestBody Users newuser)  
{  
    Optional<Users> user = userService.getByEmail(newuser.getEmail().toLowerCase());  
    if(user.isEmpty())
    {
        throw new UserNotFoundException("email :  "+newuser.getEmail().toLowerCase());  
    }
    else
    {
        if(user.get().getPassword().equals(newuser.getPassword()))
        {
       return user;
        }
        else
        {
        throw new WrongPasswordException("Wrong Password");
        }
    }

}
@PostMapping("/users/signup")
public Optional<Users> signup(@RequestBody Users newuser)  
{  
    if(userService.getByEmail(newuser.getEmail().toLowerCase()).isPresent())
    {
        throw new UserAlreadyExistException("Email Already in use!");
    }
    else
    {
        newuser.setEmail(newuser.getEmail().toLowerCase());
        Users user = userService.save(newuser);
        return userService.getById(user.getId());
    }  
    
}


}
