package it.maxi.project.stalin.model.vk;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vkphoto")
public class VkPhoto {

    @Id
    @Getter@Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter@Setter
    @Column(nullable = false)
    private String url;

}
