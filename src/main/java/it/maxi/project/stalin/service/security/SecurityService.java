package it.maxi.project.stalin.service.security;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);

}
