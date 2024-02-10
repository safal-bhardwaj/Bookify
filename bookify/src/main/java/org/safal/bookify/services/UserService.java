package org.safal.bookify.services;

import java.util.List;
import java.util.Optional;

import org.safal.bookify.models.Users;
import org.safal.bookify.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService 
{
    @Autowired
    private UserRepository userRepository;

    public Optional<Users> getById(Long id)
    {
        return userRepository.findById(id);
    }
    public List<Users> getAll()
    {
        return userRepository.findAll();
    }
    public Optional<Users> getByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }
    public void delete(Users user)
    {
        userRepository.delete(user);
    }
    public Users save(Users user)
    {
        return userRepository.save(user);
    }

}
