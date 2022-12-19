package it.maxi.project.stalin.model.vk;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Date;

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
    @ColumnDefault("2000-10-12")
    @Column(nullable = false)
    private Date lastPostDate;

}
