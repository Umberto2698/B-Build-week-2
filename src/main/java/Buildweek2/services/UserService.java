package Buildweek2.services;

import Buildweek2.enteties.User;
import Buildweek2.exceptions.ItemNotFoundException;
import Buildweek2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    }
}
