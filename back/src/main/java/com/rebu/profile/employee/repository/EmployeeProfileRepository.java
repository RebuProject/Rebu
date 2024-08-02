package com.rebu.profile.employee.repository;

import com.rebu.profile.employee.entity.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {
    List<EmployeeProfile> findByShopId(Long shopId);
}
