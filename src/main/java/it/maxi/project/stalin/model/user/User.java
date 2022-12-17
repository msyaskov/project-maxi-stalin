package it.maxi.project.stalin.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Getter@Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter@Setter
    @Column(unique = true, nullable = false)
    private String username;

    @Getter@Setter
    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @Getter@Setter
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Role> roles;

}
