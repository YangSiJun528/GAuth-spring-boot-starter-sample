package dev.yangsijun.gauth.sample.basic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GAuthUserRepository extends JpaRepository<GAuthUserEntity, Long> {
    Optional<GAuthUserEntity> findByEmail(String email);
}
