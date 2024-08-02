package com.rebu.profile.employee.repository;

import com.rebu.profile.employee.entity.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {
}
