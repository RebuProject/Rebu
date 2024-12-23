package com.rebu.feed.repository;

import com.rebu.feed.entity.Feed;
import com.rebu.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedRepositoryCustom{
    @Query("""
        SELECT f
        FROM Feed f
        JOIN FETCH f.feedImages
        JOIN FETCH f.writer
        LEFT JOIN FETCH f.hashtags
        WHERE f.owner = :profile AND f.type = :type
    """)
    List<Feed> findByOwnerAndType(Profile profile, Feed.Type type);
}
