package it.maxi.project.stalin.model.vk;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "vkgroup")
public class VkGroup {

    @Id
    @Getter@Setter
    private Integer id;

    @Getter@Setter
    @Column(nullable = false, unique = true)
    private String domain;

    @Getter@Setter
    @ColumnDefault("0")
    @Column(nullable = false)
    private Date date;

    @Getter@Setter
    @OneToMany(cascade = CascadeType.ALL)
    private Set<VkPost> vkPosts;

}
