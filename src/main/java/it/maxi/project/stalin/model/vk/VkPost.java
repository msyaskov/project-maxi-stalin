package it.maxi.project.stalin.model.vk;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vkpost", uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id", "group_id"})})
public class VkPost {

    @Id
    @Getter@Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter@Setter
    @Column(name = "post_id", nullable = false)
    private Integer postId;

    @OneToOne
    @Getter@Setter
    @JoinColumn(name = "group_id", nullable = false)
    private VkGroup group;

    @Getter@Setter
    @Column(length = 4096)
    private String text;

    @Getter@Setter
    @Column(columnDefinition = "int(11) not null default 0")
    private Integer likes;

    @Getter@Setter
    @Column(columnDefinition = "int(11) not null default 0")
    private Integer comments;

    @Getter@Setter
    @Column(columnDefinition = "int(11) not null default 0")
    private Integer views;

    @Getter@Setter
    @Column(name = "vk_publication_date", nullable = false)
    @DateTimeFormat(pattern = "dd MMM yyyy в HH:mm")
    private LocalDateTime vkPublicationDate;

    @Getter@Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VkPhoto> photos;

}
