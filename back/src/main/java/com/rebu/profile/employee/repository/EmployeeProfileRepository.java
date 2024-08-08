package com.rebu.profile.employee.repository;

import com.rebu.profile.employee.entity.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {
    List<EmployeeProfile> findByShopId(Long shopId);

    @Query("SELECT e FROM EmployeeProfile e WHERE e.member.id = :memberId AND e.status <> 'ROLE_DELETED'")
    Optional<EmployeeProfile> findEmployeeProfileByMemberId(Long memberId);

    Optional<EmployeeProfile> findByNickname(String nickname);
}
