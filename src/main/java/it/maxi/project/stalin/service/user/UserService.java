package it.maxi.project.stalin.service.user;

import it.maxi.project.stalin.model.user.User;

public interface UserService {

    User save(User user);

    User findByUserName(String username);

}
