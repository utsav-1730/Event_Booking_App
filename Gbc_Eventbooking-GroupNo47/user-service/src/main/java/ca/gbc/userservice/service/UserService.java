package ca.gbc.userservice.service;

import ca.gbc.userservice.model.User;
import ca.gbc.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Injecting UserRepository through constructor
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Save or update a user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Find a user by ID
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    // Find a user by username
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Find a user by email
    public User findUserByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    // Get a list of all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
