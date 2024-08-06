package com.rebu.reservation.repository;

import com.rebu.profile.employee.entity.EmployeeProfile;
import com.rebu.profile.shop.entity.ShopProfile;
import com.rebu.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByEmployeeProfileAndStartDateTimeBetween(EmployeeProfile employeeProfile, LocalDateTime startDate, LocalDateTime endDate);
}
