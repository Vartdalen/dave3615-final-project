package no.oslomet.userservice.repository;

import no.oslomet.userservice.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
