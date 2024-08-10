package com.rebu.alarm.repository;

import com.rebu.alarm.entity.AlarmComment;
import com.rebu.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmCommentRepository extends JpaRepository<AlarmComment, Long> {
    List<AlarmComment> findByReceiverProfile(Profile profile);
}