package it.maxi.project.stalin.repository.user;

import it.maxi.project.stalin.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
