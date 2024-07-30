package com.rebu.profile.repository;

import com.rebu.profile.entity.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>, ProfileCustomRepository {

    @EntityGraph(attributePaths = {"member"})
    Optional<Profile> findByNickname(String nickname);
}
