package ca.gbc.userservice.repository;

import ca.gbc.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query method to find a user by their username
    User findByUsername(String username);

    // Custom query method to find a user by their email
    User findByUserEmail(String userEmail);
}
