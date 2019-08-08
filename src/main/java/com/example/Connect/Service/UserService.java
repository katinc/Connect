package com.example.Connect.Service;

import com.example.Connect.Model.User;
import com.example.Connect.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getUsers(){
       return (List<User>) userRepository.findAll();
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> findUser(long id){

        return userRepository.findById(id);
    }

    public void deleteUser(long id){

        userRepository.deleteById(id);
    }
}
