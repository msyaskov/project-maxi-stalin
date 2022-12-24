package it.maxi.project.stalin.model.vk;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "vkgroup")
public class VkGroup {

    @Id
    @Getter@Setter
    private Integer id;

    @Getter@Setter
    @Column(nullable = false)
    private String domain;

    @Getter@Setter
    @Column(nullable = false)
    private String name;

    @Getter@Setter
    @OneToOne(fetch = FetchType.LAZY)
    private VkPhoto cover;

    @Getter@Setter
    @Column(nullable = false)
    private LocalDateTime lastPostDate;

}
