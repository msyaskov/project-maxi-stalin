package it.maxi.project.stalin.repository.vk;

import it.maxi.project.stalin.model.vk.VkGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VkGroupRepository extends JpaRepository<VkGroup, Integer> {

    VkGroup findByDomain(String domain);

}
