package no.oslomet.followservice.repository;

import no.oslomet.followservice.model.database.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
