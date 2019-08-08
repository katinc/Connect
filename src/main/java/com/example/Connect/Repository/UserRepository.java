package com.example.Connect.Repository;

import com.example.Connect.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
