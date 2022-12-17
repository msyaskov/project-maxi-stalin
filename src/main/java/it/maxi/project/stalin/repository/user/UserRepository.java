package it.maxi.project.stalin.repository.user;

import it.maxi.project.stalin.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
