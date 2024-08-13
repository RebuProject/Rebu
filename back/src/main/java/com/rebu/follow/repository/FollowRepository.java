package com.rebu.follow.repository;

import com.rebu.follow.dto.FollowerDto;
import com.rebu.follow.entity.Follow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query(value = """
                   SELECT f FROM Follow f
                   JOIN FETCH f.following
                   WHERE f.follower.id = :followerId
                   AND f.following.status <> 'ROLE_DELETED'
                   """)
    Slice<Follow> findByFollowerId(@Param("followerId") Long followerId, Pageable pageable);

    @Query(value = """
                   SELECT new com.rebu.follow.dto.FollowerDto(
                    f.follower,
                    (CASE
                        WHEN g.follower.id IS NOT NULL THEN true
                        ELSE false
                    END),
                    g.id
                   )
                   FROM Follow f
                   LEFT JOIN Follow g ON f.follower.id = g.following.id AND g.follower.id = :followingId
                   WHERE f.following.id = :followingId
                   AND f.follower.status <> 'ROLE_DELETED'
                   """)
    Slice<FollowerDto> findByFollowingId(@Param("followingId") Long followingId, Pageable pageable);

    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId, Long followingId);

}
